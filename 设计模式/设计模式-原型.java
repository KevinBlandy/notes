----------------------------
原型模式					|
----------------------------
	* prototype
	* 其实就是把一个对象进行复制,复制出来的对象,就拥有了原对象的所有属性和方法,并且可以基于该对象进行增强,修改.而且不会影响原对象

	
	* java 实现 Cloneable 接口

	* 原型类覆写 Object 中 clone 方法
		* 实现的过程只需要调用 return super.clone();

	* 通过调用原型实例的 clone() 方法来获取到一个clone的对象
		* clone获取的实例为浅clone
		* 如果要深 clone,需要自己去实现 clone 方法来完成

	

	* 可以通过对象序列化(io),来完成深clone
		
		//原始对象
		Object obj = new Object();

		//创建内存字节流
		ByteArrayOutputStream bos = new ByteArrayOutputStream();

		//创建对象写流,写到内存字节流中
		ObjectOutputStream oos = new ObjectOutputStream(bos);
		
		//写入对象
		oos.writeObject(obj);
		
		//把写入的对象读取为字节
		byte[] bytes = bos.toByteArray();
	
		//根据字节创建内存字节读取流
		ByteArrayInputStream bis = new ByteArrayInputStream(bytes);

		//创建对象读取流,从内存字节中读取
		ObjectInputStream ois = new ObjectInputStream(bis);
	
		//从对象流中读取深克隆对象
		Object cloneObject = ios.readObject();


