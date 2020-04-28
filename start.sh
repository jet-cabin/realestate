#!/bin/bash
echo "al"

for d in $(find ./ -type d -name target);do
 #echo $d
 dir=$(dirname $d)
 if [[ $dir =~ security || $dir =~ common ]];then
   echo $dir"***"
   continue
 fi
 cd $d
 echo `pwd`
 file=`ls|egrep .jar$`
 echo -e $file"\n"
 nohup java -jar $file > $file".log" &
 echo $?
 cd ../../
done
