------------------------------
NameServer					  |
------------------------------
	# 几乎无状态节点，可集群部署，节点之间无任何信息同步
	# 每个节点都保存一份相同的数据，各实例间相互不进行信息通讯
	# 每个Broker与NameServer集群中的所有节点建立长连接，定时注册Topic信息到所有NameServer
	# 支持Broker的动态发现与注册

------------------------------
Broker						  |
------------------------------
	# Broker分为Master与Slave，一个Master可以对应多个Slave，但是一个Slave只能对应一个Master
	# Master与Slave 的对应关系通过指定相同的 BrokerName，不同的BrokerId 来定义
		* BrokerId=0 表示master，非0表示 Slave
		* 只有 BrokerId=1 的Slave服务器才会参与消息的读负载
	
	# 集群中可以有多个 Master，使用不同的 BrokerName 区分


------------------------------
工作流程					  |
------------------------------
	# 启动NameServer
	# 启动Broker，和所有NameServer建立连接
	# 启动Producer，和任意NameServer建立连接
	# 启动COnsumer，和任意NameServer建立连接