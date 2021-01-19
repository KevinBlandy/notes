详解ServletContext

ServletContext
  
  ServletContext是Servlet三大域对象之一
  ServletContext在服务器启动时创建，在服务器关闭时销毁，一个JavaWeb应用只创建一个ServletContext对象!
  
1. 它的功能分类：
  * 存取数据
  * 读取web.xml中的应用初始化参数
  * 读取应用资源

2. 获取ServletContext对象
   在HttpServlet中可以通过以下方法来获取ServletContext对象
  * ServletContext servletContext = this.getServletContext()
  * ServletContext servletContext = this.getServletConfig().getServletContext()

2. 存取数据
  因为在一个JavaWeb应用中，只有一个ServletContext对象，所以在ServletContext中保存的数据可以共整个JavaWeb应用中的所有servlet动态资源共享
  ServletContext是Servlet三大域对象之一，域对象内部有一个Map，用来保存数据

	------------常用方法------------
	
setAttribute(String name, Object value);
	|--返回值是void .用来添加或替换ServletContext域数据
		 > servletContext.setAttribute("xxx", "XXX")，添加域数据
		 > servletContext.setAttribute("xxx", "XXXX")，覆盖域数据，因为在域中已经存在了名为xxx的数据，所以这次就是覆盖了

getAttribute(String name);
	|--注意：返回值是 Object .通过名称来获取域数据

getAttributeNames()；
	|--返回值是一个枚举 Enumeration<String> 获取所有ServletContext域数据的名称

removeAttribute(String name);
	|--返回值是void.通过名称移除域数据

getInitParameter(String name);
	|--返回String根据名称来获取web.xml文件中对应的值

getInitParameterNames();
	|--返回一个 Enumeration<String> 是有<Context-param>标签的<param-name>标签！

getResourcePaths(String);
	|--返回值是一个 Set<String> 集合.包含了指定目录下的所有文件路径。
		> Set<String> paths = servletContext.getResourcePaths("/WEB-INF");
		返回的Set中包含如下字符串：
		> /WEB-INF/lib/
		> /WEB-INF/classes/
		> /WEB-INF/web.xml
		> /WEB-INF/a.jpg


getRealPath(String path);
	|--返回值是String 获取指定名称资源的真实名称
		>String path = servletContext.getRealPath("/WEB-INF/a.jpg");
		返回值为/WEB-INF/a.jpg真实路径，即磁盘路径：C:/tomcat6/wabapps/hello/WEB-INF/a.jpg

getMimeType(String path)
	|--获取指定文件的文件类型
		> path,表示一个绝对路径的文件

getResourceAsStream(String path);
	|--返回值是 InputStream.根据指定名称的资源。获取与之相联的读取流
		>InputStream in = servletContext.getResourceAsStream("/WEB-INF/a.jpg");
		返回的是a.jpg的输入流对象，可以从流中得到a.jpg的数据

3. 读取web.xml中配置的应用初始化参数

  <context-param>
    <param-name>p1</param-name>
    <param-value>v1</param-value>  	
  </context-param>
  <context-param> 
    <param-name>p2</param-name>
    <param-value>v2</param-value>  	
  </context-param>

  * servletContext.getInitParameter("p1")，返回v1
  * servletContext.getInitParameter("p2")，返回v2
  * servletContext.getInitParameterNames()，返回Enumeration<String>，包含p1和p2

 ^^ 一个Servlet也可以获取初始化参数。但是它是局部的参数。也就是说。一个Servlet只能获取自己的初始化参数。不能获取别人的！即，初始化参数只为一个Servlet准备
	ServletConfig	获取
 ^^ 可以配置公共的初始化参数，为所有的Servlet而用
	ServletContext	获取
4. 获取项目资源(重要)
  * String getRealPath(String path)：获取资源的真实名称
  * String path = servletContext.getRealPath("/WEB-INF/a.jpg");
  返回值为/WEB-INF/a.jpg真实路径，即磁盘路径：C:/tomcat6/wabapps/hello/WEB-INF/a.jpg
 
  * InputStream getResourceAsStream(String path)：获取资源的输入流
  InputStream in = servletContext.getResourceAsStream("/WEB-INF/a.jpg");
  返回的是a.jpg的输入流对象，可以从流中得到a.jpg的数据

  * Set<String> getResourcePaths(String path)：获取指定目录下的所有资源路径
  Set<String> paths = servletContext.getResourcePaths("/WEB-INF");
  返回的Set中包含如下字符串：
    > /WEB-INF/lib/
    > /WEB-INF/classes/
    > /WEB-INF/web.xml
    > /WEB-INF/a.jpg



该对象主要用于读取Servlet的配置信息。
一个项目。只有一个ServletConfig   
 我们可以N多个Servlet中来获取这个唯一的对象。使用它可以给多个Servlet传递数据。
 各个Servlet之间是不认识的。就只有靠它了！它与天地同寿。这对象。在Tomcat启动时出现。Tomcat关闭时才消失！
 可以理解是整个项目中唯一的快递员！

?ServletContext中getAttribute和getInitParameter区别!