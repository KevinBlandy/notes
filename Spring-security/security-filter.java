-----------------
Filter			 |
-----------------
	# 系统预定义的部分过滤器
		SecurityContextPersistenceFilter
		WebAsyncManagerIntegrationFilter
		HeaderWriterFilter
		CsrfFilter
		LogoutFilter
		UsernamePasswordAuthenticationFilter
		DefaultLoginPageGeneratingFilter
		DefaultLogoutPageGeneratingFilter
		BasicAuthenticationFilter
		RequestCacheAwareFilter
		SecurityContextHolderAwareRequestFilter
		AnonymousAuthenticationFilter
		SessionManagementFilter
		ExceptionTranslationFilter
		FilterSecurityInterceptor
	
	# 默认过滤器的加载机制
		* 接口 SecurityFilterChain 负责存储所有的Filter
			* 俩接口方法
				boolean matches(HttpServletRequest request);
				List<Filter> getFilters();

			* 默认实现: DefaultSecurityFilterChain

		* FilterChainProxy 通过 SecurityFilterChain 接口, 获取到所有的Filter, 执行