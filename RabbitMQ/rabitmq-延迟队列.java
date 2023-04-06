-----------------------
延迟队列实现
-----------------------
	# 使用同一个交换机，不同的 route_key 区分不同队列即可
	# 一个交换机，具备多个不同ttl的延时队列
	# 消费队列只有一个
	# 路由图
									[queue (ttl队列, 没有消费者, 设置死信交换机, 死信key)]
		[message] -> [exchange] -> 
									[queue (正常队列, 有消费者, 当 ttl 队列成为死信后, 投递到这个队列进行消费)]
-----------------------
生产者
-----------------------

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Producer {
	
	public static final String DELAY_EXCHANGE = "delay_exchange";
	
	public static final String DELAY_05_SECONDS_QUEUE = "delay_05_seconds_queue";
	
	public static final String DELAY_10_SECONDS_QUEUE = "delay_10_seconds_queue";
	
	public static final String EXPIRATION_QUEUE = "expiration_queue";
	
	public static final String EXPIRATION_QUQUE_ROUTE = "expiration";
	
	public static void main(String[] args) throws Exception {

		// 创建连接工厂
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost("localhost");
		connectionFactory.setUsername("admin");
		connectionFactory.setPassword("admin");
		
		Connection connection = connectionFactory.newConnection();
		
		Channel channel = connection.createChannel();
		
		// 声明交换机
		channel.exchangeDeclare(DELAY_EXCHANGE, BuiltinExchangeType.DIRECT, false, false, false, null);
		
		// 声明延迟消费队列
		channel.queueDeclare(EXPIRATION_QUEUE, false, false, false, null);
		channel.queueBind(EXPIRATION_QUEUE, DELAY_EXCHANGE, EXPIRATION_QUQUE_ROUTE);
		
		// 声明05秒延迟队列
		Map<String, Object> properties = new HashMap<>();
		properties.put("x-dead-letter-exchange", DELAY_EXCHANGE);  // 没有消费者，则投递到这个交换机
		properties.put("x-dead-letter-routing-key" , EXPIRATION_QUQUE_ROUTE); // 没有消费者，，则投递到 x-dead-letter-exchange 交换机时使用的key
		properties.put("x-message-ttl", TimeUnit.SECONDS.toMillis(5)); // 消息在队列中的最大生存时间，超过这个时间还没被消费，则认为是“无消费者”
		channel.queueDeclare(DELAY_05_SECONDS_QUEUE, false, false, false, properties);
		channel.queueBind(DELAY_05_SECONDS_QUEUE, DELAY_EXCHANGE, "05"); // 5 秒 
		
		// 声明10秒延迟队列
		properties = new HashMap<>();
		properties.put("x-dead-letter-exchange", DELAY_EXCHANGE);
		properties.put("x-dead-letter-routing-key" , EXPIRATION_QUQUE_ROUTE);
		properties.put("x-message-ttl", TimeUnit.SECONDS.toMillis(10));
		channel.queueDeclare(DELAY_10_SECONDS_QUEUE, false, false, false, properties);
		channel.queueBind(DELAY_10_SECONDS_QUEUE, DELAY_EXCHANGE, "10"); // 10 秒 
		
		for (int i = 0; i < 5; i ++ ) {
			channel.basicPublish(DELAY_EXCHANGE, "05", true, false, null, 
					("[05秒]我是第" + (i + 1) + "条消息[" + DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()) + "]").getBytes());
		}
		for (int i = 0; i < 5; i ++ ) {
			channel.basicPublish(DELAY_EXCHANGE, "10", true, false, null, 
					("[10秒]我是第" + (i + 1) + "条消息[" + DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()) + "]").getBytes());
		}
		channel.close();
		connection.close();
	}
}



	

-----------------------
消费者
-----------------------
	

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CountDownLatch;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP.BasicProperties;

public class Consumer {
	
	public static final String DELAY_EXCHANGE = "delay_exchange";
	
	public static final String EXPIRATION_QUEUE = "expiration_queue";
	
	public static final String EXPIRATION_QUQUE_ROUTE = "expiration";
	
	public static void main(String[] args) throws Exception {
		
		CountDownLatch countDownLatch = new CountDownLatch(10);
		
		// 创建连接工厂
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost("localhost");
		connectionFactory.setUsername("admin");
		connectionFactory.setPassword("admin");
		
		Connection connection = connectionFactory.newConnection();
		
		Channel channel = connection.createChannel();
		
		// 声明交换机
		channel.exchangeDeclare(DELAY_EXCHANGE, BuiltinExchangeType.DIRECT, false, false, false, null);
		
		// 声明延迟消费队列
		channel.queueDeclare(EXPIRATION_QUEUE, false, false, false, null);
		channel.queueBind(EXPIRATION_QUEUE, DELAY_EXCHANGE, EXPIRATION_QUQUE_ROUTE);
		
		channel.basicConsume(EXPIRATION_QUEUE, false, new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body) throws IOException {
				
				System.out.println("[" + DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()) +"] 消费消息:"
						+ new String(body, StandardCharsets.UTF_8));
				
				channel.basicAck(envelope.getDeliveryTag(), false);
				
				countDownLatch.countDown();
			}
		});
		
		countDownLatch.await();

		channel.close();
		connection.close();
	}
}
