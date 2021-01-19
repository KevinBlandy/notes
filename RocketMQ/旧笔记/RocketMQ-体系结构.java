----------------------------
RocketMQ-体系结构			|
----------------------------
	# RocketMQ包含9个子模块
		rocketmq-common
			* 通用的常量枚举类,基类方法或者数据结构.按描述的目标来分包.通俗易懂
			* rocketmq-common-admin
			* rocketmq-common-consumer
			* rocketmq-common-filter
			* rocketmq-common-hook
			* rocketmq-common-message

		rocketmq-remoting
			* 用Netty4写的客户端和服务端,fastjson做的序列化.自定义二进制协议
			
		rocketmq-sryutil		
			* 只有一个ServerUtil类,类注解是,只提供Server依赖.目的为了拆解客户端依赖.尽可能减少客户端的依赖
			
		rocketmq-store		
			* 存储服务:消息存储,索引存储,commitlog存储

		rocketmq-client
			* 客户端:包含producer和consumer端,发送消息和接收消息的过程
			
		rocketmq-filtersrv
			* 消息过滤器server,现在rocketmq的wiki上有示例代码及说明程
			
		rocketmq-broker	
			* 对consumer和producer来说是服务器,接收producer发来的消息并存储.同时consumer来这里拉取消息

		rocketmq-tools
			* 命令行工具

		rocketmq-namesrv
			* NameServer ,类似于SOA服务的注册中心,这里保存着消息的TopicName,队列等运行时meta信息.一般系统分dataNode和nameNode,这里是nameNode

