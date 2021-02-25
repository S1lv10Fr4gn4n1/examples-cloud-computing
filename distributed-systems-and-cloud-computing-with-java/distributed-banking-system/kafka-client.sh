#!/bin/bash

docker run -it --rm --network banking-network -e KAFKA_CFG_ZOOKEEPER_CONNECT=zookeeper:2181 bitnami/kafka:2.7.0 bash

# and create the topic for
# kafka-topics.sh --create --bootstrap-server kafka-0:9092 --replication-factor 1 --partitions 1 --topic chat

# OR create the topic directly
# docker-compose exec kafka-0 kafka-topics.sh --create --bootstrap-server kafka-0:9092 --replication-factor 1 --partitions 1 --topic chat

# Produce msgs to the queue
# kafka-console-producer.sh --broker-list kafka-0:9092 --topic chat

# Consume msgs in the queue
# kafka-console-consumer.sh --bootstrap-server kafka-0:9092 --topic chat --from-beginning