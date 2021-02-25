FROM photoapp-base-docker

LABEL "Maintainer"="S1lv10Fr4gn4n1"

# ADD a directory inside the image
ADD . /usr/local/api-users-service/build

# build project
RUN cd /usr/local/api-users-service/build && mvn install

# copy jar to the main folder
RUN cp /usr/local/api-users-service/build/target/photoapp-api-users-0.0.1-SNAPSHOT.jar \
    /usr/local/api-users-service/

# delete build folder
RUN rm -rf /usr/local/api-users-service/build/

# run Spring Boot service
CMD java -jar /usr/local/api-users-service/photoapp-api-users-0.0.1-SNAPSHOT.jar