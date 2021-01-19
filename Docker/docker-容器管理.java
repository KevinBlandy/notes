----------------------------
容器管理					|
----------------------------
	# 容器所在的本地目录
		/var/lib/docker/containers
	
	# 容器在的运行,必须要有一个前台程序在运行,不然就会自动退出
		

----------------------------
容器管理					|
----------------------------
	# 新启动容器
		docker run [image] [cmd]
			-d
				* 以守护进程的形式启动容器
				* docker 必须运行一个前台程序,不然也会自动的退出

			-p
				* 端口映射
					-p [宿主机端口]:[容器端口]

				* 可以有多个-p参数,表示绑定多个端口

				* 如果存在多块网卡(多个ip),也可以指定
					-p 127.0.0.1:8081:80

				* 如果只有一个参数,则表示容器的端口,而会随机选择一个宿主机的端口进行绑定(建议这种方式同时启动多个容器)
					-p 80

				* 可以在后面添加 /udp 表示映射的是udp端口
					 -p 1024:1024/udp

			-P
				* 对外公开 Dockerfile 中 EXPOSE 定义的所有端口
				* 每个对外公开的端口,都会随机选择一个宿主机端口进行绑定

			-it
				* 以交互形式启动容器
				* -i 开启了容器的stdin
				* -t 开启一个tty的伪终端
			-e
				* 指定环境变量
					-e "WEB_PORT=8080"
			-v
				* 设置共享卷 -v [宿主机目录]:[虚拟机目录]
					-v /usr/local/website:/var/www/html/website
				* 如果目录不存在,docker会自动的创建
				* 还可以添加权限来控制是否只读:ro(readOnly)
					-v /usr/local/website:/var/www/html/website:ro
				* 可以有多个 -v 参数,来表示要设置多个共享卷
			
			-h
				* 指定容器的主机名,默认主机名使用的是容器id
			
			--rm
				* 该参数表示一次性容器,当容器关闭后就会被删除

			--volumes-from
				* 把指定容器设置的所有卷都加入进创建的容器里面
					--volumes-from app1
				* 复用指定容器的卷
				* 该命令可以执行多次,表示从多个容器复用卷
					--volumes-from app1 --volumes-from app2

			--name
				* 自定义名称(多个运行的容器名称不能重复)
				*  --name [name]
				* '在docker很多命令中,都可以通过该名称来操作容器'

			--restart
				* 指定容器的重启策略
					--restart=always
				* 枚举值
					always
						只要容器被关闭就会立即重启,不论任何原因导致的关闭
					on-failure
						只有容器的退出代码非0时才会重启(非正常关闭,则重启)
						它还接收一个最大重启次数: --restart=on-failure:5
					
			--entrypoint
				* 覆写的是Dockerfile里面的 ENTRYPOINT 指令
				* 会把 [cmd] 当作参数传递给该命令执行
			
			--privileged
				* 启动Dokcer的特权模式
					--privileged=true
				* 这种模式下允许虚拟机以,宿主机具有(几乎)的所有能力来运行容器,包括内核特性和设备访问
				* 这是想要在Docker中运行Docker必要的魔法

			--net/--network
				* 指定容器运行的网络
					--net=mynet
				* 该网络需要先创建
			
			--network-alias
				* 指定当前容器在network中的别名(默认:[container-name].[net-name])
					--network-alias my-service
			
			--cidfile
				* 可以把容器的id存储到指定的文件
					--cidfile=/tmp/containerid

			--mount
				* 挂载数据卷
					 --mount source=[name],target=[source] [target]

			--link
				* ?

			image
				* 镜像

			cmd
				* 启动OK后,向容器执行的命令,覆写的是Dockerflile里面的 CMD 指令
				* 如果包含特殊符号,需要用""包含

	
	# 重新启动已经停止的容器
		docker start [id]
			id
				* 容器id或者name
			

	# 查看运行的容器
		docker -ps
			-a
				* 包含未运行的容器
			-l
				* 仅仅列出最后一次运行的容器
			-n
				* 指定显示的数量
				* -n 15
			-q
				* 仅仅查看(返回)容器的id信息
			
			------------------------------------------------------------------------------------------------------------------------------------------------------
			CONTAINER ID        IMAGE               COMMAND                  CREATED             STATUS              PORTS               NAMES
			b860f33a7e02        nginx               "nginx -g 'daemon of…"   8 minutes ago       Up 8 minutes        80/tcp              keen_galois
			------------------------------------------------------------------------------------------------------------------------------------------------------

			* STATUS 后面的括号有一个状态值,如果该值为0,则表示是正常退出的
			* NAMES 随机的名称(贼有意思)

	
	# 优雅停止容器
		docker stop [id]
			id
				* CONTAINER ID,或者是自己定义的名称


	# 强制杀死容器
		docker kill [id]
			id
				* CONTAINER ID,或者是自己定义的名称
		

	# 复制文件到容器
		docker cp [file] [id]:[path]
			* 把file复制到名字为id的path路径下
			* id也可以是容器的CONTAINER ID
	
	# 复制文件到宿主机
		docker cp [id]:[file] [path]
			* 把指定容器(id)的指定文件(file)复制到宿主机的path目录下
	
	#  查看容器的日志
		docker logs [id]
			id
				* 容器的id或者name
			-f
				* 可以即时的看到日志刷新
			-t
				* 为每条日志加上时间信息
	

	# 删除容器
		docker rm [id]
			id
				* 容器的hash或者name
			-f
				* 可以删除运行时的容器
				* 如果不添加该参数,则只能删除停止状态的容器
			
			-v
				* 同时移除挂载的数据卷

			* 删除所有容器
				docker rm `docker ps -a -q`
	
	# 删除所有已经停止的容器
		docker container prune

	# 查看容器内的进程
		docker top [id]
			id
				* 容器的id或者name
	
	# 查看容器的状态
		docker stats [id]
			id
				* 容器的id或者name
	
	# 在容器内部运行进程
		docker exec [id] [cmd]
			id
				* 容器id或者name
			cmd
				* 启动命令
				* 如果包含特殊符号,需要用""包含
			-it
				* 在交互模式下进行
			
			* 不需要进入到容器内部, 直接在宿主机上执行

	
	# 快速的进入到容器内部
		docker attach [id]
			id
				* 容器id或者name
		

	
	# 查看容器的详细信息(返回json信息)
		docker inspect [id]
			id
				* 容器id或者name
				* 在这里可以指定一个或者多个,用空格隔开
			-f
				* 格式化(仅仅查看指定的json信息,json导航)
					docker inspect -f "{{.NetworkSettings.SandboxID}}" name 

				* 格式化的参数,需要用""包裹,因为包含了特殊的字符
				* -f 完全支持go语言的模板(不会,滚)
				* 该参数也可以同时指定多个参数,表示查看多个信息值
					docker inspect -f "{{.NetworkSettings.SandboxID}} {{.name}}" name
				* 其实参数就是一个字符串模板,然后可以用go的表达式去配置信息中取值来填充
	
	
	# 查看(返回)容器映射(与宿主机)的端口信息
		docker port [id] [port]
			id
				* 容器name或者id
			port
				* 容器的开放的端口
				* 必须在容器启动状态下才能查看
	
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

	# 查看容器的改变记录
		docker diff [id]
			id
				* 容器name或者id
	
	# 容器导出
		docker export [id] > xxxtar

	# 容器导入
		docker import

		* 可以通过指定 URL 或者某个目录来导入，例如
			docker import http://example.com/exampleimage.tgz example/imagerepo

			
----------------------------
其它						|
----------------------------
	# 从前容器中退出
		exit
			* 退出,如果是不是后台运行的容器(-d),那么就会终止容器
			* 如果run启动的时候没有添加 -d 参数,执行exit时,就会退出容器

		ctrl + p + q
			*  退出,但是不终止容器
	


			


	
		