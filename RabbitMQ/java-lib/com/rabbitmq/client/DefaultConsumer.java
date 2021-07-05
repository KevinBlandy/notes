---------------------------------------
DefaultConsumer
---------------------------------------
	# public class DefaultConsumer implements Consumer
		* 系统自带的Consumer实现类

---------------------------------------
static
---------------------------------------


---------------------------------------
this
---------------------------------------
	public DefaultConsumer(Channel channel)


	public Channel getChannel()
	public void handleShutdownSignal(String consumerTag, ShutdownSignalException sig)
	public String getConsumerTag()
		* 消费者标签，同一个Channel中的不同的消费者不一样
	
	public void handleConsumeOk(String consumerTag)
	public void handleCancelOk(String consumerTag)
	public void handleCancel(String consumerTag)
	public void handleRecoverOk(String consumerTag)

	public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
		* 消费数据
