------------------------
连接工厂
------------------------
	# 连接工厂的结构体系
		ConnectionFactory
			|-AbstractConnectionFactory
				|-CachingConnectionFactory
				|-PooledChannelConnectionFactory
				|-ThreadChannelConnectionFactory
			|-AbstractRoutingConnectionFactory
				|-SimpleRoutingConnectionFactory
			|-LocalizedQueueConnectionFactory
	

	# PooledChannelConnectionFactory
		* 普遍使用，基于 Apache Pool2 管理单个连接和两个通道池。一个池用于事务通道，另一个池用于非事务通道。
		* pool 实现: GenericObjectPool , 也就是说 commons-pool2jar 必须在类路径上才能使用这个工厂
			<!-- https://mvnrepository.com/artifact/org.apache.commons/commons-pool2 -->
			<dependency>
				<groupId>org.apache.commons</groupId>
				<artifactId>commons-pool2</artifactId>
				<version>2.11.1</version>
			</dependency>
		* 支持消息确认
		* 编程方式修改池配置
			@Bean
			PooledChannelConnectionFactory pcf() throws Exception {
				ConnectionFactory rabbitConnectionFactory = new ConnectionFactory();
				rabbitConnectionFactory.setHost("localhost");
				PooledChannelConnectionFactory pcf = new PooledChannelConnectionFactory(rabbitConnectionFactory);
				pcf.setPoolConfigurer((pool, tx) -> {
					if (tx) {
						// 事务连接池配置
					}
					else {
						// 非事务连接池配置
					}
				});
				return pcf;
			}
	

	# ThreadChannelConnectionFactory
		* 该工厂管理1个连接，以及2个 ThreadLocal 分别用于事务/非事务的Channel
		* 它能确保同一线程上的所有操作都使用相同的通道（只要它保持打开状态），这有助于严格的消息排序，而无需Scoped Operations

------------------------
连接工厂
------------------------
	# 命名连接
		* 连接名称用于目标 RabbitMQ 连接的特定于应用程序的标识。如果 RabbitMQ 服务器支持，连接名称会显示在管理 UI 中。
		* 此值不必是唯一的，也不能用作连接标识符
	
		* 设置接口
			@FunctionalInterface
			public interface ConnectionNameStrategy {
				String obtainNewConnectionName(ConnectionFactory connectionFactory);
			}

			connectionFactory.setConnectionNameStrategy(connectionFactory -> "MY_CONNECTION");
		
		* SimplePropertyValueConnectionNameStrategy 实现 可以读取环境变量
			@Bean
			public SimplePropertyValueConnectionNameStrategy cns() {
				return new SimplePropertyValueConnectionNameStrategy("spring.application.name");
			}
			connectionFactory.setConnectionNameStrategy(cns());
	

				

