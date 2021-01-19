# 修改节点数据大小的限制
	* 服务器和客户端都被设计为严格检查并限制每个Znode的数据大约是1MB(应该尽量小余此值)
	* 可以通过修改 jute.maxbuffer 参数来修改
	
	* jute.maxbuffer 默认值1048575,单位字节,用于配置单个数据节点(ZNode)上可以存储的最大数据大小
	* 需要注意的是,在修改该参数的时候,需要在zookeeper集群的所有服务端以及客户端上设置才能生效


# 服务端的修改
	* zkServer.sh 新增 -Djute.maxbuffer 配置


# 客户端的修改
	* 如果是直接使用zookeeper自带的zkCli.sh读取数据,那么要在zkCli.sh中新增 -Djute.maxbuffer 配置

	* 如果使用客户端(代码),可以在 System.properties 中设置(在初始化zookeeper客户端之前)
		System.setProperty("jute.maxbuffer",String.valueOf(1024 * 10000L)); //10MB
		
