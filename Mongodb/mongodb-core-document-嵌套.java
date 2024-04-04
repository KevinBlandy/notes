-------------------------
document - 对象
-------------------------
	# 支持属性导航, 导航的key 需要添加双引号
		db.user.find({"account.email": "10000@qq.com"});
	
	# 基本上, 领悟了导航这个操作, 就可以应对一切
	
		db.user.insert({name: "vi", account: {email: '10000@qq.com', phone: '10086'}, skill:['java', 'python', 'javascript']});
		db.user.insert({name: "kk", account: {email: '99999@qq.com', phone: '10000'}, skill:['Ruby', 'C++', {lang: 'javascirpt', proficiency: 80}]});
		db.user.insert({name: "kk", account: {email: '99999@qq.com', phone: '10000'}, skill:[{lang: 'javascirpt', proficiency: 80},{lang: 'ruby', proficiency: 72},{lang: 'c', proficiency: 55}]});
		
		// 修改
		db.user.updateOne({"account.email": "10000@qq.com"}, {$set: {"account.phone": 10010}});
		
		// 查询
		db.user.find({"account.phone": /\d+/});
	
	# 检索内嵌文档属性的时候，必须所有属性都匹配，顺序错了也检索不到
		{
			"name": {
				"first": "Kevin",
				"last": "Blandy"
			}
		}
		
		// 少了 first，也查询不到
		db.user.find({"name": { "last": "Blandy"}})
		// first 和 last 声明位置放了，查询不到
		db.user.find({"name": { "last": "Blandy", "first": "Kevin",}})
	
		* 可以使用对象导航来避免这个问题
			
			// 只声明一个，可以检索到
			db.user.find({"name.last": "Blandy"});
			// 顺序颠倒，可以检索到，关系是 OR
			db.user.find({"name.last": "Blandy", "name.first": "Kevin"});
	
-------------------------
document - 数组
-------------------------
	# 在查询中
		* 精准查询, 数组中的元素和位置必须一摸一样, 才能被匹配
			db.user.find({"skill": ['java', 'python', 'javascript']});
		
		* 也可以使用 $all 来精准查，此时顺序无关紧要
			db.user.find({"skill": {"$all": ['java', 'python', 'javascript']}});
		
		* 匹配单个元素, 只要包含了指定元素, 就可以匹配
			db.user.find({"skill": 'java'}); // 单个元素不用写 []
		
		* 匹配多个元素，or关系
			db.user.find({$or: [{"skill": "java"}, {"skill": "python"}]}); // and 关系也同理
		
		* 匹配数组中某个指定的元素（某个指定位置的元素）
			db.user.find({"skill.2": 'javascript'}); // 通过 arrayKey.[index] 指定元素的下标。下标是从0开始
		
		* 使用 $ 可以获取到元素的下标
			// {"emails": "c.qq.com"} 表示 emails 数组中，值为 "c.qq.com" 的这个元素
			// 它的下标可以通过 emails.$ 来获取
			db.user.updateOne({"emails": "c.qq.com"}, {"$set": {"emails.$": "cc.qq.com"}})

			* 只会更新数组中第一个匹配到的元素
			* 可用于返回数组中符合条件的第一个元素
		
		* $size 计算出长度
			// hobby 长度为 3 的记录
			db.user.find({"hobby": {"$size": 3}})

			* $size 不能和 $gt 一起用，可以考虑在文档中专门维护一个 "size" 字段
			* 每次新增、删除数组元素的时候同时维护 "size" 字段
		
		* $slice 只返回元素中的某一段儿数据，切片
			// 返回前 10 条，负数表示后 10 条
			db.user.find({}, {"hobby": {"$slice": 10}});

			// 偏移量，跳过前 1 个元素，从 2 个开始，获取 3 个 元素
			// 元素不足，会返回尽可能多的元素
			db.user.find({}, {"hobby": {"$slice": [1, 3]}});

			* 除非特别指定， "$slice" 会返回数组元素（元素是一个嵌套的文档）的所有 Key
		
		* KEY 键的某一个元素与查询条件的任意一条语句相匹配，文档也会被返回
			{"key": 1}
			{"key": 6}
			{"key": [8, 15]} 

			// 
			db.user.find({"key": {$gt: 5, $lt: 10}})
			// {"key": 6}, {"key": [8, 15]}  // 8 满足了查询条件（5 < 8 > 10），虽然 15 不满足，但是会整个返回。
		
		* 使用 $elemMatch 对数组元素进行比较，必须要满足 $elemMatch 中的所有条件
			db.user.find({"key": {$elemMatch: {$gt: 5, $lt: 10}}})
			// 返回空
		* 注意，如果 KEY 不是数组，会被忽略
		* "$elemMatch" 允许将限定条件进行 “分组”。仅当需要对一个内嵌文档的多个键进行操作时才会用到它。

	
	# 数组元素是对象
		* 仅仅匹配单个属性
			db.user.find({"skill.lang": "java"}); // {skill: [{lang: "java"}]}
		
		* 匹配多个属性, 使用 $elemMatch
			db.user.find({"skill.lang": {$elemMatch: {lang: "Java", proficiency: 80}}});
	

