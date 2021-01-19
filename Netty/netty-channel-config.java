--------------------------------
ChannelConfig					|
--------------------------------
	# 对于Channel的一些配置
	# 类库
		ChannelConfig
			|-SocketChannelConfig
			|-ServerSocketChannelConfig

	# 从Channel获取到Config对象
		ChannelHandlerContext ctx ...
		ChannelConfig channelConfig = ctx.channel().config();

		ChannelFuture channelFuture = ...
		ChannelConfig channelConfig = channelFuture.channel().config();
	

	# 方法
		Map<ChannelOption<?>, Object> getOptions();

		boolean setOptions(Map<ChannelOption<?>, ?> options);

		<T> T getOption(ChannelOption<T> option);

		<T> boolean setOption(ChannelOption<T> option, T value);

		int getConnectTimeoutMillis();

		ChannelConfig setConnectTimeoutMillis(int connectTimeoutMillis);

		@Deprecated
		int getMaxMessagesPerRead();

		@Deprecated
		ChannelConfig setMaxMessagesPerRead(int maxMessagesPerRead);

		int getWriteSpinCount();

		ChannelConfig setWriteSpinCount(int writeSpinCount);

		ByteBufAllocator getAllocator();

		ChannelConfig setAllocator(ByteBufAllocator allocator);

		<T extends RecvByteBufAllocator> T getRecvByteBufAllocator();

		ChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator allocator);

		boolean isAutoRead();

		ChannelConfig setAutoRead(boolean autoRead);

		boolean isAutoClose();

		ChannelConfig setAutoClose(boolean autoClose);

		int getWriteBufferHighWaterMark();

		ChannelConfig setWriteBufferHighWaterMark(int writeBufferHighWaterMark);
			* 设置高水位
			* 当发送队列待发送的字节数组达到高水位上限时,对应的 Channel 就变为不可写状态
			* 由于高水位并不会阻止业务线程调用 write() 方法并把消息加入到待发送队列中
			* 因此,必须要在消息发送时对 Channel 的状态进行判断(当到达高水位时,Channel 的状态被设置为不可写,通过对 Channel 的可写状态进行判断来决定是否发送消息)
				isWritable();

		int getWriteBufferLowWaterMark();

		ChannelConfig setWriteBufferLowWaterMark(int writeBufferLowWaterMark);

		MessageSizeEstimator getMessageSizeEstimator();

		ChannelConfig setMessageSizeEstimator(MessageSizeEstimator estimator);

		WriteBufferWaterMark getWriteBufferWaterMark();

		ChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark);