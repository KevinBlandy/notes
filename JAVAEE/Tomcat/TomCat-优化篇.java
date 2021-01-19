----------------------------
Tomcat-优化					|
----------------------------
	# 针对于Tomcat7


----------------------------
Tomcat-配置管理账户			|
----------------------------
	# 在 conf/tomcat-users.xml中添加用户
		<role rolename="manager"/>
		<role rolename="manager-gui"/>
		<role rolename="manager-script"/>
		<role rolename="admin"/>
		<role rolename="admin-gui"/>
		<user username="tomcat" password="a12551255" roles="manager,manager-script,manager-gui,admin,admin-gui"/>
	
	
	# 权限说明
		manager
		manager-gui
		manager-script
		manager-jmx
		manager-jax
		manager-status
		admin
		admin-gui


----------------------------
Tomcat-运行模式				|
----------------------------
	# tomcat提供三种运行模式
	1,bio
		* 默认的模式,性能非常低下,没有经过任何优化和处理
	2,nio
		* NIO(new IO),是JAVASE1.4以及后续版本提供的一种新的IO操作方式,也就是 java.nio包,和子包.java nio是基于一个缓冲区.并能提供非阻塞IO操作的JAVAAPI
		* NIO也被看成是 non-blocking IO的缩写,它比传统的IO(BIO)操作,有更好的并发性能
	3,apr
		* 安装起来最困难,但是从操作系统级别来解决异步IO的问题,大幅度的提高性能
	

----------------------------
Tomcat-启动NIO模式			|
----------------------------
	1,修改server.xml里的Connector节点,修改: protocol为:org.apache.coyote.http11.http11NioProtocol
		* protocol="org.apache.coyote.http11.Http11NioProtocol"
		* 其实配置文件下面就有一个注释的例子.直接可以 copy
	# 各种IO模式
		org.apache.coyote.http11.http11NioProtocol
			* NIO
		org.apache.coyote.http11.Http11Nio2Protocol
			* AIO

----------------------------
Tomcat-执行器(线程池)		|
----------------------------
	# tomcat中,每一个用户请求都是一个线程,所以可以使用线程池来提高性能
	# Tomcat 默认没有启用线程池

	1,打开线程池的注释
		<Service name="Catalina">
		<!--The connectors can use a shared executor, you can define one or more named thread pools-->
		<!-- 
			打开这个注释 ,在 name="Catalina" 的Service 节点下
		-->
		<Executor name="tomcatThreadPool" namePrefix="catalina-exec-"
			maxThreads="150" minSpareThreads="4"/>

		* maxThreads			:(int)最大连接数			
		* minSpareThreads		:(int)最小活跃线程数
		* maxQueueSize			:(int)最大等待队列数,超过则请求拒绝
		* prestartminSpareThreads:(boolean)是否在启动的时候,就生成 minSpareThreads 个线程

   2,指定线程池
		<Connector executor="tomcatThreadPool" port="8080" protocol="org.apache.coyote.http11.Http11NioProtocol"
				   connectionTimeout="20000"
				   redirectPort="8443" />

	
	# 最佳实践
		<Executor name="tomcatThreadPool" namePrefix="catalina-exec-"
			maxThreads="800" minSpareThreads="100" maxQueueSize="100" prestartminSpareThreads="true"/>

----------------------------
Tomcat-连接器(Connecter)	|
----------------------------
	# Connecter是Tomcat接收请求的入口,每个Connecter都有自己专属的监听端口
	# Connecter两种:
		HTTP Connector 
		AJP  Connector
	
	# 通用属性(太TM多了... ...自己去找)
		* 在哪里可以找到这堆参数说明?
		* http://localhost:8080/docs/config/http.html

		allowTrace
			* 如果服务器能够处理用户的HEAD/TRACE请求,这个值应该是 true,默认值为 false
		
		asyncTimeout
			* 默认超时以毫秒为单为单位的异步请求,如果没有指定,该属性值默认 10000(10秒)
		
		enableLookups
			* 默认 false,就是通过 request 的一个方法获取用户主机的时候,不去检索DNS服务器
		
		maxPostSize
			* 指定最大POST请求的大小,默认值:2097152(2兆),如果设置为0,则表示没有限制
		
		port
			* 端口,不用说.如果设置为:0,则 Tomcat 为连接器随机选择一个空闲端口.
		
		protocol
			* 指定运行模式
		
		compression
			* 是否开启文本数据压缩,使用GZIP
			* 接受的值
				off			//禁用压缩
				on			//允许压缩
				force		//强制在所有情况下压缩
		


	# 最佳实践
		
		<Connector
			executor="tomcatThreadPool"
			port="8080"
			protocol="org.apache.coyote.http11.Http11NioProtocol"
			redirectPort="8443"
			enableLookups="false"
			URIEncoding="UTF-8"
			acceptCount="100"
			acceptorThreadCount="2"
			disableUploadTimeout="true"
			maxConnections="10000"
			SLLEnabled="false"
		/>

