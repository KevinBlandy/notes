ModelAndView modelAndView = new ModelAndView();

addObject(name,value);
	> 跟request.setAttribute(name,value);相似,String类型的名称和值
setViewName(String path);
	> ..这个path就一个jsp路径!
	* mv.setViewName("/WEB-INF/jsp/demo.jsp");



SpringMVC提供一下几种途径输出模型数据

	1,ModelAndView
		* 作为处理方法的返回值,既包含视图信息,也包含模型数据.模型数据默认存储在rquest域
			addObject(name,value);				//key,自定义名称.value对象.把指定的对象存入了request域
			setViewName(String path);			//跳转路径,默认为转发
		
	2,Map 和 Model
		* 他们可以作为参数,或者返回值.
		* 框架在内部使用了一个:org.springframework.ui.Model接口存储模型数据
		* 框架在调用方法前会创建一个隐含的模型对象作为模型数据的存储容器.
		* '如果方法的形参为 Map,或者Model'类型,那么框架就会把隐藏的模型引用传递给这些形参,开发者可以通过形参获取到模型的数据,也可以添加
			public String demo(Map<String,Object> map)
			{
				map.put(key,value);				//该数据其实存储到了request域
			}

	3,@SessionAttributes
		* Session,不多解释,只能放在类上
			value		//String类型的数组
			types		//Class类型的数组
		@SessionAttributes(value={"user"})
			×　当方法中有名称为"user",的对象被放置到了 Map,或者是 ModelAndView中,那么该"user"对象也会被放置到Session中
			×　也就是通过属性名指定
		@SessionAttributes(types={String.class})
			×　当方法中有String.class类型的参数被放置到了request,那么也会自动的放置到Session
			×　也就是通过类型指定

	4,@ModelAttribute
		* 这东西其实也好理解,算是一个重点
		* 被这个注解标识的方法,会在'每个目标方法执行之前被框架调用'.
		* 该方法可以获取到请求参数
		运行流程
			1,首先会执行 @ModelAttribute 注解标识的方法
				* 该方法代码应该是,从数据库中获取对象,把对象存入 request 中
			2,框架从 request 中取出对象,并把表单的请求参数赋给该对象的对应属性
			3,框架把这个对象传入目标方法参数
			* 也就是其中的一些,属性.其实是来自数据库.一些属性来自于表单!极有可能,该对象中的某些属性,是不允许被改变的!

		注意:@ModelAttribute,标注的方法,在存入的时候,对key有要求.
		* 通俗理解就是.传入map中的key,必须要与处理方法中形参的名称一样!

		@RequestMapping(value="/demo")
		public String demo(User user)
		{
			System.out.println(user);				//此时的user对象,里面已经封装好了来自表单与数据库的值.	
			return "first";
		}
		@ModelAttribute
		public void getUser(String id,Model model)	//此处的id,就是请求参数中的id值,Model不解释了,就是要存入request域
		{
			if(id != null)
			{
				User user = service.getUserById(id)	//模拟从数据库中取出数据,封装对象
				model.addAttribute("user",user);	//存入request域,注意:key 要跟处理方法的形参一样,才被被正确的赋值
			}
		}

		* 这东西吧,目前还不知道该在哪里使用!
		SpringMVC 确定目标方法POJO类型形参的过程
			1,确定一个key
			2,在implicitModel 中查找key对应的对象.如果存在,就作为形参传递给目标方法
			3,如果不存在,就检查当前的Handler是否使用了 @ModelAtrribute 注解.
			4,如果使用了该注解,且该注解的 value,属性值中包含了key,就会从HttpSession中来获取key所对应的value值
			5,如果存在,就直接传递给目标方法形参,如果不存在!抛出异常.
			6,Handler 没有标识 @SessionAttributes 注解,或者该注解中没有包含key,则会通过反射来创建POJO类型的参数.然后传入Handler形参.
			7,SpringMVC 会把key和value,保存到 imolicitModel ,进而保存到request中


		