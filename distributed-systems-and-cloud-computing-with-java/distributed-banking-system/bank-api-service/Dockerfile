FROM maven:3.6.2-jdk-11-slim

LABEL "Maintainer"="S1lv10Fr4gn4n1"

# ADD a directory inside the image
ADD . /usr/local/banking-api-service/build

# build project
RUN cd /usr/local/banking-api-service/build && mvn package

# copy jar to the main folder
RUN cp /usr/local/banking-api-service/build/target/banking-api-service-1.0-SNAPSHOT-jar-with-dependencies.jar \
    /usr/local/banking-api-service/

# delete build folder
RUN rm -rf /usr/local/banking-api-service/build/

# run service
CMD java -jar /usr/local/banking-api-service/banking-api-service-1.0-SNAPSHOT-jar-with-dependencies.jar