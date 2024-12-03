---------------------------
Spring-boot �ļ��ϴ�		|
---------------------------
	# Spring Boot����Servlet 3 javax.servlet.http.Part API��֧���ļ��ϴ��� 
	# Ĭ������£� Spring Boot����Spring MVC�ڵ���������ÿ���ļ����1Mb�� ���10Mb���ļ����ݡ� 
	# ����Ը�����Щֵ�� Ҳ����������ʱ�ļ��洢��λ�ã� ���磬 �洢�� /tmp �ļ����£� ����������ˢ�µ����̵ķ�ֵ�� ͨ��ʹ��MultipartProperties�౩¶�����ԣ� ��
	# �������Ҫ�����ļ��������ƣ� ���磬 �������� multipart.maxFileSize ����ֵΪ -1 ��
	# ������Ҫ���ղ��֣� multipart�� �����ļ�������ΪSpring MVC�������� controller�� �������б� @RequestParam ע���
	# MultipartFile���͵Ĳ���ʱ�� multipart֧�־ͷǳ������ˡ�
	# ����ο� MultipartAutoConfiguration Դ��

---------------------------
Spring-boot �����ļ�		|
---------------------------
	1,�����ϴ�֧��

		@Bean  
		public MultipartConfigElement multipartConfigElement() {  
			MultipartConfigFactory factory = new MultipartConfigFactory();  
			factory.setMaxFileSize("128KB");  
			factory.setMaxRequestSize("128KB");  
			return factory.createMultipartConfig();  
		}  
		
	2,Controller
		 @RequestParam("file") MultipartFile file	//�����ļ�����
		 @RequestParam("name") String name			//�������������Ĳ���
		
	
	# ͨ�������������ϴ���С
		spring.servlet.multipart.max-file-size=30MB
			# ���ĵ����ļ���С
		spring.servlet.multipart.max-request-size=30MB
			# ���������С
		spring.servlet.multipart.enabled=true
			# �Ƿ�֧���ļ��ϴ�
		spring.servlet.multipart.location=/temp
			# ������ʱĿ¼
		spring.servlet.multipart.resolve-lazily=false
			# �Ƿ��ӳٽ���,Ĭ�� false
		spring.servlet.multipart.file-size-threshold=0
			# �ļ���С��ֵ,�����������ֵʱ��д�뵽����(��ʱĿ¼),��������ڴ���.Ĭ��0

		* ������:MultipartProperties
	
---------------------------
Spring-boot �����ϴ�		|
---------------------------
	1,����
		* ͬ�ϲ�����
	
	2,Controller
		* ��Ҫ����request����,����ǿ��ת��Ϊ MultipartHttpServletRequest  
		* ͨ�� getFiles(String name); ����ȡ�ļ����϶���
		* ��һ�ַ�ʽ
			List<MultipartFile> files = ((MultipartHttpServletRequest)request).getFiles("file");  
		
		2,�ڶ��ַ�ʽ
			 @RequestParam("files") MultipartFile[] file

---------------------------
Spring-boot �ļ�����		|
---------------------------
	
	# ʹ�� ResponseEntity ��Ӧ����
		@GetMapping("/files/{filename:.+}")
		@ResponseBody
		public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
			Resource file = storageService.loadAsResource(filename);
			return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, ContentDisposition
				   .attachment()    // ������ʽ
				   .filename("my.txt", StandardCharsets.UTF_8)  // �ļ����� & ����
				   .build()
				   .toString()).body(file);
		}

	# ʹ�� StreamingResponseBody ��Ӧ����
		