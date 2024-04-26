---------------------------
GET							|
---------------------------
	# �������͵�����ʽ
		* ��Ӧ ResponseEntity 
		* ���Ի�ȡ����Ӧͷ,״̬�����Ϣ

			<T> ResponseEntity<T> getForEntity(String url, Class<T> responseType, Object... uriVariables)
			<T> ResponseEntity<T> getForEntity(URI url, Class<T> responseType) throws RestClientException;
			<T> ResponseEntity<T> getForEntity(String url, Class<T> responseType, Map<String, ?> uriVariables) throws RestClientException;

		
		* ��Ӧ����,ֱ�ӷ��ر����Ķ���
			<T> T getForObject(String url, Class<T> responseType, Object... uriVariables) throws RestClientException;
			<T> T getForObject(String url, Class<T> responseType, Map<String, ?> uriVariables) throws RestClientException;
			<T> T getForObject(URI url, Class<T> responseType) throws RestClientException;

	
	# ����ʹ��ռλ�������ò�ѯ����
		ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://localhost?name={1}&age={2}",String.class,"KevinBlandy","23");
	
	# ����ʹ�� Map ������ռλ������
		* ʹ�÷��� "{}" ��ռλ

        Map<String,String> param = new HashMap<>();
        param.put("name","KevinBlandy");
        param.put("age","23");

        restTemplate.getForEntity("http://localhost/user?name={name}&age={age}",String.class,param);
	
	# ����ʹ�� UriComponentsBuilder ������ URI ����
		
		ResponseEntity<String> response = this.restTemplate.getForEntity(UriComponentsBuilder.fromHttpUrl(url)
											// ��ѯ����
											.queryParam("name", name)
											.queryParam("val", val)
											.encode()
										   .toUriString(), String.class);
	

	# ע�⣬ restTemplate ��ɵ�ƣ����Զ���URL�еĲ�ѯ�������б��룬Ҳ����˵��Բ�ѯ���������һ�κ����п��ܻ���ж��α��롣
