————————————————————————
1,MyBatis-整合Spring框架|
————————————————————————
	1,整合思路
		* mapper,dao交由于spring进行管理
		* 需要Spring通过单例方式管理SessionFactory
		* spring和mybatis整合生成代理对象,使用SqlSessionFactory生成SqlSession(框架自动完成)
	
	2,加入整合jar包
		* 除了spring和mybatis的必须jar包外.需要额外的整合包
		mybatis-spring.x.x.x.jar
			* 早期和ibatis整合,是由spring提供整合包,现在升级到MyBatis,就是由MyBatis提供

	3,配置SqlSessionFactory
		<!-- SqlSessionFactory -->
		<bean id="sessionFactoy" class="org.mybatis.spring.SqlSessionFactoryBean">
			<!-- 配置文件地址 -->
			<property name="configLocation" value="classpath:mybatis/SqlMapConfig.xml"/>
			<!-- 数据源 -->
			<property name="dataSource" ref="dataSource"/>
		</bean>
	
	4,配置事务管理器
		<!-- 事务管理 -->
		<bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
			<!-- 数据源 -->
			<property name="dataSource" ref="dataSource"/>
		</bean>
		* 跟Hibernate不同,Hiernate使用的是专门的事务管理器,而且传递的是SessionFactory
		* MyBatis的事务管理器,其实就是JDBC的事务管理器,该事务管理器传递的是数据源

	5,配置事务属性
		<!-- 事务属性,事务属性需要一个事务管理器 -->
		<tx:advice id="txAdvice" transaction-manager="transactionManager">
			<tx:attributes>
				<tx:method name="get*" read-only="true" isolation="READ_COMMITTED" propagation="REQUIRED"/>
				<tx:method name="update*" isolation="READ_COMMITTED" propagation="REQUIRED"/>
				<tx:method name="save*" isolation="READ_COMMITTED" propagation="REQUIRED"/>
				<tx:method name="delete*" isolation="READ_COMMITTED" propagation="REQUIRED"/>
			</tx:attributes>
		</tx:advice>

	6,配置事务切面
		<aop:config >
			<!-- 需要使用事务的方法,允许通配符 -->
			<aop:pointcut expression="execution(* com.kevin.service.*.*(..))" id="service"/>
			<!-- 为指定的方法,配置指定的事务属性 -->
			<aop:advisor advice-ref="txAdvice" pointcut-ref="service"/>
		</aop:config>

————————————————————————
2,MyBatis-原始DAO开发	|
————————————————————————
	1,创建mapper.xml
	2,SqlMapConfig.xml中导入mapper.xml
	3,修改SqlMapConfig.xml中的一些配置
		* 删除数据源的配置
	4,DAO注入SqlSessionFactory
		* DAO可以去继承:SqlSessionDaoSupport类
		* 该类已经声明了一个SqlSessionFactory,并且提供了set方法.
		* 该类还提供了一个getSqlSession()的方法
	5,自己从DAO中获取SqlSessionFactory来创建:SqlSession进行操作
	
————————————————————————
3,MyBatis-mapper开发	|
————————————————————————
	1,创建mapper接口
	2,根据mapper代理开发规则创建mapper.xml
	3,SqlMapConfig.xml中导入mapper.xml
	4,把mapper接口交给spring管理,让spring来生成代理对象
		1,单个注册
			<!-- Mapper -->
			<bean id="mapper" class="org.mybatis.spring.mapper.MapperFactoryBean">
				<!-- 配置SqlSessionFactory -->
				<property name="sqlSessionFactory" ref="sessionFactoy"/>
				<!-- 配置mapper接口 -->
				<property name="mapperInterface" value="com.kevin.mapper.UserMapper"/>
			</bean>
			* 整合包中有一个工厂类,负责管理这些mapper接口:MapperFactoryBean
			* '获取代理对象Bean':id值

		2,批量注册
			<!-- mapper批量扫描器,-->
			<bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
				<!-- 指定扫描的包名 -->
				<property name="basePackage" value="com.kevin.dao"/>
				<!-- 指定SqlSessionFactory -->
				<property name="sqlSessionFactoryBeanName" value="sqlSessionFactory"/>
			</bean>
			* 不用指定id,让容器自动的去创建它就好,它会从mapper包中扫描出mapper接口, 自动的创建代理对象,并且在spring容器中注册
			* 要求,mapper配置文件跟 mapper接口在同一个目录下,且名称相同!而且,必须是符合mapper代理开发的规范
			* 指定SqlSessionFactory的时候,'不是用ref,而是value',要注意.是因为如果使用ref的话.会导致顶端的c3p0扫描失效
				* c3p0数据源应该是最先创建的对象.如果这里用了了ref,那么就会先创建这些.但是这些都必须依赖c3p0
				* 千万要切记,value,写的是spring配置文件中.SqlSessionFactory bean的id名称!不是ref的指向
			* 如果是这种方式配置的话,就不用在 mybatis 的配置文件中配置mapper.xml文件的路径了!
			* '获取代理对象bean':mapper接口类名,首字母小写.
			* '扫描多个包',不能使用通配符.每个包中间使用','逗号分隔

	5,在DAO层根据id获取指定的mapper代理bean就可以进行使用
	6,通过这种方式,把mapper代理Bean交给IOC容器去管理,那么就不用在SqlMapConfig中进行配置了
	
