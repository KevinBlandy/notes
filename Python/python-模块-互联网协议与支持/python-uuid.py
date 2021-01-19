------------------------
uuid					|
------------------------
	* 不重复的IID
	* 获取IID
		def get_uuid():
			return str(uuid.uuid1()).replace('-','').upper()
		
		def get_uuid():
			return uuid.uuid1().hex.upper()
