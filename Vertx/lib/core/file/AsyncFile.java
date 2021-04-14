---------------------------
AsyncFile 
---------------------------
	# 异步的文件对象接口
		interface AsyncFile extends ReadStream<Buffer>, WriteStream<Buffer> 
	
---------------------------
抽象 
---------------------------
	AsyncFile handler(Handler<Buffer> handler);
	AsyncFile pause();
	AsyncFile resume();
	AsyncFile endHandler(Handler<Void> endHandler);
	AsyncFile setWriteQueueMaxSize(int maxSize);
	AsyncFile drainHandler(Handler<Void> handler);
	AsyncFile exceptionHandler(Handler<Throwable> handler);
	AsyncFile fetch(long amount);

	Future<Void> close();
	void close(Handler<AsyncResult<Void>> handler);
		* 关闭，释放资源

	void write(Buffer buffer, long position, Handler<AsyncResult<Void>> handler);
	Future<Void> write(Buffer buffer, long position);
		* 随机写，若位置大于或等于文件大小， 文件将被扩展以适应偏移的位置

	AsyncFile read(Buffer buffer, int offset, long position, int length, Handler<AsyncResult<Buffer>> handler);
	Future<Buffer> read(Buffer buffer, int offset, long position, int length);
		* 随机读

	Future<Void> flush();
	AsyncFile flush(Handler<AsyncResult<Void>> handler);

	AsyncFile setReadPos(long readPos);
		* 读位置

	AsyncFile setReadLength(long readLength);
	long getReadLength();

	AsyncFile setWritePos(long writePos);
	long getWritePos();
		* 写位置
		
	AsyncFile setReadBufferSize(int readBufferSize);

	default Pipe<T> pipe()
	default Future<Void> pipeTo(WriteStream<T> dst)
	default void pipeTo(WriteStream<T> dst, Handler<AsyncResult<Void>> handler)

	default Future<Void> end() 
	void end(Handler<AsyncResult<Void>> handler);
	default Future<Void> end(T data)
	default void end(T data, Handler<AsyncResult<Void>> handler)
	boolean writeQueueFull();



---------------------------
静态 
---------------------------