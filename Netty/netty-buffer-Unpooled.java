----------------------------------
Unpooled						  |
----------------------------------
	# 非池化的ByteBuf工厂类

	ByteBuf copiedBuffer(CharSequence string, Charset charset)
		* 把指定的string编码为ByteBuff
		* 会开辟字符串大小3倍长度的一个bufer
	
	CompositeByteBuf compositeBuffer()
		* 返回一个复合缓冲区

	