---------------------------
全局异常-1					|
---------------------------
	1,自定义类 GlobalDefaultExceptionHandler
	2,类标识注解 @ControllerAdvice
	3,方法上标识注解 @ExceptionHandler(value=Exception.class)
		* 可以有多个方法,不同的方法都标识该注解,并且指定不同的 Exception,
		* 异常时,会根据不同的 Exception 找到对应的方法进行处理

	4,代码
		@ControllerAdvice  
		@ResponseBody  
		public class ExceptionAdvice {  
		  
			/** 
			 * 400 - Bad Request 
			 */  
			@ResponseStatus(HttpStatus.BAD_REQUEST)  
			@ExceptionHandler(HttpMessageNotReadableException.class)  
			public Response handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {  
				logger.error("参数解析失败", e);  
				return new Response().failure("could_not_read_json");  
			}  
		  
			/** 
			 * 405 - Method Not Allowed 
			 */  
			@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)  
			@ExceptionHandler(HttpRequestMethodNotSupportedException.class)  
			public Response handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {  
				logger.error("不支持当前请求方法", e);  
				return new Response().failure("request_method_not_supported");  
			}  
		  
			/** 
			 * 415 - Unsupported Media Type 
			 */  
			@ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)  
			@ExceptionHandler(HttpMediaTypeNotSupportedException.class)  
			public Response handleHttpMediaTypeNotSupportedException(Exception e) {  
				logger.error("不支持当前媒体类型", e);  
				return new Response().failure("content_type_not_supported");  
			}  
		  
			/** 
			 * 500 - Internal Server Error 
			 */  
			@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)  
			@ExceptionHandler(Exception.class)  
			public Response handleException(Exception e) {  
				logger.error("服务运行异常", e);  
				return new Response().failure(e.getMessage());  
			}  
		}  
	
	5,返回值可以是 String,ModelAndView,如果需要返回JSON数据,需要自己添加 @ResponseBody 注解到方法

---------------------------
全局异常-2					|
---------------------------
	# 实现接口,ErrorController
		* 覆写方法,返回值为异常路径
		* 异常时,框架会自动的请求该路径,并且携带异常信息
		@Controller
		public class ExceptionController implements ErrorController{
			
			private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionController.class);
			
			/**
			 * 异常页面
			 */
			private static final String ERROR_PATH = "error";
			
			@RequestMapping(value = ERROR_PATH,method = {RequestMethod.POST,RequestMethod.GET})
			@SuppressWarnings({ "rawtypes" })
			@NoLogin
			public ModelAndView error(HttpServletRequest request,
									  HttpServletResponse response) throws IOException{
				Class exceptionType = (Class) request.getAttribute("javax.servlet.error.exception_type");   //异常类型
				Throwable exception = (Throwable) request.getAttribute("javax.servlet.error.exception");    //异常类,只有在 500 异常的清空下,该值不为空
				Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");     //HTTP异常状态码
		//		String servletName = (String) request.getAttribute("javax.servlet.error.servlet_name");     //异常的Servlet
				String erroPath = (String) request.getAttribute("javax.servlet.error.request_uri");         //发生了异常的请求地址(并不是当前地址-request.getRequestURI())
				
				LOGGER.error("服务器异常 exceptionType={},exception={},code={},path={}",exceptionType,exception,statusCode,erroPath);
				
				/**
				 * 防止部分浏览器、路由器(如小米)等劫持不显示自己的错误页面,强制将code设置为200
				 */
				response.setStatus(HttpServletResponse.SC_OK);
				HttpMessage<Void> message = null;
				if(statusCode == 400){
					message = StandardHttpMessages.BAD_REQUEST;					//参数校验失败
				}else if(statusCode == 404){
					message = StandardHttpMessages.NOT_FOND;					//请求页面未找到
				}else if(statusCode == 405){
					message = StandardHttpMessages.METHOD_NOT_SUPPORT;			//请求方法错误
				}else if(statusCode > 400 && statusCode < 500){
					message = StandardHttpMessages.REQUEST_ERROR;				//统一请求异常
				}else{
					message = StandardHttpMessages.SYSTEM_ERROR;				//系统异常
				}
				if(GeneralUtils.isAjax(request)){
					response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
					response.getWriter().write(JsonUtils.beanToJson(message));
					response.flushBuffer();
					return null;
				}
				ModelAndView modelAndView = new ModelAndView("error/error");
				modelAndView.addObject("message", message);
				return modelAndView;
			}
			
			@Override
			public String getErrorPath() {
				return ERROR_PATH;
			}
		}
	
	org.springframework.web.util.WebUtils

	static final String JAVAX_SERVLET_ERROR_SERVLET_NAME = "javax.servlet.error.servlet_name";
    static final String JAVAX_SERVLET_ERROR_EXCEPTION = "javax.servlet.error.exception";
    static final String JAVAX_SERVLET_ERROR_REQUEST_URI = "javax.servlet.error.request_uri";
    static final String JAVAX_SERVLET_ERROR_MESSAGE = "javax.servlet.error.message";
    static final String JAVAX_SERVLET_ERROR_EXCEPTION_TYPE = "javax.servlet.error.exception_type";
    static final String JAVAX_SERVLET_ERROR_STATUS_CODE = "javax.servlet.error.status_code";

	Throwable throwable = (Throwable) request.getAttribute(JAVAX_SERVLET_ERROR_EXCEPTION);
	Integer statusCode = (Integer) request.getAttribute(JAVAX_SERVLET_ERROR_STATUS_CODE);
	String errorUri = (String) request.getAttribute(JAVAX_SERVLET_ERROR_REQUEST_URI);
	String servletName = (String) request.getAttribute(JAVAX_SERVLET_ERROR_SERVLET_NAME);
	String errorMessage = (String) request.getAttribute(JAVAX_SERVLET_ERROR_MESSAGE);
	Class<? extends Throwable> throwableType = (Class<? extends Throwable>) request.getAttribute(JAVAX_SERVLET_ERROR_EXCEPTION_TYPE);