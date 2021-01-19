--------------------------------
ByteBufAllocator				|
--------------------------------
	# 内存分配器
	# 类库
		ByteBufAllocator
			AbstractByteBufAllocator
				PooledByteBufAllocator(池化)
				UnpooledByteBufAllocator(非池化)

	#  抽象方法
		ByteBufAllocator DEFAULT = ByteBufUtil.DEFAULT_ALLOCATOR;

		ByteBuf buffer();
		ByteBuf buffer(int initialCapacity);
		ByteBuf buffer(int initialCapacity, int maxCapacity);

		ByteBuf ioBuffer();
		ByteBuf ioBuffer(int initialCapacity);
		ByteBuf ioBuffer(int initialCapacity, int maxCapacity);
			* 期望返回一个直接内存buf(适合io的buf)

		ByteBuf heapBuffer();
		ByteBuf heapBuffer(int initialCapacity);
		ByteBuf heapBuffer(int initialCapacity, int maxCapacity);
			* 返回一个堆内存的buf

		ByteBuf directBuffer();
		ByteBuf directBuffer(int initialCapacity);
		ByteBuf directBuffer(int initialCapacity, int maxCapacity);
			* 返回一个直接内存buf

		CompositeByteBuf compositeBuffer();
		CompositeByteBuf compositeBuffer(int maxNumComponents);

		CompositeByteBuf compositeHeapBuffer();
		CompositeByteBuf compositeHeapBuffer(int maxNumComponents);

		CompositeByteBuf compositeDirectBuffer();
		CompositeByteBuf compositeDirectBuffer(int maxNumComponents);

		boolean isDirectBufferPooled();
		int calculateNewCapacity(int minNewCapacity, int maxCapacity);
