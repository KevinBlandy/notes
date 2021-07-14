-----------------------------------
ResponseEntityExceptionHandler 
-----------------------------------
	# 系统预定义的Exception处理器。返回值类型就是 ResponseEntity
	# 它对很多异常都进行了预处理
		@ExceptionHandler(value={
				NoSuchRequestHandlingMethodException.class,
				HttpRequestMethodNotSupportedException.class,
				HttpMediaTypeNotSupportedException.class,
				HttpMediaTypeNotAcceptableException.class,
				MissingServletRequestParameterException.class,
				ServletRequestBindingException.class,
				ConversionNotSupportedException.class,
				TypeMismatchException.class,
				HttpMessageNotReadableException.class,
				HttpMessageNotWritableException.class,
				MethodArgumentNotValidException.class,
				MissingServletRequestPartException.class,
				BindException.class,
				NoHandlerFoundException.class
			})
		public final ResponseEntity<Object> handleException(Exception ex, WebRequest request) {...}
		
		
		* 它会通过对异常类型的判断，调用不同的异常处理方法
		* 默认，都是调用 handleExceptionInternal 这个方法
				protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body,
						HttpHeaders headers, HttpStatus status, WebRequest request) {

					if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
						request.setAttribute("javax.servlet.error.exception", ex, WebRequest.SCOPE_REQUEST);
					}

					return new ResponseEntity<Object>(body, headers, status);
				}
		
		* 一般使用，可以继承它，添加 @ControllerAdvice 注解，然后复写需要处理的异常方法就行