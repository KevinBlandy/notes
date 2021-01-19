------------------------
shelve					|
------------------------
	* pickle 的高级版
	* 可以为可以序列化的字典,并且具备字典(dict)的大多数API
	* 只有一个函数
		shelve.DbfilenameShelf open(filename, flag, protocol, writeback)

	* shelve.DbfilenameShelf
		* 一个类似于字典的数据类型
		* key只能是字符串(str)
		* value,是可以Python的任意数据类型
	
	* demo
		# 打开/创建文件
		var = shelve.open("E:\\shelve")
		
		# 写入数据
		var['func'] = out
		var['dic'] = {'name':'Kevin','age':23}
		var['list'] = ['1','2','3']
		
		# 读取数据
		var['func']
		var.get('func')

		# 删除数据
		del var['func']

		# 遍历数据
			 遍历所有数据
		for k,v in var.items():
			pass

------------------------
shelve-模块属性			|
------------------------

------------------------
shelve-模块函数			|
------------------------
	shelve.DbfilenameShelf open(filename, flag, protocol, writeback)
		* 文件不存在,会创建如下三个后缀名的文件
			1,dat
			2,bak
			3,dir

			
------------------------
shelve-shelve.DbfilenameShelf|
------------------------
	close()
		* 关闭资源

	get()

	clear()
		
	items()

	pop()

	popitem()

	sync()

	update()

	obj get(keys)
		* 根据key获取值
