————————————————————————————————
一,简介							|
————————————————————————————————
	struts2,是一个侵入式的WEB/MVC框架!在是使用的时候,封装了一切的ServletAPI！
		* Session,request,response,application... ...
	使用框架,尽量别直接去使用这些,但这个框架还是比较友好!你非要用,我还是给你用!

————————————————————————————————
二,非IOC						|
————————————————————————————————
1,ActionContext	获取 Map
	* 通过这个对象,可以获取到三大域对象的Map<String,Object>引用!
	ActionContext ctx = ActionContext.getContext();
		* 该对象不能直接访问构造函数创建,说白了不能new
	get(String name);//返回一个Object,其实就是从request域里面取出对象
	put(String,Object);//往request域里面存入一个对象 
	getApplication();//获取ServletContext域对象Map的引用
	getSession();//获取Session域对象Map的引用
	getParameter(String name);//获取请求参数
		* 返回的是 Map<String,Object>,这个Object其实是个数组!这个方法跟request.getParameterMap();其实是一个德行!
	
2,ServletActionContext	获取真正对象的引用
	* 通过这个对象,可以获取到Servlet仨个域对象,以及response,甚至连jsp的域对象,PageContext的真正的API!
	* 这哥们儿不能new,方法都静态的,直接拿就是!
	HttpServletRequest	request = ServletActionContext.getRequest();
	HttpSession			session = ServletActionContext.getRequest().getSession();
	ServletContext		application = ServletActionContext.getServletContext();
	HttpServletResponse	response = ServletActionContext.getResponse();
	PageContext			pageContext = ServletActionContext.getPageContext();
	* 如果涉及到使用Cookie之类的,就有必要通过这个获取response来向客户端响应Cookie

————————————————————————————————
二,IOC注入						|
————————————————————————————————
	所谓的IOC注入,其实就是实现接口,覆写方法!
	框架会根据接口把对应的ServletAPI类或者其Map的引用通过方法传递到Action!
1,获取Map
	RequestAware,ResponseAware,ContextAware
	* 这东西不怎么解释了,就是获取接口,看下面的东西就知道该怎么用!
	* 它们注入的是:Map<String,Object>


2,获取真正的对象引用
	'接口'						'方法'
	ServletRequestAware			setServletRequest(HttpServletRequest request)	
	ServletContextAware			setServletContext(ServletContext context)
	ServletResponseAware		setServletResponse(HttpServletResponse response)
	
	* 重写方法,而且在Action中声明API的引用!在方法中进行赋值!
	* 至于Session对象,自己通过request来获取!
	
	'拓展',分析实现!
		其实这个注入是使用struts2中的一个interceptor实现的!也就是strutsl.default.xml中的!
		<interceptor name="servletConfig" class="org.apache.struts2.interceptor.ServletConfigInterceptor"/>
		* 这个类的 interceptor()方法,对action进行了判断,如果发现是某个接口的子类,就调用接口方法,注值
		if(action instanceof xxxx){}
		if(action instanceof xxxx){}
		