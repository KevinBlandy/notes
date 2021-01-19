SpringMVC异常处理机制		
	
	
		项目中包括两类异常
			1,预期异常
				* 捕获
			2,运行异常
				* 只能规定代码规范


	DispatcherServlet跟struts2那个前端控制器过滤器是一样的,当头就是一个很大的try
		try
		{
			... ...
		}
		catch ()
		{

		}
	


	ExceptionResolver		--> 全局异常处理器,整个项目只有一个.

	RuntimeException --> 给客户端提供一个提示:未知错误
	自定义 Exception --> 继承 Exception

	跟struts2是通过拦截器实现异常的捕获!逐级抛出,不必捕获

	1,解析异常类型
		|--自定义异常--> 取出异常信息--> View显示
		|--非自定义异常 --> 捕获,抛出自定义异常

	

	2,实现接口 HandlerExceptionResolver
		* 只要是实现了这个接口,那么这个类就是全局异常处理器
			ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex);
		* request 和 response不多解释
		* Object	--> 就是处理器适配器要执行的 Hander
		* Exception --> 就是系统抛出的异常,我们可以获取到这个异常,进行处理
		* (返回的就是 ModelAndView)
		处理策略:
			判断这个 Exception 是不是我们自定义的异常,如果是的话,
			根据异常,定义好,转发页面.获取错误信息就好
			如果不是 RuntimeException,那么自定义异常信息.
	
	3,在xml中配置自定义异常

		<!-- 全局异常处理器 -->
		<bean class="自定义全限定名"/>


	


	这东西只要一注册在IOC容器中,只要是调用链出现了异常,都会来执行这个类的,这个方法!
	springmvc框架的重点在于,在这个方法里面对'异常对象',进行判断!
	struts2框架的重点在于,拦截器.配置,拦截不同的异常类型,配置不同的result


	SpringMVC有三种异常处理的方式
	1,简单的异常处理器
		SimpleMappingExceptionResolver
		<bean class="org.spring....SimpleMappingExceptionResolver">
		<!-- 定义默认的异常处理页面,当该异常类型的注册时使用-->
		<property name="default" value="error"/>
		<!-- 定义异常处理页面来获取异常信息的变量名称,默认为:exception-->
		<property name="exceptionAttribute" value="ex"/>
		<!-- 定义需要特殊处理的异常,使用全限定名作为key,异常页为值-->
		<property>
			<props>
				<prop key="com...Exception">errorjsp</prop>
				<prop key="com...Exception"/>errojsp</prop>
				....还可以配置多个
			</props>
		</property>
		</bean>
	2,实现spring的异常处理接口,定义自己的异常器
		HanderExceptionResolver
	3,使用 ExcetionHander 注解实现异常处理


---------------------------------
---------------------------------
	# 第一种方式
	# 使用 Spring 提供的处理器
	# 仅仅只能处理掉 500 服务器异常,404 之类的不会处理
	# request 域中包含了很多关于异常的信息 
		org.springframework.web.util.WebUtils 中提供了很多常量,就是request域异常信息的key
		public static final String ERROR_STATUS_CODE_ATTRIBUTE = "javax.servlet.error.status_code";
		public static final String ERROR_EXCEPTION_TYPE_ATTRIBUTE = "javax.servlet.error.exception_type";
		public static final String ERROR_MESSAGE_ATTRIBUTE = "javax.servlet.error.message";
		public static final String ERROR_EXCEPTION_ATTRIBUTE = "javax.servlet.error.exception";
		public static final String ERROR_REQUEST_URI_ATTRIBUTE = "javax.servlet.error.request_uri";
		public static final String ERROR_SERVLET_NAME_ATTRIBUTE = "javax.servlet.error.servlet_name";

	@Component
	public class ExceptionController implements HandlerExceptionResolver {

		private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionController.class);
		
		/*
		 * 可以根据请求的类型(Ajax)来确定是返回mv还是直接响应ajax数据
		 * 这种方式,只能处理掉服务器的异常,无法处理 404 之类的异常
		 */
		@Override
		@ExceptionHandler
		public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
			HandlerMethod handlerMethod = (HandlerMethod) o;		//把handler参数强转
			Method method = handlerMethod.getMethod();				//获取到请求异常的方法对象

			return new ModelAndView("error/error");
		}
	}


---------------------------------
---------------------------------
	# 第二种方式
	# 使用 Spring 提供的处理器
	# 能处理各种异常

	import org.springframework.http.converter.HttpMessageNotReadableException;
	import org.springframework.stereotype.Component;

	import javax.servlet.http.HttpServletRequest;
	import javax.servlet.http.HttpServletResponse;

	import org.springframework.http.HttpStatus;

	import org.springframework.web.HttpMediaTypeNotSupportedException;
	import org.springframework.web.HttpRequestMethodNotSupportedException;
	import org.springframework.web.bind.annotation.ControllerAdvice;
	import org.springframework.web.bind.annotation.ExceptionHandler;
	import org.springframework.web.bind.annotation.ResponseStatus;


	@ControllerAdvice 
	@Component
	public class ExceptionAdvice {
		
		/** 
		 * 400 - Bad Request 
		 */  
		@ResponseStatus(HttpStatus.BAD_REQUEST)  
		@ExceptionHandler(HttpMessageNotReadableException.class)  
		public void  handleHttpMessageNotReadableException(HttpServletRequest request,HttpServletResponse response,HttpMessageNotReadableException e) {  
		}  
	  
		/** 
		 * 405 - Method Not Allowed 
		 */  
		@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)  
		@ExceptionHandler(HttpRequestMethodNotSupportedException.class)  
		public void handleHttpRequestMethodNotSupportedException(HttpServletRequest request,HttpServletResponse response,HttpRequestMethodNotSupportedException e) {  
		}  
	  
		/** 
		 * 415 - Unsupported Media Type 
		 */  
		@ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)  
		@ExceptionHandler(HttpMediaTypeNotSupportedException.class)  
		public void handleHttpMediaTypeNotSupportedException(HttpServletRequest request,HttpServletResponse response,HttpMediaTypeNotSupportedException e) {  
		}  
	  
		/** 
		 * 500 - Internal Server Error 
		 */  
		@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)  
		@ExceptionHandler(Exception.class)  
		public void handleException(HttpServletRequest request,HttpServletResponse response,Exception e) {  
			
		}  
	}
