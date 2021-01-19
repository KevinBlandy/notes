package com.kevin.demo;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
/**
 * Filter注解演示
 * */
@WebFilter(urlPatterns={"/AServlet","/AAServlet"})
public class AFilter implements Filter
{
	public void destroy() 
	{
		
	}
	public void doFilter(ServletRequest arg0, ServletResponse arg1,FilterChain arg2) throws IOException, ServletException
	{
		System.out.println("我是3.0的注解,你怕了吗？");
		arg2.doFilter(arg0, arg1);
	}
	public void init(FilterConfig arg0) throws ServletException 
	{

	}
}
/***
 * 传统的配置就不演示了....无聊
 * */
