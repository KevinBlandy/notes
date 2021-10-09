----------------------
ToString
----------------------
	# 生成ToString方法
	# ToString
		@Target(ElementType.TYPE)
		@Retention(RetentionPolicy.SOURCE)
		public @interface ToString {

			boolean includeFieldNames() default true;
				* 是否输出字段名称
			
			String[] exclude() default {};
				* 忽略指定的字段
			
			String[] of() default {};
			
			boolean callSuper() default false;
				* 是否调用父类的 toString()
			
			boolean doNotUseGetters() default false;
				* 默认是读取 getter 来获取到属性值
				* 修改为 true 后，会直接访问字段
			
			boolean onlyExplicitlyIncluded() default false;
				* 开启 Include 默认，仅仅会输出标识了 @ToString.Include 注解的字段
			
			@Target(ElementType.FIELD)
			@Retention(RetentionPolicy.SOURCE)
			public @interface Exclude {}
				* 标识在字段上，禁止改字段在 ToString 中输出
			
			@Target({ElementType.FIELD, ElementType.METHOD})
			@Retention(RetentionPolicy.SOURCE)
			public @interface Include {
				int rank() default 0;
					* 输出的顺序
				String name() default "";
					* 如果需要字段名称，那么可以通过这个属性设置
			}
				* 标识在字段上，要求在 toString 中输出
		}