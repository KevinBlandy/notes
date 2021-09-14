---------------------------
Exchange 
---------------------------
	# 交换机抽象接口
		public interface Exchange extends Declarable
	
	# 体系
		Exchange
			|-AbstractExchange
				|-CustomExchange	可以定义下列的所有类型交换机
				|-DirectExchange	把消息路由到那些 BindingKey 和 RoutingKey完全匹配的队列中
				|-FanoutExchange	把所有发送到该交换器的消息路由到所有与该交换器绑定的队列中
				|-HeadersExchange	根据header值来匹配的，性能贼差，基本不用
				|-TopicExchange		它与 direct 类型的交换器相似，也是将消息路由到 BindingKey 和 RoutingKey 相匹配的队列中，但这里的匹配规则有些不同，支持表达式模糊匹配
	
	String getName();
		* 交换机名称
	String getType();
		* 交换机类型: ExchangeTypes
	boolean isDurable();
		* 是否持久化
	boolean isAutoDelete();
		* 是否自动删除
	Map<String, Object> getArguments();
		* 参数
	boolean isDelayed();
		* 是否是延迟队列
	boolean isInternal();
		* 是否是内部的