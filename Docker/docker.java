----------------------------
docker						|
----------------------------
	# ����
		https://www.docker.com/
		http://www.docker-cn.com/

	# �ο�
		https://docker_practice.gitee.io/
	


----------------------------
Centos yum ��װ				|
----------------------------
	# �Ƴ�һЩ�Դ������
sudo yum remove docker \
docker-client \
docker-client-latest \
docker-common \
docker-latest \
docker-latest-logrotate \
docker-logrotate \
docker-selinux \
docker-engine-selinux \
docker-engine
	
	# ��װ����
yum install -y yum-utils \
device-mapper-persistent-data \
lvm2

	# ��Ӳֿ�
yum-config-manager \
--add-repo \
https://download.docker.com/linux/centos/docker-ce.repo
	
	# ���òֿ�
		yum-config-manager --enable docker-ce-edge

		yum-config-manager --enable docker-ce-test
	
		* �����Ҫ���òֿ�
			yum-config-manager --disable docker-ce-edge
	
	# ��װdocker
		yum install -y docker-ce
	
	# ����dcoker
		systemctl start docker
		
	# HelloWorld����
		docker run hello-world
	
----------------------------
���ذ�װ					|
----------------------------
	
	# ����rpm�ļ�
		https://download.docker.com/linux/centos/7/x86_64/stable/Packages/
	
	# ִ�а�װ
		yum install /opt/docker-ce-18.09.0-3.el7.x86_64.rpm
	
	# ����docker
		systemctl start docker
	
	# HelloWorld����
		docker run hello-world
	
----------------------------
Centos Docker������ά��		|
----------------------------
	systemctl enable docker
	systemctl start docker
	systemctl stop docker
	systemctl restart docker
	systemctl status docker
