#!/bin/sh

# 各自の環境に合わせて記述
#CLASSPATH=/usr/share/tomcat7/lib/mysql-connector-java-5.1.31-bin.jar:.

#export CLASSPATH
java -classpath .:./mysql-connector-java-5.1.36-bin.jar $1
