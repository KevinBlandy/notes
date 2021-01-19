--------------------------------
ConfigServer					|
--------------------------------
	# Maven
			<dependency>
				<groupId>org.springframework.cloud</groupId>
				<artifactId>spring-cloud-config-server</artifactId>
			</dependency>
	
	# 默认使用Git仓库作为配置仓库
		* 配置文件必须已经被版本控制器管理
		* spring.config.server.git.uri 配合的根路径,必须要有 .git 文件夹(必须配置到根目录)
		* 并且配置文件需要提交到版本控制
	
	# 注解驱动
		@EnableConfigServer
	
	# 基本的配置
		server:
		  port: 8015

		spring:
		  application:
			name: config-server
		  cloud:
			config:
			  server:
				git:
				  # git配置仓库的位置(不能以为 / 结尾),根目录下必须要有: .git 目录(说白了,必须是GIT的根目录)
				  uri: https://github.com/KevinBlandy/temp-config.git
				  # 本地缓存的目录
				  basedir: D:\\temp\\config
				  # 配置仓库下的相对搜索路径,可以有多个
				  search-paths:
					- config
				  # 访问仓库的账户名密码
				  username: KevinBlandy
				  password: F8575532
	
	# 服务启动OK后,可以通过浏览器访问
		http://localhost:8015/config/springcloud.yml

		* 可以访问的路径
			/{application}/{profile}/{label}

			/{application}-{profile}.yml
			/{label}/{application}-{profile}.yml

			/{application}-{profile}.properties
			/{label}/{application}-{profile}.properties
			
			application
				* 要以客户端的 spring.application.name 来定义
			profile
				* 环境
			label
				* 可以指定分支,默认为MASTER
		
		* 注意,要带上 search-paths 路径
	
	# ConifgServer还会在本地缓存配置文件
		* 本地缓存的目录
			C:\Users\KevinBlandy\AppData\Local\Temp\config-repo-[随机数]
		
		* 防止Git服务器宕机而无法加载配置
		* 可以通过配置来指定特殊的缓存目录
			spring.cloud.config.server.git.basedir=D:\\temp\\config
	
	
	
	# 可以使用本地的 git 仓库作为配置仓库
		spring.config.server.git.uri=file:D:\\config-rep\\springcloud-config

		* 使用:file: 开头表示使用本地的配置仓库
	
	# 使用URI占位符来区分不同的应用
		* application,profile,label 不仅仅可以标识配置文件规则

		* 还可以用于ConfigServer对于Git仓库的Uri地址
			spring.config.server.git.uri=https://github.com/KevinBlandy/{application}-config.git
			
			application
				* 表示应用名,当客户端发起请求的时候,Server会根据客户端的 spring.application.name 信息来填充URI
				* 就可以根据不同的服务,来动态的加载不同URI下的资源
			
			label
				* 这个参数比较特别,如果Git分支名称包含了 '/' ,那么在label参数在http的uri中应该使用 '_' 来代替,避免改变了URI的含义

			* 目前测试,好像占位符{application}不支持配置在 uri 属性中,如果使用 {application},在ConfigServer启动的时候会被替换为 : app,从而导致系统异常,提示不存在的目录
				配置 -> uri: 'https://github.com/KevinBlandy/{application}-config-rep.git'
				异常 -> Cannot clone or checkout repository: https://github.com/KevinBlandy/app-config-rep.git

			* 其实也可以解决,亲测,在ConfigServer启动的时候,先在根路径创建目录: {application},application替换为 app,并且初始化为git目录,并且有commit文件
				配置 -> uri: 'file:D:\\config-rep\\{application}-config'
				新建 -> D:\\config-rep\\app-config
			
			* 上述俩问题是因为健康监测导致的
		
		* 占位符还可以使用在搜索路径上,以此来区分同一个仓库下的不同文件夹
			spring
			  cloud:
			    config:
			      server:
				    # Git的根目录
				    uri: 'file:D:\\config-rep'
				    search-paths:
					  # 如果是yml的话,要使用双引号
					  - '{application}-config'

			* 根据不用应用名,去不同的子目录下搜索
			* 这个靠谱,经过试验没啥问题

		
	
	# 多仓库的配置
		spring.cloud.config.server.git.uri='file:\\default'
		spring.cloud.config.server.git.repos.dev.pattern='dev/*'
		spring.cloud.config.server.git.repos.dev.uri='file:D:\\dev'

		spring.cloud.config.server.git.repos.test.pattern='test/*'
		spring.cloud.config.server.git.repos.test.uri='file:D:test'

		* git.uri 指定的默认的仓库,系统启动就会去加载
		
		* 指定特殊名称的仓库,以及访问的pattern
			git.repos.<name>.pattern
			git.repos.<name>.uri

