--------------------
Future
--------------------
	# Future 主要用于承载异步结果

	# Future/回调 2种方式获取异步结果
		FileSystem fileSystem = Vertx.vertx().fileSystem();

		// 通过对返回  Future 的监听，获取到结果
		fileSystem.props("D:\\avatar.png").onComplete(result -> {
			if (result.succeeded()) {
				FileProps fileProps = result.result();
				System.out.println(fileProps.size());
			} else {
				Throwable throwable = result.cause();
				System.err.println(throwable.getMessage());
			}
		});
		
		// 传递一个Handler，通过回调获取到结果
		fileSystem.props("D:\\avatar.png", result -> {
			if (result.succeeded()) {
				FileProps fileProps = result.result();
				System.out.println(fileProps.size());
			} else {
				Throwable throwable = result.cause();
				System.err.println(throwable.getMessage());
			}
		});
	
	# Future 组合
		* 把一堆异步任务串联起来，如果全部被成功，则最终的结果就是成功，如果任何一个失败，最终的结果就是失败
			FileSystem fileSystem = Vertx.vertx().fileSystem();
			String file = "D:\\demo.txt";

			// 异步创建文件，返回Future
			fileSystem.createFile(file)
				.compose(result -> { // result == Void
					// 异步对文件写入数据，返回 Future
					return fileSystem.writeFile(file, Buffer.buffer("Hello Vertx", StandardCharsets.UTF_8.displayName()));
				})
				.compose(result -> {	 // result == Void
					// 异步移动文件，返回Future
					return fileSystem.move(file, "D:\\demo_new.txt");
				})
				.onComplete(resullt -> {
					// 最终结果
					if (resullt.succeeded()) {
						System.out.println("完成了，创建，写入，移动");
					} else {
						System.err.println("出现了异常：" + resullt.cause().getMessage());
					}
				})
				;
	
