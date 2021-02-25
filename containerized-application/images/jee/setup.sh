#!/bin/sh

# docker pull jboss/wildfly

# docker run --rm -p 8080:8080 -p 9990:9990 -it jboss/wildfly /opt/jboss/wildfly/bin/standalone.sh -b 0.0.0.0 -bmanagement 0.0.0.0

docker run -p 8080:8080 -p 9990:9990 --rm -d --name wildfly do080/wildfly