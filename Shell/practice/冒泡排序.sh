#!/bin/bash
# 直接修改原始数组

arr=(9 8 4 5 6 3 1 2 0 7)

function sort {
	len=${#arr[*]}
	for (( i = 0 ; i < ${len} ; i ++ )) ; do
		for (( x = ${i} ; x < ${len} ; x ++ )) ; do
			if [ ${arr[${x}]} -gt ${arr[${i}]} ] ; then
				temp=${arr[${x}]}
				arr[${x}]=${arr[${i}]}
				arr[${i}]=${temp}
			fi
		done
	done
}
echo "排序前:${arr[*]}"
sort
echo "排序后:${arr[*]}"




#!/bin/bash
# 返回新的数组

arr=(0 4 1 5 7 8 2 9 3 6)

function sort {
	local arr=()
	local index=0
	for i in $@ ; do
		arr[${index}]=${i}
		index=$[${index} + 1]
	done
	
	for ((i = 0 ; i < ${index} ; i ++)) ; do
		for (( x = ${i} ; x < ${index} ; x ++ )) ; do
			if [ ${arr[${x}]} -gt ${arr[${i}]} ] ; then
				temp=${arr[${x}]}
				arr[${x}]=${arr[${i}]}
				arr[${i}]=${temp}
			fi
		done
	done
	echo ${arr[*]}
}

sortArr=`sort ${arr[*]}`

echo "原始数组:${arr[*]}"

echo "排序数组:${sortArr}"