————————————————————————
一,Spring 中事务管理	|
————————————————————————
	> 事务的特性,不想再重复!什么原子性,不可分割性之类的,谁讲事务,都是拿转账来比喻的!
		* ACID 
		* 原子性,一致性,隔离性,持久性
	> 此时你应该想到一个东西!切面编程!
		开启事务-前置通知
		执行操作-目标方法
		提交事务-后置通知
		回滚事务-异常通知
	* 使用SpringAOP的去处理事务的原理
		> spring既支持编程事务管理,又支持声明式事务管理
	* 编程式事务管理
		> 把事务的管理代码嵌入到业务方法中来控制事务的提交和回滚,这种方法管理事务,
		> 必须在每个事务操作中包含额外的事务管理代码
	* 声明式事务管理(推荐使用)
		> 把事务管理代码,从业务方法中抽离出来,以声明的方法来实现事务管理,
		> 事务管理作为一种横切关注点!可以通过AOP方法模块化
		> spring 框架通过SpringAOP框架支持声明式事务管理
	> spring,的事务管理抽象是:'DataSourceTransactionManager'
	> 事务管理器以普通Bean形式声明在SpringIOC容器中
————————————————————————
二,Spring 注解配置事务	|
————————————————————————
	1,配置事务管理器,就像配置普通的bean一样
		* DataSourceTransactionManager 接口
		* org.springframework.jdbc.datasource.DataSourceTransactionManager
		<bean id="trans" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
			<property name="dataSource" ref="datasource"/>	<!--  该接口中一个dataSource属性,需要传递一个数据源c3p0的bean的id -->
		</bean>
	2,开启事务管理器
		<tx:annotation-driven transaction-manager="trans"></tx:annotation-driven>
			* transaction-manager:该属性的值就是,某个事务管理器的id!
	3,在需要用到事务的方法上进行标注注解
		@Transactional
		* xml中正确的进行了配置,那么被该注解标识的方法就是一个带事务的方法,出现异常就会回滚
['事务的传播属性']
		> 当事务方法被另一个事务方法调用的时候,必须指定事务应该如何传播.
		> 例如:方法可能继续在现有事务中运行,也可能开启一个新事务,并在自己的事务中运行
		* 说白了就是,事务嵌套了.套里面的那个事务怎么办?就是行为
		> 事务的传播行为可以由传播属性指定,Spring定义了7种类形的传播行为
			Propagation.REQUIRED[常用+默认]
				> 如果有事务在运行,当前方法就在这个事务内运行,否则,就启动一个新的事务.并在自己的事务内运行
				* 我被你拉进来了,你有事务?,行,我不用我的了,我就用你的!一起嗨!
				* 举个栗子
					> 10 块钱一本的书,你有 13 块钱!你在同一个方法内分两次购买,如果是这个属性的话,那么你一本儿也买不成!直接告诉你余额不足
			Propagation.REQUIRED_NEW[常用]
				> 当前方法必须启动新事物,并在它自己的事务内运行,如果有事务在运行,应该将它挂起
				* 我被你拉进来了,你也有事务,对不起,你别动!等我自己的事务跑完,你在运行你的事务!
				* 举个栗子
					> 10 块钱一本的书,你有 13 块钱!你在同一个方法内分两次购买,如果是这个属性的话,你就剩3块钱,书也少了一本!因为第一次购买没问题!第二次购买的话,出现了异常,3块钱还你,书放回去!
			Propagation.SUPPORTS
				> 如果有事务在运行,当前的方法就在这个事务内运行!否则它可以不运行在事务中
			Propagation.NOT_SUPPORTED
				> 当前方法不应该运行在事务中,如果有运行的事务,将它挂起
			Propagation.MANDATORY
				> 当前的方法必须运行在事务内部,如果没有正在运行的事务,就抛出异常
			Propagation.NEVER
				> 当前的方法不应该运行在事务中,如果有运行的事务,就抛出异常
			Propagation.NESTED
				> 如果有事务在运行,当前的方法就应该在这个事务的嵌套事务内运行,否则就启动一个新的事务,并在它自己的事务内运行
		> 在注解中添加属性:propagation,值就是上面的其中一个咯!Enum,你懂的!
		@Transactional(propagation=Propagation.NOT_SUPPORTED)
			
