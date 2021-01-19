
# 添加了 GenericFilter 和 HttpFilter 抽象类
	* 这些抽象类通过最低限度地实现生命周期方法 init() 和 destroy() 
	* 简化了编写过滤器

# ServletContext 接口采用了一些新方法

	ServletRegistration.Dynamic addJspFile(String servletName, String jspFile);
		* 可将带有给定 JSP 文件的 servlet 添加到 servlet 上下文中
	
	int getSessionTimeout();
	void setSessionTimeout(int sessionTimeout);
		* 设置和读取session超时时间

	String getRequestCharacterEncoding();
	void setRequestCharacterEncoding(String encoding);
		* 设置和读取默认的request的编码