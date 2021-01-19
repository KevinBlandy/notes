-----------------------------------
WebSocket-消息分片,注解式			|
-----------------------------------
	# 当消息过大的时候,我们选择拆分消息为多个消息.
	# 二进制消息与文本消息都一样
	# 代码
		@OnMessage
		public void onMessage(String message,boolean isLast){
			//balabalabal
		}

		@OnMessage
		public void onMessage(byte[] message,boolean isLast){
			//balabalabal
		}

		@OnMessage
		public void onMessage(ByteBuffer[] message,boolean isLast){
			//balabalabal
		}

	# 如果当前消息是消息体中的最后一部分消息,isLast = true,反之,则为 false


-----------------------------------
WebSocket-消息分片,编程式			|
-----------------------------------
	# MessageHandler
	# 处理客户端(发送给服务端)的消息,可以有一个或者多个.由session维护
	# 该接口定义了编程式端点,接收入站消息时,注册其兴趣的所有方式.
	# 可以使用该接口来选择是接收文本信息,还是二进制信息
	# 也可以接收整个消息,或者是当消息到达时接收较小的片段(消息特别大的时候)
	# 该接口,内部是两个接口(都实现了MessageHandler),我们需要实现该接口中,的接口实例
		interface Whole<T> extends MessageHandler {
			void onMessage(T message);		
		}
		//该接口就是分片消息,编程式的接口
		interface Partial<T> extends MessageHandler {

			void onMessage(T partialMessage, boolean last);
		}
