package com.kevin;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
public class FirstServlet extends HttpServlet 
{
	/**
	 * 以get方式访问页面时执行该函数.
	 * 执行doGet前会先执行getLastModified。
	 * 如果浏览器发现getModified返回值的数值与上次访问时返回的数值相同
	 * 则认为该文档没有更新。浏览器才用缓存。而不执行doGet
	 * 如果getLastModified返回-1，则认为是时刻更新的。总是执行doGet
	 * */
	public void doGet(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException
	{
		this.log("执行doGet方法");//调用servlet自带的日志输出信息到控制台
		this.execute(request,response);//处理doGet
	}
	/**
	 * 以pos方式访问页面时执行doPost函数。执行前不会执行getLastModified
	 * */
	public void doPost(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException
	{
		this.log("执行doPost方法");//调用servlet自带的日志输出信息到控制台
		this.execute(request,response);
	}
	/**
	 * 返回该servlet生成的文档更新时间。对Get方式访问有效。返回的时间相对于1970-1-1 08:00:00 的毫秒数
	 * 如果为-1，则表示是时时更新。默认的是-1
	 * */
	public long getLastModified(HttpServletRequest request)
	{
		this.log("执行getLastModified方法");
		return -1;
	}
	/**
	 * 执行方法
	 * */
	private void execute(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException
	{
		response.setCharacterEncoding("UTF-8");//设置response字符编码
		request.setCharacterEncoding("UTF-8");//设置request字符编码
		String requestURI = request.getRequestURI();//获取-访问该servlet的URI
		String method = request.getMethod();//获取-访问该servlet的方式.Get或者Post
		String param = request.getParameter("param");//获取-客户端提交的"param"值
		response.setContentType("text/html");//设置response的文档类型为html类型
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head><title>FirstServlet</title></head>");
		out.println("<body>");
		out.println("以"+method+"方法访问该页面。收到的param参数为"+param+"<br/>");
		out.println("<from action='"+requestURI+"'method='GET'>");
		out.println("<input type='text' name='param' value='param String'/>");
		out.println("<input type='submit' value='以Get方式查询页面"+requestURI+"'/><br/>");
		out.println("</from>");
		out.println("<from action='"+requestURI+"' method='POST'>");
		out.println("<input type='text' name='param' value='param String'/>");
		out.println("<input type='submit' value='以post方法查询页面"+requestURI+"'/><br/>");
		out.println("</from>");
		//由客户端浏览器读取该文档的更新时间
		out.println("<script>document.write('本页最后更新的时间:'+document.lastModified);</script>");
		out.println("</body>");
		out.println("</html>");
		out.flush();
		out.close();
	}
}
