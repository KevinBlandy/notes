---------------------
Get					 |
---------------------
	String run(String url) throws IOException {

		OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)    // url
                .addHeader("foo","bar") // 消息头
                .build();

        Response response = client.newCall(request).execute();

        response.body().string();
	}

---------------------
带query param的GET	 |
---------------------
	OkHttpClient client = new OkHttpClient();

	HttpUrl.Builder builder = HttpUrl.parse("http://localhost:1024/openApi/category").newBuilder();

	// 添加一个或者多个检索参数
	builder.addQueryParameter("name","val");
	builder.addEncodedQueryParameter("age","val");

	HttpUrl httpUrl = builder.build();

	Request request = new Request.Builder()
			.url(httpUrl)    // url
			.addHeader("foo","bar") // 消息头
			.build();

	Response response = client.newCall(request).execute();

	response.body().string();

---------------------
文件下载			 |
---------------------
	OkHttpClient client = new OkHttpClient();
	Request request = new Request.Builder().url("http://localhost/1.mp4").build();
	Response response = client.newCall(request).execute();
	
	// 从响应获取到InputStream
	BufferedInputStream bufferedInputStream = new BufferedInputStream(response.body().byteStream());
	
	// io到本地
	BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(new File("D:\\演讲.mp4")));
	byte[] data = new byte[1024];
	int len;
	while((len = bufferedInputStream.read(data)) != -1) {
		bufferedOutputStream.write(data,0,len);
		bufferedOutputStream.flush();
	}
	bufferedOutputStream.close();