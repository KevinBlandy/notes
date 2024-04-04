-------------------------
document 
-------------------------
	# 文档
		* BSON，MongoDB 数据库中的数据存储和网络传输格式，一种二进制表示形式
		* Key 区分大小写，且同一个文档中相同层次的 Key 不能重复。
		* 准备要保存的文档（新文档）的key，不能包含 '.'，因为 '.' 是用于在检索、更新时用于属性导航的。

	# 数据类型
		* mongo中的数字, 默认是 double 数据类型, 如果需要存储整形, 必须使用函数 NumberInt(val)
			db.user.insert({name: "n3", age: NumberInt(27)});
		
		* 插入当前日期使用: new Date();
		* 如果字段为 null,  不应该声明
	
	# 类型
		String				字符串。存储数据常用的数据类型。在 MongoDB 中，UTF-8 编码的字符串才是合法的。
		Integer				整型数值。用于存储数值。根据你所采用的服务器，可分为 32 位 NumberInt() 或 64 位。NumberLong()
		Boolean				布尔值。用于存储布尔值（真/假）。
		Double				双精度浮点值。用于存储浮点值，shell 默认使用 64 位浮点数来表示数值。
		Min/Max keys		将一个值与 BSON（二进制的 JSON）元素的最低值和最高值相对比。
		Array				用于将数组或列表或多个值存储为一个键。
			* 数组可以包含不同数据类型的元素。

		Timestamp			时间戳。记录文档修改或添加的具体时间。
		Object				用于内嵌文档。
			* 嵌入式对象。

		Null				用于创建空值。
		Symbol				符号。该数据类型基本上等同于字符串类型，但不同的是，它一般用于采用特殊符号类型的语言。
		Date				日期时间。用 UNIX 时间格式来存储当前日期或时间。你可以指定自己的日期时间：创建 Date 对象，传入年月日信息。
			* 在 MongoDB 中，创建日期对象应该用: new Date()
			* 数据库中存储的日期仅为 Unix 纪元以来的毫秒数，并没有与之关联的时区信息！

		Object ID			对象 ID。用于创建文档的 ID。
			* MongoDB 中存储的每个文档都必须有一个 "_id" key。
			* "_id" 的值可以是任何类型，但其默认为 ObjectId。
			* 每个集合中的 id 值应该是唯一的，如果插入文档时不存在 "_id" key 会自动生成。


		Binary Data			二进制数据。用于存储二进制数据。通过 shell 不能直接操作。
		Code				代码类型。用于在文档中存储 JavaScript 代码，{"x": function(){}}
		Regular expression	正则表达式类型。用于存储正则表达式，/foobar/i


-------------------------
document - id
-------------------------
	# 在集合中插入文档时，如果没有在字段名称中添加带有_id的字段名称，则MongoDB将自动添加一个Object id字段
		{"_id": ObjectId("xxxxxxxxx")}

-------------------------
document - 基本命令
-------------------------
	db.[collection].insert([document], [config]);
		* 往指定的collection插入一个或者多个(参数用数组)document
		* 如果 collection 不存在, 会创建一个不限长度的collection

		* 执行成功后返回插入成功的文档数量
			WriteResult({"nInserted": 1});
		
		* config
			{
				writeConcern: <document>
					* 可选，抛出异常的级别。
				ordered: true
					* 默认为 true, 按照顺序插入文档, 如果其中任何一个异常, 则立即返回, 剩余的文档不会处理
					* 如果为 false, Mongo会重新排列，以提高速度，如果插入文档异常, 跳过不会返回, 继续处理后面的文档
			}
			
		* 使用 insertOne 和 insertMany 更加合适单条/多条的场景，目前 insert 已经废弃了
		* insertMany 一次性插入的数据最多大约是 50Mb，超过后驱动会进行分批插入
		* 单个文档大小不能超过 16Mb，可以通过 bsonsize(doc) 来检测文档大小
			bsonsize({"title": ''})
			17

		* insertOne 返回
			{
			  acknowledged: true,
			  insertedId: ObjectId("611ce8adba30db4fe68820ed")
			}
		* insertMany 返回
			{
			  acknowledged: true,
			  insertedIds: {
				'0': ObjectId('660ced8f6a4199e694670fb2'),
				'1': ObjectId('660ced8f6a4199e694670fb3')
			  }
			}	
	
	db..[collection].deleteOne([condition], [config]) / db..[collection].deleteMany([condition], [config])

		* 根据条件移除第一个/所有数据。
		* 哪个文档是 '第一个'，取决于多个方面，包括文档被插入的顺序、对文档进行了哪些更新（对于某些存储引擎来说）以及指定了哪些索引。
		* 如果没有条件，即空对象 {}) 则移除所有数据。

		* config
			{
				justOne: false,
					* 如果设为 true 或 1，则只删除一个文档
					* 如果不设置该参数，或使用默认值 false，则删除所有匹配条件的文档。
				writeConcern: <document>
					* 可选，抛出异常的级别。
			}
		
		* 删除所有数据的话，使用 db.[collection].deleteMany({}) 不如使用 db.[collection].drop() 直接删除整个集合
		* 文档、集合删除后就找不回来了，除非有备份。

