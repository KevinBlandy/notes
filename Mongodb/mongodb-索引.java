---------------------
索引
---------------------
	# Mongo使用的索引
		B树
		地址位置空间索引
		文本索引
		哈希索引
		
	
	# 涵盖查询
		* 本质上跟mysql的“回表”是一样的，根据索引查询数据。刚好又只需要索引这个数据。那么效率就会跟高
			db.user.find({name: "1"}, {_id:0, name: 1}); // 根据索引name查询，并且只返回name字段

---------------------
索引的操作
---------------------
	# 索引的创建
		db.[collection].createIndex([keys], [options])
			
		
		* 基本的索引
			db.user.createIndex({'name': 1})  // 单个字段索引
			db.user.createIndex({'name': 1, 'phone': 1}) // 联合索引 

			{
				"createdCollectionAutomatically" : false,
				"numIndexesBefore" : 1,
				"numIndexesAfter" : 2,
				"ok" : 1
			}

		* 索引值。1:升序排序，-1:逆序排序。无所谓

	# options
		{
			background: <boolean>
				* 创建索引的过程，是在后台（前台执行会阻塞进程），默认 false

			unique: <boolean>
				* 是否添加唯一约束，默认 false

			name: <string>
				* 索引名称，默认通过连接索引的字段名和排序顺序生成一个索引名称。

			sparse: <boolean>
				* 对文档中不存在的字段数据不启用索引
				* 这个参数需要特别注意，如果设置为true的话，在索引字段中不会查询出不包含对应字段的文档.。默认值为 false.

			expireAfterSeconds: <boolean>
				* 指定一个以秒为单位的数值，完成 TTL设定，设定集合的生存时间。

			v: 
				* 索引引擎版本号。默认的索引版本取决于mongod创建索引时运行的版本。

			weights: <int>
				* 索引权重值，数值在 1 到 99,999 之间，表示该索引相对于其他索引字段的得分权重。
			
			default_language: <string>
				* 对于文本索引，该参数决定了停用词及词干和词器的规则的列表。 默认为英语
			
			language_override: <string>
				* 对于文本索引，该参数指定了包含在文档中的字段名，语言覆盖默认的language，默认值为 language.
		}


	# 查看集合索引
		* 索引信息
			db.[collection].getIndexes()
			[
				{
						"v" : 2,			// 索引引擎版本号
						"key" : {			// 索引列
								"_id" : 1
						},	
						"name" : "_id_",	// 索引名字
						"ns" : "test.user" // name space
				}
			]
		
		* 查看所有索引占用的大小
			db.[collection].totalIndexSize()
	
	# 删除索引
		* 删除指定索引
			db.[collection].dropIndex([indexName])

			db.user.dropIndex({'name': 1}); // 根据索引列，删除索引
			db.user.dropIndex('name_1');	// 根据索引名称，删除索引


		* 删除所有索引
			db.[collection].dropIndexes()
		

---------------------
索引 - 查看执行计划
---------------------
	# 通过 explain 函数来查看执行计划
		db.[collectin].find(...).explain([config]);;

		{
			"queryPlanner" : {
				"plannerVersion" : 1,
				"namespace" : "test.user",
				"indexFilterSet" : false,
				"parsedQuery" : {

				},
				"queryHash" : "8B3D4AB8",
				"planCacheKey" : "8B3D4AB8",
				"winningPlan" : {
					"stage" : "COLLSCAN",
					"direction" : "forward"
				},
				"rejectedPlans" : [ ]
			},
			"serverInfo" : {
				"host" : "SKY-20190107XTL",
				"port" : 27017,
				"version" : "4.2.6",
				"gitVersion" : "20364840b8f1af16917e4c23c1b5f5efd8b352f8"
			},
			"ok" : 1
		}
		
		queryPlanner.winningPlan.stage
			"COLLSCAN"	
				* 集合扫描

			"IDHACK"
				* 索引

---------------------
全文索引
---------------------
	