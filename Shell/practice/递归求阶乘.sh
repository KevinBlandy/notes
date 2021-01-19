#!/bin/bash
# ============================================= 2019年1月22日 13:56:43

function factorial()
	if [ ${1} -eq 1 ] ; then
		echo 1
	else
		local temp=$[${1} - 1]
		local result=`factorial ${temp}`
		echo $[${result} *  ${1}]
	fi

read -p "输入一个值:" value
result=`factorial ${value}`
echo "${value}的阶乘等于:${result}"