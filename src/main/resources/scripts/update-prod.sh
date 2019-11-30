#!/bin/bash
pkill -9 java
nohup java -Dspring.profiles.active=prod -jar /var/www/*.jar &> /dev/null &
disown
rm -- "$0"