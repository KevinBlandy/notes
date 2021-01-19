异步处理
一,回忆异步处理
	1,什么是异步处理,还记得?原来在服务器还没结束响应之前,客户端浏览器看不到响应的内容,只有响应结束的时候浏览器才能显示结果
	2,异步处理的作用,在服务器开始响应后,浏览器就可以看到响应内容,不用等待服务器响应结束

二,实现异步的步骤
1,得到AsyncContext,它叫做异步上下文
	AsyncContext ac = request.startAsync(request,response);
2,给上下文一个Runnable对象(别说不认识),启动它
	final AsyncContext ac = request.startAsync(request,response);
	ac.start(new Runnable()//匿名内部类
	{
		public void run() 
		{
			//异步执行的代码,其实也就是多线程代码
		}
		ac.complete();
	});
	* ac.complete();方法详解
		> 这个方法就是告诉服务器,我们的异步交互已经结束了,你可以关闭跟客户端的连接了
		> 因为这个线程是衍生出来的,Tomca主线程根本没办法知道你是不是已经运行完毕了,只有一直等等等,一直到连接超时才断开,
		> 这也是为什么,服务器已经响应完毕,而我们的浏览器还在请求状态,还在孤独的旋转!
	* 注意,如果不设置响应编码,经常有可能会导致异步失败
		> response.setContentType("text/html;charset=utf-8");//如果不设置编码,经常导致异步失败
		> !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		> 给你一排感叹号,就是提醒你,这东西不设置真的会失败,我已经试过了... ...
		> 其实这个重要的不是编码,而是要告诉他text/html
	* 关于异步交互,IE有个毛病
		> 当你的输出的数据小余512KB的时候,它就不给你显示,给你存着,等你发完了,就给你一堆弄出来
		> 很多人就以为IE异步不起来,其实我们只要在程序内部,先给它512KB的垃圾数据给它玩儿就好了,照样还是能够异步的
		> 这东西吧,只会影响测试.实际项目不会有问题,都特么用上异步了,这512KB还没啊?

3,记得给Servlet类头上添加一个注解
	@WebServlet(asyncSupported=true)
	> 此注解值==true,那么该页面才能使用这个异步处理

	@WebFilter(asyncSupported=true)
	> 如果使用了Filter,也要添加该注解属性

----------------------
AsyncContext	      |
----------------------
	# 接口方法
		ServletRequest getRequest();
		ServletResponse getResponse();
		boolean hasOriginalRequestAndResponse();

		void addListener(AsyncListener listener); 
		void addListener(AsyncListener listener,ServletRequest servletRequest,ServletResponse servletResponse);
			* 添加监听器

		<T extends AsyncListener> T createListener(Class<T> clazz);
			

		void complete();
			* 完成输出
		

		void dispatch();
		void dispatch(String path);
		void dispatch(ServletContext context, String path);

		long getTimeout();

		void setTimeout(long timeout);
			*  设置和获取超时时间

		void start(Runnable run);

----------------------
AsyncEvent			 |
----------------------
	# 事件
		 AsyncContext getAsyncContext()
		 ServletRequest getSuppliedRequest()
		 ServletResponse getSuppliedResponse()
		 Throwable getThrowable()


----------------------
Demo				 |
----------------------
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html;charset=utf-8");//如果不设置编码,经常导致异步失败
		AsyncContext asyncContext = req.startAsync(req, resp);
		asyncContext.addListener(new AsyncListener() {
			@Override
			public void onTimeout(AsyncEvent event) throws IOException {
				System.out.println("超时:" + event);
			}
			@Override
			public void onStartAsync(AsyncEvent event) throws IOException {
				System.out.println("开始异步输出:" + event);
			}
			@Override
			public void onError(AsyncEvent event) throws IOException {
				System.out.println("异常:" + event);
			}
			@Override
			public void onComplete(AsyncEvent event) throws IOException {
				System.out.println("执行完毕:" + event);
			}
		});
		asyncContext.start(() -> {
			for(int x = 0 ;x < 10 ; x++) {
				try {
					asyncContext.getResponse().getWriter().write(x +  "<br/>");
					asyncContext.getResponse().getWriter().flush();
					Thread.sleep(1000);
				} catch (IOException | InterruptedException e) {
					e.printStackTrace();
				}
			}
			asyncContext.complete();
		});
	}