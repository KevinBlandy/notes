--------------------
Nexus私服的安装		|
--------------------
	# 私服的概念就不必多说了,直接说安装
		* 团队开发,需要相互依赖
		* 团队开发,没有私服,集体请求中央仓库.有可能会被拉黑(同一IP请求)
		* 私服网络好
	
	# 下载
		wget --no-check-certificate https://sonatype-download.global.ssl.fastly.net/nexus/oss/nexus-2.14.1-01-bundle.tar.gz
			* 注意是HTTPS,需要添加参数:--no-check-certificate
			* 而且实际使用的时候,要注意版本
		
	# Nexus服务器,有两个版本
		1,war
			* WEB版本,发布到Tomcat,WEB服务器中
		2,Jetty
			* 自带一个Jetty服务
	
	# 目录结构
		nexus-2.12.0-01
			bin			//执行脚本
			conf		//配置文件
			lib			//运行依赖
			logs		//日志
			nexus		//Nexus管理程序(WEB项目)
			tmp
		
		sonatype-work
			
--------------------
Nexus-安装			|
--------------------
	* 前提是安装了 maven
	1,解压到任意目录
		nexus-2.12.0-01
			* 服务
		sonatype-work
			* 私有库目录

	2,配置文件(给你看看,不用编辑)
		/nexus-2.12.0-01/conf/nexus.properties
		
		# Jetty section				//jetty配置项
		application-port=8888
			* 访问端口(WEB)
		application-host=0.0.0.0
			* 允许所有IP访问
		nexus-webapp=${bundleBasedir}/nexus
			* nexus的目录
		nexus-webapp-context-path=/nexus
			* WEB访问的路径(URI)

		# Nexus section				//私服配置项
		nexus-work=${bundleBasedir}/../sonatype-work/nexus
			* 工作目录
		runtime=${bundleBasedir}/nexus/WEB-INF
			* 

	3,编辑脚本
		nexus-2.12.0-01/bin/nexus
	
		RUN_AS_USER=root
			* 修改运行用户为root
		
	
		
	4,启动Nexus
		nexus start

		jps
		 * 进程名:JswLauncher

	5,进入管理界面
		http://123.207.122.145:8888/nexus/
		* 默认的用户名和密码
			admin
			admin123
	
	6,修改密码
		* 看下面笔记

	7,代理库生效
		设置Proxy仓库允许远程下载
		* 选中 所有的 Proxy 类型 仓库
		* configuration 选项卡
		* Download Remote Indexes 值,设置为 true

	# 常用命令
		nexus start		//启动
		nexus stop		//关闭
		nexus restart	//重启
		nexus install	//安装到服务
		nexus unistall	//从服务卸载
	

--------------------
Nexus-仓库管理		 |
--------------------
	Views/Repository
		repositories	//仓库
			type:
				* group
					* 主仓库
					* 其实就是包含了下面的所有仓库
					* 用来方便开发人员进行设置的仓库

				* hosted	- 最常用	
					* 只要是hosted(宿主仓库)仓库,就是属于我们内部项目的发布仓库,一般有这么几个东西
					* 3rd party		//第三方依赖的仓库,这数据是要由内部人员自行下载后发布上去(一些无法从中央仓库下载的依赖)
					* Releases		//内部项目中 releases项目的发布仓库
					* Snapshots		//同上,属于 Snapshots 项目的发布仓库

				* proxy
					* Nexus获取外部信息的仓库,从远程中央仓库中寻找数据的仓库
					* Apache Snapshots	//Apache的快照版本仓库
					* Central			//Maven的中央仓库

				* virtual //针对于maven1的一个转接仓库,没啥卵用

	# 设置Proxy仓库允许远程下载
		* 选中 所有的 Proxy 类型 仓库
		* configuration 选项卡
		* Download Remote Indexes 值,设置为 true

	
	# 直接上传依赖
		* 选中指定的仓库
		* Artifact Upload选项卡
			GAV Definition  = GAV Parameters
				* 选择 GAV Parameters
			Group:
			Artifact:
			Version:
			Packaging:
				* 上面三个都不解释
			
			Select Artifact(s) to Upload
				* 选择依赖
			Add Artifact
				* 添加到上传队列
			Upload Artifact(s)
				* 开始上传
			
		
	# 更新远程仓库的索引
		* 选择指定仓库,右击:Repari Index
	


