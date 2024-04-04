-----------------------------
java ssl ֤������			 |
-----------------------------
	# ֤���ļ�����
		.der .cer
			* �ļ��Ƕ����Ƹ�ʽ,ֻ����֤��,������˽Կ

		.pem
			* һ�����ı���ʽ,�ɱ���֤��,�ɱ���˽Կ
			* �� -----BEGIN... ��ͷ,�� -----END... ��β,�м�������� BASE64 ����
			* ���ָ�ʽ���Ա���֤���˽Կ,��ʱҲ��PEM ��ʽ��˽Կ�ĺ�׺��Ϊ .key ������֤����˽Կ
		
		.crt
			* �п����� pem �����ʽ,Ҳ�п����� der �����ʽ
		
		.pfx .P12
			* �����Ƹ�ʽ,ͬʱ����֤���˽Կ,һ�������뱣��
			* pfx��������õ�

		.jks
			* �����Ƹ�ʽ,ͬʱ����֤���˽Կ,һ�������뱣��
			*  JAVA ��ר����ʽ(Java Key Storage)
		
	
	# ������˽Կ��֤���ļ���ʽ
		JKS
			* Java֧�ֵ�֤��˽Կ��ʽ
			* java�õĴ洢��Կ������,����ͬʱ����n����Կ��˽Կ,��׺һ����.jks����.keystore��.truststore��
		JCEKS
		PKCS12 
		BKS
		UBER
		PKCS12
			* �����˰���˽Կ�빫Կ֤��(public key certificate)���ļ���ʽ,��ҵ��׼
			* pfx ��ʵ������PKCS#12

-----------------------------
java ssl					 |
-----------------------------
	# keytool ָ��һ��
		 -certreq            ����֤������
		 -changealias        ������Ŀ�ı���
		 -delete             ɾ����Ŀ
		 -exportcert         ����֤��
		 -genkeypair         ������Կ��
		 -genseckey          ������Կ
		 -gencert            ����֤����������֤��
		 -importcert         ����֤���֤����
		 -importpass         �������
		 -importkeystore     ��������Կ�⵼��һ����������Ŀ
		 -keypasswd          ������Ŀ����Կ����
		 -list               �г���Կ���е���Ŀ
		 -printcert          ��ӡ֤������
		 -printcertreq       ��ӡ֤�����������
		 -printcrl           ��ӡ CRL �ļ�������
		 -storepasswd        ������Կ��Ĵ洢����
	
	# ������Կ�Ե�keystore
		keytool -genkey -deststoretype [pkcs12] -alias [alias] -validity [100] -keystore [server.keystore] -keyalg [RSA] -storepass [����]
			-genkey
				* ����֤���ָ��
			-deststoretype	
				* ָ��֤������,һ��̶�ֵ:pkcs12
			-alias
				* ָ��֤����keystore�еı���
			-validity
				* ��Ч��,��λ����
			-keystore
				* ָ�� keystore ����(���keystore������,���½�)
			-keyalg
				* ָ��֤��ķǶԳƼ����㷨,һ��̶�:RSA
			-keysize
				* ָ�������㷨����Կ����
			-storepass
				* keystore������
			-keypass
				* ֤�������
				* ֻ��JKS���͵�֤���֧�ָ�ѡ��,pkcs12 ��֧��,�����
			-dname
				* ���ò����Ŀ�ݷ�ʽ
					-dname "CN=Web Server,OU=Unit,O=Organization,L=City,S=State,C=US"
			
	# ��֤���е�����Կ
		keytool -export -alias [alias] -file [name.cer] -keystore [name.keystore] -storepass [����]

		keytool -export -alias TrendBox -file TrendBox.cer -keystore app.keystore -storepass 120397

			-export
				* ������Կ��ָ��
			-alias
				* keystore ��֤��ı���
			-file
				* ��Կ֤����ļ�����
			-keystore
				* keystore
			-storepass
				* keystored������
	
	# ���빫Կ�� keystore
		keytool -import -file [name.cer] -alias [alias] -keystore [name.keystore] -storepass [����]

			-import
				*  ����ָ��
			-file
				* ��Կ֤���ļ�
			-alias
				* ���� ��֤����Ŀ��keystore�еı���(Ĭ����:mykey),���ܳ�ͻ
			-keystore
				* ���뵽��Ŀ�� keystore(���keystore������,���½�)
			-storepass
				* Ŀ�� keystore������
			
	
	# ��keystoreɾ����Կ
		keytool -delete -alias [alias] -keystore [name.keystore] -storepass [����]
			-delete
				* ɾ��ָ��
			-alias
				* ��Կ�ı���
			-keystore
				* Ŀ��keystore�ļ�
			-storepass
				* keystore������
				
	# �鿴keystore�е�֤����Ŀ
		keytool -list -v -keystore [name.keystore] -storepass [����]

			-list
				* �г�ָ��
			-v
				* ��ʾ����
			-keystore
				* ָ����keystore
			-storepass
				* keystore������
	
	# ����֤������
		keytool -certreq -alias [alias] -file [name.csr] -keystore [name.keystore] -storepass [����]
		
			-certreq
				* ��������ָ��
			-alias
				* ֤����keystore�еı���
			-file
				* ���ɵ������ļ�����
			-keystore
				* ֤�����ڵ�keystore
			-storepass
				* keystore������
			
	
	# ����֤����������֤��
		keytool -gencert -alias [alias] -infile [name.csr] -outfile [name.cer] -keystore [name.keystore] -storepass [����]
			
			-gencert
				* ��������֤��ָ��
			-alias
				* ����ǩ����֤���֤�����(root֤�����)
			-infile
				* �����ļ�
			-outfile
				* ���ɵ�֤������
			-keystore
				* ����ǩ����֤���֤�����ڵ�keystore(root֤��)
			-storepass
				* ����ǩ����֤���֤�����ڵ�keystore������(root֤��)
	
	# ��ӡ֤��
		keytool -printcert -rfc -file [name.cer]

			-printcert 
				* ��ӡָ��
			-rfc 
			-file 
				* ֤���ļ�
	
	# ��������Կ�⵼��һ����������Ŀ
		keytool -importkeystore -v  -srckeystore [name.p12] -srcstoretype [pkcs12] -srcstorepass [����] -destkeystore [name.keystore] -deststoretype [pkcs12] -deststorepass [����] 
			
			-importkeystore
				* ����ָ��
			-v
			-srckeystore
			-srcstoretype
			-srcstorepass
				* Դ��Կ���ļ�,����(PCKS12),����
			-destkeystore
			-deststoretype
			-deststorepass
				* Ŀ����Կ���ļ�,����(PCKS12),����
		

