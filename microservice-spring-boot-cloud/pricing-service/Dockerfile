FROM gradle:6.8.3-jdk8

LABEL "Maintainer"="S1lv10Fr4gn4n1"

# ADD a directory inside the image
ADD . /usr/local/pricing/workspace

# build project
RUN cd /usr/local/pricing/workspace && gradle assemble --no-daemon

# copy jar to the main folder
RUN cp /usr/local/pricing/workspace/build/libs/pricing-service-0.0.1-SNAPSHOT.jar \
    /usr/local/pricing/

# delete build folder
RUN rm -rf /usr/local/pricing/workspace/

# run Spring Boot service
CMD java -jar /usr/local/pricing/pricing-service-0.0.1-SNAPSHOT.jar