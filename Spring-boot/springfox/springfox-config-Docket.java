--------------------------
swagger-Docket
--------------------------
	# 一个Docket表示一个文档
		http://springfox.github.io/springfox/javadoc/2.9.2/springfox/documentation/spring/web/plugins/Docket.html


	# Docket 方法
			// 根据文档类型创建 Docket
			Docket docket = new Docket(DocumentationType.SWAGGER_2);
			docket.additionalModels(first, remaining)
			docket.alternateTypeRules(AlternateTypeRule... alternateTypeRules)
				* 添加模型替换规则

			docket.apiDescriptionOrdering(apiDescriptionOrdering)
			docket.apiInfo(apiInfo);
				* 设置 apiInfo

			docket.apiListingReferenceOrdering(apiListingReferenceOrdering)
			docket.configure(builder)
			docket.consumes(consumes)
			docket.directModelSubstitute(final Class clazz, final Class with)
				* 直接用提供的替换项替换模型类
				* 例如:
					directModelSubstitute(LocalDate.class, Date.class)

			docket.enable(externallyConfiguredFlag)
			docket.isEnabled()
					* 是否启用此swagger
					* 默认 true				

			docket.enableUrlTemplating(enabled)
			docket.extensions(vendorExtensions)
			docket.forCodeGeneration(forCodeGen)
				* 将其设置为true, 会使用 CodeGenGenericTypeNamingStrategy 代替默认的 DefaultGenericTypeNamingStrategy
				* 可以便使文档代码生成友好		

			docket.genericModelSubstitutes(genericClasses)
			docket.getDocumentationType()
			docket.getGroupName()
			docket.globalOperationParameters(operationParameters)
			docket.globalResponseMessage(requestMethod, responseMessages)
			docket.groupName(groupName)
					* 如果存在多个Docket实例, 则每个实例都必须具有此方法提供的唯一的groupName

			docket.host(host)
			docket.ignoredParameterTypes(classes)
			
			docket.operationOrdering(operationOrdering)
			docket.pathMapping(String path)
				* 可扩展性机制, 用于将servlet路径映射(如果有)添加到api根路径

			docket.pathProvider(pathProvider)
			docket.produces(produces)
			docket.protocols(protocols)
				* 支持的协议

			docket.securityContexts(securityContexts)
			docket.securitySchemes(List<? extends SecurityScheme> securitySchemes)

			docket.select()
				.apis(RequestHandlerSelectors.basePackage("io.springboot.swagger.controller"))
				.paths(PathSelectors.any());

				* 设置扫描器和过滤器

			docket.supports(delimiter)
			docket.tags(first, remaining)
			docket.useDefaultResponseMessages(boolean apply)
				* 是否是否默认的响应状态码
				* 默认 true
				
	

	# 扫描器和过滤器的直接实现
		RequestHandlerSelectors
			static Predicate<RequestHandler> any()
			static Predicate<RequestHandler> none()
			static Predicate<RequestHandler> withMethodAnnotation(final Class<? extends Annotation> annotation)
			static Predicate<RequestHandler> withClassAnnotation(final Class<? extends Annotation> annotation)
			static Predicate<RequestHandler> basePackage(final String basePackage)
		
		PathSelectors
			static Predicate<String> any()
			static Predicate<String> none()
			static Predicate<String> regex(final String pathRegex)
			static Predicate<String> ant(final String antPattern)
	

	
	# 一个 Swagge的 Docket对象, 就是一个系列的文档, 通过 groupName 来区分
