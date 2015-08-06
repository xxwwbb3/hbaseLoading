#!/bin/bash
# Once every 2 minutes
hostname=`hostname`

interval_time=120

pathDir=/data02/datain
#pathDir=$1
resultName=$(date +%Y%m%d)_result.txt
oldFileName=.old.txt
nowFileName=.now.txt
diffFileName=.diff.txt

if [ ! -f $nowFileName ]; then 
	ls -lrtk ${pathDir} | awk '{print $9" "$5}' > $nowFileName
        exit 0;
fi 
mv $nowFileName $oldFileName

ls -lrtk ${pathDir} | awk '{print $9" "$5}' > $nowFileName
diff -B $oldFileName $nowFileName > $diffFileName

out_sum=0
in_sum=0

while read line
do
        pre=`echo $line|awk '{print $1}'`
        val=`echo $line|awk '{print $3}'`

	case $pre in
	 "<")
	 let out_sum=${out_sum}+${val}
	 ;;       
	 ">")
	 let in_sum=${in_sum}+${val}
	 ;;
	esac   

done < $diffFileName

#echo "out_sum: " $out_sum "K"
#echo "in_sum: " $in_sum "K"
out_avg=`echo "scale=2; $out_sum/$interval_time" | bc`
in_avg=`echo "scale=2; $in_sum/$interval_time" | bc`

echo "`date "+%Y-%m-%d %H:%M:%S"` $hostname  avg(K/sec) out:" $out_avg "in:" $in_avg >> $resultName

