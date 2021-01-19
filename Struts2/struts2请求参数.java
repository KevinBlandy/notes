————————————————————————————————
一,struts2中获取请求参数		|
————————————————————————————————
	struts2是一个MVC框架,在struts2中.
	Action的定位其实很尴尬!可以说是Servlet一样的控制器!也可以说说Model....
	Why?  

————————————————————————————————
二,使用属性驱动获取请求参数		|
————————————————————————————————
1 ,
	把Action作为Model,可以直接封装请求处理参数
	* 必须提供与form表单字段名称对应的set属性.(框架要通过反射来调用set方法注入属性)
	* 不会存在线程安全问题Action非单例的.
	缺点就很明显了,需要单独定义javaBean,将action中属性copy到javaBean中!
	优点:呵呵,简单!
	'原理'
		反射

2,	
	在上面的案例中我们可以看出,框架注入的参数.就是在当前Action中,如果参数过多,那么这个Action就会有很多get/set以及成员字段
	显得非常的臃肿,不利于维护!于是
	public class Demo
	{
		private User user;		//User类省略,有username,password俩字段,并且有get/set方法,符合javaBean规范
		public String demo()
		{
			System.out.println(user.getUsername()+":+user.getPassword);
		}
		public void setUser(User user)		//框架获取这个参数对象,并为这个对象的属性赋值,再把这对象赋值给Action
		{
			this.user = user;
		}
		public User getUser()
		{
			return user;
		}
	}
	> 这种方式其实是用到了struts2的OGNL表达式(后面会详解)
	> 这种方式似乎比较靠谱.那么条件也更为苛刻
	1,对于页面中表单的要求
		> 表单项中的name属性表达格式:<inut type="text" nam="user.username"/>
		* 学过java就知道,name的属性意思就是:user对象的username属性,框架收到这个格式的名称参数后..
		* 第一时间会通过反射获Usr这个类,然后在创建其对象,再把setUsername()等方法进行赋值
		* 最后把这个赋值User对象,通过setUser();还给Action
	2,对于Action和引用型变量成员的要求
		> 引用型变量成员的get/set方法要具备,而且名称要符合javaBean规范,跟表单name属性要能对接
		> Action本身,也要具备get/set该成员变量的方法,且方法名要跟name属性对接
	3,一个比较细节性的问题 
		> 当Action中引用的对象为null,那么框架会给你new一个出来,赋值完毕后,再调用setUser();赋值给你！
		* privare User user;
		* 这个情况会调用setUser();方法
		> 当Action中应用对象不为空,那么框架是不会new的,直接就获取你这个对象,往里面注值.可能会出现覆盖现象
		* private User user = new User();
		* 如果你的user已经设置了一个username值,客户端又传递一个username进来,那么直！接！覆！盖。要注意
		* User不为空的情况是不会调用setUser();方法
	优点:解决了上面的问题。。。哈哈哈哈哈!!
	缺点:因为页面有用到了OGNL表达式,所有!该页面就跟struts2框架耦合了!如果换成其他的框架,会有异常发生!
	
	'原理'
		struts2,是通过了一个拦截器进行了数据的封装!
		<interceptor name="params" class="com.opensymphony.xwork2.interceptor.ParametersInterceptor"/>
		* 就这哥门儿给我们完成的请求参数封装!

————————————————————————————————
三,使用模型驱动获取请求参数		|
————————————————————————————————
['重点']
	开发应用较多的一种模式,一下子把上面那俩的问题都解决了!
	> 步骤
	1,Action实现一个类叫做ModelDriven
		* ModelDriven<T>
		* 泛型接口,你封装的Bean是啥,你就写啥
	2,声明一个Bean对象,'必须实例化'.
	3,覆写getModel()方法
		* public T getModel(){}
	4,在getModel()方法中返回,这个'已经实例化'的bean!
	5,OK,那么Action中的这个Bean就已经封装了请求参数
['注意']
	* 表单中的数据字段还是要跟Bean的属性对应,用'正常的形式表达'!就别加那些奇奇怪怪的东西!
	* 如果使用了模型驱动,那么上面两种方式就会失效:例如,表单字段有name,字段!使用模型驱动的Action里面的name字段是不会被注值的,'但是,其他不同名的字段还是能正常的注值'
['优点']
	* 解决了属性驱动存在的问题:不会使用到OGNL,解决了表单页面与struts2框架耦合,也解决了Action内数据的封装的问题!
['缺点']
	* 一次只能封装一个Model对象!
	
————————————————————————————————
四,请求参数封装到集合的问题 	|
————————————————————————————————
List 集合
	* from表单中
	<intput name="list[0].name"/>
	<intput name="list[0].pass"/>
	<intput name="list[1].name"/>
	<intput name="list[1].pass"/>
	* Action中
	List<User> list;//该list有get/set方法
	> 框架创建集合,创建对象,自动封装,存入!最后注入Action
Map 集合
	* from 表单中
	<intput name="map['key1'].name"/>
	<intput name="map['key1'].pass"/>
	<intput name="map['key2'].name"/>
	<intput name="map['key2'].pass"/>
	* Action表单中
	Map<String,Object> map;
	> 还是一样,框架创建,集合,创建对象,存入,注入给Action!

————————————————————————————————
五,总结							|
————————————————————————————————

['方式1']
	简单,但是一旦字段过多,get/set方法过多.就影响业务代码!而且不便于层之间的传递
['方式2']
	便于层次之间的传递,而且业务代码隔离开来.但是,依赖于OGNL表达式!
['方式3']
	又便于层次之间的传递,又隔开了业务代码,也不依赖于OGNL!但是,每次访问只能封装一个对象

