----------------------------
全局函数					|
----------------------------

	* 官方文档
		https://docs.python.org/3/library/functions.html
	
	exit()
		* 退出程序,如果参数是字符串,则打印该字符串
		* 如果参数是 int,则表示退出原因,(0,表示正常退出)

	help()
		* 获取指定对象的帮助信息
		* 可以传递指定的模块,函数

	type(item);
		* 返回指定类型的数据格式

	dict locals()
		* 返回字典,就是当前环境(作用域)的所有变量名 & 值
	

	dict globals()
		* 返回当前py文件中所有的全局变量
		* key是变量的名称,value 是变量的数据/类型

	print();
		* 用于在屏幕上输出内容
			* print("你好");
		* 对于数据,可以使用逗号分隔N个,最终会进行拼接,遇到逗号","会输出一个空格
			* print(1,"world");		//1 world
		* 已知命名参数
			end
				* 在'每段儿'元素输出最后添加的数据,默认是换行符,可以修改该值来达到修改不换行,或者在元素之间添加分隔符
			flush
				* True/False....刷出缓冲
			file
				* 指定输出的文件(open(),对象),不会输入在屏幕

	input();
		* 用于用户输入,返回输入值,是字符串类型
		* 也可以给该方法传入字符串,作为提示信息
		* 该方法会导致线程阻塞
	
	bool callable()
		* 判断指定数据,是否是一个可调用的数据

	int ord();
		* 返回指定符号,表示的整数(ascii 码),字符转换为整数
		* Demo
			ord('b'); # 98

	str chr();
		* 返回指定整数,表示的符号(ascii 码),整数转换为字符
		* Demo
			chr(98)	# b

	len();
		* 返回指定数据的长度
		* 可以是字符串,数组....等等一切具备长度属性的数据,如果参数不具备长度属性,则抛出异常
		* 如果参数是字节,就是计算的字节数
			len(b'ABC');

	code compile()
		* 属于底层方法,用来编译Pythin源码,返回编译后的字节码对象
		* 参数
			字符串形式的源码
			错误的日志输出的文件
			执行方式(exec,eval...)
		* 直接把 源码(字符串),传递给 exec(),也可以执行 
		* Demo
			sourceCode = """
			for x in range(10):
				print(x)
			"""
			classic = compile(sourceCode,'err.log','exec')
			exec(classic)

	None exec(source)
		* 执行指定的Python代码
		* Demo
			sourceCode = """
			for x in range(10):
				print(x)
			"""
			exec(sourceCode)
	
	eval()
		* JavaScript你还记得吗?一样一样的
		* 只能用于简单计算的代码,如果包含了 执行语句,要使用  exec
		* demo
			dic = {'a':1,'b':'b'}
			dicStr = str(dic)
			dic = eval(dicStr)

		* 还可以用来计算
			eval("1+2")	# 3
	
	id();
		* 返回指定数据的唯一ID值
		* 其实就是内存地址,可以判断对象是否相等


	dir(obj)
		* 返回指定模块/对象的属性列表
		* 未指定参数,则返回 Python 内置属性列表
			['__annotations__', '__builtins__', '__cached__', '__doc__', '__file__', '__loader__', '__name__', '__package__', '__spec__']
		* 根据指定的属性,检索其关键字信息
			dirs = dir('__builtins__');
			['__add__', '__class__', '__contains__', '__delattr__', '__dir__', '__doc__', '__eq__', '__format__', '__ge__', '__getattribute__', '__getitem__', '__getnewargs__', '__gt__', '__hash__', '__init__', '__init_subclass__', '__iter__', '__le__', '__len__', '__lt__', '__mod__', '__mul__', '__ne__', '__new__', '__reduce__', '__reduce_ex__', '__repr__', '__rmod__', '__rmul__', '__setattr__', '__sizeof__', '__str__', '__subclasshook__', 'capitalize', 'casefold', 'center', 'count', 'encode', 'endswith', 'expandtabs', 'find', 'format', 'format_map', 'index', 'isalnum', 'isalpha', 'isdecimal', 'isdigit', 'isidentifier', 'islower', 'isnumeric', 'isprintable', 'isspace', 'istitle', 'isupper', 'join', 'ljust', 'lower', 'lstrip', 'maketrans', 'partition', 'replace', 'rfind', 'rindex', 'rjust', 'rpartition', 'rsplit', 'rstrip', 'split', 'splitlines', 'startswith', 'strip', 'swapcase', 'title', 'translate', 'upper', 'zfill']
	
	isinstance(item,class)
		* 判断 item 是否属于class类型
		* class,也可以替换为 tuple,表示多个类型,满足一个就返回 true
		* demo
			isinstance(5.6,tuple([int,str]))		//false

	next(it,default)
		* 操作迭代器,(就是内部有 __iter__() 方法的数据类型)
		* it为迭代器,每次执行该方法,都会返回it迭代器中的元素
		* 如果最后没有元素,会抛出异常
		* default,该参数为默认参数,当迭代器最后没有元素的时候,返回该值,不会抛出异常
		* next() 的参数必须是迭代器类型(Iterator),不然报错:TypeError: 'xxx' object is not an iterator

	int hash()
		* 返回指定变量的 hash 值

	round(num,leng)
		* 仅仅保留num的leng个小数位,会四舍五入

	slice slice(begin,end,spet)
		* 返回一个切片对象
		* 可以用于切片表达式
			'abcdefg'[slice(0,5,2)]
	
	zip zip(ser1,ser2)
		* 拉链...,返回 zip,可迭代数据
		* 把两个seri一一对应,最小长度依据最小的序列
		* Demo
			print(list(zip(['a','b','c'],[1,2,3,4])))
			# [('a', 1), ('b', 2), ('c', 3)]

	__import__(name)
		* 导入指定的模块,以字符串形似指定模块名称
		* 该方法是解释器使用的
		* 返回值就是模块的引用

