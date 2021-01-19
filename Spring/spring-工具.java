---------------------------------------
Resource								|
---------------------------------------
	# 可以加载 classpath 目录下的资源文件
		Resource resource = new ClassPathResource("/zhsq-core.properties");
		Properties props props = PropertiesLoaderUtils.loadProperties(resource);
	

---------------------------------------
Environment								|
---------------------------------------
	# org.springframework.core.env.Environment

---------------------------------------
DigestUtils								|
---------------------------------------
	# 加密解密工具类
	# 都是静态方法
	# md5加密
		md5DigestAsHex("Kevin".getBytes())
