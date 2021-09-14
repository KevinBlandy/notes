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



