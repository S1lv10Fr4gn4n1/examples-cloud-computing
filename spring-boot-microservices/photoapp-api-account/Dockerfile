FROM photoapp-base-docker

LABEL "Maintainer"="S1lv10Fr4gn4n1"

# ADD a directory inside the image
ADD . /usr/local/api-account-service/build

# build project
RUN cd /usr/local/api-account-service/build && mvn install

# copy jar to the main folder
RUN cp /usr/local/api-account-service/build/target/photoapp-api-account-0.0.1-SNAPSHOT.jar \
    /usr/local/api-account-service/

# delete build folder
RUN rm -rf /usr/local/api-account-service/build/

# run Spring Boot service
CMD java -jar /usr/local/api-account-service/photoapp-api-account-0.0.1-SNAPSHOT.jar