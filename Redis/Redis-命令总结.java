-------------------
Redis-key的操作		|
-------------------
	set key value NX
		# 如果指定key已经存在,那么该插入操作失效
		# 不能覆盖,还有一个操作:SETNX key value,成功返回1,失败返回0
	set key value XX
		# 如果指定的key不存在,那么该插入操作失效
		# 必须覆盖
	keys *
		# 根据一个表达式获取到匹配成功的key,如果是*号,则返回所有的key
	expire key 200
		# 指定一个key的过期时间,超过时间那么key会被删除
		# 该操作单位是秒,也存在其他的命令可以根据:unix时间戳,毫秒等时间单位来进行设置
		# 对该值进行重新设置的时候,会取消定时删除

	ttl key
		# 返回key的剩余过期时间
		# 如果返回值是:
			-1,没有设置生成时间,永久生效
			-2,则表示该key不存在,或者已经被删除
	del key
		# 删除指定的key
	del key1 key2...
		# 批量删除指定的key
	getset key newvalue
		# 给指定的key设置一个新的value,并且返回旧的value
	exists key
		# 测试指定的key是否存在
		# 存在返回1,不存在返回0 

	rename oldkey newkey
		# 重命名key
	dbsize
		# 返回当前数据库的key的数量
	flushdb
		# 清空当前数据库中的所有key
	flushall
		# 清空所有的数据库中的key
	select index
		# 跳转到指定的数据库(默认0-15,一共16个数据库)
	move key index
		# 把指定的key移动到指定下标的数据库
	strlen key
		# 返回指定key的value的长度(length)
	type key
		# 返回指定数据的,数据类型

-------------------
Redis-String操作		|
-------------------
	set key value
		# 设置值和value,如果key已经存在.那么就会覆盖原值,value可以选择使用双引号(如果value中存在空格,那么必须要使用双引号)
	setnx key value 
		# 如果指定的key已经存在,那么该操作失效,防止覆盖
	mset key value key value...
		# 一次性设置多个key和value
	msetnx key value key value...
		# 一次性设置多个key和value
		# 注意,如果该操作中有一个key是已经存在的.那么,这个批量插入全部失效
	get key
		# 获取指定key的value
	mget key key key...
		# 一次性获取多个指定key的值
	incr key
		# 对指定的key的value进行++操作,如果不存在,那么默认该key的值0,进行+1
		# 如果key的数据类型非数字,那么会抛出异常
	decr key
		# 对指定的key进行--操作,其余同上
	incrby key Integer
		# 对指定key,添加指定的值
		# 同上
	decrby key Integer
		# 对指定key减去指定值
		# 同上
	append key value
		# 对指定key追加指定的value
		# 可以对字符串进行添加后缀操作
	substr key start end
		# 返回截取后的字符串
		# 从第几个字符开始,到第几个字符结束,包含开始位置,也包含结束位置
-------------------
Redis-List操作		|
-------------------
	# list元素可重复
	lpush key string
		# 在key对应的list头部添加字符串元素,执行成功返回该list添加后的长度
		# 如果该key的list不存在,那么就创建
	rpush key string
		# 在key对应的list尾部添加字符串元素
	lpop key
		# 从list头部删除元素
	rpop key
		# 从list尾部删除元素
	llen key
		# 返回list的长度,key不存在返回0,key对应的类型不是list会报错
	lrange key start end
		# 返回指定区间内的元素,下标从0开始
		# 可以使用负数:-1代表倒数第一个,-2代表倒数2个...类推
		# lrange list 0 -1  :就是从第一个开始,获取到最后一个
		# 该获取操作,既包含开始元素,也包含结束元素
	ltrim key start end
		# 截取list,仅保留指定区间内元素
	lrem key count value
		# 从指定的集合(key)中删除,指定值(value).从左往右,删除指定的数量(index)		
		# 如果count为0,会从该集合中删除所有指定value的key
	lindex key index
		# 返回指定集合的指定角标的value,是不会删除元素的

-------------------
Redis-Set	操作		|
-------------------
	# 元素不可重复
	sadd set value
		# 添加一个元素到set集合中
		# 成功返回1,该元素已经存在返回0
	srem set value
		# 从set中移除指定的元素,成功返回1
	smove set1 set2 value
		# 从set1的集合中移除value,添加到set2
	scard set
		# 返回set中的元素个数
	sismember set value
		# 判断value是否在指定的set中
	sinter set set...set
		# 返回所有set的交集
	sunion set set...keyn
		# 返回所有set的并集
	sdiff set set ...set
		# 返回所有set的差集
	smembers set
		# 返回key对应的set的所有元素,返回结果无序,跟插入顺序不一样
	
-------------------
Redis-SortedSet	操作	|
-------------------
	# 可以排序的set,每个value都会有一个权重
	zadd key score value
		# 添加元素到集合,元素在集合中存在则更新对应score
		# score,也就是用来排序的值,传说中的权重
		# 允许相同值的存在,但是不允许member相同,已经存在会被替换掉
	zrem key value 
		# 删除指定元素,1.表示删除成功,如果元素不存在返回0
	zincrby key incr value
		# 按照incr幅度增加对应value的score值,返回增加后score值
	zrank key value
		# 返沪指定元素在集合中的排名(下标),集合元素是按从小到达排序的
	zrevrank key value
		# 同上但此时的集合确实从大到小排序的
	zrange key start end
		# 类似lrange 操作从集合中指定区间的元素,返回的是有序结果,Set是正序排序
		# 注意,这个开始结束值是下标
	zrevrange key start end
		# 同上,返回结果是逆序的
	zcard key
		# 返回集合中元素的个数
	zscore key value
		# 返回给定元素对应的score 
	zremrangebyrank key min max
		# 删除集合中,指定下标的元素,集合是从小到大排序的、
		# 且包含开始,也包含结束
	
-------------------
Redis-Hash	操作		|
-------------------
	hset key filed value
		# 设置hash的fiel为指定值,如果key不存在,则先创建
	hmset key filed1 value1...filedn valuen
		# 同时对指定的key设置多个值
	hget key filed
		# 获取指定的hash的指定,field的value
	hmget key filed1...filedn
		# 获取多个指定的hash filed
	hincrby key filed integer
		# 给指定Key的指定filed,加上指定的值
	hexists key filed
		# 测试指定key的指定filed是否存在,存在返回1,不存在返回0
	hdel key filed
		# 删除指定key的指定filed
	hlen key
		# 返回指定key的filed数量
	hkeys key
		# 返回指定key的所有filed
	hvals key
		# 返回指定key的所有filed的值
	hgetall key
		# 返回指定key的所有filed和value
		# 返回的数据格式,第一个为key,第二个为value,第三个为key,第四个为value... ...类推


	