--------------------
配置文件详解		|
--------------------
	* redis.conf
	* 复制到Server目录	
	* 可以参考
		http://download.redis.io/redis-stable/redis.conf
	
	
	daemonize no
		* Redis服务是否后台启动,默认值为:no也就是后台启动.
		* 修改为:yes 那么Redis会以后台启动的方式启动服务

	port 6379
		* 指定Redis监听的端口.默认为:6379

	pidfile /var/run/redis.pid
		* 当Redis在后台的运行的时候,默认会把pid文件放在:/var/run/redis.pid
		* 可以配置为其他的地址,当运行多个redis服务的时候,需要指定不同管道pid,文件,和端口

	notify-keyspace-events
		* 配置事件监听
		* notify-keyspace-events="Ex"  # 监听key的过期事件
			K	键空间通知，所有通知以 以__keyspace@<db>__为前缀 ，针对Key
			E	键事件通知，所有通知以 以__keysevent@<db>__为前缀，针对event
			g	DEL 、 EXPIRE 、 RENAME 等类型无关的通用命令的通知
			$	字符串命令的通知
			l	列表命令的通知
			s	集合命令的通知
			h	哈希命令的通知
			z	有序集合命令的通知
			x	过期事:每当有过期键被删除时发送
			e	驱逐(evict)事件：每当有键因为 maxmemory 政策而被删除时发送
			A	参数 g$lshzxe 的别名，相当于是All
	bind
		* 指定 Redis 只接收来自于该 IP 地址的请求，如果不进行设置，那么将处理所有请求
		* 在生产环境中最好设置该项
	
	requirepass
		* 设置连接密码
	
	maxmemory 
		* 设置最大允许使用的内存
	
	maxmemory-policy
		* 设置内存淘汰策略
	
	save [time] [keys]
		* RDB持久化设置
		* time 表示秒, keys 表示时间内发生改变的key数量
		* 如果在time秒内,有keys个key发生了修改,就发起一次快照
		* 默认的配置
			save 900 1			# 900秒内有1个key发生了改变,就发起一次快照
			save 300 10
			save 60 10000
		* 如果需要关闭,可以注释
		
	rdbcompression
		* 是否压缩RBD持久化到硬盘的数据,默认:yes
		* yes/no
	
	appendonly
		* 是否开启AOF持久化,默认未开启
		* yes/no
	
	appendfilename
		* 设置AOF持久化日志的文件名称
	
	appendfsync
		* 设置AOF持久化的频率,枚举值
			always
				* 有一个写入指令我就备份一次
				* 性能最差,但是可以保证数据

			everysec
				* 每秒备份(记录操作命令)一次(推荐,也是默认)
				* 性能和数据做了折中
			no
				* 服务器心情好,就给你备份.心情不好,就等着晚点做!(其实就是根据性能来)
				* 性能最好,但是数据没保证
	
	auto-aof-rewrite-percentage 100
		* 当AOF文件大小超过上次重写的AOF文件大小的百分之多少时会再次进行重写
		* 如果之前没有重写过.则以启动时的AOF文件大小为依据

	auto-aof-rewrite-min-size 64mb
		* 限制了允许重写的最小的AOF文件大小
		* 通常在AOF文件很小的时候即使其中有些冗余的命令也是可以忽略的
	
	aof-use-rdb-preambl yes
		* 是否开启混合持久化
		* 如果把混合持久化打开,AOF 重写的时候就直接把 RDB 的内容写到 AOF 文件开头
		* 这样做的好处是可以结合 RDB 和 AOF 的优点, 快速加载同时避免丢失过多的数据
		* 当然缺点也是有的, AOF 里面的 RDB 部分是压缩格式不再是 AOF 格式,可读性较差