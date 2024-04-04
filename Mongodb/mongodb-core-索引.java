---------------------------------
索引
---------------------------------
	# 创建索引
		// 单列索引
		db.foo.createIndex({"name": 1})
		// 复合索引，即包含了多个键的索引
		db.foo.createIndex({"name": 1, "age": -1})
		
		* 1 是升序索引，-1 是降序索引。
		* 如果集合特别大，索引创建可能会非常耗时，可以在另一个客户端中运行 db.currentOp() 或检查 mongod 的日志以查看索引创建的进度。

		* 如果再次尝试创建相同的索引，则不会执行任何操作。

		* 索引名称的默认形式是 keyname1dir1_keyname2_dir2..._keynameN_dirN，其中 keynameX 是索引的键，dirX 是索引的方向（1 或 -1）。
		* 如果索引包含两个以上的键，默认命名就很烦，可以通过 createIndex 选项的 name 来设置索引名称。
			db.soup.createIndex({"a" : 1, "b" : 1, "c" : 1, "z" : 1}, {"name" : "alphabet"})
		
	# 唯一索引

		db.users.createIndex({"age" : 1}, {"unique" : true})

		* 如果一个文档的键不存在，那么索引会将其作为 null 存储。
		* 但存在是 2 个 null 值，也会导致违反唯一约束。

		* 值大小超过 8KB 的 KEY 不会受到唯一索引的约束：比如，你可以插入多个相同的 8KB 字符串。
		* 在复合唯一索引中，单个键可以具有相同的值，但是索引项中所有键值的组合最多只能在索引中出现一次。

	# 部分索引
		* MongoDB 中的部分索引只会在数据的一个子集上创建。
		* "partialFilterExpression" 选项用于创建部分索引，它通过一个表达式来计算当前文档是否需要纳入索引。

		* 唯一索引会将 null 作为值，因此在多个文档都缺少键的情况下不能使用唯一索引。
		* 如果一个字段可能存在也可能不存在，但当其存在时必须是唯一的，那么可以将 "unique" 选项与 "partialFilterExpression" 选项组合在一起使用。

			// 如果有一个 email 字段是可选的，但是如果提供了这个字段，那么它的值就必须是唯一的。
			db.users.ensureIndex({"email" : 1}, {"unique" : true, "partialFilterExpression" : { email: { $exists: true } }})
		
		* 要创建非唯一的部分索引，只需去掉 "unique" 选项即可。

		* 使用部分索引，相同的查询可能返回不同的结果。
		* 假设有一个集合，其中大多数文档有 "x" 字段，但有一个文档没有。
			{ "_id" : 0 }				// 没有 x 字段
			{ "_id" : 1, "x" : 1 }
			{ "_id" : 2, "x" : 2 }
			{ "_id" : 3, "x" : 3 }

			
			// 在 "x" 上执行查询时，它会返回所有匹配的文档。
			db.foo.find({"x" : {"$ne" : 2}})
			{ "_id" : 0 }
			{ "_id" : 1, "x" : 1 }
			{ "_id" : 3, "x" : 3 }

			* 在 "x" 上创建一个部分索引，那么 "_id" : 0 的文档将不会被包含在索引中。
			* 因此，如果现在查询"x"，那么 MongoDB 将使用此索引并且不会返回 {"_id" : 0} 这个文档：

			// 再次执行相同查询，结果不包含 "_id" : 0
			db.foo.find({"x" : {"$ne" : 2}})
			{ "_id" : 1, "x" : 1 }
			{ "_id" : 3, "x" : 3 }
		
		* 如果需要返回那些缺少字段的文档，可以使用 hint 强制执行全表扫描。

	
	# 通常来说，如果 MongoDB 使用索引进行查询，那么它会按照索引顺序返回结果文档。

		* 如果对索引字段指定了排序顺序，而且和索引顺序不一样，MongoDB 需要在返回结果之前在内存中对结果进行排序。
		* 如果结果比较多，那么速度就会很慢或者根本不能工作。如果结果超过了 32MB，MongoDB 就会报错，拒绝对这么多数据进行排序。


		// 创建索引
		db.users.createIndex({"age" : 1, "username" : 1})
		
		// 索引结构如下，age 相同的情况下， username 按照升序排序
		[22, "name1"]
		[22, "name9"]
		[23, "name7"]
		
		// 查询
		// age 可以很轻松的根据索引定位到符合条件的记录
		// 但是最后需要根据 username 进行升序排序，这会导致 Mongo 在内存中对结果集进行排序
		db.users.find({"age" : {"$gte" : 21, "$lte" :30}}).sort({"username" : 1})

	# MongoDB 的索引选择机制
		
		* MongoDB 会为每个查询涉及到的索创建一个查询计划。
		* 然后在多个线程中并行运行查询计划，竞赛。
		* 在进行 N 轮竞赛后，耗时最短的查询计划胜出，以后只要是相同的查询，都会使用它。

		* 服务器端维护了查询计划的缓存。一个获胜的计划存储在缓存中，以备在将来用于进行该形状的查询。
		* 随着时间的推移以及集合和索引的变化，查询计划可能会从缓存中被淘汰。而 MongoDB 会再次进行尝试，以找到最适合当前集合和索引集的查询计划。
		* 其他会导致计划从缓存中被淘汰的事件有：重建特定的索引、添加或删除索引，或者显式清除计划缓存。此外，mongod 进程的重启也会导致查询计划缓存丢失。

	
	# 强制指定索引
		db.students.find({student_id:{$gt:500000}, class_id:54})
			.sort({student_id:1})
			.hint({class_id:1})				// 通过 hint 强制指定要使用的索引
			.explain("executionStats");
	
	# 索引的设计
		* 索引的设计需要考虑到等值过滤、多值过滤以及排序这 3 个因素。

			* 等值过滤的键应该在最前面
			* 用于排序的键应该在多值字段之前
			* 多值过滤的键应该在最后面

		* 在设计复合索引时，应该将等值过滤字段（$eq）排在多值过滤（$lt/$gt...）字段之前。
		* 宁愿多遍历几个索引来保证结果顺序，也不愿意少扫描索引来在内存排序（尽量避免内存排线）。

		* 只有基于多个查询条件进行排序时，索引方向才是重要的。如果只是基于一个键进行排序，那么MongoDB 可以简单地从相反方向读取索引。
	
	# 覆盖查询
		* 类似于关系型数据库中的 “回表”，如果只检索索引字段，那么就不需要再回去检索 “聚簇索引”。
	
	# 隐式索引
		* 也和关系型数据库的索引类似，可以通过 “前缀索引” 来应用索引。

			定义索引：{"age" : 1,"username": 1}				==>  可以走索引：{"age" : 1} 
			定义索引：{"a": 1, "b": 1, "c": 1 ..., "z": 1} ===>  可以走索引：{"a": 1}、{"a": 1, "b" : 1}、{"a": 1, "b": 1, "c": 1} 
	
	# 运算符和索引
		* 通常来说，取反的效率是比较低的。"$ne" 查询可以使用索引，但不是很有效。
		* 大多数使用 "$not" 的查询会退化为全表扫描 1。而 "$nin" 总是使用全表扫描。

		* MongoDB 在一次查询中仅能使用一个索引。
		* 也就是说，如果存在两个索引： {"x" : 1} 和 {"y" : 1} ，然后在 {"x" : 123, "y" : 456} 上进行查询时
		* MongoDB 会使用其中一个索引，而不是两个一起使用。
		* 使用 "$or" 时例外，每个 "$or" 子句都可以使用一个索引，因为实际上 "$or" 是执行两次查询然后将结果集合并。
		* 通常来说，执行两次查询再将结果合并的效率不如单次查询高，因此应该尽可能使用 "$in" 而不是 "$or"。
	
	# 索引对象

		* MongoDB 允许深入文档内部，对内嵌字段和数组创建索引。内嵌对象和数组字段可以和顶级字段一起在复合索引中使用。
		* 它们的大多数行为与“普通”索引字段是一致的，只是在某些方面比较特别。
			{
				"username" : "sid",
				"loc" : {
					"ip" : "1.2.3.4",
					"city" : "Springfield",
					"state" : "NY"
				}
			}
		
			db.users.createIndex({"loc.city" : 1})

			* 只有在进行与子文档字段顺序完全匹配的查询时，查询优化器才能使用 "loc" 上的索引。
			
				// ip、city、state 属性一个不少，顺序也一致
				db.users.find({"loc" : {"ip" : "123.456.789.000", "city" :"Shelbyville", "state" : "NY"}}})）

				// 查询无法使用索引
				db.users.find({"loc.city" : "Shelbyville"}) 
	
	# 索引数组
		* 也可以对数组创建索引，这样就能高效地查找特定的数组元素。
		* 对数组创建索引就是对数组中的每个元素创建索引，而不是对数组本身创建索引（整个数组无法作为一个实体创建索引）。
			
			{
				"title": "娃哈哈",
				"comments": [{
					"id": 1,
					"date": "123456"
				}]
			}

			db.blog.createIndex({"comments.date" : 1})

			* 对数组创建索引实际上就是对数组的每一个元素创建一个索引项，所以如果一篇文章有 20 条评论，那么它就会有 20 个索引项。
			* 这使得数组索引的代价比单值索引要高：对于单次的插入、更新或删除，每一个数组项可能都需要更新（也许会有上千个索引项）。

			* 数组元素上的索引并不包含任何位置（下标）信息：要查找特定位置的数组元素（如 "comments.4"），查询是无法使用索引的。
			* 然而对某个特定（下标）的数组项进行索引是可以的。
				
				// 这个索引只有在精确匹配第 11 个数组元素的时候才会起作用（数组索引从 0 开始）。
				db.blog.createIndex({"comments.10.votes": 1})
			
			* 索引项（联合索引）中只有一个字段是可以来自数组（避免在多 KEY 索引中的索引项数量爆炸式地增长）
				// 尝试给 x 和 y 创建索引会失败。因为这俩都是数组
				{"x" : [1, 2, 3], "y" : [4, 5, 6]}
				cannot index parallel arrays [y] [x]
				
				// 因为多个数组，会导致数组元素和数组元素之间的索引产生笛卡尔积，数量会爆炸式增长
				// {"x" : 1,"y" : 4}、{"x" : 1, "y" : 5}、{"x" : 1, "y" : 6}、{"x" : 2, "y" : 4}、{"x" : 2, "y" : 5}、{"x" : 2, "y" : 6}、{"x" : 3, "y" : 4}、{"x" : 3, "y" : 5} 和 {"x" : 3, "y" : 6}
			
			* 如果一个文档有被索引的数组字段，则该索引会立即被标记为 '多键索引'。
			* 可以从 explain 的输出中可以看出一个索引是否为多键索引："isMultikey": true
			* 一旦一个索引被标记为多键，就再也无法变成非多键索引了，即使在该字段中包含数组的所有文档都被删除了也一样。
			* 恢复非多键索引的唯一方法是删除并重新创建这个索引。

			* 多键索引可能会比非多键索引慢一些。
			* 可能会有许多索引项指向同一个文档，因此 MongoDB 在返回结果之前可能需要做一些删除重复数据的操作。
	
	# 索引基数
		* 指集合中某个字段有多少个不同的值，字段的基数越高，这个字段上的索引就越有用。
		* 例如，gender 字段一般只有三个：男、女、未知，基数就是 3，不适合做索引。
	
	# 查看创建的索引
		* 索引创建后，可以在 system.indexes 中看到它的元信息
		* 也可以执行 db.[collection].getIndexes() 来查看给定集合中所有索引的信息。
			[
				{
					"v" : 2,					// 版本控制
					"key" : {
						"_id" : 1				// 可用于 hint 及其他必须指定索引的地方
					},
					"name" : "_id_",			// 索引名称
					"ns" : "school.students"
				}
			]
	
	# 修改索引
		* 使用 dropIndex 命令根据索引名称来删除不再需要的索引

			db.people.dropIndex("x_1_y_1")
			{ "nIndexesWas" : 3, "ok" : 1 }

	
	# 完整的索引创建选项

		db.collection.createIndex(keys, options, commitQuorum)
		
		options:
			{
				unique: boolean,
				name: string,
				partialFilterExpression: document,
				sparse: boolean,
				expireAfterSeconds: integer,
				hidden: boolean,
				storageEngine: document
			}
		
		commitQuorum: integer / string
			

