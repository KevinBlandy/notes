-----------------------
Maven-Linux				|
-----------------------
	1,����
		wget http://211.162.213.120:9011/mirrors.hust.edu.cn/c3pr90ntc0td/apache/maven/maven-3/3.3.9/binaries/apache-maven-3.3.9-bin.tar.gz

		wget http://www-eu.apache.org/dist/maven/maven-3/3.0.5/binaries/apache-maven-3.0.5-bin.tar.gz
	2,��ѹ
		tar -xvf ...
	3,�༭���������ļ�
		vim /etc/profile
	
export MAVEN_HOME=/usr/maven/apache-maven-3.9.9
export PATH=$PATH:$MAVEN_HOME/bin

	4,source profile
		source /etc/profile