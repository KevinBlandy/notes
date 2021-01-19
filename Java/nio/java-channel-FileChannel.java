--------------------------
FileChannel				  |
--------------------------
	# Java NIO中的FileChannel是一个连接到文件的通道。可以通过文件通道读写文件。
	# FileChannel无法设置为非阻塞模式，它总是运行在'阻塞模式下'
	# 获取 FileChannel
		* FileChannel 静态方法
			FileChannel.open(Path path, OpenOption... options);
			FileChannel.open(Path path, Set<? extends OpenOption> options,FileAttribute<?>... attrs)
		* 流对象的方法
			* InputStream,OutputStream,RandomAccessFile
			* getChannel(); 方法,总是存在这些流里面

	# 在使用 FileChannel 之前，必须先打开它。需要通过使用一个 InputStream , OutputStream 或 RandomAccessFile 来获取一个FileChannel实例
		* getChannel(); 方法,总是存在这些流里面
			RandomAccessFile file = new RandomAccessFile("E:\\Main.java", "rw");
			FileChannel fromChanne = file.getChannel();

		* 从 InputStream 获取 Channel
			ReadableByteChannel readableChannel = Channels.newChannel(InputStream in);
		* 从 OutputStream 获取 Channel
			

		
	
	# 从 FileChannel 中读取数据
		ByteBuffer buf = ByteBuffer.allocate(48);		//准备一个48字节的buffer
		int bytesRead = inChannel.read(buf);			//把通道中的数据读到buffer中
		*　read()方法返回的int值表示了有多少字节被读到了Buffer中。如果返回-1，表示到了文件末尾。

	# 向 FileChannel 写数据
		String newData = "New String to write to file..." + System.currentTimeMillis();
		ByteBuffer buf = ByteBuffer.allocate(48);
		buf.clear();					//重置buffer,指针移动到0
		buf.put(newData.getBytes());	//填充数据
		buf.flip();						//修改读/写模式,指针移到0
		while(buf.hasRemaining()) {
			channel.write(buf);
		}
		* 使用FileChannel.write()方法向FileChannel写数据，该方法的参数是一个Buffer
		* 注意FileChannel.write()是在while循环中调用的。
		* 因为无法保证write()方法一次能向FileChannel写入多少字节，因此需要重复调用write()方法，直到Buffer中已经没有尚未写入通道的字节。
	
	# 关闭 FileChannel
		channel.close();
		* 用完FileChannel后必须将其关闭

	# 演示一个从文件中获取 Channel 的实例
		RandomAccessFile file = new RandomAccessFile("E:\\Main.java","rw");		
        FileChannel fileChannel = file.getChannel();		//从文件获取流
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);	//构建1MB的缓冲区
        int len = fileChannel.read(byteBuffer);				//通过管道把数据写入缓冲区
        while (len != -1){							
            byteBuffer.flip();								//复位,开始读取数据
            while (byteBuffer.hasRemaining()){
                System.out.print((char) byteBuffer.get());
            }
            byteBuffer.clear();								//数据读取完毕,复位该缓冲区准备写入
            len = fileChannel.read(byteBuffer);				//通过管道把数据写入缓冲区
        }
        file.close();		//关闭 RandomAccessFile 会关闭对应的 FileChannel

-------------------------------
scatter							|
-------------------------------
	# Scattering Reads是指数据从一个channel读取到多个buffer中。如下图描述：
					|-> Buffer
		Channel ->	|-> Buffer
					|-> Buffer
	
	# read()方法按照buffer在数组中的顺序将从channel中读取的数据写入到buffer，当一个buffer被写满后，channel紧接着向另一个buffer中写。
	# Scattering Reads'在移动下一个buffer前，必须填满当前的buffer'，这也意味着它不适用于动态消息(译者注：消息大小不固定)。
	# 换句话说，如果存在消息头和消息体，消息头必须完成填充（例如 128byte），Scattering Reads才能正常工作。

	# 代码Demo
		ByteBuffer header = ByteBuffer.allocate(128);		//一个Buffere
		ByteBuffer body   = ByteBuffer.allocate(1024);		//又一个Buffere
		ByteBuffer[] bufferArray = { header, body };		//把所有Buffer合并为一个Buffer数组
		channel.read(bufferArray);							//从Channle中读取数据到这些Buffer
	
-------------------------------
gather							|
-------------------------------
	# Gathering Writes是指数据从多个buffer写入到同一个channel。如下图描述：
					<-| Buffer
		Channel <-	<-| Buffer
					<-| Buffer

	# write()方法会按照buffer在数组中的顺序，将数据写入到channel，注意只有position和limit之间的数据才会被写入。
	# 如果一个buffer的容量为128byte，但是仅仅包含58byte的数据，那么这58byte的数据将被写入到channel中。
	# 因此与Scattering Reads相反，Gathering Writes能较好的处理动态消息。

	# 代码Demo
		ByteBuffer header = ByteBuffer.allocate(128);		//一个Buffer
		ByteBuffer body   = ByteBuffer.allocate(1024);		//又一个Buffer
		ByteBuffer[] bufferArray = { header, body };		//把所有Buffer合并为一个数组
		channel.write(bufferArray);							//把数据从Buffre中写入到channel

