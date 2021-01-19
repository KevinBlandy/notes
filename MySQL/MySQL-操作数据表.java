-------------------
对数据表的操作		|
-------------------
	# 新增数据表
		create tables if not exists [表名称](
			[字段名] [数据类型] (长度),
			...
			[字段名] [数据类型] (长度)
		)[表选项] [值];
		* 最后一行不要','号.
		* if not exists,该参数表示如果表不存在就创建,存在就无视
		* 设置字符集
			charset [字符集]
		* 设置引擎
			engine [引擎]
		* 执行这个必须先use [数据库名];,也就是先选择数据库
			其实也可以在建表的时候指定:create ... [数据库名].[表名]...;

		create table if not exists demo(
			id varchar (10),
			name varchar (10)
		) charset utf8 engine innodb;
		
		* 执行了创建表之后,会在该表所在的数据库所对应的文件夹下创建
		  对应表的结构的文件(跟存储引擎有关系)
		  frm文件	结构文件,是已经被编译的文件
	
	# 查看所有数据表
		show tables;
	
	# 查看指定表
		show tables like '[表达式]';
	
	# 查看表创建语句
		show create table [表名称];
	
	# 查看表结构
		desc [表名];
		describe [表名];
		show columns from [表名];
		
		+-------+-------------+------+-----+---------+-------+
		| Field | Type        | Null | Key | Default | Extra |
		+-------+-------------+------+-----+---------+-------+
		| id    | varchar(10) | YES  |     | NULL    |       |
		| name  | varchar(10) | YES  |     | NULL    |       |
		+-------+-------------+------+-----+---------+-------+
			Field	:字段名
			Type	:字段属性,数据类型,列类型
			Null	:是否非空
			Key		:是否是主键
			Default	:默认值
			Extra	:列属性,扩充,额外的描述不下了.就写到这里面
		
	# 修改数据表
		1,修改表本身的属性
			* alter table [表名] [表选项] [值];
			* 修改表名
				rename [旧表名] to [新表名];
			* 修改字符集
				alter table [表名] charset utf8;

		2,修改表中的字段
			* 新增字段
				* alter table [表名] add [字段名] [数据类型] [约束] [位置];
				alter table demo add newFiled varchar(10) after anyFiled;
				位置:字段名可以放在表中的任意位置
					first			:第一个位置
					after [字段名]	:在哪个字段之后
					* 默认为最后一个
			* 修改字段(通常是修改属性或者数据类型)
				* alter table [表名] modify [字段名] [数据类型] [属性] [位置];
				alter table demo modify id varchar(100) first;
			* 重命名字段
				* alter table [表名] change [旧字段名称] [新字段名称] [数据类型] [属性] [位置];
			* 删除字段
				* alter table [表名] drop [字段名];
				* 删除字段,会删除该字段的所有数据.且不可逆

	# 删除数据表
		drop table [表名1],[表名2],....[表名n];
		* 执行了删除之后,在对应的数据库的文件夹下.
		  表对应的文件,也会被删除掉.
		