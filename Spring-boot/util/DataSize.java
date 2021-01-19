------------------------------
DataSize
------------------------------
	# 存储单位的工具类


	# 可以直接注入
		config:
		  size: 1KB

		@Value("${config.size}")
		private DataSize dataSize;
	
------------------------------
DataUnit
------------------------------
	BYTES
	KILOBYTES
	MEGABYTES
	MEGABYTES
	TERABYTES

