----------------------------
v2ry						|
----------------------------
	# ��ַ
		https://www.v2ray.com
		https://github.com/v2ray/v2ray-core
	
		https://toutyrater.github.io/
		https://guide.v2fly.org/#%E5%A3%B0%E6%98%8E
	
----------------------------
�ٷ���װ					|
----------------------------
	# ���ز���װ V2Ray
		bash <(curl -s -L https://git.io/v2ray.sh)

		* yum �� apt-get ���õ������,�˽ű����Զ���װ unzip �� daemon
		* ����������ǰ�װ V2Ray �ı�Ҫ���
		* ���ϵͳ��֧�� yum �� apt-get,�����а�װ unzip �� daemon
	
		* �˽ű����Զ���װ�����ļ�
			/usr/bin/v2ray/v2ray		V2Ray ����
			/usr/bin/v2ray/v2ctl		V2Ray ����
			/etc/v2ray/config.json		�����ļ�
			/usr/bin/v2ray/geoip.dat	IP �����ļ�
			/usr/bin/v2ray/geosite.dat	���������ļ�
		
		* ���нű�λ��ϵͳ������λ��
			/etc/systemd/system/v2ray.service: Systemd
			/etc/init.d/v2ray: SysV
		
	
	# �����ļ�
		/etc/v2ray/config.json
	
	# ������ά��
		systemctl start v2ray

		start
		stop
		status
		reload
		restart
		force-reload // ǿ�����¼���


	
	# �����͸���
		* �ٴ�ִ�а�װ�ű�, ��OK
		* ����ű����Զ������û�а�װ�� V2Ray, ���û��, ����������İ�װ������
		* ���֮ǰ��װ�� V2Ray, ��ֻ���� V2Ray �����Ƴ��������������
	
	# �ͻ��˵�����
		windows:
			https://github.com/2dust/v2rayN
		android:
			https://github.com/2dust/v2rayNG

	
	# �ͻ�������
		v2ray info 
			�鿴 V2Ray ������Ϣ
		v2ray config 
			�޸� V2Ray ����
		v2ray link 
			���� V2Ray �����ļ�����
		v2ray infolink 
			���� V2Ray ������Ϣ����
		v2ray qr 
			���� V2Ray ���ö�ά������
		v2ray ss 
			�޸� Shadowsocks ����
		v2ray ssinfo 
			�鿴 Shadowsocks ������Ϣ
		v2ray ssqr 
			���� Shadowsocks ���ö�ά������
		v2ray status 
			�鿴 V2Ray ����״̬
		v2ray start 
			���� V2Ray
		v2ray stop 
			ֹͣ V2Ray
		v2ray restart 
			���� V2Ray
		v2ray log 
			�鿴 V2Ray ������־
		v2ray update 
			���� V2Ray
		v2ray update.sh 
			���� V2Ray ����ű�
		v2ray uninstall 
			ж�� V2Ray
						

----------------------------
v2ray	�����в���			|
----------------------------
	-version
		* ֻ�����ǰ�汾Ȼ���˳�,������ V2Ray ������



