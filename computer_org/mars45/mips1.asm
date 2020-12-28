       .data
strings: .asciiz  "Alpha\nBravo\nChina\nDelta\nEcho\nFoxtrot\nGolf\nHotel\nIndia\nJuliet\nKilo\nLima\nMary\nNovember\nOscar\nPaper\nQuebec\nResearch\nSierra\nTango\nUniform\nVictor\nWhisky\nX-ray\nYankee\nZulu\nalpha\nbravo\nchina\ndelta\necho\nfoxtrot\ngolf\nhotel\nindia\njuliet\nkilo\nlima\nmary\nnovember\noscar\npaper\nquebec\nresearch\nsierra\ntango\nuniform\nvictor\nwhisky\nx-ray\nyankee\nzulu\nzero\nFirst\nSecond\nThird\nFourth\nFifth\nSixth\nSeventh\nEighth\nNinth\n"
hint: .asciiz "Please input a character:\n"
star: .asciiz "\n*\n"
starts: .word 0 : 62
ends: .word 0 : 62
size: .word 62
       .text
       la $t0, strings
       la $t6, size
       lw $t6, 0($t6)
       la $t1, starts
       la $t2, ends
       li $t3, 0
loop:  add $t3, $t3, 1        # Establish the mapping between character and corresponding string
       add $t4, $t0, $t3
       lb $t5, 0($t4)
       bne $t5, 10, loop      # Stop when see a carriage
       sw $t0, 0($t1)
       sw $t4, 0($t2)
       add $t1, $t1, 4
       add $t2, $t2, 4
       add $t0, $t4, 1
       li $t3, 0
       add $t6, $t6, -1
       bgtz $t6, loop
rp:    la   $a0, hint
       li   $v0, 4
       syscall
       li   $v0, 12
       syscall
       beq  $v0, 63, end   # Exit program when input is '?'
       blt $v0, 48, invalid
       blt $v0, 58, number
       blt $v0, 65, invalid
       blt $v0, 91, uppercase
       blt $v0, 97, invalid
       blt $v0, 123, lowercase
       j invalid
number:
       add $a0, $v0, 4     # Print number, the index of number is 52 + v - 48
       j callp
uppercase:
       add $a0, $v0, -65   # Print uppercase string, the index of number is 0 + v - 65
       j callp
lowercase:
       add $a0, $v0, -71   # Print lowercase string, the index of number is 26 + v - 97
       j callp
callp: mul $a0, $a0, 4
       la $t1, starts
       la $t2, ends
       add $t1, $t1, $a0
       add $t2, $t2, $a0
       lw $a0, 0($t1)
       lw $a1, 0($t2)
       jal print
       j rp
invalid:
       la $a0, star
       li $v0, 4
       syscall
       j rp
print: add  $t0, $zero, $a0
       add  $t1, $zero, $a1
       li $a0, 10
       li $v0, 11
       syscall
lpb:   lb $a0, 0($t0)
       li $v0, 11
       syscall
       add $t0, $t0, 1
       ble $t0, $t1, lpb
       jr   $ra
end:   li  $v0, 10
       syscall
