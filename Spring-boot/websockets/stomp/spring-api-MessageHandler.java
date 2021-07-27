------------------------------
MessageHandler
------------------------------
	# 消息处理器接口
		@FunctionalInterface
		public interface MessageHandler {
			void handleMessage(Message<?> message) throws MessagingException;
		}
