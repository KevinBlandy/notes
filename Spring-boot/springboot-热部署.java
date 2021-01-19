----------------------------
Spring boot 热部署			|
----------------------------
	# 热部署的意思不在赘述
	# spring boot 热部署需要插件来完成
	# 添加插件
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
				<dependencies>
					<dependency>
						<groupId>org.springframework</groupId>
						<artifactId>springloaded</artifactId>
						<version>1.2.6.RELEASE</version>
					</dependency>
				</dependencies>
				<executions>
					<execution>
						<goals>
							<goal>repackage</goal>
						</goals>
						<configuration>
							<classifier>exec</classifier>
						</configuration>
					</execution>
				</executions>
			</plugin>
		</plugins>
	
	# 如果在控制台看到这条日志,就标识OK
		[INFO] Attaching agents: [**/1.2.4.RELEASE/springloaded-1.2.4.RELEASE.jar]

----------------------------
Spring boot 运行方法一		|
----------------------------
	# 使用 maven 插件来启动项目
		spring-boot:run

----------------------------
Spring boot 运行方法二		|
----------------------------
	# 直接运行 main 的话,需要一些处理
		1,下载 spring-loader.x.x.x.jar 添加到 lib 目标
		2,运行时候添加VM参数
			-javaagent:.\lib\springloader.x.x.x.jar -noverfy


----------------------------
Spring boot 注意			|
----------------------------

	# 如果你用的IDE是Eclipse，那以上配置就没问题了
	# 如果你用的是IntelliJ IDEA，这里你需要注意一个问题：
		* IntelliJ IDEA 是不会自动编译project的，所以，你会发现，你修改了java code，也Ctril+S了，但是Spring-Loaded就是没生效。
		* 快捷键触发：   Ctrl+Shift+F9(也可以去修改快捷键,例如:每次 ctrl+s保存的同时也执行编译)
	
----------------------------
Spring boot-devtools		|
----------------------------
	# spring-boot-devtools
	# 一个为开发者提供服务的模块,最重要的功能就是热部署,自动更新代码到应用.
	# 原理就是发现代码更改后.重新启动应用但是速度极快.
	# 深层次的原理就是,使用了俩 ClassLoader,一个加载不会改变的类(第三方jar),一个加载也许会改变的类,称为 restat ClassLoader
	# 在代码被更改的时候,原来的 restart ClassLoader 重新创建一个 restart ClassLoader,由于加载的类比较少,所有实现了较快的重启
	# 步骤

		1,添加依赖
			
			<dependency>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-devtools</artifactId>
				<optional>true</optional>
				<scope>test</scope>
			</dependency>
		
		2,添加插件
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
				<configuration>
					<!-- 如果该值不为 true,则热部署不会生效 -->
					<fork>true</fork>
				</configuration>
			</plugin>

	
	# 效果
		修改类 ->		保存会重启
		修改配置文件->	保存会重启
		修改页面->		保存会重启(跟视图层技术有关)