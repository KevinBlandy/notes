---------------------
maven-创建web项目	 |
---------------------
	1,Eclipse创建Maven项目
	2,Archtype选择webapp
	3,解决无法创建maven目录结构的问题
		* 右击项目 --> buildpass --> Source --> remove掉所有的源文件夹
		* 手动创建OK
	4,修改web.xml(web-app添加命名空间约束)
	5,设置classes文件的输出路径
		* 右击项目 --> buildpass --> Source --> output folder...
		* 确保main路径下的classes目录是在:target/classes下
		* 确保test路径下的classes目录是在:target/test-classes下
	6,修改web项目的配置
		* 右击项目 --> peoperties --> Project Facets
		* Dynamic Web Module 打勾
	7,修改部署时的默认配置
		* 右击项目 --> peoperties --> Deployment Assembly
		* remove掉:/src/test/java
		* remove掉:/stc/test/resources


	
	8,添加tomcat的插件
		* 在build标签下添加
		<plugins>
			<plugin>
	          <groupId>org.apache.tomcat.maven</groupId>
	          <artifactId>tomcat7-maven-plugin</artifactId>
	          <version>2.2</version>
			  <configuraion>
				<!-- 端口 -->
				<port>80</port>
				<!-- 路径 -->
				<path>/<path>>
			  </configuraion>
	        </plugin>
        </plugins>
	

	
	* 配置Eclipse中maven下载jar包的时候带源码
		* windows --> preferences --> Maven
		* 右边Download Artifact Sources
		* 打勾,Maven会帮你下载源代码





	9,SVN导入本地后的MAVEN
		* 一般SVN仅仅上传MAVEN的结构和POM
		1,转换为MAVEN项目
		2,更改JAVA版本,以及设置字符集为UTF-8
		3,转换为WEB项目
			* 右击项目 --> peoperties --> Project Facets
			* Dynamic Web Module 打勾(注意选择版本的问题)
			* JAVA打上勾
		4,此时会报错,因为xml挂了!而且会多出一个webContent目录
			* 直接干掉webContent目录
			* 右击项目 --> peoperties --> Deployment Assembly
			* remove掉:/src/test/java
			* remove掉:/stc/test/resources
			* add --> Folder --> src/main/webapp
			* add --> Java Build Path Entries --> Maven Depend....
		5,OK,此时POM如果还有报错
			web.xml is missing and <failOnMissingWebXml> is set to true
			* 尝试添加一个新的依赖,或者删除一个依赖再添加就OK了

	
	10,修改动态WEB版本
		在Navigator下打开项目.settings目录下org.eclipse.wst.common.component.org.eclipse.wst.common.project.facet.core.xml，
		把  <installed facet="jst.web" version="3.0"/>改成  <installed facet="jst.web" version="2.5"/>

	
---------------------
maven-解决Maven问题	 |
---------------------

	如果说创建Maven工程失败,尝试添加一个catlog,名称随意
	http://repo1.maven.org/maven2/archetype-catalog.xml

	# 无法建立项目之类的
		* 因为网络问题,导致依赖或者插件没有成功下载.会在本地生成.lastUpdated文件,需要删除
		* 进入本地maven仓库,执行cmd命令
			for /r %i in (*.lastUpdated)do del %i
		* 直接命令删除
		* 然后在项目上执行maven update
