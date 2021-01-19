————————————————————————
一,Spring 整合Hibernate	|
————————————————————————
1,搞清楚一个问题,Spring  整合Hibernate,到底是整合什么?
	> 由 Spring 的IOC容器去生成 Hibernate的SessionFactory!
	> 让Hibernate 使用上,Spring 的声明式事务!

————————————————————————
二,整合步骤				|
————————————————————————
	> 总感觉这种笔记有点弱智啊!MyGod!
一,Hibernate准备工作
	1,添加jar包(我千万不能因为忘记了怎么整合来看这个笔记)
	2,配置hibernate.cfg.xml文件
		> 其实在Spring很Hibernate进行整合的时候,这个配置文件是完全可以拿掉的!
		> 因为数据源要配置到IOC容器当中,所有Hibernate配置文件中不再配置数据源
		> 实体映射文件(..hbm.xml),也可以在IOC容器配置SessionFactory实例时进行配置
		> 如果你真的想在Hibernate.cfg.xml中配置点什么的话,可以配置点基本属性(注意删除掉session-factory的name="foo"的属性)
			* 方言
			* SQL显示
			* 格式化SQL显示
			* 自动建表策略
			* 二级缓存
			... ...
	3,编写持久化类的xml配置文件(..hbm.xml)
二,加入Spring
	1,添加jar包.
	2,配置Spring的配置文件
		* 其实配置这个配置文件,就是一个整合的过程
['重点']
	3,在IOC容器里面配置Hibernate的'SessionFactory'!
		> 通过Spring提供的'LocalSessionFactoryBean'进行配置
		> org.springframework.orm包下,找到跟自己使用的Hibernate版本相关的jar包---> 'LocalSessionFactoryBean'
--------------------------------------------------------------------------------------------------------------------------
<bean id="sessionFactory" class="org.springframework.orm.hibernate5.LocalSessionFactoryBean">							
	<property name="dataSource" ref="dataSource"/><!-- 配置数据源,也就是c3p0的配置 -->
	<!--
	<property name="configLocation" value="classpath:hibernate.cfg.xml"/>
	这个是导入Hibernate的配置文件 ,跟下面配置出现一个就好
	-->
	<property name="hibernateProperties"><!-- 设置Hibernate的属性,这个可以而替代掉上面'导入配置文件' -->
		<props>
			<prop key="hibernate.dialect">org.hibernate.dialect.MySQL5InnoDBDialect</prop><!-- 方言 -->
			<prop key="hibernate.show_sql">true</prop><!-- 显示SQL语句 -->
			<prop key="hibernate.format_sql">true</prop><!-- 格式化SQL语句 -->
			<prop key="hibernate.hbm2ddl.auto">update</prop><!-- 自动建表策略 -->
			<prop key="current_session_context_class">thread</prop><!-- 绑定线程 -->
		</props>
	</property>
	<property name="mappingLocations" value="classpath:com/kevin/entiti/*.hbm.xml"/><!-- 导入实体类映射文件,可以使用通配符 -->
</bean>
--------------------------------------------------------------------------------------------------------------------------
['重点']
	4,在IOC容器里面配置声明式事务
		> 通过spring提供的'HibernateTransactionManager'进行配置
		> org.springframework.orm包下,找到跟自己使用Hibernate版本相关的jar包--> 'HibernateTransactionManager'
'!!!'	> 特别需要注意的是,跟传统的事务管理器配置不一样!传统的是配置'dataSource',而Hibernate配置的是'sessionFactory'!
		> 一个是数据源,一个是工厂
--------------------------------------------------------------------------------------------------------------------------
<!-- 配置spring支持Hibernate的声明式事务管理器 -->
	<bean id="transactionManager" class="org.springframework.orm.hibernate5.HibernateTransactionManager">
		<property name="sessionFactory" ref="sessionFactory"/>
	</bean>
<!-- 配置事务属性 -->
	<tx:advice id="txAdvice" transaction-manager="transactionManager">
		<tx:attributes>
			<tx:method name="*"/>
			<tx:method name="get*" read-only="true"/>
			<tx:method name="find*" read-only="true"/>
		</tx:attributes>
	</tx:advice>
<!-- 配置切入点 -->
	<aop:config>
		<aop:pointcut expression="execution(void com.kevin.main.Demo.test())" id="test"/>
		<aop:advisor advice-ref="txAdvice" pointcut-ref="test"/>
	</aop:config>
--------------------------------------------------------------------------------------------------------------------------
	5,差不多就配置完成了!可以进行代码测试
	不要再Hibernate的配置中声明:<property name="current_session_context_class">thread</property>
	一配就挂.
————————————————————————
三,一些知识				|
————————————————————————
	1,Spring Hibernate 事务的流程
	* 在方法开始前
	①,获取Session
	②,把Session和当前线程绑定,这样就保证了在DAO中使用SessionFactory.getCurrentSession()来获取Session
	③,开启事务
		> 方法正常--提交事务--解除Session与线程的绑定--关闭Session
		> 方法异常--回滚事务--解除Session与线程的绑定--关闭Session
	

————————————————————————
四,提高程序健壮			|
————————————————————————
1,在DAO层中,就应该是Hibernate的主场,不应该再出现spring相关的类与对象,提高Hibernate的可移植性
	> 不要继承 HibernateDaoSupport 这些类!




————————————————————————
五,总结一下				|
————————————————————————	
	Hibernate整合有两种方法
		* 在spring配置文件中直接引入Hibernate配置文件
		* 直接把Hibernate的配置,作为类的属性配置到Spring配置文件中
	
	