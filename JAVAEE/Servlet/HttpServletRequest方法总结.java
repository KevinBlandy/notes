HttpServletRequest
	封装了客户端请求的所有数据----------几乎都是get方法。只能获取不能修改！

获取http协议请求头等
getHeader(String name);//适返回String,用于单值头
getIntHeader(String name);//返回int,适用于单值int类型的请求头
getDateHeader(String name);//返回long值,适用于单值Date类型的头
getHeaders(String name);//返回Enumeration<String>适用于多值请求头。

获取请求URL等
getMethod();//返回String类型,返回请求方式(GET/POST)
getScheme();//返回String类型,获取协议
getServerName();//返回String类型,获取服务器名
getServerPort();//返回String类型,获取服务器端口
getContextPath();//返回String类型,获取项目名(上下文路径)
getServletPath();//返回String类型,获得servlet路径
getQueryString();//返回String类型,获得参数部分。即问号后面提交的参数
getRequestURI();//返回String类型,获得请求URI.等于项目名+servlet路径
getRequestURL();//返回String类型,获得请求URL.等于不包含参数的整个请求路径
getRemoteAddr();//返回String类型,获得客户端的IP地址

获取请求参数等
getParameter(String name);//返回String,获得指定的请求参数值。适用于单值参数请求
getParameterValues(String name);//返回String[],获得指定的请求参数值。适用于多值参数请求
getParameterNames();//返回String类型,获取所有请求参数的名称
getParameterMap();//返回Map<String,String[]>类型,获取所有请求参数。其中key为参数名,value为参数值

请求转发和请求包含
RequestDispatcher rd = request.getRequestDispatcher("/MyServlet");//request获取RequestDispatcher对象。方法的参数是:被转发,或者被包含的servlet路径
rd.forward(request,response);//请求转发【常用】
rd.include(request,response);//请求包含
/**request -- 参数,是浏览器发给服务器的.属性,是servlet之间传递值用的(request域) -- **/

设置字符编码(post)
request.setCharacterEncoding("utf-8");//让服务器以指定的编码格式来解析post中提交的数据(getPrameter();)