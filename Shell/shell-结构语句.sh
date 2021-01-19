---------------------
if then				 |
---------------------
	# 语法
		if cmd 
		then
			cmds
		fi


		* cmd 其实并不是一个 bool表达式
		* 严格来说,它是一个shell命令
		* 这个命令的结束状态码如果是0,就会执行 cmds 
	
	# 更加优雅的语法
		if cmd; then
			coms
		fi

		* 在cmd后放置一个分号,就可以把then写在后面了
			if pwd > /dev/null;then
				echo "success"
			fi
	
	# if 后面可以跟多个命令(使用分号隔开), 但是只要最后一个命令返回0，就会执行then的部分
		if false; true; then echo 'hello world'; fi
		hello world # 会被执行
	
---------------------
if then else		 |
---------------------
	# 单个else语法
		if cmd; then
			coms
		else
			coms
		fi
	
	# 多个elseif语法
		if cmd; then
			coms
		elif cmd; then
			coms
		else
			coms
		fi
	
	

	# 小练习
		testuser="kevinblandy"
		if grep ${testuser} /etc/passwd; then
			echo "用户 :${testuser} 在系统中存在"
		elif ls -d /home/${testuser}; then
			echo "用户 :${testuser} 在系统中不存在"
			echo "但是存在他的家目录"
		else
			echo "用户不存在，也没有他的家目录"
		fi
	

---------------------
test				 |
---------------------
	# if then 语句中,根据shell命令返回的状态码来确定是否要执行代码块
	# 但是if then不能判断命令状态码之外的条件,此时可以使用 test 指令
		if test condition;then
			cmds
		fi
		
		* condition 是一系列的参数和值
		* 其实condition是把计算结果的true转换为了状态码0,把false转换为了非0
	
	# 如果忽略 condition,那么它就会以非0状态退出,执行else块
		if test; then
			echo "success"
		else
			echo "error"	# 执行
		fi
	
	# 测试变量是否有内容
		var=" "
		test var	# false

		* 全是空白字符串的值test运算结果为false
	
	
	# 更加优雅的写法,使用[]来代替 test 指令
		if [ condition ]; then
			echo "success"
		else
			echo "error"
		fi

		* 注意了,第一个方括号之后,和第二个方括号之前必须添加一个空格
	
	# test 命令可以判断三类条件
		* 数值的比较
		* 字符串的比较
		* 文件的比较

	
	# 不能在test命令中使用浮点数

	# 三种写法
		test expression
		[ expression ]
		[[ expression ]]
		
		* 三种写法是等价的, 但是第三种支持正则, 其他的不支持
		* 注意[] 和表达式之间有空格
	
	# test命令内部的>和<，必须用引号引起来（或者是用反斜杠转义）。否则，它们会被 shell 解释为重定向操作符。

	# 使用否定操作符!时，最好用圆括号确定转义的范围
		if [ ! \( $INT -ge $MIN_VAL -a $INT -le $MAX_VAL \) ]; then
			echo "$INT is outside $MIN_VAL to $MAX_VAL."
		else
			echo "$INT is in range."
		fi

		* test命令内部使用的圆括号，必须使用引号或者转义，否则会被 Bash 解释
	


----------------------------
if-then 的高级特性			|
----------------------------
	# 用于数学表达式的双括号
		* 表达式
			(( expression ))
		
		* 除了test运算可以使用的运算符以为,在(())里面还可以使用的运算符
			val++				后增
			varl--				后减	
			++val				先增
			--val				先减
			!					求反
			~					位反
			**					幂运算
			<<					左位移
			>>					右位移
			&					位移与
			|					位移或	
			&&					逻辑和
			||					逻辑或
	
		*  双括号中的><运算不需要转义
			val=10
			if (( $val ** 2 > 90 )); then
				(( val1=$val ** 2 ))	# 双括号赋值
				echo "the square of ${val} is ${val1}"
			fi
		
		* 不仅可以在if语句中用双括号命令,也可以在脚本的普通命令中使用双括号来赋值
			val=10
			(( val1=$val ** 2 ))
			echo ${val1}
		
		* 这种特性就是,可以在条件里面使用各种表达式,不是所有的shell都支持这种双括号要注意

	# 用于高级字符串处理功能的双方括号
		* 表达式
			[[ expression ]]
		
		* expression 使用 test命令中采用的标准字符串比较
		* 还提供了另一个特性:模式匹配
			reg="r*"	# 正则表达式
			if [[ ${USER} == ${reg} ]] ; then
				echo "Hello ${user}"
			fi
		
		
		* 这种特性就是可以在[[]]里面使用正则表达式
		* 不是所有的shell都支持双方括号

	
	# 正则
		[[ string1 =~ regex ]]

		INT=-5
		if [[ "$INT" =~ ^-?[0-9]+$ ]]; then
		  echo "INT is an integer."
		  exit 0
		else
		  echo "INT is not an integer." >&2
		  exit 1
		fi

