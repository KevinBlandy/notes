-------------------
go-redis
-------------------
	# Go Redis
		https://github.com/go-redis/redis

		* 中文文档
			https://redis.uptrace.dev/guide/go-redis.html
			https://redis.uptrace.dev/zh/
	
		https://pkg.go.dev/github.com/go-redis/redis/v9
	

-------------------
Demo
-------------------

	# 连接到 unixsocket

		options := &redis.Options{
			Network: "unix",                      // 使用 Unix Socket
			Addr:    "/var/run/redis/redis.sock", // Redis Unix Socket 路径
		}
		// 创建客户端
		client := redis.NewClient(options)
