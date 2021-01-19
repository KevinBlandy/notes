--------------------
Maven 插件
--------------------
	# 插件
		<!-- https://mvnrepository.com/artifact/org.xolstice.maven.plugins/protobuf-maven-plugin -->
		<dependency>
			<groupId>org.xolstice.maven.plugins</groupId>
			<artifactId>protobuf-maven-plugin</artifactId>
			<version>0.6.1</version>
		</dependency>
		
		* 插件的配置文档
			https://www.xolstice.org/protobuf-maven-plugin/compile-mojo.html
	
	# 基本配置
			<plugin>
				<groupId>org.xolstice.maven.plugins</groupId>
				<artifactId>protobuf-maven-plugin</artifactId>
				<version>0.6.1</version>
				<configuration>
					<!-- proto编译器（放到项目根目录） -->
					<protocExecutable>${project.basedir}/protoc-3.11.2-win64/bin/protoc.exe</protocExecutable>
					<!-- proto 文件所在的文件夹 （放到项目根目录）-->
					<protoSourceRoot>${project.basedir}/proto</protoSourceRoot>
					<!-- 生成类的文件夹 -->
					<outputDirectory>${project.build.sourceDirectory}</outputDirectory>
					<!-- 是否每次编译前都清空生成文件夹 -->
					<clearOutputDirectory>false</clearOutputDirectory>
					<!-- 临时文件夹 -->
					<temporaryProtoFileDirectory>${project.build.directory}/protoc-dependencies</temporaryProtoFileDirectory>
				</configuration>
				<executions>
					<execution>
						<goals>
							<goal>compile</goal>
							<goal>test-compile</goal>
						</goals>
					</execution>
				</executions>
			</plugin>


