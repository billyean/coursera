#!/bin/sh

RUN_HOME=`dirname $0`

if [ -z "${JAVA_HOME}" ]; then
  echo "Please set HAVA_HOME before run it"
  exit 1
fi

${JAVA_HOME}/bin/javac ${RUN_HOME}/io/haiboyan/HackAssembler.java

for asm in add/Add max/Max max/MaxL rect/Rect rect/RectL pong/PongL pong/Pong
do
  java io.haiboyan.HackAssembler ${asm}.asm ${asm}.hack
done
