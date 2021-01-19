-----------------------------
获取Bean中的所有属性描述对象 |
-----------------------------
	BeanInfo info = Introspector.getBeanInfo(xxx.class);
	PropertyDescriptor pds[] = info.getPropertyDescriptors();

-----------------------------
获取Bean中指定属性的描述对象 |
-----------------------------
	PropertyDescriptor pd = new PropertyDescriptor("userName", User.class);

-----------------------------
属性描述对象				 |
-----------------------------
	# PropertyDescriptor//属性描述对象
	getName();			
		* 获取属性名称(userName)
	getPropertyType();
		* 获取属性的类型(java.lang.String)
	getWriteMethod();
		* 返回Method类,是写入属性的Mehtod,方法！
	getReadMethod();
		* 返回Method类,是读取属性的Method,方法




Method
	invoke(对象,参数);//哪个对象来执行本方法,参数可有可无如果是静态的不用写对象，如果没返回值返回的就是null如果有,返回值是Object.
	----------javaBean所有属性对象获取
public static void test() throws Exception
	{
		 User user = new User();//创建对象
		 //1.通过Introspector来获取bean对象的beaninfo
		 BeanInfo bif = Introspector.getBeanInfo(User.class);
		 //2.通过beaninfo来获得属性描述器(propertyDescriptor)
		 PropertyDescriptor pds[] = bif.getPropertyDescriptors();
		 //3.通过属性描述器来获得对应的get/set方法
		 for(PropertyDescriptor pd : pds)
		 {
			  //4.获得并输出字段的名字
			 System.out.println("字段的名字是:"+pd.getName());
			 //5.获得并输出字段的类型
			 System.out.println("字段的类型是:"+pd.getPropertyType());
			 if(pd.getName().equals("userName"))
			 {
				 //6.获得PropertyDescriptor对象的写方法
				 Method md = pd.getWriteMethod();
				  //7.执行写方法
				  md.invoke(user, "KeivnBlandy");
		     }
		  }
		 		  //8.输出所赋值字段的值
	 		   System.out.println(user.getUserName());
	} 
-----------------javaBean单个属性对象获取
 public static void test()throws Exception
	  {
		   User user = new User();//创建对象
		    //1.通过构造器来创建PropertyDescriptor对象
		   PropertyDescriptor pd = new PropertyDescriptor("userName", User.class);
		   //2.通过该对象来获得写方法
		    Method method = pd.getWriteMethod();
		   //3.执行写方法
		    method.invoke(user, "KevinBlandy");
		   //4.输出对象字段的值
		   System.out.println(user.getUserName());
		   //5.通过对象获得读方法
		    method = pd.getReadMethod();
		   //6.执行读方法并定义变量接受其返回值并强制塑形
		    String name = (String) method.invoke(user, null);
		   //7.输出塑形后的值
		   System.out.println(name); 
	 }