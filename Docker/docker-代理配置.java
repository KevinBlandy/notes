-------------------------
配置网络代理
-------------------------
	# 删除之前配置的国内镜像源，配置 docker 官方镜像链接。

		/etc/docker/daemon.json

		{
		 "registry-mirrors": [
			"https://hub.docker.com/"
		  ]
		}
	
	
	# 编辑文件
		vim /etc/systemd/system/docker.service.d/proxy.conf


		[Service]
		Environment="HTTP_PROXY=http://127.0.0.1:7890"
		Environment="HTTPS_PROXY=http://127.0.0.1:7890"

		* 设置配置信息
	

	# 加载配置、重启 Docker

		systemctl daemon-reload
		systemctl restart docker
		
		
		* 查看代理配置是否生效

		systemctl show --property=Environment docker
			
			
			Environment=HTTP_PROXY=http://XXX:7892 HTTPS_PROXY=http://XXX:7892
