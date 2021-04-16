------------------
websocket
------------------
	# 通过 HttpServer 处理ws请求
		.webSocketHandler(webSocket -> {
			webSocket.closeHandler(vod -> { // 监听关闭
				// 可能为null，例如执行了 reject();
				Short code = webSocket.closeStatusCode();
				String reason = webSocket.closeReason();
				System.out.println(String.format("code=%d, reason=%s", code, reason));
			});
			
			if (webSocket.path().equals("/channel")) { // 只接受这个URI的请求

			} else {
				// 拒绝握手，自动关闭链接
				webSocket.reject(404);
			}
		})

	# 异步处理WebSocket握手
		server.webSocketHandler(webSocket -> {
		  Promise<Integer> promise = Promise.promise();
		  webSocket.setHandshake(promise.future());
		  authenticate(webSocket.headers(), ar -> {
			if (ar.succeeded()) {
			  // 用101状态码（协议切换）结束握手
			  // 或用401状态码（未鉴权）拒绝握手
			  promise.complete(ar.succeeded() ? 101 : 401);
			} else {
			  // 发送500错误状态码
			  promise.fail(ar.cause());
			}
		  });
		});

		* 除非手动设置了WebSocket握手处理器，否则调用（webSocketHandler传入的）处理器后，将自动接受WebSocket握手。
	
	# 协议切换为 WebSocket
		httpServer.requestHandler(req -> {
			if (req.path().equals("/channel")) {
				// 升级为webSocket
				req.toWebSocket().onSuccess(socket -> {
					socket.closeHandler(vod -> { // 监听关闭
						Short code = socket.closeStatusCode();
						String reason = socket.closeReason();
						System.out.println(String.format("code=%d, reason=%s", code, reason));
					});
					socket.textMessageHandler(message -> {
						System.out.println("收到客户端消息：" + message);
						socket.writeTextMessage(message).onComplete(result -> {
							System.out.println("消息发送结果：success=" + result.succeeded());
						});
					});
				})
				// 升级异常
				.onFailure(err -> {
					System.err.println("ws升级异常：" + err.getMessage());
				});
			} else {
				req.response().setStatusCode(401).end("fail");
			}
		})
			