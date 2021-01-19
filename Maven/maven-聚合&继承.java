————————————————————————
1,maven聚合				|
————————————————————————
	* 在根目录创建pm.xml配置文件
	* 导入三个模块
	<packaging>POM</packaging>
	<modules>
		<modul>../user-core</modul>
		<modul>../user-log</modul>
		<modul>../user-service</modul>
	<modules>
	* 依赖啥的都不要
	* 只留下GAV
	* 打包方式改为POM.不是jar
	* 这东西就是可以在一个地方编译多个

------------------------
2,继承					|
------------------------
	* 也是啥都不要
	* 仅仅配置GAV
	* packing为pom
	* 配置一些属性,配置工厂
	* 在父模块中中添加依赖
		<dependenciesManagement>
			<dependencies>
				<dependencies>
					...
				</dependencies>
			</dependencies>
		</dependenciesManagement>
		* 如果不是这种方式的话,那么子类会依赖所有父类的依赖
		* 这种方式,子类可以去自己'挑选'

	在子模块中
		<parent>
			<groupId></groupId>
			<arifactId></aritfactId>
			<version></version>
			<!-- 父POM的路径 -->
			<relativePath>../user-parent/pom.xml</relativePath>
		</parent>
	*　子模块中就仅仅需要写出atifactId,vetsion
	*　子模块中使用父POM的依赖
			仅仅只需要声明groupId和artifactId就好
	

	*　父中导入项目需要的所有jar,子类在配置文件中筛选就好！

	

* 继承和聚合,可以放一起！超级整合
* 既是做聚合的,也是做继承的


------------------------
3,多继承				|
------------------------
	<dependencyManagement>
		<dependencies>
			<!-- 此处继承了a 和 b 两个项目，type为pom，scope 为 import -->
			<dependency>
				<groupId>com.cbm.stu</groupId>
				<artifactId>maven-parent-a</artifactId>
				<version>1.0.0</version>
				<type>pom</type>
				<scope>import</scope>
			</dependency>
			<dependency>
				<groupId>com.cbm.stu</groupId>
				<artifactId>maven-parent-b</artifactId>
				<version>1.0.0</version>
				<type>pom</type>
				<scope>import</scope>
			</dependency>
		</dependencies>
	</dependencyManagement>