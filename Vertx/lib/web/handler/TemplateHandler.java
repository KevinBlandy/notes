--------------------------
TemplateHandler 
--------------------------
	# 模板引擎处理器： interface TemplateHandler extends Handler<RoutingContext> 

	String DEFAULT_TEMPLATE_DIRECTORY = "templates";
		* 默认的模板引擎目录

	String DEFAULT_CONTENT_TYPE = "text/html";
		* 默认的模板引擎COntentType

	String DEFAULT_INDEX_TEMPLATE = "index";
		* 默认的模板引擎名称
	
	
	static TemplateHandler create(TemplateEngine engine) 
	static TemplateHandler create(TemplateEngine engine, String templateDirectory, String contentType)


	TemplateHandler setIndexTemplate(String indexTemplate);


	# Demo
		TemplateHandler handler = TemplateHandler.create(engine);

		router.get("/dynamic").handler(ctx -> {

		  ctx.put("request_path", ctx.request().path());
		  ctx.put("session_data", ctx.session().data());

		  ctx.next();
		});

		router.get("/dynamic/").handler(handler);