--------------------------------
RocketMQ-顺序消费				|
--------------------------------
	# OrderProducer
	# RocketMQ可以保证严格的顺序消费
	# 遵循全局的时候,使用一个 Queue,局部顺序的时候使用多个 Queue 并行消费.


--------------------------------
RocketMQ-Provider				|
--------------------------------

package com.kevin.rocketmq.helloword;
import java.util.List;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.MessageQueueSelector;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.common.message.MessageQueue;
/**
 * 消息生产者
 * */
public class Producer {
	private static final String PRODUCER_GROUP_NAME = "ORDER_GROUP";
	private static final String NAMESERVER_ADDRESS = "120.76.182.243:9876";
	private static final String TOPIC_NAME = "ORDER_TOPIC";
	public static void main(String[] args) throws Exception{ 
		DefaultMQProducer producer = new DefaultMQProducer(PRODUCER_GROUP_NAME);
		/**
		 * nameserver地址
		 */
		producer.setNamesrvAddr(NAMESERVER_ADDRESS);
		/**
		 * 初始化服务
		 */
		producer.start();		
		for(int x = 1;x <= 5;x ++){
			String body =  "顺序消息,第[" + x + "]条";		//消息正文
			String key = "1234565";				//消息主键
			Message message = new Message(TOPIC_NAME,"tag",key,body.getBytes());
			SendResult result = producer.send(message,new MessageQueueSelector() {
				public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
					Integer index = (Integer) arg;		//这里可以获取传递的对象进行操作
					/**
					 * 这里选择一个队列进行消息推送
					 */
					return mqs.get(index);
				}
			},0);										//0就是使用的队列的下标,这里可以传递任意对象
			System.out.println(result);
		}
		producer.shutdown();		//释放资源
    }
}

--------------------------------
RocketMQ-Consumer				|
--------------------------------
package com.kevin.rocketmq.helloword;
import java.util.List;
import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerOrderly;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.message.MessageExt;
/**
 * 顺序消息消费
 */
public class Consumer {
	private static final String CONSUMER_GROUP_NAME = "ORDER_GROUP";
	private static final String NAMESERVER_ADDRESS = "120.76.182.243:9876";
	private static final String TOPIC_NAME = "ORDER_TOPIC";
    public static void main(String[] args) throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(CONSUMER_GROUP_NAME);
        /**
         * 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费<br>
         * 如果非第一次启动，那么按照上次消费的位置继续消费
         */
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        /**
         * nameserver地址
         */
        consumer.setNamesrvAddr(NAMESERVER_ADDRESS);
        /**
         * 监听队列
         */
        consumer.subscribe(TOPIC_NAME,"*");
        /**
         * 设置监听
         */
        consumer.registerMessageListener(new MessageListenerOrderly() {
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
            	try {
                	context.setAutoCommit(true);
	                for (MessageExt message: msgs) {
	                	//消息消费
	                	String body = new String(message.getBody());		//正文
						String ququeId = message.getQueueId() + "";			//队列ID
						System.out.println("收到新消息,正文="+body+",队列Id="+ququeId);
	                }
	                //线程暂停
	                Thread.sleep(2000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return ConsumeOrderlyStatus.SUCCESS;
            }
        });
        consumer.start();
        System.out.println("消费者启动");
    }
}