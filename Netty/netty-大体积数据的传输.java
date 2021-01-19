--------------------
零拷贝				|
--------------------
	# NIO 的"zero-copy(零拷贝)"功能,消除移动一个文件的内容从文件系统到网络堆栈的复制步骤
	# 类库
		FileRegion
			DefaultFileRegion

	# 直接传输一个文件的内容,没有执行的数据应用程序的处理
		// 获取 FileInputStream
		FileInputStream in = new FileInputStream(file); 

		// 创建一个新的 DefaultFileRegion 用于文件的完整长度
		FileRegion region = new DefaultFileRegion(in.getChannel(), 0, file.length()); 

		// 发送 DefaultFileRegion 并且注册一个 ChannelFutureListener
		channel.writeAndFlush(region).addListener(new ChannelFutureListener() { 
			@Override
			public void operationComplete(ChannelFuture future) throws Exception {
				// 处理发送失败
				if (!future.isSuccess()) {
					Throwable cause = future.cause(); 
				}
			}
		});

	
	# ssl 情况下,不能使用零拷贝
		* ssl 需要对数据进行加密 , 而加密就需要进入用户空间使用CPU计算,所以无法做到零拷贝

--------------------
分段传输			|
--------------------
	# 传输文件中指定的数据块
	# Handler
		ChunkedWriteHandler
	
	# 类库
		ChunkedInput(接口)
			ChunkedFile
				平台不支持 zero-copy 或者你需要转换数据,从文件中一块一块的获取数据

			ChunkedNioFile
				与 ChunkedFile 类似,处理使用了NIOFileChannel

			ChunkedStream
				从 InputStream 中一块一块的转移内容

			ChunkedNioStream
				从 ReadableByteChannel 中一块一块的转移内容
	
	# demo
		// 初始化设置 ChunkedWriteHandler 和 自己的Handler
		ChannelPipeline p = ...;
		p.addLast("streamer", new ChunkedWriteHandler());
		p.addLast("handler", new MyHandler());

		// 在自己Handler里面完成 ChunkedFile 的输出
		Channel ch = ...;
		ch.write(new ChunkedFile(new File("video.mkv"));

--------------------
传输方式的选择		|
--------------------
	@Override
    public void channelRead0(ChannelHandlerContext ctx, String filePath) throws Exception {
        RandomAccessFile raf  = new RandomAccessFile(filePath, "r");
        if (ctx.pipeline().get(SslHandler.class) == null) {
			// 未使用ssl,可以使用零拷贝
            ctx.write(new DefaultFileRegion(raf.getChannel(), 0, length));
        } else {
			// 使用了ssl,不能使用零拷贝,可以使用分段传输中的实现
            ctx.write(new ChunkedFile(raf));
        }
		ctx.flush();
    }


----------------------
ChunkedInput		  |
----------------------
	# 接口抽象方法

	boolean isEndOfInput() throws Exception;
		*  是否已经读取完毕

	void close() throws Exception;
		* 关闭

	@Deprecated
	B readChunk(ChannelHandlerContext ctx) throws Exception;
	B readChunk(ByteBufAllocator allocator) throws Exception;
		* 读取数据,返回泛型

	long length();
		* 可读长度

	long progress();
		* 目前的传输进度

--------------------------------------------
传输进度的监听								|
--------------------------------------------
	# 类库 
		ChannelProgressiveFutureListener
			* 用于监听传输进度的监听器
			* 对 GenericProgressiveFutureListener 的空实现


	ChannelFuture sendFileFuture = ctx.write(new DefaultFileRegion(file.getChannel(), 0, fileLength), ctx.newProgressivePromise());
	sendFileFuture.addListener(new ChannelProgressiveFutureListener() {
		// 传输进度
		@Override
		public void operationProgressed(ChannelProgressiveFuture future, long progress, long total) {
			if (total < 0) { // 未知总大小
				System.err.println(future.channel() + " Transfer progress: " + progress);
			} else {
				System.err.println(future.channel() + " Transfer progress: " + progress + " / " + total);
			}
		}

		// 传输完成
		@Override
		public void operationComplete(ChannelProgressiveFuture future) {
			System.err.println(future.channel() + " Transfer complete.");
		}
	});