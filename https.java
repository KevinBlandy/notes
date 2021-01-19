https://www.oschina.net/news/94188/acme-v2-and-wildcard-certificate-support-is-live
https://my.oschina.net/kimver/blog/1634575
https://github.com/Neilpang/acme.sh/wiki/%E8%AF%B4%E6%98%8E

## Nginx反向代理http的tomcat
	1,执行
		curl https://get.acme.sh | sh


	2,执行
		source ~/.bashrc

	3,执行
		# 阿里云后台的密钥
		export Ali_Key="1858118"
		export Ali_Secret="1858118"

		# 填写自己的域名
		acme.sh --issue --dns dns_ali -d springboot.io -d *.springboot.io
		
		* acme.sh比certbot的方式更加自动化,省去了手动去域名后台改DNS记录的步骤,而且不用依赖Python
		* 第一次成功之后,acme.sh会记录下App_Key跟App_Secret,并且生成一个定时任务,每天凌晨0：00自动检测过期域名并且自动续期
		* 对这种方式有顾虑的,请慎重,不过也可以自行删掉用户级的定时任务,并且清理掉~/.acme.sh文件夹就行
	
	4,在证书生成目录执行
		acme.sh --installcert -d springboot.io -d *.springboot.io  \
		--keypath       /usr/local/ssl/springboot/springboot.io.key  \
		--fullchainpath /usr/local/ssl/springboot/springboot.io.pem
		
		* 会把key和pem生成到指定的目录
	
	5,配置nginx

			server {
					listen 443;
					server_name springboot.io www.springboot.io;

					ssl on;
					ssl_certificate      /usr/local/ssl/springboot/springboot.io.pem;
					ssl_certificate_key  /usr/local/ssl/springboot/springboot.io.key;

					access_log  logs/springboot.io.log  main;
					error_log  logs/springboot.io.error.log;

					proxy_set_header Host $host;
					proxy_set_header X-Forwarded-Host $host;
					proxy_set_header X-Forwarded-Server $host;
					proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
					proxy_set_header X-Requested-For $remote_addr;
					proxy_set_header REMOTE-HOST $remote_addr;

					proxy_http_version 1.1;
					proxy_set_header Upgrade $http_upgrade;
					proxy_set_header Connection "upgrade";

					location / {
							proxy_pass http://127.0.0.1:1024;
							proxy_connect_timeout 600;
							proxy_read_timeout 600;

					}
			}

			server {
				listen       80;
				server_name  springboot.io www.springboot.io;
				return  301 https://springboot.io$request_uri;
			}

	6,acme.sh 源码
		#!/usr/bin/env sh

		#https://github.com/Neilpang/get.acme.sh

		_exists() {
		  cmd="$1"
		  if [ -z "$cmd" ] ; then
			echo "Usage: _exists cmd"
			return 1
		  fi
		  if type command >/dev/null 2>&1 ; then
			command -v $cmd >/dev/null 2>&1
		  else
			type $cmd >/dev/null 2>&1
		  fi
		  ret="$?"
		  return $ret
		}

		if _exists curl && [ "${ACME_USE_WGET:-0}" = "0" ]; then
		  curl https://raw.githubusercontent.com/Neilpang/acme.sh/master/acme.sh | INSTALLONLINE=1  sh
		elif _exists wget ; then
		  wget -O -  https://raw.githubusercontent.com/Neilpang/acme.sh/master/acme.sh | INSTALLONLINE=1  sh
		else
		  echo "Sorry, you must have curl or wget installed first."
		  echo "Please install either of them and try again."
		fi
	
	7, acme.sh 的Github和中文文档
		https://github.com/acmesh-official/acme.sh
		https://github.com/acmesh-official/acme.sh/wiki/%E8%AF%B4%E6%98%8E

## Springboot单独配置
	1,(在证书生成目录)生成keystore
		* 生成 p12 文件(会输入一次密码)
			openssl pkcs12 -export -in fullchain.cer -inkey springboot.io.key -out springboot.io.p12
			
		* 根据p12 文件生成 keystore 文件
			keytool -importkeystore -v  -srckeystore springboot.io.p12 -srcstoretype pkcs12 -srcstorepass 123456 -destkeystore springboot.io.keystore -deststoretype jks -deststorepass 123456
		
			* 如果提示警告,可以考虑根据警告的命令,再执行一波
				keytool -importkeystore -srckeystore springboot.io.keystore -destkeystore springboot.io.keystore -deststoretype pkcs12


	2,springboot配置
		#ssl
		server.ssl.enabled=true
		server.ssl.key-store=classpath:ssl/springboot.io.keystore
		server.ssl.key-store-type=PKCS12
		server.ssl.key-store-password=[key.store的密码]


