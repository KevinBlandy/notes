--------------------
JAVAHTTP-GET		|
--------------------
	public static String getByJava() throws Exception{
		//构建容器
		StringBuilder sb = new StringBuilder();
		//url
		String youdao = "http://www.baidu.com";
		//创建网络路径
		URL url = new URL(youdao);
		/*
			可以转换为:HttpURLConnection
			就可以进行获取响应状态码等操作
		*/
		URLConnection connection = url.openConnection();
		//获取网络连接输入流,字节流转换为字符流,装饰为BufferdReader
		BufferedReader reader  = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String line = null;
		//循环读取响应数据
		while((line = reader.readLine()) != null){
			sb.append(line);
		}
		return sb.toString();
	}
--------------------
JAVAHTTP-POST		|
--------------------
	public static String postByJava() throws Exception{
		//构建容器
		StringBuilder sb = new StringBuilder();
		/*
			POST提交的时候,参数不能直接写在后面
		*/
		//准备读取流
		//准备输入流
		URL url = new URL("http://www.kevinblandy.com");
		/*
			注意了,这里使用POST的时候,使用的是:HttpURLConnection
			* 该类是一个抽象类,继承自 URLConnection
			* 我们需要强转换为HttpURLConnection
		*/
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		//设置发起请求的设置-编码格式
		connection.addRequestProperty("encoding", "utf-8");
		//设置当前连接可以从网络获取数据
		connection.setDoInput(true);
		//POST的话,要设置为true才可以往服务器提交请求参数
		connection.setDoOutput(true);
		//获取out流,用于向服务器输出参数
		BufferedWriter writer  = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
		/*
			可以一次性的写入N个参数,格式就是常规的提交方式
		*/
		writer.write("key=value&key=value&key=value");
		//刷出
		writer.flush();
		//获取服务器的响应的状态码
		int code = connection.getResponseCode();
		//获取in流,用于获取服务器的响应
		BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String line = null;
		while((line = reader.readLine()) != null){
			//保存读取到的数据,每次读取一行
			sb.append(line);
		}
		return sb.toString();
	}

-------------------------------------------------------------------------


--------------------
HTTPClient-GET		|
--------------------
	* 来自于Apache的神器
	public static String htppClientGet()throws Exception{
		/*
			构建URL,get请求,可以直接添加参数
			需要注意的就是,如果参数是带中文的话,需要注意url编码.这个简单就不必多说了
		*/
		String url = "http://www.kevinblandy.com/doing?pageCode=2";
		/*
			创建HttpClient
			注意,引用是HttpClient,而静态类是:HttpClients
			而且,一定是apache包下的东西,因为sun包下也有这么个东西
		*/
		HttpClient client = HttpClients.createDefault();
		//通过字符串url创建get连接
		HttpGet get = new HttpGet(url);
		/*
			通过HttpClient对象,来打开get连接,获取服务器的响应对象HttpResponse
		*/
		HttpResponse response = client.execute(get);
		/*
			通过response,我们可以获取到N多东西
			* 响应头
			* 地址
			* ...
			* Entity - 表示正文对象
		*/
		HttpEntity entity = response.getEntity();
		/*
			使用Apcahe包下的EntityUtils把正文对象转为字符串
		 */
		String result = EntityUtils.toString(entity,"utf-8");
		/*
			释放连接	
		*/
		get.releaseConnection();
		return result;
	}

	------获取所有的响应头

	Header[] hreaders = response.getAllHeaders();
	for(Header head : hreaders){
		//获取到所有的响应头
		System.out.println(head.getName()+":"+head.getValue());
	}
--------------------
HTTPClient-POST		|
--------------------
	public static String htppClientPost()throws Exception{
		//创建连接的url
		String url = "http://www.kevinblandy.com/doing";
		//创建HttpClient
		HttpClient client = HttpClients.createDefault();
		//创建post请求对象
		HttpPost post = new HttpPost(url);
		/*
			构建一个BasicNameValuePair泛型的集合
			BasicNameValuePair该类表示提交服务器的请求参数
		*/
		List<NameValuePair> params = new ArrayList<NameValuePair>();  
		/*
			集合中添加N个,通过构造方法创建出来的BasicNameValuePair对象
			BasicNameValuePair这个对象很显然就是 NameValuePair 的子类
		*/
		params.add(new BasicNameValuePair("pageCode", "1"));  
        /*
        	post请求对象,设置一个UrlEncodedFormEntity对象
        	该对象通过构造函数注入集合.该类可以自动的对象url进行url编码
        */
        post.setEntity(new UrlEncodedFormEntity(params)); 
		//获取服务器响应
		HttpResponse response = client.execute(post);
		//从响应中获取正文
		HttpEntity entity = response.getEntity();
		//通过EntityUtils把正文转换为utf-8格式的字符串
		String result = EntityUtils.toString(entity, "utf-8");
		return result;
	}

	------另外一种添加参数的方式

	** 不写了,用的时候百度吧