----------------------------
工厂函数					|
----------------------------
	list();	
		* 把数据转换为List集合

	set();
		* 返回一个新的Set对象
		* 可以选择传递一个 List 作为初始值
		* 注意,不能包含 List 元素
	
	frozenset()
		* 返回一个不可变的set对象

	tuple();
		* 返回一个新的元组对象
		* 可以选择传递一个 List 作为初始值
	
	dict();
		* 返回一个空的字典对象

----------------------------
全局函数-转换相关			|
---------------------------
	str bin(x)
		* 返回整数x的二进制表示,如果是小数,抛出异常
		* print(bin(9))		//0b1001
	
	str hex(x)
		* 返回整数的16进制表示字符串,如果是小数抛出异常
	
	float();
		* 把数据转换为浮点数,不指定参数,返回 0.0
		* 参数是可以任意整数,浮点数以及对应的字符串
	
	str str()
		* 把数据转换为字符串
		* 关键字参数
			encoding
				* 如果参数是字符串的话,需要通过该关键字参数来指定字符编码
	
	repr(obj)
		* 把一个对象转换为字符串的形式
		* 注意只是显示用,有些对象转成字符串没有直接的意思
		* 如list,dict使用str()是无效的,但使用repr可以,这是为了看它们都有哪些值.为了显示之用
			
	bool bool()
		* 把数据转换为 boolean 类型
		* 其实就是判断数据是否是 True
	
	bytearray bytearray()
		* 把指定的数据转换为字节数组
		* 所有对 bytes 对象的操作也可以用在 bytearray 对象上
		* 可以使用下标标记给 bytearray 对象的某个字节赋值,并且,这个值必须是 0–255 之间的一个整数
		* 如果数据是字符串,则必须使用命名参数:encoding,指定字符编码
		* Demo
			arr = bytearray('abcd',encoding="UTF-8")
			print(arr)
			print(arr[0])

	int();
		* 把指定的数据转换为Number数据类型,默认基数是 10 进制
		* 允许字符串头尾带空格,但是不允许有小数点,只能是整数字符串,如果不是标准的数字类型字符串,则抛出异常
		* 如果是浮点数,则允许带小数点,int会直接保留整数,删除小数点以后的所有数据
		* 如果未传递参数,返回 0
		* 如果传递了两个参数,则第二个参数表示基数(2-36)
			int('A4',16)			//164		
			int (0xFF)				//255
	
	oct(x)
		* 返回指定整数的8进制表示字符串
	
	bytes()
		* 把数据转换为字节数组
		* 如果转换的是字符,则第二个参数应该指定字符编码
			bytes('余文朋','UTF-8')


