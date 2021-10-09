--------------------------------
Value
--------------------------------
	# 组合注解，类似于 @Data 用于所有的字段都是 private final 的
	# Value
		@Target(ElementType.TYPE)
		@Retention(RetentionPolicy.SOURCE)
		public @interface Value {
			String staticConstructor() default "";
		}
