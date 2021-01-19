--------------------------------
shell - 变量					|
--------------------------------
	# 定义规则
		* 变量名和等号之间不能有空格
		* 命名只能使用英文字母,数字和下划线,首个字符不能以数字开头
		* 中间不能有空格,可以使用下划线(_)
		* 不能使用标点符号
		*  不能使用bash里的关键字(可用help命令查看保留关键字)
	
	# 使用(读取)变量,使用 $ 符号
		name="KevinBlandy"
		echo $name
		echo${name}

		* 变量名外面的花括号是可选的,加不加都行
		* 加花括号是为了帮助解释器识别变量的边界,比如变量在字符串里面
			for skill in Java PHP Python; do
				echo "I am good at ${skill}"
			done
		* 变量没有使用$符,会被当做字符串处理
	
	# 变量可以被多次定义(修改)
		name="KevinBlandy"
		name="Litch"
	
	# 使用 readonly 关键字设置只读变量
		name="KevinBlandy"
		readonly name

		* 尝试修改name变量,会抛出异常
	
	# 使用 unset 删除变量
		name="KevinBlandy"
		unset name

		* 这个命令不是很有用。因为不存在的 Bash 变量一律等于空字符串，所以即使unset命令删除了变量，还是可以读取这个变量，值为空字符串。
		* 所以，删除一个变量，也可以将这个变量设成空字符串。
	
	# 变量的作用域类型

		* 局部变量 局部变量在脚本或命令中定义,仅在当前shell实例中有效,其他shell启动的程序不能访问局部变量

		* 环境变量 所有的程序,包括shell启动的程序,都能访问环境变量,有些程序需要环境变量来保证其正常运行
		* 必要的时候shell脚本也可以定义环境变量

		* shell变量 shell变量是由shell程序设置的特殊变量
		* shell变量中有一部分是环境变量,有一部分是局部变量,这些变量保证了shell的正常运行
	

	# 后引号运算
		* 把``里面的内容当作命令去解析,返回执行的结果

		sum=`expr 15 + 15`
		echo $sum# -> 30
		
		* 也可以使用 $() 来代替 ``
			sum=$(expr 15 + 15)
			echo $sum# -> 30

	
	# 如果变量的值本身也是变量，可以使用${!varname}的语法，读取最终的值
		var1="OK"
		var2=var1
		echo ${var2}	# var1
		echo ${!var2}	# OK
	
	# 输出变量，export 命令
		* 创建的变量仅可用于当前 Shell，子 Shell 默认读取不到父 Shell 定义的变量
		* 为了把变量传递给子 Shell，需要使用export命令。这样输出的变量，对于子 Shell 来说就是环境变量。
			NAME=foo
			export NAME		# 输出了变量NAME。变量的赋值和输出也可以在一个步骤中完成。
			export NAME=value	# 变量的赋值和输出也可以在一个步骤中完成。
	
	# let 命令
		* let命令声明变量时，可以直接执行算术表达式。
			let foo=1+2

		* let命令的参数表达式如果包含空格，就需要使用引号
			let "foo = 1 + 2"
		
		* let可以同时对多个变量赋值，赋值表达式之间使用空格分隔。
			let "v1 = 1" "v2 = v1++"
			echo $v1,$v2 # 2,1
		
		

