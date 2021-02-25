#!/bin/sh

# SET CONTAINER_ID to create a unique id to attach to the log name
export CONTAINER_ID=$(head -1 /proc/self/cgroup|cut -d/ -f3)

# Hand off to the CMD
exec "$@"