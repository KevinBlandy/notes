------------------------
Sentinel
------------------------
	# 地址
		https://sentinelguard.io/zh-cn/

		https://github.com/alibaba/Sentinel
		https://github.com/alibaba/Sentinel/wiki
	
		https://github.com/alibaba/spring-cloud-alibaba/wiki/Sentinel

		https://github.com/alibaba/Sentinel/wiki/Sentinel-%E6%A0%B8%E5%BF%83%E7%B1%BB%E8%A7%A3%E6%9E%90
	
	# Maven依赖
		<!-- https://mvnrepository.com/artifact/com.alibaba.cloud/spring-cloud-starter-alibaba-sentinel -->
		<dependency>
			<groupId>com.alibaba.cloud</groupId>
			<artifactId>spring-cloud-starter-alibaba-sentinel</artifactId>
			<version>2021.1</version>
		</dependency>
	
	
	# 熔断 和 降级
		* 被调用的服务出现故障，不再进行调用，需要调用时返回特殊的数据


	# 组件
		* sentinel	核心
		* dashboard 监控台，WEB
	
	# 使用
		1. 定义资源
		2. 定义规则
		3. 测试规则是否生效
	
	
	


	
