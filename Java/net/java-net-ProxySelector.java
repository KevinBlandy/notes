--------------------------------
ProxySelector
--------------------------------
	# 代理选择器抽象类

	# 静态方法
		static ProxySelector getDefault() 
		static void setDefault(ProxySelector ps)
		static ProxySelector of(InetSocketAddress proxyAddress)


	# 抽象方法
		abstract List<Proxy> select(URI uri);

		abstract void connectFailed(URI uri, SocketAddress sa, IOException ioe);

		

