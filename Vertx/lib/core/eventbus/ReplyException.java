-----------------------
ReplyException
-----------------------
	# 回复异常消息异常对象： class ReplyException extends VertxException 

	
	 public ReplyException(ReplyFailure failureType, int failureCode, String message) 
	 public ReplyException(ReplyFailure failureType, String message) 
	 public ReplyException(ReplyFailure failureType)

	 public ReplyFailure failureType()
	 public int failureCode()
