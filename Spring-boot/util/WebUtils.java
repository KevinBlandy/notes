---------
WebUtils |
---------
	# 包含了web项目中的大量方法和属性

		public static final String INCLUDE_REQUEST_URI_ATTRIBUTE = "javax.servlet.include.request_uri";
		public static final String INCLUDE_CONTEXT_PATH_ATTRIBUTE = "javax.servlet.include.context_path";
		public static final String INCLUDE_SERVLET_PATH_ATTRIBUTE = "javax.servlet.include.servlet_path";
		public static final String INCLUDE_PATH_INFO_ATTRIBUTE = "javax.servlet.include.path_info";
		public static final String INCLUDE_QUERY_STRING_ATTRIBUTE = "javax.servlet.include.query_string";
		public static final String FORWARD_REQUEST_URI_ATTRIBUTE = "javax.servlet.forward.request_uri";
		public static final String FORWARD_CONTEXT_PATH_ATTRIBUTE = "javax.servlet.forward.context_path";
		public static final String FORWARD_SERVLET_PATH_ATTRIBUTE = "javax.servlet.forward.servlet_path";
		public static final String FORWARD_PATH_INFO_ATTRIBUTE = "javax.servlet.forward.path_info";
		public static final String FORWARD_QUERY_STRING_ATTRIBUTE = "javax.servlet.forward.query_string";
		public static final String ERROR_STATUS_CODE_ATTRIBUTE = "javax.servlet.error.status_code";
		public static final String ERROR_EXCEPTION_TYPE_ATTRIBUTE = "javax.servlet.error.exception_type";
		public static final String ERROR_MESSAGE_ATTRIBUTE = "javax.servlet.error.message";
		public static final String ERROR_EXCEPTION_ATTRIBUTE = "javax.servlet.error.exception";
		public static final String ERROR_REQUEST_URI_ATTRIBUTE = "javax.servlet.error.request_uri";
		public static final String ERROR_SERVLET_NAME_ATTRIBUTE = "javax.servlet.error.servlet_name";
		public static final String CONTENT_TYPE_CHARSET_PREFIX = ";charset=";
		public static final String DEFAULT_CHARACTER_ENCODING = "ISO-8859-1";
		public static final String TEMP_DIR_CONTEXT_ATTRIBUTE = "javax.servlet.context.tempdir";
		public static final String HTML_ESCAPE_CONTEXT_PARAM = "defaultHtmlEscape";
		public static final String RESPONSE_ENCODED_HTML_ESCAPE_CONTEXT_PARAM = "responseEncodedHtmlEscape";
		public static final String WEB_APP_ROOT_KEY_PARAM = "webAppRootKey";
		public static final String DEFAULT_WEB_APP_ROOT_KEY = "webapp.root";
		public static final String[] SUBMIT_IMAGE_SUFFIXES = {".x", ".y"};
		public static final String SESSION_MUTEX_ATTRIBUTE = WebUtils.class.getName() + ".MUTEX";
	
		void setWebAppRootSystemProperty(ServletContext servletContext)
		void removeWebAppRootSystemProperty(ServletContext servletContext)
		Boolean getDefaultHtmlEscape(@Nullable ServletContext servletContext)
		Boolean getResponseEncodedHtmlEscape(@Nullable ServletContext servletContext)
		File getTempDir(ServletContext servletContext
		String getRealPath(ServletContext servletContext, String path)
		String getSessionId(HttpServletRequest request)
		Object getSessionAttribute(HttpServletRequest request, String name)
		Object getRequiredSessionAttribute(HttpServletRequest request, String name)
		void setSessionAttribute(HttpServletRequest request, String name, @Nullable Object value)
		Object getSessionMutex(HttpSession session)
		<T> T getNativeRequest(ServletRequest request, @Nullable Class<T> requiredType) 
		<T> T getNativeResponse(ServletResponse response, @Nullable Class<T> requiredType)
		boolean isIncludeRequest(ServletRequest request)
		void exposeErrorRequestAttributes(HttpServletRequest request, Throwable ex, @Nullable String servletName)
		void exposeRequestAttributeIfNotPresent(ServletRequest request, String name, Object value)
		void clearErrorRequestAttributes(HttpServletRequest request)
		Cookie getCookie(HttpServletRequest request, String name)
		boolean hasSubmitParameter(ServletRequest request, String name)
		String findParameterValue(ServletRequest request, String name)
		String findParameterValue(Map<String, ?> parameters, String name)
		Map<String, Object> getParametersStartingWith(ServletRequest request, @Nullable String prefix)
		MultiValueMap<String, String> parseMatrixVariables(String matrixVariables)
		boolean isValidOrigin(HttpRequest request, Collection<String> allowedOrigins
		boolean isSameOrigin(HttpRequest request)
		int getPort(@Nullable String scheme, int port)
