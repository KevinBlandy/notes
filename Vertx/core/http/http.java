---------------------------
http
---------------------------
	# 请求的处理流程
		* 当请求的头信息被完全读取时会调用Handler
			HttpServer server = vertx.createHttpServer();
			server.requestHandler(request -> {
			  // Handle the request in here
			});
		
		* 当一部分body可以被读取的时候，调用： HttpServerRequest handler(Handler<Buffer> handler);
		* 这个方法可能会被执行多次，可以自己组合所有的请求
			httpServer.requestHandler(req -> {
				Buffer body = Buffer.buffer();		// 存储整个Body
				req.handler(chunk -> {				// 把每次读取到的数据，都添加到body
					body.appendBuffer(chunk);
				});
				req.endHandler(vod -> {
					System.out.println(body.toString());	// 在请求体全部读取完毕后，获取body中的数据
				});
				req.response().putHeader("content-type", "text/plain; charset=utf-8").end("success");
			})

		* 聚合请求体，调用: 
			default HttpServerRequest bodyHandler(@Nullable Handler<Buffer> bodyHandler)	// 经典回调，只有在body() -> success的才会调用
			default HttpServerRequest body(Handler<AsyncResult<Buffer>> handler)			// 经典回调
			Future<Buffer> body();															// 链式调用
		* 框架自动聚合整个请求体，可以监听这个事件，Body为null，则为空
			
	
		* 当整个请求（包括所有请求体）已经被完全读取时，调用： HttpServerRequest endHandler(Handler<Void> endHandler);
	
	# 解析表单
		* 必须在body读取完毕后才能获取
		* 可使用 application/x-www-form-urlencoded 或 multipart/form-data 这两种 content-type 来提交 HTML 表单。
		
			request.setExpectMultipart(true);  // 需要设置这个
			request.endHandler(v -> {			// 在body都被读取完毕后，调用 formAttributes
				MultiMap formAttributes = request.formAttributes();
			});
		
		* 客户端必须正确的设置 ContentType，以及是合法允许携带body的请求方法
	
	# 多部件表单体请求
		* 处理multipart 编码形式上传的的文件
		
		httpServer.requestHandler(req -> {
			req.setExpectMultipart(true);
			req.exceptionHandler(err -> {
				req.response().putHeader("content-type", "text/plain; charset=utf-8").end("error:" + err.getMessage());
			});
			
			
			// 处理每一个文件
			req.uploadHandler(upload -> {
				String formName = upload.name();
				String fileName = upload.filename();
				String contentType = upload.contentType();
				long size = upload.size();
				
				System.out.println(String.format("formName=%s, fileName=%s, contentType=%s, size=%sd", formName, fileName, contentType, size));
				
				// 读取到内存，或者IO到磁盘
				Buffer body = Buffer.buffer();	
				upload.handler(chunk -> {
					body.appendBuffer(chunk);
				});
				
				upload.endHandler(vod -> {
					System.out.println("读取完毕");
					System.out.println("body：" + body.toString("utf-8"));
				});
			});
			
			// 统一处理表单
			req.endHandler(vod -> {
				MultiMap form = req.formAttributes();
				System.out.println("form=" + form.toString());
			});
			
			req.response().putHeader("content-type", "text/plain; charset=utf-8").end("success");
		})
	
	# server push
		
		