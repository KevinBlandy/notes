-------------------------
Pipe
-------------------------
	# 管道流接口： interface Pipe<T> 

	Pipe<T> endOnFailure(boolean end);
	Pipe<T> endOnSuccess(boolean end);
	Pipe<T> endOnComplete(boolean end);
		* 完成/异常/成功 时调用 Write流的 end()

	default Future<Void> to(WriteStream<T> dst)
	void to(WriteStream<T> dst, Handler<AsyncResult<Void>> completionHandler)
		* 把流写入到目的地

	void close();
