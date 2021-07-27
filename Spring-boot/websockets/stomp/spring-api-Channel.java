-----------------------------
Channel
-----------------------------
	# Channel 接口体系
		MessageChannel
			|-OrderedMessageChannelDecorator OrderedMessageChannelDecorator implements MessageChannel
			|-SubscribableChannel SubscribableChannel extends MessageChannel
			|-AbstractMessageChannel implements MessageChannel, InterceptableChannel, BeanNameAware
				|-AbstractSubscribableChannel extends AbstractMessageChannel implements SubscribableChannel
					|-ExecutorSubscribableChannel extends AbstractSubscribableChannel
	
-----------------------------
MessageChannel
-----------------------------
	# 消息通道接口
		long INDEFINITE_TIMEOUT = -1;
		default boolean send(Message<?> message) {
			return send(message, INDEFINITE_TIMEOUT);
		}

		boolean send(Message<?> message, long timeout);


-----------------------------------
SubscribableChannel
-----------------------------------
	# 订阅关系通道接口
		public interface SubscribableChannel extends MessageChannel
	
	# 方法
		boolean subscribe(MessageHandler handler);
		boolean unsubscribe(MessageHandler handler);

-----------------------------------
ExecutorSubscribableChannel
-----------------------------------
	# 带有执行器的SubscribableChannel
		public class ExecutorSubscribableChannel extends AbstractSubscribableChannel 
	
	# 方法
		public ExecutorSubscribableChannel()
		public ExecutorSubscribableChannel(@Nullable Executor executor)
		
		public Executor getExecutor()
		public void setInterceptors(List<ChannelInterceptor> interceptors) 
		public void addInterceptor(ChannelInterceptor interceptor)
		public void addInterceptor(int index, ChannelInterceptor interceptor)
		public boolean sendInternal(Message<?> message, long timeout) 

