FROM photoapp-base-docker

LABEL "Maintainer"="S1lv10Fr4gn4n1"

# copy JCE jars
# COPY security-setup/UnlimitedJCEPolicyJDK8/* ${JAVA_HOME}/jre/lib/security/

# TODO copy certificate
# COPY security-setup/apiEncryptionKey.jks security-setup/apiEncryptionKey.jks

# ADD a directory inside the image
ADD . /usr/local/api-cofiguration-service/build

# build project
RUN cd /usr/local/api-cofiguration-service/build && mvn install

# copy jar to the main folder
RUN cp /usr/local/api-cofiguration-service/build/target/photoapp-api-config-server-0.0.1-SNAPSHOT.jar \
    /usr/local/api-cofiguration-service/

# delete build folder
RUN rm -rf /usr/local/api-cofiguration-service/build/

# run Spring Boot service
CMD java -jar /usr/local/api-cofiguration-service/photoapp-api-config-server-0.0.1-SNAPSHOT.jar