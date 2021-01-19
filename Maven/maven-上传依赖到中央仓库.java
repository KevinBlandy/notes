-------------------------
上传依赖到maven中央仓库	 |
-------------------------
	# 参考
		https://www.sojson.com/blog/250.html
	
	# 关键地址
		1,后台登录
			https://issues.sonatype.org/secure/Dashboard.jspa

			* 申请发布构件资格
		
		2,构件发布
			https://oss.sonatype.org/#welcome
			
			* 构件仓库,把jar包上传到这里,Release 之后就会同步到maven中央仓库
		
		3,中央仓库
			http://search.maven.org/

			* 构件发布成功后,就可以在这里检索到