#!/bin/sh
#

echo "Running compile script..."
mvn compile && mvn exec:java -Dexec.mainClass=com.henrik.Main
