--------------------
Buffer				|
--------------------
	# 涉及的类库
		ByteBuf
			|-AbstractByteBuf
				|-CompositeByteBuf
				|-UnpooledHeapByteBuf
				|-UnpooledDirectByteBuf
		ByteBufHolder
		ByteBufProcessor
		ByteBufAllocator
		Unpooled
		ByteBufUtil

	# Netty 缓冲 API 提供了几个优势
		* 可以自定义缓冲类型
		* 通过一个内置的复合缓冲类型实现零拷贝
		* 扩展性好,类似 StringBuilder(可以自动的扩容)
			ByteBuf byteBuf = Unpooled.buffer(5);	// 初始5个长度
			for(int x = 0 ;x < 10 ;x ++) {			// 强行写入10个数据
				byteBuf.writeByte(x);
			}
			//UnpooledByteBufAllocator$InstrumentedUnpooledUnsafeHeapByteBuf(ridx: 0, widx: 10, cap: 64)		自动扩容到64
			System.out.println(byteBuf);
		* 读取和写入索引分开,不需要调用 flip() 来切换读/写模式
		* 方法链
		* 引用计数
		* Pooling(池)
	
	# Netty 使用 reference-counting(引用计数)的时候知道安全释放 Buf 和其他资源
	
	# ByteBuf 的默认最大容量限制是 Integer.MAX_VALUE
	
	# Buffer的相关的库
		ByteBufAllocator
			|-PooledByteBufAllocator
			|-UnpooledByteBufAllocator
		Unpooled
		CompositeByteBuf
		ByteBufUtil
		ByteBufHolder
	
	# ByteBuf的类型
		————————————————————————————————————————
					池化的				非池化的
		直接缓冲		
		堆缓冲
		复合缓冲
		————————————————————————————————————————
		heap buffer
			* 存储在jvm堆,可以快速的创建与销毁,并且可以直接访问内部的数组
			* 每次的数据io,都有一个拷贝数据的过程(把堆中数据复制到直接缓冲区)

		direct buffer
			* 在堆外直接分配空间,它不会占用堆的空间
			* 在socket进行io时性能比较好,因为省略了复制这一个步骤
			* 它不能直接访问内部的数组
			* 它的内存分配与释放比堆会比较复杂且速度会慢一些(可以通过内存池来解决这个问题)

	# 不同ByeBuf的使用场景
		* 业务消息的编解码采用HeapByteBuf
		* IO通信线程在IO缓冲区时,使用DirectByteBuf(0拷贝)

--------------------
Buffer				|
--------------------
	capacity	总大小
	readindex	读角标
	wirteindex	写角标

--------------------
Heap Buffer(堆缓冲区)|
--------------------
	# 最常用的类型是 ByteBuf 将数据存储在 JVM 的堆空间
		* 提供了直接访问数组的方法,通过 ByteBuf.array()来获取 byte[]数据
	
	# 访问非堆缓冲区 ByteBuf 的数组会导致 UnsupportedOperationException
		* 可以使用 ByteBuf.hasArray()来检查是否支持访问数组
	
------------------------
Direct Buffer(直接缓冲区)|
------------------------
	# 在堆之外直接分配内存,直接缓冲区不会占用堆空间容量
	# 直接缓冲区的缺点是在分配内存空间和释放内存时比堆缓冲区更复杂,而 Netty 使用内存池来解决这样的问题,这也是 Netty 使用内存池的原因之一
	# 直接缓冲区不支持数组访问数据,但是可以间接的访问数据数组
		ByteBuf directBuf = Unpooled.directBuffer(16);
		// 直接缓冲区
		if(!directBuf.hasArray()){
			// 可读的数据长度
			int len = directBuf.readableBytes();
			// 创建相同长度的数组
			byte[] arr = new byte[len];
			// 把缓冲区的数据读取到数组
			directBuf.getBytes(0, arr);
		}




