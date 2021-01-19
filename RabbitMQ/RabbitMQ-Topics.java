----------------------------
RabbitMQ-Topics				|
----------------------------
	# 通配模式
	# 其实跟路由模式(Routing)一个德行
	# 区别就是
		1,Routing是消息的key和队列的key必须完全一致
		2,这里是只需要符合匹配的规则
	
	# 匹配规则
		* 通配符有两种
			1,#
				一个或者多个词儿
			2,*
				只能是一个词儿
	
	
	# 图解
			  -	error.* ->	
			 |-	info.#  ->	[][][]	C1
		P -> X	
			 |- *.error ->
			 |- #.info  ->	[][][]	Cn
			  - #warn*  ->
		
			p	:消息生产者
			X	:交换机(类型是:direct(路由))
			C1	:消费者1
			C2	:消费者N
	/**
		工具
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
		生产者
	*/
	private final static String EXCHANGE_NAME = "test_exchange_topic";
    public static void main(String[] argv) throws Exception {
        // 获取到连接以及mq通道
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        // 声明exchange(交换机)
        channel.exchangeDeclare(EXCHANGE_NAME, "topic");
        // 消息内容
        String message = "Hello World!";
		/**
			绑定消息 key = key.1
		*/
        channel.basicPublish(EXCHANGE_NAME, "key.1", null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");
        channel.close();
        connection.close();
    }
	/**
		消费者1
	*/
	private final static String QUEUE_NAME = "test_queue_topic_work";
    private final static String EXCHANGE_NAME = "test_exchange_topic";
    public static void main(String[] argv) throws Exception {
        // 获取到连接以及mq通道
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        // 声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        /*
			绑定队列到交换机
			并且指定通配符  = key.*
		*/ 
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "key.*");
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
	private final static String QUEUE_NAME = "test_queue_topic_work2";
    private final static String EXCHANGE_NAME = "test_exchange_topic";
    public static void main(String[] argv) throws Exception {
        // 获取到连接以及mq通道
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        // 声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        /*
			绑定队列到交换机
			并且指定通配符 = *.*
		*/ 
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "*.*");
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