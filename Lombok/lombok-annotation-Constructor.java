------------------------------
NoArgsConstructor
------------------------------
	# 生成无参构造函数
	# NoArgsConstructor
		@Target(ElementType.TYPE)
		@Retention(RetentionPolicy.SOURCE)
		public @interface NoArgsConstructor {
			String staticName() default "";
			
			AnyAnnotation[] onConstructor() default {};
			
			AccessLevel access() default lombok.AccessLevel.PUBLIC;
				* 访问级别
			
			boolean force() default false;
				* 是否强制生成无参构造函数
				* 如果有 final 需要初始化的字段，会设置为默认值: 0 / null / false.
			
			@Deprecated
			@Retention(RetentionPolicy.SOURCE)
			@Target({})
			@interface AnyAnnotation {}
		}

------------------------------
RequiredArgsConstructor
------------------------------
	# 为每个必须字段生成一个带有 1 个参数的构造函数
		* 带 final 表示的字段
		* 带有约束的字段，例如 @NonNull

	# RequiredArgsConstructor
		@Target(ElementType.TYPE)
		@Retention(RetentionPolicy.SOURCE)
		public @interface RequiredArgsConstructor {
			String staticName() default "";
			
			AnyAnnotation[] onConstructor() default {};
			
			AccessLevel access() default lombok.AccessLevel.PUBLIC;

			@Deprecated
			@Retention(RetentionPolicy.SOURCE)
			@Target({})
			@interface AnyAnnotation {}
		}

------------------------------
AllArgsConstructor
------------------------------
	# 为类中的每个字段生成一个带有 1 个参数的构造函数
	# AllArgsConstructor
		@Target(ElementType.TYPE)
		@Retention(RetentionPolicy.SOURCE)
		public @interface AllArgsConstructor {
			String staticName() default "";
			
			AnyAnnotation[] onConstructor() default {};

			AccessLevel access() default lombok.AccessLevel.PUBLIC;
			
			@Deprecated
			@Retention(RetentionPolicy.SOURCE)
			@Target({})
			@interface AnyAnnotation {}
		}