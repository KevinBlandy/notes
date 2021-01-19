--------------------------------
Dubbo-Zookeeper					|
--------------------------------
	# Zookeeper会有单独的笔记讲解,其实是大数据里面的一种技术
	# 此处笔迹,仅仅是它作为 Dubbo 的注册中心相关的笔记
	# Zokkeeper,承担了'注册中心'的角色
		* 服务提供者注册在里面
		* 服务器消费者在里面查找注册
	
	# 各个版本下载地址
		http://apache.fayea.com/zookeeper/
	# 默认监听端口
		2181
	# maven 依赖
		<dependency>
			<groupId>org.apache.zookeeper</groupId>
			<artifactId>zookeeper</artifactId>
			<version>3.4.9</version>
		</dependency>
		<dependency>
			<groupId>com.github.sgroschupf</groupId>
			<artifactId>zkclient</artifactId>
			<version>0.1</version>
		</dependency>


	
---------------------------------
Dubbo- Windos 下Dubbo监控中心安装|
---------------------------------
	1,下载Zookeeper,解压到任意盘符
	2,修改配置文件
		* conf/zoo_sample.cfg
		* 把这个文件改名为: zoo.cfg
		
	
		tickTime=2000
		initLimit=10
		syncLimit=5
		dataDir=E:/zookeeper-data
			* 也就是zookkeeper的数据存放路径
			* 该目录必须存在,且只用修改这个属性就OK,其他的不用动.我只是复制出来给看看
		dataLogDir=E:/zookeeper-log
			* 日志文件目录
		clientPort=2181
			* 默认端口

		#maxClientCnxns=60
		#autopurge.snapRetainCount=3
		#autopurge.purgeInterval=1
	3,运行服务
		* bin/zkServer.cmd 双击

---------------------------------
Dubbo- Linux 下Dubbo监控中心安装|
---------------------------------
	* 跟windows一样,不同的是启动方式
	* 启动
		./zkServer.sh start
	* 关闭
		./zkServer.sh stop
	* 查看控制台信息
		tail -f ./zookeeper.out
	
	* 查看进程
		jps
		QuorumPeerMain		//zookeeper的进程名



---------------------------------
Dubbo- 集群						 |
---------------------------------
	# Dubbo建议使用Zookeeper来作为服务的注册中心
	# Zookeeper集群中,只要有过半节点是正常的清空下,那么整个集群对外就是可用的
	# 由于上述的特性,所有zk集群.一般'集群的机器数量,是奇数'比较合适
	# zookeeper集群的高可用,提现在Leader的算法,当Leader挂掉后,剩下的节点,会通过一个算法来重新选举出新的Leader

	1,修改 /etc/hosts 文件
		节点IP				节点名
		123.207.122.145		zk-01
		59.110.140.157		zk-02

	2,在 zookeeper 目录下,创建目录
		data		//存放数据
		logs		//存放日志

	3,复制 conf 目录下的 zoo_sample.cfg 文件,到当前目录,名为:zoo.cfg
		cp ./zoo_sample.cfg ./zoo.cfg

	4,修改配置文件
		tickTime=2000
			* zookeeper服务器集群之间或者是与客户端之间的心跳间隔,每隔多少时间(秒)发一个心跳包

		initLimit=10
			* 节点超过'多少个心跳'未收到,就认为有问题
			* 10 * 2000 = 20 秒
			* 也就是说,超过 20 秒没有收到节点n的心跳,就认为它挂了

		syncLimit=5
			* 主从之间发送消息,请求和应答的时间长度不能超过'多少个心跳'的长度
			* 5 * 2000 = 10 秒
			* 主从请求应答,不能超过10秒


		dataDir=/usr/local/zookeeper/zookeeper-3.4.9/data
			* 也就是zookkeeper的数据存放路径
			* 该目录必须存在,且只用修改这个属性就OK,其他的不用动.我只是复制出来给看看

		dataLogDir=/usr/local/zookeeper/zookeeper-3.4.9/logs
			* 日志文件目录

		clientPort=2181
			* 应用程序访问的,默认端口

		#maxClientCnxns=60
		#autopurge.snapRetainCount=3
		#autopurge.purgeInterval=1
		# 集群配置
		server.1=zk-01:2881:3881
		server.2=zk-02:2882:3882
		--------------------------------------------------------------
		# 详解该属性(集群属性)
			* 这个属性就是集群的关键点

			server.A=B:C:D

			A
				* 是一个数字,表示为集群中第几号服务器
			
			B
				* 表示该服务器的IP地址,或者是与IP做了映射的主机名
			
			C
				* 该端口,用来做集群成员之间的信息交换.表示该服务器与集群中leader服务器的交换信息的端口
			
			D
				* 当集群中Leader挂掉后,集群成员会通过该端口来进行Leader选举
		--------------------------------------------------------------
	5,在dataDir目录下创建myid文件
		touch ./myid
	
	6,在myid文件中输入对应的编号
		* 服务器是第几个服务器,里面就写几
	
	7,启动Zookeeper
		./bin/zkServer.sh start
	
	8,查看当前节点状态/角色
		./bin/zkServet.sh status
	
	9,Dubbo连接集群
		dubbo.registry.address=ip:port,ip:port,ip:port
		* 连接集群中的节点,有多少个连多少个
		* 使用逗号分隔
		

		
---------------------------------
Dubbo- 集群的迁移,升级			 |
---------------------------------
	# Zookeeper集群迁移思路



		
		

		
	

	
	
	