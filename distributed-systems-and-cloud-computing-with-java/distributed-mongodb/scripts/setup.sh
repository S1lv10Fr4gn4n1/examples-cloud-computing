#!/bin/bash

MONGODB1=mongodb-0
MONGODB2=mongodb-1
MONGODB3=mongodb-2

echo "**********************************************" ${MONGODB1}
echo "Waiting for startup.."
until curl http://${MONGODB1}:28017/serverStatus\?text\=1 2>&1 | grep uptime | head -1; do
  printf '.'
  sleep 1
done

echo curl http://${MONGODB1}:28017/serverStatus\?text\=1 2>&1 | grep uptime | head -1
echo "Started.."

echo SETUP.sh time now: `date +"%T" `
mongo --host ${MONGODB1}:27017 <<EOF
var cfg = {
    "_id": "rs0",
    "version": 1,
    "members": [
        {
            "_id": 0,
            "host": "${MONGODB1}:27017"
        },
        {
            "_id": 1,
            "host": "${MONGODB2}:27017"
        },
        {
            "_id": 2,
            "host": "${MONGODB3}:27017"
        }
    ],settings: {chainingAllowed: true}
};
rs.initiate(cfg, { force: true });
EOF

# it needs to wait a bit to elect the PRIMARY node
sleep 30;

mongo --host ${MONGODB1}:27017 <<EOF
use online-school;
db.createCollection("physics");
db.createCollection("math");
EOF