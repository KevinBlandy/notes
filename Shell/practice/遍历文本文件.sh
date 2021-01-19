#!/bin/bash
# ============================================= 2019年1月22日 13:56:43
IFS_OLD=$IFS

IFS=$'\n'	# 换行分割

for entry in `cat /etc/passwd`; do
	echo "values in :${entry}"
	IFS=:	# 冒火分割
	for value in $entry ; do
		echo "${value}	"
	done
done

# 还原原始的分割符
IFS=$IFS_OLD


--------------------------------
使用重定向
--------------------------------
file=/root/app.log
while read line; do
	echo "${line}"
done < ${file}



--------------------------------
使用cat管道
--------------------------------
file=/root/springboot.py
line_number=1
cat ${file} | while read line; do
	echo "$((line_number++)) ${line}"
done