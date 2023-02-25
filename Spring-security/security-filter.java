------------------------	
架构
------------------------	
	# 架构
		Filter -> DelegatingFilterProxy -> Filter
							↓
					FilterChainProxy
							↓
					SecurityFilterChain -> SecurityFilter -> SecurityFilter


		
	# SecurityFilterChain
		* 接口
			boolean matches(HttpServletRequest request);
			List<Filter> getFilters();
		
		* 一个 Chain，就是一个过滤器链，被 FilterChainProxy 通过 matches 方法来判断当前请求是不是要经过这个过滤器链(getFilters)的处理
		* FilterChainProxy 决定应该使用哪个 SecurityFilterChain。只有第一个匹配的 SecurityFilterChain 被调用。

	

	# 预定义的Filter顺序
		ForceEagerSessionCreationFilter
		ChannelProcessingFilter
		WebAsyncManagerIntegrationFilter
		SecurityContextPersistenceFilter
		HeaderWriterFilter
		CorsFilter
		CsrfFilter
		LogoutFilter
		OAuth2AuthorizationRequestRedirectFilter
		Saml2WebSsoAuthenticationRequestFilter
		X509AuthenticationFilter
		AbstractPreAuthenticatedProcessingFilter
		CasAuthenticationFilter
		OAuth2LoginAuthenticationFilter
		Saml2WebSsoAuthenticationFilter
		UsernamePasswordAuthenticationFilter
		DefaultLoginPageGeneratingFilter
		DefaultLogoutPageGeneratingFilter
		ConcurrentSessionFilter
		DigestAuthenticationFilter
		BearerTokenAuthenticationFilter
		BasicAuthenticationFilter
		RequestCacheAwareFilter
		SecurityContextHolderAwareRequestFilter
		JaasApiIntegrationFilter
		RememberMeAuthenticationFilter
		AnonymousAuthenticationFilter
		OAuth2AuthorizationCodeGrantFilter
		SessionManagementFilter
		ExceptionTranslationFilter
		FilterSecurityInterceptor
		SwitchUserFilter

--------------------------
异常处理
--------------------------
	# ExceptionTranslationFilter
		* ExceptionTranslationFilter 允许将 AccessDeniedException 和 AuthenticationException 翻译成 HTTP 响应。