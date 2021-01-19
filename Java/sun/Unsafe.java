----------------------------
创建Unsafe类				|
----------------------------
	# 反射方法创建
		Field field = Unsafe.class.getDeclaredField("theUnsafe");
		field.setAccessible(true);
		Unsafe unsafe = (Unsafe) field.get(null);