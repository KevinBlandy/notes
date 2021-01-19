package com.kevin;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.text.*;
public class MyFirstServlet implements Servlet
{
	//初始化Servlet--就是把该Servelet装载到内存中。该函数只会被调用一次。
	public void init(ServletConfig config)throws ServletException
	{
		
	}
	//得到ServletConfig对象。
	public ServletConfig getServletConfig()
	{
		return null;
	}
	//该函数，是服务函数。我们的业务逻辑代码。就是写在这里的。该函数每次都会被调用。
	public void service(ServletRequest req,ServletResponse res)throws ServletException,java.io.IOException
	{
		//System.out.println("Hello World"+new SimpleDateFormat("yyyy年MM月dd日").format(new Date()));
		res.getWriter().println("Hello World	"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
	}
	//该函数是得到Servlet的一些配置信息。
	public java.lang.String getServletInfo()
	{
		return null;
	}
	//销毁这个Servlet。从内存中清除掉。该函数在整个过程中只会被调用一次。
	public void destroy()
	{
	
	}
}