#Install from the same image built i usecase - 8
# FROM pictolearn/ubuntu-jdk8
FROM ubuntu:16.04

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
    nano \
    maven \
    openjdk-8-jdk

ENV JAVA_HOME="/usr/lib/jvm/java-8-openjdk-amd64/jre"

ENV PICTOLEARN_HOME /usr/local/pictolearn

# add the directory to the path
ADD . /usr/local/pictolearn

#Add a directory for logs
RUN mkdir -p /usr/local/pictolearn/logs

# Run maven
RUN cd /usr/local/pictolearn && mvn clean package

CMD ["java","-jar","-DlogPath=/usr/local/pictolearn/logs","/usr/local/pictolearn/target/pictolearn-web-1.0.0-SNAPSHOT.jar"]
