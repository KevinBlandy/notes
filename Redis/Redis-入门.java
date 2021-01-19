-----------------------
Redis-入门				|
-----------------------
	* Redis-NoSql数据库
	* MySQL:文件存储在硬盘上
	* Redis:文件存储在内存中,但是可以序列化到硬盘
	* 介绍
		Redis(Remote Dictionary Server,远程数据服务)的缩写
		由一个意大利人开发的,内存高速缓存数据库
		使用C语言编写,数据模型为key-value
		支持丰富的数据结构
			String			//字符串,甚至是二进制数据
			List			//可以模拟队列和栈结构
			Hash			//哈希表
			Set				//无序集合
			Sorted			//有序集合
			Stream
		可以持久化,保证了数据的安全
	* 缓存
		数据缓存
		页面缓存(smarty)
	* key的命名规则
		* 啥都可以,不能是换行,空格
		* 尽量短,也可以自己定义key的格式
			object-type:id:field
	* 这哥们儿还支持虚拟内存
		* 就是把硬盘的内存,腾一点出来.给计算机内存使用
	* 支持分布式
		* 一主多从
		* 一从多主
	* 支持订阅发布功能
	* 一台机器可以运行N个 Redis实例
		- 加载不同的配置文件即可.
		- 配置文件需要配置不同的端口和PID文件位置

-----------------------
Redis-安装				|
-----------------------
	Linux(CentOS6.4)
		* 把文件包通过FTP上传至Linux服务器,保存在某个目录下
		* 安装编译依赖
			yum -y install gcc tcl
		* tar zxvf redis-x.x.xx.tar.gz	//解压完成后会生成一个文件夹目录、
		* cd redis-x.x.xx				//进入解压后的文件夹目录
			* 设置安装目录 ./configure --prefix=/usr/local/redis
		* make							//执行执行编译指令就
		* make install					//编译没有问题,执行安装


		* cd src						//进入src目录
			* redis-check-aof			
			* redis-check-dump
			* redis-cli					//终端脚本
			* redis-sentinel
			* redis-server				//启动redis服务的脚本
		* mkdir /usr/local/redis		//创建reids运行目录
		* cp redis-cli redis-server /usr/local/redis	//拷贝文件到运行目录
		* cd ..							//退回到解压文件目录
			* redis.conf				//配置文件
		* cp redis.conf /usr/local/redis//拷贝配置文件到redis的运行目录
		* cd /usr/loacl/redis			//进入redis运行目录
		* ./redis-server				//启动服务,前台方式
		//后台启动redis服务
		* vim redis.conf				//打开配置文件
			* daemonize no				//17行代码大约,no:代表前台启动,yes代表后台启动
		* ./redis-server redis.conf		//重新载入配置文件,其实后台已经启动了redis服务
		* ps -A | grep redis			//查看redis进程是否启动
		//登录redis服务
		* ./redis-cli
		//登录远程的redis服务器
		./redis-cli -h localhost -p 6379 -a password
			* ip默认为本地
			* 端口默认是6379
	Windows
		* 直接解压就OK
		* 在解压目录手动的创建配置文件:redis.conf
		* 开启服务
			* 直接双击redis-server.exe 启动服务
			* 加载配置文件启动服务:redis-server.exe redis.conf
		* 双击redis-cli 启动客户端

-----------------------
Redis-支持的数据类型	|
-----------------------	
	Strings		(Binary-safe strings)
	Lists		(Lists of binary-safe strings)
	Sets		(Sets of binary-safr strings)
	Sorted sets	(Sorted sets of binary-safe strings)
	Hash

