# 下载 & 执行脚本
	* wget --no-check-certificate https://raw.githubusercontent.com/teddysun/shadowsocks_install/master/shadowsocksR.sh

	* chmod +x shadowsocksR.sh


	* ./shadowsocksR.sh 2>&1 | tee shadowsocksR.log
		- 输入密码
		- 端口
		- 加密(rc4-md5)
		- 协议(auth_aes128_md5)
		- 混淆(http_simple)

# 常用命令
	启动    /etc/init.d/shadowsocks start
	停止    /etc/init.d/shadowsocks stop
	重启    /etc/init.d/shadowsocks restart
	状态    /etc/init.d/shadowsocks status

#  文件路径
	配置文件路径    /etc/shadowsocks.json
		{
			"server":"0.0.0.0",
			"server_ipv6":"[::]",
			"server_port":28888,
			"local_address":"127.0.0.1",
			"local_port":1080,
			"password":"19931209",
			"timeout":120,					# 超时时间
			"method":"rc4-md5",				# 加密方式
			"protocol":"auth_aes128_md5",	# 协议插件
			"protocol_param":"",			# 协议参数
			"obfs":"http_simple",			# 混淆插件
			"obfs_param":"",				# 混淆参数
			"redirect":"",
			"dns_ipv6":false,
			"fast_open":false,				# 如果你的服务器 Linux 内核在3.7+，可以开启 true 以降低延迟
			"workers":1						# 默认一个工作者
		}
	日志文件路径    /var/log/shadowsocks.log
	代码安装目录    /usr/local/shadowsocks

# 多用户配置
	* 把 shadowsocks.json 配置中的:server_port 和 password 删除
	* 然后添加
		"port_password":{
			"8989":"password1",
			"8990":"password2",
			"8991":"password3"
		},



# windows 客户端
	* 网上找

# 开启UDP转发
	* 云服务器安全组开放ssr端口的udp协议
	* 防火墙开放ssr端口的udp协议

