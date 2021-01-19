----------------------------
CRC32						|
----------------------------
	# 循环冗余检查
		*  在数据存储和数据通讯领域, 为了保证数据的正确, 不得不采用检错的算法
	

	# 构造函数
		public CRC32()

	
	# 实例函数
		void update(int b)
		void update(byte[] b, int off, int len)
		void update(byte[] b)
		void update(ByteBuffer buffer)
			* 添加校验数据

		void reset()
			* 重置校验

		long getValue()
			* 获取计算出的crc32值
	
	# demo
		CRC32 crc32 = new CRC32();
		crc32.update(new byte[]{0,0,0,0,0,0});
		long value = crc32.getValue();
		System.out.println(value);
	
