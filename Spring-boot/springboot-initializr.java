--------------------------------------
SpringBoot Initializr
--------------------------------------
	# 地址
		https://start.spring.io/
		https://github.com/spring-io/start.spring.io
	
	# 自己安装
		1. cloen代码：git clone https://github.com/spring-io/start.spring.io
		2. 打包编译：cd start.spring.io ，执行mvnw clean package -Dmaven.test.skip=true
		3. target 文件下两个jar 用*-exec.jar 可以直接java -jar -Dserver.port=8080 target\start-site-exec.jar 运行

		* 需要安装nodejs和node-gyp
			yum -y install nodejs
			npm install -g node-gyp
