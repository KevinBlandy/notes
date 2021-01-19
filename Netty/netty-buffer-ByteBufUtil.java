---------------------------------
ByteBufUtil						 |
---------------------------------
	# 提供了很多的静态API可以操作buf


	String hexDump(ByteBuf buffer)
		* 返回buffer的16进制字符串,会根据rindex去读取

	String hexDump(byte[] array)
		* 返回 byte[] 的16进制字符串

	byte[] decodeHexDump(CharSequence hexDump)
		* 把16进制字符串转换为字节数组
	
	