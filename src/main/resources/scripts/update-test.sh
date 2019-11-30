#!/bin/bash
pkill -9 java
nohup java -Dspring.profiles.active=test -jar /var/www/*.jar &> /dev/null &
disown
rm -- "$0"