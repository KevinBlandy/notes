--------------------------------
异常处理
--------------------------------
	# 异步消息的问题
		* 在处理消息的时候，有可能会出错并抛出异常。因为STOMP消息异步的特点，发送者可能永远也不会知道出现了错误。
		* @MessageExceptionHandler 标注的方法能够处理消息方法中所抛出的异常。
		* 我们可以把错误发送给用户特定的目的地上，然后用户从该目的地上订阅消息，从而用户就能知道自己出现了什么错误。

	
	# Demo
		@MessageExceptionHandler(Exception.class)
		@SendToUser("/queue/errors")
		public Exception handleExceptions(Exception t){
			t.printStackTrace();
			return t;
		}
	