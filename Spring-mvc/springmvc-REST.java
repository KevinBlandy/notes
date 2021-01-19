

*	REST,也就是Representational State Transfer (资源)表现层状态转化.
	'是目前最流行的一种互联网架构',它结构清晰,符合标准.易于理解,扩展方便
	所以,正逐步得到越来越多的网站采用

*	'资源(Resources)',网络上的一个实体,或者说是网络上的一个具体信息.
	它可以使一段文本,一张图片,一首歌曲,一种服务,总之就是一个具体的存在.
	可以用一个URI(统一资源定位符)指向它,每种资源对应一个特定的URI,要获取这个资源
	访问它的URI就可以,因此'URL就是每个资源的独一无二的标识符'

*	'表现层(Representation),把资源具体呈现出来的形式',叫做它的表现层.
	比如:文本可以用TXT格式表现,也可以用HTML格式,JSON,甚至可以使用二进制

*	'状态转化(State Transfer)':每发出一个请求,就代表了客户端和服务器的一次交互过程
	HTTP协议,是一个无状态协议.即所有的状态都保存在服务器端,因此'如果客户端想要操作服务器,必须通过某种手段'
	'让服务器发生 状态变化(State Transfer).而这种转换是建立在表现层之上的,所以就是 表现层状态化'.
	具体来说就是:
		HTTP协议里面,四个表示操作方式的动词:GET,POST,PUT,DELETE,他们分别对应四种基本操作:获取,添加,修改,删除
	


示例
	/order/1	HTTP GET
		* 得到id=1的order资源
	/order/1	HTTP DELETE
		* 删除id=1的order资源
	/order/1	HTTP PUT
		* 更新id-1的order资源
	/order		HTTP POST
		* 添加一个order
	
	HiddenHttpMethodFilter
		目前浏览器的表单仅支持:POST/GET 请求,而DELETE,PUT等method并不支持
		Spring3.0新添加了一个过滤器
		可以将这些请求转换为标准的HTTP方法
		使得支持GET,POST,PUT与DELETE请求
	<!-- REST前端控制器 -->
	<filter>
		<filter-name>HiddenHttpMethodFilter</filter-name>
		<filter-class>org.springframework.web.filter.HiddenHttpMethodFilter</filter-class>
	</filter>
	<filter-mapping>
		<filter-name>HiddenHttpMethodFilter</filter-name>
		<url-pattern>/</url-pattern>
	</filter-mapping>

	发送GET请求
		<a href="ROOT/testRest/1"/>
	发送DELETE请求
		<form action="ROOT/testRest/1" method="POST">
			<input type="hidden" name="_method" value="DELETE">
			...
		</form>
	发送PUT请求
		<form action="ROOT/testRest/1" method="POST">
			<input type="hidden" name="_method" value="PUT">
			...
		</form>
	发送POST请求
		<form action="ROOT/testRest" method="POST">
			...
		</form>
	
	* 其实就是发送DELETE/PUT请求的时候,添加一个隐藏域.名称固定,值固定!那么,就被过过滤器给翻译成REST风格

	* 在目标方法中,使用 @PathVariable 注解来获取请求参数
	
	@PathVariable
		* 该注解是3.0版本后新增的东西,带占位符的URL!该功能在SpringMVC向REST目标挺进发展过程中具有里程碑的意义
		* 通过该注解,可以把URL中占位符参数绑定到控制处理器方法的形参中.
		例:
		@RequestMapping("/delete/{userid}")
		public String delete(@PathVariable("userid") String id)
		{
			userDao.delete(id);
			return null;
		}


	

	总结就是:如果只是使用GET/POST,没问题.但是因为现在浏览器不支持DELETE和PUT,
	所以就需要在web.xml中配置一个组件来翻译URL
	在表单中以POST的形式提交表单,但是添加隐藏域,指明真正的请求方式:PUT/DELETE


----------------------
ResponseEntity<T>		|
----------------------
	# restful,spring对其的支持
	# 在restful中,head,状态码用来表示请求状态,body仅仅装数据,不表示任何的状态
	# 这个类,可以设置响应状态码
	# 而且会自动的把泛型中的'对象'给格式化

		ResponseEntity<T> entity = ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			//设置状态为404,并且body值为null

		ResponseEntity<T> entity = ResponseEntity.status(HttpStatus.OK).body(User);
			//设置状态为200,并且填充Body值

		ResponseEntity<T> entity = ResponseEntity.ok(user);
			//上面那种方式的简写

		ResponseEntity<T> entity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
			//500状态
	
	# 构造函数s
		new ResponseEntity(T,HttpHeaders,HttpStatus)
	
	['demo']
		
		@RequestMapping(value="/{id}")
		public ResponseEntity<User> queryByUserId(@PathVariable(value="id")String id){
			try{
				User user = userService.queryByUserId(id);
				if(user == null){
					//资源未找到
					return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
				}
				//资源成功获取
				return ResponseEntity.status(HttpStatus.OK).body(User);
			}catch(Exception e){
				//服务器异常
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
			}
		}
	
	# 返回空值
		* 其实所谓的空值:添加数据成功,不需要给客户端响应任何数据
		* 注意辣,不需要ReqestMapping的URL映射,RESTful是根据请求方式来的

		@RequestMapping(methd=RequestMethod.POST)
		public ResponseEntity<void> create(User user{
			...
		}
	
	# 文件下载
		@GetMapping("/files/{filename:.+}")
		@ResponseBody
		public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

			Resource file = storageService.loadAsResource(filename);
			return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"" + file.getFilename() + "\"").body(file);
		}
		
	# build 和 body什么时候用 ？

----------------------
解决PUT无法提交的问题	|
----------------------
	<!-- 配置过滤器解决PUT请求无法提交表单的问题 (RESTful)-->
		<filter>
			<filter-name>HttpMethodFilter</filter-name>
			<filter-class>org.springframework.web.filter.HttpPutFormContentFilter</filter-class>
		</filter>
		<filter-mapping>
			<filter-name>HttpMethodFilter</filter-name>
			<url-pattern>/*</url-pattern>					*/
		</filter-mapping>

	<!-- 配置过滤器解决PUT/DELETE/请求无法提交表单的问题 (RESTful)-->
		
		<filter>
			<filter-name>HiddenHttpMethodFilter</filter-name>
			<filter-class>org.springframework.web.filter.HiddenHttpMethodFilter</filter-class>
		</filter>
		<filter-mapping>
			<filter-name>HiddenHttpMethodFilter</filter-name>
			<url-pattern>/*</url-pattern>				*/
		</filter-mapping>

----------------------
一些细节问题			|
----------------------
	* 通过ResponseEntity响应404状态码.不会触发web.xml中配置的404error页面