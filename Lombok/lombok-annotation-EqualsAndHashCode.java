----------------------------------
EqualsAndHashCode
----------------------------------
	# 生成 equals 和 hashCode
	# EqualsAndHashCode
		@Target(ElementType.TYPE)
		@Retention(RetentionPolicy.SOURCE)
		public @interface EqualsAndHashCode {

			String[] exclude() default {};
			
			String[] of() default {};
			
			boolean callSuper() default false;
				* 是否调用父类

			boolean doNotUseGetters() default false;
				* 是否使用 getter 方法来访问值

			CacheStrategy cacheStrategy() default CacheStrategy.NEVER;
				* hashCode 的缓存策略
			
			AnyAnnotation[] onParam() default {};
			
			@Deprecated
			@Retention(RetentionPolicy.SOURCE)
			@Target({})
			@interface AnyAnnotation {}
				* 过时，勿用
			
			boolean onlyExplicitlyIncluded() default false;
			
			@Target(ElementType.FIELD)
			@Retention(RetentionPolicy.SOURCE)
			public @interface Exclude {}
				* 标识在需要排除的字段
				
			@Target({ElementType.FIELD, ElementType.METHOD})
			@Retention(RetentionPolicy.SOURCE)
			public @interface Include {
				String replaces() default "";

				int rank() default 0;
			}
				* 标识在需要包含的字段

			public enum CacheStrategy {
				NEVER,
						* 不缓存
				LAZY
						* 延迟加载
			}
				* 缓存策略
		}