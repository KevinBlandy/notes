-----------------------
Redis-key操作			|
-----------------------
	* key的命名规则
		* 啥都可以,不能是换行,空格
		* 尽量短,也可以自己定义key的格式
			object-type:id:field
	set key value
		* 存储一个数据到内存
	mset key value key value key value..
		* 可以一次性存储多个key value
	msetnx key value key value...
		* 如果里面有一个key是已经存在的,那么不会执行任何的插入动作
	getset key newvalue
		* 给指定的key设置新的值,并且会返回原始值
	get key
		* 从内存中获得数据
	mget key key key ...
		* 从内存中获取多个指定key的数据
	exists key
		* 测试指定的Key是否存在
	del key1 key2 ...keyn
		* 删除指定的key
	keys pattern
		* 返回指定匹配模式的所有key,注意,返回的是key
		* pattern,有点像正则.但不是
	rename oldkey newkey
		* 重命名key
	dbsize
		* 返回当前数据库的key数量
	expire key seconds
		* 为key指定过期的时间
		* 设置成功后返回 1
	ttl key
		* 返回key剩余的过期秒数
		* 如果数据存在,但是没有设置过期时,间返回 -1
		* 数据已经不存在,返回 -2
	select db-index
		* 选择数据库(通过下标选择)
		* 默认是16个数据库,可以通过修改配置文件来自定义
		* 大约在84行:databases 16
	move key db-index
		* 把key从当前数据库移到指定的数据库
	flushdb
		* 清空当前数据库中的所有key
	flushall	
		* 清空所有数据库中的所有key
	strlen key
		* 返回指定key的value的长度
	set 命令还支持可选的NX选项和XX选项
		* 如果添加了NX选项,那么set操作的时候会判断key值是否存在,如果存在那么不会做覆盖操作.如果不存在,那么就会正常存入
		* 如果添加了XX选项,那么set操作的时候会判断key值是否存在,如果存在,那么就执行操作进行覆盖,如果不存在,那么不会执行任何操作
		* 添加了NX/XX选项的命令,执行成功返回ok,失败返回nil
		* 例:
			* set name Kevin NX		//name这个key如果已经存在.那么就不会进行任何操作	
			* set name Kevin XX		//name这个key必须是存在的.才会进行存储覆盖.反之不会做任何的操作
		* 针对于防止覆盖,其实还有一种写法
			* SETNX key value;		//同样,如果key已经存在那么不会执行写入操作,设置失败返回0,成功返回1
			* MSETNX key value key value ...
				* 如果有一个key是存在的,那么批量的插入都不会执行

-----------------------
Bitmap					|
-----------------------
	setbit key offset value
	getbit key offset
		* 对于bit位的设置和读取操作
		* value只能是 0/1 ,可以用这东西是实现布隆过滤器算法
		* 如果offset过大,则会在中间填充0(最长2^32, 512 MB)
			long bits = 1L << 32;       // 4294967296
			long mb = bits / 8 / 1024 / 1024;
			System.out.println(bits + " bit=" + mb + " mb");    // 4294967296bit=512mb

	
	bitop [operations] [result] [key1] [keyn..]
		* operations 表示,执行不同字符串之间的位操作
		* result 表示计算结果的存储的key
			AND
			OR
			XOR
			NOT(该操作只能接受一个key,因为它是取反操作)
		
		* bitop OR result Monday Tuesday Wednesday Thursday Friday Saturday Sunday

	bitcount key [start] [end]
		* 返回指定key被设置为 1 的位的数量
		* 可以指定开始和结束的位置


	bitpos [key] [value]
		* 返回第一个0或者1的位置

	
	# Redis 客户端的操作
		RedisClient redisClient = RedisClient.create("redis://localhost:6379/0");
		StatefulRedisConnection<String, String> connection = redisClient.connect();
		RedisCommands<String, String> syncCommands = connection.sync();

		syncCommands.setbit("users",1,1);
		syncCommands.getbit("users",1);

		syncCommands.bitopAnd("result","key1","key2");
		syncCommands.bitopOr("result","key1","key2");
		syncCommands.bitopXor("result","key1","key2");
		syncCommands.bitopNot("result","key1");
	
