-------------------
漏洞保护
-------------------
	# 漏洞保护

-------------------
CSRF
-------------------
	# 配置类
		CsrfConfigurer<HttpSecurity>
	
	# 禁用	
		http.csrf().disable();

-------------------
HEADER
-------------------
	# 配置类
		HeadersConfigurer<HttpSecurity>>
	
	# Spring Security提供了一套默认的安全相关的HTTP响应头，以提供安全的默认值。
		Cache-Control: no-cache, no-store, max-age=0, must-revalidate
		Pragma: no-cache
		Expires: 0
		X-Content-Type-Options: nosniff
		Strict-Transport-Security: max-age=31536000 ; includeSubDomains
		X-Frame-Options: DENY
		X-XSS-Protection: 0
	
		* 仅在HTTPS请求中添加 Strict-Transport-Security
	
	# 缓存控制
	# Content Type Options
	# HSTS
	# HPKP
	# X-Frame-Options
	# X-XSS-Protection
	# CSP
	# Referrer Policy
	# Feature Policy
	# Permissions Policy

	# 通过 StaticHeadersWriter 写入自定义header
		http.headers(headers -> headers.addHeaderWriter(new StaticHeadersWriter("X-Custom-Security-Header","header-value")));

	# 通过 DelegatingRequestMatcherHeaderWriter 来指定匹配路由，写入指定的Header
		// 路由
		RequestMatcher matcher = new AntPathRequestMatcher("/login");
		// 指定路由，以及 HeaderWriter
		DelegatingRequestMatcherHeaderWriter headerWriter = new DelegatingRequestMatcherHeaderWriter(matcher, new XFrameOptionsHeaderWriter());
		
		http
			.headers(headers -> {
				// 禁用全局的 XFrameOptionsHeaderWriter
				headers.frameOptions(frameOptions -> frameOptions.disable());
				// 为指定路由开启 XFrameOptionsHeaderWriter
				headers.addHeaderWriter(headerWriter);
			})
			;

	# 禁用header保护
		* 禁用所有
			http.headers(headers -> headers.disable());
		
		* 禁用指定的
			// 禁用 frameOptions
			http.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()));

-------------------
重定向到https
-------------------
	# 配置
		http.requiresChannel(channel -> channel.anyRequest().requiresSecure());
	

-------------------
防火墙
-------------------
	# 防火墙接口
		* HttpFirewall
			FirewalledRequest getFirewalledRequest(HttpServletRequest request) throws RequestRejectedException;
				* 提供将通过过滤器链传递的 request 对象。

			HttpServletResponse getFirewalledResponse(HttpServletResponse response);
				* 提供将通过过滤器链的 request。
	
	# DefaultHttpFirewall - 默认的实现
		
	# StrictHttpFirewall - 严格的实现

		* 这个实现会拒绝那些看起来是恶意的请求。
		
		public Set<String> getDecodedUrlBlacklist()
		public Set<String> getDecodedUrlBlocklist()
		public Set<String> getEncodedUrlBlocklist()
		public void setAllowBackSlash(boolean allowBackSlash)
		public void setAllowedHeaderNames(Predicate<String> allowedHeaderNames)
		public void setAllowedHeaderValues(Predicate<String> allowedHeaderValues) 
		public void setAllowedHostnames(Predicate<String> allowedHostnames)
		public void setAllowedHttpMethods(Collection<String> allowedHttpMethods)
		public void setAllowedParameterNames(Predicate<String> allowedParameterNames)
		public void setAllowedParameterValues(Predicate<String> allowedParameterValues)
		public void setAllowNull(boolean allowNull)
		public void setAllowSemicolon(boolean allowSemicolon)
		public void setAllowUrlEncodedCarriageReturn(boolean allowUrlEncodedCarriageReturn)
		public void setAllowUrlEncodedDoubleSlash(boolean allowUrlEncodedDoubleSlash) 
		public void setAllowUrlEncodedLineFeed(boolean allowUrlEncodedLineFeed) 
		public void setAllowUrlEncodedLineSeparator(boolean allowUrlEncodedLineSeparator)
		public void setAllowUrlEncodedParagraphSeparator(boolean allowUrlEncodedParagraphSeparator)
		public void setAllowUrlEncodedPercent(boolean allowUrlEncodedPercent)
		public void setAllowUrlEncodedPeriod(boolean allowUrlEncodedPeriod) 
		public void setAllowUrlEncodedSlash(boolean allowUrlEncodedSlash)
		public void setUnsafeAllowAnyHttpMethod(boolean unsafeAllowAnyHttpMethod)
		
	
	# 配置
		* 默认情况下，使用 StrictHttpFirewall 实现。
		* 自定义的话，以Bean的形式配置到IOC就行
	
