# How to run the Autohealer

### Run Zookeeper service
- `docker run -d --rm -p 2181:2181 --name zookeeper-test -d zookeeper:3.6.2`

### Build the autohealer project
- `cd autohealer && mvn clean package && cd -`

### Build the flakyworker project
- `cd flakyworker && mvn clean package && cd -`


## To run the autohealer, which in turn would launch and maintain 10 workers
Run java -jar target/autohealer-1.0-SNAPSHOT-jar-with-dependencies.jar *<number of workers>* *<path to woker jar>*

Example:
- `java -jar autohealer/target/autohealer-1.0-SNAPSHOT-jar-with-dependencies.jar 10 "flakyworker/target/flaky.worker-1.0-SNAPSHOT-jar-with-dependencies.jar"`