————————————————————————————————————
1,MyBatis与Spring整合总结			|
————————————————————————————————————
	1,整合后,不用去手动关闭SqlSessionFactory

	2,普通方式整合
		* SqlSessionFactory,就不用mybatis的了,用他们整合包的那个.在Sprin容器注册就OK
		* SqlSessionFactory,需要注入俩东西,一个是外部的配置文件,一个就是数据源
		* 哪里需要操作数据库.就把这个SqlSessionFactory注给他,然后自己去获取SqlSession操作数据就是了
	
	3,mapper代理方式整合
		1,单个mapper注册
			* 注册一个类:org.mybatis.spring.mapper.MapperFactoryBean
			* 它需要两个参数,
				* 符合mapper代理开发规范的接口全路径名
				* SqlSessionFactory
			* 然后,获取这个类的bean,其实是获取了指定接口的代理类.就可以直接操作数据库了
		2,批量扫描
			* 注册一个类:org.mybatis.spring.mapper.MapperScannerConfigurer
			* 不需要指明id,它仅仅只需要实例化就OK,让容器创建就好
			* 它也需要两个参数
				* 符合扫描规范mapper包地址(mapper接口与mapper.xml配置文件同名同位,且符合mapper代理开发规范)
				* SqlSessionFactory
					* 注意的是,这个属性并不是以ref注入进去的.而且以 vaue,写入SqlSessionFactory bean的id值.具体原因看上面
			* 指定包下符合规范的所有mapper代理对象,就被注册进了容器.他们的id是:简单类名小写
			* 扫描路径,不支持通配符,多个包用','逗号隔开.

	4,其实我觉得还有一种方式,mapper不交给Spring管理.也就是说不用spring来创建mapper的代理对象
			* 还是按照普通方式进行整合
			* 在DAO中使用SqlSession来创建mapper,代理对象.进行操作
	
	5,经过测试不论是第 1 种方式,还是第 4 种方式,spring都能准确的控制其事务
		* 但是,事务传播属性有点问题
		* 只要service出现异常就会回滚,但是设置:propagation.REQUIRED_NEW 传播属性不起作用
		* 两个操作,第一个操作没问题,但是第二个操作异常.结果连同第一个都一起回滚了.
	
	6,问题就是整合spring的时候使用,mapper代理开发的方式.DAO该怎么办?
		* 直接注入Mapper么?这好像有点秀逗,而且mapper是一个个单独的接口,这样就有点耦合了.每个DAO中有可能注入的mapper不一样
		* 注入SqlSessionFactory,自己获取SqlSession,再获取mapper接口?那,就没必要把mapper接口注册到容器了.?
	

	传统方式
		1,注册SqlSessionFactory
		2,注入DAO,自己获取session
			* 可以使用session获取mapper代理对象操作数据库
			* 也可以直接使用session来操作数据库

	mapper代理方式
		1,注册SqlSessionFactory
		2,注册mapper扫描
		3,可以把mapper直接注入到dao
			* mapper名称就是类名小写,所有,DAO的属性注入mapper的时候,整个xml文件是找不到mapper的,是运行生成
		


		
实际开发
	* mapper文件单独存放classpath目录下,并且起名带后缀
	* 可以扫描配置N个文件夹
		<bean id="sessionFactoy" class="org.mybatis.spring.SqlSessionFactoryBean">
			<property name="configLocation" value="classpath:mybatis/SqlMapConfig.xml"/>
			<property name="dataSource" ref="DruidDataSource"/>
			<property name="mapperLocations">
				<list>
					<!-- 
						mapper文件,扫描路径
						也可以配置为:
						classpath:mapper/**/*-mapper.xml
						* 意思就是,无视目录层级.只要是-mapper结尾的,一律加载
					
					-->
					<value>classpath:/mapper/*Mapper.xml</value>							*/
					<value>classpath:/mapper/*Mapper-etc.xml</value>						*/
				</list>
			</property>
		</bean>
	* dao接口,可以分开配置
	<!-- mapper -->
	<bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
		<property name="basePackage" value="com.kevin.comm.dao"/>
		<property name="sqlSessionFactoryBeanName" value="sessionFactoy"/>
		<!-- 仅仅加载指定注解标识的mapper(可选,不写的话默认加载basePackage下所有) -->
		<property name="annotationClass" value="com.zhiku.anywhere.persistence.repository.mybatis.MyBatisRepository"/>
	</bean>

