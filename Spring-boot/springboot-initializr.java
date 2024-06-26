--------------------------------------
SpringBoot Initializr
--------------------------------------
	# 地址
		https://start.spring.io/
		https://github.com/spring-io/start.spring.io
	
	# 自己安装
		1. cloen代码：git clone https://github.com/spring-io/start.spring.io
		2. 打包编译：cd start.spring.io ，执行./mvnw clean package -Dmaven.test.skip=true
		3. target 文件下两个jar 用*-exec.jar 可以直接java -jar -Dserver.port=8080 target\start-site-exec.jar 运行

		* 需要安装nodejs和node-gyp
			* 安装nodejs
				yum -y install nodejs

				npm install -g n
			
			* 升级到指定的最新版本(node版本过低可能会构建失败)
				n 16.13.0 stable
				n 14.18.1 stable

			
			* 安装这玩意儿
				npm install -g node-gyp

			* 安装 yarn
				npm install -g yarn
			
			* 你妈的可能还要python3的环境
		
		* 手动安装nodejs
			1. 下载最新的版本
				https://nodejs.org/dist/latest/
				https://nodejs.org/dist/latest/node-v20.3.0-linux-x64.tar.gz
			
			2. 解压文件
				tar xvf node-v20.3.0-linux-x64.tar.gz
			
			3. 创建软链接
				ln -s ./node-v20.3.0-linux-x64/bin/node /usr/local/bin/node
				ln -s ./root/node-v20.3.0-linux-x64/bin/npm /usr/local/bin/npm
			
			4. 检查是否成功
				node -v
				npm -v
			

	# Nginx 反向代理配置

		#PROXY-START/
		#location  ~* \.(gif|png|jpg|css|js|woff|woff2)$
		#{
		#    proxy_pass https://start.spring.io;
		#    proxy_set_header Host $host;
		#    proxy_set_header X-Real-IP $remote_addr;
		#    proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
		#    proxy_set_header REMOTE-HOST $remote_addr;
		#    expires 12h;
		#}
		location /
		{
			proxy_pass https://start.spring.io/;
			# proxy_pass http://localhost:8080/;
			#proxy_set_header Host $host;
			#proxy_set_header X-Real-IP $remote_addr;
			#proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
			#proxy_set_header REMOTE-HOST $remote_addr;
			
			#add_header X-Cache $upstream_cache_status;
			
			#Set Nginx Cache
			
				#add_header Cache-Control no-cache;
		}

		#PROXY-END/
	
	# 默认代理
		
		#PROXY-START/

		location ^~ /
		{
			proxy_pass http://localhost:8080;
			proxy_set_header Host $host;
			proxy_set_header X-Real-IP $remote_addr;
			proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
			proxy_set_header REMOTE-HOST $remote_addr;
			proxy_set_header Upgrade $http_upgrade;
			proxy_set_header Connection $connection_upgrade;
			# proxy_hide_header Upgrade;

			# add_header X-Cache $upstream_cache_status;

			#Set Nginx Cache
			
			
			#set $static_fileZHbkGDhd 0;
			#if ( $uri ~* "\.(gif|png|jpg|css|js|woff|woff2)$" )
			#{
			#	set $static_fileZHbkGDhd 1;
			#	expires 12h;
			#	}
			#if ( $static_fileZHbkGDhd = 0 )
			#{
			#add_header Cache-Control no-cache;
			#}
		}

		#PROXY-END/

--------------------------------------
SpringBoot Initializr 社区构建
--------------------------------------

1. 配置类
	package io.spring.start.site.configuration;

	import java.io.IOException;
	import java.nio.file.FileVisitResult;
	import java.nio.file.Files;
	import java.nio.file.Path;
	import java.nio.file.Paths;
	import java.nio.file.SimpleFileVisitor;
	import java.nio.file.StandardOpenOption;
	import java.nio.file.attribute.BasicFileAttributes;
	import java.util.List;

	import org.springframework.context.annotation.Bean;

	import io.spring.initializr.generator.project.contributor.ProjectContributor;

	public class ProjectContributorConfiguration {
		@Bean
		public ProjectContributor contributor() {
			return (path) -> {
				/**
				 *
				 * Maven
				 *
				 */
				Path pom = path.resolve("pom.xml");
				if (Files.exists(pom)) {
					List<String> lines = Files.readAllLines(pom);
					if (lines.size() > 11) {
						lines.add(10, "");
						lines.add(11, "  <!-- Generated by https://start.springboot.io -->");
						lines.add(12, "  <!-- 优质的 spring/boot/data/security/cloud 框架中文文档尽在 => https://springdoc.cn -->");
						lines.add(13, "");
					}
					Files.write(pom, lines, StandardOpenOption.TRUNCATE_EXISTING);
				}

				/**
				 * Gradle
				 */
				Path gradle = path.resolve("build.gradle"); // Gradle - Groovy
				if (!Files.exists(gradle)) {
					// Gradle - Kotlin
					gradle = path.resolve("build.gradle.kts");
				}
				if (Files.exists(gradle)) {
					List<String> lines = Files.readAllLines(gradle);
					if (lines.size() > 1) {
						lines.add(0, "");
						lines.add(1, "// Generated by https://start.springboot.io");
						lines.add(2, "// 优质的 spring/boot/data/security/cloud 框架中文文档尽在 => https://springdoc.cn");
						lines.add(3, "");
					}
					Files.write(gradle, lines, StandardOpenOption.TRUNCATE_EXISTING);
				}

				/**
				 * 源码文件
				 */
				Path pacakge = path.resolve(Paths.get("src", "main"));
				if (Files.exists(pacakge)) {
					Files.walkFileTree(pacakge, new SimpleFileVisitor<Path>() {
						@Override
						public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
							if (Files.isDirectory(file)) {
								return FileVisitResult.CONTINUE;
							}
							String fileName = file.getFileName().toString();
							if (fileName.endsWith("java") || fileName.endsWith("kt") || fileName.endsWith("groovy")) {
								List<String> lines = Files.readAllLines(file);
								if (lines.size() > 6) {
									lines.add(5, "");
									lines.add(6, "// Generated by https://start.springboot.io");
									lines.add(7, "// 优质的 spring/boot/data/security/cloud 框架中文文档尽在 => https://springdoc.cn");
									lines.add(8, "");
								}
								Files.write(file, lines, StandardOpenOption.TRUNCATE_EXISTING);
							}
							return FileVisitResult.CONTINUE;
						}
					});
				}
			};
		}
	}

