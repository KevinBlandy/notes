------------------------
常用的命令
------------------------
	echo
		# 打印文字到屏幕 
		# 参数
			-n
				* 在末尾不添加换行符
			-e
				* 会解释引号中的转义字符（就是让转义字符生效）
	type
		* 用户返回某个指令的类型
			type echo
			echo is a shell builtin (echo 是shell内置脚本)

			type ls
			ls is /usr/bin/ls  (type 是 /usr/bin 目录下的程序)
		
		* 参数
			-a
				* 查看命令的所有定义
					type -a echo (echo命令即是内置命令，也有对应的外部程序)
					echo is a shell builtin
					echo is /usr/bin/echo
			-t
				* 返回数据的类型
					alias		别名
					keyword		关键字(例如: type -t if)
					function	函数
					builtin		内置命令
					file		文件
				* 如果不是这几种类型, 则不会返回任何东西
			

	exec
		执行另一个 Shell 脚本 

	read
		读标准输入 
	
	source 
		* 执行另一个脚本, 通常用于重新加载一个配置文件。
		* 最大的特点是在当前 Shell 执行脚本, 不像直接执行脚本时，会新建一个子 Shell
		* 所以, source命令执行脚本时, 不需要export变量

		* 可以通过这方式导入其他外部脚本的变量, 函数

		* 简写, 可以使用一个点（.）来表示
			. .bashrc

	alias 
		* 为一个命令指定别名
			alias search=grep # 为grep命令起一个search的别名。

		* 可以用来为长命令指定一个更短的别名
			$ alias today='date +"%A, %B %-d, %Y"'
			$ today # Tuesday, June 23, 2020

		* 为了防止误删除文件，可以指定rm命令的别名
			alias rm='rm -i'
		* 可以接受参数，参数会直接传入原始命令
		* 直接调用alias命令，可以显示所有别名
			alias
		
		* unalias命令可以解除别名
			unalias lt
		
	expr
		对整数型变量进行算术运算 

	test
		用于测试变量是否相等,是否为空,文件类型等 

	exit
		退出
	
	sleep
		线程暂停N秒

------------------------
mktemp 和 trap 
------------------------
	# Bash 脚本有时需要创建临时文件或临时目录。
	# 常见的做法是，在/tmp目录里面创建文件或目录，这样做有很多弊端，使用mktemp命令是最安全的做法。
		* /tmp目录是所有人可读写的，任何用户都可以往该目录里面写文件。创建的临时文件也是所有人可读的。
		* 如果攻击者知道临时文件的文件名，他可以创建符号链接，链接到临时文件，可能导致系统运行异常。攻击者也可能向脚本提供一些恶意数据。
		* 因此，临时文件最好使用不可预测、每次都不一样的文件名，防止被利用。
	
	# 生成临时文件应该遵循下面的规则
		创建前检查文件是否已经存在。
		确保临时文件已成功创建。
		临时文件必须有权限的限制。
		临时文件要使用不可预测的文件名。
		脚本退出时，要删除临时文件（使用trap命令）。
	
	# mktemp命令就是为安全创建临时文件而设计的。
		* 在创建临时文件之前，它不会检查临时文件是否存在，但是它支持唯一文件名和清除机制，因此可以减轻安全攻击的风险。

	# 直接运行mktemp命令，就能生成一个临时文件。
		[root@KevinBlandy foo]# mktemp
		/tmp/tmp.wgXlzJQl5y

		
		#!/bin/bash
		temp_file=$(mktemp)
		echo "${temp_file}" # /tmp/tmp.sBE02WbEBj
	
		* ktemp命令生成的临时文件名是随机的，而且权限是只有用户本人可读写。
	
		* 为了确保临时文件创建成功，mktemp命令后面最好使用 OR 运算符（||），保证创建失败时退出脚本。
			TMPFILE=$(mktemp) || exit 1
		
		* 为了保证脚本退出时临时文件被删除，可以使用trap命令指定退出时的清除操作。
			trap 'rm -f "$TMPFILE"' EXIT

			TMPFILE=$(mktemp) || exit 1
			echo "Our temp file is $TMPFILE"
	
	 -d
		* 创建一个临时目录
	-p
		*参数可以指定临时文件所在的目录。
		* 默认是使用$TMPDIR环境变量指定的目录，如果这个变量没设置，那么使用/tmp目录
	-t
		* 指定临时文件的文件名模板，模板的末尾必须至少包含三个连续的X字符，表示随机字符，建议至少使用六个X。
		* 默认的文件名模板是tmp.后接十个随机字符
			mktemp -t mytemp.XXXXXXX # /tmp/mytemp.yZ1HgZV
	

		

	# trap 命令用来在 Bash 脚本中响应系统信号
		trap [动作] [信号1] [信号2] ...

		* “动作”是一个 Bash 命令
		* “信号”常用的有以下几个
			HUP：编号1，脚本与所在的终端脱离联系。
			INT：编号2，用户按下 Ctrl + C，意图让脚本中止运行。
			QUIT：编号3，用户按下 Ctrl + 斜杠，意图退出脚本。
			KILL：编号9，该信号用于杀死进程。
			TERM：编号15，这是kill命令发出的默认信号。
			EXIT：编号0，这不是系统信号，而是 Bash 脚本特有的信号，不管什么情况，只要退出脚本就会产生。
		
		
			trap 'rm -f "$TMPFILE"' EXIT # 脚本遇到EXIT信号时，就会执行rm -f "$TMPFILE"
	
		
		* trap 命令的常见使用场景，就是在 Bash 脚本中指定退出时执行的清理命令
		
		* 如果trap需要触发多条命令，可以封装一个 Bash 函
			function egress {
			  command1
			  command2
			  command3
			}

			trap egress EXIT
	