-------------------------------
 通道之间的数据传输				|
-------------------------------
	# 如果两个通道中有一个是 FileChannel ，那你可以直接将数据从一个channel,传输到另外一个channel。

	# 把目标 Channel 的数据读取到 this Channel 中
		* Demo
			RandomAccessFile fromFile = new RandomAccessFile("E:\\Main.java", "rw");			
			FileChannel      fromChannel = fromFile.getChannel();				//源Chnnel
			RandomAccessFile toFile = new RandomAccessFile("E:\\Main1.java", "rw");
			FileChannel      toChannel = toFile.getChannel();					//目标Chnnel
			long position = 0;													//指针为0
			long count = fromChannel.size();									//获取的是源channel的总大小的数据量
			toChannel.transferFrom(fromChannel, position, count);				//执行属性传输

		* 果源通道的剩余空间小于 count 个字节，则所传输的字节数要小于请求的字节数。		
		* 在SoketChannel的实现中，'SocketChannel只会传输此刻准备好的数据(可能不足count字节)'。	//SocketChannel是网络
		* 因此,SocketChannel 可能不会将请求的所有数据(count个字节) 全部传输到FileChannel中。

	# 把 this Channel 中的数据写入到 目标 Channel 中
		* Demo
			RandomAccessFile fromFile = new RandomAccessFile("E:\\Main.java", "rw");
			FileChannel      fromChanne = fromFile.getChannel();
			RandomAccessFile toFile = new RandomAccessFile("E:\\ooooo.java", "rw");
			FileChannel      toChannel = toFile.getChannel();
			long position = 0;
			long count = fromChanne.size();
			fromChanne.transferTo(position, count, toChannel);
		* 除了调用方法的FileChannel对象不一样外，其他的都一样。
		* 关于SocketChannel的问题在transferTo()方法中同样存在。
		* 'SocketChannel会一直传输数据直到目标buffer被填满'。

-------------------------------
FileChannel-内存文件映射		|
-------------------------------
	# MappedByteBuffer map(MapMode mode,long position, long size);


-------------------------------
FileChannel-API					|
-------------------------------
	int read(ByteBuffer buffer);
		* 将数据从 FileChannel 读取到Buffer中。
		* read()方法返回的int值表示了有多少字节被读到了Buffer中。如果返回-1，表示到了文件末尾。
	int write(ByteBuffer src);
		* 把 src 中的数据写入到 FileChannel 中
	long transferFrom(ReadableByteChannel src,long position, long count);
		* 把 src 通道中的数据写入到 this 通道,从 position 开始写,写 count 长度
		* 返回 long,表示写入了多少数据
	
	long transferTo(ReadableByteChannel src,long position, long count);
		* 把 this 通道中的数据写入到 src 通道,从 position 开始写,写 count 长度
		* 返回 long,表示写入了多少数据

	long position();
		* 获取FileChannel的当前位置
		* 如果将位置设置在文件结束符之后，然后试图从文件通道中读取数据，读方法将返回-1 —— 文件结束标志。

	void position(long position);
		* 设置FileChannel的位置
	
	long size();
		* 返回关联文件的大小
	
	FileChannel truncate(long size);
		* 截取一个文件。截取文件时，文件将中指定长度后面的部分将被删除
		* size(字节)多大,就截取多大,后面的都不要
	
	void force(boolean flag);
		* 将通道里尚未写入磁盘的数据强制写到磁盘上
		* 出于性能方面的考虑，操作系统会将数据缓存在内存中，所以无法保证写入到FileChannel里的数据一定会即时写到磁盘上。要保证这一点，需要调用force()方法。
		* boolean类型的参数，指明是否同时将文件元数据（权限信息等）写到磁盘上。
	
	channel.close();
		* 关闭 Channel

	
	FileLock fileChannel.tryLock();
		* 该调用不会阻塞,直接获取锁,如果锁不存在,返回null
		* 获取文件锁
		* demo
			FileLock lock = fileChanne.lock();		//获取文件锁
			lock.release();							//释放锁
	FileLock fileChannel.lock(); 
		* 同上
		* 该调用会阻塞,直到获取到锁

	MappedByteBuffer map(MapMode mode,long position, long size)
		* 把当前Channel映射到内存
		* mode 指定方式(只读,可写....),position 指定文件管道的开始位置,size 指定结束位置
		* MappedByteBuffer 是 ByteBuffer 的子类

-------------------------------
FileChannel-内部静态类			|
-------------------------------
	MapMode
		READ_ONLY
			* 只读,如果写操作会异常

		READ_WRITE
			* 可写,任何修改都会在某个时间被写入文件系统

		PRIVATE
			* 可写,但是这个不会写入到文件系统
