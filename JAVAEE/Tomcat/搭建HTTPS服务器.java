----------------------------
Tomcat-搭建HTTPS服务器		|
----------------------------
	# 对于HTTPS,势在必行.
	
	1,为网站创建数字证书
		
		* 使用的是$JAVA_HOME/bin/keytool 工具(JAVA里面自带的工具)

		* keytool -genkey -alias tomcat -validity 36500 -keystore D:\home\tomcat.keystore -keyalg RSA

			* -genkey		:表示产生密钥对
			* -alias		:表示起个别名
			* -keyalg		:指定密钥算法
			* -validity		:密钥有效时间,默认为90天,36500.表示100年
			* -keystore		:指定密钥保存的路径

		
	
		* 输入 keystore 密码
			产生的证书,系统会使用一个密钥库来保存,这里就是设置密钥库的密码
		
		* 您的名字与姓氏是什么?
			这一步很重要,表示为哪个网站生成数字证书,填写域名
		
		* 您的组织单位名称是什么？
			* 无视
		
		* 您的组织名称是什么？
			* 无视
		
		* 您所在的城市或者区域名称是什么?
			* 无视
		
		* 您所在的洲,或省份是什么?
			* 无视
		
		* 该单位的两字母国家代码是什么?
			* 无视
		
		* CN=localhost,OU=Unknow,O=Unknow,L=Unknow,ST=Unknow,C=Unknow 正确吗?
			* 确定输入: y
		
		* 输入 <tomcat> 的主密码(如果和 keystore 密码相同,直接回车)
			* 数字证书的密钥,和密钥库的密码是否相同.
			* 这项较为重要，会在tomcat配置文件中使用，建议输入与keystore的密码一致，设置其它密码也可以
		
		* OK,在~目录下,会生成 .keystore 一个证书文件
			* 至此,证书创建成功
		
		*  JKS 密钥库使用专用格式,迁移到行业标准格式 PKCS12
			keytool -importkeystore -srckeystore [name].keystore -destkeystore [name].keystore -deststoretype pkcs12

输入源密钥库口令:
已成功导入别名 localhost 的条目。
	
	2,配置服务器
		* 把 .keystore 文件复制到 $TOMCAT_HOME/conf 目录下
		* 修改server.xml,其中有段已经注释掉的
			  <Connector
				port="8443" 
				protocol="org.apache.coyote.http11.Http11Protocol"
				maxThreads="150" 
				SSLEnabled="true" 
				scheme="https" 
				secure="true"
				clientAuth="false" 
				sslProtocol="TLS"  
				keystoreFile="conf/.keystore"		//指定密钥文件
				keystorePass="123456"/>				//指定密钥库的密码
			

			
	3, 导出公钥
		keytool -export -alias [alias] -file [name].cer -keystore [name].keystore -storepass [密码]
			alias			:生成证书时使用的别名
			[name].cer		:导出的公钥文件(包含了公钥和证书)
			[name].keystore	:key.keystore

	4,导入合作方公钥
		* 通讯双方假设为A和B,A发布了自己的证书并公开了公钥,B所有经过A的公钥加密的报文发送给A后,A可以正确解密,如果A给B发送报文,A用私钥加密，B可以用公钥解密
		* 但这里有一个问题就是公钥是公开的,A发送给B的报文,任何有公钥的人都可以解密,不能保证A向B发送信息的安全性
		* 所以B也需要制作自己的证书,并对A公开自己的公钥,这样A向B发送信息里用B的公钥加密,这样B就可以用私钥解密,而其他截获信息的人因为没有私钥也不能解密
		* A需要将B的公钥导入自己的证书库

		 keytool -import -file [name].cer -keystore [name].keystore -storepass [密码]
			* [name].cer 合作方的公钥
			* [name].keystore 本地的keystore
			*  [密码] 本地keystore的密码

		 * 提示是否信任这个认证,y
		 * 回车后即可导入然后查看证书库中的证书条目
			 keytool -list -v -keystore [name].keystore -storepass [密码]
	
	5,删除合作方的公钥
		keytool -delete -alias [公钥] -keystore [keystore文件]

----------------------------
Tomcat-阿里云				|
----------------------------
	

