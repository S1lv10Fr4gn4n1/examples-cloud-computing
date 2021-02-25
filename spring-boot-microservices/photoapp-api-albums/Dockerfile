FROM photoapp-base-docker

LABEL "Maintainer"="S1lv10Fr4gn4n1"

# ADD a directory inside the image
ADD . /usr/local/api-albums/build

# build project
RUN cd /usr/local/api-albums/build && mvn install

# copy jar to the main folder
RUN cp /usr/local/api-albums/build/target/photoapp-api-albums-0.0.1-SNAPSHOT.jar \
    /usr/local/api-albums/

# delete build folder
RUN rm -rf /usr/local/api-albums/build/

# run Spring Boot service
CMD java -jar /usr/local/api-albums/photoapp-api-albums-0.0.1-SNAPSHOT.jar