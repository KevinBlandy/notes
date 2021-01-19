package com.kevin.demo;
import java.io.IOException;
import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * Servlet3.0之,异步处理演示.
 * */
@WebServlet(urlPatterns="/NewServlet",asyncSupported=true)
public class NewServlet extends HttpServlet 
{
	public void doGet(HttpServletRequest request, final HttpServletResponse response)throws ServletException, IOException 
	{
		response.setContentType("text/html;charset=utf-8");//如果不设置编码,经常导致异步失败
		for(int x = 0;x <= 512 ;x++)
		{
			response.getWriter().print("a");
		}
		response.getWriter().flush();
		final AsyncContext ac = request.startAsync(request,response);
		ac.start(new Runnable()//匿名内部类
		{
			public void run() 
			{
				println("马上开始发送<br/>",response);
				sleep(3000);//线程休眠
				for(char c = 'A';c <= 'Z';c++ )//输出26个英文字母
				{
					println(c+",",response);
					sleep(250);//线程休眠
				}
				//通知服务器,我们已经结束了,也是内部类访问局部变量,需要加上final
				ac.complete();
			}
		});
	}
	/**
	 * 专门用于输出的方法
	 * */
	private void println(String str,HttpServletResponse response)
	{
		try 
		{
			response.getWriter().print(str);//因为内部类访问局部变量,局部变量需要final修饰
			response.getWriter().flush();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	public void sleep(long l)
	{
		try 
		{
			Thread.sleep(l);
		} 
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
}
