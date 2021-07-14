---------------------------------
客户端发送消息
---------------------------------
	# 使用SEND这个COMMAND
		SEND
		destination:/topic/a
		content-type:text/plain

		hello world
		^@

		* destination 指定地址，这个地址很随便，咋写都行
		* 建议
			以/topic开头的为发布订阅模式, 消息会被所有消费者client收到
			以/queue开头的为负载平衡模式, 只会被一个消费都client收到
	
	# 消息确认
		* 协议规定, 可以在SEND命令消息中加入receipt头. 
		* receipt头的值唯一确定一次send.
		
		* server收到有receipt头的SEND命令消息后, 需要回复一个RECEIPT命令消息,
		
		SEND
		destination:/queue/a
		receipt:message-12345

		hello queue a^@

		RECEIPT
		receipt-id:message-12345

		^@
	
