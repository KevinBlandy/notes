-------------------------------
org.apache.commons.io.IOUtils	|
-------------------------------
	# Apache的东西
	# 方法
		IOUtils.copy(InputStream in,OutputStream out);
			* 复制,需要自己关闭流资源
		
		closeQuietly(Closeable c);
			* 安全的关闭资源