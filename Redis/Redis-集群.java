-----------------------
Redis-集群				|
-----------------------
	# 就算是有主从复制,每个数据库都要保存整个集群中的所有内容.容易形参木桶效应
		* 木桶效应-一桶水能装多少,是根据最低的那快饿板决定的

	# 使用 Jedis 实现了分片集群.是通过客户端来控制.哪些key数据保存到哪个服务器.如果在水平扩容的时候.必须要停止整个集群服务,手动的进行数据迁移.这样非的不好
		

	# Redis3.0版本的一大特性,就是集群.
		1,所有的Redis节点彼此互联(PING PONG机制),内部使用二进制协议,优化传输速度和宽度
		2,节点的 fail 是通过集群中超过半数的节点检测失效时才生效
			* 超过半数的意思是: 确定一个节点是否挂了,是要集群中的所有节点都去问一遍.如果说超过一个半的人都没得到回复.就确定这个节点挂了


		3,客户端与redis节点直连,不需要中间 proxy 层,客户端不需要连接所有的节点.只需要连接其中的一个即可
		4.redis-cluster 那把所有的物理节点,映射到[0-16383]slot(插槽)上,cluster 负责维护:node -> slot -> value
	
	# 修改配置文件
		1,设置不同的端口:6379,6380,6381(伪集群需要这样搞)
		2,开启集群(配置文件):cluster-enabled yes
		3,指定集群的配置文件:cluster-config-file nodes-xxxx.conf
		4,cluster-node-timeout 15000  , 节点超时毫秒

	# 创建集群
		1,安装ruby环境
			* 因为redis-trib.rb 是由ruby语言编写的,所以要装ruby环境
		2,上传 redis-3.2.1.gem 文件(版本不一定要对应Redis版本)

		3,执行命令来安装文件
			gem install -l redis-3.2.1.gem
		4,使用脚本创建集群
			redis-trib.rb		//这个文件就是ruby脚本

		5,执行命令
			./redis-trib.rb	create --replicas 0 ip:端口 ip:端口 ip:端口
			* --replicas : 这个参数指定了从数数据的数量值
			* 注意这里不能使用127.0.0.1 .否在jedis客户端无法连接到
		
		* 在创建节点的时候,如果任意节点中还有数据.会报错.必须要求所有节点中没有数据存在

	
	# 关于插槽
		* redis集群一共定义了16383个插槽,把这堆插槽平均分配到集群节点中
		* 当执行一个 set 的时候会根据key计算出一个插槽值.找到这个插槽所在的节点.把数据插入
		* 

	# redis-trib.rb用法
	
	# Client操作集群
		redis-cli -c 
			* 等的时候,要添加-c参数.
			* 登录其中一个节点的时候,插入数据.如果这个数据的插槽值属于其他的节点.那么会进行转发.转发,就必须要加这个 -c 参数来进行登录



	# '多键操作的命令,如果每个键都位于同一个节点,则可以正常支持.否则会提示错误'
	# '集群中的节点,只能使用号数据库,如果执行SELECT会报错


-----------------------
Redis-集群配置过程		|
-----------------------
	1,修改redis.conf文件
		* cluster-enabled yes					//开启集群
		* cluster-config-file nodes-6379.conf	//指定集群配置文件的名称
		* cluster-node-timeout 15000			//超时时间
	
	2,创建集群环境
		* 安装Ruby环境,我们需要使用到官方的脚本(Ruby)
			 yum -y install ruby rubygems

		* 安装脚本
			gem install -l redis-3.3.1.gem (自己下载)
	
	3,使用Ruby的脚本创建集群
		* 找到Rubby的脚本(redis-trib.rb-在Redis的安装目录)

		* 执行命令(节点可以有N多个)
			./redis-trib.rb create --replicas 0 192.168.1.135:6381 192.168.1.135:6382 192.168.1.135:6383
			

			>>> Creating cluster
			>>> Performing hash slots allocation on 3 nodes...
			Using 3 masters:
			192.168.1.135:6381
			192.168.1.135:6382
			192.168.1.135:6383
			M: 6b0f9bd031b37088b99b00561e9dd49f4f16eb70 192.168.1.135:6381
				//6b0f9bd031b37088b99b00561e9dd49f4f16eb70 节点唯一的ID值
			   slots:0-5460,5798 (5462 slots) master
				//插槽值:0-163834 会平均分配给集群中的所有节点

			M: 8fb71bdeb667ba29495e0aacb5f56c7caa8eb8df 192.168.1.135:6382
			   slots:5461-10922 (5462 slots) master

			M: 1de75c78d936158c50d2d8900c5057193d570fc0 192.168.1.135:6383
			   slots:5798,10923-16383 (5462 slots) master
			
		* Can I set the above configuration?	//这里输入yes


	4,客户端连接
		./redis-cli -c -h ip -p port 
		* 主要,要添加参数 -c ,不然异常
		* 登录其中一个节点的时候,插入数据.如果这个数据的插槽值属于其他的节点.那么会进行转发.转发,就必须要加这个 -c 参数来进行登录

			  
	

	# 注意
		* '创建集群的Redis服务,必须全部是Empty.全部都要执行flushall'
		* '命令的执行,可以在任意一个节点执行.但是必须保证所有的节点.都是配置OK的'
		* 创建的时候,必须要保证所有的节点都服务开启状态


