---------------------
全文搜索索引
---------------------
	# 全文搜索
		* 如果应用允许用户提交关键字查询，而这些查询与集合中的某些字段的文本相匹配，那么可以使用 text 索引。
		* text 索引可以快速搜索文本并提供对一些常见搜索引擎需求（比如适合于语言的分词）的支持。
		* text 索引需要一定数量的与被索引字段中单词成比例的键。因此，创建 text 索引可能会消耗大量的系统资源。



	# 创建全文索引

		db.articles.createIndex({
			"title": "text",			// 给 title 字段创建全文索引
			"body" : "text"				// 给 body 字段创建全文索引
		})
	
		
		* 索引创建中的字段的声明顺序并不重要。默认情况下，text 索引中的每个字段都会被同等对待。
		* 可以通过对每个字段指定权重来控制不同字段的相对重要性
			
			// "title" 字段与 "body" 字段的权重比为 3：2
			db.articles.createIndex({
				"title": "text",
				"body": "text"
			}, {"weights" : {
				"title" : 3,		// 为不同的字段设置权重比
				"body" : 2
			}})
		
			
			* 索引一旦创建，就不能改变字段的权重了（除非删除索引再重建）。
		

		* 使用 "$**" 在文档的所有 '字符串字段' 上创建全文本索引
		* 这样做不仅会对顶层的字符串字段建立索引，也会搜索内嵌文档和数组中的字符串字段。

			db.articles.createIndex({"$**" : "text"})


---------------------
文本查询
---------------------

	# 使用 "$text" 查询运算符对具有 text 索引的集合进行文本搜索
		
		* "$text" 会使用空格和 '大多数标点符号' 作为分隔符来对搜索字符串进行标记，并在搜索字符串时对所有这些标记执行 'OR' 逻辑。
		
			// 检索在 text 索引字段中包含了 impact 或 crater 或 lunar 的文档，
			// 这里做了投影查询，只返回 title，目的是为了整齐好看
			db.articles.find({"$text": {"$search": "impact crater lunar"}},{title: 1}).limit(7)

			{ "_id" : "170375", "title" : "Chengdu" }
			{ "_id" : "34331213", "title" : "Avengers vs. X-Men" }
			{ "_id" : "498834", "title" : "Culture of Tunisia" }
			{ "_id" : "602564", "title" : "ABC Warriors" }
			{ "_id" : "40255", "title" : "Jupiter (mythology)" }
			{ "_id" : "22483", "title" : "Optics" }
			{ "_id" : "8919057", "title" : "Characters in The Legend of Zelda series" }
		
		* 使用双引号对准确的短语进行搜索
			
			// "\"impact crater\" lunar" 中， 'impact crater' 是一个完整的词儿，不会被分为 2 个。
			db.articles.find({$text: {$search: "\"impact crater\" lunar"}}, {title: 1}).limit(10)
		

