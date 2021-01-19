----------------------
生成器					|
----------------------
	* 对于列表,元组,集合,字典的快捷生成方式

	* 语法有三种
		[变量 for 变量 in 迭代元素 if 判断语句]					# <class 'list'>
		(变量 for 变量 in 迭代元素 if 判断语句)					# <class 'generator'>
			* '它是生成器,并非是list,但是可以当作生成器用'
			* '使用它创建集合/列表/元组,必须使用工厂函数'
		{变量1,变量2 for 变量1,变量2 in 迭代元素 if 判断语句}	# <class 'dict'>

	* if 判断语句非必须的

	* 生成器的类型
		* '[]生成器本质上就是一个集合-list'
		* '()生成器本质上是一个生成器-generator'
		* '{}生成器被指上是一个-dict'
		g1 = [x for x in range(10)]
		g2 = (x for x in range(10))
		g3 = {i:v for i,v in enumerate(['a','b','c','d'])}
		print(type(g1)) # <class 'list'>
		print(type(g2)) # <class 'generator'>
		print(type(g3)) # <class 'dict'>
	
	* ()[]与list的区别
		1,()本质是一个生成器,每次调用的时候才会创建值,'比较节约内存'
		2,[]本质就是一个数组,[]创建的时候,数据就被加载到内存

----------------------
列表生成				|
----------------------
	arr = [x for x in range(10)]					#[0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
		* '除了列表的[]生成方式,其他的所有生成方式都要使用到工厂函数来进行转换'
		* '[]这个直接就是个list,直接就是赋值... ...'

	arr = list((x for x in range(10) if x % 2 ==0))	#[0, 2, 4, 6, 8]


----------------------
元组生成				|
----------------------
	tup = tuple((x for x in range(10) if x % 2 == 0))
	tup = tuple([x for x in range(10) if x % 2 == 0])

----------------------
集合生成				|
----------------------
	hashSet = set([str(x) for x in range(100) if x % 2 == 0])
	hashSet = set((str(x) for x in range(100) if x % 2 == 0))
	
----------------------
字典生成				|
----------------------
	d = {'name':'KevinBlandy','age':23}
	dic = dict({k:v for k,v in d.items()})						#{'name': 'KevinBlandy', 'age': 23}

	arr = [tuple(['1','2']),tuple(['3','4']),tuple(['5','6'])]	#[('1','3'),('3','4'),('5','6')]

	dic = dict({key:value for key,value in arr})				#{'1': '2', '3': '4', '5': '6'}

		
	
----------------------
生成器					|
----------------------
	* generator
	* 其实就是把一个列表生成式的[]或{}改成: ()
	* '生成器属于迭代器'
	* 生成器有两种
		1,普通生成器
		2,函数生成器

	# 普通生成器
		it = (x for x in range(100000)) # <class 'generator'>
	
	* 多次全局函数 next(),来获取下一个迭代值
		value = next(it)
		* 没有更多的元素时，抛出 StopIteration 的错误
		* next(it,default)也可以指定一个默认的参数,在没有更多元素的时候返回默认值,不会抛出异常
		* next() 的参数必须是迭代器类型(Iterator),不然报错:TypeError: 'xxx' object is not an iterator
	
	* 也可以调用其本身的API:__next__() 来获取下一个元素
		it.__next__()
		* 当后面没有元素时抛出异常
		* '该方式是Python2的方法,在Python3中属于特殊方法,不建议使用'

	* 它也是可迭代元素,所以可以使用 for in 来迭代(不会报错)
		for x in it:
			print(x)
		
	
	# 函数生成器
		* 如果一个函数定义中包含 yield 关键字,那么这个函数执行后的的返回值就是一个生成器, generator (<class 'generator'>)
		* generator ,在每次迭代,遇到 yield 语句返回,再次迭代时从上次返回的 yield 语句处继续执行
		* yield 跟 return 是一样的,可以有返回值(没有返回值则为:None),可以被获取
		* next() 后没有 yield ,则抛出异常:StopIteration,next()函数每次迭代,返回 yield 的返回值
		* 也可以使用 for in 迭代 generator,不会抛出异常,循环中的临时变量,就是 yield 的返回值
		
			def odd():
				print('执行第一次')
				yield 1
				print('执行第二次')
				yield 2
				print('执行第三次')
				yield 3

			x = odd()
			next(x)		//执行第一次
			next(x)		//执行第二次
			next(x)		//执行第三次
		
		* 当 yield 函数体中有 return,遇到 return 后,迭代停止(next() 还会抛出异常),获取不到 return 的返回值
		* 函数生成器也可以用于生成 list/set
			def func(num):
				while num >= 0:
					yield num
					num -= 1
			arr = list(func(5))
			print(arr)	# [5, 4, 3, 2, 1, 0]
					
		* 可以使用<class 'generator'>的,send() API 来执行迭代,而且还可以通过把值传递给 yield 
		* 当执行到 yield 的时候,先返回,再把当前 send() 传递的数据赋值给 '上一个' yield 对应的变量,并且,本次迭代可以读写该值
		* send() 函数,必须传递上次迭代的 yield 值,而next() 只能传递一个(没有下一个的)默认值,同样的是,send() 也可以获取到 yield 返回的数据
		* 如果是第一次迭代,使用了 send(),必须传递 None 值,否则异常:TypeError: can't send non-None value to a just-started generator
		* send(),可以和 next() 一起进行迭代操作,第一次迭代,我们就可以使用 next() 来规避,send() 第一次执行,必须传递 None 的问题
		* next(),可以认为就是传递了 None 的send() => send(None) 
		* 当然,send()也可以用在普通的生成器上,然而并没有什么特别的功能(第一次迭代值,同样必须为 None)

			def func():
				print("第一次执行")
				arg = yield
				
				print("第二次执行,参数是:",arg)
				yield
				
			g = func()
			next(g)				#第一次执行   
				* 也可以使用 send(None) 来代替第一次的迭代
			g.send('哈哈')		#第二次执行,参数是: 哈哈
			
		




	

	

	

