
# 安装pip
	yum install python-setuptools & easy_install pip

# 安装Shadowsocks 客户端
	pip install shadowsocks

# 编辑配置文件
	* vim /etc/shadowsocks.json

	{
		"server":"0.0.0.0",
		"port_password":{
			"123456" : "----"			# 端口和密码，shadowsocks不需要账号，真要说有，账号就是你的ip了。可以配置多个
		},
		"timeout":300,                 	# 超时时间 这个不用修改
		"method":"rc4-md5",     		# 加密方式，不用修改，想改的自行了解各种加密算法吧
		"protocol" : "auth_aes128_md5",	# 协议插件
		"protocol_param": "",			# 协议参数
		"obfs" : "http_simple",			# 混淆插件
		"obfs_param": "",				# 混淆参数
		"fast_open": false,             # 如果你的服务器 Linux 内核在3.7+，可以开启 true 以降低延迟
		"workers": 1					# 默认一个工作者
	}

# 启动 shadowsocks
	* 启动
		ssserver -c /etc/shadowsocks.json -d start

	* 停止
		ssserver -c /etc/shadowsocks.json -d stop

	* 重新启动
		ssserver -c /etc/shadowsocks.json -d restart

	
	* 查看是否启动成功
		ps -aux | grep ssserver

# 设置开机启动
	vim /etc/rc.local
	ssserver -c /etc/shadowsocks/config.json -d start

# 开启防火墙端口
	* 略


# windows客户端下载
	https://github.com/shadowsocks/shadowsocks-windows/releases


# shadowsocks服务会被阿里云之类的服务商检测到,建议使用 shadowsocksR