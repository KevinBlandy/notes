-----------------------
语句
-----------------------
	# while 循环
		while ([condition])
		{
			[statement]
		}
	
	# for 循环
		for ([init-statement]; [condition] ; [expression] )
		{
			[statement]
		}
		

		* 对于索引迭代，在判断语句中，推荐采用 !=, 而非其他语言风格的 <
		* 因为某些迭代器实现，可能未实现大小比较，但是基本上都实现了相等性比较
		
	# range 迭代
		
		for ([declaration] : [expression] )
		{
			[statement]
		}
	
		* expression 表示一个序列对象
		* declaration 表示序列中的每个元素
			
			* 支持使用引用来迭代，从而可以直接修改元素
				 for (auto &i : [expression]) {} // i 就是每个元素的别名
		

				
		
	