---------------------------------
索引 - 分析查询
---------------------------------
	db.foo.find({}).explain("executionStats");

	{
	  explainVersion: '1',
	  queryPlanner: {
		namespace: 'test.foo',
		indexFilterSet: false,
		parsedQuery: {},
		queryHash: '8880B5AF',
		planCacheKey: '8880B5AF',
		maxIndexedOrSolutionsReached: false,
		maxIndexedAndSolutionsReached: false,
		maxScansToExplodeReached: false,
		winningPlan: {		// 胜出的执行计划，一棵包含各个阶段的树
		  stage: "FETCH",	// 一个阶段（stage）可以有一个或多个输入阶段（inputStage），输入阶段向其父阶段提供文档或索引KEY。
		  inputStage: {		// 有多少个输入阶段，取决于它有多少个子阶段。
			stage: "IXSCAN",
			keyPattern: {
			  student_id: 1,
			  class_id: 1
		},
		rejectedPlans: []			// 拒绝的执行计划
	  },
	  executionStats: {			// 核心看这里，执行状态
		executionSuccess: true,
		nReturned: 103,				// 返回的结果数
		executionTimeMillis: 0,		// 执行查询所用的毫秒数
		totalKeysExamined: 0,		// 扫描的索引数量
		totalDocsExamined: 103,		// 扫描的文档总数
		executionStages: {
		  stage: 'COLLSCAN',		// COLLSCAN 表示进行了全表扫，没走索引
		  nReturned: 103,
		  executionTimeMillisEstimate: 0,
		  works: 104,
		  advanced: 103,
		  needTime: 0,
		  needYield: 0,				// 为了让写请求顺利进行，本次查询所让步（暂停）的次数。如果有写操作在等待执行，那么查询将定期释放它们的锁以允许写操作执行。在本次查询中，由于并没有写操作在等待，因此查询永远不会进行让步。
		  saveState: 0,
		  restoreState: 0,
		  isEOF: 1,
		  direction: 'forward',
		  docsExamined: 103
		}
	  },
	  command: {
		find: 'foo',
		filter: {},
		'$db': 'test'
	  },
	  serverInfo: {
		host: 'KevinBlandy',
		port: 27017,
		version: '7.0.8',
		gitVersion: 'c5d33e55ba38d98e2f48765ec4e55338d67a4a64'
	  },
	  serverParameters: {
		internalQueryFacetBufferSizeBytes: 104857600,
		internalQueryFacetMaxOutputDocSizeBytes: 104857600,
		internalLookupStageIntermediateDocumentMaxSizeBytes: 104857600,
		internalDocumentSourceGroupMaxMemoryBytes: 104857600,
		internalQueryMaxBlockingSortMemoryUsageBytes: 104857600,
		internalQueryProhibitBlockingMergeOnMongoS: 0,
		internalQueryMaxAddToSetBytes: 104857600,
		internalDocumentSourceSetWindowFieldsMaxMemoryBytes: 104857600,
		internalQueryFrameworkControl: 'trySbeRestricted'
	  },
	  ok: 1
	}