------------------------
Set
------------------------
	# 一种新集合类型，在很多方面都像是加强的Map，这是因为它们的大多数API和行为都是共有的。
		* 集合中的元素是唯一的，且维护了插入顺序
		* 与Map类似，Set可以包含任何JavaScript数据类型作为值。
		* 集合也使用SameValueZero操作（ECMAScript内部定义，无法在语言中使用），基本上相当于使用严格对象相等（===）的标准来检查值的匹配性。
			* 向 Set 加入值时认为 NaN 等于自身，而精确相等运算符认为 NaN 不等于自身。

		https://developer.mozilla.org/zh-CN/docs/Web/JavaScript/Reference/Global_Objects/Set

	# 构造函数
		
		new Set(iterable)
			
			iterable
				* 使用可迭代数据，初始化 Set

------------------------
this
------------------------
	Set.prototype.size
		* 元素数量，即 Set 长度
	Set.prototype[@@iterator]()
	Set.prototype.add(value)
		* 添加元素到 Set ，返回 this

	Set.prototype.clear()
		* 移除所有元素

	Set.prototype.delete(value)
		* 删除指定的元素，删除成功返回 true 失败的话返回 false

	Set.prototype.difference()
	Set.prototype.entries()
		* 返回元素的迭代器，key 和 value 都是一样的。


	Set.prototype.forEach(callbackFn, thisArg)
		* 迭代集合
			callbackFn
				* 迭代函数，callbackFn(value, key, map)
				* key 和 value 始终相同
			
			thisArg
				* 可选，执行迭代函数时的 this

	Set.prototype.has(value)
		* 判断集合是否包含指定的值

	Set.prototype.intersection()
	Set.prototype.isDisjointFrom()
	Set.prototype.isSubsetOf()
	Set.prototype.isSupersetOf()
	Set.prototype.keys()
		* 完全等价于 values() 方法。

	Set.prototype.symmetricDifference()
	Set.prototype.union(other)
		* 交集
		* 返回新的集合，包含了 other 集合和 this 集合中的所有元素。

			other
				* 其他集合或者是类集合


	Set.prototype.values()

------------------------
static
------------------------
	get Set[@@species]

------------------------
应用
------------------------
	# 集合的运算
		let a = new Set([1, 2, 3]);
		let b = new Set([4, 3, 2]);

		// 并集
		let union = new Set([...a, ...b]);
		// Set {1, 2, 3, 4}

		// 交集
		let intersect = new Set([...a].filter(x => b.has(x)));
		// set {2, 3}

		// （a 相对于 b 的）差集
		let difference = new Set([...a].filter(x => !b.has(x)));
		// Set {1}
