----------------------------
ActiveMQ-传输协议			|
----------------------------
	# 说白了,要么TCP要么NIO... ...

	# Connector 
		* ActiveMQ 提供,用来实现连接的通讯功能包括
			client-to-broker
			broker-to-broker
		* ActiveMQ 允许客户端使用多种协议来连接
	
	# 配置 Transport Connector
		* 在./conf/activemq.xml 中

		<transportConnectors>
			 <!-- DOS protection, limit concurrent connections to 1000 and frame size to 100MB -->
			 <transportConnector name="openwire" uri="tcp://0.0.0.0:61616?maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600"/>
			 <transportConnector name="amqp" uri="amqp://0.0.0.0:5672?maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600"/>
			 <transportConnector name="stomp" uri="stomp://0.0.0.0:61613?maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600"/>
			 <transportConnector name="mqtt" uri="mqtt://0.0.0.0:1883?maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600"/>
			 <transportConnector name="ws" uri="ws://0.0.0.0:61614?maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600"/>
		 </transportConnectors>
	
	# ActiveMQ 支持的 client-broker 通讯协议如下
		TCP (默认)
			* 默认的Broker配置,必须要序列化数据.消息是通过一个叫 wrie protocol 来序列化字节流
			* 默认清空下,ActiveMQ把 wire protocol 叫做 OpenWire ,它的目的是促使网络上效率和数据快捷交互
			* tcp连接形式:tcp://ip:port?key=value&key=value
			* tcp的优点
				1,可靠性高,稳定性强
				2,字节流方法传递,效率高
				3,支持任何平台

		NIO
			* NIO协议和TCP协议类似,但NIO更侧重于底层的访问操作.它允许开发人员对同一资源可有更多的client调用和服务端有更多的负载
			* 适合NIO的场景
				1,有大量的Client去连接到broker上
					* 一般清空下,大量的client去连接broker是被操作系统的线程数所限制的.因此,NIO的实现,要比TCP需要更少的线程去运行.所以建议使用NIO
				2,可能对Broker有一个很迟钝的网络传输
					* NIO比TCP提供更好的性能
			* NIO连接形式:nio://ip:port?key=value&key=value
			* 配置示例
				<transportConnectors>
					<transportConnector name="tcp" uri="tcp://0.0.0.0:1883"/>			//tcp	
					<transportConnector name="nio" uri="nio://0.0.0.0:61684"/>			//nio
				</transportConnectors>
		UDP
			* 这东西通常用在快速数据传递且不怕数据丢失的情况
			* 连接形式都是一个德行,只是前面协议名称不同
			* 配置示例
				<transportConnectors>
					<transportConnector name="udp" uri="udp://0.0.0.0:61684"/>			//udp
				</transportConnectors>
		SSL	
			* 不讲
		HTTP/HTTPS
			* MQ就是中间件,我相信没有傻逼会把它当WEB服务器使

		VM(如果客户端和broker在一个虚拟机内的话,可以使用这种协议减少开销)
	
