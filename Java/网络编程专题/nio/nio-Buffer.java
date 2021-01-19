------------------------------------
Buffer								|
------------------------------------
	# 是一个抽象类
	# 子抽象类
		ByteBuffer
		CharBuffer
		DoubleBuffer
		FloatBuffer
		IntBuffer 
		LongBuffer 
		ShortBuffer

	# 构建方式
		* 使用子抽象类的静态方法构建
			ByteBuffer buf = ByteBuffer.allocate(48);		//分配48字节capacity的ByteBuffer。
			CharBuffer buf = CharBuffer.allocate(1024);		//分配1024字节capacity的CharBuffer。

------------------------------------
Buffer 属性							|
------------------------------------
	# capacity,position,limit
	# 缓冲区本质上是一块可以写入数据，然后可以从中读取数据的内存。这块内存被包装成NIO Buffer对象，并提供了一组方法，用来方便的访问该块内存。
	# 为了理解Buffer的工作原理，需要熟悉它的三个属性：
		capacity
		position
		limit

	# capacity
		* 作为一个内存块，Buffer有一个固定的大小值，也叫"capacity".你只能往里写capacity个byte、long，char等类型。
		* 一旦Buffer满了，需要将其清空（'通过读数据或者清除数据'）才能继续写数据往里写数据。
		* '开辟的内存大小'
		
	# position
		* 当你写数据到Buffer中时，position表示当前的位置。初始的position值为0.当一个byte、long等数据写到Buffer后， position会向前移动到下一个可插入数据的Buffer单元。
		* position最大可为capacity – 1.
		* '指针位置'

	# limit
		* 在写模式下，Buffer的limit表示你最多能往Buffer里写多少数据。 写模式下，limit等于Buffer的capacity。			
		* 当切换Buffer到读模式时，limit会被设置成写模式下的position值。也就是说,也就是说可以读取到多少数据
		* '写的时候表示,可以写多少数据'
		* '读的是hi表示,可以读多少数据'


------------------------------------
Buffer 接口方法						|
------------------------------------
	Object		array();
	int			arrayOffset();
	int			capacity();
	Buffer		clear();
	Buffer		flip();
	boolean		hasArray();
	boolean		hasRemaining();
	boolean		isDirect();
	boolean		isReadOnly();
	int			limit();
	Buffer		limit(int newLimit);
	Buffer		mark();
	int			position();
	Buffer		position(int newPosition);
	int			remaining();
	Buffer		reset();
	Buffer		rewind();


