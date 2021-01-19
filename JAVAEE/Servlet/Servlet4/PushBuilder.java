-------------------
PushBuilder			|
-------------------	
	# 创建
		PushBuilder pushBuilder = request.newPushBuilder();

		* 如果服务器推送不可用,newPushBuilder() 将返回 null 
		* 如果客户端没有使用安全连接,服务器推送也不会起作用
	
	# demo
		// push 当前页面需要的静态资源
		PushBuilder pushBuilder = req.newPushBuilder();
		if (pushBuilder != null) {
			pushBuilder.path("static/images/index.jpg").push();
			pushBuilder.path("static/css/index.css").push();
			pushBuilder.path("static/js/index.js").push();
		}

	# 接口方法
		public PushBuilder method(String method);

		public PushBuilder queryString(String queryString);

		public PushBuilder sessionId(String sessionId);
		
		public PushBuilder setHeader(String name, String value);
	 
		public PushBuilder addHeader(String name, String value);

		public PushBuilder removeHeader(String name);

		public PushBuilder path(String path);

		public void push();
			* push() 方法是非阻塞的,立即返回

		public String getMethod();

		public String getQueryString();

		public String getSessionId();

		public Set<String> getHeaderNames();

		public String getHeader(String name);
	  
		public String getPath();

