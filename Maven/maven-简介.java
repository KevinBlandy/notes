————————————————————————
1,maven简介				|
————————————————————————
	* maven是一个项目管理工具,包含了一个项目对象模型(POM)
	* 一组标准集合,一个项目生命周期,一个依赖管理系统
	* 和用来运行应以在生命周期阶段中插件目标的逻辑
	* maven遵循约定优于配置的原则
	* 所谓约定优于配置:
		maven,虽然支持配置方式,但是有约定好的默认值.比如约定好的目录结果等

————————————————————————
2,maven安装				|
————————————————————————
	1,安装JDK(这东西会使用到JAVA_HOME)
	2,下载地址:http://maven.apache.org.download.html
	3,对apache-maven-x.x.x-bin.zip进行解压,把解压后的文件存放到任意目录
	4,设置系统环境变量:MAVEN_HOME
		* 指向apache-maven-x.x.x地址
		* C:\apache-maven-3.3.9
	5,设置path
		* 在path后面添加:%MAVEN_HOME%\bin
	6,检验安装是否成功
		* 打开CMD,输入:mvn -v
		* 如果有输出版本号,那么表示安装且配置成功
	7,IDE添加Maven插件
		* MyEclipse
	
————————————————————————
3,maven-Hello,World		|
————————————————————————
	1,在某个盘符创建一个空白文件夹
	2,在文件夹中创建pom.xml(可以去其他项目偷),并且正确的进行配置
	3,在pom.xml所在文件夹中创建子文件目录
		* src\main\java
	4,在java目录中创建java代码文件(如果是带pacakge的java文件,还需要在java文件中创建程序package目录)
	5,在pom.xml所在文件夹中打开cmd输入:mvn compile
	6,运行完毕后会在pom.xml所在文件夹中创建一个文件夹:target
	7,该文件夹有有一个classes目录
	8,classes中就是java程序的package结构,以及已经编译过的class文件.
	9,在整个期间,maven会自动的去下载所依赖的jar包

————————————————————————
4,maven目录结构			|
————————————————————————
	bin
		* 要运行的命令
		* mvn.bat/mvnDebug.bat
	boot
		* plexus-classworlds-x-x.jar
		* maven自己开发的一个类加载器
	conf
		* setting.xml
		* 对外提供的一个最核心的配置文件,如果在这里改变配置,那么就会对所有系统用户都生效
		* 通常,不配置这个,一般只配置当前用户
	lib
		* 运行所需要的jar包
	
	* 设置配置文件,仅对当前用户生效
		1,在C盘:Administrator目录下创建:.m2文件夹,并且把setting.xml文件复制进去
		2,修改此目录中的配置文件,那么就仅仅对当前Administrator用户生效

————————————————————————
5,maven-Eclipse开发		|
————————————————————————
	* 安装Eclipse插件
		* 插件名称叫做:m2eclipse
		* www.sonatype.org/m2eclipse/
		* 其实很多Eclipse是已经带有了Maven插件的
		* 至于这么装,就不多说了
	* 配置Eclipse中的Maven
		1,取消集成的Maven,使用自己的Maven
		windows-->preferences-->maven-->Installation-->Add
		* 要记得把原来的继承的maven前面的勾去掉,然后在自己的maven前面打勾
		2,配置UserSetting
		windows-->preferences-->maven-->User Setting-->User Settings-->Browes
		* 选择自己配置的仓库地址的settings文件
		* 默认的也是找C:\Users\KevinBlandy\.m2\repository
	* 创建Maven项目
		1,新建maven项目,直接下一步
		2,配置Archetype,选择
			* org.apache.wicket   wicket-archetype-quickstart
		3,配置
			groupId			//项目名称:com.zhiku
			ArifactId		//模块名称:user
			Version			//自己选择版本号
			URL				//自动生成
		4,直接完成


	* 在eclipse的安装目录下找到ini配置文件,添加
		-vm
		C:\Program Files\Java\jdk1.8.0_77\bin\javaw.exe

————————————————————————
6,maven-项目结构		|
————————————————————————
	src/main/java			//源码
	src/main/resources		//源码配置文件(spring,hibernate,struts2,mybatis,springmvc,c3p0,lo4j... ...)
	src/test/java			//测试代码
	src/test/resources		//测试配置文件(spring,hibernate,struts2,mybatis,springmvc,c3p0,lo4j... ...)
	src
	target
	pom.xml


————————————————————————
7,maven-坐标			|
————————————————————————
	* 坐标 & 构件
	* 
	<dependency>
		<groupId>com.kevin</groupId>			//com.项目名
		<artifactId>kevin-demo</artifactId>		//项目名-模块名
		<version>0.0.1SNAPSHOT</version>		//版本
	</dependency>

