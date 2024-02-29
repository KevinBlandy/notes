----------------------------
������Ϣ					|
----------------------------
	# RemoteEndpoint
		void setBatchingAllowed(boolean allowed) throws IOException;
			* ������Ϣ������
		boolean getBatchingAllowed();
			* �Ƿ�������Ϣ������
		void flushBatch() throws IOException;
			* ǿ��ˢ����Ϣ���������Ѿ��ɼ�,δ���͵���Ϣ
		void sendPing(ByteBuffer applicationData) throws IOException, IllegalArgumentException;
		void sendPong(ByteBuffer applicationData) throws IOException, IllegalArgumentException;


----------------------------
������Ϣ-ͬ��				|
----------------------------
	# ͬ����Ϣ�Ͳ��������
	# RemoteEndpoint.Basic
		void sendText(String text) throws IOException;
		void sendBinary(ByteBuffer data) throws IOException;

		void sendText(String partialMessage, boolean isLast) throws IOException;
		void sendBinary(ByteBuffer partialByte, boolean isLast) throws IOException; // or Iterable<byte[]>

		OutputStream getSendStream() throws IOException;
		Writer getSendWriter() throws IOException;

		void sendObject(Object data) throws IOException, EncodeException;


----------------------------
������Ϣ-�첽				|
----------------------------
	# RemoteEndpoint.Async
		long getSendTimeout();
			* ��ȡ��ʱʱ��
		void setSendTimeout(long timeoutmillis);
			* ���ó�ʱʱ��

		void sendText(String text, SendHandler handler);
		void sendBinary(ByteBuffer data, SendHandler handler);
		void sendObject(Object data, SendHandler handler);

		Future<Void> sendText(String text);
		Future<Void> sendBinary(ByteBuffer data);
		Future<Void> sendObject(Object data);

	
	# ͨ�� SendHandler �������첽��Ϣ
		* ���ַ�ʽ��Ҫ'���⴫��'һ���ص����� SendHandler �ӿڵ�ʵ��,������ Lambda ���ʽӴ
		* ���첽��Ϣ���ͺ�,��ص� SendHandler �ӿ�ʵ���� onResult ����,���Ҵ��� SendResult ����
		* SendHandler API
			void onResult(SendResult result);
		
		* SendResult API
			public Throwable getException();
				* ���쳣��ʱ���ȡ�쳣,���δ�����쳣,���ֵΪ null
			public boolean isOK();
				* ���쳣��ʱ���ֵ Ϊ false,��֮Ϊ true
	
		* Demo
			session.getAsyncRemote().sendText("response", (sendResult) ->{
				if(sendResult.isOK()){
					System.out.println("��Ϣ���ͳɹ�");
				}else {
					Throwable throwable = sendResult.getException();
				}
			});

		* �����ͨ�� SendHandler �������Զ������,���͵�API(����)�ڱ���ʹ���֮ǰ���Ѿ�������,��������ʱ�����쳣,	onResult��isOk()Ϊfalse,���Ե�����getException();����ȡ�쳣


	# ͨ�� Future �������첽��Ϣ
		* ���ַ�ʽͨ�����ص� Future ��������ȡ��Ϣ
		* Future API
			boolean cancel(boolean mayInterruptIfRunning);
				* ȡ��������Ϊ
			boolean isCancelled();
				* �Ƿ��Ѿ��ɹ�ȡ����������Ϊ
			boolean isDone();
				* ȷ����Ϣ�Ƿ��Ѿ�����
			V get() throws InterruptedException, ExecutionException;
				* �÷���������,ֱ��������Ϣ�Ķ������
				* �����ִ�еĹ����з����쳣,���׳�'ExecutionException'�쳣,����ͨ��'ExecutionException'��getCause();������ȡ
			V get(long timeout, TimeUnit unit)throws InterruptedException, ExecutionException, TimeoutException;
				* ͬ��,�����÷����и���ʱʱ��

		* �����ͨ�� Future �������Զ������,���͵�API(����)�ڱ���ʹ���֮ǰ���Ѿ�������,��������ʱ�����쳣,���쳣���� ExecutionException ���з�װ,ͨ�� Future �� get();��������ȡ
		* Demo
	
	# ���ַ�ʽ(SendHandler,Future)�ĶԱ�,�ܽ�
		Future
			* ����ͨ��ȡ����Ϣ��������Ԥ�������
			..��
		SendHandler
			* �����Ը�Ԥ�������
			...
		
	# �첽���ͳ�ʱ
		* RemoteEndpoint.Async
			void setSendTimeout(long timeoutmillis);
		* �����ֵΪ�κη�����,���ʾû�г�ʱʱ��
		* �ڳ�ʱ�������
			SendHandler	-> ���� TimeoutException 
			Future		-> get();�����׳� ExecutionException
		
		
	
----------------------------
������ϢAPI�ܽ�				|
----------------------------
	# ������Ϣ
		* ping
			void sendPing(ByteBuffer applicationData) throws IOException, IllegalArgumentException;
		* pong
			void sendPong(ByteBuffer applicationData) throws IOException, IllegalArgumentException;
	
	# ͬ����Ϣ
		* �ı�
			void sendText(String text) throws IOException;
			void sendText(String partialMessage, boolean isLast) throws IOException;
			Writer getSendWriter() throws IOException;
		
		* ������
			void sendBinary(ByteBuffer data) throws IOException;
			void sendBinary(ByteBuffer partialByte, boolean isLast) throws IOException; // or Iterable<byte[]>
			OutputStream getSendStream() throws IOException;
			

			void sendObject(Object data) throws IOException, EncodeException;
	
	# �첽��Ϣ
		* SendHandler ģʽ
			void sendText(String text, SendHandler handler);
			void sendBinary(ByteBuffer data, SendHandler handler);
			void sendObject(Object data, SendHandler handler);
		
		* Future ģʽ
			Future<Void> sendText(String text);
			Future<Void> sendBinary(ByteBuffer data);
			Future<Void> sendObject(Object data);
	
	# ע���̰߳�ȫ����
		* ������� RemoteEndpoint.Basic ������Ϣʱ���� RemoteEndpoint.Basic �� websocket ������æ�ڷ�����Ϣ�����磬��������߳���ͼͬʱ���÷��ͷ�������������ڷ���������Ϣ�Ĺ�������ͼ��������Ϣ����ô�������Ѵ��ڷ�æ״̬ʱ���õķ��ͷ������ܻ��׳� java.lang.IllegalStateException �쳣��