--------------------------------
shell - 变量				
--------------------------------
	# 定义规则
		* 变量名和等号之间不能有空格
		* 命名只能使用英文字母,数字和下划线,首个字符不能以数字开头
		* 中间不能有空格,可以使用下划线(_)
		* 不能使用标点符号
		*  不能使用bash里的关键字(可用help命令查看保留关键字)
	
	# 使用(读取)变量,使用 $ 符号
		name="KevinBlandy"
		echo $name
		echo${name}

		* 变量名外面的花括号是可选的,加不加都行
		* 加花括号是为了帮助解释器识别变量的边界,比如变量在字符串里面
			for skill in Java PHP Python; do
				echo "I am good at ${skill}"
			done
		* 变量没有使用$符,会被当做字符串处理
	
	# 变量可以被多次定义(修改)
		name="KevinBlandy"
		name="Litch"
	
	# 使用 readonly 关键字设置只读变量
		name="KevinBlandy"
		readonly name

		* 尝试修改name变量,会抛出异常
	
	# 使用 unset 删除变量
		name="KevinBlandy"
		unset name

		* 这个命令不是很有用。因为不存在的 Bash 变量一律等于空字符串，所以即使unset命令删除了变量，还是可以读取这个变量，值为空字符串。
		* 所以，删除一个变量，也可以将这个变量设成空字符串。
	
	# 变量的作用域类型

		* 局部变量 局部变量在脚本或命令中定义,仅在当前shell实例中有效,其他shell启动的程序不能访问局部变量

		* 环境变量 所有的程序,包括shell启动的程序,都能访问环境变量,有些程序需要环境变量来保证其正常运行
		* 必要的时候shell脚本也可以定义环境变量

		* shell变量 shell变量是由shell程序设置的特殊变量
		* shell变量中有一部分是环境变量,有一部分是局部变量,这些变量保证了shell的正常运行
	

	# 后引号运算
		* 把``里面的内容当作命令去解析,返回执行的结果

		sum=`expr 15 + 15`
		echo $sum# -> 30
		
		* 也可以使用 $() 来代替 ``
			sum=$(expr 15 + 15)
			echo $sum# -> 30

	
	# 如果变量的值本身也是变量，可以使用${!varname}的语法，读取最终的值
		var1="OK"
		var2=var1
		echo ${var2}	# var1
		echo ${!var2}	# OK
	
	# 输出变量，export 命令
		* 创建的变量仅可用于当前 Shell，子 Shell 默认读取不到父 Shell 定义的变量
		* 为了把变量传递给子 Shell，需要使用export命令。这样输出的变量，对于子 Shell 来说就是环境变量。
			NAME=foo
			export NAME		# 输出了变量NAME。变量的赋值和输出也可以在一个步骤中完成。
			export NAME=value	# 变量的赋值和输出也可以在一个步骤中完成。
	
	# 变量的默认值
		${varname:-word}
			* 如果变量varname存在且不为空，则返回它的值，否则返回word

		${varname:=word}
			* 如果变量varname存在且不为空，则返回它的值，否则将它设为word，并且返回word
		
		${varname:+word}
			* 如果变量名存在且不为空，则返回word，否则返回空值。
			* 它的目的是测试变量是否存在，比如${count:+1}表示变量count存在时返回1（表示true），否则返回空值。
		
		${varname:?message}
			* 如果变量varname存在且不为空，则返回它的值，否则打印出varname: message，并中断脚本的执行。
			* 如果省略了message，则输出默认的信息“parameter null or not set.”。
			* 它的目的是防止变量未定义，比如${count:?"undefined!"}表示变量count未定义时就中断执行，抛出错误，返回给定的报错信息undefined!。

		* 上面四种语法如果用在脚本中，变量名的部分可以用到数字1到9，表示脚本的参数。
			filename=${1:?"filename missing."}
		
	# declare 命令
		* declare命令可以声明一些特殊类型的变量，为变量设置一些限制
			declare OPTION VARIABLE=value
		
		* 主要参数（OPTION）如下
			-a：声明数组变量。
			-f：输出所有函数定义。
			-F：输出所有函数名。不包含定义。
			-i：声明整数变量。
			-l：声明变量为小写字母。
			-p：查看变量信息。对于未定义的变量，会提示找不到。
				declare -p foo

			-r：声明只读变量。
			-u：声明变量为大写字母。
			-x：该变量输出为环境变量。等同于export命令
		
		* declare命令如果用在函数中，声明的变量只在函数内部有效，等同于local命令

		* 不带任何参数时，declare命令输出当前环境的所有变量，包括函数在内，等同于不带有任何参数的set命令。


--------------------------------
其他特殊变量
--------------------------------
	$_
		* 上一个命令的最后一个参数。
	
	$!
		* 为最近一个后台执行的异步命令的进程 ID
	
	LINENO
		* 行号
	
	FUNCNAME 
		* 返回一个数组，内容是当前的函数调用堆栈。
		* 该数组的0号成员是当前调用的函数，1号成员是调用当前函数的函数，以此类推。
	
	BASH_SOURCE
		* 返回一个数组，内容是当前的脚本调用堆栈。
		* 该数组的0号成员是当前执行的脚本，1号成员是调用当前脚本的脚本，以此类推，跟变量FUNCNAME是一一对应关系。

	BASH_LINENO 
		* 返回一个数组，内容是每一轮调用对应的行号
		* ${BASH_LINENO[$i]}跟${FUNCNAME[$i]}是一一对应关系，表示${FUNCNAME[$i]}在调用它的脚本文件${BASH_SOURCE[$i+1]}里面的行号。
	

