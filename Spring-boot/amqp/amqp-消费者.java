

------------------------------


import java.io.IOException;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class BillConsumer {

	@RabbitListener(autoStartup = "true",	// 自动启动
					ackMode = "MANUAL", // 手动确认
					bindings = {@QueueBinding(value = @Queue(name = "my-queue", // 队列名称
													durable = "true", 			// 持久化
													exclusive = "false",		// 非独占
													autoDelete = "false"		// 不自动删除
													),
											exchange = @Exchange(
													name = "my-exchange",		// 交换机名称
													type = ExchangeTypes.DIRECT // 路由模式
													), 
											key = "my-key")})
	public void billLinstener (@Payload String payload, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, Channel channel) {
		log.info("tag = {}, message = {}", deliveryTag , payload);
		
		try {
			channel.basicAck(deliveryTag, true);
		} catch (IOException e) {
			log.error("消息确定异常: {}", e.getMessage());
		}
	}
}
