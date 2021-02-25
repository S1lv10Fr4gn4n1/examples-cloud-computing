FROM openjdk:8-jdk-alpine

LABEL "Maintainer"="S1lv10Fr4gn4n1"

# Install linux dependencies
RUN apk add --update maven curl

ENV CONTAINER_ID="EMPTY"

# set custom entrypoint
# https://stackoverflow.com/a/58827999/1723665
COPY entrypoint.sh /entrypoint.sh
RUN chmod +x ./entrypoint.sh
ENTRYPOINT ["/entrypoint.sh"]

ARG LOCAL_ARTIFACTORY_URL
ENV LOCAL_ARTIFACTORY="${LOCAL_ARTIFACTORY_URL}"