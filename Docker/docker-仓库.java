------------------------
�ֿ�					|
------------------------
	# Docker �ֿ��ַ & �ṹ
		https://hub.docker.com/
			|-repoisoty1
				|-image1 tag1
				|-image1 tag2
				|-image2
			|-repoisoty2
	
	# ��������

		sudo mkdir -p /etc/docker

		sudo tee /etc/docker/daemon.json <<-'EOF'
		{
		  "registry-mirrors": ["https://docker.mirrors.ustc.edu.cn", "https://ustc-edu-cn.mirror.aliyuncs.com","https://7uuu3esz.mirror.aliyuncs.com","https://780urbjd.mirror.aliyuncs.com"]
		}
		EOF


		sudo systemctl daemon-reload
		sudo systemctl restart docker
	
		
		* ���õĽڵ� 2

			sudo tee /etc/docker/daemon.json <<EOF
			{
				"registry-mirrors": ["https://dockerhub.icu"]
			}
			EOF

			sudo systemctl daemon-reload
			sudo systemctl restart docker


------------------------
���ֲֿ�				|
------------------------
	https://hub.docker.com/

------------------------
�޸�Ϊ���ڵĲֿ�		|
------------------------
	# �༭�ļ�
		vim /etc/docker/deamon.json
		*  ������,ֱ����Ӹ��ļ�
	
	# ����
		{
			"registry-mirrors":["https://docker.mirrors.ustc.edu.cn"]
		}
	
	# ��������
		systemctl daemon-reload
		systemctl restart docker


------------------------
˽�вֿ�				|
------------------------
	docker run -p5000:5000 registry

	docker push xx.com:8080/kevinblandy/app:1
	docker pull xx.com:8080/kevinblandy/app:1
	docker run xx.com:8080/kevinblandy/app:1


