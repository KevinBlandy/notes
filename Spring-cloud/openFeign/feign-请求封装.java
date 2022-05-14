----------------------
���������װ
----------------------



----------------------
����ͷ���
----------------------
	# @RequestHeader ָ��һ������ͷ
		@RequestHeader("x-user-id")  Integer userId

	#  @RequestHeader ָ���������ͷ
		@RequestHeader Map<String, String> headers

		* Ŀǰ����汾����֧�� Map<String, String[]>, Ҳ���Ƕ��header value����Ҫ�Լ�ƴ���ַ���
	
		



----------------------
SpringQueryMap
----------------------
	# �Ѷ���/Map��װΪ��ѯ����
		* OpenFeign�ṩ�� @QueryMap ע�⣬���Խ� POJO ���� GET ����ӳ��
		* ������û�� value ���ԣ�spring����ֱ����

		* spring�ṩ��һ��: @SpringQueryMap
		
		* �����Զ��� QueryMapEncoder ��ʵ���Զ���ı��뷽ʽ

	# QueryMap
		@Retention(RetentionPolicy.RUNTIME)
		@Target({ ElementType.PARAMETER })
		public @interface SpringQueryMap {

			@AliasFor("encoded")
			boolean value() default false;

			@AliasFor("value")
			boolean encoded() default false;
				* ָ��pojo����/map������ֵ�Ƿ��Ѿ��Ǳ������˵�

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
	# �ӿ�
		@RequestMapping(value = "/demo/upload", method = RequestMethod.POST, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
		String upload (@RequestPart("file") MultipartFile[] multipartFile,
				   @RequestPart("body") String body,
				   @RequestPart("name") String name,
				   @SpringQueryMap Map<String, String> queryParams);
			
	# ����
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
	
	
	
	# ���ڵ�����
		* body ����������һ��JSON����

		* OpenFeign ��ִ�� multipart �����ʱ��, �ӿ��е� @RequestPart ����ֻ���� MultipartFile ���� String/[]byte
		* ���Ĭ�ϲ���� ����/Map ���л�ΪJSON
			 @RequestPart("body") Map<String, Object> body, // ���쳣
			
		* ������񷽵Ĳ�����ʹ����������Ҳ�ᵼ���쳣
			 @RequestPart("body") Map<String, Object> body, // ���쳣
		* ��Ϊ OpenFeign ����û�취�� Part��Ӷ����Header�����·���˵� @RequestPart ���ܰ���ContentType�ҵ�������