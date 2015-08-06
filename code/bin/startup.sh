#!/bin/sh   
#-------------------------------------------------------------------------------------------------------------   
#该脚本的使用方式为-->[sh startup.sh]     
#-------------------------------------------------------------------------------------------------------------   
START_MEM=1024m
MAX_MEM=2048m  
JAVA_OPTS="-Duser.timezone=GMT+8 -server -Xms${START_MEM} -Xmx${MAX_MEM} "   
#APP_HOME=$0  
APP_HOME="$CSV2HBASE_INSTALL_DIR"
#APP_HOME=/dinglicom/csv2hbase  
APP_MAIN=com.dinglicom.hbase.HbaseMainApp  
PARAMETERS=$APP_HOME/config/
CLASSPATH=.:$APP_HOME/config 

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

for jar in "$APP_HOME"/lib/*.jar;  
do  
   CLASSPATH="$CLASSPATH":"$jar"  
done  
  
tradePortalPID=0  
 
getTradeProtalPID(){
    pid=`ps -ef|grep -v grep | grep ${APP_MAIN}|awk '{print $2}' `
    if [ -n "$pid" ]; then     
        tradePortalPID=$pid
    else  
        tradePortalPID=0  
    fi  
} 
 
startup(){  
    getTradeProtalPID  
    echo "================================================================================================================"  
    if [ $tradePortalPID -ne 0 ]; then  
        echo "$APP_MAIN already started(PID=$tradePortalPID)"  
        echo "================================================================================================================"  
 else  
        echo -n "Starting $APP_MAIN"  
       # nohup $JAVA_HOME/bin/java $JAVA_OPTS -classpath $CLASSPATH $APP_MAIN $PARAMETERS  >/dev/null 2>&1  & 
        exec $JAVA_HOME/bin/java $JAVA_OPTS -classpath $CLASSPATH $APP_MAIN $PARAMETERS  >/dev/null 2>&1  & 
        getTradeProtalPID  
        if [ $tradePortalPID -ne 0 ]; then  
            echo "(PID=$tradePortalPID)...[Success]"  
            echo "================================================================================================================" 
        else  
            echo "[Failed]"  
            echo "================================================================================================================"  
        fi  
    fi  
}  
  
startup
