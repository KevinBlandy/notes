--------------------------
命令行参数				  |
--------------------------
	# 启动执行脚本的时候,可以传递多个参数,以空格分割
	# 在脚本中可以通过这些特殊的变量去获取参数
		$0	# 脚本文件的名称
		$1	# 第一个参数
		$n	# 第N个参数
		$9	# 第九个参数
	
	# 如果参数值中包含空格,那么必须使用双引号包裹
	
	# 如果你的参数不止9个,也是可以的,在第几个变量后,需要使用${}
		${10} # 因为 $10 会被解释为 $1 和 0
	
	# 关于脚本的名称
		* ${0} 获取到的不仅仅是脚本的名称,还会包含你的路径信息
			./demo.sh				# ./demo.sh
			 /shell/demo.sh			# /shell/demo.sh
			 bash /shell/demo.sh	# /shell/demo.sh
		
		* 使用 basename 指令来获取到脚本的名称,过滤掉路径信息
			$(basename ${0})
			`basename ${0}`

	
	# 使用参数之前,建议先测试参数是否存在
		if [ -n "${1}" ]

--------------------------
特殊参数变量			  |
--------------------------
	$#
		# 返回参数的数量(不包含${0})
		# $符号的冲突问题
			* 在${} 内部不能嵌套 $,会有异常
			* 可以在内部使用 ! 来代替 $ ,!不能使用大括号{}

			for (( i = 1 ; i <= $# ; i ++)); do
				echo "${!i}"	# ${${i}},嵌套感叹号异常
			done

		# 直接获取最后一个参数
			${!#}
		
	
	$* / $@
		# $* 该变量把所有的参数都当作(连接)一个单词来保存
		# $@ 该变量会把所有的参数当作字符串中一个或者多个独立的单词,可以使用for遍历挨个遍历出来
		# demo
			echo "\$* -----------"
			for key in "$*" ; do
				echo "${key}"
			done

			echo "\$@ -----------"
			for key in "$@" ; do
				echo "${key}"
			done

			$* -----------
			a b c
			$@ -----------
			a
			b
			c

--------------------------
移动变量				  |
--------------------------
	# shift 命令可以根据它们相对的位置来移动命令行参数
	# 默认情况下,它会将每个参数变量向左移动一个位置(变量$0的值,也就是程序名,不会改变)
	# 使用场景就是:不知道到底有多少参数时,可以只操作第一个参数,移动参数,然后继续操作第一个参数

		# 只要第一个参数不为空
		while [ -n "$1" ]; do
			# 每次都直接消费第一个参数就好了
			echo "param=${1}"
			# 剩下的参数往前移动
			shift
		done
		# 文件名称不会改变
		echo "${0}"	
	
	# 参数的值被移出,它的值就被丢弃了,无法恢复

	# 可以提供一个参数,表示一次性要移动多少个位置
		#./demo.sh a b c

		shift 2
		echo "${1}"	# c
	
--------------------------
处理选项				  |
--------------------------
	# 处理简单的选项
		while [ -n "$1" ] ; do
			case "$1" in
				"-a")
					echo "有 -a 选项";;
				"-b")
					echo "有 -b 选项";;
				"-c")
					echo "有 -c 选项";;
				*)
					echo "找到  ${1} 选项";;
			esac
			shift
		done

	
	# 分离参数和选项
		* 对于linux来说特殊的字符 --(双破折号),表名选项列表结束
		* 在 -- 后的数据就是参数而不是选项了
			# ./demo.sh -a -b -c -- 9 7 8
			echo
			# 先执行选项
			while [ -n "$1" ] ; do
				case "$1" in
					"-a")
						echo "有 -a 选项";;
					"-b")
						echo "有 -b 选项";;
					"-c")
						echo "有 -c 选项";;
					"--")
						break ;;	# -- 表示选项已经结束,跳出循环
					*)
						echo "$1 不是一个合法的选项";;
				esac
				shift
			done

			# shift 已经删除了所有的选项,此时就可以获取参数了
			for param in $@ ; do
				echo "param = ${param}"
			done
	
	# 处理带值的选项
		# ./demo.sh -a -b 151 -c -- 9 7 8
		echo
		while [ -n $1 ] ; do
			case "$1" in
				"-a")
					echo "找到 -a 选项";;
				"-b")
					param="$2"
					echo "找到 -b 选项,值为 $param"
					shift ;;
				"-c")
					echo "找到 -c 选项 ";;
				"--")
					shift
					break ;;
				*)
					echo "$1 选项不合法 "
			esac
			shift
		done

		for param in "$@" ; do
			echo "param=$param"
		done
	
	# 使用 getopt
		* 在处理命令行选项和参数时非常方便的工具
		* 它能够识别命令行参数,从而在脚本中解析它们时更方便

			getopt opt parameters
			
			opt
				* 它定义了命令行有效的选项字母,还定义了哪些选项字目需要参数值
				* 在opt中列出要在脚本中用到的每个命令行项字母
				* 然后再每个需要参数值选项字母后添加一个冒号
				* getopt就会根据 opt 的描述去解析提供的参数
			
			getopt ab:cd -a -b test1 -cd test2 test3
			-a -b test1 -c -d -- test2 test3
				* 定义了a,b,c,d四个擦数
				* b参数需要参数值,因为它的后面有:
				* 该命令运行的时候,会检查提供的参数列表(-a -b test1 -cd test2 test3),并且基于opt(ab:cd)进行解析
				* 它会自动将-cd选项分成两个单独的选项,并插入双破折线来分隔行中的额外参数
		
		* 如果指定了 opt 不存在的选项,会产生错误消息,可以使用 -q 来忽略错误消息
			getopt -q ab:cd -a -b test1 -cde test2 test3
			-a -b 'test1' -c -d -- 'test2' 'test3'
		
	# 脚本使用 getopt
		* 用getopt命令生成的格式化后的版本来替换已有的命令行选项和参数
		*  用set命令能够做到
			set -- `getopt -q [参数描述] "$@"`
		
		* demo
			set -- `getopt -q ab:cd "$@"`
			while [ -n "$1" ] ; do
				case "$1" in 
					"-a")
						echo "找到 -a 选项" ;;
					"-b")
						param="$2"
						echo "找到 -b 选项,值为 $param"
						shift ;;
					"-c")
						echo "找到 -c 选项" ;;
					"--")
						shift 
						break ;;
					*)
						echo "$1 不是一个合法的选项"	
				esac
				shift
			done

			for param in "$@" ; do
				echo "param=$param"
			done
		
		* getopt命令并不擅长处理带空格和引号的参数值
		* 它会将空格当作参数分隔符,而不是根据双引号将二者当作一个参数
	
	# 更加高级的 getopts
		* 内置于bash shell,多了一些扩展功能
		* 语法
			getopts opt val

			opt
				* 就是选项的描述
				* 如果选项需要有值的话,在后面添加冒号
				* 在整个opt表达式前面添加:可以忽略错误信息
			val
				* 当前参数保存在该变量中

		* 该命令会用到两个环节变量 OPTIND 和 OPTARG
		* 如果选项需要一个参数值 OPTARG 环节变量就会保存这个值
		* 使用 getopts,参数值可以使用空格(字符串包裹)
		* 而且选项和值可以紧贴一起: -b"我 就是值"
		* 未定义的选项统一输出(opt)成问号
		* 每次getopts进行迭代时候,会把环境变量 OPTIND + 1
		* 在getopts完成处理时,你可以使用shift命令和 OPTIND 值来移动参数

		* demo
			# ./demo.sh -a -b"Hello" -c -z p1 p2 p3
			echo 
			while getopts :ab:c opt ; do
				case "$opt" in 
					"a")
						echo "找到选项 -a";;
					"b")
						echo "找到选项 -b 值是 $OPTARG";;
					"c")
						echo "找到选项 -c";;
					*)
						echo "非法选项 $opt";;
				esac
			done
			
			# 选项迭代完成后,移动参数位置,开始迭代参数
			shift $[$OPTIND - 1]
			for param in "$@" ; do
				echo "param=$param"
			done
			——————————————————————————————————————————————
			找到选项 -a
			找到选项 -b 值是 Hello
			找到选项 -c
			非法选项 ?
			param=p1
			param=p2
			param=p3
			——————————————————————————————————————————————

		


