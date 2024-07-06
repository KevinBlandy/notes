--------------------------
WeakMap
--------------------------
	# WeakMap是Map的“兄弟”类型，其API也是Map的子集。
		* WeakMap中的“weak”（弱），描述的是JavaScript垃圾回收程序对待“弱映射”中键的方式。
		* 键只能是Object、继承自Object的类型或非全局注册的 Symbol，尝试使用非法类型设置键会抛出TypeError。值的类型没有限制。
	
		
		* 不可迭代、也没有 clear 方法，因为 key 随时都有可能被回收。
	
	# 垃圾回收
		* Key 不属于正式的引用，不会阻止垃圾回收。
		* 垃圾回收后，键/值对就从弱映射中消失了。
	
	# 构造函数
		new WeakMap(iterable)

--------------------------
this
--------------------------

	WeakMap.prototype.delete()
	WeakMap.prototype.get()
	WeakMap.prototype.has()
	WeakMap.prototype.set()


--------------------------
static
--------------------------
