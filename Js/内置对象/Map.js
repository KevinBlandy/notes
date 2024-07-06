------------------
Map
------------------
	# 保存键值对，有序，任何值（对象或者原始值）都可以作为键或值。
		* Map内部使用 SameValueZero 比较操作（ECMAScript规范内部定义，语言中不能使用），基本上相当于使用严格对象相等的标准来检查键的匹配性。
		* undefined 和 null 也是两个不同的键。
		* 虽然 NaN 不严格相等于自身，但 Map 将其视为同一个键。
	
	# Map 的迭代顺序就是插入顺序，类似于 LinkedHashMap

	# 构造函数
		new Map(iterable)

			iterable
				* 用于初始化 Map 键值对的可迭代对象。
				* 例如，包含两个元素的数组，如 [[ 1, 'one' ],[ 2, 'two' ]]
	
	# 初始化
		let dict = new Map([["name", "Javascript"], ["alias", "js"]]);
		console.log(dict); // {'name' => 'Javascript', 'alias' => 'js'}

------------------
this
------------------
	Map.prototype.size
		* 返回 Map 中键值对的数量

	Map.prototype[@@iterator]()
		* 迭代器

	Map.prototype.clear()
		* 清空 map 中的所有元素

	Map.prototype.delete(key)
		* 从 map 中删除元素，如果删除成功返回 true 反之返回 false。

	Map.prototype.entries()
		* 返回 key/value 键值对的迭代器
			let dict = new Map([["name", "Javascript"], ["alias", "js"]]);
			for (const [key, value] of dict.entries()){
				console.log(key, value);
			}

	Map.prototype.forEach(callbackFn, thisArg)
		* 使用 callbackFn 迭代所有项目
			callbackFn
				* 迭代函数， callbackFn(value, key, map)
				* 注意，第一个参数是 Value

			thisArg
				* 可选，迭代时的 this 值

	Map.prototype.get(key)
		* 根据 key 从 Map 中检索 value，如果不存在则返回 undefined

	Map.prototype.has(key)
		* 判断指定的 key 是否在 Map 中存在

	Map.prototype.keys()
		* 返回 map 中所有 key 的迭代器

	Map.prototype.set(key, value)
		* 设置一个键值对到 map 中，返回 this 本身
		* 如果 key 已经存在则会被覆盖

	Map.prototype.values()
		* 返回 map 中所有 value 的迭代器

------------------
static
------------------
	get Map[@@species]
	Map.groupBy()
