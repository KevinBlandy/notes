-------------------------------
SseEmitter 
-------------------------------
	# Spring �� Server-Sent Events ��ʵ��

	# SSE
		http://www.ruanyifeng.com/blog/2017/05/server-sent_events.html


		* SSE ʹ�� HTTP Э�飬���еķ����������֧�֡�WebSocket ��һ������Э�顣
		* SSE ������������ʹ�ü򵥣�WebSocket Э����Ը��ӡ�
		* SSE Ĭ��֧�ֶ���������WebSocket ��Ҫ�Լ�ʵ�֡�
		* SSE һ��ֻ���������ı���������������Ҫ������ͣ�WebSocket Ĭ��֧�ִ��Ͷ��������ݡ�
		* SSE ֧���Զ��巢�͵���Ϣ���͡�



	# ��ṹ��ϵ
		public class SseEmitter extends ResponseBodyEmitter 
	
	# SseEmitter
		public SseEmitter()
		public SseEmitter(Long timeout)
		protected void extendResponse(ServerHttpResponse outputMessage) 

		public void send(Object object) throws IOException
		public void send(Object object, @Nullable MediaType mediaType) throws IOException
		public void send(SseEventBuilder builder) throws IOException
			* �������ݣ�����ָ����Ϣ������
			* �������һ���� SseEventBuilder ������ô���ݲ����ᱻ��װ�� data ��

		public String toString()

		public static SseEventBuilder event()
			* ����һ���¼���Ϣ������
	
		public interface SseEventBuilder
			* �¼���Ϣ�����ӿ�

			SseEventBuilder id(String id);
			SseEventBuilder name(String eventName);
			SseEventBuilder reconnectTime(long reconnectTimeMillis);
			SseEventBuilder comment(String comment);
			SseEventBuilder data(Object object);
			SseEventBuilder data(Object object, @Nullable MediaType mediaType);
			Set<DataWithMediaType> build();
	
	# public class ResponseBodyEmitter
		public ResponseBodyEmitter()
		public ResponseBodyEmitter(Long timeout)

		public Long getTimeout()
		protected void extendResponse(ServerHttpResponse outputMessage)

		public void send(Object object) throws IOException
		public synchronized void send(Object object, @Nullable MediaType mediaType) throws IOException
			* �������ݣ�����ָ����Ϣ������
			* �������һ���� SseEventBuilder ������ô���ݲ����ᱻ��װ�� data ��

		public synchronized void complete()
		public synchronized void completeWithError(Throwable ex)
			* ִ����ϣ�����ָ��һ��Error

		public synchronized void onTimeout(Runnable callback) 
			* ����ʱ�¼�
		public synchronized void onError(Consumer<Throwable> callback) 
			* �����쳣�¼�
		public synchronized void onCompletion(Runnable callback)
			* ��������¼�
		
		public String toString()

		interface Handler
			void send(Object data, @Nullable MediaType mediaType) throws IOException;
			void complete();
			void completeWithError(Throwable failure);
			void onTimeout(Runnable callback);
			void onError(Consumer<Throwable> callback);
			void onCompletion(Runnable callback);

		public static class DataWithMediaType
			public DataWithMediaType(Object data, @Nullable MediaType mediaType) 
			public Object getData()
			public MediaType getMediaType()
