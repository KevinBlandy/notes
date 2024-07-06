--------------------------
TextEncoderStream
--------------------------
	# 将一个字符串流转换为 UTF-8 编码的字节。它与 TextEncoder 的流形式等价。
		* TextEncoderStream 其实就是 TransformStream 形式的 TextEncoder。
		* 将解码后的文本流通过管道输入流编码器会得到编码后文本块的流：
	
	# 构造函数
		new TextEncoderStream()


--------------------------
this
--------------------------

encoding
readable
writable

--------------------------
static
--------------------------

