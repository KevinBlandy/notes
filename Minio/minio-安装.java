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
   -e "MINIO_ROOT_PASSWORD=minio858896" \
   quay.io/minio/minio server /data --console-address ":9090"

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
	


	
