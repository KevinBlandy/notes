
# ContentCachingRequestWrapper
	* 可以重用读取流的 HttpServletRequest 包装类
	* 必须保证在包装之前, HttpServletRequest 没读取过流, 也就是没执行过 getInputStream() 方法
	* 方法
		byte[] getContentAsByteArray()
	
