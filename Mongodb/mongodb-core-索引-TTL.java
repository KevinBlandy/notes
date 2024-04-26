----------------------
TTL 索引
----------------------
	# 为每一个文档设置一个超时时间。当一个文档达到其预设的过期时间之后就会被删除。

	# 创建 TTL 索引
		db.sessions.createIndex({"lastUpdated" : 1}, {"expireAfterSeconds" : 60*60*24})

		* 在 "lastUpdated" 字段上建立一个 TTL 索引。
		* 如果文档的 "lastUpdated" 字段存在并且它的值是日期类型，那么当服务器端时间比文档的 "lastUpdated" 字段的时间晚 "expireAfterSeconds" 秒时，文档就会被删除。

		* 可以通过将 "lastUpdated" 字段的值更新为过期续约（Session 超时）

		* MongoDB 每分钟扫描一次 TTL 索引，因此不应依赖于秒级的粒度。
		* 可以使用collMod 命令来修改 "expireAfterSeconds" 的值：TODO

		
		* 集合中可以有多个 TTL 索引。TTL 索引不能是复合索引，但是可以像 “普通” 索引一样用来优化排序和查询。
	
