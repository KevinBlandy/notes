------------------------
zuul					|
------------------------
	# 请求过滤,实现接口: com.netflix.zuul.ZuulFilter
		@Bean		//注册ioc即生效
		public class DemoFilter extends ZuulFilter {
			@Override
			public boolean shouldFilter();
				* 判断当前过滤器是否被执行,如果返回 true,则执行:run() 方法

			@Override
			public Object run();
				* 执行校验的方法
				* 目前框架不会去处理返回值(忽略了返回值)

			@Override
			public String filterType();
				* 定义过滤器的类型,它决定了过滤器在请求的哪个生命周期中执行,枚举字符串
					pre(FilterConstants.PRE_TYPE)
						* 在请求被路由之前执行
					error(FilterConstants.ERROR_TYPE)
						* 在请求异常时候处理
					post(FilterConstants.POST_TYPE)
						* 在最后(route和error之后)调用
					route(FilterConstants.ROUTE_TYPE)
						* 在请求路由时调用
					

			@Override
			public int filterOrder();
				* 当存在多个过滤器的时候,该值定义了过滤器的执行顺序
				* 数值越小,优先级越高
		}
	
	# 校验小Demo
		@Override
		public Object run() {
			RequestContext requestContext = RequestContext.getCurrentContext();
			HttpServletRequest httpServletRequest = requestContext.getRequest();
			if(httpServletRequest.getHeader("auth") == null) {
				//不进行路由
				requestContext.setSendZuulResponse(false);
				//设置响应状态码为401
				requestContext.setResponseStatusCode(HttpServletResponse.SC_UNAUTHORIZED);
				return null;
			}
			return null;
		}


------------------------
通过配置来禁用过滤器	|
------------------------
	zuul.<SimpleClasName>.<filterType>.disable=true

	* zuul.AuthFilter.pre.disable=true
	
		

------------------------
动态Filter				|
------------------------
	# 对于请求过滤器的动态加载,需要借助动态的jvm语言:Groovy

------------------------
FilterProcessor			|
------------------------
	FilterProcessor getInstance()
	void setProcessor(FilterProcessor processor)



------------------------
RequestContext			|
------------------------
	# 请求上下文
	# RequestContext
		HttpServletResponse getResponse();
        StringBuilder getFilterExecutionSummary();
        getOriginContentLength();
        getOriginResponseHeaders();
        getZuulResponseHeaders();
        getZuulEngineRan();
        getResponseGZipped();
        getResponseDataStream();
        getRequestQueryParams();
        getResponseStatusCode();
        getRouteHost();
        getResponseBody();

