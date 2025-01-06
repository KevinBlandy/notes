-----------------------
acme
-----------------------
	# 脚本地址
		https://github.com/acmesh-official/acme.sh
	
	# 安装

		curl https://get.acme.sh | sh -s email=my@example.com

		或

		wget -O -  https://get.acme.sh | sh -s email=my@example.com
	
	
		* 安装目录

			~/.acme.sh/
	

	这个比较简单，我们在使用acme脚本签发证书时，在末尾加上 --keylength 2048即可
	# DNS 验证
	
		1. 创建证书请求
			./acme.sh --issue --dns -d *.springboot.io --keylength 2048  --yes-I-know-dns-manual-mode-enough-go-ahead-please
			
			* --keylength 指定证书的算法类型，默认是 ec-256

				ec-256	(prime256v1, "ECDSA P-256", 默认的类型)
				ec-384	(secp384r1, "ECDSA P-384")
				ec-521	(secp521r1, "ECDSA P-521", Let's Encrypt 目前还不支持。)
				2048	(RSA2048)
				3072	(RSA3072)
				4096	(RSA4096)


		2. 等待添加 DNS 记录（TXT）
		3. 签发证书
			./acme.sh --renew -d *.springboot.io --keylength 2048 --yes-I-know-dns-manual-mode-enough-go-ahead-please
	
			* 证书目录名称就是域名的名称
				fullchain.cer		// 证书
				springboot.io.key	// 私钥
	
		4. 续签
		
			./acme.sh --renew --force -d *.springboot.io --keylength 2048 --yes-I-know-dns-manual-mode-enough-go-ahead-please

			
			* --force 参数的目的是强行续签
			* 同样，需要再次添加新的 DNS 记录
		
		
		
		