['事务的隔离级别']
		> 事务并发时候,也许会出现问题
		> 当同一个程序,或者不同应用程序中的多个事务在同一个数据上并发执行时,可能会出现许多意外的问题
		* 脏读
			> 对于两个事物.t1,t2!t1,读取了已经被t2更新,但是没有提交的数据!如果t2回滚,那么t1读取的就是垃圾数据!临时,无效的数据
		* 不可重复读
			> 对于两个事物,t1,t2!t1读取了一个字段,然后t2更改了该字段,之后t1再次读取同一个字段,值就不同了
		* 幻读
			> 对于两个事务,t1,t2!t1从一个表中读取了一个字段,然后t2在该表中插入了一些新的行.之后如果t1再次读取同一个表,就会多出几行
		
		> 好吧,还是事务注解 @Transactional 的一个属性来确定的:isolation
		> 这个属性也是一个 Enum
		* Isolation.DEFAULT
		* Isolation.READ_COMMITTED 
			> 读已提交	'最常用'
		* Isolation.READ_UNCOMMITTED
			> 读未提交
		* Isolation.REPEATABLE_READ
			> 可重复读
		* Isolation.SERIALIZABLE
			> 序列化,排着队来!根本就没并发访问执行这个说法了,效率最低!千万别用
		> @Transactional(isolation=Isolation.DEFAULT)
	
['只回滚指定异常事务']
	> 通常我们没必要修改它
	> 就是事务里面出现了指定的异常,执行回滚,或者不回滚
	> 默认的是对所有的运行时异常进行回滚.我们可以修改它
	* 只对指定的异常进行回滚
	* 对指定的异常不进行回滚
	> 还是事务注解 @Transactional 上的属性来控制(有如下四个属性)
		* 指定异常才回滚(二选一就好)
		rollbackFor={Exception.class,XXXXException.class...}
		rollbackForClassName={"异常类全限定名","异常类"...}
		* 对指定异常不进行回滚(二选一就好)
		noRollbackFor=同上
		noRollbackForClassName=同上
	> 四种定义方式其实只有两种!要么写异常类的全限定名!要不然就直接把异常的Class类写在里面!
	> 都是一个集合,可以无限装的!

['事务的只读属性']
	> 还是事务注解 @Transactional 的一个属性 readOnly,值是一个 boolean 形变量
	> 如果为true,那就是只能对数据库进行读,不能改!如果事务中出现update,那么就会抛出异常
	> 这个东西吧,能对数据库的引擎得到优化,毕竟不用写,那么效率更高
	> 如果方法,真的只是读取!那么应该设置这个属性,提升性能!小小的提升也是提升啊!
	> @Transactional(readOnly=false)

['强制回滚时间值']
	> 这个东西吧,就是给你设置一个时间!如果你的事务超过了这个时间还没提交!那么我就强制的执行回滚
	> 缩短事务占用的时间,可以提升性能!哈哈哈
	> 很显然,还是事务注解 @Transactional 的一个属性 timeout
	> 后面的值,是以秒为单位
	@Transactional(timeout=60)

————————————————————————
三,Spring XML配置事务	|
————————————————————————
	> 这xml配置事务吧,有点不好理解.较为复杂
	> 使用的时候,先把下面整个复制进去,慢慢修改
<!-- 配置事务管理器
	id:是名称,自定义不多说
	class:这东西不变是死的
-->
<bean id="trans" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
	<!-- 
		name:后面是一个死的属性也不用做修改
		ref:指向本xml中配置的数据库源的id(c3p0)
	-->
	<property name="dataSource" ref="datasource"/>
</bean>
<!-- 配置事务属性 
	id:不解释
	transaction-manager:表示,这里面配置的属性是对哪个事务管理器生效啊？
-->	
<tx:advice id="txAdvice" transaction-manager="trans">
	<tx:attributes>
		<!-- 根据方法,指定事务的属性
			name:指定方法的名称,可以使用通配符,这里给个建议,就是凡事get/find开头的都是查询方法!都设置为只读,那么就提升了效率
			后面还可以衔接多个属性(具体看第四章)
		-->
		<tx:method name="get*" read-only="true"/>
		<tx:method name="find*" read-only="true"/>
		<tx:method name="*" />					<!--其他方法使用默认事务策略 -->
	</tx:attributes>
