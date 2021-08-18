---------------------------------------
Channel
---------------------------------------
	# interface Channel extends ShutdownNotifier, AutoCloseable

	# Channel 不是线程安全的，注意不要共享
		* 一个线程一个Channel最好

---------------------------------------
static
---------------------------------------


---------------------------------------
this
---------------------------------------
	public abstract void close()
	public abstract void close(int arg0, String arg1)
	public abstract void abort()
	public abstract void abort(int arg0, String arg1)
	public abstract DeclareOk queueDeclarePassive(String queue)
		* 判断指定的quque是否存在，如果不存在的话，就抛出异常，并且关闭Channel

	public abstract void clearConfirmListeners()
	public abstract void addConfirmListener(ConfirmListener arg0)
	public abstract ConfirmListener addConfirmListener(ConfirmCallback arg0, ConfirmCallback arg1)
	public abstract void setDefaultConsumer(Consumer arg0)

	public abstract void addReturnListener(ReturnListener listener)
	public abstract ReturnListener addReturnListener(ReturnCallback returnCallback)
		* 在消息没有成功投递后，会反馈给生产者，就会调用这个方法
			void handleReturn(int replyCode,
				String replyText,
				String exchange,
				String routingKey,
				AMQP.BasicProperties properties,
				byte[] body) throws IOException;
			
		
	public abstract Consumer getDefaultConsumer()

	public abstract void exchangeDeclareNoWait(String exchange, String type, boolean durable, boolean autoDelete, boolean internal, Map arguments)
	public abstract void exchangeDeclareNoWait(String exchange, BuiltinExchangeType type, boolean durable, boolean autoDelete, boolean internal, Map arguments)
		* 跟 exchangeDeclare 一样，不建议使用
		* 这个方法不需要服务器返回，注意这个方法的返回值是void ，而普通的exchangeDeclare 方法的返回值是Exchange.DeclareOk，
		* 意思是在客户端声明了一个交换器之后，需要等待服务器的返回(服务器会返回 Exchange.Declare-Ok 这个 AMQP 命令)。

	public abstract void clearReturnListeners()

	public abstract void queueDeclareNoWait(String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments)
		* 跟 queueDeclare 一样，不建议使用
		* 这个也是不需要阻塞

	public abstract boolean removeConfirmListener(ConfirmListener arg0)

	public abstract DeclareOk exchangeDeclarePassive(String exchange)
		* 它主要用来检测相应的交换器是否存在。如果存在则正常返回:如果不存在则抛出异常 : 404 channel exception
		* 同时 Channel 也会被关闭
		
	public abstract void exchangeDeleteNoWait(String exchange, boolean ifUnused)
		* 跟 exchangeDelete 一样，删除交换机，它不阻塞

	public abstract void exchangeUnbindNoWait(String arg0, String arg1, String arg2, Map arg3)
	public abstract boolean removeReturnListener(ReturnListener arg0)

	public abstract DeclareOk queueDeclare(String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments)
	public abstract DeclareOk queueDeclare()
		* 声明队列
		* 相同名称相同属性可以多次重复声明，如果出现相同名称，不同属性则异常

		* 生产者和消费者都可以执行声明
		* 但是消费者如果已经在这个Channel上订阅了其他的quque，就不能重复声明了，必选先取消订阅，把Channel设置为“传输”模式，再次执行声明

		queue
			* 队列名称
		durable
			* 是否持久化
		exclusive
			* 是否设置为排他队列

			* 为 true 则设置队列为排他的。如果一个队列被声明为排他队列，该队列仅对首次声明它的 Connection 可见，并在连接断开时自动删除。
			* 排它队列是基于 Connection 的，也就是说同一个 Connection 下的 Channel 都可见，这些 Channel 可以同时访问这个队列
			* 当前 Connection 已经创声明了一个队列，其他 Connection 再次尝试声明同名队列，就会异常
			* 这个队列就算是设置了持久化(durable=true), 一旦连接关闭或者客户端退出，这个排他队列都会自动删除
			* 这种队列只适合同一个客户端既写又读的场景

		autoDelete
			* 是否自动删除
			
			* 自动删除的前提是，创建了之后至少有一个消费者进行过连接(如果从来没消费者连接过，则不会删除)
			* 之后所有消费者都断开了连接，才会自动删除

		arguments
			* 参数
		
	public abstract DeleteOk queueDelete(String queue)
	public abstract DeleteOk queueDelete(String queue, boolean ifUnused, boolean ifEmpty)
		* 删除队列
		
		ifUnused
			* 设置为 true 表示这个队列没有消费者的情况下才删除
			* 设置为 false 表示强制删除

		ifEmpty 
			* 设置为 true 表示在队列为空(队列里面没有任何消息堆积)的情况下才能够删除。

	public abstract void basicQos(int prefetchCount, boolean global)
	public abstract void basicQos(int prefetchSize, int prefetchCount, boolean global)
	public abstract void basicQos(int prefetchCount)
		* 一次性最多接受未被ACK消息的个数
		* 对于拉去模式消费无效

		* MQ会对每个消费者都设置一个: 计数，分发消息给这个消费者的时候 计数 +1，消费者确认消费后 计数 -1 
		* 如果计数满了，那么就会停止给他派发消息，类似于TCP/IP中的滑动窗口

		prefetchCount
			* 一次性最多接受未被ACK消息的个数
			* 设置为0表示不限制
		
		prefetchSize
			* 消费者可以接受的未ACK的消息的体积大小，单位是KB
			* 设置为0表示不限制
		
		global
			* 建议设置为 false
			* 使用两种 global 的模式，则会增加 RabbitMQ的负载，因为RabbitMQ 需要更多的资源来协调完成这些限制
		

	public abstract BindOk exchangeBind(String destination, String source, String routingKey)
	public abstract BindOk exchangeBind(String destination, String source, String routingKey, Map<String, Object> arguments)
	public abstract void exchangeBindNoWait(String destination, String source, String routingKey, Map<String, Object> arguments)
		* 绑定交换机到交换机
		* 消息从 source 转发到 destination

		destination
			* 目标交换机
		source
			* 源交换机
		routingKey
			* 路由KEY
		arguments
		

	public abstract UnbindOk exchangeUnbind(String arg0, String arg1, String arg2, Map arg3)
	public abstract UnbindOk exchangeUnbind(String arg0, String arg1, String arg2)

	public abstract BindOk queueBind(String queue, String exchange, String routingKey, Map<String, Object> arguments)
	public abstract BindOk queueBind(String queue, String exchange, String routingKey)
		* 绑定队列到交换机
		
		queue
			* 队列名称
		exchange
			* 交换机名称
		routingKey
			* 路由KEY


	public abstract void queueBindNoWait(String arg0, String arg1, String arg2, Map arg3)
		* 跟 queueBind 一样，只是不阻塞，不建议使用

	public abstract UnbindOk queueUnbind(String queue, String exchange, String routingKey, Map<String, Object> arguments)
	public abstract UnbindOk queueUnbind(String queue, String exchange, String routingKey)
		* 取消队列和交换机的绑定

		queue
			* 队列名称
		exchange
			* 交换机名称
		routingKey
			* 路由KEY

	public abstract PurgeOk queuePurge(String queue)
		* 清空队列内容，并不是删除队列
			
	public abstract int getChannelNumber()

	public abstract void basicPublish(String exchange, String routingKey, boolean mandatory, boolean immediate, BasicProperties props, byte[] body)
	public abstract void basicPublish(String exchange, String routingKey, BasicProperties props, byte[] body) 
	public abstract void basicPublish(String exchange, String routingKey, boolean mandatory, BasicProperties props, byte[] body)
		* 基本的消息发送
		exchange
			* 交换机，不能为null，可以设置为: ""，表示发送到默认的交换机中
		
		routingKey
			* 路由key
		
		mandatory
			* true 如果消息没有找到合适的队列，则通过 Basic.Return 命名返回给生产者
			* false 如果消息没有找到合适的队列，则直接删除

		immediate
			* true 消息成功加入了队列，但是与路由key，匹配的所有队列没有任何消费者，则通过 Basic.Return 命名返回给生产者

			* RabbitMQ 3 .0 版本开始去掉了对 imrnediate 参数的支持，对此 RabbitMQ 官方解释是 :
				imrnediate 参数会影响镜像队列的性能，增加了代码复杂性，建议采用 TTL 和 DLX 的方法代替
			* 如果参数是 true, 则会在客户端给出警告，并且在服务端给出异常

		props
		body
		

	public abstract DeleteOk exchangeDelete(String exchange)
	public abstract DeleteOk exchangeDelete(String exchange, boolean ifUnused)
		* 删除交换机
		ifUnused
			* 如果 isUnused 设置为 true 则只有在此交换器没有被使用的情况下才会被删除 
			* 如果设置 false 则无论如何这个交换器都要被删除

	public abstract GetResponse basicGet(String queue, boolean autoAck)

	public abstract void basicAck(long deliveryTag, boolean multiple)
		* 消息确认
		
		multiple
			* true 表示确认deliveryTag这个编号及其以前所有未被消费者确认的消息

	public abstract void basicNack(long deliveryTag, boolean multiple, boolean requeue)
		* 消息拒绝，跟basicReject本质上一样
		* 它多提供了一个参数，可以一次性拒绝一批

		multiple
			* true 表示拒绝deliveryTag这个编号及其以前所有未被消费者确认的消息

		

	public abstract Connection getConnection()

	public abstract DeclareOk exchangeDeclare(String exchange, String type)
	public abstract DeclareOk exchangeDeclare(String exchange, String type, boolean durable, boolean autoDelete, Map<String, Object> arguments)
	public abstract DeclareOk exchangeDeclare(String exchange, BuiltinExchangeType type, boolean durable, boolean autoDelete, Map<String, Object> arguments)
	public abstract DeclareOk exchangeDeclare(String exchange, String type, boolean durable, boolean autoDelete, boolean internal, Map<String, Object> arguments)
	public abstract DeclareOk exchangeDeclare(String exchange, BuiltinExchangeType type, boolean durable, boolean autoDelete, boolean internal, Map<String, Object> arguments)
	public abstract DeclareOk exchangeDeclare(String exchange, BuiltinExchangeType type)
	public abstract DeclareOk exchangeDeclare(String exchange, String type, boolean durable)
	public abstract DeclareOk exchangeDeclare(String exchange, BuiltinExchangeType type, boolean durable)
		* 声明交换机
		* 相同名称相同属性可以多次重复声明，如果出现相同名称，不同属性则异常

		exchange
			* 交换机名称
		type
			* 交换机类型，可以是字符串或者枚举
				DIRECT("direct"), FANOUT("fanout"), TOPIC("topic"), HEADERS("headers");
		durable
			* 是否持久化
		autoDelete
			* 是否自动删除

			* 设置为 true 则表示自动删除，首先必须的前提是有一个队列/交换机跟这个交换机进行过绑定
			* 之后所有的队列/交换机都和这个交换机进行了解绑,再没有任何绑定后，就会被删除
			* 不能理解为: "客户端连接断开就会删除"

		internal
			* 是否是内部的，客户端不能直接发布消息到这个交换机，能通过交换器路由到交换器这种方式。

		arguments
			* 参数

	public abstract void basicCancel(String consumerTag)
		* 取消指定的消费者订阅
		
	public abstract Command rpc(Method arg0)
	public abstract void asyncRpc(Method arg0)

	public abstract void basicReject(long deliveryTag, boolean requeue)
		* 拒绝消息
		* 它一次只能拒绝一条消息
	
		requeue 
			* true 则会重写放入队列，可以重写消费
			* false 则会删除这条消息，不再消费

		
	public abstract long consumerCount(String arg0)

	public abstract boolean waitForConfirms()
	public abstract boolean waitForConfirms(long timeout)
		* 阻塞，知道消息确认，可以指定超时时间
		* 返回bool表示是否发送成功

	public abstract long messageCount(String arg0)

	public abstract SelectOk txSelect()
	public abstract RollbackOk txRollback()
	public abstract CommitOk txCommit()
		* 事务方法

	public abstract String basicConsume(String queue, boolean autoAck, String consumerTag, DeliverCallback deliverCallback, CancelCallback cancelCallback, ConsumerShutdownSignalCallback shutdownSignalCallback)
	public abstract String basicConsume(String queue, boolean autoAck, String consumerTag, boolean noLocal, boolean exclusive, Map arguments, Consumer callback)
	public abstract String basicConsume(String queue, boolean autoAck, String consumerTag, boolean noLocal, boolean exclusive, Map arguments, DeliverCallback deliverCallback, CancelCallback cancelCallback)
	public abstract String basicConsume(String queue, boolean autoAck, String consumerTag, boolean noLocal, boolean exclusive, Map arguments, DeliverCallback deliverCallback, ConsumerShutdownSignalCallback shutdownSignalCallback)
	public abstract String basicConsume(String queue, boolean autoAck, String consumerTag, boolean noLocal, boolean exclusive, Map arguments, DeliverCallback deliverCallback, CancelCallback cancelCallback, ConsumerShutdownSignalCallback shutdownSignalCallback)
	public abstract String basicConsume(String queue, boolean autoAck, DeliverCallback deliverCallback, ConsumerShutdownSignalCallback shutdownSignalCallback)
	public abstract String basicConsume(String queue, boolean autoAck, DeliverCallback deliverCallback, CancelCallback cancelCallback)
	public abstract String basicConsume(String queue, Consumer callback)
	public abstract String basicConsume(String queue, boolean autoAck, DeliverCallback deliverCallback, CancelCallback cancelCallback, ConsumerShutdownSignalCallback shutdownSignalCallback)
	public abstract String basicConsume(String queue, boolean autoAck, Map arguments, Consumer callback)
	public abstract String basicConsume(String queue, DeliverCallback deliverCallback, CancelCallback cancelCallback)
	public abstract String basicConsume(String queue, DeliverCallback deliverCallback, ConsumerShutdownSignalCallback shutdownSignalCallback)
	public abstract String basicConsume(String queue, DeliverCallback deliverCallback, CancelCallback cancelCallback, ConsumerShutdownSignalCallback shutdownSignalCallback)
	public abstract String basicConsume(String queue, boolean autoAck, Consumer callback)
	public abstract String basicConsume(String queue, boolean autoAck, String consumerTag, DeliverCallback deliverCallback, ConsumerShutdownSignalCallback shutdownSignalCallback)
	public abstract String basicConsume(String queue, boolean autoAck, Map arguments, DeliverCallback deliverCallback, CancelCallback cancelCallback, ConsumerShutdownSignalCallback shutdownSignalCallback)
	public abstract String basicConsume(String queue, boolean autoAck, String consumerTag, Consumer callback)
	public abstract String basicConsume(String queue, boolean autoAck, String consumerTag, DeliverCallback deliverCallback, CancelCallback cancelCallback)
	public abstract String basicConsume(String queue, boolean autoAck, Map arguments, DeliverCallback deliverCallback, ConsumerShutdownSignalCallback shutdownSignalCallback)
	public abstract String basicConsume(String queue, boolean autoAck, Map arguments, DeliverCallback deliverCallback, CancelCallback cancelCallback)
		* push消费模式

		queue
			* 队列名称

		autoAck
			* 是否自动ACK
		
		callback
*			* 消费接口实现，可以用 DefaultConsumer，复写需要的方法

		consumerTag
			* 消费者标签，用来区分不同的消费者对象

		noLocal
			* 设置为true，则表示不能把同一个Connection中生产者的消息发送给这个Connection中的消费者
			* 生产者消费者必选是来自不同的 Connection 

		exclusive
			* 是否排他

		arguments
			* 消费者参数

		deliverCallback
		cancelCallback
		shutdownSignalCallback
		

		
	public abstract RecoverOk basicRecover(boolean requeue)
	public abstract RecoverOk basicRecover()
		

	public abstract SelectOk confirmSelect()
		* 开启发布者消息确认模式

	public abstract void queueDeleteNoWait(String arg0, boolean arg1, boolean arg2)
	public abstract CompletableFuture asyncCompletableRpc(Method arg0)

	public abstract void waitForConfirmsOrDie(long timeout)
	public abstract void waitForConfirmsOrDie()
		* 等待消息确认，会阻塞，如果失败抛出异常
		* 可以设置超时时间

	public abstract long getNextPublishSeqNo()
