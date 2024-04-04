------------------------------
mongodb						  |
------------------------------
	# 地址
		https://mongodb.com/
		https://www.mongodb.com/zh
		https://docs.mongodb.com/
		https://github.com/mongodb/mongo

		https://docs.mongodb.com/manual/reference/method/
		https://www.mongodb.com/zh-cn/docs/manual/ // 中文文档

		https://mongoing.com/
		https://www.mongodb.org.cn/

	
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

		|-mongos.exe.exe
			* 用于Windows平台的 MongoDB Shard（即）的构建。
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
				mongod.exe --dbpath=D:\mongodb-win32-x86_64-windows-7.0.7\data
			
			* 如果没有指定参数，则 mongod 会使用默认的数据目录 /data/db/（在Windows 系统中为当前卷的 \data\db\）。
			* '如果数据目录不存在或不可写，那么服务器端将无法启动。'
			* 因此在启动 MongoDB 之前，创建数据目录（如 mkdir -p /data/db/）并确保对该目录有写权限非常重要。


------------------------------
mongodb	- 客户端的使用		  |
------------------------------
	# 启动时连接到其他 mongo 服务
		mongo host:port/db
	
	# 启动后连接到其他 Mongo 服务
		// 创建服务连接
		conn = new Mongo('host:port')
		// 获取到指定的数据库
		db = conn.getBD('dbname')
	
	# 启动的时候,它会连接到默认的 test 数据库，并且把数据库的连接赋值给全局变量: db
	# Shell 中的常用指令
	
		db
			* 查看当前所在的数据库
		
		user db
			* 切换到指定的db,不存在会创建
		
		help
			* 查看帮助命令
			* 可以查看指定方法的签名，说明
			// [方法].help 返回方法原型和说明，以及官方文档的地址
			db.[collection].[function].help
			db.collection.updateOne(filter, update, options)		// 原型
				Updates a single document within the collection based on the filter.
			For more information on usage: https://docs.mongodb.com/manual/reference/method/db.collection.updateOne
		
		cls
			* 清屏
	
	# 启动参数
		--quiet
			* 执行js脚本文件，可以指定多个，依次执行
				mongo --quiet a.js b.js
		--nodb
			* 启动 mongo shell 时不连接任何 mongod。
	
	# 用户主目录下的 .mongorc.js 启动脚本
		* 此文件会在启动 shell 时自动运行。
		* 启动 shell 时指定 --norc 参数，则可以禁用对 .mongorc.js 文件的加载。
		


	# 可以使用的运行时函数
		load
		print

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


