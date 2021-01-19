------------------------
configparser			|
------------------------
	* 操作配置文件的模块
	* 有点像 Java 中的, Properties<T>
	* 配置文件的模式
		# 注释信息
		[DEFAULT]
			key=value

		[模块名称]
		key=value

		[模块名称]
		key=value
		
		* 'DEFAULT' 是一个关键,特殊的模块,有专门的API处理
		* 'DEFAULT' 必须全部大写,一字不落
		* 'DEFAULT' 中定义的内容,在其他的模块,都会存在,当然,可以被其他的模块覆写

	* ConfigParser的结构
		# 有点像是大字典,嵌套小字典
		config['DEFAULT'] = {'name':'Kevin','age':23,'job':'Java'}
		config['kevin'] = {'name':'Kevin','age':23,'job':'Java'}
		config['licth'] = {'name':'Litch','age':24,'job':'Python'}
	
	* 可以使用字典那一套操作来操作ConfigParser
		1,使用 int 判断是否存在
		2,访问或者修改使用[]
	
	* 遍历
		for x in config:
			print(x)# x为cinfig中的模块名称
			for y in config[x]:# y 为每个模块中的 key
				print(y,config[x][y])
		

------------------------
configparser-内置属性	|
------------------------

------------------------
configparser-模块函数	|
------------------------
	configparser.ConfigParser ConfigParser()
		* 获取一个 ConfigParser 实例对象

------------------------
configparser.ConfigParser|
------------------------
	list read(file)
		* 加载指定的配置文件
		* 返回 list,也就是加载的文件列表

	write(file)
		* 把配置信息写入指定的文件
		* 参数是目标文件(可以使用open()函数返回):<class '_io.TextIOWrapper'>
		* demo
			[kevin]
			name = Kevin
			age = 23
			job = Java

			[licth]
			name = Litch
			age = 24
			job = Python
	
	list sections()
		* 获取配置中除了 'DEFAULT' 模块的所有的模块名称

	bool remove_section()
		* 删除指定的模块
		* 删除成功,返回 True
		* 模块名称不存在,返回 False
	
	bool remove_option(section,key)
		* 删除指定模块下的指定key
		* 删除成功,返回 True
		* 模块不存在,抛出异常,key不存在,返回 False
	
	bool has_section(section)
		* 判断是否有指定名称的模块

	bool has_option(section,key)
		* 判断是否有指定模块,以及该模块下是否有指定的key

	None set(section,key,value)
		* 在section中添加或者修改 key & value
		* 如果 section 不存在,抛出异常

	collections.OrderedDict defaults()
		* 返回 'DEFAULT' 默认模块的数据,是一个有序字典
		* demo
			[DEFAULT]
			name = default

			OrderedDict([('name', 'default')])


	
