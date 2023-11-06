
# 华为云
	<mirror>
		<id>huaweicloud</id>
		<mirrorOf>*</mirrorOf>
		<url>https://mirrors.huaweicloud.com/repository/maven/</url>
	</mirror>

# 直接在项目中设置

	<repositories>
		<repository>
			<id>huaweicloud</id>
			<url>https://mirrors.huaweicloud.com/repository/maven/</url>
			<releases>
				<enabled>true</enabled>
			</releases>
			<snapshots>
				<enabled>true</enabled>
			</snapshots>
		</repository>
	</repositories>