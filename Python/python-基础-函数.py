
------------------------
函数-基础				|
------------------------
	* 函数(<class 'function'>)的结构
		def [函数名称]([参数列表]):
			[逻辑代码]
			[返回值]
	
	* python中函数不具备重载特性,后定义的同名函数会直接覆盖先前定义的函数

	
------------------------
函数-参数				|
------------------------
	1,必须参数
		* 执行调用的时候,一一对应,不能多,不能少
			def fun(var1,var):
				pass
		* 调用的时候,可以直接把集合/数组/元组,转换为对应的参数,在调用时,参数前面添加'*'符号
			def fun(a,b,c,d):
				print(a,b,c,d)	# 1 2 3 4
			fun(*[1,2,3,4])
				
	2,默认参数
		* 具备默认值的参数,执行时,该位置可以传递值,或者不传递
		* '默认参数,必须使用不可变参数'
		* 该类型参数,在必须参数后
			def fun(age,name="Kevin"):
				print(name)	# kevin
				print(age)	# 23
			fun(23)
						
		
	3,可变参数
		* 跟Java中的可变参数一样
		* 可以传递一个或者多个参数值
		* 定义位置在默认参数之后
		* 在函数的内部,实质上是一个 元组 (tuple)
			def fun(name,*hobys):
				print(name)			# Kevin
				print(hobys)		# ('Java', 'Linux', 'Python')
				print(type(hobys))	# <class 'tuple'>

			fun("Kevin",'Java','Linux','Python')

		* 调用的时候,可以直接把集合/数组/元组,转换为参数,在调用时,参数前面添加'*'符号
		* 函数内部仅仅获取的是一份拷贝,并非 dict 参数的引用
			def fun(name,*hobys):
				print(name)
				print(hobys)
				print(type(hobys))
			hobys = {'Java','Linux','Python'}
			fun("Kevin",*hobys)
		

	4,命名参数
		* 该方式可以限制参数的个数和名称
		* 该参数应该是在可变参数之后,使用'*'号分隔
		* 命名参数定义与调用的顺序可以不同
		* 可以指定默认值,如果未指定,调用时必须传递,否则异常
		* '默认值,必须使用不可变参数'
			def fun(*,name,age):
				pass
			fun(name="Kevin",ag=23)
			
		* 如果该参数前面还有其他参数,使用一个 '*',号分隔参数
			def fun(var,*,name="Kevin",age=35):
			fun()
		
		* 如果该参数前面,是'可变参数',则不需要使用 '*' 号分隔
			def fun(var,*hobys,name="kevin",age=35):
				pass
			fun('var',1,2,3,4,5,6,name='Litch',age=23)
			
		* 把已经存在的 dict 作为关键字参数传递,参数前面添加'**'符号
		* 函数内部仅仅获取的是一份拷贝,并非 dict 参数的引用
		* 作为参数的 dict 必须与命名参数的形参列表完全匹配,dict 中使用字符串表示参数名称
			def fun(*,hobby1,hobby2):
				print(hobby1)
				print(hobby2)
			fun(**{'hobby1':'Java','hobby2':'Python'})
		
	5,关键字参数
		* 关键字参数允许你传入0个或任意个含参数名的参数
		* 这些关键字参数在函数内部自动组装为一个dict,实参的key,属于字符串类型
		* 参数名称重复,抛出异常
		* 该类型参数定义在命名参数之后
			def fun(**info):
				print(info)			# {'name': 'Kevin', 'age': 23}
				print(type(info))	# <class 'dict'>
			fun(name='Kevin',age=23)
			
		* 把已经存在的 dict 作为关键字参数传递,参数前面添加'**'符号
		* 函数内部仅仅获取的是一份拷贝,并非 dict 参数的引用
			def fun(**info):
				print(info)
				print(type(info))
			info = {'name':'Kevin','age':23}
			fun(**info)

		* '关键字参数和命名参数同时存在,执行时,优先匹配:命名参数,不论传递的顺序'
			def fun(*,name,**dic):
				print(name)	# Kevin
				print(dic)	# {'dic1': '1', 'dic2': '2'}

			fun(dic1='1',name="Kevin",dic2="2")
			fun(**{'dic1':'1','name':"Kevin",'dic2':"2"})
			
    
    6, 仅位置参数(3.8新特性)
        * python可以把位置参数(必须参数)当做命名关键字参数进行传递, 参数的顺序可改变, 不影响结果
            def foo(var1, var2):
                print(f"var1={var1}, var2={var2}") # var1=2, var2=1
            foo(var2=1, var1=2)
        
        * 从语法层面上禁止这样调用, 就可以使用仅限位置参数
            def foo(var1, var2, /):
                print(f"var1={var1}, var2={var2}") # var1=1, var2=2
            # foo(var2=1, var1=2) error
            foo(1, 2)
        
        * 就是在位置参数后面添加一根斜线: 
            def foo(x, y, /, *args, **kwargs)
	
	7, 指定参数类型
		* py是弱类型语言，一般不用指定类型

		def add (x: int, y: int):
			return x + y
		add ('2', '3')
	

	8, 泛型
		* 花里胡哨的东西
		
		def foo(arr: list[str]) -> str:
			retVal = ''
			for i in arr:
				retVal = retVal + i
			return retVal

		retVal = foo(['1', '2'])
		print(retVal) # 12

    
	* 函数参数的定义顺序
		1,必须参数
		2,默认参数
		3,可变长参数
		4,命名参数
		5,关键字参数

	* 对于任意函数，都可以通过类似 fun(*args, **kw)的形式调用它,无论它的参数是如何定义的.
	* *args的长度大于目标函数的必须参数,默认参数,变长参数只和...多余的部分被删掉
		def fun(a,b,c,d,e='默认的',*f):
			print(a,b,c,d,e,f)  # a b c d e ('f1', 'f2', 'f3')
		fun(*['a','b','c','d','e','f1','f2','f3'])


