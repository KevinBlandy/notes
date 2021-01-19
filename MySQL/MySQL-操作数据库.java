-------------------
对数据库的操作		|
-------------------
	# 创建数据库
		create database [数据库名];
		* 字符集设定
			character set [字符集];
			charset [字符集];
		* 校对集,其实就是排序规则
			collate [校对集]
		* create database mydb charset utf8;

	# 设置当前服务器的字符集
		set names [字符集];
		* cmd连接的时候,使用的就是GKB编码,通过这个设置可以解决中文乱码

	# 查询所有数据库
		show databases;
	
	# 查询指定的数据库
		show databases like '[表达式]';
		* 会根据表达式去查询匹配的数据库
		* %:表示匹配多个字符
		* _:表示匹配单个字符
	
	# 查询数据库的创建语句
		show create database [数据库名称];
		* 查询出来的创建语句,其实跟自己写的不一样.因为系统执行的时候会对SQL语句进行优化
	
	# 更改数据库名字
		别看了,不建议修改

	# 修改字符集
		alter database [数据库名字] character set = utf8;
		alter database [数据库名字] charset utf8;
		* 等号其实不用写.

	# 修改校对集
		懒的写了,自己百度去

	# 删除数据库
		drop database [数据库名];
		* 执行删除之后,数据库就被干掉了
		* 在data目录中,对应的目录也被干掉了(递归删除)
	
	# 进入数据库
		use [数据库名];
	
	