------------------------------------------
keytool����CA��֤���Լ��䷢����֤��		  |
------------------------------------------
	# CREATE CA
		keytool -genkey -deststoretype pkcs12 -alias CA_ROOT -validity 3500 -keystore CA_ROOT.keystore -keyalg RSA -keysize 2048 -storepass 123456
		keytool -export -alias CA_ROOT -file CA_ROOT.cer -keystore CA_ROOT.keystore -storepass 123456

	# CLIENT
		keytool -genkey -deststoretype pkcs12 -alias client -validity 365 -keystore client.keystore -keyalg RSA -keysize 2048 -storepass 123456
		keytool -certreq -alias client -file client.csr -keystore client.keystore -storepass 123456

	# CLIENT SIGN
		keytool -gencert -alias CA_ROOT -infile client.csr -outfile client.cer -keystore CA_ROOT.keystore -storepass 123456

	# SERVER
		keytool -genkey -deststoretype pkcs12 -alias server -validity 365 -keystore server.keystore -keyalg RSA -keysize 2048 -storepass 123456
		keytool -certreq -alias server -file server.csr -keystore server.keystore -storepass 123456

	# SERVER SIGN
		keytool -gencert -alias CA_ROOT -infile server.csr -outfile server.cer -keystore CA_ROOT.keystore -storepass 123456

	# INSTALL CLIENT
		keytool -import -file CA_ROOT.cer -alias ca -keystore client.keystore -storepass 123456
		keytool -import -file ca_client.cer -alias client -keystore client.keystore -storepass 123456
		keytool -list -v -keystore client.keystore -storepass 123456

	# INSTALL SERVER
		keytool -import -file CA_ROOT.cer -alias ca -keystore server.keystore -storepass 123456
		keytool -import -file ca_server.cer -alias server -keystore server.keystore -storepass 123456
		keytool -list -v -keystore server.keystore -storepass 123456

	
