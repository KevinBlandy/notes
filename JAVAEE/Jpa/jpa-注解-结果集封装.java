---------------------
结果集的封装
---------------------
	@SqlResultSetMapping
		String name(); 
			* 指定一个映射的规则的名称
		EntityResult[] entities() default {};
			* 指定实体的映射规则集合
		ConstructorResult[] classes() default {};
			* 指定构造器的映射规则集合
		ColumnResult[] columns() default {};
			* 指定列属性的映射规则集合

		* 映射规则的定义, 它定义在实体类上
		* 可以通过 @SqlResultSetMappings 定义多个
	
	@EntityResult
		Class entityClass(); 
		FieldResult[] fields() default {};
		String discriminatorColumn() default "";

		* 实体的映射属性
	
	@ColumnResult
		String name();
		Class type() default void.class;
	
	@ConstructorResult
		Class targetClass();
		ColumnResult[] columns();