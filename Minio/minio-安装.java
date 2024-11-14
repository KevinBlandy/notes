--------------------
Mino - docker安装
--------------------

# 首先在 home 目录下创建资源目录
mkdir -p ~/minio/data

docker run \
   -d \
   -p 9000:9000 \
   -p 9090:9090 \
   --name minio-server \
   -v ~/minio/data:/data \
   -e "MINIO_ROOT_USER=admin" \
   -e "MINIO_ROOT_PASSWORD='123456'" \
   quay.io/minio/minio server /data --console-address ":9090"

* 资源访问: http://host:9000/ 
* 管理控制: http://host:9090/ - admin - '123456'



--------------------
Mino - 二进制
--------------------
	# 下载
		https://dl.min.io/server/minio/release/linux-amd64/minio
	
	# 执行
		chmod +x minio
		./minio server /data
		
		* 会提示警告, 需要手动去配置文件修改用户名和密码
			Detected default credentials 'minioadmin:minioadmin', please change the credentials immediately using 'MINIO_ACCESS_KEY' and 'MINIO_SECRET_KEY'
		
		* 默认的凭证信息
			minioadmin:minioadmin
	


	
--------------------
Mino - Nginx 网关
--------------------
	# 一般使用 Nginx 作为网关代理 Minio 资源

	# 访问资源根目录(/)会被重定向到管理控制台的问题

		* 修改资源网关配置，如果访问根目录则响应 403
			server {
				listen 80;
				server_name your_domain_or_ip;

				location / {
					# 检查请求路径是否为根目录
					if ($request_uri = /) {
						return 404;
					}

					# 代理其他请求到 MinIO 服务
					proxy_pass http://localhost:9000;
					proxy_set_header Host $host;
					proxy_set_header X-Real-IP $remote_addr;
					proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
					proxy_set_header X-Forwarded-Proto $scheme;
				}
			}
		
		* 也可以单独在 Server 配置块中配置（针对宝塔之类的面板）
		
			server {
				// .. 其他配置
		        location = / {
					return 403;
				}
				// .. 其他配置
				

				// 资源代理服务
				location / {
					proxy_pass http://localhost:9000;
					proxy_set_header Host $host;
					proxy_set_header X-Real-IP $remote_addr;
					proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
					proxy_set_header X-Forwarded-Proto $scheme;
				}
		}