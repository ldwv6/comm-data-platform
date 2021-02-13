#!/bin/sh

if [ -z $agent_div ]; then
    echo "agent_div is missing."
    exit 1
fi

CONTAINER_NAME=comm-datapf-api-converting-$agent_div
echo start $CONTAINER_NAME ....
docker start $CONTAINER_NAME

