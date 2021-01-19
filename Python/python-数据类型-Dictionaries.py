----------------------------
python-Dictionaries			|
----------------------------
	# 字典,有点像是JAVA的map,或者是JS的JSON
	# key值不能重复,会产生覆盖的问题
	# key值的数据类型
		* 要保证hash的正确性，作为key的对象就不能变。
		* 运行作为Key的数据类型
			基本数据类型,frozenset,tuple 
		* 不允许作为Key的数据类型
			dict,list,set	
		
	# 如果访问的key不存在,会抛出异常
	# 定义
		test = {99:59,"Litch":55,"Rocco":[1,2,3]}
		test = dict({1:2,3:4})		
		test = dict([(1,2),(3,4)])
			* 函数也可以传递数组类型,要求每个元素具备两个元素,第一个作k,第二个做v
		test = dict(name='Kevin',age=23)
			* 可以使用命名参数(可以有N多个),来进行初始化定义

	# 设置元素
		test[55] = "哈哈哈"
	
	# 访问元素
		test['Kevin']
		test[99]
	
	# 判断元素是否存在
		[99] in test		//True
		['21'] in test		//False
	
	# 删除字典元素,如果key不存在,抛出异常
		del test['Litch']
	
	# 删除字典
		del test
	
	# 获取字段的元素长度
		len(test)
	

	# 使用 for in 迭代元素
		dic = {'name':'KevinBlandy',66:99}

		for key in dic:
			print(key)
			print(dic.get(key))
		
		for kv in dic.items():
			print(kv[0])		//key
			print(kv[1])		//value
		
		for key,value in dic.items():
			print(key)
			print(value)
		
		* items 遍历效率不高,建议用第一种
	
	# 字典内涵
		dic = {key:value for key,value in {'name':'Kevin','age':23}.items()}
	
	# 字典并集
		* 多个map的并集，返回新map，相同key，后面的覆盖前面的
			map1 = {'k1': 'v1'}
			map2 = {'k2': 'v2'}
			map3 = map1 | map2
			print(map3) # {'k1': 'v1', 'k2': 'v2'}
		
		* 2个map的并集，在第一个map上增加
			map1 = {'k1': 'v1'}
			map2 = {'k2': 'v2'}
			map1 |= map2
			print(map1) # {'k1': 'v1', 'k2': 'v2'}


			* |= 还可以用来合并，可迭代对象
				map1 = {'k1': 'v1'}
				it1 = ((i, i * i) for i in range(3))
				map1 |= it1
				print(map1) # {'k1': 'v1', 0: 0, 1: 1, 2: 4}
		


			
----------------------------
python-Dictionaries-方法	|
----------------------------

	get(key,[defaultValue])
		* 根据指定key获取数据
		* 如果key不存在,返回 None,如果指定了 defaultValue,那么在数据为 None 的时候返回该值
	
	setdefault(key, defaultValue)
		* 和get()类似, 但如果键不存在于字典中，将会添加键并将值设为default
		* 如果key存在,则不会进行任何操作
		* 该方法会返回 key 的 value

	pop(key,[delfaultValue])
		* 删除指定key的元素,并且返回 value
		* 如果数据不存在,抛出异常
		* 也可以指定默认值,如果key在字典中不存在,就会返回默认值
	
	popitem()
		* 返回并且移除任意的一个 item(元组),如果字典是空的,抛出异常

	clear();
		* 清空字典
	
	copy();
		* 返回一个浅复制
	
	items();
		* 返回一个可遍历的元组
		* 其实就是把每个 Entry 都转换为一个元组
		* demo
			for x in hashMap.items():
				key = x[0]
				value = x[1]
			
			for k,v in hashMap.items():
				key = k
				value = v

	keys();
		* 以列表返回所有的key
		* 返回的数据类型是 dict_keys,本身是可遍历数据类型
		* 可以转换为 list 后进行其他的操作
		* demo
			for x in hashMap.keys():
				key = x
				value = hashMap[key]
				
	values()
		* 以列表返回字典中的所有值
		* 返回的数据类型是 dict_values,本身是可遍历数据类型
		* 可以转换为 list 后进行其他的操作
	
	update(dict2)
		* 把字典dict2的键/值对更新到 this dict里

----------------------------
python-静态方法				|
----------------------------
	dict.fromkeys(keys,value);
		* 返回一个字典
		* keys 是所有的 key,value,是所有 key 的value
		* demo
			dic = dict.fromkeys([1,2,3],'value')
			print(dic)		//{1: 'value', 2: 'value', 3: 'value'}
		* 如果 value 是一个数组,那么任意成员的数组元素变化,都会更新到其他的 key
		* demo
			dic = dict.fromkeys([1,2,3],[1,2]);
			dic[1][0] = 5 
			print(dic)      //{1: [5, 2], 2: [5, 2], 3: [5, 2]}




