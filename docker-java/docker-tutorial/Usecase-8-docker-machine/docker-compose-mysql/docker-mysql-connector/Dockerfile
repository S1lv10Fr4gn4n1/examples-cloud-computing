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

# RUN java -version
# RUN update-alternatives --list java
ENV JAVA_HOME="/usr/lib/jvm/java-8-openjdk-amd64/jre"

ADD . /usr/local/docker-mysql-connector
RUN cd /usr/local/docker-mysql-connector && mvn assembly:assembly
CMD ["java", "-cp", "/usr/local/docker-mysql-connector/target/docker-mysql-connector-1.0.0-SNAPSHOT-jar-with-dependencies.jar", "org.pictolearn.docker.mysql.MySQLConnection"]