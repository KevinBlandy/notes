----------------------------
异步执行					|
----------------------------
	OkHttpClient okHttpClient = new OkHttpClient();

	Request request = new Request.Builder()
			.url("http://www.qq.com")
			.build();

	Call call = okHttpClient.newCall(request);

	// 异步执行不会阻塞,设置回调
	call.enqueue(new Callback() {
		// 处理异常
		@Override
		public void onFailure(Call call, IOException e) {
			System.out.println(e.getMessage());
		}
		// 处理响应
		@Override
		public void onResponse(Call call, Response response) throws IOException {
			if (response.isSuccessful()) {
				System.out.println(response.body().string());
			}
		}
	});