----------------------------
Tomcat-禁用JPA连接器		|
----------------------------
	AJP（Apache JServer Protocol）
	AJPv13协议是面向包的。WEB服务器和Servlet容器通过TCP连接来交互；为了节省SOCKET创建的昂贵代价，WEB服务器会尝试维护一个永久TCP连接到servlet容器，并且在多个请求和响应周期过程会重用连接。
	
	# 说白了,就是微软 IIS,或者是 Apache 服务器,跟Tomcat通信
	# 我们一般是使用Nginx+tomcat的架构，所以用不着AJP协议，所以把AJP连接器禁用。

	# 直接注释掉AJP
		<!--
		<Connector port="8009" protocol="AJP/1.3" redirectPort="8443" />
		-->


----------------------------
Tomcat-JVM参数优化			|
----------------------------
	 栈
		* JAVA的栈是跟每一个线程相关的,JVM创建一个线程的时候,就会给这个线程分配一个栈空间.
		* 主要存储,线程运行时候的局部变量,方法返回值.以及方法调用的上下文,栈空间随着线程的终止而释放
	
	堆
		* JAVA中的堆,是所有线程共享的一块内存区域.用来保存各种JAVA对象,数组,线程等
	
	
	堆分区
		* JAVA的堆,可以分为三个区域
		1,Young
			* 年轻区
			* new 一个对象,就在这里
			* GC会回收这个区域的对象
			* 如果说对象被GC了几次都没回收,就会放入下面的区域

		2,Tenured
			* 老年区
			* 保存生命周期长的对象,一般不会被GC回收,但也不是绝对

		3,Perm
			* 永久区
			* 启动一个应用的时候,Class 对象,类的变量
			* 这里面的对象,是不会被回收的
		
	# JVM参数设置

		-Xms
			* JVM初始化启动以后初始内存
		-Xmx
			* JVM堆的最大内存,JVM启动后,会分配 -Xmx参数指定大小的内存给JVM,但是不一定全部使用.
			* JVM会根据Xms参数来调节真正的JVM内存

		* 他俩相减,就是仨Virtual空间大小
			
		-XX:PermSize=116M -XX:MaxPermSize=64M
	# Tomcat 中设置的常用参数
		* 修改配置文件:bin/catalina.sh
		* 注意,是有引号的
		JAVA_OPTS="-Dfile.encoding=UTF-8 -server -Xms1024m -Xmx2048m -XX:NewSize=512m -XX:MaxNewSize=1024m -XX:PermSize=256m -XX:MaxPermSize=256m -XX:MaxTenuringThreshold=10 -XX:NewRatio=2 -XX:+DisableExplicitGC"

		file.encoding
			* 默认文件夹编码
		-Xmx1024m
			* 设置JVM最大可用内存为1024
		-Xms1024m
			* 设置JVM最小内存为1024
			* 这个值可以与上面个相同,以避免每次垃圾回收后JVM重新分配内存
		-XX:NewSize
			* 设置年轻代大小
		XX:MaxNewSize
			* 设置最大的年轻代大小
		-XX:PermSize
			* 设置永久代大小
		-XX:MaxPermSize
			* 设置最大永久代大小
		-XX:NewRatio=4	
			* 设置年轻代和永久代的比值...

		-XX:MaxTenuringThreshold=0
			* 设置垃圾最大年龄
		-XX:+DisableExplicitGC
			* 这个设置会忽略手动的GC调用
	
	* 在命令行下用 java -XmxXXXXM -version 命令来进行测试，然后逐渐的增大XXXX的值，如果执行正常就表示指定的内存大小可用，否则会打印错误信息，示例如下： 
		java -Xmx3072M -version。