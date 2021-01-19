------------------------
RandomAccessFile		|
------------------------
	# 随机IO
	# 构造方法
		RandomAccessFile(File file, String mode)
		RandomAccessFile(String name, String mode)

		* file/name 目标文件
		* mode io模式:"r","w"组合

	# 实例方法
		FileChannel getChannel()
			* 获取关联的channel

		FileDescriptor getFD()

		long getFilePointer()
			* 返回文件记录指针的当前位置

		void seek(long pos)
			* 将文件记录指针定位到pos的位置

		long length()
			* 获取文件的长度
		
		int read() 
			* 读取一个字节的数据

		int read(byte b[])
			* 读取数据到 byte[]
		
		readFully(byte b[]) 
		
		int read(byte b[], int off, int len)
			* 读取数据到 byte[]
			* 从byte[]的off开始写入,写入len长度
		
		readLine();
			* 从指针位置读取当前行,只会读指针这一行后面的数据
		
	
	# 读取Demo
		RandomAccessFile randomAccessFile = new RandomAccessFile(PATH,"rw");
        randomAccessFile.seek(60);			//设置指针位置
        int len = 0;
        byte[] buf = new byte[1024];
        while ((len = randomAccessFile.read(buf)) != -1){
            System.out.println(new String(buf,0,len,"GBK"));
        }
        randomAccessFile.close();
	
	# 写入Demo
		RandomAccessFile randomAccessFile = new RandomAccessFile(PATH,"rw");
        randomAccessFile.seek(randomAccessFile.length());					//移到指针的最后
        randomAccessFile.write("//哈哈哈,这个是追加的哟".getBytes("GBK"));	//写入数据
	
	# 监听文件的变化
		// 不能打开写权限("w"),不然其他程序没法写入数据到该文件
		try(RandomAccessFile randomAccessFile = new RandomAccessFile("D:\\log.log", "r")){
			// 最后一次指针的位置,默认从头开始
			long lastPointer = 0;
			// 每一行读取到的数据
			String line = null;
			// 持续监听文件
			while(true) {
				// 从最后一次读取到的数据开始读取
				randomAccessFile.seek(lastPointer);
				// 读取一行,遇到换行符停止,不包含换行符
				while((line = randomAccessFile.readLine()) != null) {
					System.out.print(line + "\n");
				}
				// 读取完毕后,记录最后一次读取的指针位置
				lastPointer = randomAccessFile.getFilePointer();
			}
		}