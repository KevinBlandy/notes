-------------------------
document 
-------------------------
	# 数据类型
		* mongo中的数字, 默认是 double 数据类型, 如果需要存储整形, 必须使用函数 NumberInt(val)
			db.user.insert({name: "n3", age: NumberInt(27)});
		
		* 插入当前日期使用: new Date();
		* 如果字段为 null,  不应该声明
	
	# 类型
		String				字符串。存储数据常用的数据类型。在 MongoDB 中，UTF-8 编码的字符串才是合法的。
		Integer				整型数值。用于存储数值。根据你所采用的服务器，可分为 32 位或 64 位。
		Boolean				布尔值。用于存储布尔值（真/假）。
		Double				双精度浮点值。用于存储浮点值。
		Min/Max keys		将一个值与 BSON（二进制的 JSON）元素的最低值和最高值相对比。
		Array				用于将数组或列表或多个值存储为一个键。
		Timestamp			时间戳。记录文档修改或添加的具体时间。
		Object				用于内嵌文档。
		Null				用于创建空值。
		Symbol				符号。该数据类型基本上等同于字符串类型，但不同的是，它一般用于采用特殊符号类型的语言。
		Date				日期时间。用 UNIX 时间格式来存储当前日期或时间。你可以指定自己的日期时间：创建 Date 对象，传入年月日信息。
		Object ID			对象 ID。用于创建文档的 ID。
		Binary Data			二进制数据。用于存储二进制数据。
		Code				代码类型。用于在文档中存储 JavaScript 代码。
		Regular expression	正则表达式类型。用于存储正则表达式。


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
					* 如果为 false, 如果插入文档异常, 跳过不会返回, 继续处理后面的文档
			}
			
		* 使用 insertOne 和 insertMany 更加合适单条/多条的场景，目前 insert 已经废弃了
		* insertOne 返回
			{
			  acknowledged: true,
			  insertedId: ObjectId("611ce8adba30db4fe68820ed")
			}
		* insertMany 返回
			{
			  acknowledged: true,
			  insertedIds: { '0': ObjectId("611ce8c5ba30db4fe68820ee") }
			}
					
	db.[collection].remove([condition], [config]);
		* 根据条件移除数据
		* 如果没有条件, 则移除所有数据
		
		* config
			{
				justOne: false,
					* 如果设为 true 或 1，则只删除一个文档
					* 如果不设置该参数，或使用默认值 false，则删除所有匹配条件的文档。
				writeConcern: <document>
					* 可选，抛出异常的级别。
			}
		
-------------------------
document - 更新
-------------------------
	db.[collection].update([condition], [update], [config]);
		* 根据 condition 执行 update 修改一条文档
			db.users.update({name: "KevinBlandy"}, {$set: {name: "new Name"}}); // UPDATE `users` SET `name` = 'new Name' WHERE `name` = 'KevinBlandy';
		
		* 如果不使用 $Set, 指令来对指定的字段进行修改, 那么就会变成覆盖修改, 使用新文档彻底覆盖旧文档
			db.users.update({name: "KevinBlandy"}, {name: "ff"}); // 把name=KevinBlandy的文档，修改成只有 name=ff的文档
		

		* config 选项
			{
				upsert: true,
					*  可选，如果不存在update的记录，是否插入objNew ,true为插入，默认是false，不插入。
				multi: true,
					* 可选，默认是false,只更新找到的第一条记录，如果这个参数为true,就把按条件查出来多条记录全部更新。
				writeConcern: <document>
					* 可选，抛出异常的级别。
				collation: <document>,
				arrayFilters: [<filder-document>],
				hint: <document|string>
			}
			
		* update 已经废弃，建议使用 updateOne, updateMany
			db.user.updateMany({"_id": ObjectId("611ce81eba30db4fe68820ec")}, {"$set": {"foo": "bar"}})
	
	db.[collection].save([document], [config]);
		* 在新增的时候，可以 insert 一样，支持单个和批量
		* 覆盖修改, 通过传入的文档来替换已有文档，全量替换
		* config 选项
			{
				writeConcern: <document>
					* 可选，抛出异常的级别。 
			}
			
		* 目前save已经废弃，修改的话，执行updateOne, updateMany, replaceOne
	
	db.[collection].replaceOne([condition], [document], [config])
		* 根据condition全量替换为document
		
	
	# 更新相关的指令
		$set
			* 设置值
			* 如果 $set 的字段不存在, 会新创建
		
		$inc (原子命令)
			* 自增字段值
				db.user.update({_id: ObjectId("5eba69c51e2bb3537a710e0b")}, {$inc: {age: 1}}); // 对age字段 +1
			
		$unset (原子命令)
			* 用户删除指定的字段
				db.user.update({_id: ObjectId("5eba69c51e2bb3537a710e0b")}, {$unset: {age: 1}}) // 删除age字段

		$push
			* 添加一个元素到数组尾部
			* 如果field不存在，会自动插入一个数组类型
				db.user.update({_id: ObjectId("5eba69c51e2bb3537a710e0b")}, {$push: {skills: "ruby"}}) // 添加 ruby 到对象的 skills 集合中
			* 元素可以是任意数据类型

		$pushAll
			* 添加多个对象到数组尾部
			* 参数是一个数组
			

		$pop
			* 从数组中删除元素
				db.user.update({_id: ObjectId("5eba69c51e2bb3537a710e0b")}, {$pop: {skills: -1}}); // 删除 skills 中的第一个值
			* 删除位置的值
				-1: 第一个
				 1: 最后一个

		$pull (原子命令)
			* 从数组删除第一个匹配到的值
				db.user.update({_id: ObjectId("5eba69c51e2bb3537a710e0b")}, {$pull: {skills: "java"}}); // 删除 skills 中的 java 元素

		$pullAll (原子命令)
			* 从数组删除多个匹配到的值
				db.user.update({_id: ObjectId("5eba69c51e2bb3537a710e0b")}, {$pull: {skills: ["java", "python"]}}); // 删除 skills 中的 java, python 元素

		$addToSet (原子命令)
			* 加一个值到数组内，而且只有当这个值在数组中不存在时才增加
			* 如果参数是对象, 或者数组, 那么会进行深比较
		
		$rename (原子命令)
			* 对字段进行重命名
				db.user.update({_id: ObjectId("5eba69c51e2bb3537a710e0b")}, {$pull: {skills: "_skills"}});  // 把 skills 属性修改为 _skills
		
		$bit (原子命令)
			* 位操作，integer类型
				db.user.update({_id: ObjectId("5eba69c51e2bb3537a710e0b")}, {$bit: {val: {and: NumberInt(5)}}}); // UPDATE `val` SET `val` = (`val` and 5)
			
			* 位移操作:and,or,not.....
			
		