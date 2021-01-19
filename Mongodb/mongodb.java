------------------------------
mongodb						  |
------------------------------
	# 地址
		https://mongodb.com/
		https://www.mongodb.com/zh
		https://docs.mongodb.com/
		https://github.com/mongodb/mongo
	
	# 相关的教程
		https://mongoing.com/docs4.2
		https://www.runoob.com/mongodb/mongodb-tutorial.html
	
	# 版本号的含义: x.y.z
		y 奇数的时候, 表示是开发版, 偶数的时候, 表示为稳定版
		z 是修正版本号, 越大越好


------------------------------
mongodb	- 目录结构			  |
------------------------------
	bin
		|-mongo.exe
			* 一个js的Shell的客户端,可以使用js的语法去执行客户端的操作
			* 它还具备了js的标准库,不包含 dom 和 bom

			* 可以使用多行命令,它会检测你的js语法是否是完整的,没写完的情况下,你可以通过回车,继续在下一行写
			* 连续按三次回车,可以取消没有输入完成的命令

		|-mongod.exe
			* mongo服务端脚本

	log
		|-mongod.log

------------------------------
mongodb	- 服务启动			  |
------------------------------
	# 执行 mongod 脚本

	# 没有参数,默认会使用 /data/db 为数据目录 
		* Windows 使用当前磁盘根目录的 \data\db
		* 需要先创建,否则会启动失败
	
	# Mongo启动成功会在 27017 端口提供服务

	# 启动参数
		--noscripting
			* 禁止服务端执行js代码
		
		--config
			* 指定配置文件的地址
				mongod.exe --config=D:\mongodb\config\mongod.conf

		--dbpath
			* 指定数据存储目录


------------------------------
mongodb	- 客户端的使用		  |
------------------------------
	# 执行 mongo 脚本
	# 启动的时候,它会连接到默认的 test 数据库
	# 并且把数据库的连接赋值给全局变量: db
	
		db
			* 查看当前所在的数据库
		
		user db
			* 切换到指定的db,不存在会创建
	
	# 通过这个js shell 可以完成数据库的所有操作

	# 启动参数
		--quiet
			* 执行js脚本文件
		




------------------------------
mongodb	- 核心概念
------------------------------
	# 数据库 db
	# 集合	collection
	# 文档	document
	# ID	id



------------------------------
mongodb	- 征途
------------------------------
	# 复杂嵌套结构的检索，更新
	# 聚合检索
	# 事务
	# 索引
	# Java API
	# 备份和恢复
