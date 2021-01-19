------------------------
异常处理				|
------------------------
	# 使用 SendErrorFilter 来处理
		* 它负责处理异常,但是它是否执行的条件是,上下文中存在异常,并且尚未转发到errorPath
		* errorPath : @Value("${error.path:/error}")

			@Override
			public boolean shouldFilter() {
				RequestContext ctx = RequestContext.getCurrentContext();
				return ctx.getThrowable() != null && !ctx.getBoolean("sendErrorFilter.ran", false);
			}

		* 可以在执行过程中设置异常,来触发SendErrorFilter进行异常处理
			RequestContext requestContext = RequestContext.getCurrentContext();
			requestContext.setThrowable(new Exception());

		* 也可以添加如下的参数
			// 错误编码
			requestContext.set("error.status_code", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			// 异常对象
			requestContext.set("error.exception",new Exception());
			// 错误信息
			requestContext.set("error.message","错误信息");
		
		* 可以自己实现异常的转发的http接口,返回自己定义的错误信息
	

	# 因为在请求的生命周期:pre,post,route 三个阶段中,只要有异常抛出都会进入到erro阶段的处理,所以可以自定义ErrorFilter
		
		