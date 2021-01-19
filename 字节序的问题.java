--------------------------
字节序的问题
--------------------------
	# 对于字节序列的存储格式，目前有两大阵营，那就是Motorola的PowerPC系列CPU和Intel的x86系列CPU
		* PowerPC系列采用big endian方式存储数据
		* x86系列则采用little endian方式存储数据
		
	# C/C++语言编写的程序里数据存储顺序是跟编译平台所在的CPU相关的，而JAVA编写的程序则唯一采用big endian方式来存储数据

	# 0x1234abcd 写入到以0x0000开始的内存中 则结果为
		address	big-endian	little-endian
		0x0000	0x12		0xcd
		0x0001	0x34		0xab
		0x0002	0xab		0x34
		0x0003	0xcd		0x12

		int val = 0x1234ABCD;
		ByteBuffer buffer = ByteBuffer.allocate(4);
		// 设置小端模式
		buffer.order(ByteOrder.LITTLE_ENDIAN);
		// 写入int
		buffer.putInt(val);
		byte[] bytes = buffer.array();
		for (byte b : bytes) {
			System.out.print(Integer.toHexString(b & 0xFF));  // cdab3412
		}
	
	# Little-Endian
		* 将低序字节存储在起始地址（低位编址）

	# Big-Endian
		* 将高序字节存储在起始地址（高位编址）
		* 所有网络协议也都是采用big endian的方式来传输数据的。

		* 所以有时也会把big endian方式称之为网络字节序。
		* 当两台采用不同字节序的主机通信时，在发送数据之前都必须经过字节序的转换成为网络字节序后再进行传输。
	

	# 无论是big endian，还是little endian，都是针对多个字节的序列而言的（基本单位就是字节）
