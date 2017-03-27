package io.haiboyan;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class HackAssembler {
    private static class SymbolTable {
        final HashMap<String, Integer> symbols = new HashMap<>();

        private int nextSlot = 16;

        SymbolTable() {
            symbols.put("SP", 0);
            symbols.put("LCL", 1);
            symbols.put("ARG", 2);
            symbols.put("THIS", 3);
            symbols.put("THAT", 4);
            symbols.put("R0", 0);
            symbols.put("R1", 1);
            symbols.put("R2", 2);
            symbols.put("R3", 3);
            symbols.put("R4", 4);
            symbols.put("R5", 5);
            symbols.put("R6", 6);
            symbols.put("R7", 7);
            symbols.put("R8", 8);
            symbols.put("R9", 9);
            symbols.put("R10", 10);
            symbols.put("R11", 11);
            symbols.put("R12", 12);
            symbols.put("R13", 13);
            symbols.put("R14", 14);
            symbols.put("R15", 15);
            symbols.put("SCREEN", 16384);
            symbols.put("KBD", 24576);
        }

        int getSlot(String variableName) {
            if (!symbols.containsKey(variableName)) {
                symbols.put(variableName, nextSlot++);
            }
            return symbols.get(variableName);
        }

        void putLabel(String label, int lineNo) {
            symbols.put(label, lineNo);
        }
    }

    private final static HashMap<String, String> operatorsTable = new HashMap<>();

    static {
        operatorsTable.put("0",   "0101010");
        operatorsTable.put("1",   "0111111");
        operatorsTable.put("-1",  "0111010");
        operatorsTable.put("D",   "0001100");
        operatorsTable.put("A",   "0110000");
        operatorsTable.put("!D",  "0001101");
        operatorsTable.put("!A",  "0110001");
        operatorsTable.put("-D",  "0001111");
        operatorsTable.put("-A",  "0110011");
        operatorsTable.put("D+1", "0011111");
        operatorsTable.put("A+1", "0110111");
        operatorsTable.put("D-1", "0001110");
        operatorsTable.put("A-1", "0110010");
        operatorsTable.put("D+A", "0000010");
        operatorsTable.put("D-A", "0010011");
        operatorsTable.put("A-D", "0000111");
        operatorsTable.put("D&A", "0000000");
        operatorsTable.put("D|A", "0010101");
        operatorsTable.put("M",   "1110000");
        operatorsTable.put("!M",  "1110001");
        operatorsTable.put("-M",  "1110011");
        operatorsTable.put("M+1", "1110111");
        operatorsTable.put("M-1", "1110010");
        operatorsTable.put("D+M", "1000010");
        operatorsTable.put("D-M", "1010011");
        operatorsTable.put("M-D", "1000111");
        operatorsTable.put("D&M", "1000000");
        operatorsTable.put("D|M", "1010101");
    }

    private final static HashMap<String, String> destinationsTable = new HashMap<>();

    static {
        destinationsTable.put("",    "000");
        destinationsTable.put("M",   "001");
        destinationsTable.put("D",   "010");
        destinationsTable.put("MD",  "011");
        destinationsTable.put("A",   "100");
        destinationsTable.put("AM",  "101");
        destinationsTable.put("AD",  "110");
        destinationsTable.put("AMD", "111");
    }


    private final static HashMap<String, String> jmpTable = new HashMap<>();

    static {
        jmpTable.put("",    "000");
        jmpTable.put("JGT", "001");
        jmpTable.put("JEQ", "010");
        jmpTable.put("JGE", "011");
        jmpTable.put("JLT", "100");
        jmpTable.put("JNE", "101");
        jmpTable.put("JLE", "110");
        jmpTable.put("JMP", "111");
    }

    private final static SymbolTable SYMBOL_TABLE = new SymbolTable();

    private static HashMap<String, Integer> retrieveAllLabels(List<String> sources) {
        int index = 0;

        HashMap<String, Integer> labels = new HashMap<>();

        for (String line : sources) {
            if (line.startsWith("(") && line.endsWith(")")) {
                String label = line.substring(line.indexOf('(') + 1, line.indexOf(')'));
                SYMBOL_TABLE.putLabel(label, index);
            } else {
                index++;
            }
        }

        return labels;
    }

    private static String aInstruction(String operand) {
        int intValue;
        try {
            intValue = Integer.parseInt(operand);
        } catch (NumberFormatException nfe) {
            intValue = SYMBOL_TABLE.getSlot(operand);
        }
        return String.format("%16s", Integer.toBinaryString(intValue)).replace(' ', '0');
    }

    private static String cInstruction(String instruction) {

        String jmp = "", destination = "";

        String[] splitBySemicolon = instruction.split(";");
        if (splitBySemicolon.length == 2) {
            jmp = splitBySemicolon[1];
        }

        String[] splitByEqualSign = splitBySemicolon[0].split("=");

        if (splitByEqualSign.length == 2) {
            destination = splitByEqualSign[0];
        }

        String operator = splitByEqualSign[splitByEqualSign.length - 1];

        return "111" + operatorsTable.get(operator) + destinationsTable.get(destination) + jmpTable.get(jmp);
    }

    public static String parseLine(String line) {
        if (line.startsWith("@")) {
            return aInstruction(line.substring(1));
        } else {
            return cInstruction(line);
        }
    }

    public static void main(String[] args) throws IOException {
        // write your code here
        String source = args[0];

        String target = args[1];

        List<String> lines = Files.readAllLines(Paths.get(source));

        List<String> sourceLines = lines.stream().map(line -> line.indexOf("//") == -1 ? line :
                line.substring(0, line.indexOf("//"))).map(String::trim).filter(line -> !line.isEmpty()).collect(toList());

        retrieveAllLabels(sourceLines);

        List<String> hacks = sourceLines.stream().filter(line -> !line.startsWith("(")).filter(line -> !line.endsWith(")"))
                .map(HackAssembler::parseLine).collect(toList());

        Files.write(Paths.get(target), hacks, new OpenOption[0]);
    }
}
