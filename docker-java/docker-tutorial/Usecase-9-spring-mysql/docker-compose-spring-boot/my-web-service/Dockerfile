# Install FROM UBUNTU IMAGE
FROM ubuntu:21.04

LABEL "Maintainer"="S1lv10Fr4gn4n1"

# Set environment variables
ENV TZ=Europe/Berlin
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

# Update ubuntu repositories
RUN apt-get update && apt-get install -y \
    software-properties-common

# Adds the repository where JDK 8 can be obtained for UBUNTU
RUN add-apt-repository ppa:webupd8team/java

# Install linux dependencies
RUN apt-get install -y \
    iputils-ping \
    nano \
    mysql-client \
    maven \
    openjdk-8-jdk

ENV JAVA_HOME="/usr/lib/jvm/java-8-openjdk-amd64/jre"

# add the directory to the path
ADD . /usr/local/web-service

WORKDIR /usr/local/web-service

# Run maven
RUN mvn clean package

#Spring boot initiation
CMD ["java","-jar","-DlogPath=/usr/local/web-service", "/usr/local/web-service/target/docker-compose-mywebservice-1.0.0-SNAPSHOT.jar"]