----------------------
task					|
----------------------
	# SpringBoot自带的Task适合轻量级的
	# 注解配置
		* main程序入口配置:@EnableScheduling
		* 工作类配置:@Component
		* 工作方法配置:@Scheduled
			String cron() default "";
				* 定时任务表达式
			String zone() default "";
			long fixedDelay() default -1;
			String fixedDelayString() default "";
			long fixedRate() default -1;
			String fixedRateString() default "";
			long initialDelay() default -1;
			String initialDelayString() default "";

	
	#  处理异常
		No qualifying bean of type 'java.util.concurrent.ScheduledExecutorService' available
		* 好像是因为日志所产生的异常
		* 处理方式,修改日志配置文件
			<logger name="org.springframework.scheduling">
				<level value="info" />
			</logger>
	
	
	# 默认使用线程:scheduling 去执行
		* 可以使用 @Async 注解, 使用自定义的线程池去执行
	
	# properties配置(2.x版本好像不起作用)，配置类：TaskSchedulingProperties

		spring.task.execution.pool.max-threads = 16
			* 最大的线程数量

		spring.task.execution.pool.queue-capacity = 100
			* 任务队列的容量

		spring.task.execution.pool.keep-alive = 10s
			* 线程空闲多久就会被回收

		spring.task.execution.pool.allow-core-thread-timeout	true	
			* 是否允许核心线程超时。这样可以实现池的动态增长和收缩。
		spring.task.execution.pool.core-size	8.0	
			* 核心线程数。
		spring.task.execution.pool.keep-alive	60s	
			* 线程在被终止前可以保持空闲的时间限制。
		spring.task.execution.pool.max-size		
			* 允许的最大线程数。如果任务占满了队列，池可以扩展到该大小以适应负载。如果队列是无约束的，则忽略。
		spring.task.execution.pool.queue-capacity		
			* 队列容量。无限制的容量不会增加队列池，因此忽略了 "最大容量 "属性。
		spring.task.execution.shutdown.await-termination	false	
			* 执行者是否应该在关机时等待预定任务完成。
		spring.task.execution.shutdown.await-termination-period		
			* 执行者应等待剩余任务完成的最长时间。
		spring.task.execution.thread-name-prefix	task-	
			* 用于新创建的线程名称的前缀。
		spring.task.scheduling.pool.size	
			* 1.0	允许的最大线程数。
		spring.task.scheduling.shutdown.await-termination	false	
			* 执行者是否应该在关机时等待预定任务完成。
		spring.task.scheduling.shutdown.await-termination-period		
			* 执行者应等待剩余任务完成的最长时间。
		spring.task.scheduling.thread-name-prefix	scheduling-	
			* 用于新创建的线程名称的前缀。

# YAML 配置

  task:
    # Scheduling
    scheduling:
      shutdown:
        await-termination: true
        await-termination-period: 30s
      thread-name-prefix: "app-scheduling-"
      pool:
        size: 8
    # Aysnc
    execution:
      shutdown:
        await-termination: true
        await-termination-period: 30s
      thread-name-prefix: "app-task-"
      pool:
        queue-capacity: 50
        core-size: 8
        max-size: 16
        keep-alive: 30s

	
	# 注意
		* 默认任务的执行是单线程，如果一个任务阻塞了，其他任务都不会执行
		* 可以通过配置: spring.task.scheduling.pool.size 设置为多线程模式

		* 多线程模式下，就算任务执行时间，超出了“调度时间”。也不会重复执行。而是会等待上一次任务完成后，立即执行

---------------------
通过代码自定义配置	 |
---------------------
	# 实现接口 SchedulingConfigurer


	import java.util.concurrent.Executors;

	import org.slf4j.Logger;
	import org.slf4j.LoggerFactory;
	import org.springframework.context.annotation.Configuration;
	import org.springframework.scheduling.annotation.SchedulingConfigurer;
	import org.springframework.scheduling.config.CronTask;
	import org.springframework.scheduling.config.ScheduledTaskRegistrar;
	import org.springframework.scheduling.support.CronTrigger;

	@Configuration
	public class SchedulingConfiguration implements SchedulingConfigurer {
		
		private static final Logger LOGGER = LoggerFactory.getLogger(SchedulingConfiguration.class);

		@Override
		public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
			
			// 设置执行任务的线程池
			taskRegistrar.setScheduler(Executors.newScheduledThreadPool(20));
			
			// 动态添加新的定时任务，通过 Cron表达式执行
			taskRegistrar.addCronTask(new CronTask(new Runnable() {
				@Override
				public void run() {
					LOGGER.debug("任务执行...");
				}
			}, new CronTrigger("0/2 * * * * ?")));
		}
	}

	* 使用线程池的配置之后，再执行不仅以多线程来启动定时任务，而且也不会出现定时任务重复并发执行的问题
	* Executors.newScheduledThreadPool(20) // 很重要