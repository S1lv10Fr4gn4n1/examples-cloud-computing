# install FROM UBUNTU IMAGE
FROM photoapp-base-docker

# author of the Docker File
LABEL "Maintainer"="S1lv10Fr4gn4n1"

# ADD a directory inside the image
ADD . /usr/local/discovery-service/build

# build project
RUN cd /usr/local/discovery-service/build && mvn install

# copy jar to the main folder
RUN cp /usr/local/discovery-service/build/target/photoapp-discovery-service-0.0.1-SNAPSHOT.jar \
    /usr/local/discovery-service/

# delete build folder
RUN rm -rf /usr/local/discovery-service/build/

# run Spring Boot service
CMD java -jar /usr/local/discovery-service/photoapp-discovery-service-0.0.1-SNAPSHOT.jar