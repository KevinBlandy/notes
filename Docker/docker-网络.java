------------------------------
network						  |
------------------------------
	# 参考文档
		https://docs.docker.com/config/containers/container-networking/
	
	# 同一个网络中的容器,都维护了一个 dns列表(/etc/hosts)

		ip [container-name]
		ip [container-name].[net-name]

------------------------------
network						  |
------------------------------
	# 创建一个网络
		docker network create [name]
			name
				* 网络名称
			-d 
				* 指定网络的类型,枚举值
					bridge
					overlay(Swarm mode)
	
	# 查看所有创建的网络
		docker network ls
	
	# 查看网络的详情
		docker network inspect [name]
			name
				* 网络名称或者id
	
	# 连接容器到指定的网络
		docker network connect [net] [container]
			container
				* 指定的container容器
			net
				* 指定网络
					
	# 从指定的网络中断开容器连接
		docker network connect [net] [container] 
			container
				* 指定的container容器
			net
				* 指定网络
	
	# 删除
		docker network rm [name]
			name
				* 网络名称或者id