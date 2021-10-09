-----------------------------
Data
-----------------------------
	# 组合注解，标识在类上，相当于同时标识了如下注解
		* @ToString
		* @EqualsAndHashCode
		* @Getter 
		* @Setter 
		* @RequiredArgsConstructor
	
	# Data
		@Target(ElementType.TYPE)
		@Retention(RetentionPolicy.SOURCE)
		public @interface Data {
			String staticConstructor() default "";
				* 生成一个静态方法构造器，这个属性指定名称
		}
		
