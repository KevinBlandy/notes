-------------------------------
SseEmitter 
-------------------------------
	# Spring 对 Server-Sent Events 的实现

	# SSE
		http://www.ruanyifeng.com/blog/2017/05/server-sent_events.html

	# 类结构体系
		public class SseEmitter extends ResponseBodyEmitter 
	
	# SseEmitter
		public SseEmitter()
		public SseEmitter(Long timeout)
		protected void extendResponse(ServerHttpResponse outputMessage) 

		public void send(Object object) throws IOException
		public void send(Object object, @Nullable MediaType mediaType) throws IOException
		public void send(SseEventBuilder builder) throws IOException
			* 发送数据，可以指定消息体类型
			* 传入的是一个非 SseEventBuilder 对象，那么传递参数会被封装到 data 中

		public String toString()

		public static SseEventBuilder event()
			* 返回一个事件消息构建器
	
		public interface SseEventBuilder
			* 事件消息构建接口

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
			* 发送数据，可以指定消息体类型
			* 传入的是一个非 SseEventBuilder 对象，那么传递参数会被封装到 data 中

		public synchronized void complete()
		public synchronized void completeWithError(Throwable ex)
			* 执行完毕，可以指定一个Error

		public synchronized void onTimeout(Runnable callback) 
			* 处理超时事件
		public synchronized void onError(Consumer<Throwable> callback) 
			* 处理异常事件
		public synchronized void onCompletion(Runnable callback)
			* 处理完成事件
		
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
