-----------------------------
Binding
-----------------------------
	# 交换机/队列绑定关系
		public class Binding extends AbstractDeclarable 

	# 方法
		public Binding(String destination, DestinationType destinationType, String exchange, String routingKey, @Nullable Map<String, Object> arguments)
		public String getDestination()
		public DestinationType getDestinationType()
		public String getExchange() 
		public String getRoutingKey()
		public boolean isDestinationQueue()
	
	# DestinationType 目标类型
		public enum DestinationType {
			QUEUE,
			EXCHANGE;
		}
	

-----------------------------
BindingBuilder
-----------------------------
	# 用于创建绑定关系的Builder类
		public static DestinationConfigurer bind(Queue queue)
		public static DestinationConfigurer bind(Exchange exchange)


		public static final class DestinationConfigurer {
			public Binding to(FanoutExchange exchange)
			public HeadersExchangeMapConfigurer to(HeadersExchange exchange)
			public   to(DirectExchange exchange)
			public TopicExchangeRoutingKeyConfigurer to(TopicExchange exchange)
			public GenericExchangeRoutingKeyConfigurer to(Exchange exchange)
		}
		public static final class HeadersExchangeMapConfigurer {
			public HeadersExchangeSingleValueBindingCreator where(String key)
			public HeadersExchangeKeysBindingCreator whereAny(String... headerKeys)
			public HeadersExchangeMapBindingCreator whereAny(Map<String, Object> headerValues)
			public HeadersExchangeKeysBindingCreator whereAll(String... headerKeys)
			public HeadersExchangeMapBindingCreator whereAll(Map<String, Object> headerValues)
		}
		public final class HeadersExchangeSingleValueBindingCreator {
			public Binding exists()
			public Binding matches(Object value)
		}
		public final class HeadersExchangeKeysBindingCreator {
			public Binding exist()
		}
		public final class HeadersExchangeMapBindingCreator {
			public Binding match() 
		}
		public static final class TopicExchangeRoutingKeyConfigurer extends AbstractRoutingKeyConfigurer {
			public Binding with(String routingKey)
			public Binding with(Enum<?> routingKeyEnum)
		}
		public static final class GenericExchangeRoutingKeyConfigurer extends AbstractRoutingKeyConfigurer {
			public GenericArgumentsConfigurer with(String routingKey)
			public GenericArgumentsConfigurer with(Enum<?> routingKeyEnum)
		}
		public static class GenericArgumentsConfigurer {
			public GenericArgumentsConfigurer(GenericExchangeRoutingKeyConfigurer configurer, String routingKey)
			public Binding and(Map<String, Object> map) 
			public Binding noargs()
		}
		public static final class DirectExchangeRoutingKeyConfigurer extends AbstractRoutingKeyConfigurer {
			public Binding with(String routingKey)
			public Binding with(Enum<?> routingKeyEnum)
			public Binding withQueueName() 
		}
		
	# Demo
		Binding b = BindingBuilder.bind(someQueue).to(someTopicExchange).with("foo.*");