</tx:advice>
<!-- 配置事务切入点,以及把事务切入点和事务属性进行管理
	其实就是指定要'执行事务的方法',指定使用'哪个事务属性
-->
<aop:config>
	<!--
		expression:就是指定方法了,这个AOP里面有详细说,不多讲!可以配置N多个
		id:别重复就好
	-->
	<aop:pointcut expression="execution(void com.kevin.spring.tx.BookShopService.purchase(String, String))" id="pointcut"/>
	<aop:pointcut expression="..." id="..."/>
	<!--
		这里就是'指定一个事务属性',给'指定的方法'!
	-->
	<aop:advisor advice-ref="txAdvice" pointcut-ref="pointcut"/>
	
	<!--
		可以简写
	-->
	<aop:advisor advice-ref="txAdvice" pointcut=""/>
</aop:config>
----------------------------------结构体系
事务管理器bean配置<bean>
	> 主要就是配置好Class文件的地址(死的),指定数据源(一般就是配置的数据库连接池bean的id)
	> 也就是'注册事务管理器类','指定数据源'
事务属性<tx:advice>(依赖事务管理器)
	> 这个事务属性,约束在哪个事务管理器上,也就是'添加事务管理器'
	> 里面配置事务的各种属性,传播属性,隔离级别,只读....也就是'添加事务属性'
	> 针对方法名称进行约束,或者说直接用通配符*代表约束全部
事务切入点<aop:config>(依赖事务属性)
	> 在里面写明白哪些方法需添加事务,也就是'添加方法'
	> 然后指定哪些方法使用哪个事务属性,也就是'对指定的方法,添加事务属性的约束‘



————————————————————————
四,Spring 事务注解的属性|
————————————————————————
'这里仅仅是做了一个简单的总结,属性的具体信息,请参阅上面'
注解
	@Transactional(属性=)
属性
propagation
	> 事务的传播属性,值是一个类中的静态字段
isolation
	> 事务隔离级别,值也是一个类中的静态字段
rollbackFor
rollbackForClassName
	> 只回滚指定异常,值可以使字符串,也可以是类类型(XX.class)
noRollbackFor
rollbackForClassName
	> 对指定异常不进行回滚,同上
readOnly
	> 只读属性,值是 boolean 值
timeout
	> 强制回滚,也就是超时!值是int,单位是秒



————————————————————————
五,值得注意的问题		|
————————————————————————
	Spring 事务管理高层抽象主要包括
	PlatformTransactionManager
		* 平台事务管理器
		* commit(TransactionStatus status);					//提交
		* getTransaction(TransactionDefinition definition);	//获取事物
		* rollback(TransactionStatus status);				//回滚
	TransactionDefinition
		* 事务定义信息
		* 主要就是一大堆的常量,用来配置事务属性
		* Isolation.XXX			//设置事务的隔离级别
		* Propagation.XXX		//事务的传播行为,并不是JDBC的东西.而是为了解决实际开发中的问题
	TransactionStatus
		* 事务具体运行状态

	
	对于不同的持久层应用,Spring 提供了不同的'PlatformTransactionManager'事务管理接口的实现
	JDBC		-->		DataSourceTransactionManager		//同样还适合MyBatis
	Hibernate	-->		HibernateTransactionManager			//适合Hibernate3.0
	JAP			-->		..




	原始的声明式事务(JDBC)
		1,注册DataSource数据源
		2,注册JDBC事务管理器,注入DataSource数据源
		3,注册Service实现类
		4,注册 'TransactionProxyFactoryBean',在这里面注入事务管理器,以及Service以及声明事务属性
		<bean id="trans" class="org.springframework.transaction.interceptor.TransactionProxyFactoryBean">
			<!-- 对应的事务管理器 -->
			<property name="transactionManager" ref=""/>
			<!-- 需要被事务处理的ServiceBean 也就是目标对象-->
			<property name="target" ref=""/>
			<property name="transactionAttributes">
				<props>
					<!-- 设置事务隔离级别,传播属性等(key为方法名称) -->
					<prop key="*">ISOLATION.READ_COMMITTED </prop>
				</props>
			</property>
		</bean>
