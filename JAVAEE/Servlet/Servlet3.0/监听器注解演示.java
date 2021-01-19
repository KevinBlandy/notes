package com.kevin.demo;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
/**
 * 监听器注解演示(application生命周期监听)
 * */
@WebListener
public class AListener implements ServletContextListener
{
	public void contextDestroyed(ServletContextEvent arg0) 
	{
		System.out.println("Tomcat,关闭我我也系3.0");
	}
	public void contextInitialized(ServletContextEvent arg0) 
	{
		System.out.println("Tomcat,启动我系3.0");
	}
}
