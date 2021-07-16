--------------------
RequestHeaderSize
--------------------
	# 限制请求头大小的请求
		* 默认是 16KB，超过的话就会响应：HttpStatus.REQUEST_HEADER_FIELDS_TOO_LARGE
	
	# 工厂类
		RequestHeaderSizeGatewayFilterFactory

		* 配置属性
			private DataSize maxSize = DataSize.ofBytes(16000L);