2. main函数修改，新增配置类
	@Import(value = {ProjectDescriptionCustomizerConfiguration.class, ProjectContributorConfiguration.class})

3. client项目，index.html 静态资源修改

	index.html 新增keywords，description，百度统计，自定义脚本

	<meta name="description" content="快速构建你的 spring 应用。" />
	<meta name="keywords" content="Spring Initializr" />

	<script src="https://guohuihui.gitee.io/guohui-blog/spring/spring.js"></script>

	<script type="text/javascript">
		var _hmt = _hmt || [];
		(function() {
		 var hm = document.createElement("script");
		 hm.src = "https://hm.baidu.com/hm.js?01a8b83d4f38d7c890e8dbcaa8e661d3";
		 var s = document.getElementsByTagName("script")[0]; 
		 s.parentNode.insertBefore(hm, s);
		})();
	</script>

4. client项目，banner广告语修改

	start-client\src\components\common\layout\Header.js

	
	// 在 h1 标签后新增
	<div className='banner'>
	 <a href='https://springdoc.cn' target="_blank">优质的 spring/boot/security/data/cloud 中文文档尽在 => springdoc.cn</a>
	</div>


5. application.yml 配置修改，设置端口为：8088

	server:
	  port: 8088
	 
6. 注释代码格式化，校验插件，根项目和site项目
	
	maven-checkstyle-plugin 插件，整个删除就行


7. /start-client/src/styles/_main.scss
		#header {
		  padding-top: 40px;	// 新增此行
		  h1,
		
	  // 末尾新增如下样式
	 
	 .banner {
		  position: absolute;
		  top: 0;
		  right: 5rem;
		  left: 5rem;
		  a {
			display: block;;
			background-color: #6db33f;
			color:  #FFF;
			font-weight: normal;
			display: block;
			font-size: 0.9rem;
			height: 40px;
			line-height: 40px;
			text-align: center;
			text-decoration: none;
			cursor: pointer;
		  }
		}

> 修改文件，共8个


-----------------
自定义依赖
-----------------

# 参考配置类：io.spring.initializr.metadata.Dependency
# compatibilityRange:
# 3.1.0-M1
# [2.6.0,3.2.0-M1)
# [2.7.0-M1,3.0.0-M1)
# '[' 和 ']' 表示包含，') 表示不包含
# 参考配置类：io.spring.initializr.metadata.Dependency

    - name: "常用开源"
      content:
        # Knife4j
        - name: Knife4j
          id: knife4j
          compatibilityRange: "[2.0.0,3.2.0-SNAPSHOT]"
          mappings:
            - compatibilityRange: "[2.0.0,3.0.0-M1)"
              groupId: com.github.xiaoymin
              artifactId: knife4j-openapi3-spring-boot-starter
              version: 4.3.0
            - compatibilityRange: "[3.0.0-M1,3.2.0-SNAPSHOT]"
              groupId: com.github.xiaoymin
              artifactId: knife4j-openapi3-jakarta-spring-boot-starter
              version: 4.3.0
          description: "Knife4j 是一个集 Swagger2 和 OpenAPI3 为一体的增强解决方案。"
          links:
            - rel: guide
              href: https://doc.xiaominfo.com/docs/quick-start
        # MyBatis-Plus
        - name: MyBatis-Plus
          id: mybatis-plus
          # compatibilityRange: "[2.0.0,3.2.0-M1]"
          groupId: com.baomidou
          artifactId: mybatis-plus-boot-starter
          version: 3.5.3.2
          description: "🚀为简化开发而生。"
          links:
            - rel: guide
              href: https://baomidou.com/
