--------------------
Async				|
--------------------
	# 异步的执行方法
	# 注解驱动 @EnableAsync
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

		
		* mode 代理类型, 枚举
			PROXY
			ASPECTJ
		
		* order
			
		
	
	# 使用 @Async 开启异步
		* @Async 所修饰的函数不要定义为static类型, 这样异步调用不会生效
			public @interface Async {
				String value() default "";
			}
		
		* 可以通过 value 属性来指定 ioc 中一个线程池 bean 的名称
		* 从而使用指定的线程池来执行

		* 如果IOC中存在多个异步线程池, 那么必须要通过 value 属性明确的指出使用的线程池bean名称
	
	
	# 使用默认线程池
		* 需要在IOC注册一个实现:ThreadPoolTaskExecutor
		* 属性方法很多, 用到的时候再说
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
		
		* 如果 @Async 未配置线程池的名称(value属性)那么 @Async 都会使用该线程池来执行
	
	# 使用自定义线程池
		public interface AsyncConfigurer {
			// 返回执行任务的线程池
			@Nullable
			default Executor getAsyncExecutor() {
				return null;
			}

			// 返回异常处理器
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
				 * 异步任务线程池
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
		
		* 虚拟线程加持
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
					return new TaskExecutorAdapter(Executors.newVirtualThreadPerTaskExecutor());
				}
			}
