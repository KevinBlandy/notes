----------------
BufferedReader
----------------
	# 构造
		BufferedReader reader = new BufferedReader(new FileReader(),int length);
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		* length	设置缓冲区的大小
	
	# 实例方法
		String reader.readLine();
			* 读取一行数据(并不包含回车符),当读到末尾。返回 null.
		
		long transferTo(Writer out)
			* 把当前Reader写入到某个writer
			* 返回写入的字节数
		

----------------
LineNumberReader
----------------
	# BufferedReader 的子类,带有可以读写行号的类

	# 带行号的方法
		int getLineNumber()
			* 可以获取读取的行号

		void setLineNumber(num)
			* 设置初始的行号, 读取的第一行从num+1开始标记
		