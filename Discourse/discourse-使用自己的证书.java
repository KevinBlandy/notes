---------------------------------
使用自己的证书
---------------------------------
	*  默认使用的Let’s Encrypt 针对一个域名只会在一定时间内签发 5 次，如果你超过了签发的次数，你需要 5 天后才能再次申请。
		[Sun 04 Oct 2020 04:52:58 AM UTC] Create new order error. Le_OrderFinalize not found. {
		"Error creating new order :: too many certificates already issued for exact set of domains: www.ossez.com: see https://letsencrypt.org/docs/rate-limits/"
	

	1. 把证书文件上传到服务器目录
		* /var/discourse/shared/standalone/ssl
			文件名	类型		放置路径
			ssl.key	key 文件	/var/discourse/shared/standalone/ssl/ssl.key
			ss.crt	crt 文件	/var/discourse/shared/standalone/ssl/ssl.crt
			
		*  文件名称必须一致
	
	2. 修改 app.yml
		vi /var/discourse/containers/app.yml

		* 取消注释 (如果已经被注释的话)
			- "templates/web.ssl.template.yml" 
		
		* 添加注释
			- "templates/web.letsencrypt.ssl.template.yml"
	
		* 上面的这个配置这样注释的意图就是，启用 ssl 的配置，但是撤销使用 letsencrypt 的 ssl 签名。
		
	3. 开放端口
		* 确定expose配置了 80 和 443
	
	4. 重新构建app
		./launcher rebuild app
	

		