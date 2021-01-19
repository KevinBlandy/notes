------------------------
postfix					|
------------------------
	# 需要开放的端口
		25		SMTP, 发送邮件
		110		pop3, 接收邮件
		143		imap, 接收邮件

		465		SMTP, 发送邮件(ssk)
		995		pop3, 接收邮件(ssl)
		993		imap, 接收邮件(ssl)
	
		* 只用pop3服务, 那么只需要打开110和25端口即可


			firewall-cmd --add-port=25/tcp --permanent
			firewall-cmd –-add-port=110/tcp --permanent
			firewall-cmd --add-port=143/tcp --permanent
			firewall-cmd –-add-port=465/tcp --permanent
			firewall-cmd --add-port=995/tcp --permanent
			firewall-cmd –-add-port=993/tcp --permanent
			firewall-cmd --reload
	
	# 移除 sendmail
		* 查找
			rpm -e sendmail 
		
		* 移除
			yum remove sendmail
	
	# 移除旧版 postfix
		yum remove postfix
	
	# 安装 postfix
		yum install postfix -y
		yum install crontabs -y
		yum install cyrus-sasl* -y
			* 提供stmp虚拟账户和密码服务
		
	# 查看mta(默认邮件传输代理)
		alternatives --display mta
			mta - status is auto.
			 link currently points to /usr/sbin/sendmail.postfix
			/usr/sbin/sendmail.postfix - priority 30
			 slave mta-mailq: /usr/bin/mailq.postfix
			 slave mta-newaliases: /usr/bin/newaliases.postfix
			 slave mta-pam: /etc/pam.d/smtp.postfix
			 slave mta-rmail: /usr/bin/rmail.postfix
			 slave mta-sendmail: /usr/lib/sendmail.postfix
			 slave mta-mailqman: /usr/share/man/man1/mailq.postfix.1.gz
			 slave mta-newaliasesman: /usr/share/man/man1/newaliases.postfix.1.gz
			 slave mta-sendmailman: /usr/share/man/man1/sendmail.postfix.1.gz
			 slave mta-aliasesman: /usr/share/man/man5/aliases.postfix.5.gz
			Current `best' version is /usr/sbin/sendmail.postfix.

			* 如果没有这句话: Current `best' version is /usr/sbin/sendmail.postfix. ' 需要重新执行
				/usr/sbin/alternatives --set mta /usr/sbin/sendmail.postfix
			
			* 再次查看mta
				alternatives --display mta
	
		
	# 设置开机启动
		systemctl enable postfix.service
	
	
	# 域名解析配置(添加A记录和mx记录), 假设域名:undefined.design
		A记录
			记录  :mail				(mail.undefined.design)
			记录值:服务器Ip

		MX记录
			记录  :@				(@.undefined.design)
			记录值:mail.undefined.design
	
	# 修改本机记录
		hostnamectl  set-hostname   mail.undefined.design
	
	# 修改postfix配置文件修改(/etc/postfix/main.cf)
075行:myhostname = mail.undefined.design 
083行:mydomain = undefined.design 
099行:myorigin = $mydomain 
116行:inet_interfaces = all 
119行:inet_protocols = ipv4 
164行:mydestination = $myhostname, localhost.$mydomain, localhost, $mydomain 
264行:mynetworks = 127.0.0.0/8
419行:home_mailbox = Maildir/ 
571行:smtpd_banner = $myhostname ESMTP 

# 添加到最后 
message_size_limit = 10485760 
mailbox_size_limit = 1073741824 
smtpd_sasl_type = dovecot 
smtpd_sasl_path = private/auth 
smtpd_sasl_auth_enable = yes
smtpd_sasl_security_options = noanonymous 
smtpd_sasl_local_domain = $myhostname 
smtpd_recipient_restrictions = permit_mynetworks,permit_auth_destination,permit_sasl_authenticated,reject 
	
	# 重启
		systemctl restart postfix
	

	# 安装dovecot
		yum install dovecot -y
	
	# 编辑dovecot配置文件(/etc/dovecot/dovecot.conf)
30行:listen = *
	* 允许ipv4
48行:login_trusted_networks = 192.168.10.0/24
	* 限制可以登录的ip, 如果允许任意地方登录, 则不用修改
	
	# 编辑文件10-auth.conf (/etc/dovecot/conf.d/10-auth.conf)
 10行:disable_plaintext_auth = no 
100行:auth_mechanisms = plain login 
	
	# 编辑文件10-mail.conf(/etc/dovecot/conf.d/10-mail.conf)	
30行:mail_location = maildir:~/Maildir

	# 编辑文件10-master.conf(/etc/dovecot/conf.d/10-master.conf)
		* 添加Postfix smtp验证 (88-90行)
unix_listener /var/spool/postfix/private/auth { 
	mode = 0666 
	user = postfix
	group = postfix
}
	
	# 编辑文件10-ssl.conf(/etc/dovecot/conf.d/10-ssl.conf)
		* 如果使用了SSL, 不用修改
8行:ssl = no
	
	# 重启动dovecot并添加到开机自启
		systemctl restart dovecot
		systemctl enable dovecot
	
	# 创建用户
		* 邮件的用户是和系统用户一致的, 也就是说系统用户可以当做邮件用户
		useradd admin
		passwd admin
	
	
	# Foxmail 登录
		接收服务器类型: POP3
		邮件账户:
		密码:
		POP服务器: mail.undefined.design
		SMTP服务器: mail.undefined.design
		[] 如果服务器支持, 就使用start tls 加密
			* 如果开启了SSL, 就勾上

	
	# 日志
		/var/log/maillog
		
		* 发送成功的日志
			postfix/smtp[17082]: C6CD339B70: to=<747692844@qq.com>, relay=mx3.qq.com[203.205.219.57]:25, delay=2.4, delays=0.28/0.01/0.06/2, dsn=2.0.0, status=sent (250 Ok: queued as )
			* 如果 status=bounced ,那么就意味着目标邮件服务器将我们这个服务器IP设为恶意IP, 不接收这个IP发送的邮件
			* 也就意味着如果我们要发邮件到目标邮箱的话, 就需要更换服务器ip

------------------------
postfix	- 维护			|
------------------------
	du -sh /var/spool/postfix/*							*/
		* 查看目录下的defer和deferred的目录大小
		* 通过 postqueue –p 来查看队列的邮件, 通过 postsuper -d ALL 删除所有的队列邮件
	

------------------------
屏蔽刷postfix的 IP脚本	|
------------------------
#!/bin/bash

LOGFILE="/var/log/maillog"

#统计maillog中authentication failure的IP个数与IP
grep "authentication failure" $LOGFILE|awk '{print $7}'|grep -E -o "[0-9]+\.[0-9]+\.[0-9]+\.[0-9]+"|sort|uniq -c > af_iplist.txt

#取出AF出现大于300次时的IP
awk '$1>300 {print $2}' af_iplist.txt > block_ip_list.txt

#大于300次AF的IP添加到iptables中
cat block_ip_list.txt|while read line
do
/sbin/iptables -nL | grep $line
if [ $? != 0 ]
then
    iptables -I INPUT -s $line -j DROP
fi
done