--------------------
Mino - docker安装
--------------------
docker run -p 9000:9000 --name minio1 \
-e "MINIO_ACCESS_KEY=AKIAIOSFODNN7EXAMPLE" \
-e "MINIO_SECRET_KEY=wJalrXUtnFEMI/K7MDENG/bPxRfiCYEXAMPLEKEY" \
-v /mnt/data:/data \
-v /mnt/config:/root/.minio \
minio/minio server /data


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
	


	
