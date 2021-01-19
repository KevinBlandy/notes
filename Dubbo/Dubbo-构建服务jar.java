------------------------------------
Dubbo-使用Maven构建服务jar			|
------------------------------------
	# Dubbo的运行方式
		1,使用Servlet容器(Tomcat,Jetty)运行
			* 不建议
			* 增加复杂性(端口,管理),浪费资源
		
		2,自建Main方法类来运行(加载Spring容器)
			* 不建议(本地测试可取)
			* Dubbo本身提供的高级特性没用上
			* 自己编写的启动类有可能会有缺陷
		
		3,使用Dubbo框架提供的Main方法类来运行(加载Spring容器)
			* 建议
			* com.alibaba.dubbo.container.Main
			* 由Dubbo框架本身提供,可以实现优雅关机(shutdownHook)
		
	
	# 构建配置
		* build.xml 笔记详解
	
------------------------------------
Dubbo-可运行jar配置					|
------------------------------------
	# 可运行jar的一些配置,可以在 classpath 下创建一个 dubbo.properties文件,来进行配置
	
		dubbo.container=spring,jetty,log4j							----配置Dubbo启动加载的容器
		dubbo.spring.config=classpath*:META-INF/spring/spring.xml	----配置spring配置加载位置,可以使用通配符 *.xml,来表示加载所有xml配置文件												
		dubbo.jetty.port=8080										----配置jetty启动端口
		dubbo.jetty.directory=/foo/bar								----配置可通过jetty直接访问的目录，用于存放静态文件
		dubbo.jetty.page=log,status,system							----配置显示的页面，缺省加载所有页面
		dubbo.log4j.file=/foo/bar.log								----配置日志文件路径
		dubbo.log4j.level=WARN										----配置日志级别
		dubbo.log4j.subdirectory=20880								----配置日志子目录，用于多进程启动，避免冲突


	1,容器加载篇
		# 容器类型
			Spring Container(缺省)
				# 不添加任何参数,则默认加载spring,自动加载 META-INF/spring下所有xml文件
				自动加载META-INF/spring目录下的所有Spring配置。
				配置：(配在java命令-D参数或者dubbo.properties中)
				dubbo.spring.config=classpath*:META-INF/spring/*.xml ----配置spring配置加载位置				*/

			Jetty Container
				启动一个内嵌Jetty，用于汇报状态。
				配置：(配在java命令-D参数或者dubbo.properties中)
				dubbo.jetty.port=8080				----配置jetty启动端口
				dubbo.jetty.directory=/foo/bar		----配置可通过jetty直接访问的目录，用于存放静态文件
				dubbo.jetty.page=log,status,system	----配置显示的页面，缺省加载所有页面

			Log4j Container
				自动配置log4j的配置，在多进程启动时，自动给日志文件按进程分目录。
				配置：(配在java命令-D参数或者dubbo.properties中)
				dubbo.log4j.file=/foo/bar.log		----配置日志文件路径
				dubbo.log4j.level=WARN				----配置日志级别
				dubbo.log4j.subdirectory=20880		----配置日志子目录，用于多进程启动，避免冲突

		# 通过启动参数,指定要加载的容器
			java -jar dubbo-provider.jar spring jetty log4j
		
		# 通过 -D (JVM参数)参数,添加容器
			java -jar dubbo-provider.jar -Ddubbo.container=spring,jetty,log4j

		# 通过配置文件来加载容器
			dubbo.container=spring,jetty,log4j
		


	2,覆盖策略

		JVM启动-D参数优先，这样可以使用户在部署和启动时进行参数重写，比如在启动时需改变协议的端口。
		XML次之，如果在XML中有配置，则dubbo.properties中的相应配置项无效。
		Properties最后，相当于缺省值，只有XML没有配置时，dubbo.properties的相应配置项才会生效，通常用于共享公共配置，比如应用名。
	
	3,常用命令


------------------------------------
Dubbo-手动部署jar维护				|
------------------------------------
	1,环境变量配置
		* 不讲

	2,部署目录规范
		
		
	3,手工维护dubbo服务
		* 启动
			jar -jar xxx.jar &
			jar -jar xxx.jar && tail -f ./provider.log

		* 优雅关机
			kill PID
		
		* 暴力关机
			kill -9 PID

	4,自定义dubbo服务维护的shell脚本
		* 脚本命名规范
			/usr/local/service/xxx/service-xxx.sh
			
			/usr/local/service/user/service-user.sh
		
		* 效果
			./service-xxx.sh start
			./service-xxx.sh stop
			./service-xxx.sh restart

	5,维护注意事项
			