----------------------------
case						|
----------------------------
	# 语法(玄学的语法)
		case variable in 
		pattern | pattern) 
			command;;
		pattern) 
			command;;
		*) 
			default command;;
		esac

		variable:表示变量
		pattern:表示一个只,可以有多个,使用|分割
		command:如果值匹配上了就执行的命令
		default command:都没匹配上时执行的语句
	
		
		* 代码块中,最后一行代码末尾需要添加两个分号
		* demo
			val="c"
			case ${val} in
			"a") 
				echo "匹配到了"
				echo "a";;
			"b" | "c") 
				echo "匹配到了"
				echo "b or c";;
			*) 
				echo "都不是";;
			esac 

	# case的匹配模式可以使用各种通配符，下面是一些例子。
			a)：			匹配a。
			a|b)：			匹配a或b。
			[[:alpha:]])：	匹配单个字母。
			???)：			匹配3个字符的单词。
			*.txt)：		匹配.txt结尾。
			*)：			匹配任意输入，通过作为case结构的最后一个模式。
		
	# Bash 4.0之前，case结构只能匹配一个条件，然后就会退出case结构。
		* Bash 4.0之后，允许匹配多个条件，这时可以用 ;;& 终止每个条件块

			read -n 1 -p "Type a character > "
			echo
			case $REPLY in
			  [[:upper:]])    echo "'$REPLY' is upper case." ;;&
			  [[:lower:]])    echo "'$REPLY' is lower case." ;;&
			  [[:alpha:]])    echo "'$REPLY' is alphabetic." ;;&
			  [[:digit:]])    echo "'$REPLY' is a digit." ;;&
			  [[:graph:]])    echo "'$REPLY' is a visible character." ;;&
			  [[:punct:]])    echo "'$REPLY' is a punctuation symbol." ;;&
			  [[:space:]])    echo "'$REPLY' is a whitespace character." ;;&
			  [[:xdigit:]])   echo "'$REPLY' is a hexadecimal digit." ;;&
			esac

		* 条件语句结尾添加了;;&以后，在匹配一个条件之后，并没有退出case结构，而是继续判断下一个条件。


