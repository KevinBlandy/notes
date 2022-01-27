---------------------------------
ByteBufUtil						 |
---------------------------------
	# 提供了很多的静态API可以操作buf

	byte[] getBytes(ByteBuf buf, int start, int length, boolean copy)
		* 读取字节数组，不会修改readerIndex
		* start 开始角标，lengt 读取数量
		* copy 默认true，返回新的数组。如果false会尝过通过反射，返回同一个数组，底层共享（如果尝试失败，仍然会copy数组）

	String hexDump(ByteBuf buffer)
		* 返回buffer的16进制字符串,会根据rindex去读取，不会修改ReaderIndex

	String hexDump(byte[] array)
		* 返回 byte[] 的16进制字符串

	byte[] decodeHexDump(CharSequence hexDump)
		* 把16进制字符串转换为字节数组
	
	
