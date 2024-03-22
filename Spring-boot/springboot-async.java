--------------------
Async				|
--------------------
	# �첽��ִ�з���
	# ע������ @EnableAsync
		@Target(ElementType.TYPE)
		@Retention(RetentionPolicy.RUNTIME)
		@Documented
		@Import(AsyncConfigurationSelector.class)
		public @interface EnableAsync {

			Class<? extends Annotation> annotation() default Annotation.class;

			boolean proxyTargetClass() default false;

			AdviceMode mode() default AdviceMode.PROXY;

			int order() default Ordered.LOWEST_PRECEDENCE;
		}

		
		* mode ��������, ö��
			PROXY
			ASPECTJ
		
		* order
			
		
	
	# ʹ�� @Async �����첽
		* @Async �����εĺ�����Ҫ����Ϊstatic����, �����첽���ò�����Ч
			public @interface Async {
				String value() default "";
			}
		
		* ����ͨ�� value ������ָ�� ioc ��һ���̳߳� bean ������
		* �Ӷ�ʹ��ָ�����̳߳���ִ��

		* ���IOC�д��ڶ���첽�̳߳�, ��ô����Ҫͨ�� value ������ȷ��ָ��ʹ�õ��̳߳�bean����
	
	
	# ʹ��Ĭ���̳߳�
		* ��Ҫ��IOCע��һ��ʵ��:ThreadPoolTaskExecutor
		* ���Է����ܶ�, �õ���ʱ����˵
			@Bean
			public ThreadPoolTaskExecutor threadPoolTaskExecutor(){
				ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
				threadPoolTaskExecutor.setCorePoolSize(4);
				threadPoolTaskExecutor.setMaxPoolSize(10);
				threadPoolTaskExecutor.setThreadFactory( r -> {
					Thread thread = new Thread(r);
					thread.setName("ThreadPoolTaskExecutor-Thread");
					return thread;
				});
				return threadPoolTaskExecutor;
			}
		
		* ��� @Async δ�����̳߳ص�����(value����)��ô @Async ����ʹ�ø��̳߳���ִ��
	
	# ʹ���Զ����̳߳�
		public interface AsyncConfigurer {
			// ����ִ��������̳߳�
			@Nullable
			default Executor getAsyncExecutor() {
				return null;
			}

			// �����쳣������
			@Nullable
			default AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
				return null;
			}

		}
		
		* demo
			import java.util.concurrent.Executor;
			import java.util.concurrent.ThreadPoolExecutor;

			import org.springframework.context.annotation.Bean;
			import org.springframework.context.annotation.Configuration;
			import org.springframework.scheduling.annotation.AsyncConfigurer;
			import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

			@Configuration
			public class AsyncConfiguration implements AsyncConfigurer {

				@Override
				public Executor getAsyncExecutor() {
					return this.asyncExecutor();
				}

				/**
				 * �첽�����̳߳�
				 * @return
				 */
				@Bean(initMethod = "initialize", destroyMethod = "shutdown")
				public Executor asyncExecutor() {
					ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
					threadPoolTaskExecutor.setCorePoolSize(4);
					threadPoolTaskExecutor.setMaxPoolSize(20);
					threadPoolTaskExecutor.setThreadNamePrefix("async-task-");
					threadPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
					return threadPoolTaskExecutor;
				}
			}
		
		* �����̼߳ӳ�
			/**
			 * 
			 * �첽����
			 * 
			 */
			@Configuration
			public class AsyncTaskConfiguration implements AsyncConfigurer {

				@Bean
				@Override
				public Executor getAsyncExecutor() {
					return new TaskExecutorAdapter(Executors.newVirtualThreadPerTaskExecutor());
				}
			}
