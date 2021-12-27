--------------------
ByteBuf
--------------------
	# bytebuf 抽象类: public abstract class ByteBuf implements ReferenceCounted, Comparable<ByteBuf>


--------------------
static
--------------------

--------------------
this
--------------------
	public abstract int capacity();
	public abstract ByteBuf capacity(int newCapacity);
	public abstract int maxCapacity();
	public abstract ByteBufAllocator alloc();
	@Deprecated
	public abstract ByteOrder order();
	@Deprecated
	public abstract ByteBuf order(ByteOrder endianness);
	public abstract ByteBuf unwrap();
	public abstract boolean isDirect();
	public abstract boolean isReadOnly();
	public abstract ByteBuf asReadOnly();
	public abstract int readerIndex();
	public abstract ByteBuf readerIndex(int readerIndex);
	public abstract int writerIndex();
	public abstract ByteBuf writerIndex(int writerIndex);
	public abstract ByteBuf setIndex(int readerIndex, int writerIndex);
	public abstract int readableBytes();
	public abstract int writableBytes();
	public abstract int maxWritableBytes();
	public int maxFastWritableBytes()
	public abstract boolean isReadable()
	public abstract boolean isReadable(int size);
	public abstract boolean isWritable();
	public abstract boolean isWritable(int size);
	public abstract ByteBuf clear();
	public abstract ByteBuf markReaderIndex();
	public abstract ByteBuf resetReaderIndex();
	public abstract ByteBuf markWriterIndex();
	public abstract ByteBuf resetWriterIndex();
	public abstract ByteBuf discardReadBytes();
	public abstract ByteBuf discardSomeReadBytes();
	public abstract ByteBuf ensureWritable(int minWritableBytes);
	public abstract int ensureWritable(int minWritableBytes, boolean force);
	public abstract boolean getBoolean(int index);
	public abstract byte  getByte(int index);
	public abstract short getUnsignedByte(int index);
	public abstract short getShort(int index);
	public abstract short getShortLE(int index);
	public abstract int getUnsignedShort(int index);
	public abstract int getUnsignedShortLE(int index);
	public abstract int   getMedium(int index);
	public abstract int getMediumLE(int index);
	public abstract int   getUnsignedMedium(int index);
	public abstract int   getUnsignedMediumLE(int index);
	public abstract int   getInt(int index);
	public abstract int   getIntLE(int index);
	public abstract long  getUnsignedInt(int index);
	public abstract long  getUnsignedIntLE(int index);
	public abstract long  getLong(int index);
	public abstract long  getLongLE(int index);
	public abstract char  getChar(int index);
	public abstract float getFloat(int index);
	public float getFloatLE(int index)
	public abstract double getDouble(int index);
	public double getDoubleLE(int index)
	public abstract ByteBuf getBytes(int index, ByteBuf dst);
	public abstract ByteBuf getBytes(int index, ByteBuf dst, int length);
	public abstract ByteBuf getBytes(int index, ByteBuf dst, int dstIndex, int length);
	public abstract ByteBuf getBytes(int index, byte[] dst);
	public abstract ByteBuf getBytes(int index, byte[] dst, int dstIndex, int length);
	public abstract ByteBuf getBytes(int index, ByteBuffer dst);
	public abstract ByteBuf getBytes(int index, OutputStream out, int length) throws IOException;
	public abstract int getBytes(int index, GatheringByteChannel out, int length) throws IOException;
	public abstract int getBytes(int index, FileChannel out, long position, int length) throws IOException;
	public abstract CharSequence getCharSequence(int index, int length, Charset charset);
	public abstract ByteBuf setBoolean(int index, boolean value);
	public abstract ByteBuf setByte(int index, int value);
	public abstract ByteBuf setShort(int index, int value);
	public abstract ByteBuf setShortLE(int index, int value);
	public abstract ByteBuf setMedium(int index, int value);
	public abstract ByteBuf setMediumLE(int index, int value);
	public abstract ByteBuf setInt(int index, int value);
	public abstract ByteBuf setIntLE(int index, int value);
	public abstract ByteBuf setLong(int index, long value);
	public abstract ByteBuf setLongLE(int index, long value);
	public abstract ByteBuf setChar(int index, int value);
	public abstract ByteBuf setFloat(int index, float value);
	public ByteBuf setFloatLE(int index, float value)
	public abstract ByteBuf setDouble(int index, double value);
	public ByteBuf setDoubleLE(int index, double value)
	public abstract ByteBuf setBytes(int index, ByteBuf src);
	public abstract ByteBuf setBytes(int index, ByteBuf src, int length);
	public abstract ByteBuf setBytes(int index, ByteBuf src, int srcIndex, int length);
	public abstract ByteBuf setBytes(int index, byte[] src);
	public abstract ByteBuf setBytes(int index, byte[] src, int srcIndex, int length);
	public abstract ByteBuf setBytes(int index, ByteBuffer src);
	public abstract int setBytes(int index, InputStream in, int length) throws IOException;
	public abstract int setBytes(int index, ScatteringByteChannel in, int length) throws IOException;
	public abstract int setBytes(int index, FileChannel in, long position, int length) throws IOException;
	public abstract ByteBuf setZero(int index, int length);
	public abstract int setCharSequence(int index, CharSequence sequence, Charset charset);
	public abstract boolean readBoolean();
	public abstract byte  readByte();
	public abstract short readUnsignedByte();
	public abstract short readShort();
	public abstract short readShortLE();
	public abstract int   readUnsignedShort();
	public abstract int   readUnsignedShortLE();
	public abstract int   readMedium();
	public abstract int   readMediumLE();
	public abstract int   readUnsignedMedium();
	public abstract int   readUnsignedMediumLE();
	public abstract int   readInt();
	public abstract int   readIntLE();
	public abstract long  readUnsignedInt();
	public abstract long  readUnsignedIntLE();
	public abstract long  readLong();
	public abstract long  readLongLE();
	public abstract char  readChar();
	public abstract float readFloat();
	public float readFloatLE()
	public abstract double readDouble();
	public double readDoubleLE()
	public abstract ByteBuf readBytes(int length);
	public abstract ByteBuf readSlice(int length);
	public abstract ByteBuf readRetainedSlice(int length);
	public abstract ByteBuf readBytes(ByteBuf dst);
	public abstract ByteBuf readBytes(ByteBuf dst, int length);
	public abstract ByteBuf readBytes(ByteBuf dst, int dstIndex, int length);
	public abstract ByteBuf readBytes(byte[] dst);
	public abstract ByteBuf readBytes(byte[] dst, int dstIndex, int length);
	public abstract ByteBuf readBytes(ByteBuffer dst);
	public abstract ByteBuf readBytes(OutputStream out, int length) throws IOException;
	public abstract int readBytes(GatheringByteChannel out, int length) throws IOException;
	public abstract CharSequence readCharSequence(int length, Charset charset);
	public abstract int readBytes(FileChannel out, long position, int length) throws IOException;
	public abstract ByteBuf skipBytes(int length);
	public abstract ByteBuf writeBoolean(boolean value);
	public abstract ByteBuf writeByte(int value);
	public abstract ByteBuf writeShort(int value);
	public abstract ByteBuf writeShortLE(int value);
	public abstract ByteBuf writeMedium(int value);
	public abstract ByteBuf writeMediumLE(int value);
	public abstract ByteBuf writeInt(int value);
	public abstract ByteBuf writeIntLE(int value);
	public abstract ByteBuf writeLong(long value);
	public abstract ByteBuf writeLongLE(long value);
	public abstract ByteBuf writeChar(int value);
	public abstract ByteBuf writeFloat(float value);
	public ByteBuf writeFloatLE(float value)
	public abstract ByteBuf writeDouble(double value);
	public ByteBuf writeDoubleLE(double value)
	public abstract ByteBuf writeBytes(ByteBuf src);
	public abstract ByteBuf writeBytes(ByteBuf src, int length);
	public abstract ByteBuf writeBytes(ByteBuf src, int srcIndex, int length);
	public abstract ByteBuf writeBytes(byte[] src);
	public abstract ByteBuf writeBytes(byte[] src, int srcIndex, int length);
	public abstract ByteBuf writeBytes(ByteBuffer src);
	public abstract int writeBytes(InputStream in, int length) throws IOException;
	public abstract int writeBytes(ScatteringByteChannel in, int length) throws IOException;
	public abstract int writeBytes(FileChannel in, long position, int length) throws IOException;
	public abstract ByteBuf writeZero(int length);
	public abstract int writeCharSequence(CharSequence sequence, Charset charset);
	public abstract int indexOf(int fromIndex, int toIndex, byte value);
	public abstract int bytesBefore(byte value);
	public abstract int bytesBefore(int length, byte value);
	public abstract int bytesBefore(int index, int length, byte value);
	public abstract int forEachByte(ByteProcessor processor);
	public abstract int forEachByte(int index, int length, ByteProcessor processor);
	public abstract int forEachByteDesc(ByteProcessor processor);
	public abstract int forEachByteDesc(int index, int length, ByteProcessor processor);
	public abstract ByteBuf copy();
	public abstract ByteBuf copy(int index, int length);
	public abstract ByteBuf slice();
	public abstract ByteBuf retainedSlice();
	public abstract ByteBuf slice(int index, int length);
	public abstract ByteBuf retainedSlice(int index, int length);
	public abstract ByteBuf duplicate();
	public abstract ByteBuf retainedDuplicate();
	public abstract int nioBufferCount();
	public abstract ByteBuffer nioBuffer();
	public abstract ByteBuffer nioBuffer(int index, int length);
	public abstract ByteBuffer internalNioBuffer(int index, int length);
	public abstract ByteBuffer[] nioBuffers();
	public abstract ByteBuffer[] nioBuffers(int index, int length);
	public abstract boolean hasArray();
	public abstract byte[] array();
	public abstract int arrayOffset();
	public abstract boolean hasMemoryAddress();
	public abstract long memoryAddress();
	public boolean isContiguous()
	public abstract String toString(Charset charset);
	public abstract String toString(int index, int length, Charset charset);
	public abstract int hashCode();
	public abstract boolean equals(Object obj);
	public abstract int compareTo(ByteBuf buffer);
	public abstract String toString();
	public abstract ByteBuf retain(int increment);
	public abstract ByteBuf retain();
	public abstract ByteBuf touch();
	public abstract ByteBuf touch(Object hint);
	boolean isAccessible()
