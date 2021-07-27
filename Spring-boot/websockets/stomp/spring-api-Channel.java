-----------------------------
Channel
-----------------------------
	# Channel �ӿ���ϵ
		MessageChannel
			|-OrderedMessageChannelDecorator OrderedMessageChannelDecorator implements MessageChannel
			|-SubscribableChannel SubscribableChannel extends MessageChannel
			|-AbstractMessageChannel implements MessageChannel, InterceptableChannel, BeanNameAware
				|-AbstractSubscribableChannel extends AbstractMessageChannel implements SubscribableChannel
					|-ExecutorSubscribableChannel extends AbstractSubscribableChannel
	
-----------------------------
MessageChannel
-----------------------------
	# ��Ϣͨ���ӿ�
		long INDEFINITE_TIMEOUT = -1;
		default boolean send(Message<?> message) {
			return send(message, INDEFINITE_TIMEOUT);
		}

		boolean send(Message<?> message, long timeout);


-----------------------------------
SubscribableChannel
-----------------------------------
	# ���Ĺ�ϵͨ���ӿ�
		public interface SubscribableChannel extends MessageChannel
	
	# ����
		boolean subscribe(MessageHandler handler);
		boolean unsubscribe(MessageHandler handler);

-----------------------------------
ExecutorSubscribableChannel
-----------------------------------
	# ����ִ������SubscribableChannel
		public class ExecutorSubscribableChannel extends AbstractSubscribableChannel 
	
	# ����
		public ExecutorSubscribableChannel()
		public ExecutorSubscribableChannel(@Nullable Executor executor)
		
		public Executor getExecutor()
		public void setInterceptors(List<ChannelInterceptor> interceptors) 
		public void addInterceptor(ChannelInterceptor interceptor)
		public void addInterceptor(int index, ChannelInterceptor interceptor)
		public boolean sendInternal(Message<?> message, long timeout) 

