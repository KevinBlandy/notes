------------------------------
MessageHandler
------------------------------
	# ��Ϣ�������ӿ�
		@FunctionalInterface
		public interface MessageHandler {
			void handleMessage(Message<?> message) throws MessagingException;
		}
