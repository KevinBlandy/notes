----------------------
HttpHeaders
----------------------
	# abstract class HttpHeaders implements Iterable<Map.Entry<String, String>>
		* 抽线类
		* 默认实现
			DefaultHttpHeaders
			EmptyHttpHeaders
				EmptyHttpHeaders.INSTANCE // 默认的空响应头实现

			ReadOnlyHttpHeaders
	
	
	# 静态属性
		

	# 静态方法
		

	# 实例方法
		HttpHeaders add(HttpHeaders headers)
		HttpHeaders add(CharSequence name, Iterable<?> values)
		HttpHeaders add(CharSequence name, Object value)
		HttpHeaders addInt(CharSequence name, int value)