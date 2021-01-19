---------------------------------
AbstractRequestLoggingFilter	 |
---------------------------------
	# 抽象的请求, 响应日志过滤器
		void setIncludeQueryString(boolean includeQueryString)
		void setIncludeClientInfo(boolean includeClientInfo)
		void setIncludeHeaders(boolean includeHeaders)
		void setIncludePayload(boolean includePayload)
		void setMaxPayloadLength(int maxPayloadLength) 

		void setBeforeMessagePrefix(String beforeMessagePrefix)
		void setBeforeMessageSuffix(String beforeMessageSuffix)

		void setAfterMessagePrefix(String afterMessagePrefix)
		void setAfterMessageSuffix(String afterMessageSuffix)


		protected void setAfterMessageSuffix(String afterMessageSuffix)

		protected boolean shouldNotFilterAsyncDispatch()
		protected boolean shouldLog(HttpServletRequest request)
			* 判断当前的请求是否要打印日志信息

