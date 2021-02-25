FROM maven:3.6.2-jdk-11-slim

LABEL "Maintainer"="S1lv10Fr4gn4n1"

# ADD a directory inside the image
ADD . /usr/local/user-notification-service/build

# build project
RUN cd /usr/local/user-notification-service/build && mvn package

# copy jar to the main folder
RUN cp /usr/local/user-notification-service/build/target/user-notification-service-1.0-SNAPSHOT-jar-with-dependencies.jar \
    /usr/local/user-notification-service/

# delete build folder
RUN rm -rf /usr/local/user-notification-service/build/

# run service
CMD java -jar /usr/local/user-notification-service/user-notification-service-1.0-SNAPSHOT-jar-with-dependencies.jar