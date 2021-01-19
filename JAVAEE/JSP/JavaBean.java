JavaBean
   必须要为成员提供get/set方法(两者只提供一个也是可以的)
   必须要有一个无参构造器
   一般对具有get/set方法的成员变量称之为属性
   其实就算一个属性没有对应的成员变量,只有get/set方法也是可以的
   其实属性的名称,就是get/set方法去除get/set后,把首字母小写了！

-------------
JavaBean规范
1,必须要有无参构造器
2,必须提供get/set方法.如果只有get方法,那么这个属性是只读属性!
3,属性:有get/set方法的成员.还可以没有成员,只有get/set方法！属性名称由get/set方法决定！而不是成员名称
4,方法名称满足了一定的规范,那么它就是属性！boolean类型的属性,它的方法可以是is开头,也可以是get开头

内省就是通过反射来操作javaBean,但他比反射要方便一些！
我们需要提供javaBean类！
--------------------------------------BeanInfo
BeanInfo是一个javaBean类型的信息类

BeanInfo  info = Introspector.getBeanInfo(类型);
通过BeanInfo可以得到所有属性描述符对象
PropertyDiscriptor[] a = info.getPropertyDiscriptor();
可以通过PropertyDiscriptor得到一个属性的读/写方法！
getReadMethod();
getWriteMethod();
可以通过读和写方法来操作javaBean的属性

introspector//内省接口,给他一个JAVABean的类型,他就可以给出该JAVABean的BeanInfo!
	底层就是反射,反射得到满足JAVABean规范的内容
例:BeanInfo info = Intro spector.getBeanInfo(User.class);//User.class就是符合javaBean规范
BeanInfo//JAVABean的信息
	getMethodDescriptors();//返回MethodDescriptor[]--方法描述符
	getPropertyDescriptors();//返回PropertyDescriptor[]--属性描述符
PropertyDescriptor
	|--getReadMethod();获取读方法get
	|--getWriteMethod();获取写方法set

内省类-->Bean信息-->属性描述符-->属性的get/set对应的Method-->反射... ...  
-----------------------------------------
内省
	内省类-->Bean信息-->属性描述符-->属性的get/set对应的Method-->反射... ...  
commons-beanutils;它依赖内省完成(内省依赖反射)
阿帕奇提供的。
1,导包			(以上的操作步骤,已经被人帮我们实现了--阿帕奇提供的)
   > commons-beantils.jar	//它是依赖内省完成的！
   > commons-logging.jar	
	

		 /**
		 * beanutils的演示
		 * */
		Class clazz = Class.forName(className);
		Object bean = clazz.newInstance();
		/**赋值操作**/
		BeanUtils.setProperty(bean, "userName", "Kevin");//给userName属性赋值
		BeanUtils.setProperty(bean, "passWord", "123456");//给passWord属性赋值
		BeanUtils.setProperty(bean, "age", 22);//给age属性赋值,就算22是一个字符串,也能正确的赋值进去
		BeanUtils.setProperty(bean, "gender", "男");//给gender属性赋值
		BeanUtils.setProperty(bean, "没有的字段", "瞎赋");//给根本就不存在的属性赋值,也是不会报错的
		System.out.println(bean);//调用toString测试对象是否赋值成功
		/**读取操作**/
		String userName = BeanUtils.getProperty(bean, "userName");
		String passWord = BeanUtils.getProperty(bean, "passWord");
		String age = BeanUtils.getProperty(bean, "age");	//int类型的字段返回的是String
		String gender = BeanUtils.getProperty(bean, "gender");
		System.out.println(userName+":"+passWord+":"+age+":"+gender);	

---------------
网页中表单数据.通过request获取map后！然后就可以通过上面的工具包,直接一句话封装！
保证网页中的name跟map中的键以及对象的字段属性相同

	Class clazz = Class.forName(className);
	Object bean = clazz.newInstance();
	Map<String,String> map = new HashMap<String,String>();
	map.put("userName", "Rocco");
	map.put("passWord", "a12551255");
	map.put("age", "23");
	map.put("gender", "男");
	BeanUtils.populate(bean, map);//直接把Map中的数据直接封装到对象中
	System.out.println(bean);