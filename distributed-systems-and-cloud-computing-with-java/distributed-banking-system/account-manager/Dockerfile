FROM maven:3.6.2-jdk-11-slim

LABEL "Maintainer"="S1lv10Fr4gn4n1"

# ADD a directory inside the image
ADD . /usr/local/account-manager/build

# build project
RUN cd /usr/local/account-manager/build && mvn package

# copy jar to the main folder
RUN cp /usr/local/account-manager/build/target/account-manager-1.0-SNAPSHOT-jar-with-dependencies.jar \
    /usr/local/account-manager/

# delete build folder
RUN rm -rf /usr/local/account-manager/build/

# run service
CMD java -jar /usr/local/account-manager/account-manager-1.0-SNAPSHOT-jar-with-dependencies.jar