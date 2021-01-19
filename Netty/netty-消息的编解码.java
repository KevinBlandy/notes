------------------------
消息的编解码			|
------------------------
	# 结合解码器和编码器在一起(在一个类)
		* 可能会牺牲可重用性

	# 涉及类库
		|-ChannelDuplexHandler
			|-CombinedChannelDuplexHandler<I,O>
			|-ByteToMessageCodec<T>
			|-MessageToMessageCodec<I,O>
		
	# 系统还提供了由 byte[] 和 ByteBuf 的编解码器
		ByteArrayEncoder<T>
		ByteArrayDecoder<T>
	
------------------------
ByteToMessageCodec<T>	|
------------------------
	# 用来处理 byte-to-message 和 message-to-byte
	# 解码字节消息成 POJO 或编码 POJO 消息成字节
	# 等同于 ByteToMessageDecoder 和 MessageToByteEncoder 的组合,抽象类，其中有 2 个方法需要我们自己实现
		void encode(ChannelHandlerContext ctx, I msg, ByteBuf out) throws Exception;
			* 编码方法

		void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception;
			* 解码方法

--------------------------
MessageToMessageCodec<I,O>|
--------------------------
	# 用于 message-to-message 的编码和解码,对象转换为对象
	# 可以看成是 MessageToMessageDecoder 和 MessageToMessageEncoder 的组合体
	# 抽象类,需要实现两个方法
		void encode(ChannelHandlerContext ctx, I msg, List<Object> out)throws Exception;
			* 编码

		void decode(ChannelHandlerContext ctx, O msg, List<Object> out)throws Exception;
			* 解码 
	
	# 数字的编码和解码
		public class NumberCodec extends MessageToMessageCodec<Integer, Long> {
			@Override
			public Long decode(ChannelHandlerContext ctx, Integer msg, List<Object> out)throws Exception {
				// 把Integer转换为Long
				out.add(msg.longValue());
			}

			@Override
			public Integer encode(ChannelHandlerContext ctx, Long msg, List<Object> out)throws Exception {
				// 把Long转换为Integer
				out.add(msg.intValue());
			}
		}
----------------------------------
CombinedChannelDuplexHandler<I,O> |
----------------------------------
	# CombinedChannelDuplexHandler<I extends ChannelInboundHandler, O extends ChannelOutboundHandler> extends ChannelDuplexHandler
	# 虽然这个类不是编解码器 API 的一部分,但是它经常被用来建立一个编解码器
	# 它可以绑定一个编码器和解码器,它既可以处理入站消息,也可以处理出站消息
	# 自定义的 Char 和 Byte
		public class CharCodec extends CombinedChannelDuplexHandler<ByteToCharDecoder, CharToByteEncoder> {
			public CharCodec(){
				super(new ByteToCharDecoder(), new CharToByteEncoder());
			}
		}
