分析前端控制器的源码,来分析springMVC的执行过程


1,接收请求
	前端控制器会去调用一个doDispatcher()的方法
	> doDispatcher(HttpServletRequest request,HttpServletResponse response)
2,前端控制器去调用处理器-映射器,来查找Handler
	> protected HandlerExecutionChain getHandler(HttpServlet request);
3,调用处理器适配器去执行Handler,得到ModelAndView
	> ModelAndView = ha.handle(..);
4,视图渲染,讲Model的数据,填充到request域
	

	不写了...你也别看了!知道过程就好,不用连代码都给记下来