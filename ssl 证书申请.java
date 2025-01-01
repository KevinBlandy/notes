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
	
	# DNS 验证
	
		1. 创建证书请求
			./acme.sh --issue --dns -d *.springboot.io --yes-I-know-dns-manual-mode-enough-go-ahead-please
		2. 等待添加 DNS 记录（TXT）
		3. 签发证书
			./acme.sh --renew -d *.springboot.io --yes-I-know-dns-manual-mode-enough-go-ahead-please
	
			* 证书目录名称就是域名的名称
				fullchain.cer		// 证书
				springboot.io.key	// 私钥
	
		4. 续签
		
			./acme.sh --renew --force -d *.springboot.io --yes-I-know-dns-manual-mode-enough-go-ahead-please

			
			* --force 参数的目的是强行续签
			* 同样，需要再次添加新的 DNS 记录
		
		
		
		