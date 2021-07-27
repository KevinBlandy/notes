-------------------
Handler
-------------------

-------------------
注解
-------------------
	@DestinationVariable
		String value() default "";
	
	@Header
		String value() default "";
		String name() default "";
		boolean required() default true;
		String defaultValue() default ValueConstants.DEFAULT_NONE;
	
	@Headers
		* 标识在处理方法参数上，它必须是一个 Map 用于封装 请求头
	
	@MessageExceptionHandler
		* 异常的处理
		
		Class<? extends Throwable>[] value() default {};
	

	
	

	@Payload
		* 请求体，会通过 MessageConverter 进行封装

		String value() default "";
		String expression() default "";
		boolean required() default true;

	@SendTo
		* handler 方法返回值会把消息发送给哪个topic，广播

		String[] value() default {};
	
	@SendToUser
		String[] value() default {};
		String[] destinations() default {};
		boolean broadcast() default true;
	
	@MessageMapping
		* 监听消息的mapping，它的返回值会把消息发送给Broker

		MessageMapping
		
	
	@SubscribeMapping
		String[] value() default {};
