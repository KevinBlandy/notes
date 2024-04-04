------------------------------
collection
------------------------------
	# 集合的操作Api
		https://www.mongodb.com/zh-cn/docs/manual/reference/method/js-collection/
	
	# 集合
		* 名称不能以 'system.' 开头，不要包含保留字符，如：'$'.
		* 使用 '.' 字符分隔不同命名空间的子集合是一种组织集合的惯例，例如：blog.posts 和 blog.authors。


------------------------------
collection - 命令
------------------------------
	db.createCollection([collection], [config]) 
		* 根据配置创建指定名称的集合
		* conifg
			{
				capped: true （可选）
						* 如果为 true，则创建固定集合。固定集合是指有着固定大小的集合，当达到最大值时，它会自动覆盖最早的文档。
						* 当该值为 true 时，必须指定 size/max 参数。

				autoIndexId: true （可选）
						* 如为 true，自动在 _id 字段创建索引。默认为 false。

				size: 1024 （可选）
						* 为固定集合指定一个最大值，以千字节计（KB）。
						* 字节单位一定是2的整数次幂，如果设置的不是，它会自动偏移
						* 如果 capped 为 true，也需要指定该字段。

				max: 10
						* （可选）指定固定集合中包含文档的最大数量。
				
			}

			* 在size/max都设置了的时候，先判断size，再判断max，任何一个不满足，都会抛出异常
		
		* 只有当集合中起码写入了一个document, collection才会真正的创建, 落盘存储
	
	db.getCollection([collection])
		* 获取集合, 返回它的引用
		* 但会不会创建, 只有在插入了记录后才会被创建

	show tables
		* 查看当前db下的所有collection
		* 也可以使用
			show collections
		
	db.[collection].drop()
		* 删除指定的集合
	
	
	db.[collection].stats()
		* 查看集合状态
			{
			  ns: 'demo.user',			// 集合名称
			  size: 141,				// 文档占用空间大小
			  count: 3,					// 文档数量
			  avgObjSize: 47,			// 平均文档大小
			  storageSize: 36864,		// 存储空间，预先创建的文件大小，快写满了就扩容
			  freeStorageSize: 16384,
			  capped: false,
			  nindexes: 1,
			  indexBuilds: [],
			  totalIndexSize: 36864,
			  totalSize: 73728,
			  indexSizes: { _id_: 36864 },
			  scaleFactor: 1,
			  ok: 1,
			  '$clusterTime': {
				clusterTime: Timestamp({ t: 1629284670, i: 12 }),
				signature: {
				  hash: Binary(Buffer.from("bb3a5c1a16e2e2fa0353bcdcd743854473257c4f", "hex"), 0),
				  keyId: Long("6944398072461918210")
				}
			  },
			  operationTime: Timestamp({ t: 1629284670, i: 12 })
			}


db.collection.analyzeShardKey()
	* 计算用于求值分片键的指标。
db.collection.aggregate()
	* 提供对聚合管道
db.collection.bulkWrite()
	* 提供批量写入操作功能。
db.collection.configureQueryAnalyzer()
	* 为集合配置查询采样。
db.collection.count()
	* 封装 count，返回集合中或视图中文档的计数。
db.collection.countDocuments()
	* 使用 $sum 表达式封装 $group 聚合阶段，返回集合或视图中文档的计数。
db.collection.createIndex()
	* 在集合上构建索引。
db.collection.createIndexes()
	* 为集合构建一个或多个索引。
db.collection.dataSize()
	* 返回集合的大小。封装 collStats 的输出中的 size
db.collection.deleteOne()
	* 删除集合中的单个文档。
db.collection.deleteMany()
	* 删除集合中的多个文档。
db.collection.distinct()
	* 返回具有指定字段的不同值的文档数组。
db.collection.drop()
	* 从数据库中删除指定的集合。
db.collection.dropIndex()
删除集合的指定索引。
db.collection.dropIndexes()
删除集合上的所有索引。
db.collection.ensureIndex()
已删除。使用 db.collection.createIndex()
db.collection.estimatedDocumentCount()
封装 count，返回集合或视图中文档的大致计数。
db.collection.explain()
返回各种方法的查询执行信息。
db.collection.find()
对集合或视图执行查询，并返回游标对象。
db.collection.findAndModify()
以原子方式修改并返回单个文档。
db.collection.findOne()
执行查询并返回单个文档。
db.collection.findOneAndDelete()
查找并更新单个文档。
db.collection.findOneAndReplace()
查找并更新单个文档。
db.collection.findOneAndUpdate()
查找并更新单个文档。
db.collection.getIndexes()
返回说明集合上现有索引的一组文档。
db.collection.getShardDistribution()
对于分片集群中的集合，db.collection.getShardDistribution() 报告数据段分布的数据。
db.collection.getShardVersion()
分片集群的内部诊断方法。
db.collection.hideIndex()
在查询规划器中隐藏索引。
db.collection.insertOne()
在集合中插入新文档。
db.collection.insertMany()
在集合中插入多份新文档。
db.collection.isCapped()
报告集合是否为固定大小集合
db.collection.latencyStats()
返回集合的延迟统计信息。
db.collection.mapReduce()
执行 map-reduce 样式的数据聚合。
db.collection.reIndex()
重新构建集合上的所有现有索引。
db.collection.remove()
从集合删除文档。
db.collection.renameCollection()
更改集合的名称。
db.collection.replaceOne()
替换集合中的单个文档。
db.collection.stats()
报告集合的状态。提供 collStats 的封装器
db.collection.storageSize()
报告集合使用的总大小（以字节为单位）。提供 collStats 输出的 storageSize 字段的封装器。
db.collection.totalIndexSize()
报告集合上索引使用的总大小。提供 collStats 输出的 totalIndexSize 字段的封装器。
db.collection.totalSize()
报告集合的总大小，其中包括集合中所有文档和所有索引的大小。
db.collection.unhideIndex()
从查询规划器中取消隐藏索引。
db.collection.updateOne()
修改集合中的单个文档。
db.collection.updateMany()
修改集合中的多个文档。
db.collection.watch()
在集合上建立变更流。
db.collection.validate()
对集合执行诊断操作。