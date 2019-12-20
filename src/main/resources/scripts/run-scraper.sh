#!/bin/bash
nohup java -Dspring.profiles.active=$1 -jar /var/www/*.jar &> /dev/null &
disown
rm -- "$0"