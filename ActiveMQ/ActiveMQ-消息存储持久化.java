----------------------------
ActiveMQ-消息存储持久化		|
----------------------------
	# ActiveMQ 不仅支持 PERSISTENT 和 NON_PERSISTENT 两种持久化方式,还支持消息的恢复 (recovery) 方式

	# PTP
		Queue 的存储是很简单的,就是一个 FIFO Queue

			in  --> FIFO Queue(队列) --->  out
	
	
	# PUB/SUB
		* 对于持久化订阅主题,每个消费者都会得到一个消息的复制

			   -->  out
			in -->  out
			   -->  out
	
	# 有效的消息存储
		ActiveMQ 提供了一个插件的消息存储,类似于消息的多点传播.主要实现了如下集中

		1,AMQ消息存储
			* 以前默认的存储方式,基于文件的存储方式,现在已经被KahaDB代替
			* 是一个基于文件,事务存储设计为快速消息存储的一个结构,该结构是以流的形式来进行消息交互的
			* 配置信息
				<persistenceAdapter>
					<!-- 默认的存储目录 -->
					<amqPersistenceAdaoter directory="${activemq.base}/data" maxFileLength="32MB"/>
				</persistenceAdapter>


		2,KahaDB消息存储
			* 提升了容量以及恢复能力.是现在的默认存储方式
			* 配置信息
				<persistenceAdapter>
					<!-- 默认的存储目录 -->
					<kahaDB directory="${activemq.data}/kahadb"/>
				</persistenceAdapter>
			* 可用属性有
				directory				:指定数据文件的存储地方
				indexWriteBatchSize		:批量写入磁盘的索引page数量,默认值1000
				indexCacheSize			:内存中缓存索引page的数量,默认值10000
				enableIndexWriteAsync	:是否异步写出索引,默认 false
				journalMaxFileLength	:设置每个消息data log的大小,默认 32MB
				enableJournalDiskSyncs	:设置是否保证每个每一个,没有事务的内容都被写入磁盘.JMS持久化的时候需要.默认为 true
				cleanupInterval			:隔多久后,删除不再使用的消息.默认值 30000
				checkpointInterval		:checkpint的间隔时间,默认5000
				... 还有很多,用的时候再找

		3,JDBC
			* 有意思,通过JDBC村粗到数据库
			* 需要预定义表
			* 配置
				<persistenceAdapter>
					<!-- 配置数据源bean ,并且每次启动都自动的创建表-->
					<jdbcPersistenceAdapter dataSource="#mysql-ds" createTablesOnStartup="true" />
				</persistenceAdapter>
				<!--定义mysql数据源bean,使用垃圾的dbcp连接池.这个是定义在 broker标签之外的-->
				<bean id="mysql-ds" class="org.apache.commons.dbcp.BasicDataSource" destroy-method="close">
					<property name="driverClassName" value="com.mysql.jdbc.Driver"/>
					<property name="url" value="jdbc:mysql://localhost/activemq?relaxAutoCommit=true"/>
					<property name="username" value="activemq"/>
					<property name="password" value="activemq"/>
					<property name="poolPreparedStatements" value="true"/>
				</bean>
			
			* 注意,需要把JDBC驱动包,复制到activemq的lib目录下

		
		4,JDBC & Journal
			* 双剑合璧,客服了JDBC的不足,使用快速的缓存写入技术,大大提高了性能.
			* 配置信息
				<beans>
					<broker>
						<persistenceFactory>
							<journalPersistenceAdapterFactory
								journalLogFiles="4",
								journalLogFileSize="32768"
								useJournal="true"
								useQuickJournal="true"
								dataSource="#mysql-ds"
								dataDirectory="activemq-data"
							/>
						</persistenceFactory>
					</broker>
				</beans>
			* 注意,需要注释掉 <persistenceAdapter></persistenceAdapter>,不需要了
			* 这种方式的性能要高于jdbc
			* JDBC可以用于MASTER/SLAVE读写分类的数据库架构模式,而这个不能
			* 一般情况下,用这个

		5,Memory
			* 基于内存的消息存储.速度快,就是不安全
			* 需要的时候,去查
			