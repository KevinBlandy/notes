------------------------
ActiveMQ-网络连接		|
------------------------
	# ActiveMQ的networkConnector是什么?
		* 在一些场景下,需要多个ActiveMQ的Broker做集群,那么就涉及到Broker之间的通信,这就是:ActiveMQ的networkConnector

	# ActiveMQ的networkConnector默认是单向的,也就是一个broker发,一个broker收.这是所谓的 桥接

	# ActiveMQ也支持双向连接,创建一个双向的通道,对于两个broker,可以使用同一个通道来收发消息,通常使用 duplex connector来映射

	

------------------------
ActiveMQ-静态网络连接	|
------------------------
	# static networks
		static networkConnector是用于创建一个静态的配置,对于网络中的多个broker.这种协议用于复合url,一个复合url包括多个url地址.格式如下
			static:(uri1,uri2,uri3...)?key=value
	
	# 配置如下
		<networkConnectors>
			<networkConnector name="lcal network" uri="static://(tcp://ip:port,tcp://ip:port)"/>
		</networkConnectors>
		* 如果没有现成的标签,需要自己去配置