-------------------------------
ByteBuffer						|
-------------------------------
	# Buffer 子类


-------------------------------
ByteBuffer-API					|
-------------------------------
	# 属性
		

	# 实例方法
		//========================读相关
		byte get();
			* 返回一个byte的数据
			* 每次执行 get(),都会让 position 向前移动一位

		ByteBuffer get(byte[] dst);
			* 读取N(dst.length)个元素到 dst, position 会向前移动,所以:'数组的长度,不能超出:remaining,会抛出异常'
			* 数组长度大于 buf 的可读/写元素(remaining)长度,抛出异常
			* 数组长度小于 buf 的可读/写元素(remaining)长度,仅仅装满数组

		ByteBuffer get(byte[] dst, int offset, int length);
			* 读取length个元素到 dst, position 会向前移动,所以:'length,不能超出:remaining,会抛出异常'
			* offset 数组开始写入的位置
			* length 从当前position读取多少个字节数据

		byte get(int index);
			* 绝对读,读取byteBuffer底层的bytes中下标为index的byte，不改变position			
		
		byte[] array();
			* 获取缓冲区内部的数组引用引用

		//========================写与修改相关
		ByteBuffer put(byte b);
			* 写入一个字节,position向前移动

		ByteBuffer put(int index, byte b);
			* 绝对写入,不会移动postion,如果有.会被替换

		ByteBuffer put(byte[] src);
			* 写入(src.length)个元素到 buf, position 会向前移动,所以:'数组的长度,不能超出:remaining,会抛出异常'

		ByteBuffer put(byte[] src, int offset, int length);
			* 写入(length)个元素到 buf, position 会向前移动,所以:'length的长度,不能超出:remaining,会抛出异常'
			* offset 从数组哪个下标开始写
			* length 写入多少数据

		ByteBuffer put(ByteBuffer src);
			* 把 src 中的数据写入到当前 buf,指针向前移动,所以:'src的remaining,不能超出当前buf:remaining,会抛出异常'
			* 当前remaining就是:可以写入多少个数据
			* src remaining就是:可以读取多少个数据

		//========================位属性相关
		int remaining();
			* 返回当前位置与限制之间的元素数。
			* 在写模式下,返回'剩余可写'的空间
			* 在读模式下,返回'剩余可读'的空间
			* 源码:limit - position;
		boolean	hasRemaining();
			* 在读模式下,判断是否有还有可读的元素
			* 在写模式下,判断是否还可以写入新的元素
			* 源码:position < limit;
		
		int	limit();
			* 返回lmit
			* 在写模式下,返回的就是'最大可以写入多少'
			* 在读模式下,返回的就是'最多可以读取多少'
		Buffer	limit(int newLimit);
			* 设置新的 limit
		
		int	position();
			* 获取指针的位置
		Buffer	position(int newPosition);
			* 重新设置指针的位置

		int	capacity();
			* 返回开辟的内存大小

		Buffer clear();
			* 清空,并没有删除数据,仅仅是修改了指针
			* 源码:position = 0;limit = capacity;mark = -1;
			* '经常用于从读模式,切换到写模式'

		Buffer flip();
			* 复位,修改写模式为读
			* 源码:limit = position;position = 0;mark = -1;
			* '经常用于从写模式,切换到读模式'
		
		Buffer rewind();
			* 将position设回0，所以你可以重读Buffer中的所有数据。
			* limit保持不变，仍然表示能从Buffer中读取多少个元素（byte、char等）。

		Buffer mark();
			* 标记当前位置,可以标记Buffer中的一个特定position。
			* 之后可以通过调用Buffer.reset()方法恢复到这个position。

		Buffer reset();
			* 通过调用Buffer.mark()方法，可以标记Buffer中的一个特定position。
			* 之后可以通过调用Buffer.reset()方法恢复到这个position。


		//========================其他
		byte[] array(); 
			* 把该Buffere转换为同等长度的字节数组
			* 空的元素,默认值为0
		
		boolean	isDirect();
			* 判断当前的缓冲区是否是直接由系统分配内存
		
		boolean	isReadOnly();
			* 判断缓冲区是否是只读的

		ByteBuffer compact();
			* 清空缓冲区,只会清除已经读过的数据。
			* 任何未读的数据都被移到缓冲区的起始处,新写入的数据将放到缓冲区未读数据的后面。
			* 将所有未读的数据拷贝到Buffer起始处。然后将position设到最后一个未读元素正后面。
			* limit属性依然 跟 clear()方法一样，设置成capacity。
			* 现在Buffer准备好写数据了，但是不会覆盖未读的数据。
	
		int compareTo(ByteBuffer that);
			* compareTo()方法比较两个Buffer的剩余元素(byte、char等),如果满足下列条件，则认为一个Buffer'小于'另一个Buffer：
			1,第一个不相等的元素小于另一个Buffer中对应的元素 。
			2,所有元素都相等，但第一个Buffer比另一个先耗尽(第一个Buffer的元素个数比另一个少)

		boolean equals(Object ob);
			* 当满足下列条件时，表示两个Buffer相等：
			* 有相同的类型（byte、char、int等）。
			* Buffer中剩余的byte、char等的个数相等。
			* Buffer中所有剩余的byte、char等都相同。
			* 如你所见，equals只是比较Buffer的一部分，不是每一个在它里面的元素都比较。实际上，它只比较Buffer中的剩余元素。
		
		ByteBuffer asReadOnlyBuffer();
			* 映射出一个只读的缓冲区,'所有数据,属性都是一样的'(可以修改属性,不能修改数据)
			* 这个缓冲区的数据仅仅只会跟随母体的修改而修改
			* 他们的属性值独立
		
		ByteBuffer duplicate();
			* 复制出来一个缓冲区,'所有数据,属性都是一样的'(可以修改属性和数据)
			* 修改数据对双方都是可见的
			* 如果主体是只读,那么副体也是只读
			* 他们的属性值独立

		ByteBuffer slice();
			* 复制出来一个新的缓冲区,(可以修改属性和数据)
			* 属性修改
				pos = 0;
				lim = '原buffer的limit';
				cap = '原buffer的limit'
			* 修改数据对双方都是可见的
			* 如果主体是只读,那么副体也是只读
			* 他们的属性值独立

		boolean hasArray();
			* '未知'
		int arrayOffset(); 
			* '未知'

		ByteOrder order();
			* 获取写入顺序
		ByteBuffer order(ByteOrder bo)；
			* 设置写入顺序

		int hashCode();
		String toString(); 
		
		int getInt();
			* 一次性读取四个字节,组成一个 int 数据
			* 指针会向前移动4

		char getChar();
		char getChar(int index);
		double getDouble();
		double getDouble(int index);
		float getFloat(); 
		float getFloat(int index);
		int getInt(int index);
		long getLong();
		long getLong(int index);
		short getShort();
		short getShort(int index);
		
		ByteBuffer putInt(int value);
			* 把 int 值存入,一次性设置四个字节
			* 指针会向前移动4

		ByteBuffer putChar(char value);
		ByteBuffer putChar(int index, char value);
		ByteBuffer putDouble(double value);
		ByteBuffer putDouble(int index, double value);
		ByteBuffer putFloat(float value);
		ByteBuffer putFloat(int index, float value);
		ByteBuffer putInt(int index, int value);
		ByteBuffer putLong(int index, long value);
		ByteBuffer putLong(long value);
		ByteBuffer putShort(int index, short value);
		ByteBuffer putShort(short value);

		CharBuffer asCharBuffer();
		DoubleBuffer asDoubleBuffer();
		FloatBuffer asFloatBuffer();
		IntBuffer asIntBuffer();
		LongBuffer asLongBuffer();
		ShortBuffer asShortBuffer(); 


	# 静态
		ByteBuffer allocateDirect(int capacity);
			* 创建指定大小的缓冲区,非JVM内存,直接使用系统内存.
			* '在数据量达到一定大小的时候才会提现出速度上的优势'
		
		ByteBuffer allocate(int capacity);
			* 创建指定大小的缓冲区
			* '该缓冲区是由JVM进行分配'
		
		ByteBuffer wrap(byte[] array);
			* 把一个字节数包装为 ByteBuffer
			* 这里的bytes和缓冲区用的是一块内存，如果修改缓冲区的数据数组的数据也发生变化。
			* po : 0
			  li : array.length
			  ca : array.length

		ByteBuffer wrap(byte[] array, int offset, int length);
			* 把一个字节数包装为 ByteBuffer,从字节数组的 offset 开始获取 length 个数据存入
			* 这里的bytes和缓冲区用的是一块内存，如果修改缓冲区的数据数组的数据也发生变化。
			* '该AIP仅仅是为了修改limit的位置'
			* po : 0
			  li : length
			  ca : array.length

