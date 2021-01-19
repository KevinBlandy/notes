-----------------------------------
Quartz-入门							|
-----------------------------------
	# 简介
		* 是由OepnSymhony开源组织在job scheduling 领域又一个开源项目
		* 反正很牛逼的任务调度系统,可以直接在SE或者EE项目中使用
		* 可以创建一个,或者十个,千个等等复杂的任务调度系统
	
	# 网址
		http://www.quartz-scheduler.org


	# Maven依赖
		<dependency>
			<groupId>org.quartz-scheduler</groupId>
			<artifactId>quartz</artifactId>
		</dependency>

		<dependency>
			<groupId>org.quartz-scheduler</groupId>
			<artifactId>quartz-jobs</artifactId>
		</dependency>   

		* jobs模块提供的主要是一些工具

	# 核心接口
		Scheduler
			* 核心调度器

		Job
			* 任务

		JobDetail
			* 任务描述

		Trigger
			* 触发器
			SimpleTrigger
			CronTrigger
			CalendarIntervalTrigger
			DailyTimeIntervalTrigger
		
		JobBuilder
			* 用于定义/构建JobDetail实例
		
		TriggerBuilder
			* 用于定义/构建Trigger实例
			
		* 关系图

							Scheduler
								↑(注册)
				 -------------------------------
				| ---------------				|
				||Job + JobDetail|		Trigger	|
				| ---------------				|
				 -------------------------------

-----------------------------------
Quartz-	Trigger 触发器详解			|
-----------------------------------
	SimpleTrigger
		* 简单的触发器
		* 用来触发只需要执行一次或者在给定时间触发N次且每次执行延迟一定时间的任务
		

	CronTrigger
		* 表达式触发器
		* 比较复杂的任务调度系统,是需要通过表达式的确定任务的调度
	

-----------------------------------
Quartz-	Demo						|
-----------------------------------
	# 简单的触发示例
		* 直接就死普通类,不需要继承或者实现任何的接口或者类
		* 

	# 表达式触发示例



-----------------------------------
Quartz-	整合Spring					|
-----------------------------------
	<!-- 定义任务bean -->
	<bean name="paymentOrderJobDetail" class="org.springframework.scheduling.quartz.JobDetailFactoryBean">
		<!-- 指定具体的job类(自定义继承了:'QuartzJobBean'抽线类的子类) -->
		<property name="jobClass" value="com.taotao.store.order.job.PaymentOrderJob" />
		<!-- 指定job的名称 -->
		<property name="name" value="paymentOrder" />
		<!-- 指定job的分组 -->
		<property name="group" value="Order" />
		<!-- 必须设置为true，如果为false，当没有活动的触发器与之关联时会在调度器中删除该任务  -->
		<property name="durability" value="true"/>
		<!-- 指定spring容器的key，如果不设定在job中的jobmap中是获取不到spring容器的 -->
		<property name="applicationContextJobDataKey" value="applicationContext"/>
	</bean>
	
	<!-- 定义触发器 -->
	<bean id="cronTrigger" class="org.springframework.scheduling.quartz.CronTriggerFactoryBean">
		<property name="jobDetail" ref="paymentOrderJobDetail" />
		<!-- 每一分钟执行一次 -->
		<property name="cronExpression" value="0 0/1 * * * ?" />
	</bean>
	
	<!-- 定义调度器 -->
	<bean class="org.springframework.scheduling.quartz.SchedulerFactoryBean">
	    <property name="triggers">
	        <list>
	            <ref bean="cronTrigger" />
	        </list>
	    </property>
	</bean>

	public class PaymentOrderJob extends QuartzJobBean {

		@Override
		protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
			//获取ApplicationContext  ,:applicationContext 这个数据来源于配置信息,配置里面写的啥,这里就是啥
			ApplicationContext applicationContext = (ApplicationContext) context.getJobDetail().getJobDataMap().get("applicationContext");
			//业务代码

		}
	}