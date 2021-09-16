----------------------
mapstruct
----------------------
	# 地址
		https://mapstruct.org/
		https://github.com/mapstruct/mapstruct
		https://mapstruct.org/documentation/stable/reference/html/
	

	# 依赖
		...
		<properties>
			<org.mapstruct.version>1.4.2.Final</org.mapstruct.version>
		</properties>
		...
		<dependencies>
			<dependency>
				<groupId>org.mapstruct</groupId>
				<artifactId>mapstruct</artifactId>
				<version>${org.mapstruct.version}</version>
			</dependency>
		</dependencies>
		...
		<build>
			<plugins>
				<plugin>
					<groupId>org.apache.maven.plugins</groupId>
					<artifactId>maven-compiler-plugin</artifactId>
					<version>3.8.1</version>
					<configuration>
						<parameters>true</parameters>
						<source>1.8</source> 
						<target>1.8</target>
						<annotationProcessorPaths>
							<path>
								<groupId>org.mapstruct</groupId>
								<artifactId>mapstruct-processor</artifactId>
								<version>${org.mapstruct.version}</version>
							</path>
							<!-- other annotation processors -->
						</annotationProcessorPaths>
					</configuration>
				</plugin>
			</plugins>
		</build>
	

	# 编译配置项
		mapstruct.suppressGeneratorTimestamp=false
			* 如果设置为true, @Generated 则禁止在生成的映射器类的注释中创建时间戳。
		
		mapstruct.verbose=false
			* 如果设置为true，则 MapStruct 在其中记录其主要决策。注意，在maven写的时候，还showWarnings需要加上maven-compiler-plugin的配置有问题。
		
		mapstruct.suppressGeneratorVersionInfoComment=false
		mapstruct.defaultComponentMode=default
		mapstruct.defaultInjectionStrategy=field
		mapstruct.unmappedTargetPolicy=WARN



		* 通过maven插件来配置
			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-compiler-plugin</artifactId>
				<version>3.5.1</version>
				<configuration>
					<source>1.8</source>
					<target>1.8</target>
					<annotationProcessorPaths>
						<path>
							<groupId>org.mapstruct</groupId>
							<artifactId>mapstruct-processor</artifactId>
							<version>${org.mapstruct.version}</version>
						</path>
					</annotationProcessorPaths>
					<!-- due to problem in maven-compiler-plugin, for verbose mode add showWarnings -->
					<showWarnings>true</showWarnings>
					<compilerArgs>
						<arg>
							-Amapstruct.suppressGeneratorTimestamp=true
						</arg>
						<arg>
							-Amapstruct.suppressGeneratorVersionInfoComment=true
						</arg>
						<arg>
							-Amapstruct.verbose=true
						</arg>
					</compilerArgs>
				</configuration>
			</plugin>
					