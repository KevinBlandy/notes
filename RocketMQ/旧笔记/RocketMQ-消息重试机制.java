--------------------------------
RocketMQ-消息重试机制			|
--------------------------------
	# RocketMQ消息重试大致分为 Producer 端,和 Customer 端



--------------------------------
RocketMQ-消息重试-Producer		|
--------------------------------
	# 生产者端消息重试
		* Producer 往 Broker 推送消息失败
		* 其实,Producer 默认就有消息重试机制,默认是重试 3 次

	# 根据消息发送结果判断是否发送成功
		SendResult sendResult = producer.send(message,1000);

		sendResult.SendStatus.SEND_OK;				
		sendResult.SendStatus.FLUSH_DISK_TIMEOUT,
		sendResult.SendStatus.FLUSH_SLAVE_TIMEOUT,
		sendResult.SendStatus.SLAVE_NOT_AVAILABLE,
	
	# Procuder 设置消息失败重发次数
		void setRetryTimesWhenSendFailed(int type);
	
		

--------------------------------
RocketMQ-消息重试-Consumer		|
--------------------------------
	# 消费者端消息重试分为两种
		* Broker 往 Consumer 推送消息失败(超时)
		* Consumer 消费失败(代码异常/业务逻辑)

	1,Broker消息重试
		* 只要没有收到 Consumer 的 CONSUME_SUCCESS 或者 RECONSUME_LATER.就认为是失败
		* 这种机制是无限制次数的,如果 broker 往 Consumer 投递消息超时,那么就重新选择集群中的另一个 Consumer 进行消息投递
		* 只要这个消息没有成功'推送',就一直投递,投递... ...
		* 千万要理解的是,这个重试的意思是'Broler 推送消息,Consumer 没有收到消息,或者是没有收到 Consumer 的回复'
		* Consumer在执行消费的过程中,宕机.没有返回结构.也会触发 Broker 的消息重试机制
		* 因为消息重试机制,所以可能会产生'重复消费的可能'
		
	2,Customer消息重试
		* 消息到达了 Consumer ,但是因为业务逻辑,需要重新消费这条消息,或者是代码抛出了异常.通过程序来控制消息重试

		# 通过 consumeMessage(List<MessageExt> msgs,ConsumeConcurrentlyContext context); 方法返回值来确定是否重发消息
			return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
				* 表示成功的消费了消息

			return ConsumeConcurrentlyStatus.RECONSUME_LATER;
				* 表示消费异常,需要重新消费
				* 如果是即时性消费,则仅仅重试当前消息
				* 如果是批量消息(broker堆积),则重试当前拉取的一批消息
		
		# 消费重试次数与间隔时间
			* 当一条消息消费失败,进行消息重试的时候.是有时间间隔的
			次数		间隔
			1			10秒
			2			30秒
			3			1分钟
			4			2分钟
			5			3分钟
			6			4分钟
			..			..
			12			10分钟
			13			20分钟
			14			30分钟
			15			1小时
			16			2小时

		# 获取当前消息被重试了多少次
			* 可以通过 MessageExt 的 reconsumeTimes 属性,来获取该消息已经重试了多少次,如果该值为 0,则表示该消息还未被重试过
				MessageExt [queueId=1, storeSize=182, queueOffset=129, sysFlag=0, bornTimestamp=1480145809942, bornHost=/192.168.250.171:60428, storeTimestamp=1480145783086, storeHost=/192.168.250.227:10911, msgId=C0A8FAE300002A9F0000000000018C1E, commitLogOffset=101406, bodyCRC=721375870, reconsumeTimes=0, preparedTransactionOffset=0, toString()=Message [topic=TopicQuickstart, flag=0, properties={MIN_OFFSET=0, MAX_OFFSET=135, CONSUME_START_TIME=1480145818965, UNIQ_KEY=C0A8FAAB05D073D16E93818580160039, WAIT=true, TAGS=TagA}, body=14]]
				int times = message.getReconsumeTimes();
			
		# 设置消息,最多重试消费多少次
			* 可以通过 message.getReconsumeTimes(); 来控制,'当一个消息失败N此后,就写入日志.然后返回 SUCCESS,告诉Broker这条消息成功消费.不再继续重试了'
				try{
					//逻辑代码
				}catch(Exception e){
					if(message.getReconsumeTimes() == 10){
						//当重试消费了10次,执行错误日志记录
						doErrorLog(message);
						//并且告诉Broker,该消息不用消息重试
						return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
					}
					//消息未重复10此,go on
					return ConsumeConcurrentlyStatus.RECONSUME_LATER;
				}
		


			
			
			
			