------------------------------
CompositeByteBuf			  |
------------------------------
	# 复合Buffer,它本身也是实现了ByteBuf接口,而且它不会有内存泄漏的问题
		* 其实就是把多个Buffer柔和成一个Buffer

	# CompositeByteBuf.hasArray()总是返回 false,因为它可能包含一些直接或间接的不同类型的 ByteBuf
	
	# 构造函数
		CompositeByteBuf(ByteBufAllocator alloc, boolean direct, int maxNumComponents)
		CompositeByteBuf(ByteBufAllocator alloc, boolean direct, int maxNumComponents, ByteBuf... buffers) 
		CompositeByteBuf(ByteBufAllocator alloc, boolean direct, int maxNumComponents, Iterable<ByteBuf> buffers) 
	
	# 方法
		CompositeByteBuf addComponent(boolean increaseWriterIndex, int cIndex, ByteBuf buffer)
		CompositeByteBuf addComponent(boolean increaseWriterIndex, ByteBuf buffer)
		CompositeByteBuf addComponent(int cIndex, ByteBuf buffer)
		CompositeByteBuf addComponent(ByteBuf buffer)
		CompositeByteBuf addComponents(boolean increaseWriterIndex, ByteBuf... buffers)
		CompositeByteBuf addComponents(boolean increaseWriterIndex, Iterable<ByteBuf> buffers)
		CompositeByteBuf addComponents(int cIndex, ByteBuf... buffers)
		CompositeByteBuf addComponents(int cIndex, Iterable<ByteBuf> buffers) 
		CompositeByteBuf addComponents(ByteBuf... buffers)
		CompositeByteBuf addComponents(Iterable<ByteBuf> buffers)



	# 简单的使用
		// 创建复合缓冲区
		CompositeByteBuf compBuf = Unpooled.compositeBuffer();

		// 创建俩buffer
		ByteBuf heapBuf = Unpooled.buffer(8);
		ByteBuf directBuf = Unpooled.directBuffer(16);

		//添加 ByteBuf 到 CompositeByteBuf
		compBuf.addComponents(heapBuf,directBuf);

		//删除第一个 ByteBuf
		compBuf.removeComponent(0);

		// 可以迭代复合缓冲区中的每一个缓冲区
		Iterator<ByteBuf> iter = compBuf.iterator();
		while(iter.hasNext()){
			System.out.println(iter.next().toString());
		}

		//使用数组访问数据
		if(!compBuf.hasArray()){
			int len = compBuf.readableBytes();
			byte[] arr = new byte[len];
			compBuf.getBytes(0, arr);
		}

