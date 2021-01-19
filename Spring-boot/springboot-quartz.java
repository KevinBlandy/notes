--------------------------------
quartz	整合					|
--------------------------------
	# 导入依赖
		<dependency>
		   <groupId>org.springframework.boot</groupId>
		   <artifactId>spring-boot-starter-quartz</artifactId>
		</dependency>
	
	# 配置:QuartzProperties(配置类)
		spring:
		  quartz:
			job-store-type:
				* 存储任务
				* 枚举:MEMORY(默认),JDBC
			scheduler-name:
				* 集群的名称(单机环境无所谓)
			auto-startup: true
				* 初始化后立即执行
			startup-delay: 0
				* 系统启动后延迟多久开始执行定时任务
			wait-for-jobs-to-complete-on-shutdown: false
				* 在系统关闭的时候, 是否等待任务执行完毕
			overwrite-existing-jobs: false
				* 是否要覆盖已经存在的任务记录
			properties: 
				* 自定义的高级Quartz配置属性
			jdbc:
			  schema: classpath:org/quartz/impl/jdbcjobstore/tables_@@platform@@.sql
				* 任务表的建表语句, 默认值: classpath:org/quartz/impl/jdbcjobstore/tables_@@platform@@.sql
				* 在jar的目录: org/quartz/impl/jdbcjobstore 下, 根据DB的不同, SQL文件也不同
			  initialize-schema: EMBEDDED
				* 任务表的创建策略(枚举):
					EMBEDDED(默认)	数据库已经手动初始化, 不需要程序进行初始化
					ALWAYS			在每次启动时初始化
					NEVER			不初始化
			  comment-prefix: --
				* 设置SQL注释的前缀
	

	# 可以通过编码配置, 实现接口: SchedulerFactoryBeanCustomizer
		void customize(SchedulerFactoryBean schedulerFactoryBean);

	
	# 多个数据源的时候, 需要为指定用于处理定时任务的数据源对象标识: @QuartzDataSource
		@Bean
		@QuartzDataSource
		public Datasource datasource(){}
	
	# 数据表的说明
		qrtz_blob_triggers
			* 以Blob 类型存储的触发器。
		qrtz_calendars
			* 存放日历信息, quartz可配置一个日历来指定一个时间范围
		qrtz_cron_triggers
			* 存放cron类型的触发器
		qrtz_fired_triggers
			* 存放已触发的触发器
		qrtz_job_details
			* 存放一个jobDetail信息
		qrtz_locks
			* 存储程序的悲观锁的信息(假如使用了悲观锁)
		qrtz_paused_trigger_graps
			* 存放暂停掉的触发器
		qrtz_scheduler_state
			* 调度器状态
		qrtz_simple_triggers
			* 简单触发器的信息
		qrtz_triggers
			* 触发器的基本信息
	
	# 自定义的高级Quartz配置属性
org:
  quartz:
	scheduler:
	  instanceName: clusteredScheduler 
		* 调度器实例名称
	  instanceId: AUTO 
		* 调度器实例编号自动生成
	jobStore:
	  class: org.quartz.impl.jdbcjobstore.JobStoreTX 
		* 持久化方式配置
	  driverDelegateClass: org.quartz.impl.jdbcjobstore.StdJDBCDelegate 
		* 持久化方式配置数据驱动，MySQL数据库
	  tablePrefix: qrtz_ 
		* quartz相关数据表前缀名
	  isClustered: true 
		* 开启分布式部署
	  clusterCheckinInterval: 10000 
		* 分布式节点有效性检查时间间隔，单位：毫秒
	  useProperties: false 
		* 表示JDBCJobStore JobDataMaps中的所有值都是字符串

	threadPool:
	  class: org.quartz.simpl.SimpleThreadPool 
		* 线程池实现类
	  threadCount: 10 
		* 执行最大并发线程数量
	  threadPriority: 5 
		* 线程优先级
	  threadsInheritContextClassLoaderOfInitializingThread: true 
		* 配置是否启动自动加载数据库内的定时任务，默认true

--------------------------------
quartz	整合					|
--------------------------------
	# 任务类继承:QuartzJobBean 
		
	# JobDetail
	# Calendar
	# Trigger
