----------------------
set
----------------------
	# Bash 执行脚本时，会创建一个子 Shell
		bash script.sh 
			* script.sh是在一个子 Shell 里面执行
			* 这个子 Shell 就是脚本的执行环境，Bash 默认给定了这个环境的各种参数
		
	# set命令用来修改子 Shell 环境的运行参数，即定制环境
		* 一共有十几个参数可以定制，官方手册有完整清单，这里介绍其中最常用的几个
	
	# 一般都会使用到的重点 set
		set -eux			# 异常就终止, 变量不存在抛出异常, 执行输出前先输出输出“输出指令”
		set -o pipefail		# 管道命名中，只要有一个失败，就全部失败

		* 另一种办法是在执行 Bash 脚本的时候，从命令行传入这些参数
			bash -euxo pipefail script.sh

----------------------
set
----------------------
	set
		* 接运行set，会显示所有的环境变量和 Shell 函数。
	
	set -u
		* 默认情况如果遇到不存在的变量，Bash 默认忽略它
			echo "${val}" # val不存在，但是不会报错
		* 脚本在头部加上它，遇到不存在的变量就会报错，并停止执行
			#!/bin/bash
			set -u
			echo "${val}" # ./test.sh: line 3: val: unbound variable

		* -u还有另一种写法, 两者是等价的
			set -o nounse
		
	set -x 
		* set -x用来在运行结果之前，先输出执行的那一行命令
			#!/bin/bash
			set -x
			echo "Hello"

			[root@KevinBlandy ~]# ./test.sh 
			+ echo Hello		# 输出执行命令
			Hello
		
		* 主要作用就是，显示某一段内容是什么命令产生的,这对于调试复杂的脚本是很有用的。
		* -x还有另一种写法
			set -o xtrace
	
		* 脚本当中如果要关闭命令输出，可以使用
			set +x
	
	set -e
		* 脚本只要发生错误，就终止执行
		* 默认情况下，如果脚本里面有运行失败的命令（返回值非0），Bash 默认会继续执行后面的命令

		* 实际开发中，如果某个命令失败，往往需要脚本停止执行，防止错误累积。这时，一般采用下面的写法。
			command || exit 1 # command有非零返回值，脚本就会停止执行。
		

		* 还有一种情况。如果两个命令有继承关系，只有第一个命令成功了，才能继续执行第二个命令，那么就要采用下面的写法
			command1 && command2
		
		* set -e根据返回值来判断，一个命令是否运行失败
		* 但是，某些命令的非零返回值可能不表示失败，或者开发者希望在命令失败的情况下，脚本继续执行下去
		* 这时可以暂时关闭set -e
			set +e

		* 该命令执行结束后，再重新打开
			set -e
		
		* -e还有另一种写法
			set -o errexit
	
	set -eo pipefail
		* set -e有一个例外情况，就是不适用于管道命令。
		* 多个子命令通过管道运算符（|）组合成为一个大的命令。Bash 会把最后一个子命令的返回值，作为整个命令的返回值。
		* 也就是说，只要最后一个子命令不失败，管道命令总是会执行成功，因此它后面命令依然会执行，set -e就失效了。

		* set -o pipefail用来解决这种情况，只要一个子命令失败，整个管道命令就失败


	
	set -n
		* 不运行命令，只检查语法是否正确
		* 其他写法
			set -o noexecset -o noexec
		
	set -f
		* 不对通配符进行文件名扩展
		* 其他写法
			set -o noglob
		* 关闭
			set +f
	
	set -v
		* 打印 Shell 接收到的每一行输入。
		* 其他写法
			set -o verbose
		* 关闭
			set +v
	


----------------------
shopt 
----------------------
	# shopt命令用来调整 Shell 的参数，跟set命令的作用很类似
		* set是从 Ksh 继承的，属于 POSIX 规范的一部分，而shopt是 Bash 特有的。
	
	# 直接输入shopt可以查看所有参数，以及它们各自打开和关闭的状态
		shopt
	
	# shopt命令后面跟着参数名，可以查询该参数是否打开。
		shopt globstar # globstar  off
	
	# -s用来打开某个参数
		shopt -s optionNameHere
	
	# -u用来关闭某个参数。
		shopt -u optionNameHere
	
	# -q用来查询某个参数是否打开
		shopt -q globstar 
		echo $? # 1
	
		* 但不是直接输出查询结果，而是通过命令的执行状态（$?）表示查询结果。如果状态为0，表示该参数打开；如果为1，表示该参数关闭。
		* 这个用法主要用于脚本，供if条件结构使用。
			if shopt -q globstar; then
			  ...
			if
	


	
