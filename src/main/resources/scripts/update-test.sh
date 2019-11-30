#!/bin/bash
pkill -9 java
java -Dspring.profiles.active=test -jar /var/www/*.jar
disown
rm -- "$0"