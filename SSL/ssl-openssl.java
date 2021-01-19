-----------------------------
openssl						 |
-----------------------------
	# 官网
		https://www.openssl.org



	# 把pem证书转换为 p12证书
		openssl pkcs12 -export -in [name.cer] -inkey [name.key] -out [name.p12]
			-in
				* 证书

			-inkey
				* 私钥
			
			-out
				* 生成的p12证书文件

	# 把 pkcs12 证书转换为 pem证书
		* 提取证书文件
			openssl pkcs12 -in [name.p12] -out [name.pem] -nokeys -clcerts

				-in
					* p12密钥库文件
				-out
					* 输出的证书文件
				-nokeys
					* 不输出密钥
				-clcerts

		* 提取私钥文件
			openssl pkcs12 -in [name.p12] -out [name.key] -nocerts -nodes

				-in
					* p12密钥库文件
				-out
					* 输出的密钥文件
				-nocerts
					* 不输出证书
				-nodes
					* 不对私钥文件进行加密

