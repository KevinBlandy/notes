---------------------------
mongod.conf
---------------------------
	# 文件名称
		mongod.conf

	# 配置项目参考
		https://docs.mongodb.com/manual/reference/configuration-options/

	
	# 注意
		* mongodb会自动把配置文件中的双引号的内容进行转义
			

---------------------------
mongod.conf - 常用
---------------------------
systemLog:
  destination: file # 指定日志的输出目标为文件
  path: <string> # 指定日志文件的地址
  logAppend: <boolean> # 实例重启后，日志从末尾是否开始写入（否则，从头开始）

processManagement:
  fork: true	# 是否在后台运行
  pidFilePath: /var/run/mongodb/mongod.pid  # pid 文件路径
  
net:
  port: 27017		# 监听端口
  bindIp: 127.0.0.1  # 绑定的ip
  maxIncomingConnections: 65535 # 进程允许的最大连接数 默认值为65536
  wireObjectCheck: true # 当客户端写入数据时 检测数据的有效性(BSON) 默认值为true
  ipv6: false 			# 默认值为false
 
storage:
  dbPath: /var/lib/mongo # 指定存储目录
  journal:
    enabled: <boolean> # 启用/禁用持久性日志，以确保文件数据有效和可恢复
