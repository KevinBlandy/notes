------------------------
操作表					|
------------------------
	# 创建
		CREATE TABLE user(
		   id				INT		PRIMARY	KEY     NOT NULL,
		   name				TEXT    NOT NULL,
		   age				INT     NOT NULL,
		   address			CHAR(50),
		   salary			REAL,
		   birthday			INTEGER,
		   create_date		TEXT
		);

		* 插入记录:insert into `user`(`id`,`name`,`age`,`address`,`salary`,`birthday`,`create_date`) values(1,'KevinBlandy',24,'重庆',5115415.5,1529570646967,'2018-06-21 16:44:19');

	# 查看表概要
		.schema [tables]
	
	
	# 查看所有表
		.tables
	
	# 删除表
		drop table [tablename]
