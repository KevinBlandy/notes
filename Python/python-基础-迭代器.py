------------------------
迭代器					|
------------------------

	* 迭代器与可迭代数据
		1,Iterator,表示迭代器
		2,Iterable,表示可迭代

		from collections import Iterator,Iterable

		arr = [1,2,3]
		print(isinstance(arr, Iterator))# arr 不是迭代器 (False)
		print(isinstance(arr, Iterable))# arr 是可迭代对象 (True)

		it = iter(arr)
		print(isinstance(it, Iterator))# it 是迭代器(True)
		print(isinstance(it, Iterable))# it 是可迭代对象(False)


	* 可迭代数据
		str
		list
		set
		dict
		tuple
		生成器都是迭代器(迭代器不一定是生成器)
	
	* 获取迭代器
		1, 可迭代的数据类型的内部都会有一个方法:__iter__(),该方法返回的就是迭代器
			arr = [1,2,3]
			it = arr.__iter__()
			print(type(it))#<class 'list_iterator'>
			print(next(it))#1
			print(next(it))#2
			print(next(it))#3
			print(next(it))#StopIteration

		2, 也可以使用全局函数:iter(),来把对应的数据转换为迭代器(如果数据是不可迭代的,抛出异常)
			arr = [1,2,3]
			it = iter(arr)
			print(type(it))#<class 'list_iterator'>
			print(next(it))#1
			print(next(it))#2
			print(next(it))#3
			print(next(it))#StopIteration
	
	* 操作迭代器
		1,使用全局函数 next()操作迭代器,来获取下一个元素
			* 如果后面没有更多可迭代元素,抛出异常:#StopIteration
			next(it)
		
		2,使用 for in 来遍历迭代器
			* 没有更多元素,不会抛出异常
			* for 循环操作可迭代数据的时候,其实是进行了三步操作
				1,其实是先把可迭代元素通过 iter() 方法转换为迭代器
				2,不断使用 next() 去调用迭代器,获取到数据
				3,处理 StopIteration

			arr = [1,2,3]
			it = iter(arr)
			for x in it:
				print(x)

	* 总结
		1,可用于 for in 的都是可迭代的(Iterable)
		2,可用于 next() 的都是迭代器(Iterator)
		3,很多数据类型都是 可迭代的(Iterable),可以通过操作获取迭代器(Iterator)	
			* __iter__()
			* iter()
		
			

	