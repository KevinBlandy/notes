-----------------------
CookieManager
-----------------------
	# Cookie管理器
		CookieManager extends CookieHandler
	
	# 构造方法
		CookieManager() 
		CookieManager(CookieStore store, CookiePolicy cookiePolicy)
	
	# 实例方法
		void setCookiePolicy(CookiePolicy cookiePolicy)
		CookieStore getCookieStore()

		Map<String, List<String>> get(URI uri, Map<String, List<String>> requestHeaders)

		void put(URI uri, Map<String, List<String>> responseHeaders)
	

-----------------------
CookieStore
-----------------------
	# Cookie存储介质的接口

	# 抽象方法
		void add(URI uri, HttpCookie cookie);
		List<HttpCookie> get(URI uri);
		List<HttpCookie> getCookies();
		List<URI> getURIs();
		boolean remove(URI uri, HttpCookie cookie);
		boolean removeAll();


-----------------------
CookiePolicy
-----------------------
	# cookie的策略接口, 它决定了是否要提交cookie
	
	# 抽象方法
		boolean shouldAccept(URI uri, HttpCookie cookie);

	
	# 静态实现
		static final CookiePolicy ACCEPT_ALL
		static final CookiePolicy ACCEPT_NONE
		static final CookiePolicy ACCEPT_ORIGINAL_SERVER

	