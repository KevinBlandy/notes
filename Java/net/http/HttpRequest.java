---------------------------
HttpRequest
---------------------------
	# ��ʾһ�� http ����, ������

	# ��̬����
		HttpRequest.Builder newBuilder(URI uri) 
		HttpRequest.Builder newBuilder()
		newBuilder(HttpRequest request, BiPredicate<String, String> filter) 

	# ʵ������
		abstract Optional<BodyPublisher> bodyPublisher()
		abstract String method();
		abstract Optional<Duration> timeout();
			* �����ָ���ĳ�ʱʱ����δ�յ���Ӧ, ����׳�HttpTimeoutException�쳣��

		abstract boolean expectContinue();
			* HTTP������ܰ�����Ϊexpect���ײ��ֶ�, ��ֵΪ "100-Continue",
			* ��������˴��ײ��ֶ�, ��ͻ���ֻ�������������ͷ�ļ�, ����Ԥ�Ʒ����������ش�����Ӧ��100-Continue��Ӧ
			* �յ�����Ӧ��, �ͻ��˽��������巢�͵�������, �ڿͻ��˷���ʵ��������֮ǰ, �ͻ���ʹ�ô˼��������������Ƿ���Ի���������ײ���������
			
			* Ĭ�������, ���ײ��ֶ�δ����, ��Ҫ�������󹹽�����expectContinue(true)���������ô˹���
			* ��ע��, �������󹹽�����header("expect", "100-Continue")�����������ô˹���, ����ʹ��expectContinue(true)����������
				
		abstract URI uri();
		abstract Optional<HttpClient.Version> version();
		abstract HttpHeaders headers();
		
	
	# Request �����ԻḲ�� HttpClient ����ͬ������
		HttpClient.Version
	
---------------------------
Builder
---------------------------
	# Builder������ʽ
		Builder uri(URI uri);
		Builder expectContinue(boolean enable);
		Builder version(HttpClient.Version version);
		Builder header(String name, String value);
		Builder headers(String... headers);
			* ��������ͷ

		Builder timeout(Duration duration);
		Builder setHeader(String name, String value);

		Builder GET();
		Builder POST(BodyPublisher bodyPublisher);
		Builder PUT(BodyPublisher bodyPublisher);
		Builder DELETE();
		Builder HEAD()

		Builder method(String method, BodyPublisher bodyPublisher);
		
		HttpRequest build();

		Builder copy();
	
	# ��̬����
		public static Builder newBuilder(HttpRequest request, BiPredicate<String, String> filter) 

------------------------------
BodyPublisher
------------------------------
	# ���������ɵĽӿ�
		BodyPublisher extends Flow.Publisher<ByteBuffer>
	
	# ���󷽷�
		long contentLength();
		void subscribe(Subscriber<? super T> subscriber);


----------------------
RequestPublishers
----------------------
	# ��������� BodyPublisher �ľ�̬ʵ����

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
	# ����ʵ����, �ṩ�˺ܶ��ֳɵ�body����

	# ��̬����
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
			* �յ�������
		  static BodyPublisher concat(BodyPublisher... publishers) 

