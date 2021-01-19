-----------------------
Redis-Set集合的操作		|
-----------------------
	* Redis 的set 是String类型的'无序集合'(跟JAVA是不是很像)
	* Set集合可以包含2的32次幂-1个元素
	* 除了基本的增删改查操作之外,还有'包含集合的取并集'操作
	* 还有交集,差集等操作
	* 每个集合中的元素,不能重复
	
	sadd key value
		* 添加一个元素到key对应的set集合中

		* 成功返回1,该元素已经存在返回0,如果这个set不存在,抛出异常

	srem key value
		* 从key对应的set中移除给定的元素,成功返回1

	smove key1 key2 value
		* 从key1的集合中移除value,添加到key2

	scard key
		* 返回set元素的个数

	sismember key value
		* 判断value是否在指定的set中

	sinter key1 key2...keyn
		* 返回所有key的交集

	sunion key1 key2...keyn
		* 返回所有key的并集

	sdiff key1 key2 ...keyn
		* 返回所有key的差集

	smembers key
		* 返回key对应的set的所有元素,返回结果无序
	

	//卧槽,现在用笔记本键盘感觉好不熟悉啊！！