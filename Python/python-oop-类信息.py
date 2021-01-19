------------------------
python-类				|
------------------------
	# 定义类,通过 class 关键字
		class User(object):
			pass

		* object 表示继承哪个类？
		* 如果没有继承就写 object,或者直接为空 ,object 是所有类的父类(Java-Object)
		* 可以是多继承
	# __init__(self)
		* 它并不是创建函数的实例,而仅仅是对函数进行初始化
		* 创建实例的函数是  __new__(self)
		class User(object):
			def __init__(self,name,age):
				self.name = name
				self.age = age
			
			* __init__ 前后都是两个下划线,名称固定
			* __init__ 方法的第一个参数永远是 self,表示创建的实例本身
			* __init__ 方法内部,就可以把各种属性绑定到 self,因为 self 就指向创建的实例本身。

		user = User('Kevin',23)
			* 创建实例的时候,必须传递构造函数对应的参数个数,否则抛出异常

	# __new__(self) 
		* 执行一个类(创建类实例)的时候,会执行到类的 __call__ 方法,因为类也是实例对象
		* 类实例的 __call__  里面,就会执行 __new__ 来创建对象
		* 创建对象之后又调用了 __init__ ,传入对象来初始化函数
		* 实例创建过程中 __new__ 方法先于 __init__ 方法被调用,它才是正儿八经创建实例的函数
		* 它的第一个参数(self)类型为:type (就是当前类实例)
		* 如果不需要其它特殊的处理,可以使用object的 __new__ 方法来得到创建 self 的实例
			def __new__(self):
				# super(当前类,self).__new__(self) 也是一样的
				return object.__new__(self)
		* 如果当前类没有 __new__,就会去父类找(检索顺序还是会按照C3算法)
		* '说白了,不管怎么样,都要先执行第一个 __new__,后再执行 __init__,因为 __new__ 才会创建对象'
		* 也可以在当前类,去访问父类的 __new__
			class B():
				def __new__(self):
					print('父级 new 执行')
					return object.__new__(self)

			class A(B):
				def __new__(self):
					#return super(A,self).__new__(self)  也是一样
					return B.__new__(self)
				
				def __init__(self):
					print("构造函数执行")
				
			a = A() 
			# 父级 new 执行
			# 构造函数执行
	
	# 限制实例属性
		* 使用类属性: __slots__  来限制实例的属性,该属性是一个  tuple
		* 如果类定义了该属性,那么该类实例的属性,只能是 tuple 中定义的.
		* 也不能动态的添加属性
		* __slots__ 定义的属性仅对当前类实例起作用,对继承的子类是不起作用的
		* 在子类中也定义__slots__,这样,子类实例允许定义的属性就是自身的 __slots__ 加上父类的__slots__
		* demo
			class Obj():
				__slots__  = ('name','age','say')
				def __init__(self):
					self.name = 'Kevin'
					self.age = 23
					self.say = '1231'
					self.other=  '21'   # AttributeError: 'Obj' object has no attribute 'other'
				
				def func(self):
					pass
			obj = Obj()


	# 定义类属性(静态属性)
		class User(object):
			desc = "User 的性别"
		
		* 当我们定义了一个类属性后,这个属性虽然归类所有,但类的所有实例都可以访问到
		* 子类/实例的同名属性,会覆盖
		* 当你删除实例属性后,再使用相同的名称,访问到的将是类属性
		* 类属性,可以被子类/子类实例继承,他们都可以进行访问,但是对同名属性就会覆盖
	
	# 定义类函数(静态函数)
		@staticmethod 装饰器
			@staticmethod
			class User(object):
				@staticmethod
				def test():
					pass

			* 使用 @staticmethod 装饰器装饰的函数,就是类函数(静态函数),可以直接类名调用
			* 可以被子类/子类实例调用

		 @classmethod 装饰器
			@classmethod
			class User(objec):
				@staticmethod
				def test(self):
					print(self)
			* 该装饰器装饰的方法,表示该方法是一个类方法
			* 可以被子类/子类实例调用
			* '而且第一个参数必须存在,且它就是当前类'
	
	# 类类型
		* 类(class),本身也是一种实例对象,需要用 class 关键字标识的对象
		* '正是因为 类,也是一种对象.所以它也有对象的一些属性(参考:python-oop-对象信息.py)'
		* 使用 type() 操作类的时候,返回:<class 'type'>
			class A():
				pass
			type(A)# <class 'type'>
		* 类,本身就是 type 的一个实例对象
		* 使用 type() 创建一个类,其实就是动态的创建了一个 type 的实例对象
			def func(self):
				print("你好%s"%(self.name))
				
			def __init__(self):
				self.name = "Kevin"
			
			# 执行了 type(),创建了一个类实例
			Foo = type('Foo',(object,),{'method':func,'__init__':__init__})
			foo = Foo()
			foo.method()	# 你好Kevin
		* type() 语法
			type('类名称',(继承类1,继承类2),{'属性名称':'属性值'})
		* str 是用来创建字符串对象的类,int 是用来创建整数对象的类...(Python里面也是万物皆对象)
		* type 就是创建类对象的类,因为Python解释器遇到class定义时,仅仅是扫描一下class定义的语法,然后调用type()函数创建出class.
		* 通过 type() 函数创建的类和直接写class是完全一样的,
		* 可以通过检查 __class__ 属性来看到这一点
			int.__class__		#<class 'type'>
			str.__class__		#<class 'type'>
			age = 23
			print(age.__class__)            # <class 'int'>
			print(age.__class__.__class__)  # <class 'type'>
	
	# __metaclass__
		* 元类
		* 我们已经知道了类,本身也是一种实例,由元类创建,也就是说类,就是元类的实例
		* 该值决定了当前'类实例'(不是类的实例),由谁来创建,也就是说可以控制类实例的创建过程
		* 可以把该值设置为:type 的派生类,或者任何使用到type或者子类化type的东东都可以(metaclass是类的模板，所以必须从 type 类型派生)
		* '反正意思就是执行 __metaclass__ 属性(属性.__new__()),返回类实例'
		* 类实例的创建过程
			1, 创建一个类实例的时候,Python会在当前类的定义中寻找 __metaclass__ 属性,如果找到了.Python就会用它来创建类实例
			2, 如果Python没有找到 __metaclass__,它会继续在父类中寻找__metaclass__属性,并尝试做和前面同样的操作(创建类实例)
			3, 如果Python在任何父类中都找不到__metaclass__,它就会在模块层次(模块变量)中去寻找__metaclass__,并尝试做同样的操作(创建类实例)
			4, 如果还是找不到__metaclass__,Python就会用内置的type来创建这个类对象
	
	# 自定义元类
		1,自定义类,继承于 type,或者 type 的子类
			class ListMetaclass(type):
				# cls,目标class
				# name,类名称
				# bases,父类tuple
				# attrs,属性dict
				def __new__(cls, name, bases, attrs):
					# 给该类,添加一个方法 add
					attrs['add'] = lambda self, value: self.append(value)
					return type.__new__(cls, name, bases, attrs)
					# return type(name,bases,attrs)									也一样
					# return super(ListMetaclass,cls).__new__(cls,name,bases,attrs)		也一样
		2,自定义类
			* 继承自 list,就有了 list 的所有方法
			* 使用 metaclass 关键字,指定元类
				class MyList(list,metaclass=ListMetaclass):
					pass
			* 创建实例后,可以调用 add 方法,而其他的 list 实例没有
				arr = MyList()
				arr.add('4')
				print(arr)		# [4]

	# 类实例的创建过程
		user = User()
		1,User(),就会执行类(User)实例的 __call__ (因为User本身也是个实例)
		2,__call__ 中就会调用 __new__,创建类的实例对象
		3, 创建对象后,__call__ 就调用 __init__ ,初始化对象

		
	# 类方法
		mro()
			* 该函数的返回值跟 __mro__ 类属性的返回值是一样的(是由C3算法计算得出的结果)
			* 不一样的是函数返回的是 list,属性,返回的是 tuple
		
	# 特殊类属性
		__mro__
			* 该属性值是一个 tuple,是由C3算法计算得出的结果
			* 里面定义了当前类的继承体系
			* 也可以直接调用类的: mro() 方法,返回 list,一样的
			* 该体系,也是在多继承情况下,成员调用的的优先顺序
				1,第一个永远是自己
				-1,最后一个永远是object
		__doc__
			* 类的描述信息
				class A():
					''' 这个是就是类的描述信息  '''
				print(A.__doc__)# 这个是就是类的描述信息  
		
		__dict__
			* 返回类中的所有类属性/方法(包括实例方法)是一个 dict,key 是字符串,value 是值
	

	# 特殊类函数总结
		__call__(self)
			* 实例对象被执行调用
		__new__(self) 
			* 创建实例对象
		__init__(self)
			* 初始化实例对象
		__del__(self)
			* 实例对象被回收之前调用
		__str__(self)
			* 被打印/str()的时候调用,打印该方法返回值
		__repr__(self)
			* 执行 repr(),传递当前对象的时候,就会打印该对象的此方法返回值
		__getitem__(self,key)
		__setitem__(self,key,value)
		__delitem__(self,key)
			* 以上几个,把对象当字典使用时调用
		
		__getattribute__(self,item)
		__setattr__(self, key, value)
		__delattr__(self, item)
			* 以上函数,在对对象进行属性操作的时候调用

		__add__(self,other)
			* 执行俩对象相加的时候执行的方法
		__lt__(self,other)
			* 如果 this < other ,返回 True
		__le__(self,other)
			* 如果 this <= other,返回 True
		__eq__(self,other)
			* 如果 this == other,返回 True
			* 一旦实现了该方法,那么该对象就不是可 hash 运算的对象了
		__ne__(self,other)
			* 如果 this != other,返回 True
		__ge__(self,other)
			* 如果 this >= other,返回 True
		__gt__(self,other)
			* 如果 this > other ,返回 True
		__len__(self)
			* 执行 len(obj),返回该函数值
		__iter__(self)
			* 对实例进行迭代(for/next())操作的时候,执行该函数,返回迭代器
			* 或者对实例进行 iter(obj),操作的时候,也会返回该迭代器
		__reversed__(self)
			* 执行 reversed(obj),的时候,执行该函数
		__contains__(self,key)
			* 执行 contains(key),的时候,执行该函数,返回 bool 值
		__copy__(self)
			* 执行 copy(obj)的时候,执行该函数,返回值
		__format__(self.format_spec)
			* 执行 "{}".format(obj)...提供format支持
		__hash__(self)
			* 执行 hash(obj),时,返回该函数的值,根Java中的 HashCode 一样
		__bool__(self)
			* bool(obj) ,或者对实例进行 if 判断计算的时候,根据该方法返回值确定 True/False
		__abs__(self)
			* 执行 abs(obj) 时候,返回该函数值
		__int__(self)
			* 执行 int(obj) 时候,返回该函数值
		__float__(self)
			* 执行 float(obj) 时,返回该函数值

		__enter__(self)
		__exit__(self, exc_type, exc_value, traceback)
			* 以上俩,用于 with 语句

		__index__(self)
			... ...
		'太TM多了...只要是操作符,操作对象,对象都有对应的函数进行处理'
		
		
	# 类装饰器
		* 把一个类作为函数的增强
		class Foo():
			# 在初始化的时候保存被增强函数
			def __init__(self,func):
				self.func = func

			# 被增强函数执行的时候,会执行__call__
			def __call__(self, *args, **kwargs):
				print('类装饰器前置增强')
				# 执行被增强函数
				result = self.func()
				print('类装饰器后置增强')
				return result

		# test函数相当于指向了用Foo创建出来的实例对象
		@Foo
		def test():
			print('hahah')
			return 'ok'

		# 当在使用test()进行调用时,就相当于让这个对象()，因此会调用这个对象的__call__方法
		print(test())
		print(test)
		'''
		类装饰器前置增强
		hahah
		类装饰器后置增强
		ok
		<__main__.Foo object at 0x000000000296BBE0>		# 直接打印函数,这个函数就是就是Foo的实例对象 isinstance(test,Foo) ==> True
		'''

			