-----------------------
Redis-常用命令			|
-----------------------	
	redis开启服务
		redis-server ./redis.conf

	
	redis关闭服务
		1,redis-cli shutdown		//通过客户端关闭服务
		2,killall redis-server		//通过结束进程关闭服务
		

	1,把Redis的安装软件(源码)下载到本地,上传到Linux服务器
	2,解压tar.gz文件
		* tar -zxvf redis.tar.gz
		* 解压完成后,会在当前的目录生成一个同名文件夹
	3,进入解压目录,进行编译源码,并且安装文件
		* make
		* 如果没安装C++的编译环境,那么需要先进行安装
	4,编译成功后,进入src目录
		* 一大波东西,无视不管,最重要的几个文件(绿色的)
	5,把几个绿色的文件复制到指定的目标目录
		1,redis-benchmark		//压力测试工具
		2,redis-check-aof		//检查aof文件完整性的工具
		3,redis-check-dump		//检查数据文件的完整性工具
		4,redis-sentinel		//监控集群运行状态
		5,redis-server			//服务端
		6,redis-cli				//客户端
	6,在目标目录创建配置文件/建议从安装目录进行复制
		* redis-conf
	7,安装完毕,启动服务
		* ./redis-server redis.conf			//指定启动服务器加载的配置文件

-----------------------
Redis-关于版本号		|
-----------------------
	Redis的版本规则
		次版本号(第一个小数点后的数字),为偶数的,则是稳定版.为奇数的则是非稳定版
	
	# Windows 官方是不支持Windos平台的,我们现在使用的Windos平台的Redis,是微软自己从github上把源码扒下来,编译,发布,维护的!
	  所以,windos的 Redis的版本都会低于 Linux 版
	
		https://github.com/microsoftarchive/redis

-----------------------
Redis-安装-针对3.0		|
-----------------------
	1,下载
	2,解压
	3,copy 到 /usr/local/redis目录
	4,make				//编译,有可能会出现问题.依赖缺省.就要先装依赖
	5,make install		//安装
	6,OK~


-----------------------
Redis-后台运行			|
-----------------------
	1,在启动参数后添加 & 号
		./redis-server ./redis.conf &

	2,修改配置文件
		daemonize no				
		* 17行代码大约,no:代表前台启动,yes代表后台启动
		redis-server ./redis.conf	


-----------------------
Redis-配置文件			|
-----------------------
		port 6379						//指定端口
		pidfile	/var/run/redis.pid		//指定PID文件地址
		daemonize no					//是否在后台启动
		requirepass	xxx					//设置Redis密码
		slaveof ip port					//设置主从

		
	
-----------------------
过期key的删除策略		|
-----------------------
	# 定期删除
		* redis默认是每隔 100ms 就随机抽取一些设置了过期时间的key,检查其是否过期,如果过期就删除
		* 是随机抽取,而不是遍历所有.因为如果设置了过期的key过多的话,每100s就进行一次遍历会很伤性能
	
	# 惰性删除
		* 定期删除策略,可能会导致部分设置了过期时间的key,在过期后没有及时的被删除
		* 惰性删除发生在,key可能早就过期了但是没删除,于是在你进行访问的时候,会对这个过期的key进行删除

-----------------------
内存淘汰机制			|
-----------------------
	# 内存淘汰机制
		* 配置文件中的配置选项:maxmemory-policy 
		* 枚举值值
			volatile-lru
				* 从已设置过期时间的数据集(server.db[i].expires)中挑选最近最少使用的数据淘汰

			volatile-ttl
				* 从已设置过期时间的数据集(server.db[i].expires)中挑选将要过期的数据淘汰
			
			volatile-lfu
				* 最近最不经常使用算法,从设置了过期时间的键中选择某段时间之内使用频次最小的键值对进行清除

			volatile-random
				* 从已设置过期时间的数据集(server.db[i].expires)中任意选择数据淘汰

			allkeys-lru
				* 当内存不足以容纳新写入数据时,在键空间中,移除最近最少使用的key(这个是最常用的)

			allkeys-lfu
				* 最近最不经常使用算法,从所有的键中选择某段时间之内使用频次最少的键值对进行清除

			allkeys-random
				* 从数据集(server.db[i].dict)中任意选择数据淘汰

			noeviction
				* 禁止驱逐数据,也就是说当内存不足以容纳新写入数据时,新写入操作会报错,这个应该没人使用吧
	
	# LFU(使用频率最低的)
		* 为每个entry维护一个计数器, 每命中一次+1, 淘汰时找最小的
	
	# LRU(最近最少使用的)
		* 每次命中将entry移到队列头部, 淘汰时找队尾即可, 元素即最久没有使用过的元素

	
