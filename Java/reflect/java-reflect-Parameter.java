------------------------
Parameter				|
------------------------
	# jdk8的新api,表示方法的参数对象
	# 一般是通过 Method 对象的api获取
		Parameter[] parameters = method.getParameters();
	
	# 保留参数名这一选项由编译开关 javac -parameters打开,默认是关闭....

------------------------
Parameter-属性			|
------------------------
	
------------------------
Parameter-方法			|
------------------------

	String getName()
		* 获取参数的名称
	
	
	Class<?> getType()
		* 获取参数的类型
	
	<T extends Annotation> T getAnnotation(Class<T> annotationClass)
		* 获取参数上的注解
	

	
