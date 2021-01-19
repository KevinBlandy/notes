------------------------
InetAddress				|
------------------------
	# Java中用于描述IP地址的类
	# 没得public的构造函数,只能通过静态方法来创建
	# 子类
		Inet4Address
		Inet6Address


------------------------
实例方法				|
------------------------
	String getHostAddress();
		* 返回ip信息
	
	String getHostName()
		* 返回主机名

------------------------
静态方法				|
------------------------
	InetAddress getLocalHost()
		* 得到描述本机IP的InetAddress对象

	InetAddress getByName(String host)
		* 通过指定域名从DNS中得到相应的IP地址

	InetAddress[] getAllByName(String host)

	InetAddress getByAddress(byte[] addr)

	InetAddress getByAddress(String host, byte[] addr)

	InetAddress getLoopbackAddress()