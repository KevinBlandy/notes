----------------------------
itertools					|
----------------------------
	* 高级的迭代工具
	* itertools 模块提供的全部是处理迭代功能的函数,它们的返回值不是 list,而是 Iterator,只有用for循环迭代的时候才真正计算


----------------------------
itertools-属性				|
----------------------------


----------------------------
itertools-函数				|
----------------------------
	itertools.count count(1)
		* 返回count实例,是一个迭代器
		* 一个无限的迭代器,元素是自然数序列
		* 牛逼之处在于,这个迭代器会无限迭代下去,只能结束程序来结束它
	
	itertools.cycle cycle(itera)
		* 返回一个无限的迭代器
		* 会把传入的一个序列无限重复下去
	
	itertools.repeat repeat(item,count)
		* 把一个元素无限重复下去
		* 不过如果提供第二个参数就可以限定重复次数
	
	itertools.takewhile takewhile(func,it)
		* 根据 func 函数进行判断 it (可以是无限迭代器)里面的元素
		* 返回符合条件的
		* demo
			import itertools
			natuals = itertools.count(1)
			it = itertools.takewhile(lambda x : x < 10 ,natuals) 
			for x in it:
				print(x,end="") # 123456789
	
	itertools.chain chain(it,it..)	
		* 以把一组迭代对象串联起来,形成一个更大的迭代器
		* 返回多个迭代器组合的新迭代器
	
	itertools.groupby groupby(it)
		* 把迭代器中相邻的重复元素挑出来放在一起
		* 返回一个迭代器,每个元素都是一个元组 tuple ('')
		* demo
			import itertools
			var = itertools.groupby('AAABBBCCAAA')

			for key,item in var:
				print(key,list(item))
			# A ['A', 'A', 'A']
			# B ['B', 'B', 'B']
			# C ['C', 'C']
			# A ['A', 'A', 'A']
	
	itertools.permutations(it,count)
		* 对于,一个序列 (it),执行 count 次随意组合后返回
		* demo
			import itertools
			r = itertools.permutations(['a','b','c'],2)
			for x in r:
				print(x)
				'''
				('a', 'b')
				('a', 'c')
				('b', 'a')
				('b', 'c')
				('c', 'a')
				('c', 'b')
				'''

						

		