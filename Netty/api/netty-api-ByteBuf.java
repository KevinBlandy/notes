-----------------------------
ByteBuf						 |
-----------------------------
	#  abstract class ByteBuf implements ReferenceCounted, Comparable<ByteBuf>

-----------------------------
方法						 |
-----------------------------
	// 角标的操作
	int readerIndex();
		* 返回读角标
	
	ByteBuf readerIndex(int readerIndex);
		* 设置新的读角标

	int writerIndex();
		* 返回写角标
	
	ByteBuf writerIndex(int writerIndex)
		* 设置新的写角标
	
	ByteBuf setIndex(int readerIndex, int writerIndex);
		* 同时设置读写角标
	
	int arrayOffset();
		* 返回底层数组存储数据的偏移量(一般都是0)

	// 容器的属性操作
	int capacity();
		* 返回容器长度

	boolean isReadable();
		* 是否还有起码1个字节的可读空间
		* wi > ri

	boolean isReadable(int size);
		* 是否还有指定长度的可读空间
		* wi - ri >= size
	
	boolean isWritable();
		* 是否起码还有1个字节的可写空间
	
	boolean isWritable(int size);
		* 是否起码还有指定个字节的可写空间

	int writableBytes();
		* 返回可写的空间大小

	ByteBuf clear();
		* 设置读写标识都为1,但是未清空内容
	
	int readableBytes();
		* 返回可读字节数(写索引 - 读索引)

	
	// 数据读取
	long  readUnsignedInt()
	ByteBuf retainedDuplicate()
	short readUnsignedByte()
	CharSequence getCharSequence(int index, int length, Charset charset);
		*  从指定的角标开始,读取指定长度的数据,使用指定编码后返回

	// 数据丢弃
	ByteBuf discardReadBytes();
		* 清空 ByteBuf 中已读取的数据,未读数据往前移动,从而使 ByteBuf 有多余的空间容纳新的数据
		* 可能会涉及内存复制,因为它需要移动 ByteBuf 中可读的字节到开始位置,这样的操作会影响性能
		* 一般在需要马上释放内存的时候使用收益会比较大
	
	ByteBuf discardSomeReadBytes();

	// 标记操作
	ByteBuf markReaderIndex()
		* 标记读角标

	ByteBuf resetReaderIndex()
		* 重置读角标为标记角标

	// 复制和重用
	ByteBuf duplicate();
		* 复制一个新的缓冲区,所有的数据,包括index都是一样的
		* 共享内存,数据变化会互相影响

	ByteBuf slice();
		* 复制新的缓冲区,有独立的index
			capacity = '原来buffer的可读长度'
			rindex = 0
			windex = '原buffer的windex'
		* 共享内存,数据变化会互相影响
		* 把未读的数据复制出来

	ByteBuf slice(int index, int length);

	ByteBuf order(ByteOrder endianness);
		

	// 其他
	boolean hasArray()
		* 是否支持访问数组(可能,非堆的buf)
		* 访问非堆缓冲区 ByteBuf 的数组,会抛出异常 UnsupportedOperationException

	byte[] array();
		* 获取关联的数组
	
	int indexOf(int fromIndex, int toIndex, byte value);
		* 判断value知否存在与buffer,从from开始,到to结束
	
	int bytesBefore(byte value);
	