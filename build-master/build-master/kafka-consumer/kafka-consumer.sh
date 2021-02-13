#!/bin/sh


if [ -z $DOCKER_PROFILE ]; then
    echo "docker profile is missing."
    exit 1
fi

IMAGE_NAME=comm-data-platform/$DOCKER_PROFILE/comm-datapf-api-kafkaconsumer
CONTAINER_NAME=comm-datapf-api-kafkaconsumer
TAG=$1

if [ -z $TAG ]; then
    echo "tag is missing."
    exit 1
fi

if [ -z $PROFILE ]; then
    echo "profile is missing."
    exit 1
fi

if [ -z $SPRING_PROFILES_ACTIVE ]; then
    echo "spring.profiles.active is missing."
    exit 1
fi

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
    -Dspring.profiles.active=$SPRING_PROFILES_ACTIVE
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

echo start $CONTAINER_NAME ....

docker run -d --name $CONTAINER_NAME \
  --hostname $CONTAINER_NAME \
  --log-opt max-size=256m \
  -e TZ=Asia/Seoul \
  -e LANG=ko_KR.UTF-8 \
  -e HOSTSERVER_NAME=$hostname \
  -e PROFILE=$PROFILE \
  -e KAFKA_CONSUMER_HOME=/webapp \
  -e JVM_OPT="$JVM_OPT" \
  -e SPRINGBOOT_OPT="$SPRINGBOOT_OPT" \
  -v /usr/local/kafka-consumer/config:/webapp/config \
  -v /usr/local/kafka-consumer/logs:/webapp/logs \
  -v /usr/local/kafka-consumer/gc:/webapp/gc \
  -v /disk1:/disk1 \
  -v /disk2:/disk2 \
  -v /disk3:/disk3 \
  $IMAGE_NAME:$TAG 

sleep 3
echo ps $CONTAINER_NAME
docker ps | grep $CONTAINER_NAME

