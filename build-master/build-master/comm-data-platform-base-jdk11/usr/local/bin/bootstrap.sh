#!/bin/sh

# default Locale and Timezone
LANG=${LANG:-ko_KR.UTF-8}
TZ=${TZ:-Asia/Seoul}
JAVA_HOME=${JAVA_HOME:-/opt/oracle/java}
export LANG TZ JAVA_HOME

if [ -z "$CHARSET" ]; then
  CHARSET=`echo $LANG | sed -e 's/^.+\.//'`
  if [ -z "${CHARSET}" ]; then
    CHARSET=UTF-8
  elif locale -m $CHARSET 2>/dev/null | fgrep -vq $CHARSET; then
    CHARSET=UTF-8
  fi
fi

# Directories
APPROOT=/webapp
LOGDIR=/webapp/logs
TMPDIR=/tmp
export TMPDIR

# Application
BOOTAPP=${BOOTAPP:-}
SERVERPORT=${SERVERPORT:-}
PROFILE=${PROFILE:-prod}
CUSTOMAPPNAME=${CUSTOMAPPNAME:-}

# Java Options
JAVA_BIN=${JAVA_HOME}/bin/java
#JAVA_BIN="/bin/echo -- ${JAVA_HOME}/bin/java"
JVM_OPT=${JVM_OPT:-"-Xms512m -Xmx2048m"}
JAVA_OPT="-Dfile.encoding=${CHARSET} -Djava.io.tmpdir=${TMPDIR} -Djava.security.egd=file:/dev/./urandom"
if [ -n "$LANG" ]; then
  JAVA_OPT="$JAVA_OPT `echo $LANG | cut -d. -f1 | sed -e 's/\(..\)_\(..\)/-Duser.language=\1 -Duser.region=\2/'`"
fi

# scouter config
SCOUTER_HOME=/usr/local/scouter

SCOUTER_OBJECT_NAME=${SCOUTER_OBJECT_NAME:-`hostname`}
SCOUTER_TCP_PORT=${SCOUTER_TCP_PORT:-6100}
SCOUTER_UDP_PORT=${SCOUTER_UDP_PORT:-6100}
SCOUTER_HOOK_PATTERNS=${SCOUTER_HOOK_PATTERNS}
SCOUTER_HOOK_EXCLUDE_PATTERNS=${SCOUTER_HOOK_EXCLUDE_PATTERNS}
SCOUTER_ACCESS_PUBLIC_ENABLED=${SCOUTER_ACCESS_PUBLIC_ENABLED:-true}
SCOUTER_ACCESS_PRIVATE_ENABLED=${SCOUTER_ACCESS_PRIVATE_ENABLED:-false}
SCOUTER_ACCESS_PROTECTED_ENABLED=${SCOUTER_ACCESS_PROTECTED_ENABLED:-flase}
SCOUTER_ACCESS_NONE_ENABLED=${SCOUTER_ACCESS_NONE_ENABLED:-false}
PROFILE_CONNECTION_OPEN_ENABLED=${PROFILE_CONNECTION_OPEN_ENABLED:-true}
TRACE_INTERSERVICE_ENABLED=${TRACE_INTERSERVICE_ENABLED:-true}
_TRACE_AUTO_SERVICE_ENABLED=${_TRACE_AUTO_SERVICE_ENABLED:-false}
HOOK_SERVICE_PATTERNS=${HOOK_SERVICE_PATTERNS}


