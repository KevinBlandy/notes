----------------------------
NonNull
----------------------------
	# 生成非空判断的代码

	# NotNull
		@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.TYPE_USE})
		@Retention(RetentionPolicy.CLASS)
		@Documented
		public @interface NonNull {
		}
	
