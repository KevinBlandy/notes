--------------------------------
WebSocket-路径映射				|
--------------------------------
	# 同一个应用中不能不是两个URI相同的端点
	# URI的大小写是敏感的
	# 支持模版URI
		* @ServerEndpoint(value="/test/{name}")
		* ws:/ip/test/kevin				name=kevin
		* ws:/ip/test/litch				name=kevin
		* 可以把路径参数映射到模板中,参考RESTful
	
