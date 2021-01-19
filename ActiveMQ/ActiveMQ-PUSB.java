----------------------------
生产者						|
----------------------------
	public static void main(String[] args) throws Exception {
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnectionFactory.DEFAULT_USER,ActiveMQConnectionFactory.DEFAULT_PASSWORD,"tcp://123.207.122.145:61616");
		Connection connection = connectionFactory.createConnection();
		connection.start();
		Session session = connection.createSession(Boolean.TRUE, Session.CLIENT_ACKNOWLEDGE);
		Destination destination = session.createTopic("topic#1");
		MessageProducer messageProducer = session.createProducer(destination);
		//设置 - 消息头
		messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT);
		TextMessage message = new ActiveMQTextMessage();
		message.setText("这是一条广播");
		messageProducer.send(message);
		session.commit();
		connection.close();
	}

----------------------------
订阅者-非持久化订阅			|
----------------------------
	public static void main(String[] args) throws JMSException {
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnectionFactory.DEFAULT_USER,ActiveMQConnectionFactory.DEFAULT_PASSWORD,"tcp://123.207.122.145:61616");
		Connection connection = connectionFactory.createConnection();
		connection.start();
		Session session = connection.createSession(Boolean.FALSE, Session.CLIENT_ACKNOWLEDGE);
		Destination destination = session.createTopic("topic#1");
		MessageConsumer messageConsumer = session.createConsumer(destination);
		while(true){
			ActiveMQTextMessage message =  (ActiveMQTextMessage) messageConsumer.receive();
			System.out.println("非持久订阅者B,收到消息:"+message.getText());
			message.acknowledge();
		}
	}

----------------------------
订阅者-持久化订阅			|
----------------------------
	public static void main(String[] args) throws JMSException {
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnectionFactory.DEFAULT_USER,ActiveMQConnectionFactory.DEFAULT_PASSWORD,"tcp://123.207.122.145:61616");
		Connection connection = connectionFactory.createConnection();
		/** 设置ID值 **/
		connection.setClientID("ID#1");
		connection.start();
		Session session = connection.createSession(Boolean.FALSE, Session.CLIENT_ACKNOWLEDGE);
		/** 订阅到一个topic **/
		Topic topic = session.createTopic("topic#1");
		/** 创建持久订阅 设置订阅名称**/
		TopicSubscriber topicSubscriber = session.createDurableSubscriber(topic, "t1");
		while(true){
			ActiveMQTextMessage message =  (ActiveMQTextMessage) topicSubscriber.receive();
			System.out.println("持久订阅者C,收到消息:" + message.getText());
			message.acknowledge();
		}
	}