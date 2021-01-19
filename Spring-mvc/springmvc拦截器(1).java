

/////////////////////////////////
	1,拦截器的定义
		* 实现接口
			org.springframework.web.servlet.HandlerInterceptor
		* 覆写三个方法
		public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception 
			1,在请求到达Handler方法之前执行
				request
				response
				handler
			2,用于身份认证,身份授权
				* 根据该方法的 Boolean 类型的返回值来确定是否放行,执行Handler

		public void postHandle(HttpServletRequest request,HttpServletResponse response, Object handler,ModelAndView modelAndView) throws Exception
			1,在Handler执行完毕后,ModelAndView返回之前
				request
				response
				handler
				modelAndView
			2,可以获取到ModelAndView,也就是说,可以用拦截器的这个方法,来统一填充一些公用的模型数据!
			3,只有第一个方法返回true的时候才会执行
			4,在试图渲染之前执行

		public void afterCompletion(HttpServletRequest request,HttpServletResponse response, Object handler, Exception exception)throws Exception 
			1,在Handler返回MoldelAndView之后执行
				request
				response
				handler
				exception
			2,Handler执行完毕后,执行此方法.可以进行:日志处理,异常信息记录,
			3,必须是是一个返回值为true的时候执行、
			3,用于对资源进行清理

	* 拦截器都是基于AOP实现的!
	* SpringMVC 拦截器针对于 HandlerMapping进行拦截设置,如果在某个HandlerMapping 中配置拦截.经过该HandlerMapping,映射成功的Handler,最终才使用该拦截器

	* SpringMVC配置全局拦截器,SpringMVC框架将配置的类似于全局的拦截器注入到每个Handler中


配置拦截器:
	<bean class="org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping">
		<property name="interceptors">
			<ref bean=""/>	<!-- 拦截器1 -->
			<ref bean=""/>	<!-- 拦截器2 -->	
		</property>
	</bean>

	* 这种方式不推荐使用

	全局的配置(推荐使用)

	<!-- 拦截器,多个拦截器顺序执行 -->
	<mvc:interceptors>
		<!-- 第一个拦截器 -->
		<mvc:interceptor>
			<mvc:mapping path="/**"/>
			<mvc:exclude-mapping path="/hello.do"/>			//hello.do不执行拦截
			<bean class="com.kevin.interceptor.MyInterceptor"/>
		</mvc:interceptor>
		<!-- 第二个拦截器 -->
		<mvc:interceptor>
			<mvc:mapping path="/**"/>
			<mvc:exclude-mapping path="/hello.do"/>			//hello.do不执行拦截
			<bean class="com.kevin.interceptor.MyInterceptor"/>
		</mvc:interceptor>
	</mvc:interceptors>

	* 如果直接把Bean配置在了interceptors下面,那么会拦截所有的请求
	

	/**																						*/
		> 表示拦截URL所有子URL路径
	/*																						*/
		> 表示只拦截指定路径的下一级子路径

	执行测试结果... ...
	前置拦截器执行
	A拦截器===前置拦截执行执行
	B拦截器===前置拦截执行执行
	B拦截器===后置拦截执行执行
	A拦截器===后置拦截执行执行
	后置拦截器执行
	B拦截器===最终拦截执行执行
	A拦截器===最终拦截执行执行
	最终拦截器执行

	总结:有点像回调,

	拦截器1放行,拦截器2放行
	拦截器2执行完,拦截器1执行完,
	拦截器2最终执行,拦截器1最终执行

	例如:登录验证拦截器应该放在第一个位置,权限验证的拦截器就应该放在第二个位置


	只要有一个拦截器不放行,任何postHandle都不会放行.

-------------------------------
高级							|
-------------------------------
	1,在拦截器中获取请求方法/Controller
		HandlerMethod handlerMethod = (HandlerMethod) handler;		//把handler参数强转
	    Method method = handlerMethod.getMethod();					//获取到请求方法对象

		* 如果说请求的不是Controller,例如:请求静态资源触发了拦截器.这个强转会报错
	
	2,同样也是获取请求方法
		