--------------------
MYSQL-JNDI			|
--------------------
	 # JNDI,JAVA命令和目录接口.JNDI的作用就是
	 # 在服务器上配置资源,然后通过统一的放弃来获取配置的资源
	 # 这里讲述的资源就是连接池.这样的话,项目中可以通过统一的方式来获取连接池对象


--------------------
MYSQL-JNDI，Tomcat	|
--------------------
	1,找到Tomcat下,conf目录下的context.xml文件中的 Context 标签
		* 对该文件进行操作,那么就是默认对服务器下所有的项目都进行了统一的配置
		* 其实没有这个必要,我们可以在:server.xml文件的的Host标签下创建一个 Context 标签,然后进行配置
		* 也可以在Tomcat\conf\Catalina\localhost目录下创建一个xml文件,名称跟项目名称相同(仅仅对当前项目有效).然后里面配置一个: Context 标签,来进行配置操作是可以的
		
	2,进行配置
		<Context>
			<Resource 
				name="jdbc/dataSource"							
				type="com.alibaba.druid.pool.DruidDataSource"
				factory="org.apacahe.naming.factory.BeanFactory"
				<!-- 
					以上三个,为必配的资源 
					下面的都是属于资源(DruidDataSource)的参数
				-->
				url="jdbc:mysql://localhost:3306/kevinblandy?useUnicode=true&characterEncoding=utf8"
				driverClassName="com.mysql.jdbc.Driver"
				username="root"
				password="root"
				maxActive="1000"/>
		</Context>
		# 配置JNDI资源需要到<Context>元素中配置<Resource>子元素
		[]name		:指定资源的名称,这个名称可以随意.在获取资源的时候就需要这个名称
						# 资源名称
		[]auth		:写不写无所谓,起码目前来说是这个样子的
		[]factory	:用来创建资源的工厂,这个值是固定的.一般不用改
						# 资源由谁来负责创建
		[]type		:资源的类型,我们这里给出的连接池的类型
						# 这里配置的是一个数据库连接池.Druid的连接池
		[]bar		:表示资源的属性,如果资源存在有bar属性.那么就在这里配置bar值.
					 对于Druid连接池而言,是不需要bar的.而是需要url,username,password等属性.
	
	3,获取资源
		配置资源的目的是为了获取资源.只要是启动了Tomcat,那么就可以在项目的任何类中通过JNDI获取资源的方式来获取资源了
		['DEMO']
			//首先创建上下文对象
			Context iniCtx = new InitialContext();
			//通过上下文对象查找,再次找到一个上下文对象--java:comp/env:该值是固定的,不能该
			Context envCtx = (Context)iniCtx.lookup("java:comp/env");
			//然后通过这个上下文,获取指定名称的资源
			DataSource dataSource = (DataSource)envCtx.lookup("jdbc/dataSource");

		# 上面的其实可以简写
		   DataSource dataSource = (DataSource)iniCtx.lookup("java:comp/env/jdbc/dataSource");
		   * 希望你能看懂.意思就是不必查询两次,直接一次搞定.把名字放一块儿

	4,Spring 中使用 JNDI 的资源
		# 第一种方式
			<bean id="dataSource" class="org.springframework.jndi.JndiObjectFactoryBean">
				<property name="jndiName">
					<value>java:comp/env/jdbc/dataSource</value>
				</property>
			</bean>
			# java:comp/env/jdbc/dataSource
			  java:comp/env/		很显然是死的
			  jdbc/dataSource	就是配置在xml的name,也就是资源名

			# 换汤不换药
			  <bean id="dataSource" class="org.springframework.jndi.JndiObjectFactoryBean">        
				<property name="jndiName" value="comp/env/jdbc/dataSource"/>        
			  </bean>  
			  * jndiName :这个name属性就是死的了
		
		# 第二种方式
			<jee:jndi-lookup id="dataSource" jndi-name="java:comp/env/jdbc/dataSource"/>

			* 这个就简单明了了

		