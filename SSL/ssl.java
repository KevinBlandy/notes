-----------------------------
java ssl 证书类型			 |
-----------------------------
	# 证书文件类型
		.der .cer
			* 文件是二进制格式,只保存证书,不保存私钥

		.pem
			* 一般是文本格式,可保存证书,可保存私钥
			* 以 -----BEGIN... 开头,以 -----END... 结尾,中间的内容是 BASE64 编码
			* 这种格式可以保存证书和私钥,有时也把PEM 格式的私钥的后缀改为 .key 以区别证书与私钥
		
		.crt
			* 有可能是 pem 编码格式,也有可能是 der 编码格式
		
		.pfx .P12
			* 二进制格式,同时包含证书和私钥,一般有密码保护
			* pfx是浏览器用的

		.jks
			* 二进制格式,同时包含证书和私钥,一般有密码保护
			*  JAVA 的专属格式(Java Key Storage)
		
	
	# 包含了私钥的证书文件格式
		JKS
			* Java支持的证书私钥格式
			* java用的存储密钥的容器,可以同时容纳n个公钥或私钥,后缀一般是.jks或者.keystore或.truststore等
		JCEKS
		PKCS12 
		BKS
		UBER
		PKCS12
			* 定义了包含私钥与公钥证书(public key certificate)的文件格式,行业标准
			* pfx 就实现了了PKCS#12

-----------------------------
java ssl					 |
-----------------------------
	# keytool 指令一览
		 -certreq            生成证书请求
		 -changealias        更改条目的别名
		 -delete             删除条目
		 -exportcert         导出证书
		 -genkeypair         生成密钥对
		 -genseckey          生成密钥
		 -gencert            根据证书请求生成证书
		 -importcert         导入证书或证书链
		 -importpass         导入口令
		 -importkeystore     从其他密钥库导入一个或所有条目
		 -keypasswd          更改条目的密钥口令
		 -list               列出密钥库中的条目
		 -printcert          打印证书内容
		 -printcertreq       打印证书请求的内容
		 -printcrl           打印 CRL 文件的内容
		 -storepasswd        更改密钥库的存储口令
	
	# 生成密钥对到keystore
		keytool -genkey -deststoretype [pkcs12] -alias [alias] -validity [100] -keystore [server.keystore] -keyalg [RSA] -storepass [密码]
			-genkey
				* 生成证书的指令
			-deststoretype	
				* 指定证书类型,一般固定值:pkcs12
			-alias
				* 指定证书在keystore中的别名
			-validity
				* 有效期,单位是天
			-keystore
				* 指定 keystore 名称(如果keystore不存在,会新建)
			-keyalg
				* 指定证书的非对称加密算法,一般固定:RSA
			-keysize
				* 指定加密算法的密钥长度
			-storepass
				* keystore的密码
			-keypass
				* 证书的密码
				* 只有JKS类型的证书才支持该选项,pkcs12 不支持,会忽略
			-dname
				* 设置参数的快捷方式
					-dname "CN=Web Server,OU=Unit,O=Organization,L=City,S=State,C=US"
			
	# 从证书中导出公钥
		keytool -export -alias [alias] -file [name.cer] -keystore [name.keystore] -storepass [密码]

			-export
				* 导出公钥的指令
			-alias
				* keystore 中证书的别名
			-file
				* 公钥证书的文件名称
			-keystore
				* keystore
			-storepass
				* keystored的密码
	
	# 导入公钥到 keystore
		keytool -import -file [name.cer] -alias [alias] -keystore [name.keystore] -storepass [密码]

			-import
				*  导入指令
			-file
				* 公钥证书文件
			-alias
				* 设置 该证书在目标keystore中的别名(默认名:mykey),不能冲突
			-keystore
				* 导入到的目标 keystore(如果keystore不存在,会新建)
			-storepass
				* 目标 keystore的密码
			
	
	# 从keystore删除公钥
		keytool -delete -alias [alias] -keystore [name.keystore] -storepass [密码]
			-delete
				* 删除指令
			-alias
				* 公钥的别名
			-keystore
				* 目标keystore文件
			-storepass
				* keystore的密码
				
	# 查看keystore中的证书条目
		keytool -list -v -keystore [name.keystore] -storepass [密码]

			-list
				* 列出指令
			-v
				* 显示详情
			-keystore
				* 指定的keystore
			-storepass
				* keystore的密码
	
	# 生成证书请求
		keytool -certreq -alias [alias] -file [name.csr] -keystore [name.keystore] -storepass [密码]
		
			-certreq
				* 生成请求指令
			-alias
				* 证书在keystore中的别名
			-file
				* 生成的请求文件名称
			-keystore
				* 证书所在的keystore
			-storepass
				* keystore的密码
			
	
	# 根据证书请求生成证书
		keytool -gencert -alias [alias] -infile [name.csr] -outfile [name.cer] -keystore [name.keystore] -storepass [密码]
			
			-gencert
				* 生成请求证书指令
			-alias
				* 用于签发的证书的证书别名(root证书别名)
			-infile
				* 请求文件
			-outfile
				* 生成的证书名称
			-keystore
				* 用于签发的证书的证书所在的keystore(root证书)
			-storepass
				* 用于签发的证书的证书所在的keystore的密码(root证书)
	
	# 打印证书
		keytool -printcert -rfc -file [name.cer]

			-printcert 
				* 打印指令
			-rfc 
			-file 
				* 证书文件
	
	# 从其他密钥库导入一个或所有条目
		keytool -importkeystore -v  -srckeystore [name.p12] -srcstoretype [pkcs12] -srcstorepass [密码] -destkeystore [name.keystore] -deststoretype [pkcs12] -deststorepass [密码] 
			
			-importkeystore
				* 导入指令
			-v
			-srckeystore
			-srcstoretype
			-srcstorepass
				* 源密钥库文件,类型(PCKS12),密码
			-destkeystore
			-deststoretype
			-deststorepass
				* 目标密钥库文件,类型(PCKS12),密码
		

------------------------------------------
keytool制作CA根证书以及颁发二级证书		  |
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

	
