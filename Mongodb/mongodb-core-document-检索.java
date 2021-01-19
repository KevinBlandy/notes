-------------------------
document - 查询相关
-------------------------
	# 基本的查询
		db.[collection].find([condition])
			* 根据条件检索出结果
			* 如果没有条件, 则检索出所有的数据
			
			* 该方法返回的对象是一个 '迭代器', 可以使用 while 来进行迭代
				const it = db.foo.find();
				while (it.hasNext()) {
					printjson(it.next());
				}
			
			* 可以使用 pretty() 函数, 格式化json结果
				db.foo.find().pretty;
			

	
	# 分页
		db.[collection].find([condition]).skip([rows])
			* 用skip()方法来跳过指定数量记录条数的数据

		db.[collection].find([condition]).limit([rows])
			* 使用 limit 方法, 来限制结果集数量
	
	
	# 统计数量
		db.[collection].count([condition]);
			* 根据条件统计文档的数量
			* 如果没有条件, 则统计所有的文档

	# 排序
		db.[collection].find().sort([row])
			* 通过 sort 指定排序的字段以及策略
				db.users.find().sort({name: -1}) // 根据name字段，逆序排序
			
			* 排序策略。-1: 逆序，1:升序
			* 如果它和 skip() 一起使用, 是先排序, 再分页

	
	# 投影查询, 仅仅检索部分列
		db.[collection].find([condition], [props])
			* 通过 props 指定查询的列
			* props 中使用 key: val 描述是否要检索, val 如果是1：表示查询，0：表示不查询
			* 默认会检索 _id 属性
		
		* 仅仅查询对象的某些属性
			db.user.find({}, {name: 1, _id: 0}) // 仅仅查询name属性，连id都不要
	

-------------------------
document - 聚合检索
-------------------------

-------------------------
document - 条件语句
-------------------------
	# 基本查询条件
		等于				{<key>:<value>}			db.col.find({"by": "菜鸟教程"}).pretty()		where by = '菜鸟教程'
		小于				{<key>:{$lt:<value>}}	db.col.find({"likes":{$lt: 50}}).pretty()		where likes < 50
		小于或等于			{<key>:{$lte:<value>}}	db.col.find({"likes":{$lte: 50}}).pretty()		where likes <= 50
		大于				{<key>:{$gt:<value>}}	db.col.find({"likes":{$gt: 50}}).pretty()		where likes > 50
		大于或等于			{<key>:{$gte:<value>}}	db.col.find({"likes":{$gte: 50}}).pretty()		where likes >= 50
		不等于				{<key>:{$ne:<value>}}	db.col.find({"likes":{$ne: 50}}).pretty()		where likes != 50
		包含				{<key>:{$in:[<value>]}}	db.col.find({"likes":{$in: [10]}}).pretty()		where likes IN (10)
	
	# 正则查询
		* 正则, 通过js的 /reg/ 正则来匹配数据
			db.user.find({name: /^\d+$/});				// 匹配 name 是数字的记录
		
		* 也可以通过 $regex 指令
			db.user.find({name: {$regex: "^\\d+$"}});  // 匹配 name 是数字的记录
	
	# $type 操作符
		* $type操作符是基于BSON类型来检索集合中匹配的数据类型，并返回结果。
		
			类型					数字	备注
			Double					1	 
			String					2	 
			Object					3	 
			Array					4	 
			Binary data				5	 
			Undefined				6		已废弃。
			Object id				7	 
			Boolean					8	 
			Date					9	 
			Null					10	 
			Regular	Expression		11	 
			JavaScript				13	 
			Symbol					14	 
			JavaScript	(with scope)15	 
			32-bit integer			16	 
			Timestamp				17	 
			64-bit integer			18	 
			Min key					255		Query with -1.
			Max key					127	 
		
		* 根据指定key的类型去匹配数据
			db.users.find({name: {$type: 'string'}});  // 仅仅匹配name属性是字符串的记录
			db.users.find({name: {$type: 2}});		// 同上

	
	# AND条件关系
		* AND 关系, 默认对象中的属性都是 AND 条件
			db.col.find({name: "1", age: 23}); // WHERE name = '1' AND age = 23;
		
		* 可以使用 $and
			db.col.find({$and: [{name: 'Kevin'}, {age: 27}]});
		
	# OR 条件关系
		db.col.find({$or: [{name: "v"}, {name: "z"}]}); // WHERE name = 'v' OR `name` = `z`
		
	# 组合关系
			db.users.find({
				name: "KevinBlandy",
				$or: [{
					age: {
						$gt: 18
					}
				}, {
					age: {
						$lt: 50
					}
				}]
			});  // WHERE name = 'KevinBlandy' AND (age > 18 OR age < 50)
		

			db.user.find({
				$and: [{
					name: "vin"
				}, {
					$or: [{
						age: {
							$lt: 25
						}
					}]
				}]
			}); //  WHERE name = 'KevinBlandy' AND (age < 50)
			
			db.user.find({$or: [{name: 'vin'}, {age: {$in: [1,2,3]}}]}); // WHERE name = 'vin' OR `age` IN (1,2,3)