------------------------
函数-返回值				|
------------------------
	* 使用 return 返回结果,如果没有 return 语句,默认 return None
	* 函数遇到 return 关键字后,立即返回,不会执行剩下的代码

	* 可以 return 多个值,
	* 返回值其实是一个 tuple,在语法上,返回一个 tuple 可以省略括号,而多个变量可以同时接收一个tuple,按位置赋给对应的值
		def func():
			return 1,2,3,4,6
		result = func()
		print(result)       # (1, 2, 3, 4, 6)
		print(type(result)) # <class 'tuple'>

------------------------
函数-作用域				|
------------------------
	1,Python作用域分为四种情况(从小到大)
		* L,局部作用域,也就是函数中的变量
		* E,函数上层函数的作用域,发生在函数嵌套时
		* G,全局变量,也就是模块级别的变量
		* B,系统固定模块里面的变量
		
	2,关于函数的作用域
		* 依次向上检索,找到为止,找不到就抛出异常

	3,函数中定义全局变量
		* 使用 global 关键字,在函数内部声明的变量,就会被提升为全局变量
		* 只有函数被调用,该全局变量才会被载入内存
		* global 只能进行声明,不能同时赋值,也就说,要先声明,然后才可以赋值,得分两步操作
			def fun():
				global num 
				num = 1
			fun()
			print(num)	# 1
		
	4,函数中修改全局变量
		* 局部变量不能直接修改全局变量
		* 直接修改,没有任何效果
		* 在函数中声明的变量,全部是局部变量
			num = 5
			def func():
				num = 6
			func()
			print(num)	# 5

		* 使用 global 关键字先进行声明
		* 使用 global 关键字声明后,表示该变量不用局部创建,而是使用全局的,可以修改
		* 如果修改非 global 声明的同名全局变量,函数仅仅在在局部创建同名变量,而非修改全局
			num = 5
			def func():
				global num
				num += 1
			func()
			print(num)	# 6
	
	5,函数中修改外部函数变量
		* 其实就是修改嵌套作用域
		* 直接修改没有任何效果
			def func1():
				num = 4
				def func2():
					num = 5
				func2()
				return num
			print(func1())	# 4

		* 使用Python3提供的关键字:nonlocal
			def func1():
				num = 4
				def func2():
					nonlocal num
					num = 5
				func2()
				return num
			print(func1())	# 5
	
	6,函数中的变量,必须要先声明,后使用
		* 跟JS一个德行,也有一个变量提升的毛病
		num = 5
		def func():
			print(num)
			num = 4
			print(num)
		func()
		# UnboundLocalError: local variable 'num' referenced before assignment
		# 应该先进行变量的声明,是全局变量,还是局部变量: global num / num = [值]
	
	7,使用 global 关键字提升变量等级
		* 函数必须先要执行,才会把使用 global 声明的变量变为全局函数
		def func():
			global count
			count = '全局变量'

			global inner
			def inner():
				print('全局函数')
			
		func()
		print(count)    # 全局变量
		inner()         # 全局函数

------------------------
函数-lambda				|
------------------------	
	1,语法
		* lambda 参数:函数体
		* 它的数据类型仍然是:<class 'function'>
		* 参数是可选的

	2,细节
		* lambda 的主体是一个表达式,而不是一个代码块,仅仅能在lambda表达式中封装有限的逻辑进去
		* 函数体不能包含循环,可以包含判断
		* lambda 函数拥有自己的名字空间,不能访问全局变量,只能访问形参列表中的数据
		* 函数体不能使用 for/while/return/yield,可以使用条件判断
		* 如果返回的是元组,记得使用()号
		* lambda 表达式的结果就是一个匿名函数
	
			square = lambda x : x**2
				print(square(3))		# 9

			sum = lambda x, y : x + y
				 print(sum(2, 3))		# 5

		* 不带参数的 lambda 表达式
			func = lambda : True
			print(func())

