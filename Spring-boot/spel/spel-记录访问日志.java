-------------------------
记录访问日志
-------------------------

-------------------------
OperationLog
-------------------------
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(METHOD)
public @interface SysLog {
	
	String value();
	
}


-------------------------
OperationLogAop
-------------------------
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.jincai.abs.common.constant.HttpHeaders;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class SysLogAop {
	
	@Autowired
	private ApplicationContext applicationContext;

	public static final TemplateParserContext TEMPLATE_PARSER_CONTEXT = new TemplateParserContext("{{", "}}");

	public static final ExpressionParser EXPRESSION_PARSER = new SpelExpressionParser();

	@Pointcut(value = "@annotation(com.jincai.abs.aop.log.SysLog)")
	public void controller() {
	};

	@Around(value = "controller()")
	public Object actionLog(ProceedingJoinPoint joinPoint) throws Throwable {

		Object ret = null;

		try {
			ret = joinPoint.proceed();
			log(joinPoint, ret, null);
		} catch (Throwable e) {
			log(joinPoint, null, e);
			throw e;
		} finally {
//			log(joinPoint, ret, null);
		}
		return ret;

	}

	private void log(ProceedingJoinPoint joinPoint, Object ret, Throwable throwable) {
		
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();

		// 参数
		Object[] args = joinPoint.getArgs();

		// 参数名称
		String[] parameterNames = signature.getParameterNames();

		// 目标方法
		Method targetMethod = signature.getMethod();

		SysLog sysLog = targetMethod.getAnnotation(SysLog.class);

		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();

		String requestId = request.getHeader(HttpHeaders.X_REQUEST_ID);

		try {
			
			StandardEvaluationContext evaluationContext = new StandardEvaluationContext();
			evaluationContext.setBeanResolver(new BeanFactoryResolver(this.applicationContext));
			
			for (int i = 0; i < args.length; i++) {
				evaluationContext.setVariable(parameterNames[i], args[i]);
			}
			
			
			evaluationContext.setVariable("return", ret);
			evaluationContext.setVariable("throwable", throwable);
			
			String logContent = EXPRESSION_PARSER.parseExpression(sysLog.value(), TEMPLATE_PARSER_CONTEXT)
					.getValue(evaluationContext, String.class);

			// TODO 异步存储日志

			log.info("requestId={}, sysLog={}", requestId, logContent);
			
		} catch (Throwable e) {
			log.error("操作日志SpEL表达式解析异常: {}", e.getMessage());
		}
	}
}



-------------------------
Controller
-------------------------
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jincai.abs.aop.log.SysLog;
import com.jincai.abs.model.entity.QFoo;
import com.jincai.abs.service.FooService;


@RestController
@RequestMapping("/api/test")
public class TestController {
	
	@Autowired
	FooService fooService;

	@GetMapping("sysLog")
	@SysLog("查询了 id = {{#id}} 的用户，有 {{(#return?:\"null\".toString())}} 个结果，异常是: {{(#throwable?:\"没有异常\").toString()}}")
	public Object test (@RequestParam("id") Integer id) {
		QFoo qFoo = QFoo.foo;
		if (id < 1) {
			throw new IllegalArgumentException();
		}
		return this.fooService.applyReadOnly(query -> query.select(qFoo).from(qFoo).where(qFoo.id.in(id))).fetch();
	}
}
