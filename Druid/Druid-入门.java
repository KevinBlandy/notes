------------------------
Druid入门				|
-----------------------	
	* 首先,这东西是一个连接池,实现了 DataSource 
	* 来自于阿里巴巴集团,开源,免费
	* 它的口号就是,为监控而生的数据库连接池
------------------------
spring配置				|
-----------------------	
	<bean id="druidDataSource" class="com.alibaba.druid.pool.DruidDataSource" init-method="init" destroy-method="close">
		<property name="driverClassName" value="${jdbc.driverClass}"/>
		<property name="url" value="${jdbc.jdbcUrl}"/>
		<property name="username" value="${jdbc.user}"/>
		<property name="password" value="${jdbc.password}"/>
		 <!-- 配置监控统计拦截的filters -->
		<property name="filters" value="stat"/>
		<!-- 最大连接数 -->
		<property name="maxActive" value="20"/>
		<!-- 初始化连接 -->
		<property name="initialSize" value="5"/>
	</bean>

	* 不必多说,熟悉连接池,就应该懂


	* 接下来就说一下Drui的牛逼之处
	* 连接监控
		1,在web.xml中配置一个Servlet
			<!-- Druid监控 -->
			<servlet>
				<servlet-name>Druid</servlet-name>
				<servlet-class>com.alibaba.druid.support.http.StatViewServlet</servlet-class>
				<!-- 用户名 -->
				<init-param>
					<param-name>loginUsername</param-name>
					<param-value>admin</param-value>
				</init-param>
				<!-- 密码 -->
				<init-param>
					<param-name>loginPassword</param-name>
					<param-value>admin</param-value>
				</init-param>	
			</servlet>
			<!-- Druid监控 -->
			<servlet-mapping>
				<servlet-name>Druid</servlet-name>
				<url-pattern>/druid/*</url-pattern>							*/
			</servlet-mapping>

			<!-- WEB URI 监控 -->
			<filter>
				<filter-name>DruidWebStatFilter</filter-name>
				<filter-class>com.alibaba.druid.support.http.WebStatFilter</filter-class>
				<init-param>
					<!-- 一些排除在外 -->
					<param-name>exclusions</param-name>
					<param-value>*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*</param-value>
				</init-param>
			</filter>
			<filter-mapping>
				<filter-name>DruidWebStatFilter</filter-name>
				<url-pattern>/*</url-pattern>																	*/
			</filter-mapping>

		2,通过浏览器访问
			http://localhost:8080/kevinblandy/druid/index.html

		

		*　能够看到所有的数据监控
		



实际商业化项目的配置:

<!-- 配置数据源(连接池，druid) -->
<bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource" init-method="init" destroy-method="close">
	<!-- 基本属性 url、user、password -->
	<property name="url" value="${db.url}"/>
	<property name="username" value="${db.username}"/>
	<property name="password" value="${db.password}"/>

	<!-- 配置初始化大小、最小、最大 -->
	<property name="initialSize" value="${db.pool.initialSize}"/>
	<property name="minIdle" value="${db.pool.minIdle}"/>
	<property name="maxActive" value="${db.pool.maxActive}"/>

	<!-- 配置获取连接等待超时的时间 -->
	<property name="maxWait" value="60000"/>

	<!-- 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒 -->
	<property name="timeBetweenEvictionRunsMillis" value="60000"/>

	<!-- 配置一个连接在池中最小生存的时间，单位是毫秒 -->
	<property name="minEvictableIdleTimeMillis" value="300000"/>

	<property name="validationQuery" value="${db.validation}"/>
	<property name="testWhileIdle" value="true"/>
	<property name="testOnBorrow" value="false"/>
	<property name="testOnReturn" value="false"/>

	<!-- 打开PSCache，并且指定每个连接上PSCache的大小（Oracle使用） 
	<property name="poolPreparedStatements" value="true" /> 
	<property name="maxPoolPreparedStatementPerConnectionSize"
	value="20" /> -->
	
	<!-- 配置监控统计拦截的filters,wall:防御SQL注入攻击 -->
	<property name="filters" value="stat,wall" />

	<!-- 慢SQL记录 -->
	<property name="connectionProperties" value="druid.stat.slowSqlMillis=5000" />

</bean>


	<!-- 配置_Druid和Spring关联监控配置 -->
	<bean id="druid-stat-interceptor" class="com.alibaba.druid.support.spring.stat.DruidStatInterceptor"></bean>


------------------------
Durid-加密				|
-----------------------	
	1,执行加密,得到私钥,公钥,加密后的密文
		java -cp druid-1.1.10.jar com.alibaba.druid.filter.config.ConfigTools [密码]
	
	2,配置
		spring:
		  datasource
		    password: aAO2o86uTLOgw42z6xWJYb8Ta4F7OR2LzKnqcO46dYR5fccPIpuxLdvCznH1+MXRadtxsnmbCK4Masu8zE3QGg==
		    public-key: MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAI5cG4UDYuGP7eljYApw4czFoORmMMCgN1JUs4GgAdcXS8130FbaUzK8Of2JvK1oT778EdmnBJaRAGEv/9vH0gMCAwEAAQ==

		    filters: config
		    connectionProperties: config.decrypt=true;config.decrypt.key=${spring.datasource.public-key}