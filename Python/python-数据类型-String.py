------------------
String				|
------------------
	# 可迭代的元素
	# 定义可以使用单引号,或者是双引号
		name = 'kevin'
		name = "kevin"

	# 可以使用转义符号
		name = "\"kevin\""
	
	# 也可以使用Unicode编码
		name =  '\u4e2d\u6587';

	# 可以使用 + / +=操作符号进行拼接
		* 直接相加的拼接,其实效率不高,更好的方法是使用 join 函数
			s1 = "Kevin"
			print(''.join([s1,"哈哈哈"]))	//Kevin哈哈哈
		
	# 使用 * 完成字符串的复制
		x  = "*" * 5 // *****

	# 字符串全部不转义,直接在前面加 r/R
		name = R"kevin \n";
	
	# 字符太多换行,可以使用 '''内容 ''' 来表示
		name = '''你好
			我叫余文朋'''
		* 该表达式也可以使用转义字符,也可以在面前加R/r,表示都不转义
		* 如果这种写法,不是赋值,那么它就是个注释
	
	# 二进制表示
		x =  b'字节'
	
	# 可以使用[下标]来访问字符串成员
		"Kevin"[0]
			* 越界会抛出异常
			* 只能读取,不能赋值/修改
		"Kevin"[0:5]
			* 可以使用该方式来获取指定区间的字符串
	
	# 判断字符串是否存在
		a = "Kevin"
		e in a		//true
		hhh in a	//false
	
------------------
String-格式化输出	|
------------------
	# 格式化
		* 跟C一样,也是使用 % 来实现
		* 占位符(常用)
			%d	整数
			%f	浮点数
			%s	字符串
			%x	十六进制整数

		* demo
			x = 5
			y = 5
			print("%d * %d = %d" %(x,y,x*y))		//5 * 5 = 25
		
		* demo
			name = input("输入名称 \n")
			age = int(input("输入age\n"))
			kill = input("请输入技能\n")
			value = """
			------------ Info of KevinBlandy ------------ 
			name    :%s
			age     :%d
			kill    :%s
			---------------------------------------------
			"""%(name,age,kill)
			print(value)

	# 使用字典格式
		* 占位符后,使用括号表示变量名称
		* %(name)s
		* %(age)d
		print("I'm %(name)s. I'm %(age)d year old" % {'name':'Vamei', 'age':99})
	
	# 使用f前缀来格式化
		name = 'KevinBlandy'
		age = 23
		r = f'Hello {name}, Im {age}'
		print(r)

		* 支持赋值符号 '!s' 和 '!f' ,制定输入结果格式



