------------------------
base64					|
------------------------
	* base64编码模块

------------------------
base64-模块属性			|
------------------------
------------------------
base64-模块方法			|
------------------------
	bytes b64encode(bytes)
		* 对指定字节数据进行base64编码
		* 返回的还是字节数据类型
	
	bytes b64decode(bytes)
		* 对指定的字节数据进行base64解码
		* 返回的还是字节数据类型
	
	
	bytes urlsafe_b64encode(bytes)
		* 标准的Base64编码后可能出现字符+和/
		* 在URL中就不能直接作为参数,所以 urlsafe 的base64编码,其实就是把字符+和/分别变成-和_
		* URL安全的Base64位编码
			先将内容编码成Base64结果
			将结果中的加号"+"替换成中划线"-"
			将结果中的斜杠"/"替换成下划线"_"
			将结果中尾部的"="号全部保留
	
	bytes urlsafe_b64decode(bytes)
		* 同上,解码 urlsafe 的Base64 编码


------------------------
base64-Demo				|
------------------------

# 基本的BASE64编码

	import base64
	result = base64.b64encode(bytes('admin:888888','UTF_8')).decode('UTF_8')
	print(result)   # YWRtaW46ODg4ODg4
	

		
	