SCOUTER_OPT=
if [ -n "$SCOUTER_SERVER" -a "$USE_SCOUTER" == "true" ]; then
  sed \
    -e "s/\${SCOUTER_SERVER}/${SCOUTER_SERVER}/g" \
    -e "s/\${SCOUTER_TCP_PORT}/${SCOUTER_TCP_PORT}/g" \
    -e "s/\${SCOUTER_UDP_PORT}/${SCOUTER_UDP_PORT}/g" \
    -e "s/\${SCOUTER_OBJECT_NAME}/${SCOUTER_OBJECT_NAME}/g" \
    -e "s!\${LOGDIR}!${LOGDIR}!g" \
    -e "s/\${SCOUTER_HOOK_PATTERNS}/${SCOUTER_HOOK_PATTERNS}/g" \
    -e "s/\${SCOUTER_HOOK_EXCLUDE_PATTERNS}/${SCOUTER_HOOK_EXCLUDE_PATTERNS}/g" \
    -e "s/\${SCOUTER_ACCESS_PUBLIC_ENABLED}/${SCOUTER_ACCESS_PUBLIC_ENABLED}/g" \
    -e "s/\${SCOUTER_ACCESS_PRIVATE_ENABLED}/${SCOUTER_ACCESS_PRIVATE_ENABLED}/g" \
    -e "s/\${SCOUTER_ACCESS_PROTECTED_ENABLED}/${SCOUTER_ACCESS_PROTECTED_ENABLED}/g" \
    -e "s/\${SCOUTER_ACCESS_NONE_ENABLED}/${SCOUTER_ACCESS_NONE_ENABLED}/g" \
    -e "s/\${PROFILE_CONNECTION_OPEN_ENABLED}/${PROFILE_CONNECTION_OPEN_ENABLED}/g" \
    -e "s/\${TRACE_INTERSERVICE_ENABLED}/${TRACE_INTERSERVICE_ENABLED}/g" \
    -e "s/\${_TRACE_AUTO_SERVICE_ENABLED}/${_TRACE_AUTO_SERVICE_ENABLED}/g" \
    -e "s/\${HOOK_SERVICE_PATTERNS}/${HOOK_SERVICE_PATTERNS}/g" \
    $SCOUTER_HOME/agent.java/conf/scouter.conf \
      >$APPROOT/config/scouter.conf 

    SCOUTER_OPT="-javaagent:${SCOUTER_HOME}/agent.java/scouter.agent.jar -Dscouter.config=${APPROOT}/config/scouter.conf -Dobj_name=${SCOUTER_OBJECT_NAME}"
fi

# SpringBoot options : SERVERPORT
if [ $SERVERPORT ]
then
  SPRINGBOOT_OPT="$SPRINGBOOT_OPT --server.port=$SERVERPORT --spring.profiles.active=$PROFILE --relno=${RELEASE_NO}"
else
  SPRINGBOOT_OPT="$SPRINGBOOT_OPT --spring.profiles.active=$PROFILE --relno=${RELEASE_NO}"
fi

# SpringBoot options : CUSTOMAPPNAME
if [ $CUSTOMAPPNAME ]
then
  SPRINGBOOT_OPT="$SPRINGBOOT_OPT --custom.appname=$CUSTOMAPPNAME"
fi


CMD=$1; shift
case $CMD in
  start)
    if [ -n "$BOOTAPP" ]; then
      echo exec $JAVA_BIN $JVM_OPT $JAVA_OPT $SCOUTER_OPT -jar /webapp/$BOOTAPP $SPRINGBOOT_OPT "$@"
      exec $JAVA_BIN $JVM_OPT $JAVA_OPT $SCOUTER_OPT -jar /webapp/$BOOTAPP $SPRINGBOOT_OPT "$@"
    else
      echo "\$BOOTAPP is not specified. Add ENV parameter. (eg: -e BOOTAPP=myapp.jar)"
      exit 1
    fi
    ;;

  selftest)
    #if [ -f $APPROOT/config/selftest.yaml ]; then
    #  exec $APPROOT/bin/selftest.sh $APPROOT/config/selftest.yaml
    #else
    #  echo "File not found: $APPROOT/config/selftest.yaml" >&2
    #fi
    ;;

  relno)
    echo $RELEASE_NO
    ;;

  sh|bash|/bin/sh|/bin/bash|/usr/bin/bash)
    /bin/bash "$@"
    ;;

  *)
    echo usage: "$0 { start [ args ... ] | selftest }"
    ;;

esac
