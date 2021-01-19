------------------------
frp
------------------------
	# frp地址
		https://github.com/fatedier/frp/releases
		https://github.com/fatedier/frp/blob/master/README_zh.md

	# 服务端安装
		* 下载 & 解压
			wget https://github.com/fatedier/frp/releases/download/v0.30.0/frp_0.30.0_linux_amd64.tar.gz

		* 进入文件编辑服务端配置文件
			vi frps.ini
	[common]
	bind_port = 7000
	# 80端口的http服务
	vhost_http_port = 80
	# 客户端的链接token
	token = aabbcc123456

	log_file = ./frps.log
	log_level = info
	log_max_days = 3

	# 管理控制台的配置
	dashboard_port = 7500
	dashboard_user = admin
	dashboard_pwd = frV1n123456
			
		## 启动服务端进程
			 nohup ./frps -c ./frps.ini &
		
	# 客户端安装
		* 下载 & 解压
			https://github.com/fatedier/frp/releases/download/v0.30.0/frp_0.30.0_windows_amd64.zip
		
		* 配置客户端文件
			frpc.ini
	[common]
	server_addr = [服务器公网ip]
	server_port = 7000
	# 服务端设置的token
	token = aabbcc123456

	[web]
	type = http
	# 本地的http服务端口
	local_port = 80
	# 解析到服务器的自定义域名
	custom_domains = frp.springboot.io
		
		* 启动客户端
			frpc.exe -c frpc.ini
	
------------------------
frp代理多个web服务
------------------------
# 服务端 配置
[common]
bind_port = 7000
vhost_http_port = 8080
token = 123456

log_file = ./frps.log
log_level = info
log_max_days = 3

[web1]
type = http
custom_domains = api.baidu.com

[web2]
type = http
custom_domains = manager.baidu.com

[web3]
type = http
custom_domains = channel.baidu.com

[web4]
type = http
custom_domains = agent.baidu.com


# 客户端配置
[common]
server_addr = 38.21.227.30
server_port = 7000
token = 123456

vhost_http_port = 8080

[web1]
type = http
local_port = 8080
custom_domains = api.baidu.com

[web2]
type = http
local_port = 8081
custom_domains = manager.baidu.com

[web3]
type = http
local_port = 8082
custom_domains = channel.baidu.com

[web4]
type = http
local_port = 8083
custom_domains = agent.baidu.com
