FROM maven:3.6.2-jdk-11-slim

LABEL "Maintainer"="S1lv10Fr4gn4n1"

# ADD a directory inside the image
ADD . /usr/local/kafka-producer/build

# build project
RUN cd /usr/local/kafka-producer/build && mvn package

# copy jar to the main folder
RUN cp /usr/local/kafka-producer/build/target/kafka.producer-1.0-SNAPSHOT-jar-with-dependencies.jar \
    /usr/local/kafka-producer/

# delete build folder
RUN rm -rf /usr/local/kafka-producer/build/

# run service
CMD java -jar /usr/local/kafka-producer/kafka.producer-1.0-SNAPSHOT-jar-with-dependencies.jar