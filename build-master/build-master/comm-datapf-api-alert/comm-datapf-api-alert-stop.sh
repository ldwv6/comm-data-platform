#!/bin/sh

CONTAINER_NAME=comm-datapf-api-alert
ALIVE=`docker ps --format '{{.Names}}' | grep $CONTAINER_NAME | wc -l`

if [ $ALIVE -eq 1 ]; then
    echo stop $CONTAINER_NAME ...
    docker stop $CONTAINER_NAME
else
    echo $CONTAINER_NAME is already stopped.
fi


