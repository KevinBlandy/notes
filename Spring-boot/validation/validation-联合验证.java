-------------------------------
联合验证
-------------------------------
	# 联合校验，经常用在验证逻辑比较复杂，当前数据的验证依赖于其他数据
	# 可以自己定义一个 验证方法, 使用 @AssertTrue/@AssertFalse 来完成校验
	
-------------------------------	
GroupSequenceProvider
-------------------------------
	# Hibernate 提供的注解
		@Retention(RUNTIME)
		@Target({ TYPE })
		public @interface GroupSequenceProvider {

			/**
			 * @return The default group sequence provider implementation.
			 */
			Class<? extends DefaultGroupSequenceProvider<?>> value();
		}
