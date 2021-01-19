------------------------
ResponseBodyAdvice		|
------------------------
	# ResponseBody增强接口
	# 可以在 @ResponseBody 响应客户端之前做一些事情,只需要实现该接口,然后注册到spring-mvc子IOC中
		ResponseBodyAdvice<T>{
			boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType);

			T beforeBodyWrite(T body, MethodParameter returnType, MediaType selectedContentType,
				Class<? extends HttpMessageConverter<?>> selectedConverterType,
				ServerHttpRequest request, ServerHttpResponse response);
		}
	# 例如:JSONP,加密,等等.... ...
	# Fastjson 有实现


