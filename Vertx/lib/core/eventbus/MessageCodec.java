---------------------
MessageCodec
---------------------
	# 消息编解码器接口： interface MessageCodec<S, R>

	void encodeToWire(Buffer buffer, S s);
		* 把s编码到buffer中，发送

	R decodeFromWire(int pos, Buffer buffer);
		* 从buffer中读取数据，位置是，post，解码为对象

	R transform(S s);
		* 把s编码为R

	String name();
		* 自定义的名称

	byte systemCodecID();