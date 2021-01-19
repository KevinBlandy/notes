#!/bin/sh

## java 环境
export JAVA_HOME=/usr/local/java/jdk1.7.0_72
export JRE_HOME=$JAVA_HOME/jre

## 服务名称
APP_NAME=user

SERVICE_DIR=/home/wusc/edu/service/$APP_NAME

SERVICE_NAME=edu-service-$APP_NAME
JAR_NAME=$SERVICE_NAME\.jar
PID=$SERVICE_NAME\.pid

cd $SERVICE_DIR

case "$1" in

    start)
        nohup $JRE_HOME/bin/java -Xms256m -Xmx512m -jar $JAR_NAME >/dev/null 2>&1 &
        echo $! > $SERVICE_DIR/$PID
        echo "=== start $SERVICE_NAME"
        ;;

    stop)
        kill `cat $SERVICE_DIR/$PID`
        rm -rf $SERVICE_DIR/$PID
        echo "=== stop $SERVICE_NAME"

        sleep 5
		##
		## edu-service-aa.jar
		## edu-service-aa-bb.jar
        P_ID=`ps -ef | grep -w "$SERVICE_NAME" | grep -v "grep" | awk '{print $2}'`
        if [ "$P_ID" == "" ]; then
            echo "=== $SERVICE_NAME process not exists or stop success"
        else
            echo "=== $SERVICE_NAME process pid is:$P_ID"
            echo "=== begin kill $SERVICE_NAME process, pid is:$P_ID"
            kill -9 $P_ID
        fi
        ;;

    restart)
        $0 stop
        sleep 2
        $0 start
        echo "=== restart $SERVICE_NAME"
        ;;

    *)
        ## restart
        $0 stop
        sleep 2
        $0 start
        ;;

esac
exit 0

