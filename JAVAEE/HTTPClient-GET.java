---------------------
常规GET请求			 |
---------------------
	public static void main(String[] args) throws Exception {
		// 创建Httpclient对象		可以理解为打开浏览器
		CloseableHttpClient httpclient = HttpClients.createDefault();
		// 创建http GET请求			可以理解为输入地址
		HttpGet httpGet = new HttpGet("http://www.baidu.com/");
		CloseableHttpResponse response = null;
		try {
			// 使用 Httpclient 执行请求		可以理解为敲!回!车!
			response = httpclient.execute(httpGet);
			// 判断返回状态是否为200
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				//获取响应对象
				HttpEntity entity = response.getEntity();
				//把响应的对象,转换为 utf8格式的文本文件
				String content = EntityUtils.toString(entity, "UTF-8");
				System.out.println("内容："+content);
			}
		} finally {
			if (response != null) {
				//关闭与服务器的连接
				response.close();
			}
			//关闭"浏览器"
			httpclient.close();
		}
	}

---------------------
带参GET请求			 |
---------------------
	 public static void main(String[] args) throws Exception {
        // 创建Httpclient对象		可以理解为打开浏览器
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // 创建http GET请求		可以理解为输入地址,而且是带参数的 setParameter(key,value);可以有N多个
        URIBuilder builder = new URIBuilder("http://www.baidu.com");
		/*
			这个方法返回的还是当前对象,说白了.可以连环调用	
		*/
        builder.setParameter("key1", "value1");
        //设置参数n
        builder.setParameter("key2", "value2");
        //转换为URI对象
        URI uri = builder.build();
        HttpGet httpGet = new HttpGet(uri);
        CloseableHttpResponse response = null;
        try {
            // 使用 Httpclient 执行请求		可以理解为敲!回!车!
            response = httpclient.execute(httpGet);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            	//获取响应对象
            	HttpEntity entity = response.getEntity();
            	//把响应的对象,转换为 utf8格式的文本文件
                String content = EntityUtils.toString(entity, "UTF-8");
                System.out.println("内容："+content);
            }
        } finally {
            if (response != null) {
                response.close();
            }
            httpclient.close();
        }
    }
	//其实第一种方式,直接在URL后面通过'?'添加参数也是可以达到参数提交的效果.只是说第二种看起来比较的优雅

---------------------
HTTPClient的一些API	 |
---------------------
	httpGet.setHeader("key","value");
		* 设置请求头,可以模拟浏览器
		* httpPost也可以使用
	
	Header[] hreaders = response.getAllHeaders();
	for(Header head : hreaders){
		System.out.println(head.getName()+":"+head.getValue());
	}
		* 获取到服务器的响应头数据


---------------------
连接管理			 |
---------------------
	  public static void main(String[] args) throws Exception {
			PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
			// 设置最大连接数
			cm.setMaxTotal(200);
			// 设置每个主机地址的并发数
			cm.setDefaultMaxPerRoute(20);
			doGet(cm);
			doGet(cm);
		}
		public static void doGet(HttpClientConnectionManager cm) throws Exception {
			CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();
			// 创建http GET请求
			HttpGet httpGet = new HttpGet("http://www.baidu.com/");
			CloseableHttpResponse response = null;
			try {
				// 执行请求
				response = httpClient.execute(httpGet);
				// 判断返回状态是否为200
				if (response.getStatusLine().getStatusCode() == 200) {
					String content = EntityUtils.toString(response.getEntity(), "UTF-8");
					System.out.println("内容长度：" + content.length());
				}
			} finally {
				if (response != null) {
					response.close();
				}
				// 此处不能关闭httpClient，如果关闭httpClient，连接池也会销毁
				// httpClient.close();
			}
		}

		# 连接池-这种方式更为科学,不用每次都去创建HTTPClient对象,可以提高修性能


