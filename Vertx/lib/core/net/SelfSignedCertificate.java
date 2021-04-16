------------------------------------
SelfSignedCertificate
------------------------------------
	# 自签名证书的接口，测试用：  interface SelfSignedCertificate
		
		PemKeyCertOptions keyCertOptions();
		PemTrustOptions trustOptions();
		String privateKeyPath();
		String certificatePath();
		void delete();

		static SelfSignedCertificate create()
		static SelfSignedCertificate create(String fqdn)