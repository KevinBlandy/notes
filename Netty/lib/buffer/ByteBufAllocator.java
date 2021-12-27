--------------------------------
ByteBufAllocator				|
--------------------------------
	# buf分配器接口：public interface ByteBufAllocator

	# 实现类体系
		ByteBufAllocator
			|-AbstractByteBufAllocator
				|-PooledByteBufAllocator(池化)
				|-UnpooledByteBufAllocator(非池化)

	#  抽象方法
		ByteBufAllocator DEFAULT = ByteBufUtil.DEFAULT_ALLOCATOR;

		ByteBuf buffer();
		ByteBuf buffer(int initialCapacity);
		ByteBuf buffer(int initialCapacity, int maxCapacity);
			* 创建新的Buffer
			* buffer的类型，取决于实现

		ByteBuf ioBuffer();
		ByteBuf ioBuffer(int initialCapacity);
		ByteBuf ioBuffer(int initialCapacity, int maxCapacity);
			* 期望返回一个适合io的buf, 最好是直接内存buf

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
			* 当buf需要扩容的时候，可以根据这个方法计算出新buf的容量。maxCapacity 指定上限

