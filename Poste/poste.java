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
@			TXT		"v=spf1 mx -all"

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
-p 465:465 \
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
			https://mail.springcloud.io/admin/ 
		
		* 邮件管理后台
			https://mail.springcloud.io/webmail
			
	
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

-----------------------------
Go发送
-----------------------------
	import (
		"gopkg.in/gomail.v2"
	)


	func main() {
		// 创建邮件消息，设置编码
		message := gomail.NewMessage(gomail.SetCharset("utf-8"))

		message.SetAddressHeader("From", "noreply@springcloud.io", "SpringBoot中文社区") // 发件人以及发件人名称
		message.SetHeader("To", "747692844@qq.com")                                  // 收件人，可以有多个
		message.SetHeader("Subject", "欢迎注册!")                                        // 标题
		message.SetBody("text/html", "<h1>Hello World</h1>")                         // 邮件内容

		// 创建邮箱链接信息，指定主机端口，账户名，密码
		conn := gomail.NewDialer("smtp.springcloud.io", 465, "noreply@springcloud.io", "123456")

		// 创建连接，并且发送邮件
		if err := conn.DialAndSend(message); err != nil {
			panic(err)
		}
	}



-----------------------------
discourse配置
-----------------------------
env
  DISCOURSE_SMTP_ADDRESS: smtp.springcloud.io
  DISCOURSE_SMTP_PORT: 587
  DISCOURSE_SMTP_USER_NAME: noreply@springcloud.io
  DISCOURSE_SMTP_PASSWORD: "123456"
  DISCOURSE_SMTP_ENABLE_START_TLS: true           # (optional, default true)
  DISCOURSE_SMTP_AUTHENTICATION: login
  DISCOURSE_SMTP_OPENSSL_VERIFY_MODE: none

run:
  - exec: rails r "SiteSetting.notification_email='noreply@springcloud.io'"
