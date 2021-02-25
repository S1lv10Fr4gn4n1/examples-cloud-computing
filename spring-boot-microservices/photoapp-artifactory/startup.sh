#!/bin/bash

echo "This script will:"
echo " - stop container if is currently running"
echo " - delete host persisted folder"
echo " - create new host folder"
echo " - create and start new artifactory container"
echo ""
echo "Are you sure you want to continue? (Press any key to continue)"
read REPLY -p
echo ""

echo "--> stopping current running container if exists"
docker stop artifactory
echo "--> deleting old container if exists"
docker rm artifactory
echo "--> deleting old host folder"
sudo rm -rf /var/tmp/artifactory_data_v1
sudo rm -rf /var/tmp/artifactory_data_v2

echo "--> creating host folder to persist configurations and data from artifactory"
sudo mkdir /var/tmp/artifactory_data_v1
sudo mkdir /var/tmp/artifactory_data_v2
echo "--> updating host folder's permissions"
sudo chown 1030:1030 /var/tmp/artifactory_data_v1/
sudo chown 1030:1030 /var/tmp/artifactory_data_v2/

# echo "--> starting artifactoty container"
# docker run -d -p 7081:8081 -p 7082:8082 \
#     --name artifactory \
#     -v /var/tmp/artifactory_data:/var/opt/jfrog/artifactory/  \
#     docker.bintray.io/jfrog/artifactory-oss:7.12.5

# Manual set up
# - create remote repositories to
#     - spring-release https://repo.spring.io/release
#     - spring-snapshot https://repo.spring.io/snapshot
#
# Maybe
#     - maven-repo1 https://repo1.maven.org/maven2/
#     - maven2 https://repo.maven.apache.org/maven2/
#     - spring-milestone https://repo.spring.io/milestone
#
# - create virtual repository (maven-virtual) and add:
#     - spring-release
#     - spring-snapshot

# Auxiliar commands
# docker stop artifactory
# docker start artifactory