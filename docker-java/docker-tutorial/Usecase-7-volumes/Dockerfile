# This file is used to create a docker image.
FROM tomcat:jdk8-corretto

LABEL "Maintainer"="S1lv10Fr4gn4n1"

EXPOSE 8080

# Need a war file from the host to be mounted you can do so here. This instruction tells docker that content in those directories does not go in images and
# can be accessed from other containers using the --volumes-from command line parameter. You have to run the container using -v
#/path/on/host:/path/in/container to access directories from the host.
VOLUME "/usr/local/tomcat/webapps"
VOLUME "/usr/local/tomcat/logs"


COPY docker-spring-mvc.war $CATALINA_HOME/webapps/docker-spring-mvc.war

#The WORKDIR instruction sets the working directory for any RUN, CMD, ENTRYPOINT, COPY and ADD instructions that follow it in the Dockerfile.
WORKDIR $CATALINA_HOME