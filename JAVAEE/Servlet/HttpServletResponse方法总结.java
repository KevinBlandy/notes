HttpServletResponse

	ServletResponse-->与协议无关的类型
	HttpServletResponse-->与http协议相关的类型
---------------------------
对于http状态码的处理(状态码看http笔记)
sendError(int sc);//发送错误状态码
sendError(int sc,String msg);//发送错误状态码,带上错误信息
setStatus(int sc);//发送成功状态码--可以发送302(重定向)

对于http响应头的处理(详细头看http笔记)
/***响应头是一个键值对,可能存在[一个名称|一个值]也可能存在[一个名称|多个值]***/
setHeader(String name,String value);//适用于单值的响应头【重要】
addHeader(String name,String value);//适用于多值的响应头
setIntHeader(String name,int value);//适用于单值的int类型的响应头
addIntHeader(String name,int value);//适用于多值的int类型的响应头
setDateHeader(String name,long value);//适用于单值的毫秒类型响应头
addDateHeader(String name,long value);//适用于多值的毫秒类型响应头

常用方法
getOutputStream();//返回一个ServletOutputStream对象(OutputStream子类)。字节流，可以发送字节相关的数据。跟getWeiter不能同时使用。
getWriter();//返回一个PrintWriter对象。可以发送字符流(注意设置编码处理乱码问题)。跟getOutputStream不能同时使用。
setContentType("文档类型");//设置response的文档类型为指定文档类型(text/html)
sendRedirect("连接URL");//设置重定向连接.相当于同时发送了302状态码以及location头！
setCharacterEncoding("utf-8");//设置输出的字符编码。
	