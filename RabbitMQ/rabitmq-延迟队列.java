-----------------------
�ӳٶ���ʵ��
-----------------------
	# ʹ��ͬһ������������ͬ�� route_key ���ֲ�ͬ���м���
	# һ�����������߱������ͬttl����ʱ����
	# ���Ѷ���ֻ��һ��
	# ·��ͼ
									[queue (ttl����, û��������, �������Ž�����, ����key)]
		[message] -> [exchange] -> 
									[queue (��������, ��������, �� ttl ���г�Ϊ���ź�, Ͷ�ݵ�������н�������)]
-----------------------
������
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

		// �������ӹ���
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost("localhost");
		connectionFactory.setUsername("admin");
		connectionFactory.setPassword("admin");
		
		Connection connection = connectionFactory.newConnection();
		
		Channel channel = connection.createChannel();
		
		// ����������
		channel.exchangeDeclare(DELAY_EXCHANGE, BuiltinExchangeType.DIRECT, false, false, false, null);
		
		// �����ӳ����Ѷ���
		channel.queueDeclare(EXPIRATION_QUEUE, false, false, false, null);
		channel.queueBind(EXPIRATION_QUEUE, DELAY_EXCHANGE, EXPIRATION_QUQUE_ROUTE);
		
		// ����05���ӳٶ���
		Map<String, Object> properties = new HashMap<>();
		properties.put("x-dead-letter-exchange", DELAY_EXCHANGE);  // û�������ߣ���Ͷ�ݵ����������
		properties.put("x-dead-letter-routing-key" , EXPIRATION_QUQUE_ROUTE); // û�������ߣ�����Ͷ�ݵ� x-dead-letter-exchange ������ʱʹ�õ�key
		properties.put("x-message-ttl", TimeUnit.SECONDS.toMillis(5)); // ��Ϣ�ڶ����е��������ʱ�䣬�������ʱ�仹û�����ѣ�����Ϊ�ǡ��������ߡ�
		channel.queueDeclare(DELAY_05_SECONDS_QUEUE, false, false, false, properties);
		channel.queueBind(DELAY_05_SECONDS_QUEUE, DELAY_EXCHANGE, "05"); // 5 �� 
		
		// ����10���ӳٶ���
		properties = new HashMap<>();
		properties.put("x-dead-letter-exchange", DELAY_EXCHANGE);
		properties.put("x-dead-letter-routing-key" , EXPIRATION_QUQUE_ROUTE);
		properties.put("x-message-ttl", TimeUnit.SECONDS.toMillis(10));
		channel.queueDeclare(DELAY_10_SECONDS_QUEUE, false, false, false, properties);
		channel.queueBind(DELAY_10_SECONDS_QUEUE, DELAY_EXCHANGE, "10"); // 10 �� 
		
		for (int i = 0; i < 5; i ++ ) {
			channel.basicPublish(DELAY_EXCHANGE, "05", true, false, null, 
					("[05��]���ǵ�" + (i + 1) + "����Ϣ[" + DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()) + "]").getBytes());
		}
		for (int i = 0; i < 5; i ++ ) {
			channel.basicPublish(DELAY_EXCHANGE, "10", true, false, null, 
					("[10��]���ǵ�" + (i + 1) + "����Ϣ[" + DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()) + "]").getBytes());
		}
		channel.close();
		connection.close();
	}
}



	

-----------------------
������
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
		
		// �������ӹ���
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost("localhost");
		connectionFactory.setUsername("admin");
		connectionFactory.setPassword("admin");
		
		Connection connection = connectionFactory.newConnection();
		
		Channel channel = connection.createChannel();
		
		// ����������
		channel.exchangeDeclare(DELAY_EXCHANGE, BuiltinExchangeType.DIRECT, false, false, false, null);
		
		// �����ӳ����Ѷ���
		channel.queueDeclare(EXPIRATION_QUEUE, false, false, false, null);
		channel.queueBind(EXPIRATION_QUEUE, DELAY_EXCHANGE, EXPIRATION_QUQUE_ROUTE);
		
		channel.basicConsume(EXPIRATION_QUEUE, false, new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body) throws IOException {
				
				System.out.println("[" + DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()) +"] ������Ϣ:"
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
