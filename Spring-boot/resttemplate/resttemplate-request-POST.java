-------------------------
POST					 |
-------------------------
	URI postForLocation(String url, @Nullable Object request, Object... uriVariables) throws RestClientException;
	URI postForLocation(String url, @Nullable Object request, Map<String, ?> uriVariables)throws RestClientException;
	URI postForLocation(URI url, @Nullable Object request) throws RestClientException;

	<T> T postForObject(String url, @Nullable Object request, Class<T> responseType,Object... uriVariables) throws RestClientException;
	<T> T postForObject(String url, @Nullable Object request, Class<T> responseType,Map<String, ?> uriVariables) throws RestClientException;
	<T> T postForObject(URI url, @Nullable Object request, Class<T> responseType) throws RestClientException;

	<T> ResponseEntity<T> postForEntity(String url, @Nullable Object request, Class<T> responseType,Object... uriVariables) throws RestClientException;
	<T> ResponseEntity<T> postForEntity(String url, @Nullable Object request, Class<T> responseType,Map<String, ?> uriVariables) throws RestClientException;
	<T> ResponseEntity<T> postForEntity(URI url, @Nullable Object request, Class<T> responseType)throws RestClientException;



	# http表单体
		RestTemplate restTemplate = new RestTemplate();

		// 构建消息头
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_FORM_URLENCODED_VALUE);

		// 构建消息体
		MultiValueMap<String, Object> requestBody = new LinkedMultiValueMap<>();
		requestBody.add("name", "KevinBlandy"); // 单个消息
		requestBody.add("age", "23");
		requestBody.addAll("skills", Arrays.asList("java","python","javascript","c")); // 多个消息
		
		// 完整的http消息体
		HttpEntity<MultiValueMap> httpEntity = new HttpEntity<>(requestBody,httpHeaders);

		ResponseEntity<String> responseEntity = restTemplate.postForEntity("http://localhost/user.do", httpEntity, String.class);
	
	# json表单体

		RestTemplate restTemplate = new RestTemplate();

		// 构建消息头
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
		
		// 构建JSON对象体
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("name","KevinBlandy");
		jsonObject.put("age",23);

		// 构建完整的http表单体
		HttpEntity<JSONObject> httpEntity = new HttpEntity<>(jsonObject,httpHeaders);
		// 可以直接使用json字符串 : HttpEntity<String> httpEntity = new HttpEntity<>(jsonObject.toJSONString(),httpHeaders);

		ResponseEntity<String> responseEntity = restTemplate.postForEntity("http://localhost:8081/user.do", httpEntity, String.class);

	
	# 多部件表单体
		RestTemplate restTemplate = new RestTemplate();
		
		// 消息头
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA); // 多部件表单体

		MultipartBodyBuilder multipartBodyBuilder = new MultipartBodyBuilder();
		// 设置普通表单项
		multipartBodyBuilder.part("name", "KevinBlandy");
		multipartBodyBuilder.part("skill", Arrays.asList("Java","Python","Javascript","C"));

		// 设置文件表单项(表单名,文件对象,ContentType)
		Resource file1 = new FileSystemResource("D:\\20181009153347.jpg");
		multipartBodyBuilder.part("file", file1,MediaType.IMAGE_JPEG).header("Bar", "Foo");

		Resource file2 = new ClassPathResource("log-1.log"); // Resource 可以有很多种实现,用于从不同的源加载数据
		multipartBodyBuilder.part("file", file2,MediaType.TEXT_PLAIN).header("Bar", "Foo"); // 可以设置多个同表单名称的文件表单项
		
		// 完整的消息体
		MultiValueMap<String, HttpEntity<?>> multipartBody = multipartBodyBuilder.build();

		ResponseEntity<JSONObject> responseEntity = restTemplate.postForEntity("http://localhost:8081/user.do", multipartBody, JSONObject.class);

		System.out.println(responseEntity);