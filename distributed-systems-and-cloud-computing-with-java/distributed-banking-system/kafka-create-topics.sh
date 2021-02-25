#!/bin/bash

docker_kafka() {
    docker run -it --rm --network banking-network -e KAFKA_CFG_ZOOKEEPER_CONNECT=zookeeper:2181 bitnami/kafka:2.7.0 $1
}

docker_kafka "kafka-topics.sh --create --if-not-exists --bootstrap-server kafka-0:9092 --replication-factor 3 --partitions 3 --topic valid-transactions"

docker_kafka "kafka-topics.sh --describe --bootstrap-server kafka-0:9092 --topic valid-transactions"

docker_kafka "kafka-topics.sh --create --if-not-exists --bootstrap-server kafka-0:9092 --replication-factor 3 --partitions 2 --topic suspicious-transactions"

docker_kafka "kafka-topics.sh --describe --bootstrap-server kafka-0:9092 --topic suspicious-transactions"