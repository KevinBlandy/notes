---------------
生产者
---------------

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP.Queue.DeclareOk;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Producer {
	
	private static final String QUEUE_NAME = "simple_quque";
	
	public static void main(String[] args) throws IOException, TimeoutException {
		
		// 创建连接工厂
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost("127.0.0.1");
		connectionFactory.setUsername("admin");
		connectionFactory.setPassword("123456");
		
		// 获取连接
		try (Connection connection = connectionFactory.newConnection()){
			
			// 创建Channel
			try(Channel channel =  connection.createChannel()){
				
				// 声明队列
				DeclareOk declareOk = channel.queueDeclare(QUEUE_NAME, false, false, false, null);
				System.out.println(declareOk);
				
				String message = "我是消息!";
				
				channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
			}
		}
	}
}



---------------
消费者
---------------

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Consumer {
	
	private static final String QUEUE_NAME = "simple_quque";
	
	public static void main(String[] args) throws IOException, TimeoutException {
		
		// 创建连接工厂
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost("127.0.0.1");
		connectionFactory.setUsername("admin");
		connectionFactory.setPassword("123456");
		
		// 由于使用了 try (), 会自动的在执行完毕后释放资源关闭Channel, Connection
		// 所以启动的时候，如果没有读取到消息，就会自动关闭，并且退出
		// 可以取消 try()，那么就会阻塞直到消费到数据
		// 当然，也可以用while (true){}

		// 获取连接
		try (Connection connection = connectionFactory.newConnection()){  
			
			// 创建Channel
			try(Channel channel =  connection.createChannel()){
				
				channel.basicConsume(QUEUE_NAME, true,(consumerTag, message) -> {
					byte[] body = message.getBody();
					System.out.println("收到消息:" + new String(body, StandardCharsets.UTF_8));
				}, consumerTag -> {
					System.out.println("消息被取消:" + consumerTag);
				});
				
			}
		}
	}
}