---------------------
请求配置的设置		 |
---------------------
	public static void main(String[] args) throws Exception {
        // 创建Httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // 创建http GET请求
        HttpGet httpGet = new HttpGet("http://www.baidu.com/");
        // 构建请求配置信息
		RequestConfig config = RequestConfig.custom().setConnectTimeout(1000) 	// 创建连接的最长超时时间(毫秒)
                .setConnectionRequestTimeout(500) 								// 从连接池中获取到连接的最长时间(毫秒)
                .setSocketTimeout(10 * 1000) 									// 数据传输的最长时间(毫秒)
                .setStaleConnectionCheckEnabled(true) 							// 提交请求前测试连接是否可用(已经过期)
                .build();
        // 设置请求配置信息
        httpGet.setConfig(config);
        CloseableHttpResponse response = null;
        try {
            // 执行请求
            response = httpclient.execute(httpGet);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                String content = EntityUtils.toString(response.getEntity(), "UTF-8");
                System.out.println(content);
            }
        } finally {
            if (response != null) {
                response.close();
            }
            httpclient.close();
        }
    }

---------------------
整合Spring			 |
---------------------
	# HttpClient整合Spring
		* 不需要整合包,直接配置里面就完事儿
	

	<!-- 导入外部配置 -->
	<context:property-placeholder location="classpath:httpclient.properties" ignore-unresolvable="true"/>

	<!-- 
		HTTP接管理器,指定销毁方法 
	-->
	<bean id="httpClientConnectionManager" class="org.apache.http.impl.conn.PoolingHttpClientConnectionManager" destroy-method="close">
		<!-- 最大连接数 -->	
		<property name="maxTotal" value="${http.maxTotal}"/>
		<!-- 每个主机的最大连接(并发)数 -->
		<property name="defaultMaxPerRoute" value="${http.defaultMaxPerRoute}"/>
	</bean>

	<!-- 
		HttpClient 构建器 
	-->
	<bean id="httpClientBuilder" class="org.apache.http.impl.client.HttpClientBuilder">
		<!-- 注入HTTP连接管理器 -->
		<property name="connectionManager" ref="httpClientConnectionManager"/>
	</bean>

	<!-- 
		RequestConfig 构建器
	 --> 
	<bean id="requestConfigBuilder" class="org.apache.http.client.config.RequestConfig.Builder">
		<!-- 创建连接的最长超时时间 -->
	  	<property name="connectTimeout" value="${http.connectTimeout}"/>
	  	<!-- 从连接池中获取到连接的最长时间 -->
	 	<property name="connectionRequestTimeout" value="${http.connectionRequestTimeout}"/>
	 	<!-- 数据传输的最长时间 -->
	 	<property name="socketTimeout" value="${http.socketTimeout}"/>
	 	<!-- 提交请求前测试连接是否可用(API 已经过期) 
	 	<property name="staleConnectionCheckEnabled" value="${http.staleConnectionCheckEnabled}"/>-->
	</bean>

	<!-- 
		RequestConfig 请求配置控制器
		这个Bean 是由 id 为 requestConfigBuilder 的 Bean 的 build 方法创建到IOC 
		如果需要,请求配置.那么就把这个注入到需要的类
	 --> 
	 <bean class="org.apache.http.client.config.RequestConfig" factory-bean="requestConfigBuilder" factory-method="build"/>

	 <!-- 
		HttpClient
		这个Bean 是由 id 为 httpClientBuilder 的 Bean 的 build 方法创建到IOC 
		并且,这个对象.不能是单例.每次获取都通过 httpClientBuilder 类的 build 得到新的实例
		它是一个执行:HTTP 请求的执行者,哪里需要HTTP请求,就注入它
	 -->
	<bean class="org.apache.http.impl.client.CloseableHttpClient" factory-bean="httpClientBuilder" factory-method="build" scope="prototype"/>

	<!-- 
		定期清理无效连接 
	-->
	<bean class="com.taotao.web.httpclient.IdleConnectionEvictor" destroy-method="shutdown">
		<!--  构造器注入: HTTP接管理器-->
		<constructor-arg index="0" ref="httpClientConnectionManager"/>
	</bean>


	
	
	httpclient.properties
	
		# 最大连接数
		http.maxTotal=1000
		# 每个主机的最大连接(并发)数
		http.defaultMaxPerRoute=100
		# 创建连接的最长超时时间 
		http.connectTimeout=2000
		# 从连接池中获取到连接的最长时间
		http.connectionRequestTimeout=500
		# 数据传输的最长时间
		http.socketTimeout=60000
		# 提交请求前测试连接是否可用(已经过期) 
		http.staleConnectionCheckEnabled=true
	




