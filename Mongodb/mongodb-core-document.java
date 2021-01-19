-------------------------
document 
-------------------------
	# 数据类型
		* mongo中的数字, 默认是 double 数据类型, 如果需要存储整形, 必须使用函数 NumberInt(val)
			db.user.insert({name: "n3", age: NumberInt(27)});
		
		* 插入当前日期使用: new Date();
		* 如果字段为 null,  不应该声明


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
		* 如果 collection 不存在, 会创建

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
	
	db.[collection].save([document], [config]);
		* 覆盖修改, 通过传入的文档来替换已有文档
		* config 选项
			{
				writeConcern: <document>
					* 可选，抛出异常的级别。 
			}
		
	
	
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
			
		