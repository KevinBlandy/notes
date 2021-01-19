---------------------------
spring-quartz 任务调度系统|
---------------------------
	# Scheduler --> Trigger --> JobDetail --> bean
	# 关系
		Scheduler
			--> 负责注册,N个trigger
		Trigger
			--> 负责注册jobdetail,配置表达式
		JobDetail
			--> 负责注册执行类,以及执行的方法
		bean
			--> 负责执行
		
			

	# Scheduler	配置			
		<bean id="myScheduler" class="org.springframework.scheduling.quartz.SchedulerFactoryBean">
			<property name="triggers">
				<list>
					<!-- 指定任务 trigger -->
					<ref bean="myTriggersA"></ref>
					<ref bean="myTriggersB"></ref>
				</list>
			</property>
			<!-- 是否自动执行 -->
			<property name="autoStartup" value="true"/>
		</bean>

	# Trigger	配置				
		<bean id="myTriggersA" class="org.springframework.scheduling.quartz.CronTriggerFactoryBean">
			<!-- 指定 JobDetail -->
			<property name="jobDetail" ref="myJobDetailA"/>
			<!-- 配置执行表达式 -->
			<property name="cronExpression">
				<value>0/1 * * * * ?</value>
			</property>
		</bean>

	# JobDetail	配置			

		<bean id="myJobDetailA" class="org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean">
			<!-- 指定业务类 -->
			<property name="targetObject" ref="myJobA" />
			<!--指定执行任务的方法名 -->
			<property name="targetMethod" value="work" />
			<!-- 是否允许任务并发执行。当值为false时，表示必须等到前一个线程处理完毕后才再启一个新的线程 -->
			<property name="concurrent" value="false" />
		</bean>

	# bean	配置				
		<bean id="myJobA" class="com.quartz.MyJobA"/>

		# 业务类不需要继承任何父类，也不需要实现任何接口，只是一个普通的java类。



	# spring-quartz 任务调度系统|
					Scheduler 
						|
		Trigger		Trigger		Trigger
			|			|			|
		JobDetail	JobDetail	JobDetail
			|			|			|
		  bean		  bean		  bean


