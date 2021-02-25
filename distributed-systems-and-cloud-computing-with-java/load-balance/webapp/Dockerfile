FROM maven:3.6.3-openjdk-11-slim

LABEL "Maintainer"="S1lv10Fr4gn4n1"

# ADD a directory inside the image
ADD . /usr/local/webapp/build

# build project
RUN cd /usr/local/webapp/build && mvn install

# copy jar to the main folder
RUN cp /usr/local/webapp/build/target/webapp-1.0-SNAPSHOT-jar-with-dependencies.jar \
    /usr/local/webapp/

# delete build folder
RUN rm -rf /usr/local/webapp/build/

ENTRYPOINT ["java","-jar", "/usr/local/webapp/webapp-1.0-SNAPSHOT-jar-with-dependencies.jar"]
CMD ["80", "Server Name"]