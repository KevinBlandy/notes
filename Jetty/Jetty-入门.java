------------------------------
Jetty-入门					  |
------------------------------
	# Jetty 这个项目成立于 1995 年
	# 现在已经有非常多的成功产品基于 Jetty
		* Apache Geromino， JBoss， IBM Tivoli， Cisco SESM

	# 学习站点
		http://www.ibm.com/developerworks/cn/web/wa-lo-jetty/
		http://www.cnblogs.com/yiwangzhibujian/p/5832597.html
	
	# 易用
		* 易用性是Jetty的设计原则,易用性主要提现在一下几个方面
		1,通过 XML 或者 API 来对 Jetty 进行配置；
		2,默认配置可以满足大部分的需求；
		3,将 Jetty 嵌入到应用程序当中只需要非常少的代码；
	# 可扩展
		* 在使用了 Ajax 的 Web 2.0 的应用程序中，每个连接需要保持更长的时间，这样线程和内存的消耗量会急剧的增加。这就使得我们担心整个程序会因为单个组件陷入瓶颈而影响整个程序的性能。但是有了 Jetty：
		* 即使在有大量服务请求的情况下，系统的性能也能保持在一个可以接受的状态。
		* 利用 Continuation 机制来处理大量的用户请求以及时间比较长的连接。
		* 另外 Jetty 设计了非常良好的接口，因此在 Jetty 的某种实现无法满足用户的需要时，用户可以非常方便地对 Jetty 的某些实现进行修改，使得 Jetty 适用于特殊的应用程序的需求。
	# 易嵌入
		* Jetty 设计之初就是作为一个优秀的组件来设计的
		* 这也就意味着 Jetty 可以非常容易的嵌入到应用程序当中而不需要程序为了使用 Jetty 做修改。
		* 从某种程度上，你也可以把 Jetty 理解为一个嵌入式的Web服务器。
	
	# 部署应用程序
		* 将自己的应用程序部署到 Jetty 上面是非常简单的
		* 首先将开发好的应用程序打成 WAR 包放到 Jetty 的 Webapps 目录下面。
		* 然后用如下的命令来启动 Jetty 服务器:
			Java –jar start.jar
		* 在启动服务器后。我们就可以访问我们的应用程序了
		* Jetty 的默认端口是 8080
		* WAR 的名字也就是我们的应用程序的 Root Context
		* 例如一个典型的 URL 就是：http://127.0.0.1:8080/sample/index.jsp 。
	
	# 默认端口
		* 8080
		配置属性信息通过如下方式获得
		1,首先去start.d/http.ini文件中找jetty.http.port=8080配置信息，若找到配置端口即为指定端口
		2,根据模块modules/http.mod文件找到指定配置默认为etc/jetty-http.xml，在此配置文件中有jetty.http.port配置信息，若找到即为此端口配置
		3,若上面两个都没有，则随机一个没有使用的端口

	# 目录介绍
		bin
			* Linux 系统下运行的脚本 
		demo-base
			* 一个可运行包含示例的web应用的Jetty服务器基目录
		etc
			* Jetty配置文件目录
		lib
			* Jetty运所必须的jar
		logs
			* 日志
		modules
			* 各个模块
		resources
			* 包含新增到classpath配置文件夹，如log4j.properties
		webapps
			* 个用来存放运行在默认配置下的Jetty Web应用目录
		license-eplv10-aslv20.html
			* Jetty许可文件
		notice.html
			* 许可信息等
		start.ini
			* 存放启动信息
		start.jar
			* 运行Jetty的jar
		VERSION.txt
			* 版本信息


