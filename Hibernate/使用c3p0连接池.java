————————————————————————————
一,使用c3p0数据库连接池		|
————————————————————————————

	这东西,你已经学过了！JAVAEE/MYSQL笔记里面有!
	在Hibernate,是支持c3p0数据库连接池的!在Hibernate中其实默认使用的不是c3p0,是自己的一个很简单的连接池!所以在项目上线的时候我们一般都会更换成c3p0


	在hibernate-cfg.xml中添加如下配置
		<!-- c3p0连接池配置 -->
		<!-- 使用c3p0连接池,配置连接池提供的供应商 -->		
		<property name="hibernate.connection.provider_class">org.hibernate.connection.C3P0ConnectionProvider</property>
		<!-- 设置连接池中可用数据库连接的最小数目 -->
		<property name="c3p0.min_size">5</property>
		<!-- 设置连接池中可用数据库连接的最大数目 -->
		<property name="c3p0.max_size">20</property>
		<!-- 设置数据库连接连接的超时时间,单位是秒,如果超时就断开连接 -->
		<property name="c3p0.timeout">120</property>
		<!-- 设置,检查连接池中所有空闲链接,的间隔时间,单位是秒 -->
		<property name="c3p0.idle_test_period"></property>


	记得导入相关的jar包
		lib\optional\c3p0目录下的所有jar包
		c3p0-0.9.2.1.jar
		hibernate-c3p0-5.0.5.Final.jar
		mchange-commons-java-0.2.3.4.jar


  
	

	也可以用 Driud,用谁！！谁就必须要提供给Hibernate的整合包