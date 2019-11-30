#!/bin/bash
pkill -9 java
java -Dspring.profiles.active=prod -jar /var/www/*.jar
disown
rm -- "$0"