------------------------
CorsHandler
------------------------
	# 跨域handler： interface CorsHandler extends Handler<RoutingContext>
	
	static CorsHandler create()
	static CorsHandler create(String allowedOriginPattern) 

	CorsHandler addOrigin(String origin);
	CorsHandler addOrigins(List<String> origins);
	CorsHandler allowedMethod(HttpMethod method);
	CorsHandler allowedMethods(Set<HttpMethod> methods);
	CorsHandler allowedHeader(String headerName);
	CorsHandler allowedHeaders(Set<String> headerNames);
	CorsHandler exposedHeader(String headerName);
	CorsHandler exposedHeaders(Set<String> headerNames);
	CorsHandler allowCredentials(boolean allow);
	CorsHandler maxAgeSeconds(int maxAgeSeconds);


	# 自定义的比较好使
		.handler(ctx -> {
			String origin = ctx.request().getHeader(HttpHeaders.ORIGIN);
			if (!StringUtil.isNullOrEmpty(origin)) {
				// 允许的源站
				ctx.response().headers().set(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, origin);
				// 允许携带的请求头
				String accessControlRequestHeaders = ctx.request().getHeader(HttpHeaders.ACCESS_CONTROL_REQUEST_HEADERS);
				if (!StringUtil.isNullOrEmpty(accessControlRequestHeaders)) {
					ctx.response().headers().set(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, accessControlRequestHeaders);
				}
				// 允许访问的请求头
				ctx.response().headers().set(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "*");
				// 允许携带凭证
				ctx.response().headers().set(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
				// 允许请求方法
				ctx.response().headers().set(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "GET, POST, PUT, OPTIONS, DELETE, PATCH");
				if (ctx.request().method() == HttpMethod.OPTIONS) {
					ctx.response().setStatusCode(204).end();
					return;
				}
			}
			ctx.next();
		})