----------------------------
生产者						|
----------------------------
	public static void main(String[] args) throws Exception {
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnectionFactory.DEFAULT_USER,ActiveMQConnectionFactory.DEFAULT_PASSWORD,"tcp://123.207.122.145:61616");
		Connection connection = connectionFactory.createConnection();
		connection.start();
		Session session = connection.createSession(Boolean.TRUE, Session.CLIENT_ACKNOWLEDGE);
		Destination destination = session.createQueue("queue#1");
		MessageProducer producer = session.createProducer(destination);
		// 消息头设置 - 消息持久化
		producer.setDeliveryMode(DeliveryMode.PERSISTENT);		
		TextMessage message = new ActiveMQTextMessage();
		// 消息属性设置 
		message.setStringProperty("name", "KevinBlandy");
		//设置正文
		message.setText("Hello World");
		//执行发送
		producer.send(message);
		session.commit();
		connection.close();
	}


----------------------------
消费者						|
----------------------------
	public static void main(String[] args) throws Exception {
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnectionFactory.DEFAULT_USER,ActiveMQConnectionFactory.DEFAULT_PASSWORD,"tcp://123.207.122.145:61616");
		Connection connection = connectionFactory.createConnection();
		connection.start();
		Session session = connection.createSession(Boolean.FALSE, Session.CLIENT_ACKNOWLEDGE);
		Destination destination = session.createQueue("queue#1");
		MessageConsumer messageConsumer = session.createConsumer(destination);
		//messageConsumer.setMessageListener(new Listener());		//监听模式
		while(true){
			ActiveMQTextMessage message =  (ActiveMQTextMessage) messageConsumer.receive();
			System.out.println("收到消息:"+ message.getText());
			message.acknowledge(); //签收消息
			System.out.println(message.getStringProperty("name"));
		}
	}