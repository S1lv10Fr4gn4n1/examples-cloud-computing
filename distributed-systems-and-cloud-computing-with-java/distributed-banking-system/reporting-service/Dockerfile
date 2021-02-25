FROM maven:3.6.2-jdk-11-slim

LABEL "Maintainer"="S1lv10Fr4gn4n1"

# ADD a directory inside the image
ADD . /usr/local/reporting-service/build

# build project
RUN cd /usr/local/reporting-service/build && mvn package

# copy jar to the main folder
RUN cp /usr/local/reporting-service/build/target/reporting-service-1.0-SNAPSHOT-jar-with-dependencies.jar \
    /usr/local/reporting-service/

# delete build folder
RUN rm -rf /usr/local/reporting-service/build/

# run service
CMD java -jar /usr/local/reporting-service/reporting-service-1.0-SNAPSHOT-jar-with-dependencies.jar