------------------------------
BodyPublisher
------------------------------
	# 请求体生成的接口
		BodyPublisher extends Flow.Publisher<ByteBuffer>
	
	# 抽象方法
		long contentLength();
		void subscribe(Subscriber<? super T> subscriber);


----------------------
RequestPublishers
----------------------
	# 包含了诸多 BodyPublisher 的静态实现类

		class ByteArrayPublisher implements BodyPublisher
			ByteArrayPublisher(byte[] content)
			ByteArrayPublisher(byte[] content, int offset, int length)

		class IterablePublisher implements BodyPublisher
			IterablePublisher(Iterable<byte[]> content)
		
		class EmptyPublisher implements BodyPublisher
		
		class FilePublisher implements BodyPublisher
		class StreamIterator implements Iterator<ByteBuffer>
		class InputStreamPublisher implements BodyPublisher
		class PublisherAdapter implements BodyPublisher


------------------------------
BodyPublishers
------------------------------
	# 基于实现类, 提供了很多现成的body构建

	# 静态方法
		 static BodyPublisher fromPublisher(Flow.Publisher<? extends ByteBuffer> publisher)
		 static BodyPublisher fromPublisher(Flow.Publisher<? extends ByteBuffer> publisher, long contentLength)
		 static BodyPublisher ofString(String body)
		 static BodyPublisher ofString(String s, Charset charset)
		 static BodyPublisher ofInputStream(Supplier<? extends InputStream> streamSupplier)
		 static BodyPublisher ofByteArray(byte[] buf)
		 static BodyPublisher ofByteArray(byte[] buf, int offset, int length)
		 static BodyPublisher ofFile(Path path)
		 static BodyPublisher ofByteArrays(Iterable<byte[]> iter)
		 static BodyPublisher noBody()
			* 空的请求体

