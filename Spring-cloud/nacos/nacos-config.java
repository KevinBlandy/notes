------------------------------
config
------------------------------

	spring.cloud.nacos.config.prefix=${spring.application.name}
		* 设置data-id中的prefix前缀

	spring.cloud.nacos.config.file-extension=properties
		* 配置内容的数据格式，仅仅支持 yaml 和 properties
	
	spring.cloud.nacos.config.enabled=false
		* 是否彻底关闭nacos配置中心

	spring.cloud.nacos.config.shared-configs

------------------------------
discovery
------------------------------