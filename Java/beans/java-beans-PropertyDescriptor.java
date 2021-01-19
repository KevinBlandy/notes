--------------------------------
PropertyDescriptor				|
--------------------------------
	# JavaBean的属性描述对象
	
	# 构造函数
		PropertyDescriptor(String propertyName, Class<?> beanClass)
		PropertyDescriptor(String propertyName, Class<?> beanClass, String readMethodName, String writeMethodName)
		PropertyDescriptor(String propertyName, Method readMethod, Method writeMethod)

	
	# 实例方法
		Method getReadMethod()
		Method getWriteMethod()
			* 获取属性的读取方法
		
		Class<?> getPropertyType() 
			* 获取属性类型
		
		String getName()
		String getDisplayName()
		boolean isExpert()
