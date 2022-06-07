---------------------
HttpHeaders
---------------------
	# Http 请求头

	# 静态方法
		static HttpHeaders of(Map<String,List<String>> headerMap, BiPredicate<String,String> filter)


	# 实例方法
		Optional<String> firstValue(String name)
		OptionalLong firstValueAsLong(String name)
			* 获取第一个值

		List<String> allValues(String name)
		Map<String,List<String>> map()
			* 转换为Map
		
