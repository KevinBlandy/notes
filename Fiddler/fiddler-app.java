-----------------------------------
Fiddler 抓包
-----------------------------------
	# Fiddler 开启允许远程客户端访问
		Tools -> Options -> Connections -> Allow Remote Computers To Connection
	
		* 勾选后重启Filder
	
	# 客户端在网络处, 设置http代理, 填写 Fiddler 所在的ip和端口

-----------------------------------
ios 抓包
-----------------------------------
	# 设置了http代理后
	# 在 safir 浏览器打开: http://fiddler-ip:8888/
	# 下载证书, 

	# 设置 -> 通用 -> 描述文件与设备 
		* 安装 DO_NOT_TRUST_FiddlerRoot
	
	# 设置 -> 通用 -> 关于本机 -> 信任证书设置 -> 开启 DO_NOT_TRUST_FiddlerRoot 的信任
	
