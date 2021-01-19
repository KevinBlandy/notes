-----------相关计算机CMD命令
netstat -nao
	|--查看本机的端口占用情况
-----------Tomcat 启动失败
	JAVA环境变量不对
	其实就是拿JAVA写的程序。启动的时候需要有JVM支持。环境变量会影响到汤姆猫的运行。
	端口被占用
	可以使用上面的CMD命令来查看本机的端口被占用情况。通过CMD命名或者任务管理器杀死进程。
-----------Catalina_home 环境变量问题。
	TomCat启动，是默认以这个环境变量指向的服务器，所以。尽量不要配置这个环境变量。
-----------Tomcat的目录结构
bin	存放启动和关闭Tomcat的脚本文件
conf	存放Tomcat服务器的各种配置文件
	Server.xml	————	用于配置Server相关的信息。
	web.xml		————	用于配置，web应用(相当于一个web站点)。
	tomcat-user.xml	————	用于配置tomcat的用户名以及权限。
lib	存放Tomcat服务器支撑的jar包
logs	存放Tomcat的日志文件	
	localhost_access_log..txt  文档存放的是来访IP,访问时间以及访问方式(get/post)
temp	存放Tomcat运行时产生的临时文件
webapps	web应用所在目录，即提供外界访问的web资源存放目录	—— 对于开发人员，最重要的目录。
work	Tomcat的工作目录
	存放JSP被访问后，生成的对应Servlet文件，class文件。
-----------Tomcat常见配置
conf目录下 - Server.xml
<Connector port="8080" protocol="HTTP/1.1"
               connectionTimeout="20000"
               redirectPort="8443" />
	设置服务器所在的端口,默认是8080
---设置虚拟映射
在Tomcat目录的 conf\Server.html 的HOST节点中中添加
 <Context path="访问路径" docBase="WEB目录"/>
	访问路径，是一个不存在的目录。把实际存在的WEB目录通过此设置。可以让外界。通过访问虚拟目录来访问到实际存在的WEB目录。
	此项设置需要重启服务器才能生效。(所以不建议此操作)
 在Tomcat目录的 conf\Catalina\localhost下，创建XML文件。这个文件就是对外的虚拟访问目录。把你的WEB目录通过<Context docBase="WEB目录"/>这种格式写入XML。
	这种设置不需要重启服务器也能生肖(建议操作)、
	'访问路径如果是空字符串,那么该项目,就是服务器缺省项目'
-----------常见的协议端口
 http	80
 smtp	25
 pop3	110
 ftp	23
 https	443
 ----------