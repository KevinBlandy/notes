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

		connection.connect();
		//快捷操作  URLConnection connection = url.openStream();
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
		//设置当前连接可以从网络获取数据
		connection.setDoInput(true);
		//POST的话,要设置为true才可以往服务器提交请求参数
		connection.setDoOutput(true);

		//设置请求头
		connection.addRequestProperty("encoding", "utf-8");
		//设置请求方式
		connection.setRequestMethod("POST");

		// 建立与服务器的连接，并未发送数据
		connection.connect();


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


