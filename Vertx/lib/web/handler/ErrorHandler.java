--------------------------
ErrorHandler
--------------------------
	# 异常处理器接口： interface ErrorHandler extends Handler<RoutingContext> 
		* 会自动渲染异常页面

	
	String DEFAULT_ERROR_HANDLER_TEMPLATE = "META-INF/vertx/web/vertx-web-error.html";
		* 默认的异常页面，默认在 vertx-web 模块下

	static ErrorHandler create(Vertx vertx) 
	static ErrorHandler create(Vertx vertx, String errorTemplateName, boolean displayExceptionDetails)
	static ErrorHandler create(Vertx vertx, boolean displayExceptionDetails)
	static ErrorHandler create(Vertx vertx, String errorTemplateName)
		* 创建异常处理器
			errorTemplateName			从classpath找模板引擎
			displayExceptionDetails		如果设置为true，则会显示细节
		
			