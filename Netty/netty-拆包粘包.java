----------------------------
拆包和粘包的解决方案		|
----------------------------
	# 如果确切知道消息的固定长度,可以简单的使用ByteToMessageDecoder
		@Override
		protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) { // (2)
			if (in.readableBytes() < 4) {
				// 消息长不足4,不能解码
				return; 
			}
			// 可以成功解码了一个消息
			out.add(in.readBytes(4)); 
		}
	
	# 也可以使用现成提供的解码器
		LineBasedFrameDecoder
			* 回车换行符作为消息结束符的TCP黏包的问题

		LengthFieldBasedFrameDecoder
			* 固定长度

		DelimiterBasedFrameDecoder
			* 以指定的符号分割消息
				
		FixedLengthFrameDecoder
			* 固定长度的消息头
	
	# 自动添加长度头的编码器
		LengthFieldPrepender

----------------------------
LengthFieldBasedFrameDecoder|
----------------------------
	# 专门为固定包头提供的解码器
		public LengthFieldBasedFrameDecoder(int maxFrameLength,int lengthFieldOffset, int lengthFieldLength)
		public LengthFieldBasedFrameDecoder(int maxFrameLength,int lengthFieldOffset, int lengthFieldLength,int lengthAdjustment, int initialBytesToStrip)
		public LengthFieldBasedFrameDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength,int lengthAdjustment, int initialBytesToStrip, boolean failFast)
		public LengthFieldBasedFrameDecoder(ByteOrder byteOrder, int maxFrameLength, int lengthFieldOffset, int lengthFieldLength,int lengthAdjustment, int initialBytesToStrip, boolean failFast)


		
		maxFrameLength
			* 单个包最大的长度,这个值根据实际场景而定

		lengthFieldOffset
			* 表示数据长度字段开始的偏移量
			* 第几个字节开始,是表示数据长度的(0)
		
		lengthFieldLength
			* 数据长度字段的所占的字节数
		
		lengthAdjustment
			* 添加到长度字段的补偿值(0)
			* lengthAdjustment + 数据长度取值 = 数据长度字段之后剩下包的字节数
			* 对于某些协议,长度字段还包含了消息头的长度,在这种应用场景中,往往需要使用lengthAdjustment进行修正
				由于整个消息(包含消息头)的长度往往大于消息体的长度,所以它要设置为负数(数据长度字段的长度取负)

		initialBytesToStrip
			* 表示从整个包第一个字节开始,向后忽略的字节数(0)
			* 可以设置该参数,来忽略掉包头信息,仅仅留下数据包体,给下一个Handler处理
	
	# 各种协议
		lengthFieldOffset   = 0
		lengthFieldLength   = 2		// 通用的前面2字节表示数据长度
		lengthAdjustment    = 0
		initialBytesToStrip = 0 

		BEFORE DECODE (14 bytes)         AFTER DECODE (14 bytes)
		+--------+----------------+      +--------+----------------+
		| Length | Actual Content |----->| Length | Actual Content |
		| 0x000C | "HELLO, WORLD" |      | 0x000C | "HELLO, WORLD" |
		+--------+----------------+      +--------+----------------+
		
		 lengthFieldOffset   = 0
		 lengthFieldLength   = 2
		 lengthAdjustment    = 0
		 initialBytesToStrip = 2	// 丢弃前面2字节的包头

		 BEFORE DECODE (14 bytes)         AFTER DECODE (12 bytes)
		 +--------+----------------+      +----------------+
		 | Length | Actual Content |----->| Actual Content |
		 | 0x000C | "HELLO, WORLD" |      | "HELLO, WORLD" |
		 +--------+----------------+      +----------------+
	
		 lengthFieldOffset   =  0
		 lengthFieldLength   =  2
		 lengthAdjustment    = -2 // 长度头表示的长度,包含了自身头部的长度
		 initialBytesToStrip =  0

		 BEFORE DECODE (14 bytes)         AFTER DECODE (14 bytes)
		 +--------+----------------+      +--------+----------------+
		 | Length | Actual Content |----->| Length | Actual Content |
		 | 0x000E | "HELLO, WORLD" |      | 0x000E | "HELLO, WORLD" |
		 +--------+----------------+      +--------+----------------+
	
		 lengthFieldOffset   = 2	// 表示消息长度的头,不在首部
		 lengthFieldLength   = 3
		 lengthAdjustment    = 0
		 initialBytesToStrip = 0

		 BEFORE DECODE (17 bytes)                      AFTER DECODE (17 bytes)
		 +----------+----------+----------------+      +----------+----------+----------------+
		 | Header 1 |  Length  | Actual Content |----->| Header 1 |  Length  | Actual Content |
		 |  0xCAFE  | 0x00000C | "HELLO, WORLD" |      |  0xCAFE  | 0x00000C | "HELLO, WORLD" |
		 +----------+----------+----------------+      +----------+----------+----------------+
	
		 lengthFieldOffset   = 0
		 lengthFieldLength   = 3
		 lengthAdjustment    = 2	// 整个消息体的长度,还要包含一个头部的长度,因为后面还有一个头部
		 initialBytesToStrip = 0

		 BEFORE DECODE (17 bytes)                      AFTER DECODE (17 bytes)
		 +----------+----------+----------------+      +----------+----------+----------------+
		 |  Length  | Header 1 | Actual Content |----->|  Length  | Header 1 | Actual Content |
		 | 0x00000C |  0xCAFE  | "HELLO, WORLD" |      | 0x00000C |  0xCAFE  | "HELLO, WORLD" |
		 +----------+----------+----------------+      +----------+----------+----------------+

		 lengthFieldOffset   = 1 (= the length of HDR1)			// 第2个字节表示数据长度
		 lengthFieldLength   = 2								
		 lengthAdjustment    = 1 (= the length of HDR2)			// 除此之外,还有1个字节的消息头
		 initialBytesToStrip = 3 (= the length of HDR1 + LEN)	// 移除前面3个字节的数据

		 BEFORE DECODE (16 bytes)                       AFTER DECODE (13 bytes)
		 +------+--------+------+----------------+      +------+----------------+
		 | HDR1 | Length | HDR2 | Actual Content |----->| HDR2 | Actual Content |
		 | 0xCA | 0x000C | 0xFE | "HELLO, WORLD" |      | 0xFE | "HELLO, WORLD" |
		 +------+--------+------+----------------+      +------+----------------+
	
		 lengthFieldOffset   =  1
		 lengthFieldLength   =  2								
		 lengthAdjustment    = -3 (= the length of HDR1 + LEN, negative)	//长度头表示的是整个消息体的长度
		 initialBytesToStrip =  3

		 BEFORE DECODE (16 bytes)                       AFTER DECODE (13 bytes)
		 +------+--------+------+----------------+      +------+----------------+
		 | HDR1 | Length | HDR2 | Actual Content |----->| HDR2 | Actual Content |
		 | 0xCA | 0x0010 | 0xFE | "HELLO, WORLD" |      | 0xFE | "HELLO, WORLD" |
		 +------+--------+------+----------------+      +------+----------------+


----------------------------
LengthFieldPrepender		|
----------------------------
	# 自动为数据包添加长度头的编码器

		LengthFieldPrepender(int lengthFieldLength)
		LengthFieldPrepender(int lengthFieldLength, boolean lengthIncludesLengthFieldLength)
		LengthFieldPrepender(int lengthFieldLength, int lengthAdjustment)
		LengthFieldPrepender(int lengthFieldLength, int lengthAdjustment, boolean lengthIncludesLengthFieldLength)
		LengthFieldPrepender(ByteOrder byteOrder, int lengthFieldLength,int lengthAdjustment, boolean lengthIncludesLengthFieldLength)
		
		lengthFieldLength
			* 长度字段的所占的字节数
			* 只能是:1, 2, 3, 4, 8 
		
		lengthIncludesLengthFieldLength
			* 长度是否包含了消息头的长度
		
		lengthAdjustment
			* 添加到长度字段的补偿值
			* 可以消息中还有其他的头
		
