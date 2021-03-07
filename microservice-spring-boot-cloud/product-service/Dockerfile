FROM gradle:6.8.3-jdk8

LABEL "Maintainer"="S1lv10Fr4gn4n1"

# ADD a directory inside the image
ADD . /usr/local/product/workspace

# build project
RUN cd /usr/local/product/workspace && gradle assemble --no-daemon

# copy jar to the main folder
RUN cp /usr/local/product/workspace/build/libs/product-service-0.0.1-SNAPSHOT.jar \
    /usr/local/product/

# delete build folder
RUN rm -rf /usr/local/product/workspace/

# run Spring Boot service
CMD java -jar /usr/local/product/product-service-0.0.1-SNAPSHOT.jar