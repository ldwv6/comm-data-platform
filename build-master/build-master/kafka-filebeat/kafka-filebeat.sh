#!/bin/sh

if [ -z $PROFILE ]; then
    echo "PROFILE is missing."
    exit 1
fi

if [ -z $USE_SCOUTER ]; then
    echo "USE_SCOUTER is missing."
    exit 1
fi

if [ -z $QUEUE_DIR ]; then
    echo "QUEUE_DIR is missing."
    exit 1
fi

if [ -z $IGNORE_DAYS_AFTER ]; then
    echo "IGNORE_DAYS_AFTER is missing."
    exit 1
fi

if [ -z $SCAN_TERM_SECS ]; then
    echo "SCAN_TERM_SECS is missing."
    exit 1
fi

if [ -z $LINE_BATCH_SIZE ]; then
    echo "LINE_BATCH_SIZE is missing."
    exit 1
fi

if [ -z $DIR_SCAN_COUNT ]; then
    echo "DIR_SCAN_COUNT is missing."
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
IMAGE_NAME=comm-data-platform/$PROFILE/kafka-filebeat
CONTAINER_NAME=kafka-filebeat

if [ -z "$JVM_OPTS" ]; then
    JVM_OPT="
    $JVM_OPT 
    -Xms6g 
    -Xmx6g 
    -XX:+UseG1GC 
    -XX:MaxGCPauseMillis=20 
    -Xloggc:gc/gc.log 
    -verbose:gc 
    -XX:+PrintGCDetails 
    -XX:+PrintGCDateStamps 
    -XX:+PrintGCTimeStamps 
    -XX:+UseGCLogFileRotation 
    -XX:NumberOfGCLogFiles=5 
    -XX:GCLogFileSize=128K 
    -XX:InitiatingHeapOccupancyPercent=35 
    -XX:G1HeapRegionSize=16M 
    -XX:MinMetaspaceFreeRatio=50 
    -XX:MaxMetaspaceFreeRatio=80 
    -Djava.awt.headless=true
    "
fi

if [ -z "$SPRINGBOOT_OPT" ]; then
    SPRINGBOOT_OPT="
    $SPRINGBOOT_OPT 
    --bootstrap-server $BOOTSTRAP_SERVER 
    --queue-dir $QUEUE_DIR 
    --scan-term-secs $SCAN_TERM_SECS 
    --line-batch-size $LINE_BATCH_SIZE 
    --dir-scan-count $DIR_SCAN_COUNT 
    --ignore-days-after $IGNORE_DAYS_AFTER 
    --print-offset 
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
  -e KAFKA_FILEBEAT_HOME=/webapp \
  -e JVM_OPT="$JVM_OPT" \
  -e SPRINGBOOT_OPT="$SPRINGBOOT_OPT" \
  -e USE_SCOUTER="$USE_SCOUTER" \
  -e SCOUTER_HOOK_PATTERNS="$SCOUTER_HOOK_PATTERNS" \
  -e SCOUTER_HOOK_EXCLUDE_PATTERNS="$SCOUTER_HOOK_EXCLUDE_PATTERNS" \
  -e _TRACE_AUTO_SERVICE_ENABLED=true \
  -e HOOK_SERVICE_PATTERNS= \
  -v /usr/local/kafka-filebeat/config:/webapp/config \
  -v /usr/local/kafka-filebeat/logs:/webapp/logs \
  -v /usr/local/kafka-filebeat/gc:/webapp/gc \
  -v /disk1:/disk1 \
  -v /disk2:/disk2 \
  -v /disk3:/disk3 \
  $REPO/$IMAGE_NAME:$TAG $SHELL_OPT

sleep 3
echo ps $CONTAINER_NAME
docker ps | grep $CONTAINER_NAME

