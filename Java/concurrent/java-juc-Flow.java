-------------------
Flow
-------------------
	# 来了来了, 响应式编程它终于来了
	# JDK9的新东西, 同样来自于Doug Lea大神
	# 源码
		public final class Flow {
			@FunctionalInterface
			public static interface Publisher<T> {
				public void subscribe(Subscriber<? super T> subscriber);
			}

			public static interface Subscriber<T> {
				public void onSubscribe(Subscription subscription);
				public void onNext(T item);
				public void onError(Throwable throwable);
				public void onComplete();
			}

			public static interface Subscription {
				public void request(long n);
				public void cancel();
			}

			public static interface Processor<T,R> extends Subscriber<T>, Publisher<R> {}

			static final int DEFAULT_BUFFER_SIZE = 256;

			public static int defaultBufferSize() {
				return DEFAULT_BUFFER_SIZE;
			}
		}

	# Publisher
		* 定义了生产数据和控制事件的方法

	# Subscriber
		* 定义了消费数据和事件的方法

	# Subscription
		* 定义了链接Publisher和Subscriber的方法
		
	# Processor
		* 定义了转换Publisher到Subscriber的方法
			Publisher(T) -> Processor -> Subscriber(R)
		

	# 看起简单, 实现难
		* Publisher接口的简单实现, 可以将其用于简单的用例或扩展以满足自己的需求
		* 作为应用程序开发人员, 会发现实现这些接口很复杂
		* RxJava是响应式流的Java实现之一



-------------------
SubmissionPublisher
-------------------
	# Flow.Publisher 的实现
		class SubmissionPublisher<T> implements Publisher<T>, AutoCloseable
	
		* 它可以灵活的生产数据, 同时与Reactive Stream兼容


	
	