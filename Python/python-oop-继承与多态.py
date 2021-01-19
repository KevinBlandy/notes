------------------------
python-继承				|
------------------------
	# 继承可以把父类的所有功能都直接拿过来，这样就不必重零做起，子类只需要新增自己特有的方法，也可以把父类不适合的方法覆盖重写。
	# 继承语法
		class User(object):
			pass
		
		class User(o1,o2):
			pass
		* object就表示你要继承的类
		* '在继承一个类时候,子类甚至都可以不用进行任何的定义,仅仅使用一个 pass关键字即可'
		* 类可以多继承
	
	
	
	# 判断继承
		isinstance(obj,Class)
			* 判断 obj是不是Class,或者其子类
		
	# 实例的私有属性不可以被继承

	# 父类的析构函数可以被继承,当然,也可以被覆写

	# 类属性,可以被子类,以及子类的实例继承,但是子类包括其实例不能进行覆写

	# 子类同名的属性/方法,就是覆写
	
	# 子类访问父类的成员
		* '这种方式,可以在多继承的时候,很自由的选择要访问哪个父类的成员'
		1,访问父类的构造函数
			* 跟函数一个德行,使用括号中的变量,去访问父类的构造函数
			* 有种JS的感觉,使用 call(this),去继承父类的属性
				class Persion():
					def __init__(self,name,age):
						self.name = name
						self.age = age
						
				class Boy(Persion):
					def __init__(self,sex,name,age):
						self.sex = sex
						# 使用括号中的变量,去访问父类的构造函数(__init__)
						Persion.__init__(self,name, age)
			* 如果父类的构造函数对实例进行了属性的初始化,子类实例化的时候,在构造函数中没有调用父类的构造函数,则父类的属性不会初始化
				class Persion():
					def __init__(self):
						self.name = "Kevin"
						
				class Boy(Persion):
					def __init__(self):
						# 并未执行父类的构造函数
						pass
				Boy().name # 'Boy' object has no attribute 'name'

		2,访问父类的函数
			* 使用类括号中的变量去访问父类的方法
			* 该方式同样适用于析构函数
				class Persion():
					def __init__(self):
						pass
					def test(self):
						print("我是父类")
						
				class Boy(Persion):
					def test(self):
						Persion.test(self)	# 执行父类的方法
						print("我是子类")	
				
				Boy().test() 
				# 我是父类
				# 我是子类
			
							
	
	# 使用super去访问父类的成员
		1,使用 super() 函数访问父类构造函数
			* super(当前类,当前类实例).__init__(..)
			class Persion():
				def __init__(self,name,age):
					self.name = name
					self.age = age

			class Boy(Persion):
				def __init__(self,sex,name,age):
					self.sex = sex
					# 使用super()访问父类构造函数
					super(Boy,self).__init__(name, age)
		
		2,使用 super() 访问父类函数
			* super(当前类,当前类实例).函数名()
			* 该方式同样适用于析构函数
			class Persion():
				def test(self):
					print("我是父类")
				
			class Boy(Persion):
				def test(self):
					# 使用 super() 访问父类函数
					super(Boy,self).test()
					print("%s我是子类"%(self.name))
		* 由于 当前实例已经由 super()函数传入,所以执行  __init__() 函数不用传递 self(调用父类 __new__ 函数除外,必须要传递 self)
	
	# 关于类属性:__mro__
		* 该值是一个 tuple,是由C3算法计算得出的结果
		* 里面定义了当前类的继承体系
		* 该体系,也是在多继承情况下,成员调用的的优先顺序
		* 也可以调用的类方法:mro(),来获取C3算法的结果,跟该属性值是一样的
		* 一般对象的 __mro__ 结构(object对象,就只有一个,就是自己)
			* 第一个永远是自己
			* 最后一个永远是object


	# 多继承的情况下
		* 类成员,构造函数,__new__,析构函数,super(),静态函数,静态变量
		* 他们都会按照当前类的: __mro__ /mro() 结果进行搜索,找到第一个就执行调用
	
	# 如果是继承了一些特殊的对象,那么就可以进行特殊的计算
		1,继承了 int 类,就可以进行 > < =  = - * / ...的计算
		2,继承了 bool ,就可以进行 if ...等关于 bool 的计算
		
		* 核心的原因就是因为,这些特殊类里面,包含了特殊操作会调用的方法
		* 子类继承后,也会有这些方法,我们可以对方法进行覆写,形成多态,于是就被可以进行特殊的计算

------------------------
python-多态				|
------------------------
	# 动态语言的鸭子类型特点决定了继承不像静态语言那样是必须的。
	
	