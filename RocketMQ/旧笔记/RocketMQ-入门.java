----------------------------
RocketMQ-入门				|
----------------------------
	# 哈,没错.又是阿里爸爸的神器
		'事务消费'
		'顺序消费'
		'消息重试'
		'定时消息'

	# 有两个版本,一个是开源的.一个是非开源的

	# 是一款分布式,队列模型中间件.具有以下特点
		能够严格保证消息顺序
		能提供丰富的消息拉取模式
		高效的订阅者水平扩展能力
		实时的消息订阅机制
		亿级消息堆积能力

	# 在线文档
		http://www.boyunjian.com/javadoc/com.alibaba.rocketmq/rocketmq-client/3.1.3/_/com/alibaba/rocketmq/client/consumer/listener/MessageListenerOrderly.html

	# 牛逼之处
		强调集群,非单点产品.任意节点高可用.水平可扩展
		海量消息堆积能力,消息堆积后,写入延迟低
		支持上万队列
		消息失败重试机制
		消息可查询
		开源社区活跃
		成熟度(经过阿里双十一考验)
	
	# 缺点
		RocketMQ,并不解决重复消费的问题

	# Metaq3.0版本改名,产品名称改为RocketMQ
	
	# RocketMQ '不遵循任何规范',这个是很叼的,仅仅是参考了各种规范与同类产品的设计思想

	# 版本简介
		1.x和2.x,集群都是需要Zookeeper
		3.x后抛弃了zk,自己内部实现了一个name server的组件
	
	# 下载地址
		https://github.com/alibaba/RocketMQ/releases
	
	# 端口详情
		nameserver	9876
		mqserver	10911
	
	# 安装(Linux)
		1,从git上clone最新的的发布版
			* git clone git@github.com:alibaba/RocketMQ.git
		2,进入目录,执行 install.sh
			* 必须要有maven环境,才能进行编译
		3,编译OK后,进入目录,获取到rocketmq
			* target/alibaba-rocketmq-broker/alibaba-rocketmq
		# 如果懂这个流程,那windows安装也没多大区别
	
	# 环境变量配置
		export ROCKETMQ_HOME=/usr/local/rocketmq/alibaba-rocketmq
		export PATH=$PATH::$ROCKETMQ_HOME/bin

	# 命令管理
		