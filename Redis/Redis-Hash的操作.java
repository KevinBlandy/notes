-----------------------
Redis-Hash的操作		|
-----------------------
	* Hash,数据类型存储的数据与mysql数据库中存储的一条记录非常相似
	* 在这里不涉及算法之类的,仅仅是个数据类型而已

	
	hset key filed value
		* 设置hash的fiel为指定值,如果key不存在,则先创建
	hmset key filed1 value1...filedn valuen
		* 同时对指定的key设置多个值
	hget key filed
		* 获取指定的hash field
	hmget key filed1...filedn
		* 获取全部指定的hash filed
	hincrby key filed integer
		* 给指定Key的指定filed,加上指定的值
	hexists key filed
		* 测试指定key的指定filed是否存在,存在返回1,不存在返回0
	hdel key filed
		* 删除指定key的指定filed
	hlen key
		* 返回指定key的filed数量
	hkeys key
		* 返回指定key的所有filed
	hvals key
		* 返回指定key的所有filed的值
	hgetall key
		* 返回指定key的所有filed和value
	

	* key 就是一个对象,filed,就是以其属性
	* key 也可以理解是一条数据,里面的字段(filed)可以随意增加,可以操作他的value