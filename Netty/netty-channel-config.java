--------------------------------
ChannelConfig					|
--------------------------------
	# ����Channel��һЩ����
	# ���
		ChannelConfig
			|-SocketChannelConfig
			|-ServerSocketChannelConfig

	# ��Channel��ȡ��Config����
		ChannelHandlerContext ctx ...
		ChannelConfig channelConfig = ctx.channel().config();

		ChannelFuture channelFuture = ...
		ChannelConfig channelConfig = channelFuture.channel().config();
	

	# ����
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
			* autoRead�������Ǹ���ȷ�����ʿ��ƣ�����򿪵�ʱ��Netty�ͻ������ע����¼���
			* ��ע���˶��¼����������ɶ�����Netty�ͻ��channel��ȡ���ݡ�
			
			* �����autoread�ص�����Netty�᲻ע����¼���
			* ������ʹ�ǶԶ˷������ݹ�����Ҳ���ᴥ�����¼����Ӷ�Ҳ�����channel��ȡ�����ݡ���recv_buffer��ʱ��Ҳ�Ͳ����ٽ������ݡ�


		boolean isAutoClose();

		ChannelConfig setAutoClose(boolean autoClose);
		
		WriteBufferWaterMark getWriteBufferWaterMark();
		ChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark);
		int getWriteBufferHighWaterMark();
		ChannelConfig setWriteBufferHighWaterMark(int writeBufferHighWaterMark);
		int getWriteBufferLowWaterMark();
		ChannelConfig setWriteBufferLowWaterMark(int writeBufferLowWaterMark);
			* ���øߵ�ˮλ
			* �����Ͷ��д����͵��ֽ�����ﵽ��ˮλ����ʱ,��Ӧ�� Channel �ͱ�Ϊ����д״̬
			* ���ڸ�ˮλ��������ֹҵ���̵߳��� write() ����������Ϣ���뵽�����Ͷ�����
			* ���,����Ҫ����Ϣ����ʱ�� Channel ��״̬�����ж�(�������ˮλʱ,Channel ��״̬������Ϊ����д,ͨ���� Channel �Ŀ�д״̬�����ж��������Ƿ�����Ϣ)
				isWritable();

		MessageSizeEstimator getMessageSizeEstimator();

		ChannelConfig setMessageSizeEstimator(MessageSizeEstimator estimator);

		