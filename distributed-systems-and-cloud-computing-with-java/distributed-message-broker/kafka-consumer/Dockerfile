FROM maven:3.6.2-jdk-11-slim

LABEL "Maintainer"="S1lv10Fr4gn4n1"

# ADD a directory inside the image
ADD . /usr/local/kafka-consumer/build

# build project
RUN cd /usr/local/kafka-consumer/build && mvn package

# copy jar to the main folder
RUN cp /usr/local/kafka-consumer/build/target/kafka.consumer-1.0-SNAPSHOT-jar-with-dependencies.jar \
    /usr/local/kafka-consumer/

# delete build folder
RUN rm -rf /usr/local/kafka-consumer/build/

# run service
CMD java -jar /usr/local/kafka-consumer/kafka.consumer-1.0-SNAPSHOT-jar-with-dependencies.jar