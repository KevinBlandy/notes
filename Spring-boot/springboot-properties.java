-------------------------------
Spring boot-配置项				|
-------------------------------
	// Server相关
		server.port=80
			# WEB监听端口

		server.servlet.context-path=/
			# WEB访问路径
		
		server.tomcat.basedir
			# 设置tomcat的临时目录
			# 在linux系统中,springboot应用服务再启动(java -jar 命令启动服务)的时候,会在操作系统的/tmp目录下生成一个tomcat*的文件目录
			# 上传的文件先要转换成临时文件保存在这个文件夹下面
			# 由于临时/tmp目录下的文件,在长时间(10天)没有使用的情况下,就会被系统机制自动删除掉,所以如果系统长时间无人问津的话,就可能导致:The temporary upload location [/tmp/tomcat.1337767218595042057.80/work/Tomcat/localhost/ROOT] is not...
	
	//编码处理
		server.tomcat.uri-encoding=UTF-8
		spring.http.encoding.charset=UTF-8
		spring.http.encoding.enabled=true

	//日期格式处理
		spring.mvc.date-format=yyyy-MM-dd HH:mm:ss

	//日志
		logging.config=classpath:community-logback.xml
			# logback配置文件地址
	
	//静态文件映射
		spring.mvc.static-path-pattern=/static/**													*/
			# 设置静态资源的访问前缀,默认情况下,静态资源目录有
				* /static 
				* /public
				* /resources
				* /META-INF/resources

			# 用于指定静态文件的目录(在classpath目录下-src/main/resources),允许外界直接访问
		
		spring.resources.static-locations[0]=
			# 也是静态资源的映射处理,是一个数组,支持多个
			# 支持 classpath:/ ,支持 file:/

	//MyBatis
		mybatis.mapper-locations[0]=classpath*:mapper/**/Mapper.xml
			# mybatis mapper文件扫描地址

		mybatis.config-location=classpath:mybatis/mybatis-config.xml
			# mybatis配置文件地址

	//导入外部配置文件
		spring.profiles.include[0]=datasource
		spring.profiles.include[1]=redis
	
	//上传配置
		spring.servlet.multipart.max-file-size=30MB
		spring.servlet.multipart.max-request-size=30MB
		spring.servlet.multipart.enabled=true