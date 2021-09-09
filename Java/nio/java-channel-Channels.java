-----------------------
Channels				|				
-----------------------
	# Channel 的工具类


-----------------------
Channels-静态方法		|				
-----------------------

	public static ReadableByteChannel newChannel(InputStream in)
		* 从 InputStream 获取 ReadableByteChannel
	
	public static WritableByteChannel newChannel(final OutputStream out)
		* 从 OutputStream 获取 WritableByteChannel
	
	public static InputStream newInputStream(ReadableByteChannel ch) 
	public static OutputStream newOutputStream(WritableByteChannel ch)

	public static InputStream newInputStream(AsynchronousByteChannel ch)
	public static OutputStream newOutputStream(AsynchronousByteChannel ch)

	public static Reader newReader(ReadableByteChannel ch, CharsetDecoder dec, int minBufferCap)
	public static Reader newReader(ReadableByteChannel ch, String csName)
	public static Reader newReader(ReadableByteChannel ch, Charset charset) 
	public static Writer newWriter(WritableByteChannel ch, CharsetEncoder enc, int minBufferCap)
	public static Writer newWriter(WritableByteChannel ch, String csName)
	public static Writer newWriter(WritableByteChannel ch, Charset charset)
