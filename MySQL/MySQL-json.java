----------------------
json				  |
----------------------
	# 5.7的新特性

	# 建表
		CREATE TABLE `test` (
		  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
		  `value` json NOT NULL,
		  PRIMARY KEY (`id`)
		) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

	# 插入
		INSERT INTO `test`(`id`, `value`) VALUES(null, '["Java","Python","Javascript"],"name":"KevinBlandy","age":23}'); 

		* 把json字段当作字符串插入
	
		
	# 创建相关的函数
		json_array
			* 把数据转换为json数组
				SELECT JSON_ARRAY(1, "abc", NULL, TRUE, CURTIME());
				+---------------------------------------------+
				| JSON_ARRAY(1, "abc", NULL, TRUE, CURTIME()) |
				+---------------------------------------------+
				| [1, "abc", null, true, "11:30:24.000000"]   |
				+---------------------------------------------+

		json_object
			* 把数据转换为json对象
				SELECT JSON_OBJECT('id', 87, 'name', 'carrot');
				+-----------------------------------------+
				| JSON_OBJECT('id', 87, 'name', 'carrot') |
				+-----------------------------------------+
				| {"id": 87, "name": "carrot"}            |
				+-----------------------------------------+
		
		json_quote
			* 将json转成原始json字符串类型
				select json_quote('{"name": "KevinBlandy"}');  // "{\"name\": \"KevinBlandy\"}"

		
		json_unquote()
			* 把json字符串转换为json
				select json_unquote("{\"name\": \"KevinBlandy\"}"); // {"name": "KevinBlandy"}
		
	
	# 查询相关的函数
		json_contains(json_doc, val[, path]) 
			* 搜素json文本中是否包含特定值, 返回: 0/1 
			* 可能会返回null
				- 目标JSON文本或者特定值为NULl
				- 指定路径非目标JSON文本下的路径
			
			* 参数
				json_doc
					- json文本
				val
					- 特定的值, 可以是json或者基本数据类型
				path
					- jsonpath
			
			* demo
				select json_contains('{"a": 1, "b": 2, "c": {"d": 4}}', '1', '$.a');	// 1
				select json_contains('{"a": 1, "b": 2, "c": {"d": 4}}', '1', '$.b');	// 0
				select json_contains('{"a": 1, "b": 2, "c": {"d": 4}}', '{"d": 4}', '$.b');		// 0
				select json_contains('{"a": 1, "b": 2, "c": {"d": 4}}', '{"d": 4}', '$.c');		// 1


		json_contains_path(json_doc, one_or_all, path[, path] ..)
			* 仅仅检查json文本是否包含指定的路径, 返回: 0/1
			* 参数
				json_doc
					- json文本
				
				one_or_all
					- one: 文本中存在至少一个指定路径则返回1, 否则返回0
					- all: 文本中包含所有指定路径则返回1, 否则返回0
				path
					- json path, 就是指定的路径可以有多个
				
			
			* demo
				select json_contains_path('{"a": 1, "b": 2, "c": {"d": 4}}', 'one', '$.c'); //1
				select json_contains_path('{"a": 1, "b": 2, "c": {"d": 4}}', 'all', '$.c', '$.z'); // 0

				

		json_extract(json_doc, path[, path] ...)
			* 返回json_doc中与path参数相匹配的数据
			* demo
				SELECT JSON_EXTRACT('[10, 20, [30, 40]]', '$[1]');	// 20
				SELECT JSON_EXTRACT('[10, 20, [30, 40]]', '$[1]', '$[0]');	// [20, 10] 
				SELECT JSON_EXTRACT('[10, 20, [30, 40]]', '$[2][*]');	// [30, 40]
				SELECT JSON_EXTRACT('[10, 20, [30, 40]]', '$[2][0]');	// [30]

								

		json_keys(json_doc[, path])
			* 返回JSON对象的顶层目录的所有key值或者path指定路径下的顶层目录的所有key所组成的JSON数组

		json_search(json_doc, one_or_all, search_str[, escape_char[, path] ...])
			* 返回json中包含了指定字符串的jsonpath
			* 参数
				json_doc
					- json

				one_or_all
					- one 当查找操作找到第一个匹配对象，并将结果路径返回后就停止查找
					- all 将返回所有的匹配结果路径, 结果中不包含重复路径, 如果返回结果集中包含多个字符串, 将自动封装为一个数组, 元素的排序顺序无意义
				
				search_str
					- 搜素的字符串, 包含一些特殊字符, 如果需要匹配特殊字符, 需要转义
					- 特殊符号:%	匹配多个
					- 特殊符号:_	匹配一个
					- 可以有多个
				

	# 修改相关的函数
		json_append
			* 现已在MySQL 5.7中弃用, 并在MySQL 8.0中删除

		json_array_append
			* 在指定的数组末尾以JSON文本形式追加指定的值并返回

		json_array_insert
		json_insert
		json_merge
		json_remove
		json_replace
		json_set
		json_unquote


	# 返回json属性
		json_depth(json)
			* 返回json文档的深度
		json_length
			* 返回JSON文档的长度(size)
		json_type(json)
			* 返回json的类型, 或者是json的数据类型
				OBJECT        
				ARRAY
				INTEGER
				BOOLEAN
			
		json_valid(json)
			* 判断json是否是标准json
			* 如果json不是标准的, 会抛出异常
		

	# json path语法
		$ 表示根对象

	# COLUMN->PATH 运算
		* COLUMN 参数表示json 列, PATH 参数 表示jsonpath
		* 当与两个参数一起使用时, 就是json_extract的函数的别名, 用于快捷的提取json值

		* demo, json = {"age": 23, "name": "KevinBlandy", "skill": ["Java", "Python", "Javascript"]}

			select `value`, `value`-> "$.name" from `test`; // {"age": 23, "name": "KevinBlandy", "skill": ["Java", "Python", "Javascript"]}	"KevinBlandy"
			select `id`, `value`-> "$.skill" from `test`;	// 1	["Java", "Python", "Javascript"]
			select `id`, `value`-> "$.skill[0]" from `test`; // 1	"Java"
	
		
	# COLUMN->>PATH 
		* 改进的, 不引用的提取操作符, 可在MySQL 5.7.13及更高版本中使用
		* 它跟如下的操作一样, 会使用JSON_UNQUOTE函数对表达式检索到的值进行计算
			JSON_UNQUOTE(JSON_EXTRACT(column, path))
			JSON_UNQUOTE(column -> path)
		
	