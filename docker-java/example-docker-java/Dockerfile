# Install FROM UBUNTU IMAGE
FROM ubuntu:21.04

# Author of the Docker File
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

#RUN update-alternatives --list java
ENV JAVA_HOME="/usr/lib/jvm/java-8-openjdk-amd64/jre"
#RUN java -version

# ADD a directory called docker-git-hello-world inside the UBUNTU IMAGE where you will be moving all
# of these files under this DIRECTORY to
ADD . /usr/local/example-docker-java

# AFTER YOU HAVE MOVED ALL THE FILES GO AHEAD CD into the directory and run mvn assembly.
# Maven assembly will package the project into a JAR FILE which can be executed
RUN cd /usr/local/example-docker-java && mvn assembly:assembly

# THE CMD COMMAND tells docker the command to run when the container is started up from the image.
# In this case we are executing the java program as is to print Hello World.
CMD java -cp \
    /usr/local/example-docker-java/target/example-docker-java-0.0.1-SNAPSHOT-jar-with-dependencies.jar \
    com.sfs.HelloWorldPing