-------------------
配置文件
-------------------
	# 文档
		https://docs.spring.io/spring-boot/docs/current/reference/html/spring-boot-features.html#boot-features-external-config-files
	
	# 所有配置文件相关的参数
		spring.config.activate.on-cloud-platform
		spring.config.activate.on-profile
		spring.config.additional-location
		spring.config.import
		spring.config.location
		spring.config.name=application
		
		* spring.config.name 和 spring.config.location 很早就用于确定必须加载哪些文件。必须将它们定义为环境属性（通常是OS环境变量，系统属性或命令行参数）
			System.setProperty("spring.config.additional-location", "optional:file:D:\\config/");

	
	# active & include 指定后缀的文件
		spring.profiles.active 
			* 激活指定后缀的配置文件
				spring.profiles.active=pro // 激活文件 ${spring.config.name}-${spring.profiles.active}.properties/yaml
			
		spring.profiles.include
			* 包含一个或者多个后缀的配置文件
				spring.profiles.include=mysql,redis	 // 激活文件 ${spring.config.name}-${spring.profiles.include}.properties/yaml
					
	
	# Profile Groups，一次性激活N个指定后缀的配置文件
		spring.profiles.group.prod=proddb,prodmq,prodmetrics


	# 继续兼容旧版(2.3)的配置加载方式
		spring.config.use-legacy-processing=true
	
	# springboot启动加载配置文件路径
		1. classpath:/
		2. classpath:/config/
		3. file:./
		4. file:./config/*/																		*/
		5. file:./config/

		* 上面的目录从上往下，底下的配置覆盖上面的

		* 默认名称为: application.properties/application.yaml
		* 默认名称可以通过: spring.config.name 配置修改


	# 导入外部的配置文件
		spring.config.location
			* 重新指定了默认配置文件

		spring.config.import
			* 新导入配置文件，而不是重新指定，可以理解为，在这个配置项的位置导入其他的配置项
			* 它支持Kubernetes中的配置树，需要使用configtree:前缀
				spring.config.import=optional:configtree:/etc/config/
			
			* 如果要从同一父文件夹导入多个配置树，则可以使用通配符快捷方式
				spring.config.import=optional:configtree:/etc/config/*/																									<--*/
		
		* 如果有多个，可以使用逗号分隔
			spring.config.location=optional:classpath:/default.properties,optional:classpath:/override.properties

		* 文件可以添加 optional: 前缀来标识它是可选的，如果这个文件不存在，系统就会跳过
			spring.config.location=optional:/etc/config/application.properties
		
		* 如果想将所有指定的配置文件都默认为可选的，可以通过来设置
			SpringApplication.setDefaultProperties("spring.config.on-location-not-found", "ignore"); // 也可以把这个值写入到环境变量
		
		* 文件必须要有扩展名 properties/yaml，如果没有的话，需要在配置指定
			spring.config.import=file:/etc/config/myconfig[.yaml]
	
	# 配置文件的加载目录

		* 重新设定配置文件加载目录
			spring.config.location 
				spring.config.location=optional:classpath:/custom-config/,optional:file:./custom-config/
			
			* 这个配置，也可以指定一个目录，用于替换默认的配置文件加载路径，这个配置应该以目录: / 结尾
			* 如果指定了多个位置，则后面的位置可以覆盖前面的位置，并且默认的配置文件路径不会生效了

	
		* 添加新的配置文件目录
			spring.config.additional-location
				spring.config.additional-location=optional:classpath:/custom-config/,optional:file:./custom-config/

			* 添加新的配置文件加载路径到默认的路径后面，而不是替换
		
		* file目录的配置都支持通配符(classpath不支持)，表示加载下面所有目录的配置文件
			spring.config.location=optional:file:/config/*/																		*/
				/config/redis/application.properties
				/config/mysql/application.properties
	

	# 同一个物理文件，可以拆分为多个不同的逻辑配置文件
		* Yml的拆分，使用 --- 
			spring.application.name: MyApp
			---
			spring.config.activate.on-cloud-platform: kubernetes
			spring.application.name: MyCloudApp
	
		* properties的拆分，使用 #---
			spring.application.name=MyApp
			#---
			spring.config.activate.on-cloud-platform=kubernetes
			spring.application.name=MyCloudApp
	
		
		* 多个逻辑配置，有相同属性的，后面的会覆盖前面的




		* 逻辑文件的属性的激活
			spring.config.activate.on-profile
				* 当前逻辑配置，在spring.profiles.active指定的模式下才生效
				* 支持关系运算
					spring.config.activate.on-profile=prod | staging

			spring.config.activate.on-cloud-platform
				* 运行平台检测，在指定CloudPlatform下才生效
	
		
	# Origin Chains
		* Origin 接口更新，使用了全新的 getParent() 方法，这样就可以提供完整的参数起源链，以准确显示某一项参数的来源。
		* 比如在 application.properties 配置文件中使用 spring.config.import 来导入第二个配置文件的参数，从第二个配置文件加载的参数的 Origin 将具有一个指向原始导入声明的父级。
	
		* 通俗说，就是可以看到参数从哪里导进来的，可以通过 actuator/env 或者 actuator/configprops 端点来查看与之相关的输出信息
	

	
	# 总结
		spring.config.name							指定配置文件的名称
		spring.config.location						重新导入一个配置文件，或者重新指定配置文件的搜索路径
		spring.config.import						导入外部的配置文件，或者加载Kubernetes中的配置树
		spring.config.additional-location			添加配置文件的搜索路径
		spring.profiles.active						仅仅激活指定环境的配置文件
		spring.profiles.include						一次性包含多个环境配置文件
		spring.config.activate.on-profile			当前逻辑配置，只有在指定环境生效的时候，才会生效
		spring.config.activate.on-cloud-platform	当前逻辑配置，只有在指定云平台下才生效