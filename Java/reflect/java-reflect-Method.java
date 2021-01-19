
---------------------
java.reflect.Method	 |
---------------------

---------------------
实例方法			 |
---------------------
	invoke(Object obj,Object value...);
		* 以反射的方法执行方法对象,第一个参数表示是哪个对象的此方法,第二个参数表示方法参数,如果没有直接无视即可

	<T extends Annotation>  getAnnotation(Class<T> annotationClass) 
		* 如果存在该元素的指定类型的注释，则返回这些注释，否则返回 null。 

	Annotation[] getDeclaredAnnotations();  
		* 获取该方法所有的注解

	boolean isAnnotationPresent(Class<? extends Annotation> annotationClass);
		* 判断是否有指定类型的注解标识在该方法
	
	int getModifiers()
		* 返回权限修饰的表示数值(public,private,native,final....)
	
	 Class<?> getReturnType()
		* 返回 return 的Class类类型
	
	boolean isBridge()
		* 是否是泛型桥接方法
		* 桥接方法是 JDK 1.5 引入泛型后,为了使Java的泛型方法生成的字节码和 1.5 版本前的字节码相兼容
		* 由编译器自动生成的方法
			interface Parent<T> {
				void foo(T t);
			}

			class Sub implements Parent<String> {
				@Override
				public void foo(String s) {
					System.out.println(s);
				}
			//	JVM编译器生成的桥接方法
			//	public void foo(Object s) {
			//		this.foo((String) s);
			//	}
			}

			Method bridgeMethod = Sub.class.getMethod("foo",Object.class);
			System.out.println(bridgeMethod.isBridge());		// true

			Method method = Sub.class.getMethod("foo",String.class);
			System.out.println(method.isBridge());				// false
	
