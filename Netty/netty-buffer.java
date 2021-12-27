--------------------
Buffer				|
--------------------
	# �漰�����
		ByteBuf
			|-AbstractByteBuf
				|-CompositeByteBuf
				|-UnpooledHeapByteBuf
				|-UnpooledDirectByteBuf
		ByteBufHolder
		ByteBufProcessor
			* ���ڣ�����ʹ��: ByteProcessor
		ByteBufAllocator
		Unpooled
		ByteBufUtil

	# Netty ���� API �ṩ�˼�������
		* �����Զ��建������
		* ͨ��һ�����õĸ��ϻ�������ʵ���㿽��
		* ��չ�Ժ�,���� StringBuilder(�����Զ�������)
			ByteBuf byteBuf = Unpooled.buffer(5);	// ��ʼ5������
			for(int x = 0 ;x < 10 ;x ++) {			// ǿ��д��10������
				byteBuf.writeByte(x);
			}
			//UnpooledByteBufAllocator$InstrumentedUnpooledUnsafeHeapByteBuf(ridx: 0, widx: 10, cap: 64)		�Զ����ݵ�64
			System.out.println(byteBuf);
		* ��ȡ��д�������ֿ�,����Ҫ���� flip() ���л���/дģʽ
		* ������
		* ���ü���
		* Pooling(��)
	
	# Netty ʹ�� reference-counting(���ü���)��ʱ��֪����ȫ�ͷ� Buf ��������Դ
	
	# ByteBuf ��Ĭ��������������� Integer.MAX_VALUE
	
	# Buffer����صĿ�
		ByteBufAllocator
			|-PooledByteBufAllocator
			|-UnpooledByteBufAllocator
		Unpooled
		CompositeByteBuf
		ByteBufUtil
		ByteBufHolder
	
	# ByteBuf������
		��������������������������������������������������������������������������������
					�ػ���				�ǳػ���
		ֱ�ӻ���		
		�ѻ���
		���ϻ���
		��������������������������������������������������������������������������������
		heap buffer
			* �洢��jvm��,���Կ��ٵĴ���������,���ҿ���ֱ�ӷ����ڲ�������
			* ÿ�ε�����io,����һ���������ݵĹ���(�Ѷ������ݸ��Ƶ�ֱ�ӻ�����)

		direct buffer
			* �ڶ���ֱ�ӷ���ռ�,������ռ�öѵĿռ�
			* ��socket����ioʱ���ܱȽϺ�,��Ϊʡ���˸�����һ������
			* ������ֱ�ӷ����ڲ�������
			* �����ڴ�������ͷűȶѻ�Ƚϸ������ٶȻ���һЩ(����ͨ���ڴ��������������)

	# ��ͬByeBuf��ʹ�ó���
		* ҵ����Ϣ�ı�������HeapByteBuf
		* IOͨ���߳���IO������ʱ,ʹ��DirectByteBuf(0����)

--------------------
Buffer				|
--------------------
	capacity	�ܴ�С
	readindex	���Ǳ�
	wirteindex	д�Ǳ�

--------------------
Heap Buffer(�ѻ�����)|
--------------------
	# ��õ������� ByteBuf �����ݴ洢�� JVM �Ķѿռ�
		* �ṩ��ֱ�ӷ�������ķ���,ͨ�� ByteBuf.array()����ȡ byte[]����
	
	# ���ʷǶѻ����� ByteBuf ������ᵼ�� UnsupportedOperationException
		* ����ʹ�� ByteBuf.hasArray()������Ƿ�֧�ַ�������
	
------------------------
Direct Buffer(ֱ�ӻ�����)|
------------------------
	# �ڶ�֮��ֱ�ӷ����ڴ�,ֱ�ӻ���������ռ�öѿռ�����
	# ֱ�ӻ�������ȱ�����ڷ����ڴ�ռ���ͷ��ڴ�ʱ�ȶѻ�����������,�� Netty ʹ���ڴ�����������������,��Ҳ�� Netty ʹ���ڴ�ص�ԭ��֮һ
	# ֱ�ӻ�������֧�������������,���ǿ��Լ�ӵķ�����������
		ByteBuf directBuf = Unpooled.directBuffer(16);
		// ֱ�ӻ�����
		if(!directBuf.hasArray()){
			// �ɶ������ݳ���
			int len = directBuf.readableBytes();
			// ������ͬ���ȵ�����
			byte[] arr = new byte[len];
			// �ѻ����������ݶ�ȡ������
			directBuf.getBytes(0, arr);
		}




