#!/bin/bash
# ============================================= 2019年1月22日 13:56:43
IFS_OLD=$IFS
IFS=:
# 遍历path环境变量
for folder in ${PATH} ; do
	echo "${folder}"
	# 遍历每一项目录下的文件
	for file in ${folder}/* ; do
		# 如果是可执行文件则打印
		if [ -x $file ] ; then
			echo "	$file"
		fi
	done
done
IFS=$IFS_OLD