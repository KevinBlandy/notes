---------------------------
hashlib						|
---------------------------
	* 安全散列与消息摘要

---------------------------
hashlib-内置属性			|
---------------------------
	
---------------------------
hashlib-模块方法			|
---------------------------
	_hashlib.HASH md5()
		* 获取到 md5 加密对象
		* <md5 HASH object @ 0x02E85B78>
	
	_hashlib.HASH sha1()
		* 获取 sha1 加密对象
		* <sha1 HASH object @ 0x02A55B78>

	_hashlib.HASH sha256()
		...

---------------------------
hashlib-_hashlib.HASH		|
---------------------------
	None update()
		* 把指定的数据进行md5加密,参数为字节数组
		* 如果多次调用 update(),则是把多次传递才加密参数,进行拼接

	str hexdigest()	
		* 获取加密后的16进制密文

	* demo(md5)
		md5 = hashlib.md5()
		md5.update("a12551255".encode("UTF-8"))
		var = md5.hexdigest()	# 493d01fa9ae5c2aaae17fbe88ec1d5fb
	* demo(sha1)
		sha1 = hashlib.sha1()
		sha1.update("a12551255".encode("utf-8"))
		var = sha1.hexdigest()	# 98e01e46e4f714f30548052fe7bbca6df24bc3e7