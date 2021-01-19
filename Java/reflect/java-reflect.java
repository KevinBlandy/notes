

---------------------
反射泛型信息		 |
---------------------
	# 父类获取子类传递的泛型信息

		Class 类是反射的鼻祖,什么反射都得从他开始
		它有一个方法
		Type	getGenericSuperclass();//返回表示此 Class 所表示的实体（类、接口、基本类型或 void）的直接超类的 Type。 


		Type 是一个接口,它有一个子类接口--ParameterizedType
		Type ---- > ParameterizedType(我们得到这个Type,会强制转换成这个类型)
		ParameterizedType
			> 参数化类型  其实就是 Demo<String>
			> 就是类,加上类型参数,类名和泛型
			> getActualTypeArguments()//获取真实的类型参数们,返回一个Type[] 
			> 上面的方法返回一个Type[]数组,那么这个数组就是我们需要的Class[]

		示例代码
			> !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			Class c = (Class) ((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
			> 这串代码写在你的泛型类里面
			> 最好写在构造函数,那么这个c,就是这个类的子类传递上来的泛型类型的Class对象
			> 如果子类传递String  ..那么这个c 就是String.class
			> 如果子类传递Integer ..那么这个c 就是Integer.class

		获取子类传递的类形(跟上面一样)
			ParameterizedType parameterizedType = (ParameterizedType) this.getClass().getGenericSuperclass();
			clazz = (Class<T>) parameterizedType.getActualTypeArguments()[0];