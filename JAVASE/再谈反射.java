反射的理解
*应用在一些通用性比较高的代码中
*后面学到的框架,大多数都是使用反射来实现的
		在框架开发中，都是基于配置文件开发
			在配置文件中配置了类。可以通过反射得到类中左右内容。可以让类中的某个方法来执行
*类中的所有内容，以及其对应的对象
属性(字段)	-- Field
构造方法	--Constructor
普通方法	--Method
--------------------
原理
	*首先把java文件保存到本地硬盘	.java文件
	*编译java文件。					.class文件
	*万物皆对象。 class 文件在内存中使用 Class 类表示
	*当使用反射的时候，首先要获得 Class 类。得到这个类之后就可以得到 class 文件中的所有内容
.java文件-----编译-----class.文件-----加载进内存-----生成对应的 Class 对象！
得到 Class 类对象。有三种方法
1,类名.class 
2,对象.getClass();
3,使用Class.forName("路劲");
Class 类的一些方法
----------------------------
Class cla = Class.forName();
cla.newInstance();//访问该类无参构造器，创建该类的对象
cla.getConstructor(String.class,String.class);//获取指定的构造器对象.
	返回 Constructor 对象 Constructor 
cla.getConstructors();//返回所有的构造器
	返回 Constructor[] 数组
... ...
使用泛型来操作普通方法
	


