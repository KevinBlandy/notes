-------------------------
开启虚拟线程
-------------------------
	# 配置属性
		spring.threads.virtual.enabled=true # 默认为 false 表示未开启



-------------------------
服务器配置
-------------------------
	# Tomcat，目前仅支持 Tomcat
		@Configuration
		public class TomcatConfiguration {
			
			@Bean
			public TomcatProtocolHandlerCustomizer<?> protocolHandlerVirtualThreadExecutorCustomizer() {
				
				return protocolHandler -> {
					// 使用虚拟线程，指定线程前缀以及开始的数值
					protocolHandler.setExecutor(Executors.newThreadPerTaskExecutor(Thread.ofVirtual().name("coroutine-", 1).factory()));
				};
			}
		}


-------------------------
@Async 异步任务
-------------------------
	/**
	 * 
	 * 异步任务
	 * 
	 */
	@Configuration
	public class AsyncTaskConfiguration implements AsyncConfigurer {

		@Bean
		@Override
		public Executor getAsyncExecutor() {
			return new TaskExecutorAdapter(Executors.newThreadPerTaskExecutor(Thread.ofVirtual().name("vt-async-", 1).factory()));
		}
	}