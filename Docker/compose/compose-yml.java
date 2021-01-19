
# 配置文件参考
	https://github.com/docker/docker.github.io/blob/master/compose/compose-file/index.md


# 常用

version: "[版本号]"
# 表示的是配置文件的版本号
services:
  [应用名称]:
    image:[image]
	container_name: [容器名称]
    build: [指定Dockerfile目录,会新构建一个镜像]
	ports:
	  - "[宿主机端口]:[容器端口]"
    volumes:
	  - [宿主机目录]:[容器目录]
    networks:
	  - [网络]
    environment:
	    # 环境变量
		NAME: root
		PASS: ROOT
   depends_on:
      # 该应用,依赖的应用
      - nginx
	  - mysql
    working_dir: /opt/app
    # 工作目录
	deploy:
	# 部署选项
	  replicas: [集群数量]
	  update_config:
	    parallelism: 2
		delay: 10s
		restart_policy:
		# 重启策略
		  condition: on-failure
		  # 重启条件,枚举值