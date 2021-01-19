-------------------------
rsa模块					 |
-------------------------
	# 创建密钥对
		(publicKey, privateKey) rsa.newkeys(length)
			* length 表示长度
		
	# 把key序列化为证书格式的文本
		publicKey.save_pkcs1().decode()
		privateKey.save_pkcs1().decode()

		* 其实就是序列化为字符串
			-----BEGIN RSA PUBLIC KEY-----
			MIIBCgKCAQEA3TQgbv7yA2tmp6Cxp2VO9dz8ByfYSZWVhtpc1kKYwTljjaZv2U4e
			...
			-----END RSA PUBLIC KEY-----

			-----BEGIN RSA PRIVATE KEY-----
			MIIEqgIBAAKCAQEA3TQgbv7yA2tmp6Cxp2VO9dz8ByfYSZWVhtpc1kKYwTljjaZv
			2U4eKCc4k33z6euoWwEyn5eYYfSC+9I7AJLwJXq7ABy+o/fBVXZR/WsepuX506Ew
			...
			-----END RSA PRIVATE KEY-----
	
	# 导入密钥
		* 导入文本格式的公钥和密钥(格式见上)
		
		rsa.PublicKey.load_pkcs1(publicKeyStr)
		rsa.PrivateKey.load_pkcs1(privateKeyStr)
	
	# 私钥签名,公钥验签
		signature = rsa.sign(content, privateKey, 'SHA-1')	# 使用私钥计算出前面,算法使用 SHA-1
		rsa.verify(content, signature, publicKey)				# 使用公钥对签名进行验证
	
	# 公钥加密,私钥解密
		import rsa
		publicKey, privateKey = rsa.newkeys(512)                        # 初始化密钥对
		result = rsa.encrypt("你好啊".encode('UTF_8'), publicKey)        # 公钥加密,计算出密文
		content = rsa.decrypt(result, privateKey);                      # 私钥根据密文解密出原始数据
		print(content.decode('UTF_8'))

	# 私钥加密公钥解密
		* 虽然rsa算法理论上支持对称的公钥加密私钥解密/私钥加密公钥解密,但大部分平台的rsa api都设计成只提供public key encrypt/ private key decrypt的接口
		* 这是由于私钥加密会带来私钥泄露的风险,一般私钥加密过程只用于签名sign
		* 因为sign的过程是加密之前对消息进行hash,然后der,然后加密,验证的过程是逆向的,对比解密和der解码之后的hash做对比,因此不会泄露private key

