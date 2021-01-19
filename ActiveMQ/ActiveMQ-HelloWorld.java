package com.kevin.demo.activemq.helloworld;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnectionFactory;
/**
 * 消息生产者
 * */
public class Provider {
	public static void main(String[] args) throws JMSException {
		/**
		 * 1,创建连接工场.使用JMS规范
		 * 	用户名和密码使用默认,连接方式为TCP
		 * */
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
												ActiveMQConnectionFactory.DEFAULT_USER,
												ActiveMQConnectionFactory.DEFAULT_PASSWORD,
												"tcp://123.207.122.145:61616");
		/***
		 * 2,通过工厂获取一个连接,并且打开它(在创建连接的时候,也是可以设置用户名和密码)
		 * 	默认是关闭状态
		 * */
		Connection connection = connectionFactory.createConnection();
		connection.start();
		/**
		 * 3,通过连接,获取会话
		 * 	参数1,是否启用事务(不启用)
		 * 	参数2,消息签收模式(消费者手动签收)
		 * */
		Session session = connection.createSession(Boolean.TRUE,Session.CLIENT_ACKNOWLEDGE);
		/**
		 * 4,通过会话创建 Destination 对象
		 * 	Destination ,用来描述 消费者 和 消息来源的对象.其实是一个高层的抽象
		 *  	在PTP模式中,Destination 被称为:Queue,也就是队列
		 * 		在Pub/Sub模式中,Destination 被称为:Topic,也就是主题
		 * */
		Destination destination = session.createQueue("queue#1");		//PTP模式,创建的是队列,并且指定队列名称
		/**
		 * 5,通过会话创建 消息生产者 MessageProducer
		 * 	构造传入 Destination 描述对象
		 *	这里的构造,也可以指定为 null,在  messageProducer 执行 send 发送消息的时候,才指定 Destination
		 * */
		MessageProducer messageProducer = session.createProducer(destination);
		/**
		 * 6,设置 消息生产者的一些属性
		 * 	也可以在执行 send 的时候,去指定属性
		 * */
		messageProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);		//设置为非持久化
		messageProducer.setPriority(9); 							//设置消息优先级别
		/**
		 * 7,最后,通过会话,使用JMS规范中的 TextMessage 形式创建数据,并使用 消息生产者MessageProducer 来发送消息
		 * */
		for(int x = 0;x < 5;x++){
			TextMessage message = session.createTextMessage("消息签收模式-客户端签收消息#" + x);			//直接指定消息内容
			//message.setText("hello");		也可以创建后添加消息
			messageProducer.send(message);
			//N多构造,可以在发送的时候,配置一些属性
			//messageProducer.send(destination, message, DeliveryMode.NON_PERSISTENT, Priority.DEBUG_INT, 9999999);
		}
		session.commit();
		/**
		 * 8,关闭资源
		 * */
		if(connection != null){
			/**
			 * 仅需要关闭此连接,它会自己去关闭其他的有关连接
			 * */
			connection.close();
		}
	}
}



-----------------------------------------------------------------------------------------------
package com.kevin.demo.activemq.helloworld;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnectionFactory;
/**
 * 消息消费者
 * */
public class Consumer {
	public static void main(String[] args) throws JMSException {
		/**
		 * 1,建立ConnectionFactory工厂
		 * 	使用默认的用户名和密码,连接方式为TCP
		 * */
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
												ActiveMQConnectionFactory.DEFAULT_USER,
												ActiveMQConnectionFactory.DEFAULT_PASSWORD,
												"tcp://123.207.122.145:61616");
		/**
		 * 2,通过ConnectionFactory工厂,获取到连接 Connection,并且打开连接(默认为关闭状态)
		 * */
		Connection connection = connectionFactory.createConnection();
		connection.start();
		/**
		 * 3,通过连接,创建会话,Session
		 * 	不启用事务
		 * 	消息签收模式为 消费者手动签收
		 * */
		Session session = connection.createSession(Boolean.FALSE,Session.CLIENT_ACKNOWLEDGE);
		/**
		 * 4,通过 会话 创建 Destination 对象
		 * 	Destination ,用来描述 消费者 和 消息来源的对象.其实是一个高层的抽象
		 *  	在PTP模式中,Destination 被称为:Queue,也就是队列
		 * 		在Pub/Sub模式中,Destination 被称为:Topic,也就是主题
		 * */
		Destination destination = session.createQueue("queue#1");	//指定队列的名称
		/**
		 * 5,通过Session创建消息接收对象
		 * 	构造传入 Destination 描述对象
		 * */
		MessageConsumer messageConsumer = session.createConsumer(destination);
		/**
		 * 6,使用接收对象(MessageConsumer),的 receive 来创建 ,JMS规范的 TextMessage 对象,
		 * */
		while(true){
			/**
			 * receive(),这个方法有很多重载形式
			 * 	1,空参,阻塞,不多解释.BIO那种节奏
			 * 	2,Long,阻塞时间,如果超时.就会往下执行
			 * 	3,NoWait(),程序执行到这里检查是否有数据,有数据就消费.不然直接往下执行.不会等待,更不会阻塞
			 * */
			TextMessage message = (TextMessage) messageConsumer.receive();
			if(message == null){
				break;
			}
			message.acknowledge(); 		//手动签收消息,其实就是另起线程.TCP通知MQ,这个消息我成功消费了
			System.out.println(message.toString());
			System.err.println("收到消息:" + message.getText());
		}
		/**
		 *7, 关闭资源
		 * */
		if(connection != null){
			connection.close();
		} 
	}
}
