--------------------------------
contextlib						|
--------------------------------
	* with状态的上下文工具
	* 执行资源释放,一般依赖于 with 语句,或者是 try 语句的 finally 语句
	* 任何对象,只要正确实现了上下文管理,就可以用于 with 语句
	* 实现上下文管理是通过 __enter__ 和 __exit__ 这两个方法实现的
	* demo
		class Query(object):
    
			def __init__(self, name):
				self.name = name

			def __enter__(self):
				print('Begin')
				return self

			def __exit__(self, exc_type, exc_value, traceback):
				if exc_type:
					print('Error')
				else:
					print('End')

			def query(self):
				print('Query info about %s...' % self.name)

		with Query('Kevin') as q:
			q.query()
		# Begin
		# Query info about Kevin...
		# End

	* 编写 __enter__和__exit__ 仍然很繁,因此Python的标准库 contextlib 提供了更简单的写法
		from contextlib import contextmanager
		class Query(object):

			def __init__(self, name):
				self.name = name

			def query(self):
				print('Query info about %s...' % self.name)

		# 上下文管理器,@contextmanager 接受一个生成器
		@contextmanager
		def create_query(name):
			print('Begin')  # 执行之前执行
			q = Query(name) # 创建目标对象
			yield q         # 返回目标对象
			print('End')    # 执行之后执行
		
		# 在with语句中,执行上下文管理对象获取到,目标对象
		with create_query('Kevin') as q:
			q.query()       # 执行目标对象的函数
	
	* 很多时候，我们希望在某段代码执行前后自动执行特定代码,也可以用 @contextmanager 实现
		from contextlib import contextmanager
		@contextmanager
		def tag(name):
			print("<%s>" % name)
			yield
			print("</%s>" % name)

		with tag("h1"):
			print("hello")
			print("world")
	
	* @contextmanager 让我们通过编写generator来简化上下文管理。
	* @contextmanager 标识的生成器(generator)执行顺序
		1,先执行生成器中第一段代码,越到 yield 后返回,可以有返回值
		2,执行 with 语句中的代码,如果 yield 有返回值,可以使用 as 接收
		3,执行 yield 后面的代码
	
	* 如果一个对象没有实现上下文,我们就不能把它用于with语句
	* 这个时候,可以用 closing() 来把该对象变为上下文对象
	* closing() 也是一个经过 @contextmanager 装饰的generator,这个generator编写起来其实非常简单
		@contextmanager
		def closing(thing):
			try:
				yield thing
			finally:			
				thing.close()
	* closing() 源码
		class closing(AbstractContextManager):
			def __init__(self, thing):
					self.thing = thing
			def __enter__(self):
				return self.thing
			def __exit__(self, *exc_info):
				self.thing.close()

--------------------------------
contextlib						|
--------------------------------

--------------------------------
contextlib						|
--------------------------------