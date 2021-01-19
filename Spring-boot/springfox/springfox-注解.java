----------------------
注解
----------------------
	# 标识注解
		@Api				标记类上，表明是资源
		@ApiImplicitParam	表示API操作中的单个参数。
		@ApiImplicitParams	允许多个参数列表
		@ApiModel			针对实体model提供额外信息
		@ApiModelProperty	添加操作数据或者model属性
		@ApiOperation		描述HTTP方法，通常针对特定的路径
		@ApiParam			对于操作添加额外的信息
		@ApiResponse		描述一个操作的响应
		@ApiResponses		允许多个参数列表，描述响应对象
		@Authorization		使用在类上或者方法上，表示授权信息
		@AuthorizationScope	描述 OAuth2 的授权作用域
		@ResponseHeader		代表响应头的部分信息

----------------------
注解
----------------------
	@Api
		* 标识在controller

		String value() default "";
			* 用于兼容旧版的标签
		String[] tags() default "";
			* 用于分组, 一个controller 可以属于多个组
			* 如果设置了 tags 属性, 则value属性会被忽略

		String produces() default "";
		String consumes() default "";
		String protocols() default "";
		Authorization[] authorizations() default @Authorization(value = "");
		boolean hidden() default false;

	@ApiImplicitParam
		String name() default "";
		String value() default "";
		String defaultValue() default "";
		String allowableValues() default "";
		boolean required() default false;
		String access() default "";
		boolean allowMultiple() default false;
		String dataType() default "";
		Class<?> dataTypeClass() default Void.class;
		String paramType() default "";
		String example() default "";
		Example examples() default @Example(value = @ExampleProperty(mediaType = "", value = ""));
		String type() default "";
		String format() default "";
		boolean allowEmptyValue() default false;
		boolean readOnly() default false;
		String collectionFormat() default "";

		
		* 可以用过 @ApiImplicitParams 一次性定义多个
			ApiImplicitParam[] value();

	@ApiKeyAuthDefinition

	@ApiModel
		* 给实体类, 添加实体注释

		String value() default "";
		String description() default "";
			* 描述

		Class<?> parent() default Void.class;
		String discriminator() default "";
		Class<?>[] subTypes() default {};
		String reference() default "";

	@ApiModelProperty
		* 实体类中, 给字段添加的注释
		* 可以被继承
		
		String value() default "";
		String name() default "";
		String allowableValues() default "";
		String access() default "";
		String notes() default "";
		String dataType() default "";
		boolean required() default false;
		int position() default 0;
		boolean hidden() default false;
		String example() default "";
		AccessMode accessMode() default AccessMode.AUTO;
			AUTO,
			READ_ONLY,
			READ_WRITE;
		
		String reference() default "";
		boolean allowEmptyValue() default false;
		Extension[] extensions() default @Extension(properties = @ExtensionProperty(name = "", value = ""));

	@ApiOperation
		* 给controller方法添加的注释

		String value();
			* 名字
		String notes() default "";
			* 描述
		String[] tags() default "";
			* 标签
		Class<?> response() default Void.class;
			* 手动设置响应类型
		String responseContainer() default "";
		String responseReference() default "";
		String httpMethod() default "";
		String nickname() default "";
		String produces() default "";
		String consumes() default "";
		String protocols() default "";
		Authorization[] authorizations() default @Authorization(value = "");
		boolean hidden() default false;
		ResponseHeader[] responseHeaders() default @ResponseHeader(name = "", response = Void.class);
		int code() default 200;
		Extension[] extensions() default @Extension(properties = @ExtensionProperty(name = "", value = ""));
		boolean ignoreJsonView() default false;

	@ApiIgnore
		
		* 可以标识在类, 方法, 参数上, 用于在文档中忽略该controller, action, param

		String value() default "";

	@ApiParam
		* 给controller方法的请求参数添加的注释


		String name() default "";
		String value() default "";
		String defaultValue() default "";
		String allowableValues() default "";
		boolean required() default false;
		String access() default "";
		boolean allowMultiple() default false;
		boolean hidden() default false;
		String example() default "";
		Example examples() default @Example(value = @ExampleProperty(mediaType = "", value = ""));
		String type() default "";
		String format() default "";
		boolean allowEmptyValue() default false;
		boolean readOnly() default false;
		String collectionFormat() default "";


