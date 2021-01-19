---------------------
POST JSON			 |
---------------------
	String post(String url, String json) throws IOException {

		MediaType JSON = MediaType.parse("application/json; charset=utf-8");

		OkHttpClient client = new OkHttpClient();

		// json请求体
		RequestBody body = RequestBody.create(JSON, json);

		Request request = new Request.Builder().url(url).post(body).build();
		Response response = client.newCall(request).execute();
		return response.body().string();
	}

---------------------
POST FORM			 |
---------------------
	OkHttpClient client = new OkHttpClient();

	// 表单请求体
	RequestBody body = new FormBody.Builder()
			.add("name","KevinBlandy")          // 添加表单项
			.addEncoded("charset","utf-8")      // 添加编码后的表单项
			.build();

	Request request = new Request.Builder().url("").post(body).build();

	Response response = client.newCall(request).execute();
	response.body().string();

---------------------
构建文件表单体		 |
---------------------
	OkHttpClient client = new OkHttpClient();

	// 文件表单体
	File file = new File("");
	RequestBody requestBody = new MultipartBody.Builder()
		.setType(MultipartBody.FORM)
		.addFormDataPart("file",file.getName(), RequestBody.create(MediaType.parse("image/png"), file))
		.addFormDataPart("name", "KevinBlandy")
		.build();

	Request request = new Request.Builder().url("").post(requestBody).build();
	Response response = client.newCall(request).execute();
	response.body().string();


---------------------
复杂的文件表单体	 |
---------------------
	OkHttpClient client = new OkHttpClient();
	// 文件表单体
	File file = new File("");

	RequestBody requestBody = new MultipartBody.Builder()

		.setType(MultipartBody.FORM)        //设置文件表单项

		.addFormDataPart("file",file.getName(), RequestBody.create(MediaType.parse("image/png"), file))  // 从磁盘加载文件数据

		.addFormDataPart("file","head.jpg", RequestBody.create(MediaType.parse("image/png"), new byte[]{})) // 从字节数组加载数据

		.addFormDataPart("name", "KevinBlandy") // 普通的表单项

		.addPart(MultipartBody.Part.create(  // 自定义消息头的表单项
				// 额外的消息头
				Headers.of(Collections.singletonMap("foo","bar")),
				// 消息体
				new FormBody.Builder()
						.addEncoded("name","KevinBlandy")
						.build()))

		.addPart(MultipartBody.Part.create( //自定义消息头的文件项
				// 额外的消息头
				Headers.of(Collections.singletonMap("foo","bar")),
				// 消息体
				RequestBody.create(MediaType.parse("image/png"), new byte[]{})
				))
		.build();

	Request request = new Request.Builder().url("").post(requestBody).build();

	Response response = client.newCall(request).execute();
	
	response.body().string();

