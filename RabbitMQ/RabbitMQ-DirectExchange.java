----------------------------
RabbitMQ-Routing			|
----------------------------
	# 传说中的路由模式
	
			  -	error ->	
			 |-	info  ->	[][][]	C1
		P -> X	
			 |- error ->
			 |- info  ->	[][][]	Cn
			  - warn  ->
		
			p	:消息生产者
			X	:交换机(类型是:direct(路由))
			C1	:消费者1
			C2	:消费者N
		
		* 生产者把消息发送给交换机(路由类型)
		* 消费者创建N个队列,指定一个'路由key',绑定到交换机
		* 生产者产生消息的时候,就需要指定'路由key'
		* 消息到达交换机的时候,就会根据不同的'路由key',把消息推送到不同的消费者队列.可以推送到N个'相同路由key'的队列
		* 这是一种有选择性的消息接收
		* 传说中的'路由key',其实就是个字符串... ...

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
		生产者
	*/
	private final static String EXCHANGE_NAME = "test_exchange_direct";	//交换机名称
    public static void main(String[] argv) throws Exception {
        // 获取到连接以及mq通道
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        // 声明exchange,类型是 direct
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");
        // 消息内容
        String message = "Hello World!";
		/**
			发送消息到路由.并且指定 路由key = "key2"
		*/
        channel.basicPublish(EXCHANGE_NAME, "key2", null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");

        channel.close();
        connection.close();
    }
	/**
		消费者1
	*/
	private final static String QUEUE_NAME = "test_queue_work_direct1";
    private final static String EXCHANGE_NAME = "test_exchange_direct";
    public static void main(String[] argv) throws Exception {
        // 获取到连接以及mq通道
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        // 声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        /*
			绑定队列到交换机
			并且指定了一个路由key = "update"
		*/
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "update");
		/*
			再绑定一个队列
			并且指定了一个路由key = "delete"
			 channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "delete");
		*/
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
	private final static String QUEUE_NAME = "test_queue_work_direct2";
    private final static String EXCHANGE_NAME = "test_exchange_direct";
    public static void main(String[] argv) throws Exception {
        // 获取到连接以及mq通道
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        // 声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        /*
			绑定队列到交换机
			并且指定了一个路由key = "key2"
		*/
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "key2");
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
		