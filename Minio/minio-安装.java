--------------------
Mino - docker��װ
--------------------

# ������ home Ŀ¼�´�����ԴĿ¼
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
Mino - ������
--------------------
	# ����
		https://dl.min.io/server/minio/release/linux-amd64/minio
	
	# ִ��
		chmod +x minio
		./minio server /data
		
		* ����ʾ����, ��Ҫ�ֶ�ȥ�����ļ��޸��û���������
			Detected default credentials 'minioadmin:minioadmin', please change the credentials immediately using 'MINIO_ACCESS_KEY' and 'MINIO_SECRET_KEY'
		
		* Ĭ�ϵ�ƾ֤��Ϣ
			minioadmin:minioadmin
	


	
