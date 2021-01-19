---------------------------------------
RequestContextHolder
---------------------------------------
	# 持有了当前的 RequestContext, 该Contenxt有request/reponse
		
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
	
	# 静态方法
		static RequestAttributes currentRequestAttributes()
		static RequestAttributes getRequestAttributes() 

		static void resetRequestAttributes()

		void setRequestAttributes(@Nullable RequestAttributes attributes)
		static void setRequestAttributes(@Nullable RequestAttributes attributes, boolean inheritable)