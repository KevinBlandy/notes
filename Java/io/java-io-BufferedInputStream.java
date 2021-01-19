--------------------------
BufferedInputStream			|
--------------------------
	# InputStream 装饰类,带缓冲区的流
	# 构造
		BufferedInputStream(InputStream in) 
		BufferedInputStream(InputStream in, int size) 
          * 创建具有指定缓冲区大小的 BufferedInputStream 并保存其参数，即输入流 in，以便将来使用。 


--------------------------
方法/字段					|
--------------------------
	int read(byte[] b);
		* 把流读取到一个字节数组,返回读取到的字节数
	int read()
		* 仅仅读取一个字节并且返回
	int available() ;
		* 返回可以从此输入流读取（或跳过）、且不受此输入流接下来的方法调用阻塞的估计字节数。

--------------------------
静态方法/字段				|
--------------------------

--------------------------
Demo						|
--------------------------
	FileInputStream fileInputStream = new FileInputStream(PATH);
	BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream,1024);	//构建缓冲区流,指定缓冲区大小
	int len = 0;
	byte[] buf = new byte[1024];
	while (((len = bufferedInputStream.read(buf)) != -1)){
		System.out.println(new String(buf,0,len,"GBK"));
	}
	bufferedInputStream.close();
