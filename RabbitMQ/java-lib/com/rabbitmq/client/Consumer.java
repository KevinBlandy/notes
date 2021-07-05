---------------------------------------
Consumer
---------------------------------------
	# public interface Consumer 
		* push消费模式实现接口
	
	# 系统自带的实现类
		DefaultConsumer

---------------------------------------
static
---------------------------------------


---------------------------------------
this
---------------------------------------


	public abstract void handleShutdownSignal(String consumerTag, ShutdownSignalException sig)
		* Channel 或者 Connection 关闭的时候会调用

	public abstract void handleCancel(String consumerTag)
	public abstract void handleCancelOk(String consumerTag)
		* 消费者在隐式或者显示取消订阅时调用

	public abstract void handleRecoverOk(String consumerTag)
	public abstract void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
	public abstract void handleConsumeOk(String consumerTag)
		* 会在其他方法之前调用
