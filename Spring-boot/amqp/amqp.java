-------------------
rabbitmq
-------------------
	# �ο�
		https://docs.spring.io/spring-amqp/docs/current/reference/html/
		https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.messaging
	
	# ����
		 <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-amqp</artifactId>
        </dependency>
	
	
	# spring-amqp �ṩ�˸��߼��ĳ��󣬵ײ�ʵ�ֿ�����Rabbit��������ʵ����amqp��Э�����Ϣ����
		
	
	# �������
		CachingConnectionFactory 
			* ���ӹ���
		RabbitAdmin 
			* RabbitMQ�Ĺ���ͻ���ʵ��
		RabbitTemplate 
			* ��Ϣ����Template
		
		Queue 
			* ����
		Message
			* ��Ϣ����
		Exchange
			* ������
		Binding
			* �󶨹�ϵ
	
	# AMQP ע��
		@EnableRabbit
		@RabbitListener
		@Exchange
		@Queue
		@QueueBinding
		@RabbitListeners
		@RabbitHandler
			boolean isDefault() default false;
				* ��Ϊ true ʱ����� payload ���������� RabbitHandler ������ƥ�䣬ָ������Ĭ�ϵ� fallback ������
				* ֻ��һ���������Ա����ָ����
			
			* ע���ڷ����ϣ���ע�������ϵ� @RabbitListener �������
			* �յ������� @RabbitListener ָ�����е���Ϣʱ���ӵ�ǰ������ע���� @RabbitHandler �����У�������Ϣ�������ͺͷ�����������ƥ�䣬ѡ��һ���������е��á�
			* ����Ҫô�ǵ���������Ƕ��������Ҫʹ�� @Payload ָ����Ϣ�󶨵Ĳ���

		@Argument
	
	# ͨ�õ�Messageע��
		@Header
			* ��Ϣͷ

		@Headers
		@DestinationVariable
		@MessageExceptionHandler
		@MessageMapping
		@Payload
			* ��Ϣ��
		
		@SendTo


-------------------
rabbitmq ����
-------------------
	# ������
		RabbitProperties

spring:
  rabbitmq:
    # �������÷�ʽ�������ӵ���Ⱥ: host1:5672,host2:5672,host3:5672
    addresses: "amqp://admin:secret@localhost" 
    host: localhost
    port: 5672
    username: admin
    password: secret



