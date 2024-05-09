---------------------------
ȫ���쳣-1					|
---------------------------
	1,�Զ����� GlobalDefaultExceptionHandler
	2,���ʶע�� @ControllerAdvice
	3,�����ϱ�ʶע�� @ExceptionHandler(value=Exception.class)
		* �����ж������,��ͬ�ķ�������ʶ��ע��,����ָ����ͬ�� Exception,
		* �쳣ʱ,����ݲ�ͬ�� Exception �ҵ���Ӧ�ķ������д���

	4,����
		@ControllerAdvice  
		@ResponseBody  
		public class ExceptionAdvice {  
		  
			/** 
			 * 400 - Bad Request 
			 */  
			@ResponseStatus(HttpStatus.BAD_REQUEST)  
			@ExceptionHandler(HttpMessageNotReadableException.class)  
			public Response handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {  
				logger.error("��������ʧ��", e);  
				return new Response().failure("could_not_read_json");  
			}  

			// NoResourceFoundException  �°汾���ӵ��쳣�࣬���Դ���̬��Դ�����ڵ��쳣
			@ExceptionHandler(NoResourceFoundException.class)
			public Response notFound(HttpServletRequest request, HttpServletResponse response,
					NoResourceFoundException exception) {
			}
		  
			/** 
			 * 405 - Method Not Allowed 
			 */  
			@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)  
			@ExceptionHandler(HttpRequestMethodNotSupportedException.class)  
			public Response handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {  
				logger.error("��֧�ֵ�ǰ���󷽷�", e);  
				return new Response().failure("request_method_not_supported");  
			}  
		  
			/** 
			 * 415 - Unsupported Media Type 
			 */  
			@ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)  
			@ExceptionHandler(HttpMediaTypeNotSupportedException.class)  
			public Response handleHttpMediaTypeNotSupportedException(Exception e) {  
				logger.error("��֧�ֵ�ǰý������", e);  
				return new Response().failure("content_type_not_supported");  
			}  
		  
			/** 
			 * 500 - Internal Server Error 
			 */  
			@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)  
			@ExceptionHandler(Exception.class)  
			public Response handleException(Exception e) {  
				logger.error("���������쳣", e);  
				return new Response().failure(e.getMessage());  
			}  
		}  
	
	5,����ֵ������ String,ModelAndView,�����Ҫ����JSON����,��Ҫ�Լ���� @ResponseBody ע�⵽����

---------------------------
ȫ���쳣-2					|
---------------------------
	# ʵ�ֽӿ�,ErrorController
		* ��д����,����ֵΪ�쳣·��
		* �쳣ʱ,��ܻ��Զ��������·��,����Я���쳣��Ϣ
		@Controller
		public class ExceptionController implements ErrorController{
			
			private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionController.class);
			
			/**
			 * �쳣ҳ��
			 */
			private static final String ERROR_PATH = "error";
			
			@RequestMapping(value = ERROR_PATH,method = {RequestMethod.POST,RequestMethod.GET})
			@SuppressWarnings({ "rawtypes" })
			@NoLogin
			public ModelAndView error(HttpServletRequest request,
									  HttpServletResponse response) throws IOException{
				Class exceptionType = (Class) request.getAttribute("javax.servlet.error.exception_type");   //�쳣����
				Throwable exception = (Throwable) request.getAttribute("javax.servlet.error.exception");    //�쳣��,ֻ���� 500 �쳣�������,��ֵ��Ϊ��
				Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");     //HTTP�쳣״̬��
		//		String servletName = (String) request.getAttribute("javax.servlet.error.servlet_name");     //�쳣��Servlet
				String erroPath = (String) request.getAttribute("javax.servlet.error.request_uri");         //�������쳣�������ַ(�����ǵ�ǰ��ַ-request.getRequestURI())
				
				LOGGER.error("�������쳣 exceptionType={},exception={},code={},path={}",exceptionType,exception,statusCode,erroPath);
				
				/**
				 * ��ֹ�����������·����(��С��)�Ƚٳֲ���ʾ�Լ��Ĵ���ҳ��,ǿ�ƽ�code����Ϊ200
				 */
				response.setStatus(HttpServletResponse.SC_OK);
				HttpMessage<Void> message = null;
				if(statusCode == 400){
					message = StandardHttpMessages.BAD_REQUEST;					//����У��ʧ��
				}else if(statusCode == 404){
					message = StandardHttpMessages.NOT_FOND;					//����ҳ��δ�ҵ�
				}else if(statusCode == 405){
					message = StandardHttpMessages.METHOD_NOT_SUPPORT;			//���󷽷�����
				}else if(statusCode > 400 && statusCode < 500){
					message = StandardHttpMessages.REQUEST_ERROR;				//ͳһ�����쳣
				}else{
					message = StandardHttpMessages.SYSTEM_ERROR;				//ϵͳ�쳣
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