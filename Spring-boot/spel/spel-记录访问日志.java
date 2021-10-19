-------------------------
记录访问日志
-------------------------

-------------------------
OperationLog
-------------------------
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OperationLog {
	
	/**
	 * SpEL 模板表达式
	 * 当前HandlerMethod的所有形参都会被放入到SpEL的Context，名称就是方法参数名称。
	 * @return
	 */
	String expression();
}


-------------------------
OperationLogAop
-------------------------
@Aspect  
@Component 
@Order(-1)
@Slf4j
public class OperationLogAop {
	
	public static final TemplateParserContext TEMPLATE_PARSER_CONTEXT = new TemplateParserContext("{{", "}}");
	
	public static final ExpressionParser EXPRESSION_PARSER = new SpelExpressionParser();
	
	
	@Pointcut(value="@annotation(io.springcloud.web.log.OperationLog)")
	public void controller() {};
	
	@Before(value = "controller()")
	public void actionLog (JoinPoint joinPoint) throws Throwable {
		
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();


		
		// 参数
		Object[] args = joinPoint.getArgs();
		
		// 参数名称
		String[] parameterNames = signature.getParameterNames();

		// 目标方法
		Method targetMethod = signature.getMethod();
		
		OperationLog operationLog = targetMethod.getAnnotation(OperationLog.class);
		
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		// HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
		
		String requestId = request.getHeader(io.springcloud.constant.HttpHeaders.X_REQUEST_ID);
		
		try {
			// SpEL解析
	    	EvaluationContext evaluationContext = new StandardEvaluationContext();
			for (int i = 0; i < args.length; i ++) {
				evaluationContext.setVariable(parameterNames[i], args[i]);
			}
			
	    	String logContent = EXPRESSION_PARSER.parseExpression(operationLog.expression(), TEMPLATE_PARSER_CONTEXT).getValue(evaluationContext, String.class);
	    	
			// TODO 异步存储日志
			
			log.info("requestId={}, operationLog={}", requestId, logContent);
		} catch (Exception e) {
			log.error("操作日志SpEL表达式解析异常: {}", e.getMessage());
		}
	}
}


-------------------------
Controller
-------------------------
	@PostMapping
	@OperationLog(expression = "执行了测试操作，account={{ #loginAdmin.account }}"
			+ ", password={{ #loginAdmin.password }}"
			+ ", action={{ #action }}")
	public Object test (HttpServletRequest request,
						HttpServletResponse response,
						@RequestParam("action") String action,
						@RequestBody @Validated LoginAdmin loginAdmin) {
		
		if(ThreadLocalRandom.current().nextBoolean()) {
			throw new ServiceException("随机异常哦");
		}
		return ResponseEntity.ok(loginAdmin);
	}