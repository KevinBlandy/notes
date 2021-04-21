-------------------------------
ResponseContentTypeHandler 
-------------------------------
	# 自动添加Response的Content-Type头： interface ResponseContentTypeHandler extends Handler<RoutingContext> 
		* 本质上就是，根据客户端的accepet头，设置contentType头
		* 还是需要自己在业务handler中判断accept头来进行不同的数据响应


	String DEFAULT_DISABLE_FLAG = "__vertx.autoContenType.disable";
		* 默认禁用的flag
		* 如果RoutingContext域中有这个flag，值非null，那么就会忽略这个handler
	
	static ResponseContentTypeHandler create()
	static ResponseContentTypeHandler create(String disableFlag)