----------------------------
全局函数-数学相关			|
----------------------------
	sum(iter)
	max(iter)
	min(iter)
		* 
	
	range(start,stop,[step]);
		* 创建算数级数序列的通用函数
		* start 指定开始默认0,stop 指定结束(不包含),step表示每次的跳跃的大小默认1
			x = list(range(0,101))
	
	abs(x)
		* 返回指定数据的绝对值
		* 如果参数非数字,抛出异常
	
	tuple divmod(x,y)
		* 返回一个数组
		* 第一个参数:x // y 的值
		* 第二个参数:x %  y 的值
	
	pow(x,y);
		* 返回 x 的 y 此幂
		* x ** y
	
	pow(x,y,z)
		* (x ** y) % z
	
	round(x,[n])
		* 返回浮点数x四舍五入后得到的整数
		* 如果有传递n,则表示仅仅保留几个小数

----------------------------
操作序列/迭代				|
----------------------------
	reversed(seri)
		* 把指定的数据反转

	enumerate(x,[start])
		* 对于一个可迭代的（iterable）/可遍历的对象（如列表、字符串）
		* enumerate将其组成一个索引序列,利用它可以同时获得索引和值
		* 参数是可迭代的,返回的组合就是 索引/值
		* 参数是字典,返回的组合就是		key/value
		* demo
			arr = [1,2,3,4,5,6,7,8,9];
			for x in enumerate(arr):
				print(x,end="")		//(0, 1)(1, 2)(2, 3)(3, 4)(4, 5)(5, 6)(6, 7)(7, 8)(8, 9)

			arr = enumerate(['a','b','c'])
			for index,value in arr:
				print(index,value)
		
			dic = enumerate({'name':'KevinBlandy',66:99})
			for key,value in dic:
				print(key,value)

	filter filter(fun,iter)
		* 过滤,fun可以是一个函数/lambda表达式,如果该参数为空,返回可迭代对象中所有不为False的元素
		* iter是一个可迭代的数据
		* 返回结果是一个filter类型(<class 'filter'>)的数据(迭代器),需要使用 转换函数进行转换
		* Demo
			arr = [1,2,3,4,5,6]
			result = filter(lambda x : x > 2,arr)
			print(list(result))				//[3, 4, 5, 6]

			def check(num):
				return num > 2
			print(list(filter(check,arr)))	//[3, 4, 5, 6]

	map(fun,iter)
		* 第一个参数fun是lambda/函数,第二个参数是一个可迭代数据
		* 会把可迭代数据中的所有数据,都传递给 fun 执行后返回结果,把所有的结果组合,进行返回
		* 返回结果是一个 map 类型(<class 'map'>)的数据(迭代器),
		* Demo
			arr = [1,2,3,4,5,6]
			result = map(lambda x : x + 1,arr)
			print(list(result))		//[2,3,4,5,6,7]

			def add(x):
				x += 1
				return x
			result = map(add,arr)
			print(list(result))     //[2, 3, 4, 5, 6, 7]
			
			list(map(str, [1, 2, 3, 4, 5, 6, 7, 8, 9]))		//把所有元素都转换为字符串

	reduce(fun,iter)
		* 第一个参数fun是lambda/函数,第二个参数是一个可迭代数据
		* 在Python3中,该函数需要引入才能使用 from functools import reduce
		* fun应该具备两个参数
		* 会首先把第0,1参数给 fun 进行运算,然再把上次运算结果,结果加上第三个3参数,给 fun 进行运算,类推下去....
		* Demo
			from functools import reduce
			arr = [1,2,3,4,5,6]
			result = reduce(lambda x,y : str(x) + str(y),arr)
			print(str(result))		//123456	

	list sorted(iter,*,key,reverse)
		* 排序,返回排序后的新结果
		* 具备命名参数函数
		* 命名参数
			key
				指定一个lambda/fun,会把每个元素都传递给它执行,根据其返回值来作为比较的依据

			reverse
				boolean值,是否要反转iter
		* Demo
			L = [('Bob', 75), ('Adam', 92), ('Bart', 66), ('Lisa', 88)]
			def comp (x):
				return x[-1]	# 根据每个元组的第二个字段进行排序
			result = sorted(L, key=comp)
			print(list(result))		
		
	bool all(x)
		* 参数是可迭代的
		* 如果所有成员都返回 True,则结果就是 true

	bool any(x)
		* 参数是可迭代的
		* 如果任意成员返回 True,则结果就是 true


	str ascii(var)
		* 返回指定数据的 ascii 编码
	