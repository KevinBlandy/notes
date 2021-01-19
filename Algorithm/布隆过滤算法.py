----------------------------
布隆过滤算法				|
----------------------------
	# 在N多数据中,判断数据是否存在

	# 添加逻辑
		1,存入数据,使用多个hash函数对数据进行运算
			v1 = hash3(key)
			v2 = hash3(key)
			v3 = hash3(key)

		2,多个hash值取模数组长度,把得到的结果角标设置为1
			arr[v1 %  arr.length] = 1;
			arr[v2 %  arr.length] = 1;
			arr[v3 %  arr.length] = 1;

	
	# 判断逻辑
		1,使用多个hash函数对数据进行运算
			v1 = hash3(key)
			v2 = hash3(key)
			v3 = hash3(key)

		2,多个hash值取模数组长度,判断结果角标是否都为1,如果是则包含,任何非1则不包含
			arr[v1 %  arr.length] == 1 && 
			arr[v2 %  arr.length] == 1 &&
			arr[v3 %  arr.length] == 1 
	
	# 注意
		* hash运算次数越多,数组越长,误报的几率越小
