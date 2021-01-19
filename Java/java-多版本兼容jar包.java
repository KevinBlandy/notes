-------------------
多版本兼容jar包
-------------------
	# 在 META-INF 目录下创建了文件夹:version
		META-INF
			|-version
				|-8
				|-9
				|-10
		
		* 在version目录下新建 "版本目录", 支持什么版本就新建一个什么版本的目录

	# META-INF 目录下 MANIFEST.MF 文件新增了一个属性
		Multi-Release: true

-------------------
打包
-------------------
	#  编译不同版本的源码
		javac -d java8 --release 8 src/main/java8/io/java/tester/*.java		*/
		javac -d java9 --release 9 src/main/java9/io/java/tester/*.java		*/
		
		-d			指定存放编译代码的目录名称
		--release	指定编译的版本号
		最后指定当前这个版本的Java代码所在包

	
	# 创建多版本jar包
		
		jar -c --main-class=io.java.Mai -f test.jar -C java8 . --release 9 -C java9.

		--main-class			可以指定main函数类
		-f						指定jar包名称
		-C						指定编译好的java目录
		--release 9 -C java9	新增一个版本到这个jar，指定版本号以及类目录
	


