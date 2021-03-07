# Photo app microservices

### Goal
This project is to demostrate how to create a small app with Spring boot/cloud using microservices architecture.

### Services available
- photoapp-discovery-service
- photoapp-api-config-server
- photoapp-gateway
- photoapp-api-account
- photoapp-api-albums
- photoapp-api-users
- photoapp-rabbitmq
- photoapp-elk
- photoapp-artifactory

### Exposed services
- gateway
    - http://localhost:8082/actuator
- configuration
    - http://localhost:8012/<service-name>/default
- eureka
    - http://localhost:8010/
    - user: test
    - pass: test
- rabbit
    - http://localhost:15672/
    - guest -> guest
- zipkin
    - http://localhost:9411/zipkin/
- adminer (web sql client)
    - http://localhost:8088/
    - server: photo-database
    - user: users-ws
    - pass: test123
- kibana
    - http://localhost:5601/
- elasticsearch
    - http://localhost:9200/

### Building
- Run `sh build.sh`
    - it will build the base image, all the images needed and pull others from docker hub
- Configuration service must be change to use spring.profiles.active or your own github
- Run `docker-compose up -d` to initialize all the services


### How to consume
- Please import the [Postman collection](others/Springboot-microservices.postman_collection.json)
- Create new user, if it doesn't exist
- Login to create authentication token
- Execute queues `Users: get user and albums`