
	# 参考
		https://blog.csdn.net/xu_0705/article/details/34435445

	# 创建配置文件
		openssl.cnf

	
	# 生成RSA私钥
		openssl genrsa -out ca_root.key 2048
			* -out 置顶输出文件,不指定的话,输出到屏幕
			*  2048 表示RSA密钥长度,默认 2048
	
	# 生成证书请求
		openssl req -new -key ca_root.key -out ca_root.csr -md5
			* -key 指定RSA私钥文件
			* -out  生成的证书文件
			* -config 指定配置文件
			* -md5 使用的hash算法,默认使用的是sha1
		
		* 需要交互输入
			Contry Name:cn
				* 国别,用小写字母
			State Of Province Name:重庆
				* 省份
			Locality Name:重庆
				* 城市
			Organization Name:Javaweb
				* 企业名称
			Organizational Unit Name:Javaweb
				* 单位名称
			Common Name:javaweb.io
				* 域名
			Email Address:747692844@qq.com
				* 邮件地址
			
			* 下列选项可以不用考虑,留空.回车跳过即可

			A challenge password:
				* 密码
			An Optional Company name:
				* 可选的公司名称

		* 直接回车将选择使用默认值,输入点"."将表示该信息项留空
	

	# 签发证书
		openssl req -x509 -key ca_root.key -in ca_root.csr -out ca1.crt -days 365
			* -x509 表示需要自签署证书
			* -key 指定RSA私钥
			* -in 证书请求文件
			* -out 签发的证书文件
			* -days 过期时间
		
		* "-x509" 可以配合 "-new"或"-newkey"使用,这样的话,不需要指定请求证书文件(-in 选项)
		* "-x509"选项后,"-new"或"-newkey"将表示创建一个证书文件,而不是一个证书请求文件
		* 它会在签署过程中自动创建请求证书文件,但是需要交互式输入申请者信息
			openssl req -new -x509 -key ca_root.key -out ca1.crt -days 365

------------------------
签发下级证书			|
------------------------
	# 生成自己的私钥
		openssl genrsa -aes128 -out myprivate.key 2048
	
	# 生成证书请求文件
		openssl  req -new -key  myprivate.key  -out MyCaReq.csr -config  D:\OpensslInstall\openssl.cnf
	
	# 根证书签发下级证书
		openssl x509 -req -in MyCaReq.csr  -out MyCa.crt   -signkey myprivate.key  -CA CARoot.crt -CAkey rootca.key -CAcreateserial  -days 990

------------------------
其他操作				|
------------------------
	# 从证书提取出公钥
		openssl req -in ca_root.csr -pubkey -noout
			* -in 指定证书文件
			* -pubkey 指定提取公钥
			* -noout 输出到屏幕
			* -out 指定公钥输出的文件
	
	# 验证请求文件的数字签名,这样可以验证出证书请求文件是否被篡改过
		openssl req -verify -in ca_root.csr
			 -in 指定证书文件
	
