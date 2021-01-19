------------------------
swagger
------------------------
	# 官网
		https://swagger.io/
	
	
-----------------------------
swagger - springboot 整合
-----------------------------
	# springfox
		http://springfox.github.io/springfox/
		http://springfox.github.io/springfox/docs/current/

	
	# springfox 3
		* Spring Boot 支持 springfox-boot-starter 依赖性（零配置，自动配置支持）

		<!-- https://mvnrepository.com/artifact/io.springfox/springfox-boot-starter -->
		<dependency>
			<groupId>io.springfox</groupId>
			<artifactId>springfox-boot-starter</artifactId>
			<version>3.0.0</version>
		</dependency>

	
	# 启用 swagger
		@EnableOpenApi
	
	# 配置 Docket
	
	
	# 标识注解
		* controoller
			@Api(tags = {"用户管理"})

		* 请求方法
			@ApiOperation("添加用户")
		
		* 请求查询参数
			@ApiParam("用户的昵称")
		
		* 响应对象
			@ApiModel("用户信息")
		
		* 响应对象的属性
			@ApiModelProperty("用户id")

	# 访问
		http://localhost/swagger-ui.html
		http://localhost/v2/api-docs

		http://host/context-path/swagger-ui.html
		http://host/context-path/swagger-ui/index.html
		http://host/context-path/swagger-ui/
	


-----------------------------
swagger - springboot 整合
-----------------------------
	# controller 方法, 返回值如果是对象, 就会被 swagger 扫描到, 并且识别为 model

