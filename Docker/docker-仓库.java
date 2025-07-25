------------------------
仓库					|
------------------------
	# Docker 仓库地址 & 结构
		https://hub.docker.com/
			|-repoisoty1
				|-image1 tag1
				|-image1 tag2
				|-image2
			|-repoisoty2
	
	# 快速设置

		sudo mkdir -p /etc/docker

		sudo tee /etc/docker/daemon.json <<-'EOF'
		{
		  "registry-mirrors": ["https://docker.1panel.live", "https://hub.rat.dev","https://docker.actima.top","https://docker.m.daocloud.io"]
		}
		EOF


		sudo systemctl daemon-reload
		sudo systemctl restart docker
	
		
		* 可用的节点 2

			sudo tee /etc/docker/daemon.json <<EOF
			{
				"registry-mirrors": ["https://dockerhub.icu"]
			}
			EOF

			sudo systemctl daemon-reload
			sudo systemctl restart docker


------------------------
各种仓库				|
------------------------
	https://hub.docker.com/

------------------------
修改为国内的仓库		|
------------------------
	# 编辑文件
		vim /etc/docker/deamon.json
		*  不存在,直接添加该文件
	
	# 内容
		{
			"registry-mirrors":["https://docker.mirrors.ustc.edu.cn"]
		}
	
	# 重启服务
		systemctl daemon-reload
		systemctl restart docker


------------------------
私有仓库				|
------------------------
	docker run -p5000:5000 registry

	docker push xx.com:8080/kevinblandy/app:1
	docker pull xx.com:8080/kevinblandy/app:1
	docker run xx.com:8080/kevinblandy/app:1


