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
		db.user.update({"account.email": "10000@qq.com"}, {$set: {"account.phone": 10010}});
		
		// 查询
		db.user.find({"account.phone": /\d+/});

-------------------------
document - 数组
-------------------------
	# 在查询中
		* 精准查询, 数组中的元素和位置必须一摸一样, 才能被匹配
			db.user.find({"skill": ['java', 'python', 'javascript']});
		
		* 匹配单个元素, 只要包含了指定元素, 就可以匹配
			db.user.find({"skill": 'java'}); // 单个元素不用写 []
		
		* 匹配多个元素，or关系
			db.user.find({$or: [{"skill": "java"}, {"skill": "python"}]}); // and 关系也同理
		
		* 匹配数组中某个指定的元素（某个指定位置的元素）
			db.user.find({"skill.2": 'javascript'}); // 通过 arrayKey.[index] 指定元素的下标。下标是从0开始
	
	# 数组元素是对象
		* 仅仅匹配单个属性
			db.user.find({"skill.lang": "java"}); // {skill: [{lang: "java"}]}
		
		* 匹配多个属性, 使用 $elemMatch
			db.user.find({"skill.lang": {$elemMatch: {lang: "Java", proficiency: 80}}});
			


	# 在排序中
	# 在条件中
	# 在修改中