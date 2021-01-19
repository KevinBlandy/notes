
----------------------------
解码						|
----------------------------
	# 解码器其实就是实现了ChannelInboundHandler的handler,主要的作用就是把消息转换为自定义的对象
	# 涉及类库
		|-ByteToMessageDecoder
			|-ReplayingDecoder<S> 
			|-LineBasedFrameDecoder
			|-LengthFieldBasedFrameDecoder
			|-DelimiterBasedFrameDecoder
			|-FixedLengthFrameDecoder
		|-MessageToMessageDecoder<T>
			|-StringDecoder

----------------------------
ByteToMessageDecoder		|
----------------------------
	# 把二进制数据转换为自定义的对象
	# 抽象类,核心的抽象方法需要用户手动的覆写
		void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception;
		
		* 通过该方法,手动的buf里面的数据转换为自定义的对象,添加到out集合里面
		* 如果buf里面的数据不够组包,直接 return,直到可以组装成一个对象时才处理
		* 在下一个handler处理器里就可以强制的把对象转换为自己解码的对象了(建议使用SimpleChannelInboundHandler<T>)
	
	# 运行机制
		* 每当有新数据接收的时候,ByteToMessageDecoder 都会调用 decode() 方法来处理内部的累积缓冲
		* decode() 方法可以决定当累积缓冲里没有足够数据时可以往 out 对象里放任意数据
		* 当有更多的数据被接收了 ByteToMessageDecoder 会再一次调用 decode() 方法
		* 如果在 decode() 方法里增加了一个对象到 out 对象里,这意味着解码器解码消息成功
		* ByteToMessageDecoder 将会丢弃在累积缓冲里已经被读过的数据
		* 不需要对多条消息调用 decode(),ByteToMessageDecoder 会持续调用 decode() 直到不放任何数据到 out 里
	
	# Integer 解码器
		public class IntegerDecoder extends ByteToMessageDecoder {
			@Override
			protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
				if(in.readableBytes() >= 4){
					// 必须起码够四个字节,才读取为int对象
					out.add(in.readInt());
				}
			}
		}
		//在下一个解码器里面就可以使用了,SimpleChannelInboundHandler<Integer>

----------------------------
ReplayingDecoder<T>			|
----------------------------
	# 继承自 ByteToMessageDecoder 抽象类
	# 跟 ByteToMessageDecoder 相比就是,它读取缓冲区的数据的时候,不需要去判断是否有足够的字节
		* 它内部使用了一个特殊的 ByteBuf 实现:ReplayingDecoderByteBuf
		* 如果在decode()中执行读取的字节数不够,ByteBuf自己会抛出异常然,在异常抛出后会被 ReplayingDecoder catch住
			* 异常实例:Signal REPLAY = Signal.valueOf(ReplayingDecoder.class, "REPLAY");
			* 每次的异常抛出,都是抛出同一个实例,避免异常对象多次创建的负担
		* ReplayingDecoder catch住异常后,会把读角标重置为初始值
		* 当有更多的数据后,会再次调用 decode() 方法

	# 源码导读
		// 如果可读的长度小于4字节,返回
		if (buf.readableBytes() < 4) {
			return;
		}
		// 标记读索引
		buf.markReaderIndex();

		// 读取一个int,表示数据的长度
		int length = buf.readInt();

		if (buf.readableBytes() < length) {
			// 如果可读数据,小于数据的长度,返回
			buf.resetReaderIndex();	// 并且重置读索引
			return;
		}

		out.add(buf.readBytes(length));

----------------------------
MessageToMessageDecoder<T>	|
----------------------------
	# 把对象转换为对象,泛型类
	# 抽象类,核心的抽象方法需要用户手动的覆写
		void decode(ChannelHandlerContext ctx, T msg, List<Object> out) throws Exception;
		
		* 可能在前面的handler中已经把二进制数据转换了对象
		* 但是,需要把解码后的对象再次通过handler转换为其他的对象,于是就出现了这个

