--------------------------------
RabbitMQ-整合Spring				|
--------------------------------
	# 重头戏
	# 'spring对AMQP做了支持,但是目前只对RabbitMQ提供了实现'
	# xml的约束头
		xmlns:rabbit="http://www.springframework.org/schema/rabbit"
		xsi:schemaLocation = http://www.springframework.org/schema/rabbit http://www.springframework.org/schema/rabbit/spring-rabbit.xsd

	# 不仅要需要RabbitMQ的客户端,还需要整合包
		<dependency>
			<groupId>com.rabbitmq</groupId>
			<artifactId>amqp-client</artifactId>
			<version>3.6.5</version>
		</dependency>

		* 说实话,这MQ的API使着确实不顺手,所以需要封装.要么我们自己来.要么别人来.
		* 当然是别人来,没错,万金油:spring
			<dependency>
				<groupId>org.springframework.amqp</groupId>
				<artifactId>spring-rabbit</artifactId>
				<version>1.6.3.RELEASE</version>
			</dependency>
	
		
		/**
			xml配置
		*/
		<description>spring整合RabbitMQ-demo</description>
		<!-- 定义RabbitMQ的连接工厂 -->
		<rabbit:connection-factory 
					id="connectionFactory"
					host="127.0.0.1" port="5672" 
					username="kevin" 
					password="a12551255"
					virtual-host="/kevinblandy" />

		
		<!-- 
			定义Rabbit模板，指定连接工厂以及定义(交换机)exchange / 或者是队列
			IOC中获取到 RabbitTemplate 就是它,它发送的消息就到了,指定的交换机上
		-->
		<rabbit:template 
			id="amqpTemplate" 
			connection-factory="connectionFactory" 
			exchange="fanoutExchange" />

		<!-- 同上,也是定义模版,交换机而且这个还指定了消息的key
			<rabbit:template 
				id="amqpTemplate" 
				connection-factory="connectionFactory"
				exchange="fanoutExchange" 
				routing-key="foo.bar" />
		-->
		<!-- 同上,定义模块,这种方式是把消息直接给队列,并非是给交换机
			<rabbit:template 
				id="amqpTemplate" 
				connection-factory="connectionFactory"
				queue="myQueue"  />
		-->


		<!-- 
			MQ的管理，包括队列、交换器等 .指定一个连接工厂就OK
			它注册IOC的作用就是,帮我们完成自动的声明啊之类的
		-->
		<rabbit:admin connection-factory="connectionFactory" />

		<!-- 
			定义队列，自动声明(declare):如果队列不存在,就创建,存在就啥也不干 
			durable:指定是要要持久化队列
		-->
		<rabbit:queue name="myQueue" auto-declare="true" durable="flase"/>
		
		<!-- 
			定义交换机，自动声明(auto-declare):如果交换机不存在,我就创建,如果存在.就啥也不干
		-->
		<rabbit:fanout-exchange name="fanoutExchange" auto-declare="true">
			<!-- 交换机绑定队列 -->
			<rabbit:bindings>
				<rabbit:binding queue="myQueue"/>
			</rabbit:bindings>
		</rabbit:fanout-exchange>
		
		<!--
			这个也是定义交换机,是topic类型,通配符的
		<rabbit:topic-exchange name="myExchange">
			<rabbit:bindings>
				<rabbit:binding queue="myQueue" pattern="foo.*" />
			</rabbit:bindings>
		</rabbit:topic-exchange> 
		-->



		
		<!-- 
			队列监听 指定连接工厂,以及指定处理类和方法,还有监听的队列名称
		-->
		<rabbit:listener-container connection-factory="connectionFactory">
			<rabbit:listener ref="foo" method="listen" queue-names="myQueue" />
		</rabbit:listener-container>
		<!-- 消费者bean 注册到IOC-->
		<bean id="foo" class="cn.itcast.rabbitmq.spring.Foo" />


		/**
			演示主函数
		*/
		public class SpringMain {
			public static void main(final String... args) throws Exception {
				AbstractApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:spring/rabbitmq-context.xml");
				//从IOC中获取RabbitMQ模板
				RabbitTemplate template = ctx.getBean(RabbitTemplate.class);
				//发送消息
				template.convertAndSend("Hello, world!");
				Thread.sleep(1000);// 休眠1秒
				ctx.destroy(); //容器销毁
			}
		}

		/**
		 * 消费者
		 **/
		public class Foo {
			/**
			 * 消费者监听到消息执行的业务方法
			 * */
			public void listen(String foo) {
				System.out.println("消费者： " + foo);
			}
		}
	
--------------------------------
RabbitMQ-整合Spring-API			|
--------------------------------

	RabbitTemplate
		//发送数据
		converAndSend("item.update","{id:'123',name:'Kevin'}");
			* 第一个参数就是队列key