------------------------------------
手动安装单域名证书					|
------------------------------------
	
1,clone 
	git clone git@github.com:certbot/certbot.git

2,生成证书
	* 在当前服务器生成(standalone)建议
		./letsencrypt-auto certonly --standalone --email [邮箱] -d [域名] -d [域名]

	 * 非当前服务器上生成(manual)
		./letsencrypt-auto certonly --manual --email [邮箱] -d [域名] -d [域名]

		* 需要在服务器的上安装可访问的,脚本提供的文本
			
			CjhdUm0L4oQU0ZHg7F7832FtFweUPlRFJs0LxJGx_qg.-ielpqOUtyZI_Q0f9xYi8-Bj57TsuD5y4mGIMxW9GwM			文本
			http://[域名]/.well-known/acme-challenge/CjhdUm0L4oQU0ZHg7F7832FtFweUPlRFJs0LxJGx_qg			访问地址
	
	* 执行期间会与控制台有两次交互,第一次输入A,表示同意协议,第二次输入YES,表示允许他们收集你的邮箱
	* 创建期间,必须保证80端口不被占用,脚本会在80端口启动http服务来验证域名所属(standalone)
	* 创建成功后会在  /etc/letsencrypt/ 下生成证书文件
	* 如果遇到因为 pip 带来的异常,尝试删除(建议重命名处理) ~/.pip/pip.conf 文件
	* 记得安全组/防火墙开放80端口

3,证书续约(需要在生成证书的服务器上进行)
	./letsencrypt-auto renew
		

		Saving debug log to /var/log/letsencrypt/letsencrypt.log

		- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		Processing /etc/letsencrypt/renewal/springcloud.io.conf(处理/etc/letsencrypt/renewal/springcloud.io.conf)							
		- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		Cert not yet due for renewal(证书还没有到期)														

		- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

		The following certs are not due for renewal yet:(下列证书尚未到期续期)									
		  /etc/letsencrypt/live/springcloud.io/fullchain.pem expires on 2018-11-29 (skipped)
		No renewals were attempted.(没有尝试更新)
		- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

# nginx配置
		ssl on;
		ssl_certificate      /etc/letsencrypt/live/[域名]/fullchain.pem
		ssl_certificate_key  /etc/letsencrypt/live/[域名]/privkey.pem

# 转换为 tomcat 的格式
	1,进入目录  /etc/letsencrypt/live/[域名]/ 执行
		openssl pkcs12 -export -in fullchain.pem -inkey privkey.pem -out [域名].p12

		* 生成 p12 文件(会输入一次密码)

	2,根据p12 文件生成 keystore 文件
		keytool -importkeystore -v  -srckeystore [域名].p12 -srcstoretype pkcs12 -srcstorepass [p12文件的密码] -destkeystore [域名].keystore -deststoretype jks -deststorepass [keytroe的密码]

		* 如果提示警告,可以考虑复制警告的命令,再执行一波
				keytool -importkeystore -srckeystore [域名].keystore -destkeystore [域名].keystore -deststoretype pkcs12

------------------------------------
手动安装泛域名证书					|
------------------------------------
1,下载
	wget https://dl.eff.org/certbot-auto
	
	chmod 775 certbot-auto

	* 也可采用certbot官方 yum安装方式

2,执行
	./certbot-auto certonly  -d *.bxcp360.com -d bxcp360.com --manual --email 747692844@qq.com --preferred-challenges dns --server https://acme-v02.api.letsencrypt.org/directory

	* 其实就是跟上面的那种方法一样,采用的是,非当前服务器上生成(manual)

3,添加TXT解析记录到dns服务器,添加完成后,等待证书生成,生成后	
	* 创建成功后会在  /etc/letsencrypt/ 下生成证书文件
	* 跟手动创建单域名证书一样的
	
4,更新证书
	./certbot-auto renew

	* ??失败了,可以尝试重新执行生成操作


------------------------------------
在线证书转换工具					|
------------------------------------

	https://myssl.com/