# ���� & ִ�нű�
	* wget --no-check-certificate https://raw.githubusercontent.com/teddysun/shadowsocks_install/master/shadowsocksR.sh

	* chmod +x shadowsocksR.sh


	* ./shadowsocksR.sh 2>&1 | tee shadowsocksR.log
		- ��������
		- �˿�
		- ����(rc4-md5)
		- Э��(auth_aes128_md5)
		- ����(http_simple)

# ��������
	����    /etc/init.d/shadowsocks start
	ֹͣ    /etc/init.d/shadowsocks stop
	����    /etc/init.d/shadowsocks restart
	״̬    /etc/init.d/shadowsocks status

#  �ļ�·��
	�����ļ�·��    /etc/shadowsocks.json
		{
			"server":"0.0.0.0",
			"server_ipv6":"[::]",
			"server_port":28888,
			"local_address":"127.0.0.1",
			"local_port":1080,
			"password":"19931209",
			"timeout":120,					# ��ʱʱ��
			"method":"rc4-md5",				# ���ܷ�ʽ
			"protocol":"auth_aes128_md5",	# Э����
			"protocol_param":"",			# Э�����
			"obfs":"http_simple",			# �������
			"obfs_param":"",				# ��������
			"redirect":"",
			"dns_ipv6":false,
			"fast_open":false,				# �����ķ����� Linux �ں���3.7+�����Կ��� true �Խ����ӳ�
			"workers":1						# Ĭ��һ��������
		}
	��־�ļ�·��    /var/log/shadowsocks.log
	���밲װĿ¼    /usr/local/shadowsocks

# ���û�����
	* �� shadowsocks.json �����е�:server_port �� password ɾ��
	* Ȼ�����
		"port_password":{
			"8989":"password1",
			"8990":"password2",
			"8991":"password3"
		},



# windows �ͻ���
	* ������

# ����UDPת��
	* �Ʒ�������ȫ�鿪��ssr�˿ڵ�udpЭ��
	* ����ǽ����ssr�˿ڵ�udpЭ��


# SSR��׿�ͻ���
	https://github.com/shadowsocksrr/shadowsocksr-android/releases

