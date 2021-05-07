ContentCachingResponseWrapper
	* 可以缓存响应数据的 HttpServletResponse
	* 方法
		void copyBodyToResponse()
			* 把缓存的内容响应给客户端

		byte[] getContentAsByteArray()
		InputStream getContentInputStream()
		int getContentSize()

