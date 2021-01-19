
SpringMVC的Handler,处理方法的形参,可以接收如下ServletAPI类型的参数

	HttpServletRequest
	HttpServletResponse
		* 如果使用response给客户端进行相应,那么不能有返回值.才会生效.也就是说视图解析>response直接操作
	HttpSession
	java.security.Principal
	Locale
	InputStream
	OutputStream
	Reader
	Writer

