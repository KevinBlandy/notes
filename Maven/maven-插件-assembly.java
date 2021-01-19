--------------------
打包可执行jar		|
--------------------
	* 这种方式会把依赖的类。。。复制出来,复制到jar里面复制和自己的类一起
	* 打包后,会在目录下生成：xxxx-1.0.0-SNAPSHOT-jar-with-dependencies.jar 文件

	<plugin>
		<groupId>org.apache.maven.plugins</groupId>
		<artifactId>maven-assembly-plugin</artifactId>
		<version>3.1.0</version>
		<configuration>
			<descriptorRefs>
				<descriptorRef>jar-with-dependencies</descriptorRef>
			</descriptorRefs>
			<archive>
				<manifest>
					<!--  main函数 -->
					<mainClass>io.javaweb.excute.Main</mainClass>
				</manifest>
			</archive>
		</configuration>
		<executions>
			<execution>
				<id>make-assembly</id>
				<phase>package</phase>
				<goals>
					<goal>single</goal>
				</goals>
			</execution>
		</executions>
	</plugin>