-------------------------
Redis-插槽和key的分配关系|
-------------------------
	# Redis一共有:163834 个插槽(0-163833)
	# 集群中的每个节点,会平均分配这堆插槽.
	# 执行写入的时候,Redis会计算出key的插槽值,然后存入到指定的节点
	# cluster nodes 
		* 该命令可以查看当前集群的信息
			637c3b26a4973dfc96865790f239940c6e27bb79 192.168.1.135:6383 master - 0 1476711017668 3	connected 10923-16383
			ac09d00a884399630f6421f1f900bc63707a864a 192.168.1.135:6381 master - 0 1476711018678 1	connected 0-5460
			acfb0412cf5f375c07a22cbb174d60b3f6c1fbca 192.168.1.135:6382 myself,master - 0 0 2		connected 5461-10922
		* 可以看到 id 身份 角色 连接数 插槽数等信息
	
	
	# 计算key的插槽值
		* 太长,DBA应该了解.程序狗跳过
	# 所以,这里你也应该想到'Redis最大支持16383个节点的集群'
	
	# 注意
		* 如果key中包含了'{'或者'}'这些个符号,估计要出事,尽量别乱用符号作为key

		

-----------------------
Redis-新增节点			|
-----------------------
	# 先进行集群的配置,ruby环境的安装,ruby脚本的安装运行(具体看上面)
	
	# 先启动服务

	# 执行命令
		./redis-trib.rb add-node ip:port ip:port
		* 注意,第一个,ip:port.是指'准备要加入集群的节点属性'
		* 第二个,ip:port.是指:'已经存在的集群(准备要加入)的任意节点'
	
	# 分配新插槽
		* 如果不分配插槽,那么key永远也写不到这台新添加的服务器上
		* 其实就是从其他的机器上面扣点下来
		./redis-trib.rb reshard ip:port
			* IP和端口任意给,只要是集群之一的节点就OK

		How many slots do you want to move(form 1 to 16384)		//你需要移动多少个插槽?输入,然后回车
				
		What is the receiving node ID?							//是集群中的哪个节点接收?注意,是节点的ID值

		Source node #1:											
			* 输入源节点ID,就从指定的节点扣出指定数量的插槽给新增的节点
			* 输入all,从所有的节点中都扣一点给新的节点
		
		
		最后一个..反正输入yes.就OK了
	
	
	# '这个虽然是新增节点的做法,但是也适用于:手动的分配插槽'
		* 不同的服务器肯定性能不一样,负载不一样.内存不一样.所以.我们可以根据不同的配置.分配不同的插槽
		* 就使用上面的  ./redis-trib.rb reshard ip:port 来完成




-----------------------
Redis-移除节点			|
-----------------------
	# 要想删除节点,必须严格执行两步
		1,执行脚本: ./redis-trib.rb reshard ip:port(节点的任意IP)
		2,选择需要转移的插槽数量.
			* 其实就是杀驴卸磨,移除你之前先把你的插槽值给别人
		
		3,执行脚本完成删除
			./redis-trib.rb del-node ip:port id
			//参数-要删除的节点IP:端口 ID值

-----------------------
Redis-集群故障转移		|
-----------------------
	# 如果集群中的某个节点宕机
	# cluster nodes
		......disconnected			//表示宕机
	# 只要有一个节点宕机,那么整个节点就会不可用
	# 故障机制
		1,集群中的每个节点,都会定期往其他节点发送ping命令,并且通过有没有收到回复来判断目标节点是否宕机
		2,集群中每一秒就会随机选择五个节点,然后选择其中最久没有响应的节点执行ping
		3,如果一定时间内,目标节点都没有响应.那么该节点就认为'疑似下线'
		4,当集群中超过半数的节点,都认为目标节点'疑似下线',那么该节点就会被标记为下线
		5,'当集群中任何一个节点宕机,就会导致整个集群宕机.插槽,你懂'
	
	# 处理故障问题
		1,在Redis集群中,可以使用主从模式,实现某一个节点的高可用
		2,当该节点(master)宕机后,集群会把该节点从数据库(slave)转换为(master),继承完成集群服务
	



	
-----------------------
Redis-Jedis操作集群		|
-----------------------
	/**
	 * Jedis操作集群演示
	 * */
	public class RedisCluster {
		public static void main(String[] args) {
			//创建节点SET集合
			Set<HostAndPort> nodes = new HashSet<HostAndPort>();
			//仅仅需要添加其中一个节点就OK
			nodes.add(new HostAndPort("192.168.1.135",6381));
			//根据节点创建连接
			JedisCluster jedisCluster = new JedisCluster(nodes);
			//jedisCluster 是不用关闭的,因为内部已经关闭了
			System.out.println(jedisCluster.set("name", "KevinBlandy"));
			System.out.println(jedisCluster.get("name"));
		}
	}