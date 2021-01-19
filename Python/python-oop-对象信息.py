------------------------
python-实例				|
------------------------
	# 创建实例
		user = User()
		print(user)				<__main__.User object at 0x0000000001DEB240>
		print(type(user))		<class '__main__.User'>
		print(user.__class__)	<class '__main__.User'>

		* 创建实例,一定会调用 __new__ 后再调用 __init__
		* 当前类,没有,就会去父类中找
	
	# 实例的创建过程
		# TODO
	
	# 定义实例函数
		* 和普通的函数相比,在类中定义的函数只有一点不同,就是第一个参数永远是实例变量 self ,并且，调用时，不用传递该参数。
		* 除此之外，类的方法和普通函数没有什么区别，所以，你仍然可以用默认参数,可变参数,关键字参数和命名关键字参数
		class User(object):
			
			def __init__(self,name,age):
				self.name = name
				self.age = age
			
			def say(self):
				print("我的名字叫%s,今年%s岁了"%(self.name,self.age))
				
		user = User('Kevin',23)
		user.say()
	
	# 析构函数
		* 跟构造函数的作用相反,它是在实例释放/销毁(程序退出)的时候执行
		* 一般用于做一些清理的工作
		* 定义
			 def __del__(self):
				pass
		
		* 这个函数,我们自己没法调用,是由系统调用的,所以没法传递属性
		* 直接执行: del obj,也会执行到obj的析构函数(__del__(self))
	
	# 私有属性/函数
		* 可以把属性的名称前加上两个下划线__
		* 在Python中实例的变量名如果以 __ 开头,就变成了一个私有变量(private),只有内部可以访问,外部不能访问
			class User(object):
				def __init__(self,name,age):
					# 定义私有变量
					self.__name = name
					self.__age = age
				
				# 私有方法        
				def __he(self):
					return "私有方法"

				# 公共方法
				def say(self):
					# 调用私有方法
					self.__he()
					print("我的名字%s,年龄%s"%(self.__name,self.__age))
					
			user = User('KevinBlandy',23)
			user.say()

		* 需要注意的是，在Python中,变量名类似 __xxx__ 的,也就是以双下划线开头,并且以双下划线结尾的,是特殊变量,特殊变量是可以直接访问的,不是private变量
		* 所以,不能用__name__,__score__这样的变量名。

	# 一个陷阱
		class User(object):
			def __init__(self,name,age):
				self.__name = name
				self.__age= age
			
			def get_name(self):
				return self.__name
				
		user = User('KevinBlandy',23)
		user.__name = '新的名字'
		print(user.get_name())  # KevinBlandy
		print(user.__name)      # 新的名字
		print(user.get_name())  # KevinBlandy
		
		* 表面上看,外部代码"成功"地设置了__name变量,但实际上这个__name变量和class内部的__name变量不是一个变量！
		* 内部的 __name 变量已经被Python解释器自动改成了 _User__name(如果直接霸王硬上弓是可以,修改 _User__name属性的)
			user._User__name = "哈哈哈"
			user.get_name()	# 哈哈好
		* 外部代码给user新增了一个 __name 变量!

	# 属性函数(getter)
		@property
		* 使用该装饰器装饰的函数,属于实例的一个'属性'
		* 不能执行,不能与实例已有的属性重名
		* 直接调用,该函数的返回值就是属性值
		* 仅仅只能有一个参数,就是当前实例
			class A():
				def __init__(self):
					self.__name = 'Kevin'
					self.__age = 23
					self.__skills = ['Java','Python','JavaScript']
				@property
				def name(self):
					return self.__name
			a = A()
			print(a.name)   # Kevin
			print(a.__name) #'A' object has no attribute '__name'
		* 不能对属性函数进行重新的赋值
			a.name = '新的值'	# AttributeError: can't set attribute
		* 不能对函数对象进行删除操作
			del a.name # can't delete attribute
	
	# 属性函数(setter)
		@{getter}.setter
		* 这个装饰器装饰的方法,是专门用于给 @property 属性 进行赋值操作
		* 它的定义,必须在 {getter}的定义之后,且 getter 要跟函数名称一样
		* 不能执行,不能与实例已有的属性重名
		* 第一个参数永远是自己,第二个参数就是新值
			class A():
				def __init__(self):
					self.__name = 'Kevin'
				@property
				def name(self):
					return self.__name

				@name.setter
				def name(self,value):
					self.__name = value
				
			a = A()
			print(a.name)    # Kevin
			a.name = 'Litch' # 调用 @name.setter 进行赋值
			print(a.name)    # Litch

	# 属性函数(deleter)
		@{getter}.deleter
		* 该装饰器,装饰的方法,是专门用于 del @property 属性的
		* 它的定义,必须在 {getter}的定义之后,且 getter 要跟函数名称一样
		* 当执行 del obj.property,就会执行该方法
		* 不能执行,不能与实例已有的属性重名,仅仅只能有一个参数,就是当前实例
			class A():
				def __init__(self):
					self.__name = 'Kevin'
				@property
				def name(self):
					return self.__name

				@name.setter
				def name(self,value):
					self.__name = value
				
				@name.deleter
				def name(self):
					del self.__name
					
			a = A()
			print(a.name)       # Kevin
			a.name = 'Litch'    # 调用 @name.setter 进行赋值
			print(a.name)       # Litch
			del a.name          # 调用 @name.deleter 进行删除操作
			print(a.name)       # AttributeError: 'A' object has no attribute '_A__name'

	
	# 属性/方法操作
		user = User()
		1,添加/修改属性
			* 直接修改
				user.name = "KevinBlandy"
			* 使用全局函数修改
				setattr(obj, 'name', 19) 
		2,删除属性
			* 直接删除(属性/方法不存在,抛出异常)
				del obj.attr 
			* 使用全局函数删除
				delattr(obj,attr)
		3,判断属性是否存在
			* 使用全局函数判断
				hasattr(obj,attr)
		4,获取属性值
			getattr(obj, 'name')
				* 如果试图获取不存在的属性,会抛出AttributeError的错误
			getattr(obj, 'name', 404)
				* 如果属性不存在,返回默认值
		5,获取所有的属性
			* 使用全局函数,返回  list
				dir(obj)
	
	
	# 获取实例类类型
		type(obj)		
			* 通过全局函数
		obj.__class__
			* 通过对象的  __class__ 函数

	# 实例属性
		__class__
			* 该属性值返回当前对象的 class 
			* 跟 type(obj) 是一样的
		
		__module__
			* 当前对象在哪个模块
			* 如果当前模块就是 main 模块(脚本从当前py文件执行),则该值为: '__main__

		__dict__
			* 返回对象中的所有属性,仅仅是属性,不包含方法. 
			* key 是字符串,value 是值

	# 判断数据类型
		* 使用 type() 来判断,返回的就是数据类型(class)
		* 如果要在if语句中判断,就需要比较两个变量的type类型是否相同
			type(123) == type(456)		//true
			type(123) == int			//true
		
		* 判断基本数据类型可以直接写int,str等,但如果要判断一个对象是否是函数等非基本数据类型的变量,可以使用types模块中定义的常量：
			import types
			def fn():
				pass
			
			type(fn) == types.FunctionType							//函数
			type(abs) == types.BuiltinFunctionType					//系统预定义函数
			type(lambda x: x) == types.LambdaType					//lambda 表达式
			type((x for x in range(10))) == types.GeneratorType		//生成器

	# 对象的计算
		* ><=!=都可以直接计算对象
		* 判断对象是否相等
			1,如果未实现 __eq__(self,other) ,则判断内存地址
			2,如果实现了 __eq__(self,other) ,则根据该函数的返回值(bool)判断是否相等
	
	# 判断实例类型
		1,判断实例是否是指定类的直接实例,或者子类实例
			isinstance(obj,object)
			isinstance(obj,(a,b,c))

		2,判断实例是否是指定类的直接实例
			* 使用  type() 判断
				type(User()) == User		# True

			* 使用  __class__ 判断
				User().__class__ == User	# True
	
	# 装饰器
		* 实例方法,可以有装饰器
