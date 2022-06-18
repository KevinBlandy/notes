-------------------------
Lombok
-------------------------
	# Site
		https://projectlombok.org/
		https://github.com/projectlombok/lombok
	
	# Maven
		<dependency>
			<groupId>org.projectlombok</groupId>
			<artifactId>lombok</artifactId>
			<version>1.18.22</version>
			<scope>provided</scope>
		</dependency>
	
	# experimental 包下的都是实验性的
		

	# 常用
		@Builder
		@With
		@NoArgsConstructor
		@AllArgsConstructor
		@Data
		@Slf4j
	

--------------------------------------------------
Lombok 和 MapStruct同时使用
--------------------------------------------------
	# 可能会导致运行异常: 找不到符号
	# 解决办法：手动配置plugin (annotationProcessorPaths 配置顺序很重要)
		<plugin>
			<groupId>org.apache.maven.plugins</groupId>
			<artifactId>maven-compiler-plugin</artifactId>
			<configuration>
				<parameters>true</parameters>
				<annotationProcessorPaths>
					<path>
						<groupId>org.projectlombok</groupId>
						<artifactId>lombok</artifactId>
						<version>${lombok.version}</version>
					</path>
					<path>
						<groupId>org.mapstruct</groupId>
						<artifactId>mapstruct-processor</artifactId>
						<version>${mapstruct.version}</version>
					</path>
				</annotationProcessorPaths>
			</configuration>
		</plugin>
	


