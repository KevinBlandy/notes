
///////////////////////////////////////////////////////////////////////////////////////

@Controller			
	* 标识在Handler,表示在IOC中进行注册
@RequestMapping		
	* 标识类上,也可以标识在方法上!类上URL+方法上URL = URL访问路径,还支持ant风格的URL,其实就是:*,**,? 几个符号作为通配符
@RequestParam
	* 标识Handler形参,做为请求参数接收变量
@RequestHeader
	* 标识Handler形参,作为请求头接收变量
@CookieValue
	* 标识Handler形参,作为Cookie接收变量
@SessionAttributes
	* 标识Handler,把指定的模型数据.存入Session
@ModelAttribute
	* 标识方法上或者Handler形参上,会在Handler处理方法执行之前执行.可以获取请求参数!
@PathVariable
	* 标识Handler形参,替代URL占位符,RESTful风格的url
@InitBinder
	* 标识Hander,可以对WebDataBinder对象进行初始化.方法返回值必须是void,参数通常是:WebDataBinder!
@DateTimeFormat
	* 标识POJO类的,Date 数据类型的字段,指定表示时间的字符串格式!提交参数就必须以这种格式来提交代表时间的字符串.
@NumberFormat
	* 标识PJO类的,Number 数据类型的字段,通过使用"#"号,来确定数据格式.例:@NumberFormat(pattern="####,###.#")
@RequestBody
	* 标识Handler形参POJO,把请求参数中的json字符串转换为javaBean
@ResponseBody
	* 标识Handler的返回值(该返回值,应该是一个POJO或者一个集合),或者直接标志在方法上,把返回给客户的对象,转换为json

