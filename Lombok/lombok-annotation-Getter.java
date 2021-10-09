--------------------------
Getter
--------------------------
	# 生成 Getter 代码
	# Getter
		@Target({ElementType.FIELD, ElementType.TYPE})
		@Retention(RetentionPolicy.SOURCE)
		public @interface Getter {

			lombok.AccessLevel value() default lombok.AccessLevel.PUBLIC;
				* 访问级别
			
			AnyAnnotation[] onMethod() default {};
				* 在生成的方法上添加注释
				* 已经过时不要使用
			
			boolean lazy() default false;

			@Deprecated
			@Retention(RetentionPolicy.SOURCE)
			@Target({})
			@interface AnyAnnotation {}
		}
