#!/bin/bash

# remove stop containers and delete all images
# docker rm $(docker ps -a -q) | exit 0 && docker image prune -a

docker build -t photoapp-base-docker ./photoapp-base-docker \
    --build-arg LOCAL_ARTIFACTORY_URL="http://localhost:7082/artifactory/maven-virtual/" \
&& \
docker-compose build --parallel --force-rm --progress auto \
&& \
docker-compose pull
