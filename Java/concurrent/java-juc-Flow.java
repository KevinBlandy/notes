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
					* 第一次建立订阅关系

				public void onNext(T item);
					* 接受到数据

				public void onError(Throwable throwable);
					* 异常

				public void onComplete();
					* 异常处理完毕
			}

			public static interface Subscription {
				public void request(long n);
					* 告诉发布者，当前消费者需要消费多少资源

				public void cancel();
			}

			public static interface Processor<T,R> extends Subscriber<T>, Publisher<R> {}

			static final int DEFAULT_BUFFER_SIZE = 256;
				* 默认缓冲池的长度

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
		
	# Processor<T,R>
		* 定义了转换Publisher到Subscriber的方法
			Publisher(T) -> Processor -> Subscriber(R)
		
		* Publisher发布的数据，可以被Processor进行转换，过滤后，再给Subscriber消费
		

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
	
	# 构造函数
		public SubmissionPublisher(Executor executor, int maxBufferCapacity, BiConsumer<? super Subscriber<? super T>, ? super Throwable> handler)
		public SubmissionPublisher(Executor executor, int maxBufferCapacity)
		public SubmissionPublisher()
	
	# 实例方法
		public void subscribe(Subscriber<? super T> subscriber)
			* 订阅消费者
		
		public int submit(T item)
			* 发布数据
		
		public int offer(T item, BiPredicate<Subscriber<? super T>, ? super T> onDrop)
		public int offer(T item, long timeout, TimeUnit unit, BiPredicate<Subscriber<? super T>, ? super T> onDrop)
		public void close()
		public void closeExceptionally(Throwable error)
		public boolean isClosed()
		public Throwable getClosedException()
		public boolean hasSubscribers()
		public int getNumberOfSubscribers()
		public Executor getExecutor()
		public int getMaxBufferCapacity()
		public List<Subscriber<? super T>> getSubscribers()
		public boolean isSubscribed(Subscriber<? super T> subscriber)
		public long estimateMinimumDemand()
		public int estimateMaximumLag()
		public CompletableFuture<Void> consume(Consumer<? super T> consumer)
	

	# Demo
		import java.util.concurrent.Flow;
		import java.util.concurrent.SubmissionPublisher;
		import java.util.concurrent.TimeUnit;

		public class MainTest {
			public static void main(String[] args) {
				// 创建生产者
				SubmissionPublisher<Integer> submissionPublisher = new SubmissionPublisher<>();
				// 创建消费者
				Flow.Subscriber<Integer> subscriber = new Flow.Subscriber<>() {
					private Flow.Subscription subscription;
					@Override
					public void onSubscribe(Flow.Subscription subscription) {
						System.out.println("生产消费关系建立");
						subscription.request(10); // 初始只消费10个数据
						this.subscription = subscription;
					}

					@Override
					public void onNext(Integer item) {
						System.out.println("消费到了数据：" + item);
						if (item.equals(5)) {
							// 消费到第5个的时候，罢工不干了。取消订阅。
							this.subscription.cancel();
						} 

						// 响应式的关键实现，就是在这里，在消费完成之后，再调用request，向生产者索要数据
						// 避免了上游消息量过大，压垮下游
						// subscription.request(1); 
					}
					@Override
					public void onError(Throwable throwable) {
						// onNext中出现了异常
						System.err.println("异常了：" + throwable.getMessage());
						this.subscription.cancel();
					}
					@Override
					public void onComplete() {
						// 发布者调用关闭的时候，会被触发
						System.out.println("消费完成");
					}
				};

				// 建立关系
				submissionPublisher.subscribe(subscriber);

				// 生产数据
				for (int i = 0; i < 5; i ++){
					submissionPublisher.submit(i);
				}

				// 关闭发布者，一般在finally中
				submissionPublisher.close();

				try {
					// 主线程暂停，
					Thread.sleep(TimeUnit.SECONDS.toMillis(10));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}



	
	