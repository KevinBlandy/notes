----------------------------
编码						|
----------------------------
	# 编码就是把对象转换为网络传输的二进制数据(ByteBuf)
	# 涉及类库
		|-MessageToByteEncoder<I>
		|-MessageToMessageEncoder<I>
			|-LengthFieldPrepender
			|-StringEncoder

----------------------------
MessageToByteEncoder<T>		|
----------------------------
	# 把对象转换为ByteBuf,抽象类,需要实现抽象方法
		void encode(ChannelHandlerContext ctx, I msg, ByteBuf out) throws Exception;

		msg
			* 对象
		out
			* 把对象转换为字节数据后,写入到该Buf
	
	# 把 Integer 转换为 Byte
		public class IntegerToByteEncoder extends MessageToByteEncoder<Integer> {
			@Override
			protected void encode(ChannelHandlerContext ctx, Integer msg, ByteBuf out) throws Exception {
				out.writeInt(msg);
			}
		}
----------------------------
MessageToMessageEncoder<T>	|
----------------------------
	# 抽象类,把对象转换为对象,需要实现抽象方法
		void encode(ChannelHandlerContext ctx, I msg, List<Object> out)

