------------------------
Hudson-入门				|
------------------------
	# 自动化编译,测试,发布

	# Hudson只是一个持续集成服务器(持续集成工具)
	# 搭建一套完整的持续集成管理平台,还需要SVN,Maven等
	# Hudson 支持war或者是针对于操作系统的RMP安装
		* 为了通用性和迁移性,一般都是才用WAR包,运行在Tomcat容器

	1,下载,解压
		wget http://ftp.jaist.ac.jp/pub/eclipse/hudson/war/hudson-3.3.3.war

	2,配置环境变量
		export HUDSON_HOME=/usr/local/hudson
		
	3,Hudson比较消耗内存,所以应该配置Tomcat的JVM信息
		* 编辑bin/catalina.sh
		JAVA_OPTS='-Xms512m -Xmx2048m'
	
	
	