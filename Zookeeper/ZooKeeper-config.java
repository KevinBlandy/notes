--------------------------------
配置				|
--------------------------------
	# 配置文档
		http://zookeeper.apache.org/doc/current/zookeeperAdmin.html#sc_maintenance



--------------------------------
配置							|
--------------------------------

tickTime=2000
	* zookeeper服务器之间或者与客户端保持心跳的时间间隔

initLimit=10
	* zookeeper接受的客户端数量

syncLimit=5
	* Leader和Follower之间发送消息,请求,应答时间的长度
	* 最长不能超过多少个'tickTime'

dataDir=/tmp/zookeeper
	* 数据目录

dataLogDir
	* 日志目录

clientPort=2181
	* 客户端端口

server.[id]=[host]:[heart-port]:[election-port]
	* 集群配置
	* [id] 节点的id
	* [host] 节点的host
	* [heart-port] 节点心跳/数据交互端口
	* [election-port] 节点的选举端口
