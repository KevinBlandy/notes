--------------------------------------
SpringBoot Initializr
--------------------------------------
	# ��ַ
		https://start.spring.io/
		https://github.com/spring-io/start.spring.io
	
	# �Լ���װ
		1. cloen���룺git clone https://github.com/spring-io/start.spring.io
		2. ������룺cd start.spring.io ��ִ��./mvnw clean package -Dmaven.test.skip=true
		3. target �ļ�������jar ��*-exec.jar ����ֱ��java -jar -Dserver.port=8080 target\start-site-exec.jar ����

		* ��Ҫ��װnodejs��node-gyp
			* ��װnodejs
				yum -y install nodejs

				npm install -g n
			
			* ������ָ�������°汾(node�汾���Ϳ��ܻṹ��ʧ��)
				n 16.13.0 stable
				n 14.18.1 stable

			
			* ��װ�������
				npm install -g node-gyp
			
			* ����Ŀ��ܻ�Ҫpython3�Ļ���
				