------------------------
整合Servlet				|
------------------------
	# 两种方式

------------------------
1, 添加监听器			|
------------------------
	<listener>
		<listener-class>org.quartz.ee.servlet.QuartzInitializerListener</listener-class>
	</listener>

	<context-param>
		<param-name>quartz:config-file</param-name>
		<param-value>/some/path/my_quartz.properties</param-value>
	</context-param>
	<context-param>
		<param-name>quartz:shutdown-on-unload</param-name>
		<param-value>true</param-value>
	</context-param>
	<context-param>
		<param-name>quartz:wait-on-shutdown</param-name>
		<param-value>false</param-value>
	</context-param>
	<context-param>
		<param-name>quartz:start-scheduler-on-load</param-name>
		<param-value>true</param-value>
	</context-param>

------------------------
2, 添加Servlet			|
------------------------
	<servlet>
		<servlet-name>QuartzInitializer</servlet-name>
		<servlet-class>org.quartz.ee.servlet.QuartzInitializerServlet</servlet-class>
		<init-param>
			<param-name>shutdown-on-unload</param-name>
			<param-value>true</param-value>
		</init-param>
		<load-on-startup>2</load-on-startup>
	</servlet>

	