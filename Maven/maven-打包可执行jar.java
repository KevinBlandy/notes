<build>
	<plugins>
		<!-- jar打包插件 -->
		<plugin>
			<groupId>org.apache.maven.plugins</groupId>
			<artifactId>maven-jar-plugin</artifactId>
			<version>3.1.1</version>
			<configuration>
				<classesDirectory>target/classes/</classesDirectory>
				<archive>
					<manifest>
						<!-- main函数类 -->
						<mainClass>io.javaweb.cas.Main</mainClass>
						<!-- MANIFEST.MF文件不记录的时间戳版本 -->
						<useUniqueVersions>false</useUniqueVersions>
						<!-- 添加到classpath -->
						<addClasspath>true</addClasspath>
						<!-- classpath 目录 -->
						<classpathPrefix>lib/</classpathPrefix>
					</manifest>
					<!-- 增加当前目录 -->
					<manifestEntries>
						<Class-Path>.</Class-Path>
					</manifestEntries>
				</archive>
			</configuration>
		</plugin>

		<!-- 依赖处理插件 -->
		<plugin>
			<groupId>org.apache.maven.plugins</groupId>
			<artifactId>maven-dependency-plugin</artifactId>
			<version>3.1.1</version>
			<executions>
				<execution>
					<id>copy-dependencies</id>
					<phase>package</phase>
					<goals>
						<!-- 复制依赖 -->
						<goal>copy-dependencies</goal>
					</goals>
					<configuration>
						<!-- 打包jar文件 -->
						<type>jar</type>
						<includeTypes>jar</includeTypes>
						<!-- 复制到目录 -->
						<outputDirectory>
							${project.build.directory}/lib
						</outputDirectory>
					</configuration>
				</execution>
			</executions>
		</plugin>
	</plugins>
</build>