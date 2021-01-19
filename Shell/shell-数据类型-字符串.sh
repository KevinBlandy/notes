-----------------
数据类型字符串	 |
-----------------
	# Bash 只有一种数据类型，就是字符串。不管用户输入什么数据，Bash 都视为字符串。因此，字符串相关的引号和转义，对 Bash 来说就非常重要。
	# 字符串是shell编程中最常用最有用的数据类型(除了数字和字符串,也没啥其它类型好用了)
	# 字符串可以用单引号,也可以用双引号,也可以不用引号,单双引号的区别跟PHP类似

	# 单引号
		str='this is a string'

		* 单引号里的任何字符都会原样输出,单引号字符串中的变量是无效的
			name="KevinBlandy"
			echo 'Hello ${name}'	-> Hello ${name}
			
		* 单引号字串中不能出现单独一个的单引号(对单引号使用转义符后也不行)
				str='hello, \'' -> unexpected EOF while looking for matching `''

		* 单引号可成对出现,作为字符串拼接使用
			name='v'
			str='hello, '$name' !'	-> hello, v !
		
		* 如果要在单引号中输出单引号，不能使用转义，需要在外层的左边的单引号前面加上一个美元符号（$），然后再对里层的单引号转义。
			echo $'\'Hello\''
			'Hello'
			* 更合理的方法是改在双引号之中使用单引号。
		
	
	# 双引号
		your_name='vin'
		str="Hello, I know you are \"$your_name\"! \n"
		echo -e $str

		* 双引号保存原始命令的输出格式
			echo `free -m`		# 格式超级乱，一行
			echo "`free -m`"	# 原汁原味
	
	# 字符串的拼接
		name="vin"

		greeting="hello, "$name" !"		-> hello, vin !
		greeting_1="hello, ${name} !"	-> hello, vin !

		greeting_2='hello, '$name' !'	-> hello, vin !
		greeting_3='hello, ${name} !'	-> hello, ${name} !
	
	#  获取字符串的长度
		#!/bin/bash
		name="KevinBlandy"
		echo ${#name}			-> 11

		S="KevinBlandy"
		echo "${S} size = ${#S}"	-> 11
	
	# 提取子字符串
		string="KevinBlandy"
		echo ${string:1:4} -> evin

		* ${:x:y} ,x表示开始的角标,y表示取多少个字符，如果不写y，表示截取到末尾

		* 如果x为负值，则表示从后面开始截取，注意，负数前面必须有一个空格， 以防止与${variable:-word}的变量的设置默认值语法混淆
			echo ${foo: -5}
		* 这时，如果还指定y，则y不能小于零。

		* 这种语法不能直接操作字符串，只能通过变量来读取字符串
	
	# 查找子字符串角标
		#!/bin/bash
		NAME="KevinBlandy"
		S="B"
		index=`expr index ${NAME} ${S}`
		echo "${index}"	# -> 6

		* 返回的开始位置是从1开始计算的
		* 如果找不到返回0
		* 实际上使用的是shell命令
			expr index "KevinBlandy" "B" 

	

	# Here 文档 
		* 是一种输入多行字符串的方法，格式如下
			<< token
			我就是多行
				字符串
			token

			[root@KevinBlandy ~]# <<token
			> 我就是
			>         多行字符串
			> token
			[root@KevinBlandy ~]# 

		* token自定义
		* Here 文档也不能作为变量的值，只能用于命令的参数。

		* Here 文档还有一个变体，叫做 Here 字符串（Here string），使用三个小于号（<<<）表示。
		* 它的作用是将字符串通过标准输入，传递给命令。
			$ cat <<< 'hi there'	->	$ echo 'hi there' | cat
			$ md5sum <<< 'ddd'	->	$ echo 'ddd' | md5sum
	
	# 大小写转换
		* 转换为大写 ${varname^^}
		* 转换为小写 ${varname,,}

	# 字符串头部的模式匹配
		* 两种语法可以检查字符串开头，是否匹配给定的模式。
		* 如果匹配成功，就删除匹配的部分，返回剩下的部分。原始变量不会发生变化

			${variable#pattern}
				* 如果 pattern 匹配变量 variable 的开头，
				* 删除最短匹配（非贪婪匹配）的部分，返回剩余部分
				
			${variable##pattern}
				* 如果 pattern 匹配变量 variable 的开头，
				* 删除最长匹配（贪婪匹配）的部分，返回剩余部分

		* 匹配模式pattern可以使用*、?、[]等通配符
		* 删除文件路径的目录部分，只留下文件名。
			path=/home/cam/book/long.file.name
			echo ${path##*/}	# long.file.name

		* 匹配不成功，返回原始字符串
		* 将头部匹配的部分，替换成其他内容
			${variable/#pattern/string} # 模式必须出现在字符串的开头

			foo=JPG.JPG
			echo ${foo/#JPG/jpg} # jpg.JPG


	# 字符串尾部的模式匹配
		* 两种语法可以检查字符串结尾，是否匹配给定的模式。
		* 如果匹配成功，就删除匹配的部分，返回剩下的部分。原始变量不会发生变化。
			${variable%pattern}
				* 如果 pattern 匹配变量 variable 的结尾，
				* 删除最短匹配（非贪婪匹配）的部分，返回剩余部分

			${variable%%pattern}
				* 如果 pattern 匹配变量 variable 的结尾
				* 删除最长匹配（贪婪匹配）的部分，返回剩余部分
		
		* 其他的同上
	
	# 任意位置的模式匹配
		* 两种语法可以检查字符串内部，是否匹配给定的模式。
		* 如果匹配成功，就删除匹配的部分，换成其他的字符串返回。原始变量不会发生变化。
			${variable/pattern/string}
				* 如果 pattern 匹配变量 variable 的一部分，
				* 最长匹配（贪婪匹配）的那部分被 string 替换，但仅替换第一个匹配

			${variable//pattern/string}
				* 如果 pattern 匹配变量 variable 的一部分，
				* 最长匹配（贪婪匹配）的那部分被 string 替换，所有匹配都替换 
		


				