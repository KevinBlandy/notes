-----------------------------------
订阅和取消订阅
-----------------------------------
	# 订阅消息用SUBSCRIBE命令
		SUBSCRIBE
		id:0
		destination:/topic/foo
		ack:client

		^@
	
		* id, 一个client对于一个server可以订阅多次, 甚至对于同一个目的地址都可以订阅多次.
		* 为了唯一确定一次订阅, 协议规定必须包含id header, 此id要求在同一连接中唯一.

		* ack header, 告诉server, server如何确认client已经收到消息.
		* 有三个值: auto, client, client-individual
			auto
				* 表示当server发出消息后就立即确认client收到了消息. 也就是说当client收到消息后不会对server进行确认
			client
				* 表示只有当server收到client的ack后才确认client收到了消息, 也就是说client需要对server发ack进行确认.
				* 这个确认是累积的, 意思是说收到某条消息的ack, 那么这条消息之前的所有的消息, server都认为client已收到.
			client-individual
				* 与client类似. 只不过不是累积的. 每收到一条消息都需要给server回复ack来确认.
	
	# 取消订阅用UNSUBSCRIBE这个命令
		UNSUBSCRIBE
		id:0

		^@
				
		* 消订阅相对来说比较简单只需要传一个id header.
		* 这个id header的值来自订阅时id header值. 这样server才能唯一确定到底要取消哪个订阅.
