---------------------
EventBus
---------------------
	# 消息总线的设计
	
	# public class EventBus
		
		* 消息总线，同步执行

		public EventBus()
		public EventBus(String identifier)
		public EventBus(SubscriberExceptionHandler exceptionHandler)

		public final String identifier()

		public void register(Object object)
		public void unregister(Object object)
			* 注册监听者	
			
		public void post(Object event)
			* 发布事件
	

	# public class AsyncEventBus extends EventBus
		* 消息总线，异步执行

		public AsyncEventBus(String identifier, Executor executor) 
		public AsyncEventBus(Executor executor, SubscriberExceptionHandler subscriberExceptionHandler)
		public AsyncEventBus(Executor executor)
	
	
	# public class SubscriberExceptionContext
		* 异常上下文
		
		public EventBus getEventBus()
		public Object getEvent() 
		public Object getSubscriber()
		public Method getSubscriberMethod()
	
	# public class DeadEvent 
		public DeadEvent(Object source, Object event)
		public Object getSource()
		public Object getEvent()


	# 注解
		@AllowConcurrentEvents
			* 标识在事件方法上，表示允许并发执行
		
		@Subscribe
			* 标识在事件方法上，表示监听事件
		
		