--------------------------------
访问权限						|
--------------------------------
	# 默认采用HTTP协议的鉴权
		spring.cloud.config.server.git.username
		spring.cloud.config.server.git.password
	
	# 采用SSH协议的鉴权
		需要自己在当前主机环境生成 ssh密钥对,并且把公钥配置到git的服务器

--------------------------------
本地文件系统					|
--------------------------------
	# 不使用GIT/SVN,直接使用文件系统
		spring.profiles.active=native

		cloud.config.server.native.search-locations
			* 指定一个或者多个配置文件的搜索路径

		* 不建议使用本地文件系统,还是要使用GIT

--------------------------------
健康监测						|
--------------------------------
	# 在 gti.uri 配置中使用 {application} 占位符,会给出警告,(找不到 app 仓库)因为Server实现了健康监测器
		ConfigServerHealthIndicator

		* 在该检测器中默认构建一个 application 为 app 的仓库

	
	# 可以直接Git的仓库中新建一个配置库,让健康检测器访问
		spring
		  cloud:
			config:
			  server:
				health:
				  # 可以选择关闭健康监测
				  enabled: false
				  # Map<String, Repository> repositories
				  repositories:
					check:
					  name: check-rep
					  label: master
					  profiles: default
		

--------------------------------
属性覆盖						|
--------------------------------
	# 通过该属性配置的K/V都会被加载到客户端,但是客户端不能修改
		spring:
		  cloud:
			config:
			  server:
			    # Map<String, String>
				overrides:
				  name: KevinBlandy
				  skill:
					- Java
					- Python

--------------------------------
安全保护						|
--------------------------------
	# 服务端添加Security依赖
		<dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>
	
	# 服务端配置用户名和密码
		spring:
		  security:
			user:
			  # 服务端的账户名密码
			  name: config
			  password: 123456
		
		* 服务端访问端点,也需要使用该账户名密码登录
	
	# 客户端使用用户名和密码连接
		spring:
		  cloud:
			config:
			  # 访问服务端的账户名密码
			  username: config
			  password: 123456
	
--------------------------------
配置的加密/解密					|
--------------------------------
	# 在SpringCloud Config 中加载的配置使用 {cipher} 前缀来标识,表示该内容是一个加密的配置值

		spring.datasource.password={cipher}e10adc3949ba59abbe56e057f20f883e

		* 配置服务器进行加载的时候,会自动为带有 {cipher} 前缀的配置进行解密
		* 使用 yml 配置,需要使用 '' 包裹值
		
	# 使用的前提,需要在Oracle官方下载依赖:jce
		local_policy.jar
		US_export_policy.jar
	
		* Java8的下载地址:https://www.oracle.com/technetwork/java/javase/downloads/jce8-download-2133166.html
		* 添加到: $JAVA_HOME/jre/lib/security 目录下
	
	# 服务端添加密钥配置(对称加密),需要配置在:bootstrap.yml
		encrypt:
		  key: 4D44331C666011E9B03100163E11BA6D
	
	# 可以访问的端点
		/encrypt/status	加密功能的状态
		/key			查看密钥(非对称加密)
		/encrypt		对请求Body加密
		/decrypt		对请求Body解密


	# 可以使用非对称加密
		* 生成证书
			keytool -genkey -alias mytestkey -keyalg RSA  -dname "CN=Config Server,OU=Unit,O=Organization,L=City,S=State,C=CN"   -keypass changeme -keystore server.jks -storepass letmein
	
		* 服务器配置
			encrypt:
			  key-store:
				# keystore文件位置
				location: classpath:ssl/server.jks
				# 证书别名
				alias: mytestkey
				# keystore密码
				password: letmein
				# JKS类型keystore的证书密码
				secret: changeme
				# 证书类型
				type: JKS