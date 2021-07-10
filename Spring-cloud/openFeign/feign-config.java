------------------
config
------------------
	# 文档
		https://docs.spring.io/spring-cloud-openfeign/docs/current/reference/html/appendix.html
	

------------------
config
------------------
	feign.compression.request.enabled=true
	feign.compression.response.enabled=true
		* 是否开启请求/响应压缩
	feign.compression.request.mime-types=text/xml,application/xml,application/json
		* 仅仅只对这些类型的请求体进行压缩
	feign.compression.request.min-request-size=2048
		* 最小压缩体积
	feign.compression.response.useGzipDecoder=true
		* 默认使用gzip解响应压缩

	feign.autoconfiguration.jackson.enabled=true
		* 启用 Jackson Modules 来支持 org.springframework.data.domain.Page 和 org.springframework.data.domain.Sort 解码

	feign.client.refresh-enabled=true
		* 如果开启了refresh，每个feign客户端都是以 feign.Request.Options 作为 scope 范围的bean来创建的。
		* 这意味着诸如connectTimeout和readTimeout等属性可以通过 POST /actuator/refresh 对任何Feign客户端实例进行刷新
		* 默认为 false

		* 不能在 @FeignClient 上添加 @RefreshScope 注解

	feign.okhttp.enabled=false
		* 使用okhttp客户端
	
	feign.metrics.enabled=true
		* 开启指标
	
	
		