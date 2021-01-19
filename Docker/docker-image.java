-------------------------
image					 |
-------------------------
	# 查询镜像
		docker search [name]
			name
				* 查询指定的镜像
			
			--filter=stars=10000
				* 用于过滤, 仅仅查看收藏大于10000的镜像
			
			 --filter=is-automated=true
				* 仅仅显示automated build的镜像
			

		————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
		NAME                                   DESCRIPTION                                     STARS               OFFICIAL            AUTOMATED
		mysql                                  MySQL is a widely used, open-source relation…   7649                [OK]                [ok]
		————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
		NAME 名称
		DESCRIPTION 描述
		STARS 星星数量(受到欢迎的程度)
		OFFICIAL 是否是官方管理的
		AUTOMATED 是否DockerHub自动构建的


	# 查看本地的镜像(列出镜像)
		docker images [name]
			name
				* 仅仅列出本地的镜像
			-a
				* 显示所有的镜像
				* 中间层的镜像也会显示出来

			--digest
				* 显示摘要信息
			
			--no-trunc
				* 显示完整信息
			
		--------------------------------------------------------------------------------------
		REPOSITORY            TAG                 IMAGE ID            CREATED             SIZE
		hello-world           latest              fce289e99eb9        6 days ago          1.84kB
		nginx                 latest              7042885a156a        9 days ago          109MB
		--------------------------------------------------------------------------------------
	
	# 镜像拉取
		docker pull [name]:[tag]
			name
				* 镜像名称
			tag
				* 标签(版本号)
				* 如果省略, 默认拉取 latest 版本
			-a
				* 下载指定镜像的所有tag(版本)到本地
	
	# 删除镜像
		docker rmi [name]:[tag]
			* 如果不指定tag, 默认删除 last 版本
			* 也可以把name换成指定的image id
			* 删除所有的镜像
				docker rmi `docker images -q`
		
			-f
				* 强制删除, 就算是还有正在运行的容器
	
	# 导出镜像
		docker save [name]:[tag] > /[path].image
		
	# 导入镜像
		docker load < /[path].image
	
	

-------------------
虚悬镜像			|
-------------------
	# 关于
		这个镜像原本是有镜像名和标签的,原来为 mongo:3.2,随着官方镜像维护,发布了新版本后,重新 docker pull mongo:3.2 时,mongo:3.2 这个镜像名被转移到了新下载的镜像身上
		而旧的镜像上的这个名称则被取消,从而成为了 <none>
		除了 docker pull 可能导致这种情况,docker build 也同样可以导致这种现象,由于新旧镜像同名,旧镜像名称被取消,从而出现仓库名,标签均为 <none> 的镜像
		这类无标签镜像也被称为 虚悬镜像(dangling image) ,可以用下面的命令专门显示这类镜像

			docker image ls -f dangling=true

	# 删除虚悬镜像
		docker image prune