----------------------------
for							|
----------------------------
	# 语法
		for var in list
		do
			commands
		done

		var :临时的迭代变量
			* 它会一直存在(值是最后一次迭代值)直到脚本终止(js一样)
			* 当然,可以主动的修改这个值

		list :多个迭代的数据
			* 默认情况下使用以下字符作为分隔符:空格,制表符,换行

		commands: 每次迭代执行的代码
	
	# 也可以把 do 关键字放在第一行
		for var in list; do
			commands
		done
		
		——————————————————————————————————
		for var in 1 2 3 "Hello"; do
			echo "value=${var}"
		done

		value=1
		value=2
		value=3
		value=Hello
		——————————————————————————————————
	
	# list数据中有引号的问题
		for var in I don't know if this'll work; do
			echo "word:${var}"
		done

		word:I
		word:dont know if thisll	# 两个单引号之间的内容,当作一个内容输出了
		word:work

		* shell尝试把列表中的单引号内容看做是一份单独的数据

		* 可以给单引号添加转义字符
			for var in I don\'t know if this\'ll work; do
				echo "word:${var}"
			done
		
		* 也可以使用双引号来定义用到单引号的值
			for var in I "don't" know if "this'll" work; do
				echo "word:${var}"
			done

	# list数据中有空格的问题
		for var in Hello World; do
			echo "word:${var}"
		done
		word:Hello
		word:World	# 因为以空格分割,所以,会被拆分为2个词儿

		* 可以使用双引号把含有空格的内容括起来
			for var in "Hello World"; do
				echo "word:${var}"
			done
	
	# 当一个迭代项,两边都有"号时,shell并不会把"当作值的一部分(见上述案例)
		* 如果要输出",请使用转义
			for var in \"Hello\" \"World\"; do
				echo "word:${var}"
			done
	

	# 从变量读取列表
		list="KevinBlandy is a pythonista"
		list="${list} !"
		for var in $list; do
			echo "word:${var}"
		done

	# 从命令读取值
		file="demo.sh"
		for var in `cat ${file}`; do
			echo "word:${var}"
		done
	
	# 更改字段的分隔符
		* 默认使用的是:换行,空格,制表符
		* 在脚本中添加代码,指定唯一的默认分隔符
			IFS=$'\n'	# 指定分隔符为换行,那么空格和制表符不会起作用

		* 如果代码量很大,需要在第一个地方修改IFS值,然后忽略这次修改
		* 让脚本的其他地方继续使用IFS的默认值,可以使用一个比较安全简单的操作
			
			IFS_OLD=$IFS	# 先记录原始值
			IFS=$'\n'		# 修改分隔符
			IFS=$IFS_OLD	# 在完成迭代操作后,还原为原始值
		
		
		* 使用冒号(:)分割的值(比如:/etc/passwd 文件)
			IFS=:
			value="H:e:l:l:o"
			for var in ${value}; do
				echo "word:${var}"
			done
		
		* 使用多个IFS字符
			IFS=$'\n':;" #使用换行,冒号,分号,双引号 作为分隔符
		
	# 用通配符读取目录
		for file in /usr/local/*; do
			if [ -d ${file} ]; then
				echo "目录：${file}"
			else
				echo "文件：${file}"
			fi
		done

		* 如果不在末尾添加 *,那么路径就会被当作一个字符串
		* 也可以添加多个路径,合并为一个list进行迭代
			for file in /usr/local/* /usr/*; do
				if [ -d ${file} ]; then
					echo "目录：${file}"
				else
					echo "文件：${file}"
				fi
			done
		
		* 就算是目录/文件不存在,for也会尝试去处理列表中的内容
		* 所以在执行操作之前,尽量先判断一下目录/文件是否存在

----------------------------
C 风格的for					|
----------------------------
	# 基本语法
		for ((  var  ; condition ; process ))
		do
			cmd
		
		done

		* 简直就是模仿C打造的(会让不会C的shell开发者懵逼)
		* 变量的赋值可以有空格
		* 条件中的变量不以$开头
		* 迭代过程的算式没有用 expr 命令过程	

		for ((i = 0;i < 10 ; i++)); do	# 很显然,它也可以使用优雅的写法
			echo "${i}"
		done
	
	# 也可以使用多个变量
		for ((i = 0,x = 10 ;i < 10 ; i++, x--)); do
			echo "i=${i},x=${x}"
		done
	
	# 遍历数组
		arr=(9 8 7 6 5 4 3 2 1 0)
		len=${#arr[*]}
		for (( i = 0; i < ${len}; i++ )); do
			echo "[${i}] = ${arr[${i}]}"
		done
	

----------------------------
while						|
----------------------------
	# 语法
		while test
		do
			common
		done

		* test 和 if 的 test 一样,可以使用shell命令或者 test 命令进行条件测试

		val=5
		while (( $val > 0 )); do # 也可以使用这种更加优雅的语法和高级的特性
			echo "${val}"
			val=$[${val} - 1]
		done

	
	# while 允许在 while 语句行定义多个测试命令
		* 只有最后一个测试命令的退出状态码会决定是否要退出循环

		val=5
		while echo $val 
			[ $val -ge 0 ]		# 第二个条件不写在下一行,会被当做 echo 的参数打印的
		do
			echo "${val}"
			val=$[ ${val} - 1 ]
		done
		————————————————————————————
		5
		5
		4
		4
		3
		3
		2
		2
		1
		1
		0
		0
		-1
		————————————————————————————

----------------------------
until 命令					|
----------------------------
	# 和while的工作方式相反,只有条件不成立,才会执行,条件成立后退出循环
	# 语法
		until test
		do
			command
		done
		
	# demo
		until (( $val < 0 )) ; do	# 可以使用高级语法,和优雅的表达式
			echo "${val}"
			val=$[${val} - 1]
		done
	
	 # 它的条件也可以存在多个条件,但是只有最后一个命令成立时才提出循环
	


----------------------------
控制循环					|
----------------------------
	# 没啥好解释的
		break continue

	# break默认跳出当前循环,也可以给它指定一个参数,跳出第几层循环
		break val

		* val 默认是1,就是跳出当前的循环仅仅
		* 可以修改该值,就可以跳出n层循环(嵌套循环)
	
	# continue 也跟break一样,可以指定一个参数,用于终止第几层的这一次循环
		continue val

		* val默认是1,表示终止当前层次的这一次循环
	
----------------------------
处理循环的输出				|
----------------------------
	# 可以在done后添加一个重定向命令实现

		val=10
		for (( i = 1 ;i < ${val} ; i++)); do
			for (( y = 1; y <= ${i}; y ++ )); do
				sum=$[${i} * ${y}]
				echo -n "${y} x ${i} = ${sum}    "
			done
			echo ""
		done > 99.log

		* 循环内部的 echo 不会打印在屏幕,会被输出到 文件
	
	# 也可以把循环的结果,通过管道给另一个命令
		val=10
		for (( i = 1 ;i < ${val} ; i++)); do
			for (( y = 1; y <= ${i}; y ++ )); do
				sum=$[${i} * ${y}]
				echo -n "${y} x ${i} = ${sum}    "
			done
			echo ""
		done | sort


----------------------------
select
----------------------------
	# select结构主要用来生成简单的菜单。它的语法与for...in循环基本一致。
		select name [in list]; do
		  commands
		done
	
	# Bash 会对select依次进行下面的处理。
		1, select生成一个菜单，内容是列表list的每一项，并且每一项前面还有一个数字编号。
		2, Bash 提示用户选择一项，输入它的编号。
		3, 用户输入以后，Bash 会将该项的内容存在变量name，该项的编号存入环境变量REPLY。如果用户没有输入，就按回车键，Bash 会重新输出菜单，让用户选择。
		4, 执行命令体commands。
		5, 执行结束后，回到第一步，重复这个过程。




















