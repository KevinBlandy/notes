---------------------
java.reflect.Field	 |
---------------------


---------------------
实例方法			 |
---------------------
	setAccessible(boolean b);
		* 如果该值为 true, 则设置字段的属性为可被访问。如果进行赋值操作的时候该字段是 private 那么必须要有这一步。
		* 反之可跳过。这个叫做暴力访问。

	set(Object obj,Object value);
		* 给指定对象的这个字段进行赋值操作

	get(Object obj);
		* 获取指定对的这个字段值
	
	<T extends Annotation>  getAnnotation(Class<T> annotationClass) 
		* 如果存在该元素的指定类型的注释，则返回这些注释，否则返回 null。 

	Annotation[] getDeclaredAnnotations();  
		* 获取该字段上所有的注解

	boolean isAnnotationPresent(Class<? extends Annotation> annotationClass);
		* 判断是否有指定类型的注解标识在该属性

	Class<?> getType()
		* 返回数量的类型
	
	int getModifiers()
		* 返回权限修饰的表示数值(public,private,native,final....)