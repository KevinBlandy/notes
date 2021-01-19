----------------------------
python-条件判断				|
----------------------------
	
	# 全面的判断语句
		if [条件]:
			//TODO
		elif [条件]:
			//TODO
		elif [条件]:
			//TODO
		else:
			//TODO
	
	# 简单的判断
		if x :
			//TODO
		* 只要x是非零数值,非空字符串,非空list等，就判断为 True 否则为 False
	
	
	# boolean值的运算
		* 逻辑与
			& and
				* &		非短路运算
				* and	是短路运算(&&)
		
		* 逻辑非
			| or
				* |		非短路运算
				* or	短路运算 (||)

		not
			* 取反运算(!)
		
	* 只要是非零数值,非空字符串,非空list,非 None 等。都可以认为是 True,否则为False
	* if 是没有作用域的,条件判断代码块中声明的变量,都是属于模块级别的变量,并非是局部变量
	
----------------------------
python-循环					|
----------------------------
	# 对于List和Tple..等数据的遍历
		* 使用 for in 遍历
		* demo1
			names = ['Michael', 'Bob', 'Tracy']
			for name in names:
				print(name)

		* demo2
			sum = 0
			for x in [0,1,2,3,4,5,6,7,8,9]:
				sum+=x			//计算和
		
		* for else 循环

	# while 循环
		while [条件]:
			TODO
	
	# 使用 range 来代替 for 变量循环
		arrs = [0,1,2,3,4,5]
		for x in range(0,len(arrs)):
			print(arrs[x])
	
	# 多值循环
		for x, y in [(1, 1), (2, 4), (3, 9)]:
		    print(x, y)
		* 这种循环方式,还可以是更多值的
	
		arr = enumerate(['a','b','c'])
		for index,value in arr:
			print(index,value)
	
		dic = enumerate({'name':'KevinBlandy',66:99})
		for key,value in dic:
			print(key,value)
		
	
	# else 循环
		while [条件]:
			//TODO
		else:
			//TODO
		* for 循环也可以使用 else 循环语句
		* 循环体正常结束, else 会在循环完毕后执行
		* 循环体中出现 continue,else 会在循环完毕后执行
		* 循环体中出现 break ,else '不会执行'
	
	# for/while 循环体,必须是可迭代的
		1,其实是先把可迭代元素通过 iter() 方法转换为迭代器
		2,不断使用 next() 去调用迭代器,获取到数据
		3,处理 StopIteration

----------------------------
python-del					|
----------------------------
	# del 关键字的作用是,取消某个对象引用与对象的绑定,并删除对象的引用


----------------------------
python-assert				|
----------------------------
	# 断言
	# 语法
		assert [boolean 表达式],'异常信息'
		assert [boolean 表达式],['异常信息']
	
	# 如果 布尔表达式的结果为 false,就会产生一个 AssertionError 异常,参数就是异常信息

----------------------------
python-with					|
----------------------------
	# with 语句适用于对资源进行访问的场合,确保不管使用过程中是否发生异常都会执行必要的"释放资源"操作
	# 比如文件使用后自动关闭,线程中锁的自动获取和释放等
	# 有点像是Java中,JDK7的自动关闭接口
	# 语法
		with [打开资源操作] as var,[打开资源操作] as var
	# Demo
		with open('D:\\Main.java',encoding="UTF-8") as file:
			file.readline()
			file.read()
		* 当 with 代码块结束后,file会自动的关闭资源:file.close()


		with open('D:\\Main1.java',encoding="UTF-8") as file1,open('D:\\Main2.java',encoding="UTF-8") as file2:
			pass
		* with 语句,后面后跟上多个变量生成