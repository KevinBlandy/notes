
关于Spring请求参数的获取

['基本数据类型']
	* SpringMVC通过分析处理'方法的签名',把HTTP请求信息绑定到处理方法相应的'形参'中!
	* SpringMVC对控制器处理方法签名的限制是很宽松的,几乎可以按喜欢的任何方式对方法进行签名
	* 必要时,可以对方法,以及方法形参标注相应的注解
		* @RequestParam
			value
				* 参数名
			required
				* 是否是必须的,默认为true.表示请求参数中必须包含对应的参数,如果不存在抛出异常
			defaultValue
				* 默认值,如果这个参数没有传入,那么处理方法中将使用这个默认值和形参绑定
			例:
				public String demo(@RequestParam(value="name",required=false,defaulValue="Kevin")String name,@RequestParam(value="age",required=true)Integer age){}
				* 表示,请求参数中,name的属性可选,如果没,那么默认为kevin,age属性是必须的
				* 注解标识的参数名可以不和方法形参名一样
				* 框架会自动的进行类型转换
		* @PathVariable(REST)

['HTTP请求头']
	* @RequestHeader
		* 该注解是获取HTTP请求头
		例:
			public String demo(@RequestHeader(value="Accept-Language")String language){}
			* 该形参就会得到指定请求头的值
	* 框架会把HTTP请求信息绑定到相应方法的形参中,并根据方法的返回值类型做出相应的后续处理
	
['Cookie']

	* @CookieValue
		* 可以让处理方法形参绑定某个来自浏览器的Cookie值
		* 跟基本数据类型的注入一样,也是具备一个'是否为必须'的属性
		例:
			public String demo(@CookieValue(value="jsessionid",required=false)String id){}

['使用POJO作为参数']
	* 框架会自动根据请求参数,和POJO属性名进行自动匹配!自动的为对象填充属性值
	* '支持级联',如Luser.id,user.order.id
	* localhost8080/demo.action?userName=Kevin&user.order.id=10086
	例:
		public String demo(User user)
		{
			//跟struts2一个德行,页面input字段,支持属性.属性来对POJO的属性进行赋值
		}


['数组形式']
	* String[]  hobby
	* 可以指定在形参中定义数组



['List']
	* 当参数为 List 的时候,要通过包装POJO来接收,也就是在POJO中要定义 List 属性,且属性名要和页面一样
	* 框架支持级联属性 href=demo.action?user.user.name=sdasd
	* 甚至还支持: name="user.hobby[1]"	有点像OGNL表达式

	* 牛逼之处在于, List 类型的数据绑定还可以通过这样
		http://..item/delelteById?ids=123,456,789

		public void deleteById(@RequestParam(value="ids") List<Object> ids){
			
		}
		# 页面传递的一个数据,是以','号分割的.那么可以用List来接收,springmvc会自动的进行切割... ...叼不叼?

['Map']
	* 当参数为 Map 的时候,也是要通过包装类型的POJO 来进行接收
	

	




	SpringMVC的Handler,处理方法的形参,可以接收如下ServletAPI类型的参数

	HttpServletRequest
	HttpServletResponse
		* 如果使用response给客户端进行相应,那么不能有返回值.才会生效.也就是说视图解析>response直接操作
	HttpSession
	java.security.Principal
	Locale
	InputStream
	OutputStream
	Reader
	Writer









['过期方式']
	1, Handler继承一个类(该类已经被标识过时,不建议使用)
		×　org.springframework.web.servlet.mvc.AbstractCommandController
		×　它其实已经实现了:Controller接口,所以用哪个适配器,就不多说
	2, 同样也是覆写一个handle方法
		protected ModelAndView handle(request,response,Obj,BindException error){}
		×　比原始方式(实现Controller接口)多出两个参数.Object obj,BindException error
		×　其实这个obj,就是请求参数封装后的实体,error,表示封装时产生的异常
	3, 在Handler中显示声明无参构造器!在构造器中,调用来自父类的方法:this.setCommandClass(Class clzz);
		×　其实,就是传递进去Model,的类类型.框架会自动的创建对象,封装数据
		×　同时肯定要求,表单的数据的name属性,要跟Model的属性名称一样
	4, 那么在handle方法中,你就可以通过方法参数获取到这个已经封装了数据的对象.进行强转后使用!
	5, 中文乱码的问题,request.setChartset("utf-8");不起作用
	6, 跟struts2有点类似,它能完成一些基础类型的数据格式转换.但是涉及到了 Date 之类的就比较坑爹,就需要使用到'自定义类型转换器'
		×　向springMVC内部注入一个自定义的类型转换器