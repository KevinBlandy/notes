----------------------------------
Process								|
----------------------------------
	# Process 表示系统进程的接口

		public abstract OutputStream getOutputStream();
			* 返回输出流
		
		public abstract InputStream getInputStream();
			* 返回输入流
		
		public abstract InputStream getErrorStream();
			* 返回异常输出流
		
		public abstract int waitFor() throws InterruptedException;
			* 等待，直到进程结束，返回进程的结束状态
		
		public boolean waitFor(long timeout, TimeUnit unit)
			* 等待线程结束，设置超时时间
		
		public abstract int exitValue();
			* 获取进程结束的状态
		
		public abstract void destroy();
			* 杀死进程
		
		public Process destroyForcibly()
			* 杀死进程，强制

		public boolean isAlive()
			* 进程是否存活

