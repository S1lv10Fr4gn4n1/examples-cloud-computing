# How to run the Leader Election

### Run Zookeeper service
- `docker run -d --rm -p 2181:2181 --name zookeeper-test -d zookeeper:3.6.2`

### Build the leader election project
- `mvn clean package`

### Run the project using a few instances of the leader election project
- run it using at least 2 terminals

    `java -jar target/leader-election-1.0-SNAPSHOT-jar-with-dependencies.jar`
- it should display outputs like
    ```
    /election namespace already exists
    znode name /election/c_0000000013

    I'm NOT the leader, c_0000000012 is the leader
    Watching znode c_0000000012

    I'm the leader
    ```