---------------------------
GET							|
---------------------------
	# 两种类型的请求方式
		* 响应 ResponseEntity 
		* 可以获取到响应头,状态码等信息

			<T> ResponseEntity<T> getForEntity(String url, Class<T> responseType, Object... uriVariables)
			<T> ResponseEntity<T> getForEntity(URI url, Class<T> responseType) throws RestClientException;
			<T> ResponseEntity<T> getForEntity(String url, Class<T> responseType, Map<String, ?> uriVariables) throws RestClientException;

		
		* 响应对象,直接返回编码后的对象
			<T> T getForObject(String url, Class<T> responseType, Object... uriVariables) throws RestClientException;
			<T> T getForObject(String url, Class<T> responseType, Map<String, ?> uriVariables) throws RestClientException;
			<T> T getForObject(URI url, Class<T> responseType) throws RestClientException;

	
	# 可以使用占位符来设置查询参数
		ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://localhost?name={1}&age={2}",String.class,"KevinBlandy","23");
	
	# 可以使用 Map 来设置占位符参数
		* 使用符号 "{}" 来占位

        Map<String,String> param = new HashMap<>();
        param.put("name","KevinBlandy");
        param.put("age","23");

        restTemplate.getForEntity("http://localhost/user?name={name}&age={age}",String.class,param);
	
	# 可以使用 UriComponentsBuilder 来构建 URI 对象
	
