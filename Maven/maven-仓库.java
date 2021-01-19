————————————————————————
1,maven仓库				|
————————————————————————
	* 当maven安装好后,默认的仓库位置是在:C:\Users\KevinBlandy\.m2\repository
	* 当maven项目执行的时候,就先去这个位置到有没有对应的jar包,
	* 如果有就使用,如果没有就去下载,而且保持在这个位置
	* 下载的地址是:中央仓库repository

————————————————————————
2,配置本地maven仓库		|
————————————————————————
	* 因为一些原因,我们是不能让'仓库'设置在C盘,我们应该合理的把这些JAR包放置到其他的位置
	* 在conf目录下有个settings.xml文件
	* 有个节点,是被注释掉了,打开它,配置一个目录
		* <localRepository>D:/maven/repository</localRepository>
	* 并且把settings.xml复制(修改后),粘贴到配置的目标文件夹的'父文件夹'中
		* 上述例子,就应是粘贴在:maven文件居中,那么jar包会自动的下载到repository文件夹中
	* 执行一下:mvc compile,下载必要的插件
	

	 <!-- 本地仓库 -->
	 <localRepository>D:/maven/repository</localRepository>
	 * 其实直接可以在maven目录的settings.xml中配置,全局的!对所有用户都有效
————————————————————————
3,mavon中央仓库			|
————————————————————————
	* 当本地仓库中找不到需要的jar包的时候,就会去中央仓库查找
	* 在maven文件中,一个jar包中其实就定义了中央仓库的地址
		C:\apache-maven-3.3.9\lib\maven-model-builder-x.x.x.jar\org\apache\maven\model\pom-4.0.0.xml
	* 该xml文件
		<repositories>
			<repository>
			  <id>central</id>
			  <name>Central Repository</name>
			  <!-- '中央仓库地址' -->
			  <url>https://repo.maven.apache.org/maven2</url>
			  <layout>default</layout>
			  <snapshots>
				<enabled>false</enabled>
			  </snapshots>
			</repository>
		</repositories>

	* 配置国内的镜像站点,因为中央仓库是在国外,网络会有些延迟
	* 国内有一些镜像站点可以使用
	* 例:oschina(开源中国提供)
		<mirror>
			<id>nexus-osc</id>
			<mirrorOf>*</mirrorOf>
			<name>Nexus osc</name>
			<url>http://maven.oschina.net/content/groups/public/</url>
		</mirror>

		* 配置在settings.xml文件中的<mirrors>标签节点中
	* mirrorOf:这个配置中的'*',表示:只要你使用到依赖,全部都去这个仓库找

	* 在pom.xml中配置的
	<repositories>
		<repository>
			<id>github-releases</id>
			<url>http://oss.sonatype.org/content/repositories/github-releases/</url>
		</repository>
		<repository>
			<id>maven-restlet</id>
			<name>Public online Restlet repository</name>
			<url>http://maven.restlet.org</url>
		</repository>
		<repository>
			<id>ibiblio.org</id>
			<name>ibiblio Mirror of http://repo1.maven.org/maven2/</name>
			<url>http://mirrors.ibiblio.org/pub/mirrors/maven2</url>
		</repository>
		<repository>
			<id>jboss-public-repository-group</id>
			<name>JBoss Public Repository Group</name>
			<url>http://repository.jboss.org/nexus/content/groups/public</url>
		</repository>
	</repositories>


--------------------------
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

实际开发中是要配置出一个私有仓库(自己的仓库)
	可以使用:Sonatype Nexus



可以在pom.xml(父)文件中配置镜像仓库的地址



当你需要把本地的jar发布到私服上还需要配置
	<server>
		<id>...
		<username>...
		<password>...
	</server>