--------------------
Nexus-账户管理		 |
--------------------
	# 配置Admin安全邮箱,如果忘记密码可以找回
		Administration/Server

		Hostname
			* SMTP服务器地址
			* smtp.qq.com
		Port
			* SMTP服务端口
			* 25
		Username
			* 用户名
			* 747692844@qq.com
		Password
			* 密码
			* xxxxxxx
			* '如果是腾讯邮箱,则是SMTP的授权码'
		Connection
			* 默认就好
		System Email
			* 跟 Username 一样
			* 747692844@qq.com
	
	# 用户管理
		Security/Users

		* 里面默认有三个用户
		admin
			* 邮箱修改为自己的
		deployment
		anonymous
	
	# 修改admin密码

--------------------
Nexus-使用私服		 |
--------------------
	//setting.xml配置
	<?xml version="1.0" encoding="UTF-8"?>
	<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0" 
			  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
			  xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0 http://maven.apache.org/xsd/settings-1.0.0.xsd">

		<localRepository>E:/apache-maven-3.1.0/.m2/repository</localRepository>
		<interactiveMode>true</interactiveMode>
		<offline>false</offline>
		<pluginGroups>
			<pluginGroup>org.mortbay.jetty</pluginGroup>
			<pluginGroup>org.jenkins-ci.tools</pluginGroup>
		</pluginGroups>
		
		//以下开始
		<!--配置权限,使用默认用户-->
		<servers>
			<server>
				<id>nexus-releases</id>
				<username>deployment</username>
				<password>deployment123</password>
			</server>
			<server> 
				<id>nexus-snapshots</id>
				<username>deployment</username>
				<password>deployment123</password>
			</server>
		</servers>

		<mirrors>

		</mirrors>

		<profiles>
			<!-- 配置远程仓库 -->
			<profile>
			   <id>demo</id>
					<activation>
						<activeByDefault>false</activeByDefault>
						<jdk>1.6</jdk>
					</activation>
					<repositories>
						<!-- 私有库地址-->
						<repository>
							<id>nexus</id>
							<url>http://192.168.4.221:8081/nexus/content/groups/public/</url>
							<releases>
								<enabled>true</enabled>
							</releases>
							<snapshots>
								<enabled>true</enabled>
							</snapshots>
						</repository>
					</repositories>      
					<pluginRepositories>
						<!--插件库地址-->
						<pluginRepository>
							<id>nexus</id>
							<url>http://192.168.4.221:8081/nexus/content/groups/public/</url>
							<releases>
								<enabled>true</enabled>
							</releases>
							<snapshots>
								<enabled>true</enabled>
						   </snapshots>
						</pluginRepository>
					</pluginRepositories>
				</profile>
		</profiles>
		<!--激活profile-->
		<activeProfiles>
			<!-- 激活指定IP的profile -->
			<activeProfile>demo</activeProfile>
		</activeProfiles>
	</settings>
	

	//pom.xml配置,有这个配置才可以把项目部署到远程仓库
	<distributionManagement>
		<!-- Release 仓库 -->
		<repository>
			<id>nexus-releases</id>
			<name>Nexus Release Repository</name>
			<url>http://123.207.122.145:8888/nexus/content/repositories/releases/</url>
		</repository>
		<!-- snapshot 仓库 -->
		<snapshotRepository>
			<id>nexus-snapshots</id>
			<name>Nexus Snapshot Repository</name>
			<url>http://123.207.122.145:8888/nexus/content/repositories/snapshots/</url>
		</snapshotRepository>
	</distributionManagement>







	
	