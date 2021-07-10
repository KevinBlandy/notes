---------------------
config
---------------------
	# 作为配置中心使用

---------------------
使用步骤
---------------------
	# 添加依赖
		<!-- https://mvnrepository.com/artifact/com.alibaba.cloud/spring-cloud-starter-alibaba-nacos-config -->
		<dependency>
			<groupId>com.alibaba.cloud</groupId>
			<artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
			<version>${spring-cloud-starter-alibaba-nacos.version}</version>
		</dependency>

	# 添加配置
		# 在boostrap.yaml 指定nacos地址
		spring.cloud.nacos.config.server-addr=127.0.0.1:8848
		# 应用名称是必须的
		spring.application.name=example

	# 在代码中使用配置
		@RestController
		@RequestMapping("/config")
		@RefreshScope					// 使用 @RefreshScope 来开启配置的动态刷新
		public class ConfigController {

			@Value("${useLocalCache:false}")		// 加载的配置项
			private boolean useLocalCache;

			@RequestMapping("/get")
			public boolean get() {
				return useLocalCache;
			}
		}

---------------------
核心的一些概念
---------------------
	Data ID
		* 组成公式: ${prefix}-${spring.profiles.active}.${file-extension}

		* 之所以需要配置 spring.application.name, 是因为它是构成 Nacos 配置管理 dataId字段的一部分

		* prefix 默认为 spring.application.name 的值, 也可以通过配置项 spring.cloud.nacos.config.prefix 来配置
		* spring.profiles.active 即为当前环境对应的 profile, 详情可以参考 Spring Boot文档。 
			* 注意这个是可选的: 当 spring.profiles.active 为空时，对应的连接符 - 也将不存在，dataId 的拼接格式变成 ${prefix}.${file-extension}
		* file-exetension 为配置内容的数据格式, 可以通过配置项 spring.cloud.nacos.config.file-extension 来配置, 目前只支持 properties 和 yaml 类型

			 
	Group
	