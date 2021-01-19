----------------------------
docker						|
----------------------------
	# 官网
		https://www.docker.com/
		http://www.docker-cn.com/

	# 参考
		https://docker_practice.gitee.io/
	


----------------------------
Centos yum 安装				|
----------------------------
	# 移除一些自带的组件
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
	
	# 安装依赖
yum install -y yum-utils \
device-mapper-persistent-data \
lvm2

	# 添加仓库
yum-config-manager \
--add-repo \
https://download.docker.com/linux/centos/docker-ce.repo
	
	# 启用仓库
		yum-config-manager --enable docker-ce-edge

		yum-config-manager --enable docker-ce-test
	
		* 如果需要禁用仓库
			yum-config-manager --disable docker-ce-edge
	
	# 安装docker
		yum install -y docker-ce
	
	# 启动dcoker
		systemctl start docker
		
	# HelloWorld测试
		docker run hello-world
	
----------------------------
本地安装					|
----------------------------
	
	# 下载rpm文件
		https://download.docker.com/linux/centos/7/x86_64/stable/Packages/
	
	# 执行安装
		yum install /opt/docker-ce-18.09.0-3.el7.x86_64.rpm
	
	# 启动docker
		systemctl start docker
	
	# HelloWorld测试
		docker run hello-world
	
----------------------------
Centos Docker基本的维护		|
----------------------------
	systemctl start docker
	systemctl stop docker
	systemctl restart docker
	systemctl status docker