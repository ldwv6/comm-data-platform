#!/bin/sh

if [ -z $PROFILE ]; then
    echo "PROFILE is missing."
    exit 1
fi

if [ -z $USE_SCOUTER ]; then
    echo "USE_SCOUTER is missing."
    exit 1
fi


TAG=$1

if [ -z $TAG ]; then
    echo "TAG is missing."
    exit 1
fi

if [ "$2" == "DOCKER_DEBUG" ]; then
    TERM_OPT="-i -t"
    SHELL_OPT="/bin/bash"
fi

REPO=121.254.135.234:5000
IMAGE_NAME=comm-data-platform/$PROFILE/comm-datapf-web-admin
CONTAINER_NAME=comm-datapf-web-admin

if [ -z "$JVM_OPTS" ]; then
    JVM_OPT="
    $JVM_OPT 
    -Xms6g 
    -Xmx6g 
    -Xlog:gc:gc/gc.log
    -verbose:gc
    -Djava.awt.headless=true
    "
fi

if [ -z "$SPRINGBOOT_OPT" ]; then
    SPRINGBOOT_OPT="
    $SPRINGBOOT_OPT 
    "
fi

ALIVE=`docker ps --format '{{.Names}}' | grep $CONTAINER_NAME | wc -l`

if [ $ALIVE -eq 1 ]; then
    echo stop $CONTAINER_NAME ...
    docker stop $CONTAINER_NAME
fi

sleep 3

EXIST_CONTAINER=`docker ps -a --format '{{.Names}}' | grep $CONTAINER_NAME | wc -l`

if [ $EXIST_CONTAINER -eq 1 ]; then
    echo rm $CONTAINER_NAME ...
    docker rm $CONTAINER_NAME
fi

docker pull $REPO/$IMAGE_NAME:$TAG

echo start $CONTAINER_NAME ....

docker run -d $TERM_OPT --name $CONTAINER_NAME \
  --hostname $CONTAINER_NAME \
  --log-opt max-size=256m \
  -e TZ=Asia/Seoul \
  -e LANG=ko_KR.UTF-8 \
  -e HOSTSERVER_NAME=$hostname \
  -e PROFILE=$PROFILE \
  -e JVM_OPT="$JVM_OPT" \
  -e SPRINGBOOT_OPT="$SPRINGBOOT_OPT" \
  -e USE_SCOUTER="$USE_SCOUTER" \
  -e SCOUTER_HOOK_PATTERNS="$SCOUTER_HOOK_PATTERNS" \
  -e SCOUTER_HOOK_EXCLUDE_PATTERNS="$SCOUTER_HOOK_EXCLUDE_PATTERNS" \
  -e _TRACE_AUTO_SERVICE_ENABLED=true \
  -e HOOK_SERVICE_PATTERNS= \
  -v /var/log/comm-datapf:/var/log/comm-datapf \
  -v /usr/local/comm-datapf-web-admin/gc:/webapp/gc \
  -p 9400:9400/tcp \
  $REPO/$IMAGE_NAME:$TAG $SHELL_OPT

sleep 3
echo ps $CONTAINER_NAME
docker ps | grep $CONTAINER_NAME

