FROM photoapp-base-docker

LABEL "Maintainer"="S1lv10Fr4gn4n1"

# ADD a directory inside the image
ADD . /usr/local/api-gateway/build

# build project
RUN cd /usr/local/api-gateway/build && mvn install

# copy jar to the main folder
RUN cp /usr/local/api-gateway/build/target/photoapp-api-gateway-0.0.1-SNAPSHOT.jar \
    /usr/local/api-gateway/

# delete build folder
RUN rm -rf /usr/local/api-gateway/build/

# run Spring Boot service
CMD java -jar /usr/local/api-gateway/photoapp-api-gateway-0.0.1-SNAPSHOT.jar