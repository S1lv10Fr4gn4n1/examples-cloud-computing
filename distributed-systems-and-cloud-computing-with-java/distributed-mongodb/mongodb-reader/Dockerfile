FROM maven:3.6.2-jdk-11-slim

ARG STUDENT_PARAMS

LABEL "Maintainer"="S1lv10Fr4gn4n1"

# ADD a directory inside the image
ADD . /usr/local/mongodb-reader/build

# build project
RUN cd /usr/local/mongodb-reader/build && mvn package

# copy jar to the main folder
RUN cp /usr/local/mongodb-reader/build/target/mongodb-reader-1.0-SNAPSHOT-jar-with-dependencies.jar \
    /usr/local/mongodb-reader/

# delete build folder
RUN rm -rf /usr/local/mongodb-reader/build/

# run service
CMD java -jar /usr/local/mongodb-reader/mongodb-reader-1.0-SNAPSHOT-jar-with-dependencies.jar ${STUDENT_PARAMS}