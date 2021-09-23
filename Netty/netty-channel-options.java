---------------------------------
ChannelOption					 |
---------------------------------
	# ChannelOption �ṩ�ı�׼������
	# ChannelConfig ����Ҳ�ṩapi,���Զ���Щ���Խ�������

---------------------------------
ALLOCATOR						 |
---------------------------------
	* ����Channe��buffer������,�ͻ���,����˶������õ�����
		ByteBufAllocator byteBufAllocator = ctx.alloc();

	* ���Ĳ����ǽӿ�ʵ��:ByteBufAllocator
	* ϵͳԤ������һЩĬ�ϵ�ʵ��
		serverBootstrap.childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
		serverBootstrap.childOption(ChannelOption.ALLOCATOR, UnpooledByteBufAllocator.DEFAULT);
		
---------------------------------
RCVBUF_ALLOCATOR				 |
---------------------------------
	* ���ջ��������ڴ������,�ͻ���,����˶������õ�����
	* ���Ĳ����ǽӿڵ�ʵ��:RecvByteBufAllocator
	* ���е�ʵ����
		DefaultMaxBytesRecvByteBufAllocator
		DefaultMaxMessagesRecvByteBufAllocator
			|-AdaptiveRecvByteBufAllocator(Ĭ��)
				* ������̬�����Ľ��ջ�����������,�������֮ǰChannel���յ������ݱ���С���м���,���������������ջ������Ŀ�д�ռ�,��̬��չ����
				* �������2�ν��յ������ݱ���С��ָ��ֵ,��������ǰ���������Խ�Լ�ڴ�
				* ���췽��
					AdaptiveRecvByteBufAllocator(int minimum, int initial, int maximum)

					minimum 
						* ��С��Ԥ�ڻ�������С�İ�������(Ĭ�� 64)
					initial 
						* ��δ�յ�����ʱ��ʼ����ʼ��������С(Ĭ�� 1024)
					maximum 
						* ���Ԥ�ڻ�������С�İ�������(Ĭ�� 65536)

		
			|-FixedRecvByteBufAllocator
				* �̶����ȵĽ��ջ�����������,���������ByteBuf���ȶ��ǹ̶���С��,���������ʵ�����ݱ��Ĵ�С��̬����
				* �����������,֧�ֶ�̬��չ,��̬��չ��Netty ByteBuf��һ���������,��ByteBuf��������ʵ��û�й�ϵ


	MESSAGE_SIZE_ESTIMATOR
	CONNECT_TIMEOUT_MILLIS
	MAX_MESSAGES_PER_READ

	WRITE_SPIN_COUNT
	WRITE_BUFFER_HIGH_WATER_MARK
	WRITE_BUFFER_LOW_WATER_MARK
	WRITE_BUFFER_WATER_MARK
		* ����дbuffer�ߵ�ˮλ
			serverBootstrap.option(ChannelOption.WRITE_BUFFER_WATER_MARK, new WriteBufferWaterMark(1024 * 1024, 8 * 1024 * 1024));
			// �ߵ�ˮλ���isWritableʹ��

	ALLOW_HALF_CLOSURE

	AUTO_READ
	AUTO_CLOSE

	SO_BROADCAST
	SO_KEEPALIVE
		* �Ƿ����������������
		* ��˫��TCP�׽��ֽ������Ӻ�(��������ESTABLISHED״̬)����������Сʱ�����ϲ�û���κ����ݴ���������,���׻��ƲŻᱻ����

	SO_SNDBUF
	SO_RCVBUF
	SO_REUSEADDR
	SO_LINGER
	SO_BACKLOG
		* ���ڹ��������׽���ServerSocket����,��ʶ���������������߳�ȫ��ʱ
		* ������ʱ���������������ֵ�����Ķ��е���󳤶�
		*  ���δ���û������õ�ֵС��1,Java��ʹ��Ĭ��ֵ50
	
	SO_TIMEOUT

	IP_TOS
	IP_MULTICAST_ADDR
	IP_MULTICAST_IF
	IP_MULTICAST_TTL
	IP_MULTICAST_LOOP_DISABLED
	TCP_NODELAY

	DATAGRAM_CHANNEL_ACTIVE_ON_REGISTRATION
	SINGLE_EVENTEXECUTOR_PER_GROUP

	