--------------------------
获取用户的输入			  |
--------------------------
	# 使用read命令来获取用户的输入
	# 收到输入后,read命令会把输入放入一个变量
		read val
		
		* 简单demo
		echo -n "输入你的名字:"
		read name
		echo "你的名字是:$name"
	
	# 可以通过 -p 选项指定提示语句
		read -p "请输入你的名字:"
	
	# 可以把数据分配到多个变量

		read -p "请输入几个名字:" name1 name2 names

		* 输入的每个数据值都会分配给变量列表中的下一个变量
		* 如果变量数量不够,剩下的数据就全部分配给最后一个变量
	
	# 也可以不指定变量,read命令会将它收到的任何数据都放进特殊环境变量 REPLY 中
		read -p "请输入几个名字:"
		echo $REPLY
	
	# 可以通过 -t (秒)设置超时机制
		* 超时后,read 命令会返回一个 非0 状态码

		if read -t 5 -p "请输入你的名字:" name; then
			echo "name=$name"
		else
			echo
			echo "超时"
		fi
	
	# 允许用户输入的时候，使用readline库提供的快捷键，添加 -e 参数
		read -e fileName
		echo $fileName # 在输入文件名称的时候，可以通过 tab 键补充文件名称
	
	# 使用 -n 限制输入的字符数
		* 当达到指定的数量后,read自动返回(无需回车)
	
		read -n 1 -p "确定要删除吗?[Y/N]:" answer
		case $answer in 
			"y" | "Y")
				echo
				echo "删除ok";;
			"n" | "N")
				echo
				echo "返回";;
			*)
				echo
				echo "非法参数 $answer";;
		esac
	
	# 隐藏方式读取(输入密码场景),可以使用 -s 选项
		* 这种方式,屏幕不会记录用户的录入信息

		read -s -p "请输入密码:" password
		echo
		echo "$password"
	
	# 把参数封装为数组, 使用 -a 选项
		read -a info
		echo "${info[0]}, ${info[1]}"
	
	# 其他的一些参数
		-d delimiter:	定义字符串delimiter的第一个字符作为用户输入的结束，而不是一个换行符。
		-r:				raw 模式，表示不把用户输入的反斜杠字符解释为转义字符。
		-u fd:			使用文件描述符fd作为输入。

	
	# 从文件读取
		* 可以用read命令来读取Linux系统上文件里保存的数据
		* 每次调用read命令,它都会从文件中读取一行文本
		* 当文件中再没有内容时,read命令会退出并返回非零退出状态码
		* 最常见的方法是对文件使用cat命令,将结果通过管道直接传给含有read命令的while命令

		number=1
		echo "-------- start ----------"
		cat /etc/passwd | while read line ; do
			echo "$number:$line"
			number=$[$number + 1]
		done
		echo "-------- end ----------"



	# IFS 变量
		* read命令读取的值，默认是以空格分隔。可以通过自定义环境变量IFS（内部字段分隔符，Internal Field Separator 的缩写），修改分隔标志。
			FILE=/etc/passwd

			# 读取用户输入的用户名
			read -p "Enter a username > " user_name

			# 根据用户名从  /etc/passwd 文件中找到行
			file_info="$(grep "^$user_name:" $FILE)"

			# 如果找到
			if [ -n "$file_info" ]; then
			  # 设置分隔符为:并且使用
			  # 巧妙的修改分隔符
			  IFS=":" read user pw uid gid name home shell <<< "$file_info"
			  echo "User = '$user'"
			  echo "UID = '$uid'"
			  echo "GID = '$gid'"
			  echo "Full Name = '$name'"
			  echo "Home Dir. = '$home'"
			  echo "Shell = '$shell'"
			else
			  echo "No such user '$user_name'" >&2
			  exit 1
			fi

		* IFS的赋值命令和read命令写在一行，这样的话，IFS的改变仅对后面的命令生效
		* 该命令执行后IFS会自动恢复原来的值
			IFS=":" read user pw uid gid name home shell <<< "$file_info"
		
		* 如果不写在一行，就要采用下面的写法
			# 备份旧的
			OLD_IFS="$IFS"
			# 重新设置
			IFS=":"
			read user pw uid gid name home shell <<< "$file_info"
			# 还原
			IFS="$OLD_IFS"
	
		* 如果IFS设为空字符串，就等同于将整行读入一个变量。
			input="/path/to/txt/file"
			while IFS= read -r line
			do
			  echo "$line" # 逐行读取文件，每一行存入变量line
			done < "$input"


	
	
