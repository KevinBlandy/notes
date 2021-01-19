-----------------------------
RequestContextHolder		 |
-----------------------------
	# 当前request的一个线程安全容器
	# 获取当前的请求数据信息
		 RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes()

		 * RequestAttributes 是一个接口

	 
	# 获取request和response
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = requestAttributes.getRequest();
		HttpServletResponse response = requestAttributes.getResponse();

		//从 SCOPE_SESSION 里面获取对应的值
		String myValue = (String) requestAttributes.getAttribute("my_value",RequestAttributes.SCOPE_SESSION);

	 
	 
