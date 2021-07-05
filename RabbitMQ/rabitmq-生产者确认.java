---------------------------
生产者确认
---------------------------
	# 默认情况下发送消息的操作是不会返回任何信息给生产者的
		* 也就是默认情况下生产者是不知道消息有没有正确地到达服务器
	
	# Rabbitmq针对这个问题有2个解决方案
		1. 事务
		2. 确认

	# 这2个解决方案不能共存，一下子只能使用一个


---------------------------
事务机制
---------------------------
	# 事务方法
		public abstract SelectOk txSelect()
		public abstract RollbackOk txRollback()
		public abstract CommitOk txCommit()
	
	# 实现
		public static void transaction (Channel channel) throws IOException {
			try {
				// 开始事务
				SelectOk selectOk = channel.txSelect();
				// 发送消息
				channel.basicPublish("my-exchange", "order", null, "Hello".getBytes());
				// 提交事务
				CommitOk commitOk = channel.txCommit();
			} catch (Throwable e) {
				// 回滚事务
				RollbackOk rollbackOk = channel.txRollback();
				throw e;
			}
		}
	
	# 这种方式有性能问题，会严重降低MQ的吞吐量，不建议使用

---------------------------	
发送方确认
---------------------------
	# 确认模式
		* 生产者将信道设置成确认模式，那么所有在该信道上面发布的消息都会被指派一个唯一的ID，从开0始
		* 消息进入队列后，会给生产者发送确认消息(Basic.Ack)
			* 如果消息是可持久化的，那么确认消息会在写入磁盘后发出
		* 确认消息中包含了消息的序号

	# 消息确认模式，同步
		public static void config (Channel channel) throws Throwable {
			try {
				// 开始确认模式
				Confirm.SelectOk selectOk = channel.confirmSelect();
				// 发送消息
				channel.basicPublish("my-exchange", "order", null, "Hello".getBytes());
				// 阻塞，直到获取MQ的确认消息
				if (!channel.waitForConfirms()) {
					// TODO 发送失败，执行处理
				}
			} catch (Throwable e) {
				// 回滚事务
				throw e;
			}
		}
	
