SpringMVC中使用到的那些注解

	@RequestMapping
		* 该注解为控制器指定,可以处理哪些URL请求
		* 可以注解方法/类.类与方法上的注解构成一个完整的URL请求路径
		* 如果只有单个存在,那么是相对于根目录结构.如果类注解存在,那么方法路径就是相对于类路径
		具备属性
			1,value
				* 请求url
			2,method
				* 请求方法(get/post),是一个数组,运行多个
			3,params
				* 请求参数(不常用),可以指定:必须包含哪个参数?,参数必须是多少?不一定要包含?..去你妈的,没用!
			4,heads
				* 请求头(不常用)
		* 该注解除了可以使用URL映射请求头外,还可以使用请求方法,请求参数,请求头...分别就是上面的属性指定
		  它们之间的关系是与,联合使用多个条件,可以让请求更加精确化
		  例:@RequestMapping(value="/demo.action",method=RequestMethod.GET)
			* 只能使用GET方式来访问这个控制器
		* 它的映射还支持通配符,不过它的通配符是通过ant风格来进行配置的
			* ant风格资源地址,支持三种匹配符
				1,?:匹配文件名中的一个字符
				2,*:匹配文件名中的任意字符
				3,**:匹配多层路径

				localhost:8080/demo/*/logon.action														*/
				了解就好... ...别去折腾
	

	@RestConttroller
		* 组合了 @Controller 和 @ResponseBody 的一个注解

	@PathVariable
		* 该注解是3.0版本后新增的东西,带占位符的UR!该功能在SpringMVC向REST目标挺进发展过程中具有里程碑的意义
		* 通过该注解,可以把URL中占位符参数绑定到控制处理器方法的形参中.
		* 例:
			@RequestMapping("/delete/{userid}")
			public String delete(@PathVariable("userid") String id){
				userService.delete(id);
				return null;
			}
		* 如果模板路径中带有'.'点的话,会丢失'.'后面的参数
			@RequestMapping("/delete/{userid:.+}")		//:.+ 就可以成功的获取到模版参数中带.的部分
			public String delete(@PathVariable("userid") String id){
				userService.delete(id);
				return null;
			}
		
	@CookieValue
		# 把Cookie值绑定到形参

	@RequestParam
		# 请求参数绑定到形参
	
	@RequestHeader
		# Htttp 请求头绑定到形参

	@InitBinder
		# 由表单字段到javaBean的绑定,期间可以完成一些类型的转换以及数据的格式化处理
		# 该注解标识的方法,可以对WebDataBinder对象进行初始化.WebDataBinder 是DateBinder的子类.用于完成由表单字段到javaBean属性的绑定
		# 被它标识的方法不能有返回值,它必须声明为void.
		# 被它标识的方法,形参通常是WebDataBinder
		例:
			@InitBinder
			public void initBinder(WebDataBinder dataBinder)
			{
				dataBinder.setDisallowedfields("roleset);
			}
	
	@ModelAttribute
		# 
	
	@GetMapping
	@PostMapping
	@DeleteMapping
	@PutMapping
		# 很显然,RESTFult 的Mapping,比较高的版本才有的东西
	
	
	@SessionAttributes
		# 属性
			* String[] value() default {};
			* Class<?>[] types() default {};
		

	@ExceptionHandler
		# 标识在一个方法上,并且该类需要被Spring所管理
		# 当Controller发送异常的时候,会调用该方法,其实就是个异常处理
		# 属性
			Class<? extends Throwable>[] value() default {};
			* 该值表示,当系统异常时,会根据不同的Exception找到不同的 @ExceptionHandler 进行处理

	
	@ControllerAdvice
		# Controller增前,该注解标识了  @Component  注解,也就是说可以被扫描到,但是需要添加该注解在 scan include中
		# 可以在这个Controller里面定义一些针对于所有Controller的事情
		# 有一些用处,当使用 @ExceptionHandler 最有用，另外两个(@InitBinder,@ModelAttribute)用处不大。
		# 可以把异常处理器应用到所有控制器，而不是 @Controller 注解的单个控制器。
		# Demo
			@ControllerAdvice  
			public class ControllerAdviceTest {  
			  
				@ModelAttribute  
				public User newUser() {  
					System.out.println("============应用到所有@RequestMapping注解方法，在其执行之前把返回值放入Model");  
					return new User();  
				}  
			  
				@InitBinder  
				public void initBinder(WebDataBinder binder) {  
					System.out.println("============应用到所有@RequestMapping注解方法，在其执行之前初始化数据绑定器");  
				}  
			  
				@ExceptionHandler(Exception.class)  
				@ResponseStatus(HttpStatus.UNAUTHORIZED)  
				public String processUnauthenticatedException(HttpServletRequest  request, Exception e) {  
					System.out.println("===========应用到所有@RequestMapping注解的方法，在其抛出Exception异常时执行");  
					return "viewName"; //返回一个逻辑视图名  
				}  
			}  