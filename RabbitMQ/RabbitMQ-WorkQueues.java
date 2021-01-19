----------------------------
RabbitMQ-Work queues		|
----------------------------
	# Work 模式
	# 一个生产者生存消息,N个消费者消费消息
						-> C1
		P	-> [][][]
						-> C2
			p	:消息生产者
			[]	:队列
			C	:消息消费者
	
	# '只能有一个消费者获取到消息'
		* 怎么办? ----  抢,真抢

	# 代码测试总结
		* 消费者1和消费者2获取到的消息内容是不同的,也就是说一条消息只能被一个消费者所消费
		* 消费者1和消费者2获取到的消息数目是一样的.根据发送的内容(数字递增),发现一个是消费了奇数,一个消费了偶数
		* 这里的消费者1,每10毫秒可以消费一个消息,消费者2每1秒才能处理一个消息.但是他们处理的消息数量是一样的.这不公平
		* 开启能者多劳,也就是说.谁消费快,谁就消费多,在消费者中开启:   channel.basicQos(1);

	
	
	# 总结
		1,一个生产者对N个消费者
		2,最关键的一句代码(消费者中)
			channel.basicQos(1);
			* 添加这行代码,则能者多劳
			* 不添加/注释.则雨露匀沾
		

			
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
	private final static String QUEUE_NAME = "test_queue_work";
    public static void main(String[] argv) throws Exception {
        // 获取到连接以及mq通道
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        // 声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        for (int i = 0; i < 100; i++) {
            // 消息内容
            String message = "" + i;
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
            Thread.sleep(i * 10);
        }
        channel.close();
        connection.close();
    }
	/**
		消费者1
	*/
	private final static String QUEUE_NAME = "test_queue_work";
    public static void main(String[] argv) throws Exception {
        // 获取到连接以及mq通道
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        // 声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        /*
			同一时刻服务器只会发一条消息给消费者
			也就说同一时刻,MQ服务器只会给当前消费者一条消息,你消费完了,我在给
			也就是消费快的人,消费多,慢得就消费少
		*/
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
            //休眠10毫秒
            Thread.sleep(10);
            // 返回确认状态
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        }
    }
	/**
		消费者n
	*/
	private final static String QUEUE_NAME = "test_queue_work";
    public static void main(String[] argv) throws Exception {
        // 获取到连接以及mq通道
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        // 声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        /*
			同一时刻服务器只会发一条消息给消费者
			也就说同一时刻,MQ服务器只会给当前消费者一条消息,你消费完了,我在给
			也就是消费快的人,消费多,慢得就消费少
		*/ 
        channel.basicQos(1);
        // 定义队列的消费者
        QueueingConsumer consumer = new QueueingConsumer(channel);
        // 监听队列，手动返回完成状态
        channel.basicConsume(QUEUE_NAME, false, consumer);
        // 获取消息
        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            System.out.println(" [x] Received '" + message + "'");
            // 休眠1秒
            Thread.sleep(1000);
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        }
    }