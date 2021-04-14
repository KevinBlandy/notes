--------------------
ReplyFailure
--------------------
	# 回复异常类型的枚举 enum ReplyFailure 

		TIMEOUT
			* 超过时间，都没有获取到回复

		NO_HANDLERS
			* 没有处理器处理

		RECIPIENT_FAILURE
			* 消费者主动失败，调用了fail

		ERROR


		public static ReplyFailure fromInt(int i)
		public int toInt()