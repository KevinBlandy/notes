-------------------
Handler
-------------------

-------------------
ע��
-------------------
	@DestinationVariable
		String value() default "";
	
	@Header
		String value() default "";
		String name() default "";
		boolean required() default true;
		String defaultValue() default ValueConstants.DEFAULT_NONE;
	
	@Headers
		* ��ʶ�ڴ����������ϣ���������һ�� Map ���ڷ�װ ����ͷ
	
	@MessageExceptionHandler
		* �쳣�Ĵ���
		
		Class<? extends Throwable>[] value() default {};
	

	
	

	@Payload
		* �����壬��ͨ�� MessageConverter ���з�װ

		String value() default "";
		String expression() default "";
		boolean required() default true;

	@SendTo
		* handler ��������ֵ�����Ϣ���͸��ĸ�topic���㲥

		String[] value() default {};
	
	@SendToUser
		String[] value() default {};
		String[] destinations() default {};
		boolean broadcast() default true;
	
	@MessageMapping
		* ������Ϣ��mapping�����ķ���ֵ�����Ϣ���͸�Broker

		MessageMapping
		
	
	@SubscribeMapping
		String[] value() default {};
