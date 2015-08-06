#!/bin/bash
name=`hostname`
path="/hadoop/forHbase"
size=$(du -s -m $path |awk '{print $1}')
maxSize=1
if [ $size -le $maxSize ]; then
	echo "$name : ${size}M Right";
	exit;
fi


APP_MAIN=com.dinglicom.hbase.HbaseMainApp  
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
    if [ $tradePortalPID -ne 0 ]; then  
	echo "$name : ${size}M Right";
    else  
	echo "$name : ${size}M Wrong!";
    fi  
}
getServerStatus  
#echo "check the log file!"

logfile="/cache/hbaseLoad/log/csv2hbase.log"
update=`stat -c %Y $logfile`
#echo "update  time: $update"
now=`date +%s`
#echo "current time: $now"
interval=$[ $now - $update ];
threshold=600
if [ "$interval" -lt "${threshold}" ];then 
	echo "$name : ${size}M Right";
else 
	echo "$name : ${size}M Wrong!";
fi