-------------------------
document - 更新
-------------------------
	db.[collection].updateOne([condition], [update], [config]); / updateMany
		* 根据 condition 执行修改一条/多条文档
			db.users.updateOne({name: "KevinBlandy"}, {$set: {name: "new Name"}}); // UPDATE `users` SET `name` = 'new Name' WHERE `name` = 'KevinBlandy';
		
		* 如果不使用 $Set, 指令来对指定的字段进行修改, 那么就会变成覆盖修改, 使用新文档彻底覆盖旧文档
			db.users.updateOne({name: "KevinBlandy"}, {name: "ff"}); // 把name=KevinBlandy的文档，修改成只有 name=ff的文档
		
		* 更新多个
			db.user.updateMany({"_id": ObjectId("611ce81eba30db4fe68820ec")}, {$set: {"foo": "bar"}})
		
		* 返回 
				{
				  acknowledged: true,
				  insertedId: null,
				  matchedCount: 1,
				  modifiedCount: 1,
				  upsertedCount: 0
				}
	

		* config 选项
			{
				upsert: true,
					*  可选，如果不存在 update 的记录，是否插入objNew ,true为插入，默认是false，不插入。
					* 新文档是以条件文档作为基础，并对其应用修饰符文档进行创建的，且是原子性的。
						
						// name = foo 的记录不存在，则插入。如果存在，则 views 字段自增 1
						db.user.updateOne({"name": "foo"}, {$inc: {"views": 1}}, {upsert: true})
						{
						  "name": "foo",
						  "views": 1
						}
					
					* 如果条件key，和更新 key 是同一个 key，并且对该 key 的值进行递增的 upsert 操作，则这个递增会应用于所匹配的文档
						db.user.updateOne({"count": 1}, {$inc: {"count": 1}}, {"upsert": true})
						// "count": 1 的记录不存在，插入后，会执行 $inc 自增。
						{
						  "count": 2
						}
					
				multi: true,
					* 可选，默认是 false,只更新找到的第一条记录，如果这个参数为true,就把按条件查出来多条记录全部更新。
				
				writeConcern: <document>
					* 可选，抛出异常的级别。
				
				collation: <document>,
				arrayFilters: [<filder-document>],
					* 用于更新单个数组元素的选项，修改与特定条件匹配的数组元素
						{
						  "id": 1,
						  "hobby": [
							{
							  "name": "唱",
							  "rate": 1
							},
							{
							  "name": "跳",
							  "rate": 2
							},
							{
							  "name": "rap",
							  "rate": 3
							}
						  ]
						}
						
						// 更新 id = 1 文档
						// 把 hobby 数组中 rate >= 2的元素的 rate 值，自增 1

						// 这个语句将 elem 定义为了 "hobby" 数组中每个匹配元素的标识符
						db.user.updateOne({"id": 1}, {$inc: {"hobby.$[elem].rate": 1}}, {arrayFilters: [{"elem.rate": {"$gte": 2}}]});
					

				hint: <document|string>
			}
			
		
	db.[collection].replaceOne([condition], [document], [config])
		* 根据 condition 全量替换为 document
		* 一般用来做文档的结构更新，先读取文档，修改后再写入
			// 检索
			var u = db.users.findOne({...});
			// 修改结果
			u.Address = {...}
			// 修改结果
			delete u.Foo
			// 更新
			db.users.replaceOne({...}, u);
		
		* 注意，整个文档替换时是可以改变 "_id" 的。
	
	save() 辅助函数
		* save 是一个 shell 函数，它可以在文档不存在时插入文档，在文档存在时更新文档。
		* 它只有唯一的文档参数。如果文档中包含 "_id" key，save 就会执行一个 upsert。否则，执行插入操作。

	
	findOneAndDelete(filter, options)
		* 删除并返回被删除的文档，它不接受更新文档作为参数

	findOneAndReplace(filter, replacement, options )
	findOneAndUpdate(filter, update, options)
		* findOneAndUpdate 以接受一个用来更新的聚合管道。
		* 管道可以包含以下阶段：$addFields 及其别名 $set、$project 及其别名 $unset，以及 $replaceRoot 及其别名 $replaceWith。
		* 在一个操作中返回匹配的结果并进行更新，默认返回文档修改/替换之前的状态。
	
	* 与 updateOne 之间的主要区别在于，它们可以原子地获取已修改文档的值。
	* 将选项文档中的 "returnNewDocument" 字段设置为 true，那么它将返回修改/替换后的文档。
			db.users.findOneAndUpdate({...}, {...}, {"returnNewDocument": true})

