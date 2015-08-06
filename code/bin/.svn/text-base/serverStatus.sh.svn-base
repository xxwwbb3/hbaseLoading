#!/bin/sh   
APP_MAIN=com.dinglicom.hbase.HbaseMainApp  
  
# find java
if [ -z "${JAVA_HOME}" ] ; then
  echo "JAVA_HOME is not set!"
  # Try to use Bigtop to autodetect JAVA_HOME if it's available
  if [ -e /usr/libexec/bigtop-detect-javahome ] ; then
    . /usr/libexec/bigtop-detect-javahome
  elif [ -e /usr/lib/bigtop-utils/bigtop-detect-javahome ] ; then
    . /usr/lib/bigtop-utils/bigtop-detect-javahome
  fi

  # Using java from path if bigtop is not installed or couldn't find it
  if [ -z "${JAVA_HOME}" ] ; then
    JAVA_DEFAULT=$(type -p java)
    [ -n "$JAVA_DEFAULT" ] || error "Unable to find java executable. Is it in your PATH?" 1
    JAVA_HOME=$(cd $(dirname $JAVA_DEFAULT)/..; pwd)
  fi
fi

tradePortalPID=0  
  
getTradeProtalPID(){  
    pid=`ps -ef|grep -v grep | grep ${APP_MAIN}|awk '{print $2}' `
    if [ -n "$pid" ]; then  
        tradePortalPID=$pid  
    else  
        tradePortalPID=0  
    fi  
}  
  
getServerStatus(){  
    getTradeProtalPID  
    echo "================================================================================================================"  
    if [ $tradePortalPID -ne 0 ]; then  
        echo "$APP_MAIN is running(PID=$tradePortalPID)"  
        echo "================================================================================================================"  
    else  
        echo "$APP_MAIN is not running"  
        echo "================================================================================================================"  
    fi  
}  
  
getServerStatus  
