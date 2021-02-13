#!/bin/sh

if [ -z $agent_div ]; then
    echo "agent_div is missing."
    exit 1
fi

CONTAINER_NAME=comm-datapf-api-converting-$agent_div
ALIVE=`docker ps --format '{{.Names}}' | grep $CONTAINER_NAME | wc -l`

if [ $ALIVE -eq 1 ]; then
    echo stop $CONTAINER_NAME ...
    docker stop $CONTAINER_NAME
else
    echo $CONTAINER_NAME is already stopped.
fi