------------------------
函数-闭包				|
------------------------
	* 如果在一个内部函数里,对在外部作用域(但不是在全局作用域)的变量进行引用,那么内部函数就被认为是闭包(closure).
		def funcX(x):
			def funcY(y):
				return x * y
			return funcY
	
		# 执行外层函数,获取内层函数,内存函数已经被外层函数的参数赋值
		tempFunc = FuncX(3)

		# 执行内存函数,传递参数,与外层函数的值进行计算
		result = tempFunc(5)

		print(result)            # 15
		
		# 简单操作
		result = FuncX(3)(5)
		print(result)           # 15

------------------------
函数-递归				|
------------------------
	* 写个代码就好了,天下编程语言都会递归
	def func(x):
		if(x == 1):
			return 1
		return x * func(x - 1)
	print(func(5))          #120


------------------------
函数-装饰器				|
------------------------
	* 在代码运行期间动态增加功能的方式，称之为'装饰器'-Decorator
	* 跟JAVA的动态代理有点像,而且用到了闭包
	* 简单增强
		# 定义增强,参数就是需要被增强的目标函数
		def log(func):
			# 定义增强函数,使用万金油参数
			def wrapper(*args, **kw):
				# 增强处..............
				print('call %s():' % func.__name__)
				 # 在增强函数中调用目标函数并且返回其执行结果,使用万金油参数
				return func(*args, **kw)
			# 返回增强函数
			return wrapper


		# 在目标函数上声明增强函数
		@log
		def now():
			print('2015-3-25')

		# 调用目标函数,就会执行增强
		now();

	* 也可以直接执行增强函数,获得到增强后的方法
	* 因为 now 函数,被 @log 修饰,这里再次执行,会执行两次增强方法
		now = log(now);
		now()
	
	* 带参数增强
	* 其实就是在普通增强前面又套了一个函数

		def aopText(message):
			def aop(fun):
				def wrapper(*args,**kw):
					print('增强处,获取到Message' + message)
					return fun(*args,**kw)
				return wrapper
			return aop


		@aopText('3333333333333333')
		def nowText():
			print('2015-3-25')

		nowText()
		
		# 其实是下面这样的,先传递值,再返回函数增强的闭包
		nowText = aopText('execute')(nowText)
	
	* 解决函数签名的问题
		* 返回的那个 wrapper()函数名字就是'wrapper',所以,需要把原始函数的 __name__ 等属性复制到wrapper()函数中
		* 不然,有些依赖函数签名的代码执行就会出错。
		* 涉及到 functools 的方法

		import functools
		def message(message):
			def aop(func):
				@functools.wraps(func)	# 解决函数签名的问题
				def wapper(*args,**kw):
					print("aop=",message)
					func(*args,**kw)
				return wapper
			return aop

		@message("Hello")
		def func(name):
			print(name)

		print(func.__name__)	# func,如果没有使用@functools.wraps(func),该值为 wapper
	
	* =================	Demos	===========================
	* 普通装饰
		def aop(target):
			def wapper(*args,**kws):
				print("==== 装饰处 ====")
				return target(*args,**kws)
			return wapper
		@aop
		def func(name):
			print(name)

		func("你好")
		# ==== 装饰处 ====
		# 你好
	
	* 带参装饰
		def text(arg):
			def aop(func):
				def wapper(*args,**kws):
					print("==== 装饰处 ====")
					print("==== 参数:%s===="%(arg))
					func(*args,**kws)
				return wapper
			return aop

		@text('我就是参数')
		def func(name):
			print(name)
			
		func('Hello')
		# ==== 装饰处 ====
		# ==== 参数:我就是参数====
		# Hello

------------------------
函数-偏函数				|
------------------------
	* Python的 functools 模块提供了很多有用的功能,其中一个就是偏函数(Partial function)
	* functools.partial的作用就是,把一个函数的某些参数给固定住(也就是设置默认值),返回一个新的函数,调用这个新函数会更简单
	* 当函数的参数个数太多，需要简化时,使用 functools.partial 可以创建一个新的函数
	* 这个新函数可以固定住原函数的部分参数,从而在调用时更简单
	* 创建偏函数时,实际上可以接收 '函数对象','*args'和'**kw'这3个参数
		import functools
		def func(*,name,age,**infos):
			print(name)
			print(age)
			print(infos)
		fun1 = functools.partial(func, name='Kevin',info1='1',info2="2",info3="3")
		fun1(age=23)
		# Kevin
		# 23
		# {'info1': '1', 'info2': '2', 'info3': '3'}
	

