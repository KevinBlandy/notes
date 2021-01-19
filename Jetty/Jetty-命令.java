-----------------------
Jetty-命令				|
-----------------------	
	java -jar start.jar
		 # 启动服务器
		 # 参数
			--list-modules
				* 此命令将返回当前服务所有可用的模块，同时也会显示本地的模块，信息包括模块实现的顺序，依赖模块以及相应的jar信息
			--list-config
				* 显示运行环境和配置文件等信息 

			jetty.http.port=8081
				* 运行的时候修改Jetty端口
			
			--add-to-startd=http,deploy
				* 在当前目录创建一些文件
				* java -jar D:\data\frame\jetty-distribution-9.3.11.v20160721\start.jar --add-to-startd=http,deploy
