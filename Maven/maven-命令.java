	
————————————————————————
1,maven命令				|
————————————————————————	
	mvn archetype:generate		//创建 Maven 项目
	mvn archetype:generate -DarchetypeCatalog=internal
								//不要联网下载模版,海外网络你懂的！
	mvn compile					//编译源代码到target文件中
	mvn test-compile			//编译测试代码
	mvn test					//运行应用程序中的单元测试
	mvn site					//生成项目相关信息的网站
	mvn clean					//清除目标目录中的生成结果
		* 删除maven生成的目标文件
	mvn package					//依据项目配置打包文件
	mvn install					//在本地 Repository 中安装 jar
	mvn deploy					//将jar包发布到远程仓库
		* 发布到私服
	mvn eclipse:eclipse			//生成 Eclipse 项目文件
	mvn dependency:list			//查看当前项目已经解析的依赖
	mvn dependency:tree
————————————————————————
1,maven-创建工程		|
————————————————————————
	1,mvn archetype:generate
		* 开始下载依赖的jar包
	2,选择 archetype 所使用的版本
		* 一般选择最高的版本:6
	3,输入groupId
	4,输入artifactId
	5,输入版本
		* 0.0.1-SNAPSHOT
	6,创建包,可以无视直接就使用本路径
	7,是否要建立?输入y,回车.就会创建一个maven结构的Java工程
		* 并且会创建好pom.xml文件
	8,直接一个命名搞定上面所有
		* mvn archetype:generate -DarchetypeCatalog=internal -DgroupId=com.ans -DartifactId=ans-user -Dversion=0.0.1-SNAPSHOT



* 不要在网上下载模板,要死人

mvn archetype:generate -DarchetypeCatalog=internal -DgroupId=com.ans -DartifactId=ans-user -Dversion=1.0-SNAPSHOT