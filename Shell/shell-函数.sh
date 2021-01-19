--------------------------------
创建函数						|
--------------------------------
	# 创建函数
		function name {
			commands
		}

		name(){
			commands
		}
	
		* name ,函数的唯一名称,不唯一的话,会被覆盖
		* commands ,代码
	
	# 调用,直接写函数名称就可以
		function say { 
			echo "Hello"
		}
		say
	
		* 函数必须先定义再执行
	
	# 函数的删除跟删除变量是一样的
		unset -f functionName
	
	# 查看当前shell定义的所有函数
		declare -f
			* 不仅会输出函数名，还会输出所有定义。输出顺序是按照函数名的字母表顺序。
			* 由于会输出很多内容，最好通过管道命令配合more或less使用。不仅会输出函数名，还会输出所有定义。输出顺序是按照函数名的字母表顺序。由于会输出很多内容，最好通过管道命令配合more或less使用。
		
		declare -F
			* 输出所有已经定义的函数名，不含函数体。

		* 还支持查看单个函数的定义
			declare -f functionName
		
		

	
--------------------------------
返回值							|
--------------------------------
	# 默认退出状态码
		* 默认情况下,函数的退出状态码是函数中最后一条命令返回的退出状态码
		* 在函数执行结束后,可以用标准变量$?来确定函数的退出状态码
			function say { 
				echo "Hello"
			}
			say
			echo "status = $?"
	
	
	# 使用 return
		* return命令来退出函数并返回特定的退出状态码
		* return命令允许指定一个整数值来定义函数的退出状态码
		* 退出状态码必须是0~255,并且遇到return ,函数终止执行
			retun val
	
	# 使用函数输出
		* 对函数的输出进行保存
		* 跟保存命令的输出一样

			function foo () {
				echo "Hello"
				echo "World"
			} 

			result=$(foo)
			echo $result # Hello World

		
		* 通过这种技术,还可以返回浮点值和字符串值
		* 这使它成为一种获取函数返回值的强大方法

--------------------------------
函数中使用变量					|
--------------------------------
	# 函数的参数
		* bash shell会将函数当作小型脚本来对待
		*  意味着可以像普通脚本那样向函数传递参数
			function foo {
				for (( i = 0 ; i < $# ; i++ )) ; do
					echo "param[${i}] = ${!i}"	
				done
			}
			foo $JAVA_HOME "参数1" "参数3"
	# 形参
		$1~$9：函数的第一个到第9个的参数。
		$0：函数所在的脚本名。
		$#：函数的参数总数。
		$@	函数的全部参数，参数之间使用空格分隔。
		$*：函数的全部参数，参数之间使用变量$IFS值的第一个字符分隔，默认为空格，但是可以自定义。
		
		* 如果函数的参数多于9个，那么第10个参数可以用${10}的形式引用，以此类推

	
	# 处理变量
		* 函数使用两种类型的变量
			全局变量
			局部变量
		
		* 全局变量	
			* shell脚本中任何地方都有效的变量
			* 你在脚本的主体部分定义了一个全局变量,那么可以在函数内读取它的值
			* 如果你在函数内定义了一个全局变量,可以在脚本的主体部分读取它的值
			* 默认情况下,你在脚本中定义的任何变量都是全局变量,在函数外定义的变量可在函数内正常访问
				function foo {
					globalVal="Hello"
				}
				foo	# 函数要执行,全局变量才会被载入内存
				echo $globalVal
		
		*  局部变量
			* 在变量赋值语句中使用local关键字
			* local关键字保证了变量只局限在该函数中
				local localVal="Hello"
			
--------------------------------
数组变量和函数					|
--------------------------------
	# 在函数中使用数组变量值有点麻烦,而且还需要一些特殊考虑
	# 向函数传入数组参数
		* 必须将该数组变量的值分解成单个的值,然后将这些值作为函数参数使用
		*  在函数内部,可以将所有的参数重新组合成一个新的变量

			function testit {
				local newarray
				# 获取所有的参数,其实就是数组
				newarray=`echo "$@"`
				echo "函数内部收到的数组: ${newarray[*]}"
			}

			myarray=(1 2 3 4 5)

			echo "外面定义的数组 ${myarray[*]}"
			
			# 把数组一起传递进去
			testit ${myarray[*]}

		* 求和demo
			#!/bin/bash
			function addarray {
				local sum=0
				local newarray
				newarray=`echo "$@"`
				for value in ${newarray[*]} ; do
					sum=$[ $sum + $value ]
				done
				echo $sum
			}
			myarray=(1 2 3 4 5)
			echo "原始数组: ${myarray[*]}"
			arg1=`echo ${myarray[*]}`
			result=`addarray $arg1`
			echo "计算结果是: $result"
	
	# 从函数返回数组
		* 从函数里向shell脚本传回数组变量也用类似的方法
		*  函数用echo语句来按正确顺序输出单个数组值,然后脚本再将它们重新放进一个新的数组变量中

		* 把每个数组元素都 * 2 后返回demo
			function arraydblr {
				local array
				local result
				local index=0
				array=`echo "$@"`
				for val in $array ; do
					result[${index}]=$[$val * 2]
					index=$[${index} + 1]
				done 
				echo "${result[*]}"
			}
			array=(1 2 3 4 5 6)
			echo "原始数组:${array[*]}" 
			result=`arraydblr ${array[*]}`
			echo "函数返回数组:${result}"


--------------------------------
函数的递归						|
--------------------------------
	# 递归而已,没啥哈解释的
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


--------------------------------
创建库							|
--------------------------------
	# bash shell允许创建函数库文件,然后在多个脚本中引用该库文件
	# 使用 source 命令
		source file

		source ./funcs.sh
		
		* 引用进来的脚本,其实就是从头到尾执行了一遍

	# 也可以使用 . 简写
		. ./funcs.sh
	
	# 这样就可以在当前的函数中调用库中的函数了

--------------------------------
在命令行上使用函数				|
--------------------------------
	# 在命令行上创建函数
		$ function divem { echo $[ $1 / $2 ]; }
		$ divem 100 5
		20

		* 在命令行上定义函数时,你必须记得在每个命令后面加个分号
		*  这样shell就能知道在哪里是命令的起止了
	
		
		*  另一种方法是采用多行方式来定义函数在定义时,bash shell会使用次提示符来提示输入更多命令
		*  用这种方法,不用在每条命令的末尾放一个分号
		*  在函数的微尾部使用{花括号,shell就知道你要定义函数
	
		* 如果给函数起了个跟内建命令或另一个命令相同的名字,函数将会覆盖原来的命令
		* 命令行上直接定义shell函数的明显缺点是退出shell时,函数就消失了

	# 在 .bashrc 文件中定义函数
		* .bashrc在每次启动一个新shell的时候,都会由shell重新载入
			~/.bashrc
		
		* 许多Linux发行版已经在.bashrc文件中定义了一些东西,所以注意不要误删了

		* 把你写的函数放在文件末尾就行了

		* 只要是在shell脚本中,都可以使用 source 命令(或者.别名操作符)把库文件中的函数添加 到 .bashrc 文件中
			. /usr/local/myfuncs.sh
		
			*  这样的话,bashrc 就在启动的时候加载自定义的库,就能在命令行界面使用自定义的函数了
	

	

	

