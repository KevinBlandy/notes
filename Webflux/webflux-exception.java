-------------------------
异常处理
-------------------------
	# 异常处理接口: WebExceptionHandler
		public interface WebExceptionHandler {
			Mono<Void> handle(ServerWebExchange exchange, Throwable ex);
		}

		* 它可以处理Filter/Handler的异常
		* 自定义实现，通过注解添加到IOC
		* 可以有多个，通过 @Order 设置执行顺序
	
	# 异常处理实现体系
		interface WebExceptionHandler
			|-interface ErrorWebExceptionHandler
				|-abstract AbstractErrorWebExceptionHandler abstract
					|-DefaultErrorWebExceptionHandler

		
	# DefaultErrorWebExceptionHandler 是默认注册的异常处理器

--------------------------------------------------
ResponseStatusExceptionHandler
--------------------------------------------------
	# 系统预定义的异常实现，通过设置对异常的 HTTP 状态代码的响应来提供对类型异常的处理 
		public class ResponseStatusExceptionHandler implements WebExceptionHandler
	
	# 方法
		public void setWarnLogCategory(String loggerName)
		@Nullable
		@Deprecated
		protected HttpStatus determineStatus(Throwable ex)
		protected int determineRawStatusCode(Throwable ex) 

--------------------------------------------------
WebFluxResponseStatusExceptionHandler
--------------------------------------------------
	# 扩展 ResponseStatusExceptionHandler 还可以确定 @ResponseStatus 任何异常注释的 HTTP 状态代码。
		public class WebFluxResponseStatusExceptionHandler extends ResponseStatusExceptionHandler 
	
	# 方法
		protected int determineRawStatusCode(Throwable ex)