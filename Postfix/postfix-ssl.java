# 进入目录
	cd /etc/pki/tls/certs

# 生成key
	 make server.key
	
	* 需要输入密码

# 生成RSA key
	openssl rsa -in server.key -out server.key

	 * 需要输入 server.key 设置的密码

# 生成ssr
	 make server.csr

	 Country Name (2 letter code) [XX]:
		* 设置国别

	State or Province Name (full name) []:
		* 洲或省的名称
	
	Locality Name (eg, city) [Default City]:
		* 城市
	
	Organization Name (eg, company) [Default Company Ltd]:
		* 组织名称
	
	Organizational Unit Name (eg, section) []:
		* 组织单位
	
	Common Name (eg, your name or your server's hostname) []:
		* 域名地址
	
	Email Address []:
		* 电子邮件
	
	* 所有设置全部空白, 一顿回车就完事儿了
	

# 生成证书
	openssl x509 -in server.csr -out server.crt -req -signkey server.key -days 3650
	
	Signature ok
	subject=/C=cn/ST=cq/L=cq/O=undefined/OU=undefined/CN=undefined.design
	Getting Private key
	Enter pass phrase for server.key:
		* 如果设置了密码, 这里需要输入

# 修改证书的权限
	chmod 400 server.* 


# vim /etc/postfix/main.cf
	* 添加配置到最后

smtpd_use_tls = yes
smtpd_tls_cert_file = /etc/pki/tls/certs/server.crt
smtpd_tls_key_file = /etc/pki/tls/certs/server.key
smtp_tls_session_cache_database = btree:${data_directory}/smtp_scache
smtpd_tls_session_cache_database = btree:${data_directory}/smtpd_scache
smtpd_tls_loglevel = 0
smtpd_tls_auth_only = yes

# vim /etc/postfix/master.cf
	* 取消该配置的注释(28行)
	* 千万注意, -o 前面有俩空格

	  -o smtpd_tls_wrappermode=yes

# vim /etc/dovecot/conf.d/10-ssl.conf 
	* 8行, 设置为yes
		ssl = yes

	* 14,16行: 指定证书
		ssl_cert = </etc/pki/tls/certs/server.crt
		ssl_key = </etc/pki/tls/certs/server.key
	
	* 如果设置了密码,还需要在21行配置密码
		ssl_key_password = 123456

# 开发SSL端口
	firewall-cmd --add-port={465/tcp,995/tcp,993/tcp} --permanent
	firewall-cmd --reload 

# 重启
	systemctl restart postfix
	systemctl restart dovecot