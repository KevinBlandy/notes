-----------------------
HttpServerFileUpload
-----------------------
	# multipart请求处理器接口：  interface HttpServerFileUpload extends ReadStream<Buffer> 

	HttpServerFileUpload exceptionHandler(Handler<Throwable> handler);
	HttpServerFileUpload handler(Handler<Buffer> handler);
		* 每次解析出body数据都会调用这个方法
		* 它可以被调用多次
		
	HttpServerFileUpload endHandler(Handler<Void> endHandler);
		* 当前body解析完毕后会被调用

	HttpServerFileUpload pause();
	HttpServerFileUpload resume();
	HttpServerFileUpload fetch(long amount);

	void streamToFileSystem(String filename, Handler<AsyncResult<Void>> handler);
	Future<Void> streamToFileSystem(String filename);
		* IO到磁盘目录
		
	boolean cancelStreamToFileSystem() throws IllegalStateException;

	String filename();
		* 文件名称
	String name();
		* 表单名称
	String contentType();
	String contentTransferEncoding();
	String charset();
	long size();
	boolean isSizeAvailable();
	AsyncFile file();