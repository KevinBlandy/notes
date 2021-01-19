----------------------------
ParameterizedType			|
----------------------------
	# 接口,基础自 Type
	# 抽象方法
		 Type[] getActualTypeArguments();
			* 返回泛型列表
				
		 Type getRawType();
		 Type getOwnerType();
		

----------------------------
获取泛型列表				|
----------------------------
	abstract class  Parent<T,R> {
		abstract void foo(T t,R r);
	}

	class Sub extends Parent<String,Integer> {
		@Override
		public void foo(String s,Integer i) {
			System.out.println(s + ":" + i);
		}
	}

	Class<? extends Sub> clazz = Sub.class;

	Type type = clazz.getGenericSuperclass();
	// 泛型父类
	if(type instanceof ParameterizedType){

		ParameterizedType parameterizedType = (ParameterizedType) type;

		// 获取到父类的泛型列表
		Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();

		Stream.of(actualTypeArguments).forEach(System.out::println);
		//class java.lang.String
		//class java.lang.Integer
	}