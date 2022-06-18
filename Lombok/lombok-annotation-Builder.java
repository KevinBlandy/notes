----------------------------
Builder
----------------------------
	# Builder 构建
	# Builder
		@Target({TYPE, METHOD, CONSTRUCTOR})
		@Retention(SOURCE)
		public @interface Builder {
			@Target(FIELD)
			@Retention(SOURCE)
			public @interface Default {}
				* 如果属性带有默认值，可以在属性上添加这个注解
					@Builder.Default
					private Boolean isSubtotal = false;

			String builderMethodName() default "builder";
				* 获取builder的方法名称

			String buildMethodName() default "build";
				* 构建方法名称

			String builderClassName() default "";
				* builder类名称

			boolean toBuilder() default false;
				* 是否生成toBuilder方法
			
			AccessLevel access() default lombok.AccessLevel.PUBLIC;
				* 方法级别

			String setterPrefix() default "";
			
			@Target({FIELD, PARAMETER})
			@Retention(SOURCE)
			public @interface ObtainVia {
				String field() default "";
				
				String method() default "";
				
				boolean isStatic() default false;
			}
		}
	
	
	# 如果涉及到继承关系，那么推荐使用 @SuperBuilder
		* 子类和父类都用这个注解，这样的话，子类和父类都有自己的builder函数
	
