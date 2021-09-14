------------------------
Message
------------------------
	# 消息对象
		public class Message implements Serializable 
	
	# 方法
		public Message(byte[] body)
		public Message(byte[] body, MessageProperties messageProperties)

		public static void addAllowedListPatterns(String... patterns) 
		public static void setDefaultEncoding(String encoding) 

		public byte[] getBody()
		public MessageProperties getMessageProperties()

