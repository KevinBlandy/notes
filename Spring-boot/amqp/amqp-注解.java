------------------------
@RabbitListener
------------------------
	String id() default "";
		* 管理该端点的容器的唯一标识符。如果没有指定，将提供一个自动生成的标识。

	String containerFactory() default "";
		* RabbitListenerContainerFactory 的bean名称 ，用于创建负责为该端点服务的消息监听器容器
		* 如果没有指定，将使用默认的容器工厂（如果有）。如果提供了 SpELexpression (#{...}})，该表达式可以评估为容器工厂实例或 bean 名称。

	String[] queues() default {};
		* 该监听器的队列。条目可以是 "队列名称"、"属性占位符键" 或 "表达式"。
		* 表达式必须被解析为队列名称或队列对象。
		* 队列必须存在，或者在其他地方被定义为上下文中的 org.springframework.amqp.rabbit.core.RabbitAdmin 的 bean。
		* 与 bindings 和 queuesToDeclare 互斥。
	
	Queue[] queuesToDeclare() default {};
		* 如果在上下文中有 org.springframework.amqp.rabbit.core.RabbitAdmin，那么队列将在 broker 上用 defaultbinding （默认 exchange ，队列名称为 routing key）声明。
		* 与 bindings() 和 queues() 相互排斥。注意：broker 命名的队列不能以这种方式声明，它们必须被定义为 bean（名称为一个空字符串）。

	boolean exclusive() default false;
		* 当为true时，容器中的单个消费者将独占使用 queues()，从而阻止其他消费者从队列接收消息
		* 当为真时，要求 concurrency 值1。 默认为 false

	String priority() default "";
		* 此端点的优先级，要求 RabbitMQ 3.2 或更高版本。默认情况下不会改变容器的优先级。
		* 较大的数字表示较高的优先级，并且可以使用正数和负数。
	
	String admin() default "";
		* 引用 AmqpAdmin bean，如果监听器使用自动删除队列并且这些队列被配置为有条件的声明，则需要引用 AmqpAdmin。
		* 这是一个管理员，它将在容器（重新）启动时（重新）声明这些队列。更多信息请参见参考文档。
		* 如果提供了SpEL表达式（#{...})，该表达式可以评估为 org.springframework.amqp.core.AmqpAdmin 实例或bean名称。
	
	QueueBinding[] bindings() default {};
		* QueueBinding 提供监听器队列名称以及 exchange 和可选绑定信息的数组。
		* 提供监听器队列名称的队列绑定数组，以及交换和可选的绑定信息。与 queues() 和 queuesToDeclare() 互斥。
		* 注意：broker 命名的队列不能以这种方式声明，它们必须被定义为 bean（名称为空字符串）。
	
	String group() default "";
		* 如果提供，这个监听器的监听器容器将被添加到一个以这个值为名称的bean中，类型为 Collection<MessageListenerContainer>。
		* 这允许，例如，在集合上的迭代来启动/停止一个容器子集。

	String returnExceptions() default "";
		* 设置为 "true "可以使监听器抛出的异常使用正常的 replyTo/@SendTo 语义发送到发送方。
		* 当设置为 "false "时，异常会被扔到监听器容器中，并进行正常的重试/DLQ处理。

	String errorHandler() default "";
		* 设置一个 org.springframework.amqp.rabbit.listener.api.RabbitListenerErrorHandler，以便在监听器方法抛出异常时进行调用，一个代表 bean 名称的简单字符串。
		* 如果提供了 Spel 表达式（#{...}），该表达式必须评估为一个 Bean 名称或一个 org.springframework.amqp.rabbit.listener.api.RabbitListenerErrorHandlerinstance。

	String concurrency() default "";
		* 为这个监听器设置监听器容器的并发量。覆盖由监听器容器工厂设置的默认值。映射到容器类型的并发性设置。
		* 对于SimpleMessageListenerContainer来说，如果这个值是一个简单的整数，它就会在 concurrentConsumers 属性中设置一个固定数量的消费者。
			* 如果它是一个形式为 "m-n "的字符串， concurrentConsumers 被设置为mand， maxConcurrentConsumers 被设置为n。
		* 对于一个 DirectMessageListenerContainer 来说，它设置 consumerPerQueue 属性。


	String autoStartup() default "";
		* 设置为 true 或 false，以覆盖容器工厂的默认设置。
		
	String executor() default "";
		* 为这个监听器的容器设置 executor bean 名称；覆盖容器工厂上设置的任何executor。如果提供了SpEL表达式(#{...}}，该表达式可以评估为一个 executor 实例或一个 bean 名。

	String ackMode() default "";
		* 消息确认模式，由 AcknowledgeMode 枚举类定义
			NONE
			MANUAL
			AUTO

	String replyPostProcessor() default "";
		* org.springframework.amqp.rabbit.listener.adapter.ReplyPostProcessor 的bean名称，用于在发送响应之前进行后处理。
		* 如果提供了SpEL表达式(#{...})，该表达式可以评估为一个 ReplyPostProcessor 实例或一个bean名称。
		
	String messageConverter() default "";
		* 覆盖该监听器使用的容器工厂的消息转换器。

	String replyContentType() default "";
		* 用于设置回复消息的内容类型。
		* 当与可以处理多种内容类型的消息转换器一起使用时非常有用，例如 org.springframework.amqp.support.converter.ContentTypeDelegatingMessageConverter.SpEL 表达式和属性占位符被支持。
		* 如果你希望在与某些转换器一起使用时控制最终的内容类型属性，也很有用。
		* 这不适用于返回类型为org.springframework.amqp.core.Message或org.springframework.messaging.Message的情况；在这些情况下，分别设置内容类型message属性或header。

	String converterWinsContentType() default "true";
		* 设置为'false'可以用'replyContentType'属性的值来覆盖消息转换器设置的任何 content type 头。
		* 一些转换器，如 org.springframework.amqp.support.converter.SimpleMessageConverter 使用payload类型并适当地设置内容类型头。
		* 例如，如果你将'replyContentType'设置为 "application/json"，并在返回包含JSON的字符串时使用简单消息转换器，该转换器将覆盖内容类型为'text/plain'。
		* 将此设置为 false ，以防止该动作。
		* 这不适用于返回类型为org.springframework.amqp.core.Message的情况，因为不涉及转换。当返回org.springframework.messaging.Message时，将内容类型消息头和org.springframework.amqp.support.AmqpHeaders.CONTENT_TYPE_CONVERTER_WINS设为false。


------------------------
Queue
------------------------
	@AliasFor("name")
	String value() default "";
	@AliasFor("value")
	String name() default "";
	String durable() default "";
	String exclusive() default "";
	String autoDelete() default "";
	String ignoreDeclarationExceptions() default "false";
		* 是否忽略声明异常

	Argument[] arguments() default {};
		* 声明队列时的参数
	
	String declare() default "true";
		* 如果 admin(s)（如果存在）应该声明这个组件，则为true。

	String[] admins() default {};
		* 返回一个应该声明此组件的 admin bean name 列表。默认情况下，所有 admin 都会声明此组件。

------------------------
QueueBinding
------------------------
	Queue value();
	Exchange exchange();
	String[] key() default {};
	String ignoreDeclarationExceptions() default "false";
	Argument[] arguments() default {};
		String declare() default TRUE;
		* 如果该值为 true ，并且 admins 存在的话，那么会用 admins 中的所有 admin 进行声明
		
	String[] admins() default {};
		* 指定 AmqpAdmin bean 的名称，可以指定多个

------------------------
Exchange
------------------------
	String TRUE = "true";
	String FALSE = "false";

	@AliasFor("name")
	String value() default "";
	@AliasFor("value")
	String name() default "";
		* 交换机名称
			
	String type() default ExchangeTypes.DIRECT;
		* 交换机类型

	String durable() default TRUE;
	String autoDelete() default FALSE;
	String internal() default FALSE;
		* 是否是内部的

	String ignoreDeclarationExceptions() default FALSE;
		* 忽略声明异常

	String delayed() default FALSE;
		*  如果为 true ，则 exchange 被声明为 'x-delayed-message' exchange。需要 broker 上的延迟消息 exchange 插件。

	Argument[] arguments() default {};
		* 声明的参数

	String declare() default TRUE;
		* 如果该值为 true ，并且 admins 存在的话，那么会用 admins 中的所有 admin 进行声明
		
	String[] admins() default {};
		* 指定 AmqpAdmin bean 的名称，可以指定多个

------------------------
Argument
------------------------
	String name();
	String value() default "";
	String type() default "java.lang.String";

