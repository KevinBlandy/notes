--------------------
apr					|
--------------------
	# 这个东西可以大大提升Tomcat对静态文件的处理性能,同时如果你使用了HTTPS方式传输的话,也可以提升SSL的处理性能


	# 下载依赖
		* 下载地址:http://apr.apache.org/download.cgi
			apr-1.6.5.tar.gz
			apr-util-1.6.1.tar.gz
			apr-iconv-1.2.2.tar.gz
			tomcat-native.tar.gz
				* 在 ${TOMCAT_HOME}/lib 目录下,不需要下载
	
	# 解压安装 apr-1.6.5.tar.gz  
		tar -xvf apr-1.6.5.tar.gz   
　　　　cd apr-1.5.2   
		./configure   
		make && make install
	
	# 解压安装 tomcat-native.tar.gz
		tar -xvf tomcat-native.tar.gz
		cd tomcat-native-1.2.17-src/native
		./configure --with-apr=/usr/local/apr --with-java-home=/usr/local/java/jdk1.8.0_181
		make && make install

		* --with-apr 是安装apr自动生成的安装目录
		* --with-java-home 是自己环境的Java目录
	
	# 环境变量
		vim /etc/profile   
		export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:/usr/local/apr/lib   
		source /etc/profile
	
	# 修改Tomcat配置
		* 修改server.xml配置文件
		* Connector 节点的 protocol 属性
			protocol="org.apache.coyote.http11.Http11AprProtocol"

--------------------
apr-windows			|
--------------------