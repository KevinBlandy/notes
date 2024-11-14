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
   -e "MINIO_ROOT_PASSWORD='123456'" \
   quay.io/minio/minio server /data --console-address ":9090"

* ��Դ����: http://host:9000/ 
* �������: http://host:9090/ - admin - '123456'



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
	


	
--------------------
Mino - Nginx ����
--------------------
	# һ��ʹ�� Nginx ��Ϊ���ش��� Minio ��Դ

	# ������Դ��Ŀ¼(/)�ᱻ�ض��򵽹������̨������

		* �޸���Դ�������ã�������ʸ�Ŀ¼����Ӧ 403
			server {
				listen 80;
				server_name your_domain_or_ip;

				location / {
					# �������·���Ƿ�Ϊ��Ŀ¼
					if ($request_uri = /) {
						return 404;
					}

					# ������������ MinIO ����
					proxy_pass http://localhost:9000;
					proxy_set_header Host $host;
					proxy_set_header X-Real-IP $remote_addr;
					proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
					proxy_set_header X-Forwarded-Proto $scheme;
				}
			}
		
		* Ҳ���Ե����� Server ���ÿ������ã���Ա���֮�����壩
		
			server {
				// .. ��������
		        location = / {
					return 403;
				}
				// .. ��������
				

				// ��Դ�������
				location / {
					proxy_pass http://localhost:9000;
					proxy_set_header Host $host;
					proxy_set_header X-Real-IP $remote_addr;
					proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
					proxy_set_header X-Forwarded-Proto $scheme;
				}
		}