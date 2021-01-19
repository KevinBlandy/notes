----------------------------
docker 命令					|
----------------------------
	docker help
		* 查看所有的命令帮助

	docker info
		* 查看docker的基本配置信息
	
----------------------------
其他						|
----------------------------
	# 命令可以参数化
		*  使用 $(cmd)
			docker rmi $(docker images -q)
		
		*  ``也可以
			docker rm `docker ps -aq`













docker build -t friendlyname .# 使用此目录的 Dockerfile 创建镜像
docker run -p 4000:80 friendlyname  # 运行端口 4000 到 90 的“友好名称”映射
docker run -d -p 4000:80 friendlyname         # 内容相同，但在分离模式下
docker ps                                 # 查看所有正在运行的容器的列表
docker stop <hash>                     # 平稳地停止指定的容器
docker ps -a           # 查看所有容器的列表，甚至包含未运行的容器
docker kill <hash>                   # 强制关闭指定的容器
docker rm <hash>              # 从此机器中删除指定的容器
docker rm $(docker ps -a -q)           # 从此机器中删除所有容器
docker images -a                               # 显示此机器上的所有镜像
docker rmi <imagename>            # 从此机器中删除指定的镜像
docker rmi $(docker images -q)             # 从此机器中删除所有镜像
docker login             # 使用您的 Docker 凭证登录此 CLI 会话
docker tag <image> username/repository:tag  # 标记 <image> 以上传到镜像库
docker push username/repository:tag            # 将已标记的镜像上传到镜像库
docker run username/repository:tag                   # 运行镜像库中的镜像


docker stack ls              # 列出此 Docker 主机上所有正在运行的应用
docker stack deploy -c <composefile> <appname>  # 运行指定的 Compose 文件
docker stack services <appname>       # 列出与应用关联的服务
docker stack ps <appname>   # 列出与应用关联的正在运行的容器
docker stack rm <appname>                             # 清除应用

docker-machine create --driver virtualbox myvm1 # 创建 VM（Mac、Win7、Linux）
docker-machine create -d hyperv --hyperv-virtual-switch "myswitch" myvm1 # Win10
docker-machine env myvm1                # 查看有关节点的基本信息
docker-machine ssh myvm1 "docker node ls"         # 列出 swarm 中的节点
docker-machine ssh myvm1 "docker node inspect <node ID>"        # 检查节点
docker-machine ssh myvm1 "docker swarm join-token -q worker"   # 查看加入令牌
docker-machine ssh myvm1   # 打开与 VM 的 SSH 会话；输入“exit”以结束会话
docker-machine ssh myvm2 "docker swarm leave"  # 使工作节点退出 swarm
docker-machine ssh myvm1 "docker swarm leave -f" # 使主节点退出，终止 swarm
docker-machine start myvm1            # 启动当前未运行的 VM
docker-machine stop $(docker-machine ls -q)               # 停止所有正在运行的 VM
docker-machine rm $(docker-machine ls -q) # 删除所有 VM 及其磁盘镜像
docker-machine scp docker-compose.yml myvm1:~     # 将文件复制到节点的主目录
docker-machine ssh myvm1 "docker stack deploy -c <file> <app>"   # 部署应用
