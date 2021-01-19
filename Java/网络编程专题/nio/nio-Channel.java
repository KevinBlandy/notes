------------------------
Channel					|
------------------------
	# Channel 接口就只声明了两个方法
		void close();
		boolean isOpen();
	
	# Channel 最重要的两个子接口是
		* ReadableByteChannel
			* 声明了 read(ByteBuffer dst) 方法
			* 该方法把数据源的数据读入参数指定的 ByteBuffer 缓冲区中

		* WritableByteChannel
			* 声明了 writr(ByteBuffer src)
			* 该方法把参数 ByteBuffer 缓冲区中的数据写到数据汇中

		* Channel 与 Buffer 关系
				|<---------------- ReadableByteChannel ----------------|
			ByteBuffer												数据源
				|----------------- WritableByteChannel --------------->|
		
		* ByteChannel 是一个便利接口,扩展了 ReadableByteChannel 和 WritableByteChannel,所以同时支持读写操作

-----------------------------------------------
ScatteringByteChannel 和 GatheringByteChannel	|
-----------------------------------------------
	# ScatteringByteChannel 
		* 该接口扩展了 ReadableByteChannel 接口,允许分散地读取数据
		  分散读取数据是指单个读取操作,能填充多个缓冲区,
		* 该接口声明了 read(ByteBuffer[] dsts)方法,该方法把从数据源读取到的数据依次填充到参数指定的 ByteBuffer 数组的各个 Buffer 中

	# GatheringByteChannel 
		* 该接口扩展了 WritableByteChannel 接口,允许集中的写入数据
		  集中写入数据是指单个写操作能把多个缓冲区的数据写到一起(数据汇)
		* 该接口声明了 write(ByteBuffer[] srcs)方法,该方法依次把srcs中的每个 Buffer 中的数据写入数据汇
	
	# 分散读取和集中写数据,能够进一步提高输入和输出操作的速度

-----------------------------------------------
FileChannel										|
-----------------------------------------------
	# FileChannel 实现了接口 Channel,代表一个与文件相连的通道
	# 该类还实现了 ByteChannel,ScatteringByteChannel,GatheringByteChannel
	# 它支持读,写,分散读,集中写等操作
	# 它没有提供 public 的构造方法,不能 new 
	# 在 FileInputStream,FileOutputStream,RandomAccessFile 类中,都有提供 getChannel() 方法,返回对应的 FileChannel 实例

-----------------------------------------------
SelectableChannel								|
-----------------------------------------------
	# 也是一种通道,不仅支持阻塞的IO操作,还支持非阻塞的IO操作
	# 它有两个子类
		ServerSocketChannel
		SocketChannel
	# 它还实现了 ByteChannel 接口,具有 read(ByteBuffer b); 和 write(ByteBuffer src); 方法
	# 在非阻塞模式下,读写数据不会阻塞,并且它还可以向 Selector 注册读写就绪等事件
	# Selector 负责监控这些事件,在事件触发的时候,例如:触发了读事件,那么该接口就可以进行读操作了
	# 主要的方法
		 SelectableChannel configureBlocking(boolean block)
			* 如果 block 为 true,则表示设置为阻塞模式,否则为非阻塞模式
			* 默认 true(阻塞)
		
		SelectionKey register(Selector sel, int ops, Object att)
		SelectionKey register(Selector sel, int ops)
			* 向 Selector 注册事件
			* 返回的 SelectionKey,用来跟踪被注册的事件
			* 第二个api还支持携带一个附件,在处理该事件的时候,可以从 SelectionKey 中获得这个附件
			* SelectionKey 还支持主动的去关联一个附件, attach(Object att)
		

	
