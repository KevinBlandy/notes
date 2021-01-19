-------------------------------
泄漏检测						|
-------------------------------
	# Netty 定义了四种泄漏检测等级(枚举)
		ResourceLeakDetector.Level
			DISABLED
			SIMPLE
			ADVANCED
			PARANOID

		* 修改检测等级,可以修改 io.netty.leakDetectionLevel jvm参数
			java -Dio.netty.leakDetectionLevel=paranoid
	
	
	# 是通过日志来判断内存泄漏的
	
	12:05:24.374 [nioEventLoop-1-1] ERROR io.netty.util.ResourceLeakDetector - LEAK: ByteBuf.release() was not called before it's garbage-collected.
	Recent access records: 2
	#2:
		Hint: 'EchoServerHandler#0' will handle the message from this point.
		io.netty.channel.DefaultChannelHandlerContext.fireChannelRead(DefaultChannelHandlerContext.java:329)
		io.netty.channel.DefaultChannelPipeline.fireChannelRead(DefaultChannelPipeline.java:846)
		io.netty.channel.nio.AbstractNioByteChannel$NioByteUnsafe.read(AbstractNioByteChannel.java:133)
		io.netty.channel.nio.NioEventLoop.processSelectedKey(NioEventLoop.java:485)
		io.netty.channel.nio.NioEventLoop.processSelectedKeysOptimized(NioEventLoop.java:452)
		io.netty.channel.nio.NioEventLoop.run(NioEventLoop.java:346)
		io.netty.util.concurrent.SingleThreadEventExecutor$5.run(SingleThreadEventExecutor.java:794)
		java.lang.Thread.run(Thread.java:744)
	#1:
		io.netty.buffer.AdvancedLeakAwareByteBuf.writeBytes(AdvancedLeakAwareByteBuf.java:589)
		io.netty.channel.socket.nio.NioSocketChannel.doReadBytes(NioSocketChannel.java:208)
		io.netty.channel.nio.AbstractNioByteChannel$NioByteUnsafe.read(AbstractNioByteChannel.java:125)
		io.netty.channel.nio.NioEventLoop.processSelectedKey(NioEventLoop.java:485)
		io.netty.channel.nio.NioEventLoop.processSelectedKeysOptimized(NioEventLoop.java:452)
		io.netty.channel.nio.NioEventLoop.run(NioEventLoop.java:346)
		io.netty.util.concurrent.SingleThreadEventExecutor$5.run(SingleThreadEventExecutor.java:794)
		java.lang.Thread.run(Thread.java:744)
	Created at:
		io.netty.buffer.UnpooledByteBufAllocator.newDirectBuffer(UnpooledByteBufAllocator.java:55)
		io.netty.buffer.AbstractByteBufAllocator.directBuffer(AbstractByteBufAllocator.java:155)
		io.netty.buffer.AbstractByteBufAllocator.directBuffer(AbstractByteBufAllocator.java:146)
		io.netty.buffer.AbstractByteBufAllocator.ioBuffer(AbstractByteBufAllocator.java:107)
		io.netty.channel.nio.AbstractNioByteChannel$NioByteUnsafe.read(AbstractNioByteChannel.java:123)
		io.netty.channel.nio.NioEventLoop.processSelectedKey(NioEventLoop.java:485)
		io.netty.channel.nio.NioEventLoop.processSelectedKeysOptimized(NioEventLoop.java:452)
		io.netty.channel.nio.NioEventLoop.run(NioEventLoop.java:346)
		io.netty.util.concurrent.SingleThreadEventExecutor$5.run(SingleThreadEventExecutor.java:794)
		java.lang.Thread.run(Thread.java:744)