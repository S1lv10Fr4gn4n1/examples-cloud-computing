FROM gradle:6.8.3-jdk8

LABEL "Maintainer"="S1lv10Fr4gn4n1"

# ADD a directory inside the image
ADD . /usr/local/inventory/workspace

# build project
RUN cd /usr/local/inventory/workspace && gradle assemble --no-daemon

# copy jar to the main folder
RUN cp /usr/local/inventory/workspace/build/libs/inventory-service-0.0.1-SNAPSHOT.jar \
    /usr/local/inventory/

# delete build folder
RUN rm -rf /usr/local/inventory/workspace/

# run Spring Boot service
CMD java -jar /usr/local/inventory/inventory-service-0.0.1-SNAPSHOT.jar