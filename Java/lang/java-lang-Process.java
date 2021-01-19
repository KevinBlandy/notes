----------------------------------
Process								|
----------------------------------
	# Process,应该是代表系统进程
		void prop.destroy();
			* 杀死进程。(只能弄死它启动的程序)

		InputStream prop.getInputStream();
			* 可以获取到cmd的一个输入流,执行了cmd命名后可以读取到cmd显示出来的内容

		InputStream getErrorStream() 
			*  获取子进程的错误流。
			
		InputStream getInputStream()  
			* 获取写入流

		int exitValue();
			* 此 Process 对象表示的子进程的出口值。根据惯例，值 0 表示正常终止。 

		int waitFor();
			* 当期线程会一直阻塞,直到 Process 对象表示的进程已经终