-----------------------
持久化					|
-----------------------
	# RDB 持久化
		* 默认开启
		* 配置选项:save
			save 900 1			# 900秒内有1个key发生了改变,就发起一次快照
			save 300 10			# 300秒内有10个key发生了改变,就发起一次快照
			save 60 10000		# 60秒内有10000个key发生了改变,就发起一次快照
		
			* 数据修改的频率高,备份的频率也高
			* 数据修改的频率低,备份的频率也低

		* 如果数据非常多(10-20GB),就不适合频繁该持久化操作

		* 快照持久化,持久化的是文件的名字和存储的位置
		
		* Redis启动后,会读取RDB文件,把数据从硬盘写入内存.据吹牛逼说.1GB数据,读取到内存需要20-30秒.当然,不同的服务器肯定是有差异的
		* RDB的快照过程
			1,Redis使用fork函数,复制一份当前的进程(父进程)的副本(子进程)
			2,父进程继续接收客户端发来的命令,而子进程开始把把内存中的数据写如到硬盘
			3,当子进程写完所有数据库后,会用该临时文件替换旧的RDB文件

		* RDB文件是通过压缩的(默认开启压缩),可以通过配置 rdbcompression 参数来禁止压缩
			rdbcompression yes/no

			* 压缩消耗性能,但是降低磁盘空间
			* 不压缩较占用磁盘空间
	
	# AOF持久化
		* 本质:把用户执行的每个写指令(添加,修改,删除)保存到文件中,还原的时候,就是仅仅执行了指定的语句而已
		* Redis启动的时候,会执行AOF文件,达到数据恢复的效果
		* 开启AOF持久化
			* 会清空redis内部的数据,所以安装的时候就建议开启
			* 在redis.conf配置文件中
				appendonly no					//改为yes即可

				appendfilename appendonly.aof	//持久化文件的名称

		* 配置文件被修改,需要删除旧进程,在根据配置文件重启新的进程

		* AOF追加持久化的备份频率,配置项:appendfsync 
				always
					* 有一个写入指令我就备份一次
					* 性能最差,但是可以保证数据

				everysec
					* 每秒备份(记录操作命令)一次(推荐,也是默认)
					* 性能和数据做了折中

				no
					* 服务器心情好,就给你备份.心情不好,就等着晚点做!(其实就是根据性能来)
					* 性能最好,但是数据没保证

		* 为AOF备份文件做优化处理
			redis-cli bgrewriteaof
				* 这个命令其实就是把备份文件内容进优化压缩
				* 例如:多个incr指令变成了一个set指令
			
		* 重写策略的参数设置
			auto-aof-rewrite-percentage 100
				* 当AOF文件大小超过上次重写的AOF文件大小的百分之多少时会再次进行重写
				* 如果之前没有重写过.则以启动时的AOF文件大小为依据
			
			auto-aof-rewrite-min-size 64mb
				* 限制了允许重写的最小的AOF文件大小
				* 通常在AOF文件很小的时候即使其中有些冗余的命令也是可以忽略的
			
			* 假设用户对Redis设置了如上配置选项并且启用了AOF持久化
			* 那么当AOF文件体积大于64mb并且AOF的体积比上一次重写之后的体积大了至少一倍(100%)的时候,Redis将执行BGREWRITEAOF命令

	# 混合持久化的支持
		aof-use-rdb-preambl yes

		* 如果把混合持久化打开,AOF 重写的时候就直接把 RDB 的内容写到 AOF 文件开头
		* 这样做的好处是可以结合 RDB 和 AOF 的优点, 快速加载同时避免丢失过多的数据
		* 当然缺点也是有的, AOF 里面的 RDB 部分是压缩格式不再是 AOF 格式,可读性较差


	# 总结
		* RDB持久化跟AOF持久化是一个互补关系
		* RDB持久化做大的文件备份

		* AOF做细致的文件备份
