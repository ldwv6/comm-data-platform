FROM 121.254.135.234:5000/comm-data-platform/base/comm-data-platform-bootapp-base-jdk11:latest
MAINTAINER jhkwon19@interpark.com

ARG bootapp=
ARG relno=
ARG profile=prod
ARG serverport=
ARG scouter_ip=180.70.98.88

ENV BOOTAPP=$bootapp
ENV PROFILE=$profile
ENV RELEASE_NO=$relno
ENV SERVERPORT=$serverport
ENV SCOUTER_SERVER=$scouter_ip

COPY ./$bootapp /webapp/$bootapp


