----------------------
https抓包
----------------------
	# 勾选选项
		Tools -> Options -> Https -> Decrypt Https Traffic
			
		
		Ignore Server Certificate error (Unsafe)
		Check For Certificate Revocation
		Protocols
		Skip Decryption For The Follow Hosts

		
	

	

----------------------
https抓包失败处理
----------------------

	# Certificate Error
		* Creation of the root certificate was not successful.

		1. 重置https证书
			Actions ->	Reset All Certificates
		
		2. 生成证书
			Actions ->	Trust Root Certificates
		
		3. 查看证书是否生成OK
			Actions ->	Open Windows Certificate Manager

			* 操作 -> 查找证书 -> 输入: fiddler
			* 查询fiddler的根证书是否生成成功
	

----------------------
解决win7下证书的生成问题
----------------------
	# 进入fiddler的安装目录, 然后执行以下命令
		makecert.exe -r -ss my -n "CN=DO_NOT_TRUST_FiddlerRoot, O=DO_NOT_TRUST, OU=Created by http://www.fiddler2.com" -sky signature -eku 1.3.6.1.5.5.7.3.1 -h 1 -cy authority -a sha1 -m 120 -b 01/01/2020

 

		-m
			* 指定有效期(单位是月)
		-b
			* 证书生效时间

	* 执行完之后 再去重新配置一次 就可以正确安装证书了

	# 下载 FiddlerCertMaker & 执行
		http://www.enhanceie.com/dl/FiddlerCertMaker.exe


----------------------
firefox https抓包
----------------------
	# 谷歌和IE, 都是读取系统的证书, 比较简单
	# Firefox 是管理自己的证书

	# 选项 -> 高级 -> 网络 -> 系统代理/手动指定代理

	# 导入 Fiddler生成的证书
		