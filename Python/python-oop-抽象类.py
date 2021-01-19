-------------------------------
抽象类							|
-------------------------------
	* 抽象类的定义需要依赖  abc 模块
	* 定义抽象类(import abc)
		1,定义抽象类的元类, metaclass=abc.ABCMeta 
		2,在实例抽象方法上添加		: @abc.abstractmethod 
		3,在类抽象方法上添加		: @abc.abstractclassmethod
		4,在静态抽象方法上添加		: @abc.abstractstaticmethod
		5,在抽象 @property 上添加	: @abc.abstractproperty
		6,抽象方法的方法体一般是一个 '多行注释'
	
	* 抽象类如果存在 @abc.abstractmethod ..等注解标识的抽象方法,那么它不能实例化,会抛出异常
	* 抽象类,子类如果没有对抽象方法进行实现,那么子类一样无法实例化,会抛出异常
	* 抽象类可以有非抽象方法
	* Demo
		import abc
		class Foo(metaclass=abc.ABCMeta):  ##抽象类
			
			def f1(self):
				print('我不是抽象方法')
		 
			def f2(self):
				print('我调用了抽象方法')
				self.f3()
		 
			@abc.abstractmethod  ##抽象方法
			def f3(self):
				'''
					实例抽象方法
				'''
			@abc.abstractclassmethod
			def f4(cls):
				'''
					抽象类方法
				'''
			@abc.abstractstaticmethod
			def f5():
				'''
					抽象静态方法
				'''
			@abc.abstractproperty
			def abs_property(self):
				''''
					抽象的property
				'''
				
		class Bar(Foo):
			def f3(self):
				print('实例抽象方法实现:%s'%(self))
				
			
			@classmethod
			def f4(cls):
				print('抽象类方法实现:%s'%(cls))
			
			@staticmethod
			def f5():
				print('抽象静态方法实现')
			
			@property
			def abs_property(self):
				return '抽象property的实现'
				
		#f = Foo()   # Can't instantiate abstract class Foo with abstract methods f3
		b = Bar()
		b.f1()  # 我不是抽象方法
		b.f2()  # 我调用了抽象方法 \n 实例抽象方法实现:<__main__.Bar object at 0x0000000002958E10>
		b.f3()  # 实例抽象方法实现:<__main__.Bar object at 0x0000000002968E10>
		b.f4()  # 抽象类方法实现:<class '__main__.Bar'>
		b.f5()  # 抽象静态方法实现
		print(b.abs_property)   # 抽象property的实现




-------------------------------
抽象方法						|
-------------------------------
	* 其实关于抽象方法的重写,好像并不是那么的严格,与参数个数无关
	* '只要子类有与抽象方法同名的方法出现,就可以认为是对抽象方法重写'
	* 不管该实现方法是类方法,实例方法,静态方式,或者是属性方法(property)
	* 在覆写了父类抽象方法后,就需要子类遵循方法的规则
