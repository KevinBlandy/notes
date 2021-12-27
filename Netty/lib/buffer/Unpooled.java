------------------
Unpooled
------------------
	# 非池化的ByteBuf工厂类: public final class Unpooled
	# 默认维护了
		private static final ByteBufAllocator ALLOC = UnpooledByteBufAllocator.DEFAULT;

------------------
static
------------------
	public static final ByteOrder BIG_ENDIAN = ByteOrder.BIG_ENDIAN;
	public static final ByteOrder LITTLE_ENDIAN = ByteOrder.LITTLE_ENDIAN;
		* 字节大小端

	public static final ByteBuf EMPTY_BUFFER = ALLOC.buffer(0, 0);
		* 空buf
	
	public static ByteBuf buffer()
	public static ByteBuf directBuffer()
	public static ByteBuf buffer(int initialCapacity)
	public static ByteBuf directBuffer(int initialCapacity)
	public static ByteBuf buffer(int initialCapacity, int maxCapacity)
	public static ByteBuf directBuffer(int initialCapacity, int maxCapacity)
	public static ByteBuf wrappedBuffer(byte[] array)
	public static ByteBuf wrappedBuffer(byte[] array, int offset, int length)
	public static ByteBuf wrappedBuffer(ByteBuffer buffer)
	public static ByteBuf wrappedBuffer(long memoryAddress, int size, boolean doFree)
	public static ByteBuf wrappedBuffer(ByteBuf buffer)
	public static ByteBuf wrappedBuffer(byte[]... arrays)
	public static ByteBuf wrappedBuffer(ByteBuf... buffers)
	public static ByteBuf wrappedBuffer(ByteBuffer... buffers)
	public static ByteBuf wrappedBuffer(int maxNumComponents, byte[]... arrays)
	public static ByteBuf wrappedBuffer(int maxNumComponents, ByteBuf... buffers)
	public static ByteBuf wrappedBuffer(int maxNumComponents, ByteBuffer... buffers)
	public static CompositeByteBuf compositeBuffer()
	public static CompositeByteBuf compositeBuffer(int maxNumComponents)
		* 返回一个复合缓冲区

	public static ByteBuf copiedBuffer(byte[] array)
	public static ByteBuf copiedBuffer(byte[] array, int offset, int length)
	public static ByteBuf copiedBuffer(ByteBuffer buffer)
	public static ByteBuf copiedBuffer(ByteBuf buffer)
	public static ByteBuf copiedBuffer(byte[]... arrays)
	public static ByteBuf copiedBuffer(ByteBuf... buffers)
	public static ByteBuf copiedBuffer(ByteBuffer... buffers)
	public static ByteBuf copiedBuffer(CharSequence string, Charset charset)
		* 把指定的string编码为ByteBuff
		* 会开辟字符串大小3倍长度的一个bufer
	
	public static ByteBuf copiedBuffer(CharSequence string, int offset, int length, Charset charset)
	public static ByteBuf copiedBuffer(char[] array, Charset charset)
	public static ByteBuf copiedBuffer(char[] array, int offset, int length, Charset charset)
	@Deprecated
    public static ByteBuf unmodifiableBuffer(ByteBuf buffer)
	public static ByteBuf copyInt(int value) 
	public static ByteBuf copyInt(int... values)
	public static ByteBuf copyShort(int value)
	public static ByteBuf copyShort(short... values)
	public static ByteBuf copyShort(int... values)
	public static ByteBuf copyMedium(int value) 
	public static ByteBuf copyMedium(int... values)
	public static ByteBuf copyLong(long value)
	public static ByteBuf copyLong(long... values)
	public static ByteBuf copyBoolean(boolean value)
	public static ByteBuf copyBoolean(boolean... values)
	public static ByteBuf copyFloat(float value)
	public static ByteBuf copyFloat(float... values)
	public static ByteBuf copyDouble(double value)
	public static ByteBuf copyDouble(double... values)
	public static ByteBuf unreleasableBuffer(ByteBuf buf)
	@Deprecated
    public static ByteBuf unmodifiableBuffer(ByteBuf... buffers)
	public static ByteBuf wrappedUnmodifiableBuffer(ByteBuf... buffers)



	