————————————————————————
7,maven-仓库			|
————————————————————————
	在 maven目录下的lib目录下的:maven-model-builder-3.3.9.jar\org\apache\maven\model下有一个pom文件
	pom-4.0.0.xml
		* maven项目中的所有pom,都会继承它
		<repositories>
			<repository>
			<id>central</id>
			<name>Central Repository</name>					
			<url>https://repo.maven.apache.org/maven2</url>		//默认的仓库地址
			<layout>default</layout>							//使用默认的布局
			<snapshots>
			<enabled>false</enabled>							//禁止下载为快照的版本控件
			</snapshots>
			</repository>
		</repositories>
	
	* 配置镜像仓库
		* maven中央仓库,在国外.你懂的!
		* 修改在conf中的setting文件
		<mirrors>
			<mirror>
				<id>nexus-osc</id>			//该镜像仓库的id
				<mirrorOf>*</mirrorOf>		//为哪个仓库配置镜像,可以直接指定仓库名称,或者直接使用通配符:*
				<name>Nexus osc</name>
				<url>http://maven.oschina.net/content/groups/public/</url>
			</mirror>
		</mirrors>
	* 仓库的还可以在pom.xml中进行配置
————————————————————————
7,maven-生命周期		|
————————————————————————
	* 相关的生命周期插件
	* 这些生命周期是相互独立的
		clean
			* 清理项目
			1,pre-clean		//执行清理前的工作
			2,clean			//清理上一次构建生成的所有文件
			3,post-clean	//执行清理后的文件
		default
			* 构建项目
			1,compil
			2,text
			3,package
			4,install
		site
			* 根据POM信息,生成项目站点
			1,pre-site		//在生成项目站点前要完成的工作
			2,site			//生成项目的站点文档
			3,post-site		//在生成项目站点后要完成的工作
			3,site-deploy	//发布生成的站点到服务器上

		
	clean
	compile
	test
	package		//当执行package的时候,test和compile会自动的执行
	instal


————————————————————————
7,maven-依赖的范围		|
————————————————————————
	<dependency>
			<groupId></groupId>
			<artifactId></artifactId>
			<version></version>
			<type></type>
			<scope>
				
			</scope>
	</dependency>
	* spoce,控制依赖存在什么时候
	* maven在不同的时候会有不同的classpath
	* scope,就确定了哪些依赖存在于哪些classpath
	1,compile[默认]
		* 编译,测试,运行
	2,provided
		* 编译,测试
		* ServletAPI
	3,runtime
		* 测试,运行
		* JDBC
	4,test
		* 测试
		* Junit
	5,system
		* 编译,测试
		* 跟本地相关
	6,import
		* 导入的范围,它仅使用在dependencyManagerment中,表示,从其他的pom中导入dependecy的配置

————————————————————————
7,maven-依赖的传递		|
————————————————————————
	* 山鸡依赖陈浩南,陈浩南依赖B哥
	* 山鸡对B哥就是传递性依赖
	* 排除依赖
		* 山鸡只听南哥的,不听B哥的,于是就可以在配置中去除对B哥的依赖
	<dependency>
	 <exclusions>
	  	<exclusion>
	  		<groupId>com.kevin.maven</groupId>
			<artifactId>maven-one</artifactId>
	  	</exclusion>
	  </exclusions>
    </dependency>

————————————————————————
7,maven-依赖的冲突		|
————————————————————————
	* 短路优先
		A->B->C->X(jar)

		A->D->X(jar)
		*　Ａ会优先解析这个X
	* 路径相同
		先声明,先优先

———————————————————————
7,maven-聚合和继承		|
————————————————————————
	* 把依赖放入这里<dependencyManagement>
	* 设置:<packaging>pom</packaging>
		<dependencyManagement>
		<dependencies>
			<dependency>
			  <groupId>junit</groupId>
			  <artifactId>junit</artifactId>
			  <version>3.8.1</version>
			  <scope>test</scope>
			</dependency>
			</dependencies>
	   </dependencyManagement>
	   * 使用这个标签对依赖进行管理的话,这个项目并不会在项目中运行
	   * <properties>
			<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
			<junit.version>3.8.1</junit.version>
		  </properties>
		  * 定义一些属性,可以在该页面的下面一些节点使用
		  * ${junit.version},有点像EL表达式

	* 子项目继承
	<parent>
		<groupId>com.kevin.maven</groupId>
		<artifactId>maven-parant</artifactId>
		<version>0.0.1-SNAPSHOT</version>
	</parent>
		* 找到父项目的坐标,写入parent标签


———————————————————————
7,maven-创建WEB项目		|
————————————————————————