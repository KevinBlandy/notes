-----------------------------------------
docker-registry							 |
-----------------------------------------
	# 安装
		docker run -d -p 5000:5000 --restart=always --name registry -v /usr/local/registry:/var/lib/registry registry
	
	# 为本地镜像添加标签
		 docker tag [image] 127.0.0.1:5000/[image]:[tag]
		
	# 推送到本地仓库
		docker push 127.0.0.1:5000/[image]:[tag]
	
	# 查看仓库中的镜像
		curl 127.0.0.1:5000/v2/_catalog
		{"repositories":["ubuntu"]}
	
	# 删除镜像
		docker image rm 127.0.0.1:5000/[image]:[tag]
	
	# 让本网段的其他主机也能把镜像推送到私有仓库
		* 直接推送会失败,因为Docker 默认不允许非 HTTPS 方式推送镜像
		* 可以通过 Docker 的配置选项来取消这个限制
		* 修改配置添加局域网的ip地址:/etc/docker/daemon.json
			{
			  "insecure-registries": ["192.168.199.100:5000" ]
			}

-----------------------------------------
阿里云									 |
-----------------------------------------
	1. 登录阿里云Docker Registry
		$ sudo docker login --username=747692844@qq.com registry.cn-hangzhou.aliyuncs.com
		用于登录的用户名为阿里云账号全名，密码为开通服务时设置的密码。

		您可以在产品控制台首页修改登录密码。

	2. 从Registry中拉取镜像
		$ sudo docker pull registry.cn-hangzhou.aliyuncs.com/kevinblandy/test:[镜像版本号]

	3. 将镜像推送到Registry
		$ sudo docker login --username=747692844@qq.com registry.cn-hangzhou.aliyuncs.com
		$ sudo docker tag [ImageId] registry.cn-hangzhou.aliyuncs.com/kevinblandy/test:[镜像版本号]
		$ sudo docker push registry.cn-hangzhou.aliyuncs.com/kevinblandy/test:[镜像版本号]
		请根据实际镜像信息替换示例中的[ImageId]和[镜像版本号]参数。

	4. 选择合适的镜像仓库地址
		从ECS推送镜像时，可以选择使用镜像仓库内网地址。推送速度将得到提升并且将不会损耗您的公网流量。

		如果您使用的机器位于经典网络，请使用 registry-internal.cn-hangzhou.aliyuncs.com 作为Registry的域名登录，并作为镜像命名空间前缀。
		如果您使用的机器位于VPC网络，请使用 registry-vpc.cn-hangzhou.aliyuncs.com 作为Registry的域名登录，并作为镜像命名空间前缀。

	5. 示例
		使用"docker tag"命令重命名镜像，并将它通过专有网络地址推送至Registry。

		$ sudo docker images
		REPOSITORY                                                         TAG                 IMAGE ID            CREATED             VIRTUAL SIZE
		registry.aliyuncs.com/acs/agent                                    0.7-dfb6816         37bb9c63c8b2        7 days ago          37.89 MB
		$ sudo docker tag 37bb9c63c8b2 registry-vpc.cn-hangzhou.aliyuncs.com/acs/agent:0.7-dfb6816
		使用"docker images"命令找到镜像，将该镜像名称中的域名部分变更为Registry专有网络地址。

		$ sudo docker push registry-vpc.cn-hangzhou.aliyuncs.com/acs/agent:0.7-dfb6816