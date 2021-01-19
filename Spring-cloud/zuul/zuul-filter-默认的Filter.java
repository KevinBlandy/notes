------------------------
系统预定义Filter		|
------------------------
	ServletDetectionFilter
		* 它的执行顺序为: -3;
		* 它的执行时间为: pre
		* 是最先执行的Filter
		* 主要的作用就是检测,当前的请求是Spring的 DispatcherServlet 还是 ZuulServlet 来处理运行的
		* 它的检测结果会保存在一个上下文参数中:String IS_DISPATCHER_SERVLET_REQUEST_KEY = "isDispatcherServletRequest";
			RequestUtils.isZuulServletRequest();
			RequestUtils.isDispatcherServletRequest();

	Servlet30WrapperFilter
		* 它的执行顺序为: -2
		* 它的执行时间为: pre
		* 它是第二个执行过滤器
		* 主要的作用就是把 HttpServletRequest 封装为:Servlet30RequestWrapper(装饰者设计模式)

	FormBodyWrapperFilter
		* 它的执行顺序为: -1
		* 它的执行时间为: pre
		* 它是第三个执行的Filter
		* 它对两类请求生效:
			Content-Type = application/x-www-form-urlencoded
			Content-Type = multipart/form-data
		* 它的目的就是为把请求体包装为:FormBodyRequestWrapper 对象

	DebugFilter
		* 它的执行顺序为: 1
		* 它的执行时间为: pre
		* 它是第四个执行的Filter
		* 该Filter会根据系统配置:zuul.debug.request 和请求中的debug参数来决定是否要执行过滤器操作
		* 它的具体过滤操作内容就是,把当前请求上下文中的 debugRouting 和 debugRequest 参数设置为true
		* 在同一个请求的生命周期内,都可以访问到这个值,所以后续的Filer可以根据这个两个值来定义一些dbug信息
		* 对于请求参数中的debug参数名,可以通过配置来设置:zuul.debug.parameter


	PreDecorationFilter
		* 它的执行顺序为: 5
		* 它的执行时间为: pre
		* 它是系统预定义的pre阶段中最后执行的Filter
		* 它会判断当前请求上下文中是否存在:forward.to 和 serviceId 参数
		* 如果都存,那么它就会执行过滤操作(只有一个参数存在的话,说明当前请求已经被处理过了,因为这两个信息就是根据当前请求的路由信息加载进来的)
		* 它的具体过滤操作就是,为当前请求做一些预处理,比如进行路由规则的匹配,在请求上下文中设置该请求的基本信息等等
		* 这些信息是后续过滤器进行处理的重要依据,可以通过:RequestContext.getCurrentContext() 来访问

		* 可以在该实现中看到一些对HTTP头进行添加的逻辑:
			X-Forwarded-Prefix
			X-Forwarded-Host
			X-Forwarded-Port
			X-Forwarded-Proto
			....

		* 可以通过参数来控制,是否添加这些请求头(X-Forwarded-*):zuul.addProxyHeaders=true
		


	RibbonRoutingFilter
		* 它的执行顺序为: 10
		* 它的执行时间为: route
		* 它是route阶段第一个执行的过滤器
		* 该过滤器只对请求上下文中存在serviceId参数的请求进行处理(也就是只对通过serviceId配置路由规则的请求生效)
		* 该过滤器的执行逻辑就是面向路由核心,它通过使用Ribbon和Hystrix来向服务实例发起请求,并将服务实例的执行结果返回

	SimpleHostRoutingFilter
		* 它的执行顺序为: 100
		* 它的执行时间为: route
		* 它是route阶段第二个执行的过滤器
		* 该过滤器只对请求上下文中存在 routeHost 参数的请求进行处理(也就是通过 url 配置路由规则的请求生效)
		* 它的执行逻辑就是,直接对 url 发起请求,使用的是 httpclient 包实现的,没有使用Hystrix命令进行包装(所以没有线程隔离器和断路器的保护)

	SendForwardFilter
		* 它的执行顺序为: 500
		* 它的执行时间为: route
		* 它是route阶段第三个执行的过滤器
		* 它只对请求上下文中存在:forward.to参数的请求进行处理,即用来处理路由规则中的forward本地跳转配置

	SendErrorFilter
		* 它的执行顺序为: 0
		* 它的执行时间为: post
		* 它是post阶段第一个执行的过滤器
		* 它负责处理异常,但是它是否执行的条件是,上下文中存在异常,并且尚未转发到errorPath
		* 它的逻辑就是,使用请求上下文中的错误信息来组成一个 forward 到 api 网关 /error 错误端点的请求,来产生错误响应

	SendResponseFilter
		* 它的执行顺序为: 1000
		* 它的执行时间为: post
		* 它是post阶段第最后一个执行的过滤器
		* 该过滤器会检测请求上下文中是否包含请求响应相关的头信息,响应数据流或是响应体
		* 只有在包含它们其中一个的时候执行处理逻辑
		* 处理逻辑就是:请求请求上下文的响应信息来组织需要发送回客户端的响应内容

------------------------
系统预定义Filter		|
------------------------

排序				Filter							功能
pre	---------------------------------------------------------
-3					ServletDetectionFilter			标记Servlet类型
-2					Servlet30WrapperFilter			包装HttpServletRequest对象
-1					FormBodyWrapperFilter			包装请求体
 1					DebugFilter						标记调试标志
 5					PreDecorationFilter				处理请求上下文,以供后续使用
route -------------------------------------------------------
10					RibbonRoutingFilter				serviceId转发
100					SimpleHostRoutingFilter			url请求转发
500					SendForwardFilter				forward请求转发
post --------------------------------------------------------
0					SendErrorFilter					处理有错误的请求响应
1000				SendResponseFilter				处理正常的请求响应

