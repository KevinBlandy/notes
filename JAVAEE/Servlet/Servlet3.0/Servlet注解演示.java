package com.kevin.demo;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * Servlet注解演示
 * */
@WebServlet(urlPatterns={"/AServlet","/AAServlet"},
		initParams={@WebInitParam(name="p1",value="v1"),
					@WebInitParam(name="p2",value="v2")},
					loadOnStartup=1)		
public class Aservlet extends HttpServlet
{
	protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException 
	{
		//向控制台输出
		System.out.println("hello Servlet3.0");
		response.getWriter().print("hello Servlet3.0");
	}
}
/*
 
传统web.xml配置
<servlet>
	<servlet-name>Aservlet</servlet-name>
	<servlet-class>com.kevin.demo.Aservlet</servlet-class>
	<init-param><!-- 初始参数 -->
		<param-name>p1</param-name>
		<param-value>v1</param-value>
	</init-param>
	<init-param>
		<param-name>p2</param-name>
		<param-value>v2</param-value>
	</init-param>
	<load-on-startup>1</load-on-startup><!-- Serlvet实例随服务器启动而创建 -->
</servlet>
<servlet-mapping>
	<serlvet-name>Aservlet</servlet-name>
	<url-pattern>/Aservlet</url-pattern>
	<url-pattern>/AAservlet</url-pattern>
</servlet-mappong>

*/
