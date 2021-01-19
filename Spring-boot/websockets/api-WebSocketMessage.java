-------------------------------
WebSocketMessage
-------------------------------
	# 表示消息对象接口 WebSocketMessage<T>
		T getPayload();
			* 获取消息体

		int getPayloadLength();
			* 消息体大小

		boolean isLast();
			* 分配消息, 是否是最后一帧
		
	# 类组织
		WebSocketMessage
			|-AbstractWebSocketMessage
			|-BinaryMessage<ByteBuffer> 
			|-PingMessage<ByteBuffer> 
			|-PongMessage<ByteBuffer> 
			|-TextMessage<String>
	
