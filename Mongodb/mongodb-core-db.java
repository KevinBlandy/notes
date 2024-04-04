----------------------
db
----------------------
	# 数据库
		* 数据库名称的长度限制为 64 字节，区分大小写。
	
	# 系统预定于的数据
		admin
			* root数据库, 存储的是用户和权限
			* 一些特殊的命令, 只能在这个库中执行
		
		local
			* 这个数据永远不会被复制, 可以用来存储限于本地单台服务器的任何集合
		
		config
			* 当Mongo用于分片的时候, config数据库在内部使用, 用于保存分片的信息

----------------------
db - 相关
----------------------
	db
		* 查看当前 db 指向，即当前数据库。

	use [name]
		* 创建或者切换到指定名称的数据库
		* 此时db对象, 指向新的数据库

		* 此时创建的数据库是在内存中, 通过 show dbs 不可见
		* 只有当该 db 中起码有1个 document/collection 后, 才会落盘存储

	show dbs
		* 查看所有的db
	
	db.dropDatabase()
		* 删除当前的数据库
	
	db.getCollection('[collection name]')
		* 获取到指定名称的集合，和 db.[collection] 一样。
		* 它的作用是获取那些包含了关键字名称的集合
			db.version					// 返回的是版本号函数
			db.getCollection('version')	// 返回的是 version 集合
		
	
