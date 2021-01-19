--------------------------
BufferedOutputStream			|
--------------------------
	# InputStream 装饰类,带缓冲区的流
	# 构造
		BufferedOutputStream(OutputStream out) 
		BufferedOutputStream(OutputStream out, int size) 
          * 创建具有指定缓冲区大小的 BufferedOutputStream 并保存其参数，即输出流 out，以便将来使用。 


--------------------------
方法/字段					|
--------------------------
	void flush() 
		* 刷新此缓冲的输出流。 

	void write(byte[] b);
		* 写入整个字节数组

	void write(byte[] b, int off, int len) 
		* 将指定 byte 数组中从偏移量 off 开始的 len 个字节写入此缓冲的输出流。 

	void write(int b)	
		* 写入一个字节


--------------------------
静态方法/字段				|
--------------------------

--------------------------
Demo						|
--------------------------
	# 文件字节流输出的 Buffer 装饰流,带缓冲区的,可以提高效率
	FileOutputStream fileOutputStream = new FileOutputStream("E:\\IOTEST.java");
	BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream,1024);
	bufferedOutputStream.write(new String("余文朋").getBytes());
	bufferedOutputStream.flush();
	bufferedOutputStream.close();