----------------------
Stream
----------------------
	# 流，核心的接口就俩
		ReadStream
		WriteStream
	
	# 异步写入
		* 写调用是立即返回的，而写操作的实际是在内部队列中排队写入
	
		* 若写入对象的速度比实际写入底层数据资源速度快，那么写入队列就会无限增长，最终导致内存耗尽
		* 可以通过判断，队列是否装满了，再决定要不要继续写入，writeQueueFull
			if (!sock.writeQueueFull()) {
			  sock.write(buffer);  // 如果队列没满，则写入。这种情况可能丢失数据
			}
		
		* 防止丢失数据，暂停一下，pause
			sock.write(buffer);				// 先写
			if (sock.writeQueueFull()) {	// 如果队列满了，就暂停一下
			  sock.pause();					// 但是，等待唤醒
			}
		
		* 队列有空闲了，取消暂停，恢复写入，resume
			ReadStream<String> readStream = null;
			WriteStream<String> writeStream = null;
			// 监听消息读事件
			readStream.handler(message -> {
				writeStream.write(message);				// 先写入数据
				if (!writeStream.writeQueueFull()) {	// 队列是否满了
					readStream.pause();					// 队列满了，暂停读取
					writeStream.drainHandler(vod -> {
						readStream.resume();			// 监听队列空闲事件，恢复读取
					});
				}
			});
		
		* 已经封装好的方法，pipeTo
			sock.pipeTo(sock);
								
