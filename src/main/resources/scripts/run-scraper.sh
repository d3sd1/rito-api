#!/bin/bash
nohup java -Dspring.profiles.active=$(cat 'env.conf') -jar /var/www/*.jar &> /dev/null &
disown
rm -- "$0"