#!/bin/bash
nohup java -Dspring.profiles.active=$(cat '/var/www/env.conf') -jar /var/www/*.jar &> /dev/null &
disown
rm -- "$0"