------------------------------
Spring-boot 使用JSP视图		  |
------------------------------
	# 参考网站
		* http://www.cnblogs.com/God-/p/5857428.html
		* http://blog.csdn.net/alan666156/article/details/52168450


------------------------------
Spring-boot				方案一 |
------------------------------

	1,在pom.xml中添加依赖
		<!--JSTL标签库-->
		<dependency>
			<groupId>javax.servlet</groupId>
			<artifactId>jstl</artifactId>
		</dependency>
		<!-- Servlet -->
		<dependency>
			<groupId>javax.servlet</groupId>
			<artifactId>javax.servlet-api</artifactId>
		</dependency>

		<!-- jsp解析 -->
		<dependency>
			<groupId>org.apache.tomcat.embed</groupId>
			<artifactId>tomcat-embed-jasper</artifactId>
		</dependency>
	
	3,创建目录 src/main/webapp/WEB-INF/views
		* 在该目录中存放jsp文件
	
	4,视图解析配置
		spring.mvc.view.prefix=/WEB-INF/views/
		spring.mvc.view.suffix=.jsp
	
	5,程序返回视图
		@GetMapping
		public ModelAndView index() {
			return new ModelAndView("index");
		}



	