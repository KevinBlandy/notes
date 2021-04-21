-----------------------
MultiTenantHandler
-----------------------
	# 多租户handler： interface MultiTenantHandler extends Handler<RoutingContext> 


	String TENANT = "tenant";
		* 数据默认存在在RoutingContext中的key

	
	static MultiTenantHandler create(String header)
	static MultiTenantHandler create(Function<RoutingContext, String> tenantExtractor)
	static MultiTenantHandler create(Function<RoutingContext, String> tenantExtractor, String contextKey)
		* 可以自定义header, key, 执行器(通过返回值作为value)来创建handler

	MultiTenantHandler addTenantHandler(String tenant, Handler<RoutingContext> handler)
	MultiTenantHandler removeTenant(String tenant)
		* 根据value，创建/删除对应的处理器

	MultiTenantHandler addDefaultHandler(Handler<RoutingContext> handler)
		* 添加默认的处理器
	


	# Demo
		MultiTenantHandler.create("X-Tenant")			// 监听header
		  .addTenantHandler("tenant-A", ctx -> {		// 处理 X-Tenant: tenant-A 的请求
			// do something for tenant A...
		  })
		  .addTenantHandler("tenant-B", ctx -> {		// 处理 X-Tenant: tenant-B 的请求
			// do something for tenant B...
		  })
		  // optionally
		  .addDefaultHandler(ctx -> {					// 处理默认请求
			// do something when no tenant matches...
		  });

