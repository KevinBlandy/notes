------------------
Path
------------------
	# 表示路径表达式, 路径是指变量, 属性和集合成员的访问权限
		interface Path<T> extends Expression<T>

	# 抽象方法
		PathMetadata getMetadata();
		Path<?> getRoot();
		AnnotatedElement getAnnotatedElement();
