-----------------------
延时队列系统
-----------------------
	# 基于Redis设计
	# 参考
		https://tech.youzan.com/queuing_delay/
		https://tech.youzan.com/queuing_delay/

	# 核心概念
		Job
			* 需要延时处理的任务单元，以KV形式存储在Redis，用Hash比较好

		Topic
			* 具有一组相同消费逻辑的Job集合(队列)，供消费者订阅消费
			* 队列中存储的是Job的ID

			* 消费者轮询的时候可以使用Redis的BLPOP阻塞原语，当队列中没有数据得时候会阻塞直到有数据
			* 而不用一直空轮询浪费CPU性能
		
		JobPool
			* 存储所有Job的实际数据，以KV形式存储，KEY就是ID
		
		Bucket
			* Timer 每时每刻都在轮询这个Bucket中的job，判断是否到了执行时间
			* 如果到了执行时间，则添加到Topic中供消费者消费
			* 为了提升性能，可以有多个，Job放入的时候使用轮询负载

	# Job
		* 核心属性
			Topic				Job	类型。可以理解成具体的业务名称。
			Id					Job的唯一标识。用来检索和删除指定的Job信息。
			Delay				Job需要延迟的时间。单位：秒。（以绝对时间形式存储）
			TTR（time-to-run)	Job执行超时时间。单位：秒。（为了保证消息传输的可靠性。）
			Body				Job的内容，供消费者做具体的业务处理，以json格式存储。
	
		
	
	# 一个Job的生命周期
		* 用户下单，创建【付款超时】的Job，计算出它的绝对执行时间，以K/V形式存储到Jobpool，生成唯一ID
		* 轮询放入Bucket（zset），value就是ID，socre就是绝对执行时间
		* Timer时刻轮训bucket，获取并且移除每一个Job，判断它的执行时间是否到了。
		* 如果到了，就push到Topic中，时间没到，继续放入Bucket ？？



	
	