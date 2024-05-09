----------------------------------------
knife4j
----------------------------------------
	# Knife4j
		knife4j是为Java MVC框架集成Swagger生成Api文档的工具,前身是swagger-bootstrap-ui
	
	# 地址
		https://doc.xiaominfo.com/
	
	# Maven
		<!-- https://mvnrepository.com/artifact/com.github.xiaoymin/knife4j-spring-boot-starter -->
		<dependency>
			<groupId>com.github.xiaoymin</groupId>
			<artifactId>knife4j-spring-boot-starter</artifactId>
			<version>3.0.3</version>
		</dependency>
	


----------------------------------------
openapi3 & sb3
----------------------------------------
	# 依赖
		<dependency>
			<groupId>com.github.xiaoymin</groupId>
			<artifactId>knife4j-openapi3-jakarta-spring-boot-starter</artifactId>
			<version>${knife4j-openapi3-jakarta.version}</version>
		</dependency>
	
	# 配置
		# springdoc-openapi项目配置
		springdoc:
		  swagger-ui:
			enabled: true
			path: /swagger-ui.html
			tags-sorter: alpha
			operations-sorter: alpha
		  api-docs:
			path: /v3/api-docs
		  # API 分组
		  group-configs:
			- group: "manager"
			  packages-to-scan: demo.web.controller.manager
            # 组名称
			- group: "user"
			  # 扫描的包
			  packages-to-scan: demo.web.controller.user
			  # 排除的包
			  packages-to-exclude: demo.web.controller.user.test
		
		# knife4j的增强配置，不需要增强可以不配
		knife4j:
		  enable: true
		  setting:
			language: zh_cn
			footer-custom-content: "Chatty Application"
	
	# 注解
		@Tag(name = “接口类描述”)			Controller 类
		@Operation(summary =“接口方法描述”)	Controller 方法
		@Parameters							Controller 方法
		@Parameter(description=“参数描述”)	Controller 方法上 @Parameters 里Controller 方法的参数
		@Parameter(hidden = true) 、@Operation(hidden = true)@Hidden	排除或隐藏api
		@Schema								DTO实体DTO实体属性
		@Hidden								隐藏API接口