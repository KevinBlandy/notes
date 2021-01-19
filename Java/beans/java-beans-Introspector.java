------------------------
Introspector			|
------------------------
	# Java 内省, 提供N多的静态方法

	# 静态方法
		String decapitalize(String name)
		void flushCaches()
		void flushFromCaches(Class<?> clz)
			* 使用 Introspector, 会产生一个系统级别的缓存(WeakCache<Class<?>, Method[]> declaredMethodCache = new WeakCache<>())
			* 在内省操作后, 要记的刷出缓存
	

		BeanInfo getBeanInfo(Class<?> beanClass)
		BeanInfo getBeanInfo(Class<?> beanClass, int flags)
		BeanInfo getBeanInfo(Class<?> beanClass, Class<?> stopClass)

		String[] getBeanInfoSearchPath()
		void setBeanInfoSearchPath(String[] path)

	