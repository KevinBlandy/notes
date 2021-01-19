-------------------
Annotation
-------------------
	@Expose
		boolean serialize() default true;
		boolean deserialize() default true;
		
		* 标识在对象字段上
		* 是否序列化, 反序列化该字段
		* new Gson()方式创建Gson则 @Expose 则没有任何效果,
		* 只有采用GsonBuilder创建Gson并且调用了excludeFieldsWithoutExposeAnnotation
		* 则@Expose将会影响toJson和fromGson序列化和反序列化数据



	@SerializedName
		String value();
			* 序列化或反序列化时字段的名称

		String[] alternate() default {};
			* 反序列化时字段的备用名称

		* 对序列字段进行重命名
		* 还可以用来标识在枚举的实例上, 覆写枚举的序列化/反序列化值
	
	@Since
		double value();

		* 版本号注解, 标识在类, 字段上
	
	@JsonAdapter 

		Class<?> value();
			* 指定 JsonDeserializer 或者 JsonSerializer 的实现类
			* 用于自定义序列化或者反序列化的策略

		boolean nullSafe() default true;

		* 通过注解指定序列化策略, 标识在类, 字段上
			