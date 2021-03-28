---------------------------
CompositeFuture
---------------------------
	# interface CompositeFuture extends Future<CompositeFuture> 
		* 用于 Future 协作

---------------------------
static
---------------------------
	static <T1, T2> CompositeFuture all(Future<T1> f1, Future<T2> f2) 
	static <T1, T2, T3> CompositeFuture all(Future<T1> f1, Future<T2> f2, Future<T3> f3)
	static <T1, T2, T3, T4> CompositeFuture all(Future<T1> f1, Future<T2> f2, Future<T3> f3, Future<T4> f4) 
	static <T1, T2, T3, T4, T5> CompositeFuture all(Future<T1> f1, Future<T2> f2, Future<T3> f3, Future<T4> f4, Future<T5> f5)
	static <T1, T2, T3, T4, T5, T6> CompositeFuture all(Future<T1> f1, Future<T2> f2, Future<T3> f3, Future<T4> f4, Future<T5> f5, Future<T6> f6)
	static CompositeFuture all(List<Future> futures)
		* 所有成功，或者任意失败后就会返回到最终 Future
		* 必须是全部任务都成功，最终的结果才是成功，任意一个失败，最终的结果就是失败

	static <T1, T2> CompositeFuture any(Future<T1> f1, Future<T2> f2) 
	static <T1, T2, T3> CompositeFuture any(Future<T1> f1, Future<T2> f2, Future<T3> f3)
	static <T1, T2, T3, T4> CompositeFuture any(Future<T1> f1, Future<T2> f2, Future<T3> f3, Future<T4> f4)
	static <T1, T2, T3, T4, T5> CompositeFuture any(Future<T1> f1, Future<T2> f2, Future<T3> f3, Future<T4> f4, Future<T5> f5)
	static <T1, T2, T3, T4, T5, T6> CompositeFuture any(Future<T1> f1, Future<T2> f2, Future<T3> f3, Future<T4> f4, Future<T5> f5, Future<T6> f6)
	static CompositeFuture any(List<Future> futures)
		* 任何一个任务成功，或者全部失败，就会返回到最终 Future 
		* 任何一个任务成功，最终结果就是成功，全部任务失败，最终的结果就是失败

	static <T1, T2> CompositeFuture join(Future<T1> f1, Future<T2> f2)
	static <T1, T2, T3> CompositeFuture join(Future<T1> f1, Future<T2> f2, Future<T3> f3)
	static <T1, T2, T3, T4> CompositeFuture join(Future<T1> f1, Future<T2> f2, Future<T3> f3, Future<T4> f4)
	static <T1, T2, T3, T4, T5> CompositeFuture join(Future<T1> f1, Future<T2> f2, Future<T3> f3, Future<T4> f4, Future<T5> f5)
	static <T1, T2, T3, T4, T5, T6> CompositeFuture join(Future<T1> f1, Future<T2> f2, Future<T3> f3, Future<T4> f4, Future<T5> f5, Future<T6> f6)
	static CompositeFuture join(List<Future> futures)
		* 等待所有的 Future 完成，无论成败
		* 全部成功，则最终结果就是成功，任意失败，最终结果就是失败的


---------------------------
Method
---------------------------
	 CompositeFuture onComplete(Handler<AsyncResult<CompositeFuture>> handler);
	 default CompositeFuture onSuccess(Handler<CompositeFuture> handler)
	 default CompositeFuture onFailure(Handler<Throwable> handler)
	 Throwable cause(int index);
	 boolean succeeded(int index);
	 boolean failed(int index);
	 boolean isComplete(int index);
	 <T> T resultAt(int index);
	 int size();
	 default <T> List<T> list()
	 default List<Throwable> causes()