-------------------------
document - 更新相关的指令
-------------------------
	$set
		* 设置/修改值，连类型都可以修改
		* 如果 $set 的字段不存在, 会新创建
	
	$unset （原子命令）
		* 用户删除指定的字段
			db.user.update({_id: ObjectId("5eba69c51e2bb3537a710e0b")}, {$unset: {age: 1}}) // 删除age字段
	
	$inc (原子命令)
		* 自增字段值
			db.user.update({_id: ObjectId("5eba69c51e2bb3537a710e0b")}, {$inc: {age: 1}}); // 对age字段 +1
		
		* 如果字段不存在，则会成为 $set，如果字段非数值、整数类型则会抛出异常

	$push
		* 添加一个元素到数组尾部
		* 如果field不存在，会自动插入一个数组类型
			db.user.update({_id: ObjectId("5eba69c51e2bb3537a710e0b")}, {$push: {skills: "ruby"}}) // 添加 ruby 到对象的 skills 集合中
		* 元素可以是任意数据类型
		* 配合 $each 指令，可以一次性添加多个元素
			// 修改 hobby 字段，添加 $each 指定的三个元素
			db.user.updateOne({"name": "V"}, {"$push": {"hobby": {"$each": ["唱", "跳", "rap"]}}})
			// 修改后的结果如下
			{
			  "hobby": [
				"唱",
				"跳",
				"rap"
			  ]
			}
			 
		* 配合 $slice 可以限制长度
			// 修改 hobby 字段，添加  $each 指定的 2 个元素，然后修剪 hobby 字段，如果字段元素长度大于了 2 个，则只保最后的 2 个
			db.user.updateOne({"name": "V"}, {"$push": {"hobby": {"$each": ["写代码", "打篮球"], "$slice": -2}}})
			// 修改后结果如下
			{
			  "hobby": [
				"写代码",
				"打篮球"
			  ]
			}
		
		*在 "$slice" 截断之前可以将 "$sort" 修饰符应用于 "$push"，先按照某种方式排序，在截断
			db.user.updateOne({"name": "V"}, {"$push": {"hobby": {"$each": ["写代码", "打篮球"], "$slice": -2, "$sort": 1}}})
		
			* 注意，不能只将 "$slice" 或 "$sort" 与 "$push" 配合使用，必须包含 "$each"。
		
	$addToSet (原子命令)
		* 加一个值到数组内，而且只有当这个值在数组中不存在时才增加
			// 更新 name = V 的文档，往 emails Set 中添加 a.qq.com
			db.user.updateOne({"name": "V"}, {$addToSet: {"emails": "a.qq.com"}})

		* 如果参数是对象, 或者数组, 那么会进行深比较。
		* 也可以和 $each 配合使用
	
	$pop
		* 从数组中删除元素
			db.user.update({_id: ObjectId("5eba69c51e2bb3537a710e0b")}, {$pop: {skills: -1}}); // 删除 skills 中的第一个值
		* 删除位置的值
			-1: 第一个，从头
			 1: 最后一个，从尾

	$pull (原子命令)
		* 从数组删除所有匹配到的值
			db.user.update({_id: ObjectId("5eba69c51e2bb3537a710e0b")}, {$pull: {skills: "java"}}); // 删除 skills 中的 java 元素

	$setOnInsert
		* 在创建文档时对字段进行设置，但在后续更新时不对其进行更改。


	$rename (原子命令)
		* 对字段进行重命名
			db.user.update({_id: ObjectId("5eba69c51e2bb3537a710e0b")}, {$pull: {skills: "_skills"}});  // 把 skills 属性修改为 _skills
	
	$bit (原子命令)
		* 位操作，integer类型
			db.user.update({_id: ObjectId("5eba69c51e2bb3537a710e0b")}, {$bit: {val: {and: NumberInt(5)}}}); // UPDATE `val` SET `val` = (`val` and 5)
		
		* 位移操作:and,or,not.....
		
	