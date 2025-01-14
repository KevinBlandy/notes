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
	
	# 修改CA
		* acme.sh 脚本默认 CA 服务器是 ZeroSSL，有时可能会导致获取证书的时候一直出现：Pending，The CA is processing your order，please just wait.
		* 只需要把 CA 服务器改成 Lets Encrypt 即可，虽然更改以后还是有概率出现 pending，但基本 2-3 次即可成功

		./acme.sh --set-default-ca --server letsencrypt
		./acme.sh --set-default-ca --server zerossl
	
	# 修改邮箱
		* Lets Encrypt 不能使用默认的 my@example.com 邮箱
		* 编辑文件 account.conf ，修改里面的 ACCOUNT_EMAIL 配置项


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
				fullchain.cer		// 全链证书，有些 Nginx 需要配置这个
				springboot.io.cer	// 证书
				springboot.io.key	// 私钥
	
		4. 续签
		
			./acme.sh --renew --force -d *.springboot.io --keylength 2048 --yes-I-know-dns-manual-mode-enough-go-ahead-please

			
			* --force 参数的目的是强行续签
			* 同样，需要再次添加新的 DNS 记录
		
		
		
	# 文件验证
		./acme.sh --issue -d app.springboot.io -d springboot.io --webroot /root/app/public/

			--webroot
				* 指的是域名服务的根目录。
				* acme.sh 会全自动的生成验证文件，并放到网站的根目录，验证完成后会聪明的删除验证文件，整个过程没有任何副作用。






