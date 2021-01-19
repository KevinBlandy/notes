----------------------------
RabbitMQ-HelloWord			|
----------------------------
	# 简单的模式:消息生产者生存消息,消息消费者消费消息
	# 结构
		P -- > [][][]  -- > C
			p	:消息生产者
			[]	:队列
			C	:消息消费者
	
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
		发送端
	*/
    private final static String QUEUE_NAME = "test_queue";			//队列名称
    public static void main(String[] argv) throws Exception {
        // 获取到连接以及mq通道
        Connection connection = ConnectionUtil.getConnection();
        // 从连接中创建通道
        Channel channel = connection.createChannel();
        /*
			声明（创建）队列
			消息必须存在于队列
		*/
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        // 消息内容
        String message = "Hello World!";
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");
        //关闭通道和连接
        channel.close();
        connection.close();
    }
	/**
		接收端
	*/
	private final static String QUEUE_NAME = "test_queue";			//队列名称
    public static void main(String[] argv) throws Exception {
        // 获取到连接以及mq通道
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        /*
			声明队列
			对于消费者而已,其实声明队列可以在确定队列存在情况下忽略该操作
			但是,为了保险起见,也可以进行声明操作.不会对任何地方产生任何的影响
		*/
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        // 定义队列的消费者
        QueueingConsumer consumer = new QueueingConsumer(channel);
        // 监听队列
        channel.basicConsume(QUEUE_NAME, true, consumer);
        /*
			获取消息,进行消费
			消费一条少一条,传说中的阅后即焚
		*/
        while (true) {
			/*
				因为监听的缘故,所以这行代码,会一直阻塞.直到收到消息
			*/
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            System.out.println(" [x] Received '" + message + "'");
        }
    }