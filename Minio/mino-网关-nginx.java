---------------------
Nginx��Ϊ����
---------------------
	server {
		listen 80;
		server_name example.com;

        location = / {
			# ��ֹ���ʸ�Ŀ¼
			return 403;
		}

		location / {
			proxy_set_header Host $http_host;
			proxy_pass http://localhost:9000;
		}
	}

	server {
		listen 80;
		server_name bucket1.example.com;
		location / {
			proxy_set_header Host $http_host;
			proxy_pass http://localhost:9000/bucket1/;
		}
	}

	server {
		listen 80;
		server_name bucket2.com;
		location / {
			proxy_set_header Host $http_host;
			proxy_pass http://localhost:9000/bucket2/;
		}
	}

	# ʹ��һ��Server����������̨
	# ʹ��N��Server(����������������������)������bucket
		* ��ҪҪ�ں������ / ��Ȼ�����ܵ��������ض���

	# ��http�������������, �ͻ������������
		client_max_body_size 1000m;
	
