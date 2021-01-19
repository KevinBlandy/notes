-----------------------------
Poste						 |
-----------------------------
	# 官网
		https://poste.io
	
	# 参考文档
		https://www.wumvp.com/2019/03/24/poste.html
	
	# 域名解析(springcloud.io)

mail		A		[邮件服务器ip]

smtp		CNAME	mail.springcloud.io	 
pop			CNAME	mail.springcloud.io	 
imap		CNAME	mail.springcloud.io

@			MX		mail.springcloud.io
@			TXT		"v=spf1 mx ~all"

s20191223631._domainkey TXT "DKIM key
	* 该记录需要填写，通过控制台生成的key
	* 先在控制台生成后，再填写

_dmarc.springcloud.io	TXT "v=DMARC1; p=none; rua=mailto:dmarc-reports@springcloud.io"
	* 该记录，不需要添加




	# Docker 安装
docker run \
-p 25:25 \
-p 80:80 \
-p 110:110 \
-p 143:143 \
-p 443:443 \
-p 587:587 \
-p 993:993 \
-p 995:995 \
-v /etc/localtime:/etc/localtime:ro \
-v /srv/poste/data:/data \
--name "PosteServer" \
-h "mail.springcloud.io" \
-t analogic/poste.io
	
	# 设置admin邮箱 & 密码
		admin@springcloud.io
	
	# 创建密钥
		Virtual domains -> springcloud.io -> create new key
		
		* 把 DKIM key 解析到域名

	# SSL设置
		System settings -> TLS Certificate -> Let's Encrypt certificate

		Enable: √
		Common name: mail.springcloud.io
		Alternativenames: 
			smtp.springcloud.io
			pop.springcloud.io
			imap.springcloud.io


	# 页面
		* 总管理后台
			https://mail.undefined.design/admin/ 
		
		* 邮件管理后台
			https://mail.undefined.design/webmail
			
	
	# 开启邮件加密
		设置 -> 选项 -> 加密 -> 除了[默认附加我的公共 PGP 密钥]选项以外, 都勾上
	
	# 设置发件人身份
		设置 -> 发件人身份 -> 显示名称			=> 邮件列表中, 显示的名称
		设置 -> 发件人身份 -> 组织				=>


	# 日志
		* 查看日志
			docker logs -f PosteServer
		
		* 日志目录
			data/logs
