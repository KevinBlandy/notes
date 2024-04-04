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
		
		* 深度分页可能会带来性能问题
		* 如果数据量过大，建议按照索引字段来进行分页，而不是 skip 指令
			// 每页数据的第一条，以上一页的最后一条 ID 作为开始
			// ObjectId('660e6c7ac5ef631d778afc3a')，即上一页的最后一条 ID
			db.foo.find({"_id": {"$gt": ObjectId('660e6c7ac5ef631d778afc3a')}}).limit(10);
	
	
	# 统计数量
		db.[collection].count([condition]);
			* 根据条件统计文档的数量
			* 如果没有条件, 则统计所有的文档

	# 排序
		db.[collection].find().sort([row])
			* 通过 sort 指定排序的字段以及策略
				db.users.find().sort({name: -1}) // 根据name字段，逆序排序
				db.users.find().sort({name: -1, age: 1}) // 根据name字段，逆序排序，age字段升序排序
			
			* 排序策略。-1: 逆序，1:升序
			* 如果它和 skip() 一起使用, 是先排序, 再分页
			
			* 对于类型的比较有一个层次结构。有时一个 KEY 的值可能有多种类型。
			* 如果对混合类型的 KEY 进行排序，那么会有一个预定义的排序顺序，从最小值到最大值，顺序如下。
				/最小值/null/数字（整型、长整型、双精度浮点型、小数型）/字符串对象或文档/数组/二进制数据/对象 ID/布尔型/日期/时间戳/正则表达式/最大值/

	
	# 投影查询, 仅仅检索部分列
		db.[collection].find([condition], [props])
			* 通过 props 指定查询的列
			* props 中使用 key: val 描述是否要检索, val 如果是1：表示查询，0：表示不查询
			* 默认会检索 _id 属性
		
		* 仅仅查询对象的某些属性
			db.user.find({}, {name: 1, _id: 0}) // 仅仅查询name属性，连id都不要
		
		* 除了id以外，其他字段不能做“排他”
	

-------------------------
document - 游标
-------------------------
	# find() 返回的就是一个游标，可用来迭代查询

		* 如果没有把 find() 返回的游标存储到变量中，Mongo 会自动迭代遍历前面的一些文档，输出。
		
		let cursor = db.foo.find();

		// 迭代游标
		while (cursor.hasNext()){
			// 获取项目
			let doc = cursor.next();
			print(doc);
		}
		
		// 也支持使用 forEach 回调进行迭代
		cursor.forEach(doc => {
			print(doc);
		});
		
	
	# 调用 find 时，shell 并不会立即查询数据库，而是等到真正开始请求结果时才发送查询
		
		* 这样可以在执行之前给查询附加额外的选项，游标大多数方法都是返回 this ，这样就可以实现链式调用
		
		// 添加 N 个选项，在但是不会立即发起查询
		let cursor = db.foo.find().skip(1).limit(100).sort({"i": -1});

		while (cursor.hasNext()){
			let doc = cursor.next();
			print(doc);
		}

		* 当调用 'cursor.hasNext()' 的时候，查询才会被发往服务器端。
		* shell 会立刻获取前 100 个结果或者前 4MB 的数据（两者之中较小者），这样下次调用 next 或者 hasNext 时就不必再次连接服务器端去获取结果了。
		* 在客户端遍历完第一组结果后，shell 会再次连接数据库，使用 getMore 请求更多的结果。
		* getMore 请求包含一个游标的标识符，它会向数据库询问是否还有更多的结果，如果有则返回下一批结果。
		* 这个过程会一直持续，直到游标耗尽或者结果被全部返回。
	
	# 游标的生命周期
		* 游标包括两个部分：面向客户端的游标（上面讲的）和由客户端游标所表示的数据库游标。

		* 在服务器端，游标会占用内存和资源。一旦游标遍历完结果之后，或者客户端发送一条消息要求终止，数据库就可以释放它正在使用的资源。

		* 当游标遍历完匹配的结果时，它会清除自身！
		* 当游标超出客户端的作用域时，驱动程序会向数据库发送一条特殊的消息，让数据库知道它可以“杀死”该游标。
		* 用户没有遍历完所有结果而且游标仍在作用域内，如果 10 分钟没有被使用的话，数据库游标也将自动 “销毁”。

		* 许多驱动程序实现了一个称为 immortal 的函数，或者类似的机制，它告诉数据库不要让游标超时。
		* 如果关闭了游标超时，那么客户端必须尽快的遍历完所有数据，或者主动销毁游标。否则，数据库会一直保持游标直到下次重启。



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
		不包含				{<key>:{$nin:[<value>]}}	db.col.find({"likes":{$nin: [10]}}).pretty()		where likes NOT IN (10)
	
	# 正则查询
		* 正则, 通过js的 /reg/i 正则来匹配数据，后面的 i 表示不区分大小写
			db.user.find({name: /^\d+$/});				// 匹配 name 是数字的记录
		
		* 也可以通过 $regex 指令
			db.user.find({name: {$regex: "^\\d+$"}});  // 匹配 name 是数字的记录

		
		* Mongo 使用 Perl 兼容的正则表达式（PCRE）库来对正则表达式进行匹配，任何 PCRE 支持的正则表达式语法都能被 MongoDB 接受。


	
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
	
	# $exists 操作符
		db.users.find({name: {$exists: true}});		// name 字段存在，则满足
		db.users.find({name: {$exists: false}});	// name 字段不存在，则满足

		* null 会匹配 “不存在” 这个条件
			// 如果 foo 字段不存在，或者是为 null 都会检索出来
			db.users.find({"foo": {$eq: null}})
		
		* 配合 $exists，就可以实现 “必须存在且为 null”
			db.users.find({"foo": {$eq: null, $exists: true}})
	
	# $where 操作符
		* 可以用 JS 代码对文档进行过滤
			
			// 如果函数返回 true，文档就作为结果集的一部分返回；如果函数返回 false，文档就不返回。
			db.foo.find({"$where" : function () {
				// this 指向当前文档
				for (var current in this) {
					for (var other in this) {
						if (current != other && this[current] == this[other]) {
							return true;
						}
					}
				}
				return false;
			}});
		
		* 除非绝对必要，否则不应该使用 "$where" 查询：它们比常规查询慢得多。每个文档都必须从 BSON 转换为 JavaScript 对象，然后通过 "$where" 表达式运行。
		* "$where" 也无法使用索引。

		* 可以先使用其他查询进行过滤，然后再使用 "$where" 子句，这样组合使用可以降低性能损失。
	
	# $expr 操作符
		* 允许在 MongoDB 查询语句中使用聚合表达式，它不需要执行 JavaScript，所以速度比 $where 快，建议尽可能使用此运算符替代 $where。


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
	
