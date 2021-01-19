------------------------------
数组						  |
------------------------------
	# 不支持多维数组,没有限制数组的大小,下标从0开始,并且可以是字符串或者数值
	# 越界不会报错,返回空
	# 使用括号表示数组,每个元素使用空格分开
		ARR=(1 2 3 4)
	
	# 也可以使用回车来定义
		ARR=(
			Hello



			World
		)
		echo ${ARR[*]}	# -> Hello World
		echo ${ARR[1]}	# -> world
		echo ${ARR[2]}	# -> 空

		* 空白的元素会被忽略,后面的元素会向上靠拢
	
	# 可以先定义,再初始化
		ARR=()
		ARR[0]=0
		ARR[1]="Hello"
		ARR[3]="World"
		echo ${ARR[*]}	# -> 0 Hello World
		echo ${ARR[2]}	# 空
		echo ${ARR[3]}	# World

		* 可以不按照顺序来定义,其中跳过的部分为空
	
	# 可以通过角标来获取和设置值
		ARR=(1 2 3 4)
		ARR[0]=1
	
	# 可以通过 @/* 符合来获取到所有的值
		ARR=(1 2 3)
		echo ${ARR} # -> 1
		echo ${ARR[@]} # -> 123
		echo ${ARR[*]} # -> 123

		* 如果元素为空,全部获取时会被忽略
	
	# 获取数组的长度
		ARR=()
		ARR[0]=0
		ARR[15]="1"
		SIZE=${#ARR[*]}
		echo ${SIZE}	# 2

		* 这种方式不会计算数组中空元素的个数
	
	# 定义数组的时候，可以使用通配符
		files=(.*)				# 当前目录下的所有文件
		echo "${files[*]}"
	
	# 获取到数组的序号
		* ${!array[@]}或${!array[*]}，可以返回数组的成员序号，即哪些位置是有值的。

		arr=([5]=a [9]=b [23]=c)
		echo ${!arr[@]} # 5 9 23
		echo ${!arr[*]}	# 5 9 23

		* 利用这个语法，也可以通过for循环遍历数组。
			for i in ${!arr[@]};do
				echo ${arr[i]}
			done
	

	# 提取数组成员
		* ${array[@]:position:length}的语法可以提取数组成员。
			${food[@]:1:1}	# 返回从数组1号位置开始的1个成员
			${food[@]:1:3}	# 返回从1号位置开始的3个成员。
		
		* 如果省略长度参数length，则返回从指定位置开始的所有成员
	
	# 追加数组成员
		* 数组末尾追加成员，可以使用+=赋值运算符
			foo=(a b c)
			echo ${foo[@]} # a b c

			foo+=(d e f)
			echo ${foo[@]} # a b c d e f
	
	# 删除数组
		foo=(a b c d e f)
		echo ${foo[@]} # a b c d e f

		unset foo[2]
		echo ${foo[@]} # a b d e f
	
		
		* 将某个成员设为空值，可以从返回值中“隐藏”这个成员。
			foo=(a b c d e f)
			foo[1]=''		# 不角标1的值, 设置为''即可
			echo ${foo[@]} # a c d e f
		
		* 这里是“隐藏”，而不是删除，因为这个成员仍然存在，只是值变成了空值。
	
		* unset ArrayName可以清空整个数组

------------------------------
关联数组
------------------------------
	# 类似于对象
	# declare -A可以声明关联数组。
		declare -A colors
		colors["red"]="#ff0000"
		colors["green"]="#00ff00"
		colors["blue"]="#0000ff"
	
	# 访问关联数组成员的方式，几乎与整数索引数组相同
		echo ${colors["blue"]}
	
	# 遍历
		declare -A langs
		langs["java"]="System.out.println(\"Hello\")"
		langs["Python"]="print('Hello')"
		langs["Javascript"]="console.log('Hello')"

		*  遍历key
			for i in ${!langs[*]}; do
				echo "${i}"
			done
	
		* 变量val
			for i in ${langs[*]}; do
				echo "${i}"
			done
	
		* 遍历Key和Val
			for i in ${!langs[*]}; do
				key="${i}"
				val="${langs[${i}]}"
				echo "key=${key}, val=${val}"
			done

------------------------------
数组		遍历				  
------------------------------
	# 下标遍历
		arr=(9 8 7 6 5 4 3 2 1 0)
		len=${#arr[*]}
		for (( i = 0; i < ${len}; i++ )); do
			echo "[${i}] = ${arr[i]}"
		done

		[0] = 9
		[1] = 8
		[2] = 7
		[3] = 6
		[4] = 5
		[5] = 4
		[6] = 3
		[7] = 2
		[8] = 1
		[9] = 0
	
	# for in 遍历
		arr=(9 8 7 6 5 4 3 2 1 0)
		for item in ${arr[*]}; do
			echo "${item}"
		done

		9
		8
		7
		6
		5
		4
		3
		2
		1
		0