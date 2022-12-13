------------------------
sqlite					|
------------------------
	# 嵌入式数据库
	# 官网
		https://www.sqlite.org/index.html

	
	# 文档
		* 数据类型
			https://www.sqlite.org/datatype3.html
		
		* SQL语法
			https://www.sqlite.org/lang.html
		
		* 内置函数
			https://www.sqlite.org/lang_corefunc.html
		
		* Date 和 Time 相关的函数
			https://www.sqlite.org/lang_datefunc.html

------------------------
sqlite-window安装		|
------------------------
	# 下载文件,都解压到一个文件夹中
		sqlite-tools-win32-x86-3240000.zip
		sqlite-dll-win64-x64-3240000.zip
	
	# 最终目录文件
		sqlite3_analyzer.exe
		sqlite3.exe
		sqldiff.exe
		sqlite3.dll
		sqlite3.def

	# 可以考虑添加目录到环境变量

------------------------
sqlite-Linux安装		|
------------------------

------------------------
schematab 表
------------------------
	# 每个数据库都包含了一张: sqlite_schema 表
		* 也可能名称为：sqlite_master/sqlite_temp_schema/sqlite_temp_master

		CREATE TABLE sqlite_schema(
		  type text,
		  name text,
		  tbl_name text,
		  rootpage integer,
		  sql text
		);
		
		* type
			类型 'table'(普通表/虚拟表), 'index', 'view', 或 'trigger' ，具体取决于定义的对象类型。
		
		* name
			对象的名称
		
		* tbl_name
			* 对象所关联的表或视图的名称。
			* 对于一个表或视图，tbl_name列是name列的副本
			* 对于一个索引来说，tbl_name是被索引的表的名称。
			* 对于一个触发器，tbl_name列存储了导致触发器启动的表或视图的名称。
		
		* sql
			描述该对象的SQL文本