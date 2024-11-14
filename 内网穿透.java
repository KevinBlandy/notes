------------------------
frp
------------------------
	# frp��ַ
		https://github.com/fatedier/frp/releases
		https://github.com/fatedier/frp/blob/master/README_zh.md

	# ����˰�װ
		* ���� & ��ѹ
			wget https://github.com/fatedier/frp/releases/download/v0.61.0/frp_0.61.0_linux_amd64.tar.gz

		* �����ļ��༭����������ļ�
			vi frps.ini
	[common]
	bind_port = 7000
	# 80�˿ڵ�http����
	vhost_http_port = 80
	# �ͻ��˵�����token
	token = aabbcc123456

	log_file = ./frps.log
	log_level = info
	log_max_days = 3

	# �������̨������
	dashboard_port = 7500
	dashboard_user = admin
	dashboard_pwd = frV1n123456
			
		## ��������˽���
			 nohup ./frps -c ./frps.ini &
		
	# �ͻ��˰�װ
		* ���� & ��ѹ
			https://github.com/fatedier/frp/releases/download/v0.61.0/frp_0.61.0_windows_amd64.zip
		
		* ���ÿͻ����ļ�
			frpc.ini
	[common]
	server_addr = [����������ip]
	server_port = 7000
	# ��������õ�token
	token = aabbcc123456

	[web]
	type = http
	# ���ص�http����˿�
	local_port = 80
	# ���������������Զ�������
	custom_domains = frp.springboot.io
		
		* �����ͻ���
			frpc.exe -c frpc.ini
	
------------------------
frp������web����
------------------------
# ����� ����
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


# �ͻ�������
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
