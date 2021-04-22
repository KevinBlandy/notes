---------------------
Sockjs
---------------------
	# Demo
		// sockjs 配置
		SockJSHandlerOptions options = new SockJSHandlerOptions().setHeartbeatInterval(2000);
		
		// 创建handler
		SockJSHandler sockJSHandler = SockJSHandler.create(vertx, options);
		
		// 添加 mainRouter
		mainRouter.mountSubRouter("/channel", sockJSHandler.socketHandler(socket -> {
			LOGGER.info("新的链接：remote={}", socket.remoteAddress());
			socket.handler(buffer -> {
				
				String message = buffer.toString();
				
				LOGGER.info("新的消息：{}", message);
				if ("bye".equalsIgnoreCase(message)) {
					socket.close(WebSocketCloseStatus.NORMAL_CLOSURE.code(), "be");
					return;
				}
				
				// 回写给客户端
				socket.write(message)
					.onSuccess(vod -> {
					}).onFailure(exception -> {
						LOGGER.info("响应客户端异常：{}", exception.getMessage());
					});
			});
			socket.endHandler(vod -> {
				LOGGER.info("链接断开：{}", socket.remoteAddress());
			});
			socket.exceptionHandler(exception -> {
				LOGGER.info("链接异常：{}", exception.getMessage());
			});
		}));