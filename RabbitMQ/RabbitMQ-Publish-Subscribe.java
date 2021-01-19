----------------------------
RabbitMQ-Publish / Subscribe|
----------------------------
	# 订阅模式
	# 一个消息生产者,N个消息消费者
					 [][][]	-> C1
		P - > X - > 
					 [][][]	-> Cn
			p	:消息生产者
			X	:Exchanges(交换)
			[]	:队列
			C	:消息消费者
		
		* 这种模式并不是直接把消息发送到队列,而是发送到交换机
		* 每个消费者都有一个自己的队列,每个队列都绑定到交换机
		* 生产者发送的消息,经过交换机.到达队列,实现'一个消息被多个消费者消费'的目的
	
	# '如果把消息发送到,未绑定任何的队列的交换机时,那么该消息将会丢失'
		* 交换机并没有存储消息的能力,消息只能存储于队列
	



	/**
		工具类
	*/
	public static Connection getConnection() throws Exception {
        //定义连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //设置服务地址
        factory.setHost("localhost");
        //端口
        factory.setPort(5672);
        //设置账号信息，用户名、密码、vhost
        factory.setVirtualHost("/kevinblandy");
        factory.setUsername("kevin");
        factory.setPassword("a12551255");
        // 通过工程获取连接
        Connection connection = factory.newConnection();
        return connection;
    }
	
	/**
		消息生产者
	*/
	private final static String EXCHANGE_NAME = "test_exchange_fanout";		//交换机名称
    public static void main(String[] argv) throws Exception {
        // 获取到连接以及mq通道
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        /*
			声明exchange,名称自定义
		*/ 
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        // 消息内容
        String message = "Hello World!";
        channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");
        channel.close();
        connection.close();
    }
	/**
		消费者1
	*/
	private final static String QUEUE_NAME = "test_queue_work";				//队列名称
    private final static String EXCHANGE_NAME = "test_exchange_fanout";		//交换机名称
    public static void main(String[] argv) throws Exception {
        // 获取到连接以及mq通道
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        // 声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        // 绑定队列到交换机
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "");
        // 同一时刻服务器只会发一条消息给消费者
        channel.basicQos(1);
        // 定义队列的消费者
        QueueingConsumer consumer = new QueueingConsumer(channel);
        // 监听队列，手动返回完成
        channel.basicConsume(QUEUE_NAME, false, consumer);
        // 获取消息
        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            System.out.println(" [x] Received '" + message + "'");
            Thread.sleep(10);
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        }
    }
	/**
		消费者N
	*/
	private final static String QUEUE_NAME = "test_queue_work2";				//队列名称
    private final static String EXCHANGE_NAME = "test_exchange_fanout";			//交换机名称
    public static void main(String[] argv) throws Exception {
        // 获取到连接以及mq通道
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        // 声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        // 绑定队列到交换机
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "");
        // 同一时刻服务器只会发一条消息给消费者
        channel.basicQos(1);
        // 定义队列的消费者
        QueueingConsumer consumer = new QueueingConsumer(channel);
        // 监听队列，手动返回完成
        channel.basicConsume(QUEUE_NAME, false, consumer);
        // 获取消息
        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            System.out.println(" [x] Received '" + message + "'");
            Thread.sleep(10);

            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        }
    }
