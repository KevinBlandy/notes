---------------------------
Packet						|
---------------------------
	# 消息包体的父类
	# 子类
		EncodedPacket
	# Packet-属性
		private Integer synSeq = 0;
			* 同步发送时，需要的同步序列号
		private ByteBuffer preEncodedByteBuffer = null;	
			* 预编码过的bytebuffer
			* 如果此值不为null，框架则会忽略原来的encode()(对象转换为Buffer)过程

	# Packet-方法	
		ByteBuffer getPreEncodedByteBuffer();
		Integer getSynSeq();
		String logstr()
		void setPreEncodedByteBuffer(ByteBuffer preEncodedByteBuffer);
		void setSynSeq(Integer synSeq);

---------------------------
EncodedPacket				|
---------------------------
	# Packet 子类
	# 属性
		private byte[] bytes;

	# 方法
		public EncodedPacket(byte[] bytes){
			this.bytes = bytes;
		}

		public byte[] getBytes(){
			return bytes;
		}
		public void setBytes(byte[] bytes){
			this.bytes = bytes;
		}