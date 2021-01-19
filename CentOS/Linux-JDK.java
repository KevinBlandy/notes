	# 太简单,不多说



	1,下载文件
		jdk-8u101-linux-x64.tar.gz
	2,上传到 /usr/local/java 下
	3,解压:tar -xvf jdk-8u101-linux-x64.tar.gz
	4,删除原文件:rm jdk-8u101-linux-x64.tar.gz
	5,修改/etc/profile文件(注意版本)
		#JDK
export JAVA_HOME=/usr/local/java/jdk1.8.0_131
export JRE_HOME=/usr/local/java/jdk1.8.0_131/jre
export CLASSPATH=.:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools/jar:$JRE_HOME/lib:$CLASSPATH
export PATH=$JAVA_HOME/bin/:$PATH




