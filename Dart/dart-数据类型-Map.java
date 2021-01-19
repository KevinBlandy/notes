---------------------------
Map
---------------------------
	# Map 是一个关联键和值的对象, 键和值都可以是任何类型的对象, 每个键只出现一次(后者覆盖前者)

	# 初始化
		var map = {1: 'KevinBlandy', 'name': '123'};

		Map map = {'name': 'KevinBlandy', 1: 456};

		Map map = new Map();
		map['1'] = 'KevinBlandy';
		map[2] = 'Hello';
		print(map);  // {1: KevinBlandy, 2: Hello}
	
		* 在不适用泛型的情况下, map可以存在多个不同数据类型的k/v
	
	# 元素的访问和修改, 通过通过 [] 表达式
		Map map = new Map();
		map['1'] = 'KevinBlandy';
		map[2] = 'Hello';
		print(map['1']);  // KevinBlandy
		print(map[2]);	// Hello

		* 通过 [] 访问不存在的元素, 返回 null		
		* 元素的key可以为 null
			Map map = new Map();
			map[null] = 'foo'; //  foo
			print(map[null]);
	
	# Map实例的一些属性
		length
			* 返回元素的个数
	
	# 通过泛型<>来定义map的key和value的类型
		Map<String, String> map = new Map();
		map[1] = '15'; // Error: A value of type 'int' can't be assigned to a variable of type 'String'.
		
		* 使用非泛型指定的数据类型来访问map, 不会异常
			Map<String, String> map = new Map();
			print(map[1]);  // null
		
	
---------------------------
Map 实例方法
---------------------------
---------------------------
Map 静态方法
---------------------------

			