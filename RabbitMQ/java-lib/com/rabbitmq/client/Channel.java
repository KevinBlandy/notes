---------------------------------------
Channel
---------------------------------------
	# interface Channel extends ShutdownNotifier, AutoCloseable

	# Channel �����̰߳�ȫ�ģ�ע�ⲻҪ����
		* һ���߳�һ��Channel���

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
		* �ж�ָ����quque�Ƿ���ڣ���������ڵĻ������׳��쳣�����ҹر�Channel

	public abstract void clearConfirmListeners()
	public abstract void addConfirmListener(ConfirmListener arg0)
	public abstract ConfirmListener addConfirmListener(ConfirmCallback arg0, ConfirmCallback arg1)
	public abstract void setDefaultConsumer(Consumer arg0)

	public abstract void addReturnListener(ReturnListener listener)
	public abstract ReturnListener addReturnListener(ReturnCallback returnCallback)
		* ����Ϣû�гɹ�Ͷ�ݺ󣬻ᷴ���������ߣ��ͻ�����������
			void handleReturn(int replyCode,
				String replyText,
				String exchange,
				String routingKey,
				AMQP.BasicProperties properties,
				byte[] body) throws IOException;
			
		
	public abstract Consumer getDefaultConsumer()

	public abstract void exchangeDeclareNoWait(String exchange, String type, boolean durable, boolean autoDelete, boolean internal, Map arguments)
	public abstract void exchangeDeclareNoWait(String exchange, BuiltinExchangeType type, boolean durable, boolean autoDelete, boolean internal, Map arguments)
		* �� exchangeDeclare һ����������ʹ��
		* �����������Ҫ���������أ�ע����������ķ���ֵ��void ������ͨ��exchangeDeclare �����ķ���ֵ��Exchange.DeclareOk��
		* ��˼���ڿͻ���������һ��������֮����Ҫ�ȴ��������ķ���(�������᷵�� Exchange.Declare-Ok ��� AMQP ����)��

	public abstract void clearReturnListeners()

	public abstract void queueDeclareNoWait(String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments)
		* �� queueDeclare һ����������ʹ��
		* ���Ҳ�ǲ���Ҫ����

	public abstract boolean removeConfirmListener(ConfirmListener arg0)

	public abstract DeclareOk exchangeDeclarePassive(String exchange)
		* ����Ҫ���������Ӧ�Ľ������Ƿ���ڡ������������������:������������׳��쳣 : 404 channel exception
		* ͬʱ Channel Ҳ�ᱻ�ر�
		
	public abstract void exchangeDeleteNoWait(String exchange, boolean ifUnused)
		* �� exchangeDelete һ����ɾ������������������

	public abstract void exchangeUnbindNoWait(String arg0, String arg1, String arg2, Map arg3)
	public abstract boolean removeReturnListener(ReturnListener arg0)

	public abstract DeclareOk queueDeclare(String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments)
	public abstract DeclareOk queueDeclare()
		* ��������
		* ��ͬ������ͬ���Կ��Զ���ظ����������������ͬ���ƣ���ͬ�������쳣

		* �����ߺ������߶�����ִ������
		* ��������������Ѿ������Channel�϶�����������quque���Ͳ����ظ������ˣ���ѡ��ȡ�����ģ���Channel����Ϊ�����䡱ģʽ���ٴ�ִ������

		queue
			* ��������
		durable
			* �Ƿ�־û�
		exclusive
			* �Ƿ�����Ϊ��������

			* Ϊ true �����ö���Ϊ�����ġ����һ�����б�����Ϊ�������У��ö��н����״��������� Connection �ɼ����������ӶϿ�ʱ�Զ�ɾ����
			* ���������ǻ��� Connection �ģ�Ҳ����˵ͬһ�� Connection �µ� Channel ���ɼ�����Щ Channel ����ͬʱ�����������
			* ��ǰ Connection �Ѿ���������һ�����У����� Connection �ٴγ�������ͬ�����У��ͻ��쳣
			* ������о����������˳־û�(durable=true), һ�����ӹرջ��߿ͻ����˳�������������ж����Զ�ɾ��
			* ���ֶ���ֻ�ʺ�ͬһ���ͻ��˼�д�ֶ��ĳ���

		autoDelete
			* �Ƿ��Զ�ɾ��
			
			* �Զ�ɾ����ǰ���ǣ�������֮��������һ�������߽��й�����(�������û���������ӹ����򲻻�ɾ��)
			* ֮�����������߶��Ͽ������ӣ��Ż��Զ�ɾ��

		arguments
			* ����
		
	public abstract DeleteOk queueDelete(String queue)
	public abstract DeleteOk queueDelete(String queue, boolean ifUnused, boolean ifEmpty)
		* ɾ������
		
		ifUnused
			* ����Ϊ true ��ʾ�������û�������ߵ�����²�ɾ��
			* ����Ϊ false ��ʾǿ��ɾ��

		ifEmpty 
			* ����Ϊ true ��ʾ�ڶ���Ϊ��(��������û���κ���Ϣ�ѻ�)������²��ܹ�ɾ����

	public abstract void basicQos(int prefetchCount, boolean global)
	public abstract void basicQos(int prefetchSize, int prefetchCount, boolean global)
	public abstract void basicQos(int prefetchCount)
		* һ����������δ��ACK��Ϣ�ĸ���
		* ������ȥģʽ������Ч

		* MQ���ÿ�������߶�����һ��: �������ַ���Ϣ����������ߵ�ʱ�� ���� +1��������ȷ�����Ѻ� ���� -1 
		* ����������ˣ���ô�ͻ�ֹͣ�����ɷ���Ϣ��������TCP/IP�еĻ�������

		prefetchCount
			* һ����������δ��ACK��Ϣ�ĸ���
			* ����Ϊ0��ʾ������
		
		prefetchSize
			* �����߿��Խ��ܵ�δACK����Ϣ�������С����λ��KB
			* ����Ϊ0��ʾ������
		
		global
			* ��������Ϊ false
			* ʹ������ global ��ģʽ��������� RabbitMQ�ĸ��أ���ΪRabbitMQ ��Ҫ�������Դ��Э�������Щ����
		

	public abstract BindOk exchangeBind(String destination, String source, String routingKey)
	public abstract BindOk exchangeBind(String destination, String source, String routingKey, Map<String, Object> arguments)
	public abstract void exchangeBindNoWait(String destination, String source, String routingKey, Map<String, Object> arguments)
		* �󶨽�������������
		* ��Ϣ�� source ת���� destination

		destination
			* Ŀ�꽻����
		source
			* Դ������
		routingKey
			* ·��KEY
		arguments
		

	public abstract UnbindOk exchangeUnbind(String arg0, String arg1, String arg2, Map arg3)
	public abstract UnbindOk exchangeUnbind(String arg0, String arg1, String arg2)

	public abstract BindOk queueBind(String queue, String exchange, String routingKey, Map<String, Object> arguments)
	public abstract BindOk queueBind(String queue, String exchange, String routingKey)
		* �󶨶��е�������
		
		queue
			* ��������
		exchange
			* ����������
		routingKey
			* ·��KEY


	public abstract void queueBindNoWait(String arg0, String arg1, String arg2, Map arg3)
		* �� queueBind һ����ֻ�ǲ�������������ʹ��

	public abstract UnbindOk queueUnbind(String queue, String exchange, String routingKey, Map<String, Object> arguments)
	public abstract UnbindOk queueUnbind(String queue, String exchange, String routingKey)
		* ȡ�����кͽ������İ�

		queue
			* ��������
		exchange
			* ����������
		routingKey
			* ·��KEY

	public abstract PurgeOk queuePurge(String queue)
		* ��ն������ݣ�������ɾ������
			
	public abstract int getChannelNumber()

	public abstract void basicPublish(String exchange, String routingKey, boolean mandatory, boolean immediate, BasicProperties props, byte[] body)
	public abstract void basicPublish(String exchange, String routingKey, BasicProperties props, byte[] body) 
	public abstract void basicPublish(String exchange, String routingKey, boolean mandatory, BasicProperties props, byte[] body)
		* ��������Ϣ����
		exchange
			* ������������Ϊnull����������Ϊ: ""����ʾ���͵�Ĭ�ϵĽ�������
		
		routingKey
			* ·��key
		
		mandatory
			* true �����Ϣû���ҵ����ʵĶ��У���ͨ�� Basic.Return �������ظ�������
			* false �����Ϣû���ҵ����ʵĶ��У���ֱ��ɾ��

		immediate
			* true ��Ϣ�ɹ������˶��У�������·��key��ƥ������ж���û���κ������ߣ���ͨ�� Basic.Return �������ظ�������

			* RabbitMQ 3 .0 �汾��ʼȥ���˶� imrnediate ������֧�֣��Դ� RabbitMQ �ٷ������� :
				imrnediate ������Ӱ�쾵����е����ܣ������˴��븴���ԣ�������� TTL �� DLX �ķ�������
			* ��������� true, ����ڿͻ��˸������棬�����ڷ���˸����쳣

		props
		body
		

	public abstract DeleteOk exchangeDelete(String exchange)
	public abstract DeleteOk exchangeDelete(String exchange, boolean ifUnused)
		* ɾ��������
		ifUnused
			* ��� isUnused ����Ϊ true ��ֻ���ڴ˽�����û�б�ʹ�õ�����²Żᱻɾ�� 
			* ������� false ��������������������Ҫ��ɾ��

	public abstract GetResponse basicGet(String queue, boolean autoAck)

	public abstract void basicAck(long deliveryTag, boolean multiple)
		* ��Ϣȷ��
		
		multiple
			* true ��ʾȷ��deliveryTag�����ż�����ǰ����δ��������ȷ�ϵ���Ϣ

	public abstract void basicNack(long deliveryTag, boolean multiple, boolean requeue)
		* ��Ϣ�ܾ�����basicReject������һ��
		* �����ṩ��һ������������һ���Ծܾ�һ��

		multiple
			* true ��ʾ�ܾ�deliveryTag�����ż�����ǰ����δ��������ȷ�ϵ���Ϣ

		

	public abstract Connection getConnection()

	public abstract DeclareOk exchangeDeclare(String exchange, String type)
	public abstract DeclareOk exchangeDeclare(String exchange, String type, boolean durable, boolean autoDelete, Map<String, Object> arguments)
	public abstract DeclareOk exchangeDeclare(String exchange, BuiltinExchangeType type, boolean durable, boolean autoDelete, Map<String, Object> arguments)
	public abstract DeclareOk exchangeDeclare(String exchange, String type, boolean durable, boolean autoDelete, boolean internal, Map<String, Object> arguments)
	public abstract DeclareOk exchangeDeclare(String exchange, BuiltinExchangeType type, boolean durable, boolean autoDelete, boolean internal, Map<String, Object> arguments)
	public abstract DeclareOk exchangeDeclare(String exchange, BuiltinExchangeType type)
	public abstract DeclareOk exchangeDeclare(String exchange, String type, boolean durable)
	public abstract DeclareOk exchangeDeclare(String exchange, BuiltinExchangeType type, boolean durable)
		* ����������
		* ��ͬ������ͬ���Կ��Զ���ظ����������������ͬ���ƣ���ͬ�������쳣

		exchange
			* ����������
		type
			* ���������ͣ��������ַ�������ö��
				DIRECT("direct"), FANOUT("fanout"), TOPIC("topic"), HEADERS("headers");
		durable
			* �Ƿ�־û�
		autoDelete
			* �Ƿ��Զ�ɾ��

			* ����Ϊ true ���ʾ�Զ�ɾ�������ȱ����ǰ������һ������/��������������������й���
			* ֮�����еĶ���/������������������������˽��,��û���κΰ󶨺󣬾ͻᱻɾ��
			* �������Ϊ: "�ͻ������ӶϿ��ͻ�ɾ��"

		internal
			* �Ƿ����ڲ��ģ��ͻ��˲���ֱ�ӷ�����Ϣ���������������ͨ��������·�ɵ����������ַ�ʽ��

		arguments
			* ����

	public abstract void basicCancel(String consumerTag)
		* ȡ��ָ���������߶���
		
	public abstract Command rpc(Method arg0)
	public abstract void asyncRpc(Method arg0)

	public abstract void basicReject(long deliveryTag, boolean requeue)
		* �ܾ���Ϣ
		* ��һ��ֻ�ܾܾ�һ����Ϣ
	
		requeue 
			* true �����д������У�������д����
			* false ���ɾ��������Ϣ����������

		
	public abstract long consumerCount(String arg0)

	public abstract boolean waitForConfirms()
	public abstract boolean waitForConfirms(long timeout)
		* ������֪����Ϣȷ�ϣ�����ָ����ʱʱ��
		* ����bool��ʾ�Ƿ��ͳɹ�

	public abstract long messageCount(String arg0)

	public abstract SelectOk txSelect()
	public abstract RollbackOk txRollback()
	public abstract CommitOk txCommit()
		* ���񷽷�

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
		* push����ģʽ

		queue
			* ��������

		autoAck
			* �Ƿ��Զ�ACK
		
		callback
*			* ���ѽӿ�ʵ�֣������� DefaultConsumer����д��Ҫ�ķ���

		consumerTag
			* �����߱�ǩ���������ֲ�ͬ�������߶���

		noLocal
			* ����Ϊtrue�����ʾ���ܰ�ͬһ��Connection�������ߵ���Ϣ���͸����Connection�е�������
			* �����������߱�ѡ�����Բ�ͬ�� Connection 

		exclusive
			* �Ƿ�����

		arguments
			* �����߲���

		deliverCallback
		cancelCallback
		shutdownSignalCallback
		

		
	public abstract RecoverOk basicRecover(boolean requeue)
	public abstract RecoverOk basicRecover()
		

	public abstract SelectOk confirmSelect()
		* ������������Ϣȷ��ģʽ

	public abstract void queueDeleteNoWait(String arg0, boolean arg1, boolean arg2)
	public abstract CompletableFuture asyncCompletableRpc(Method arg0)

	public abstract void waitForConfirmsOrDie(long timeout)
	public abstract void waitForConfirmsOrDie()
		* �ȴ���Ϣȷ�ϣ������������ʧ���׳��쳣
		* �������ó�ʱʱ��

	public abstract long getNextPublishSeqNo()