------------------
方法				|
------------------
	'查找'

	'替换'
	
	'判断'

	'剪切'



	capitalize();
		# 返回当前字符串的副本,首字母大写,不会修改当前字符串,返回新的
	
	center(width,char);
		# width 指定要返回的字符串长度
		# char 指定返回的字符串用啥填充
		# this 字符串放在中间
		# demo
			str = "Kevin"
			print(str.center(10,"-"))   //--Kevin---
	
	ljust(len,s)
		# 返回总长度为 len 个字符
		# this字符在左边,剩下的以s填充
		# demo
			s = "U"
			print(s.ljust(5,'1'))	//U1111

	rjust(len,s)
		# 同上, this 字符在右边

	count(char);
		# 返回指定 char 在当前字符串中出现的次数
		# 还可以指定 start,end
	
	encode(enc);
		# 对字符串进行解码,转换为字节数组类型
		# <class 'bytes'>
		# 以字符串形式指定编码格式
		# 命名参数
			encoding	指定编码格式,默认为UTF-8
	decode(s);
		# 把指定的字符串/字节数组,进行指定格式的编码
		# 命名参数
			encoding	指定编码格式,默认为UTF-8
	
	endswith(char,[start],[end])
		# 判断字符串是否以 char 结尾
		# char 也可以传递一个元组,只要是其中一个就好
		# 也可以通过 start和end来指定字符串的边界
	
	expandtabs(tabsize=8)
		# 就是把字符串中的 table 换行,转换为多少个空格
		# demo
			str = "1\t3"
			print(str.expandtabs(tabsize=10))	//1         3

	startswith(char,[start],[end])
		# 同上,判断是否是以 char 开头
	
	find(str,[start],[end])	
		# 返回 str 在字符串中第一次出现的位置,如果未找到返回 -1
		# 也可以通过 start和end来指定字符串的边界
	
	rfind(str,[start],[end])	
		# 同上,不过是逆序查找.返回的是最后一次出现的位置
	
	index(str,[start],[end])	
		# 从左往右开始检索,返回str第一次出现的位置,如果未找到.抛出异常

	rindex(str,[start],[end])
		# 同上,不过是逆序查找.返回的是最后一次出现的未知
	
	isalnum()
		# 阿拉伯数字或者字母
		# 字符串非空,且每个字符都是字母或者数字,返回 True
	
	isalpha()
		# 纯英文字符
		# 字符串非空,且每个字符都是字母,返回True
	
	isdecimal()
		#  字符串非空,且每个字符都是10进制,返回True
	
	isdigit()
		# 如果字符串只包含数字则返回 True 否则返回 False。
		# 小数也不行
	
	isidentifier()
		# 字符串非空,且是一个有效的标识符,返回True
		# 其实就是判断名称,是不是非法变量

	islower()
		# 字符串非空,且所有可以小写的字母,都是小写,返回 True,会无视前面的空格
	
	isupper();
		# 字符串非空,且所有可以大写的字母,都是大写,返回 True,会无视前面的空格
	
	isnumeric();
		# 检测字符串是否只由数字组成。这种方法是只针对unicode对象。

	isprintable();
		# 字符串非空,并且每个字符都是可以打印的,包括空格 返回True
	
	isspace()
		# 如果字符串非空,并且每个字符串都是空格,返回true
		# Table 也会被认为是空格
	
	title()
		# 把 this 字符串转换为 title,每个首字母都是大写

	istitle()
		# 判断每个单词的首字母是不是大写,会无视前面的空格
	
	join(targ)
		# 拼接字符串,使用当前字符串作为分隔符,连接targstr的每一个元素
		# targ也可以是数组/元组,但是里面的元素都必须是字符串
		# demo
			name = "Kevin"
			print(name.join("55"))      //5Kevin5
		
	
	replace(t,u,[n])
		# 使用 u 替换掉当前字符串中的 t,如果不指定 n,则替换所有
	
	partition(s)
		# 返回一个元组对象
		# [0],当前字符串在s字符串左边的子串儿
		# [1],当前字符串在s字符串右边的子串儿
		# 如果 s 不在this字符串中,则[0][1]都是空
		# 使用,rpartition 仅仅在this的右边进行分区

	lower();
		# 把字符串转换为小写
	
	upper();
		# 把字符串转换为大写
	
	swapcase();
		# 把大写的转换为小写,把小写的转换为大写
	
	split(t,n)
		# 使用 t 分隔字符串,t必须是字符串中的元素之一,否则,直接返回仅有一个元素(this)的元组
		# 如果没有指定n则分割所有,否则.仅仅分割出n个
		# 返回元组
	
	rsplit()
		# ..
	
	splitlines()
		# 以换行符来进行分隔
		# 跟 split("\n") 一样
		# 主要是 跨操作系统,不同系统的换行符不一样

	strip(s);
		# 去除两端空格/换行符,也可以通过s来指定要去除的字符
		# 反正就是仅仅留下字符
		# demo	
			s = "<[KevinBlandy]>"
			print(s.strip("[]<>{}()"))		//KevinBlandy
	
	rstrip(s);
		# 同上,仅仅去除右边的字符
	
	lstrip(s);
		# 同上,仅仅去除左边的字符


	zfill(s)
		# 返回当前字符串的副本
		# 如果长度不足,s,则在开头补0
	
	replace(o,n,count)
		# 把字符串中的所有o都替换为n
		# 可以用过 count 指定,仅仅替换多少个参数

	format(param)
		# 根据给定的参数格式化字符串
		# 根据位置参数替换
			s = "{0}你好{1}"
			s.format("哎哟","Kevin")		//哎哟你好Kevin

			* python 3.1支持不用写数字,占位符和位置,一一对其
			"{}{}{}".format("1","2","4")	//124
		
		# 根据字段名称格式化
			s = "你好{name},{name1}"
			s.format(name="Kevin",name1="hah")		//你好Kevin
	
		# 组合
			s = "你好{0},{name}"
			s.format("啊",name="123",)	//你好啊,123
			* 关键字,总是在位置参数之后
		
		# 使用List/元组格式化
			arr = ["a","b","c"]
			s = "{0[1]} -- {0[2]}"
			print(s.format(arr))			//b -- c
			* {0[1]} ,表示第0个模版位置,但是当前位置会使用数组来进行占位,[1]表示取数组的第1个元素
		
		# 使用dict进行格式化
			* 位置占位
				dic = {'name':'kevin','age':22}
				s = "{0[name]}今年{0[age]}岁了"
				print(s.format(dic))			//kevin今年22岁了
				* {0[name]},表示第0个模版位置,当前位置会使用字典来进行占位,[name],标识取出字典中的name属性

			* 命名占位
				s = "{name}今年{age}岁了"
				print(s.format(**{'name':'Kevin','age':22}))	//kevin今年22岁了
				* 这种方式更为快捷
				* 其实就是函数的关键字参数
				* 其实就是:根据字段名称格式化,只不过把 dict 先转换为了关键字参数
		
		# 使用其他的属性进行格式化
			s = "{0.pi}"
			s.format(math)
			* 不多说,一看就懂
		
	format_map()
		# 专门为 dict 格式化准备的
		# demo
			s = "你好{name}"
			print(s.format_map(dict({'name':'Kevin'})))    //你好Kevin
	
	translate()
		# ..
	
	removeprefix(str)
	removesuffix(str)
		# 移除指定的前缀/后缀
			print("HelloWorld".removeprefix("He")) # lloWorld
			print("HelloWorld".removesuffix("ld")) # HelloWor


------------------
静态方法			|
------------------
	maketrans()

------------------
Demo				|
------------------
# 字符串与二进制
	name = "余文朋"
	# 把字符串按照 utf-8 格式转换为字节数组
	enc = name.encode(encoding='utf_8')
	# 把字节数组,以 utf-8 格式转换为字符串
	dec = enc.decode(encoding="utf-8")
	print(dec)
