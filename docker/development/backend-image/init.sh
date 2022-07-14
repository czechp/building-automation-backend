#!/bin/bash 
rm -rf App
mkdir App
cd App
rm -rf *
rm -rf .*
git clone https://github.com/czechp/building-automation-backend.git .
mvn clean install package -DskipTests
java -jar core/target/core-0.0.1-SNAPSHOT.jar --spring.profiles.active=integration-development
