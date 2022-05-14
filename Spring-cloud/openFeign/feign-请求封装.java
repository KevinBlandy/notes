----------------------
请求参数封装
----------------------



----------------------
请求头添加
----------------------
	# @RequestHeader 指定一个请求头
		@RequestHeader("x-user-id")  Integer userId

	#  @RequestHeader 指定多个请求头
		@RequestHeader Map<String, String> headers

		* 目前这个版本还不支持 Map<String, String[]>, 也就是多个header value，需要自己拼接字符串
	
		



----------------------
SpringQueryMap
----------------------
	# 把对象/Map封装为查询参数
		* OpenFeign提供了 @QueryMap 注解，可以将 POJO 用作 GET 参数映射
		* 但是它没有 value 属性，spring不能直接用

		* spring提供了一个: @SpringQueryMap
		
		* 可以自定义 QueryMapEncoder 来实现自定义的编码方式

	# QueryMap
		@Retention(RetentionPolicy.RUNTIME)
		@Target({ ElementType.PARAMETER })
		public @interface SpringQueryMap {

			@AliasFor("encoded")
			boolean value() default false;

			@AliasFor("value")
			boolean encoded() default false;
				* 指定pojo对象/map的属性值是否已经是被编码了的

		}
	

	# Demo

		// Params.java
		public class Params {
			private String param1;
			private String param2;

			// [Getters and setters omitted for brevity]
		}


		@FeignClient("demo")
		public interface DemoTemplate {

			@GetMapping(path = "/demo")
			String demoEndpoint(@SpringQueryMap Params params);
		}


----------------------
MatrixVariable
----------------------
	# MatrixVariable
		@Target(ElementType.PARAMETER)
		@Retention(RetentionPolicy.RUNTIME)
		@Documented
		public @interface MatrixVariable {
			@AliasFor("name")
			String value() default "";
			@AliasFor("value")
			String name() default "";
				
			String pathVar() default ValueConstants.DEFAULT_NONE;
			boolean required() default true;
			String defaultValue() default ValueConstants.DEFAULT_NONE;
		}




----------------------
Multipart
----------------------
	# 接口
		@RequestMapping(value = "/demo/upload", method = RequestMethod.POST, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
		String upload (@RequestPart("file") MultipartFile[] multipartFile,
				   @RequestPart("body") String body,
				   @RequestPart("name") String name,
				   @SpringQueryMap Map<String, String> queryParams);
			
	# 服务
		@PostMapping("/upload")
		public Object upload (HttpServletRequest request,
								@RequestPart("file") MultipartFile[] multipartFiles,
							  @RequestPart("body") String body,
							  @RequestPart("name") String name,
							  @RequestParam("page") int page){
			for (MultipartFile multipartFile : multipartFiles){
				LOGGER.info("file-name:{},file-size:{}", multipartFile.getOriginalFilename(), multipartFile.getSize());
			}
			LOGGER.info("body:{}, name:{}, page:{}, query:{}", body, name, page, request.getQueryString());
			return "success";
		}
	
	
	
	# 存在的问题
		* body 参数本来是一个JSON对象

		* OpenFeign 在执行 multipart 请求的时候, 接口中的 @RequestPart 参数只能是 MultipartFile 或者 String/[]byte
		* 框架默认不会把 对象/Map 序列化为JSON
			 @RequestPart("body") Map<String, Object> body, // 会异常
			
		* 如果服务方的参数，使用其他对象也会导致异常
			 @RequestPart("body") Map<String, Object> body, // 会异常
		* 因为 OpenFeign 好像没办法给 Part添加额外的Header，导致服务端的 @RequestPart 不能按照ContentType找到解析器