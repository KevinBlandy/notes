--------------------
bytes 类方法		|
--------------------
	decode()
		* 把字节数据转换为字符,只能转换 ASCII 码,不能转换汉字
	
	formhex()
		* 把16进制字符串转换为字节
	

--------------------
bytes				|
--------------------
	* 直接定义二进制
		x = 0B110
		print(type(x))	# <class 'int'>
		print(x)		# 6
	

	

	* 字符串与二进制
		name = "余文朋"
		# 把字符串按照 utf-8 格式转换为字节数组
		enc = name.encode(encoding='utf_8')
		# 把字节数组,以 utf-8 格式转换为字符串
		dec = enc.decode(encoding="utf-8")
		print(dec)

		x = b'KevinBalndy'
		print(type(x))	# <class 'bytes'>
		print(x)		# b'KevinBalndy'


--------------------
bytes				|
--------------------

	* 16进制字符串转换为字节数组
		bytes().fromhex(hex_str)
			* hex_str 不用带 0X 前缀
			* demo
				bytes().fromhex("FF0505AE")	# b'\xff\x05\x05\xae'
	
	
	* 把字节数组,转换为16进制字符串
		def byte_to_hex(bins):
			return ''.join(["%02X" % x for x in bins ] ).strip()

--------------------
bytearray			|
--------------------
	* 支持修改的字节数组,运行操作其中的元素
	* api跟list类似
	

	* 把字节数组转换为16进制字符串,不带 0x
		def bytearray_to_hexstr(bins):
			return ''.join([hex(i).replace('0x','').zfill(2).upper() for i in bytearray(bins)])
