-----------------------
Redis-string操作		|
-----------------------
	* string 是redis最基本的类型
	  redis的string可以包含任何数据:图片(二进制数据),序列化的对象
	  单个value值最大的上限是1GB
	  如果仅仅只用string类型,redis就可以理解是加上了持久化功能的memcache
	set key value
		* 保存嘛,值类型是string
	mset key1 value1 key2 value2 ..keyn valuen 
		* 一次设置N个
	mset key1 key2...keyn
		* 一次获取多个
	incr key
		* 对key的值做++操作后,返回新值
		* 如果key不是数字的话,会报错
		* 可以操作新key,或者旧key.如果是新key,那很显然值就是1了
	decr key
		* 对key的值做--操作后,返回新值
		* 同上
	incrby key Integer
		* 对指定key,添加指定的值
		* 同上
	decrby key Integer
		* 对指定key减去指定值
		* 同上
	append key value
		* 对指定key追加指定的value
	substr key start end
		* 返回截取后的字符串
		* 从第几个字符开始,到第几个字符结束,包含开始位置,也包含结束位置
	