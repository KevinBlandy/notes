-----------------------------------
geospatial						   |
-----------------------------------
	# 将指定的地理空间位置(纬度, 经度, 名称)添加到指定的key中
		* 这些数据将会存储到sorted set
		* 可以使用 GEORADIUS 或者 GEORADIUSBYMEMBER命令对数据进行半径查询等操作
	
	
	# 添加地址位置
		GEOADD key <纬度> <经度> <name> [<纬度> <经度> <name> ...]
			* 例子，指定Kevin和Litch的经纬度，存储在location这个key中
				GEOADD location 13.361389 38.115556 "Kevin" 15.087269 37.502669 "Litch"
	
	# 以某个经纬度为中心，获取其周围一定范围内的所有成员信息
		GEORADIUS key longitude latitude radius m|km|ft|mi [WITHCOORD] [WITHDIST] [WITHHASH] [COUNT count]
			* 以给定的经纬度为中心， 返回键包含的位置元素当中， 与中心的距离不超过给定最大距离的所有位置元素。
			* 范围可以使用以下其中一个单位
				m 表示单位为米
				km 表示单位为千米
				mi 表示单位为英里
				ft 表示单位为英尺
			
			WITHDIST
				* 在返回位置元素的同时， 将位置元素与中心之间的距离也一并返回。 距离的单位和用户给定的范围单位保持一致。
			
			WITHCOORD
				* 将位置元素的经度和维度也一并返回
			
			WITHHASH
				* 以 52 位有符号整数的形式， 返回位置元素经过原始 geohash 编码的有序集合分值。 
				* 这个选项主要用于底层应用或者调试， 实际中的作用并不大。
			
			* 命令默认返回未排序的位置元素。 通过以下两个参数， 用户可以指定被返回位置元素的排序方式：
				ASC: 根据中心的位置， 按照从近到远的方式返回位置元素。
				DESC: 根据中心的位置， 按照从远到近的方式返回位置元素。
			
			* 例子，从Sicily这个key中获取15-37周围200km内的用户信息
				GEORADIUS Sicily 15 37 200 km WITHCOORD WITHDIST 
	
	# 以某个member为中心，获取其周围一定范围内的所有成员信息
		GEORADIUSBYMEMBER key member radius m|km|ft|mi [WITHCOORD] [WITHDIST] [WITHHASH] [COUNT count]

		* 跟GEORADIUS一样，只是它指定member，而不是经纬度
		* 例子，从Sicily这个key中，获取Agrigento周围100km内的用户信息
			GEORADIUSBYMEMBER Sicily Agrigento 100 km
			

	# 返回一个或多个位置元素的 Geohash 表示
		GEOHASH key member [member ...]

		* 通常使用表示位置的元素使用不同的技术，使用Geohash位置52点整数编码。
			
	# 从key里返回所有给定位置元素的位置（经度和纬度）。
		GEOPOS key member [member ...]
		
	
	# 返回两个给定member之间的距离。
		GEODIST key member1 member2 [unit]

		* 如果两个位置之间的其中一个不存在， 那么命令返回空值。
		* 指定单位的参数 unit 必须是以下单位的其中一个：
			m 表示单位为米(默认)。
			km 表示单位为千米。
			mi 表示单位为英里。
			ft 表示单位为英尺。
		

	# 删除元素
		* 不好意思，没
		* 但是，可以用 ZREM 来删除元素，因为地理索引结构只是一个sorted set。
	
	# 底层是sorted set
		* 于是，可以用sorted set的操作方法来操作它
	
