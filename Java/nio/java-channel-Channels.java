-----------------------
Channels				|				
-----------------------
	# Channel 的工具类


-----------------------
Channels-静态方法		|				
-----------------------

	ReadableByteChannel newChannel(InputStream in)
		* 从 InputStream 获取 ReadableByteChannel
	
	WritableByteChannel newChannel(final OutputStream out)
		* 从 OutputStream 获取 WritableByteChannel