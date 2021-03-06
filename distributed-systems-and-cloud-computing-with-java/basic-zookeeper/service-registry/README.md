# How to run the Service Registry

### Run Zookeeper service
- `docker run -d --rm -p 2181:2181 --name zookeeper-test -d zookeeper:3.6.2`

### Build the service registry project
- `mvn clean package`

### Run the project using a few instances of the service registry project
- run it using at least 2 terminals

    `java -jar target/leader-election-1.0-SNAPSHOT-jar-with-dependencies.jar`
- it should display outputs like
    ```
    Successfully connected to Zookeeper
    znode name /election/c_0000000017
    I'm NOT the leader, c_0000000016 is the leader
    Registered to service registry
    Watching znode c_0000000016

    I'm the leader
    The cluster addresses are: []
    The cluster addresses are: [http://<machine-name>:8080]
    The cluster addresses are: [http://<machine-name>:8080, http://<machine-name>:8081]

    ```