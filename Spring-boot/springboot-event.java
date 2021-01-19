----------------------------
Event						|
----------------------------
	# Spring本身提供了这种事件发布订阅的模型
	# 顶层抽象了一个事件发布接口, ApplicationContext 实现了该接口
		@FunctionalInterface
		public interface ApplicationEventPublisher {
			default void publishEvent(ApplicationEvent event) {
				publishEvent((Object) event);
			}
			void publishEvent(Object event);
		}

	# 抽象的事件接口
		* JDK提供的事件对象
			public class EventObject implements java.io.Serializable {
				private static final long serialVersionUID = 5516075349620653480L;
				protected transient Object  source;
				public EventObject(Object source) {
					if (source == null)
						throw new IllegalArgumentException("null source");
					this.source = source;
				}
				public Object getSource() {
					return source;
				}

				public String toString() {
					return getClass().getName() + "[source=" + source + "]";
				}
			}
		
		* Spring提供的顶层事件对象
			public abstract class ApplicationEvent extends EventObject {
				private static final long serialVersionUID = 7099057708183571937L;
				private final long timestamp;
				public ApplicationEvent(Object source) {
					super(source);
					this.timestamp = System.currentTimeMillis();
				}
				public final long getTimestamp() {
					return this.timestamp;
				}
			}
	
	# 抽象的事件监听接口
		public interface EventListener {
		}
		@FunctionalInterface
		public interface ApplicationListener<E extends ApplicationEvent> extends EventListener {
			void onApplicationEvent(E event);
		}

	# 完整的事件发布订阅流程
		1. 自定义事件对象, 继承类:ApplicationEvent
		2. 自定义事件监听器, 实现接口:ApplicationListener, 泛型参数就是要监听的事件对象
		3. 使用 ApplicationEventPublisher 实现发布一个事件对象的实现
		4. 对应的监听器会得到执行
			* 如果存在多个监听, 则多个监听都会执行
			* 默认执行的线程, 就是当前的发布线程(非异步)

----------------------------
applicationEvent			|
----------------------------
	# 专门监听applicationContent的各种事件 
	# 实现接口 ApplicationListener<E extends ApplicationEvent>
	# 通过该接口的泛型来决定要监听的事件(ApplicationEvent子类)
	# Spring 预定义了N多的事件
		SpringApplicationEvent
		ApplicationStartingEvent

		....
	# demo
		@Component
		public class ApplicationStartingListener implements ApplicationListener<ApplicationStartingEvent> {

			private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationStartingListener.class);

			@Autowired
			private ApplicationContext applicationContext;

			@Override
			public void onApplicationEvent(ApplicationStartingEvent event) {
				LOGGER.debug("application 启动ok");
				SpringContext.setApplicationContext(this.applicationContext);
			}
		}


----------------------------
@EventListener				|
----------------------------
	# 可以使用注解驱动, 来监听事件
		@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
		@Retention(RetentionPolicy.RUNTIME)
		@Documented
		public @interface EventListener {

			@AliasFor("classes")
			Class<?>[] value() default {};

			@AliasFor("value")
			Class<?>[] classes() default {};

			String condition() default "";

		}
	
	# 默认使用参数作为监听的事件
		@EventListener
		public void stringListener(StringApplicationEvent event){
			System.out.println(Thread.currentThread().getName());
			System.out.println("common:" + event);
		}
		
		* 也可以通过注解的属性, 来指定监听的事件
	

	# 它可以去接受到非 ApplicationEvent 子类的事件
		@Component
		public class ObjectListener {

			// 接收到所有发布的事件
			@EventListener
			public void objectListener(Object event){
				System.out.println("object:" + event);
			}
		}

	
		* 发布的事件对象, 如果是监听事件的子类, 则会执行
	
	# 也支持通过泛型对象来监听事件
		@EventListener
		public void orderListener(GenericEvent<Order> event) {
			System.out.println("通用泛型事件监听, 订单");
		}

		* 系统发布的多个泛型事件, 只有与该监听方法想匹配的泛型事件对象才会执行
				
	# 可以通过SpEL来设置触发的条件
		* 在一些时候可能只要满足某些条件才进行对事件监听
		* 可以用 @EventListener 的 condition 属性来指定条件, 条件是一个 SpEL 表达式

		@EventListener(condition = "#event.order.orderStatus eq '待付款'")
		public void orderCreateEventCondition(OrderCreateEvent event) {
			// 只有订单状态是待付款才监听才有效
			System.out.println("订单创建事件");
		}
	
	# 新事件继续传播
		* 可以在事件方法内, 主动的调用方法来发送新的事件
			applicationContext.publishEvent(new StringApplicationEvent("Hello"));
		
		* 也可以返回一个事件对象:ApplicationEvent, 来广播新的事件
		* 也可以返回一个时间对象数组, 来广播多个
		* 千万要注意, 如果返回的事件对象, 还可以被当前的事件方法捕获的话, 那么就会产生一个不断发布, 处理的死循环


----------------------------
异步事件					|
----------------------------
	# 事件广播器的源码:ApplicationEventMulticaster
		public static final String APPLICATION_EVENT_MULTICASTER_BEAN_NAME = "applicationEventMulticaster";
		protected void initApplicationEventMulticaster() {
			ConfigurableListableBeanFactory beanFactory = getBeanFactory();
			// 如果ioc已经包含了事件广播器, 则使用IOC的
			if (beanFactory.containsLocalBean(APPLICATION_EVENT_MULTICASTER_BEAN_NAME)) {
				this.applicationEventMulticaster = beanFactory.getBean(APPLICATION_EVENT_MULTICASTER_BEAN_NAME, ApplicationEventMulticaster.class);
				if (logger.isTraceEnabled()) {
					logger.trace("Using ApplicationEventMulticaster [" + this.applicationEventMulticaster + "]");
				}
			}
			else {
				// 未包含, 则新创建
				this.applicationEventMulticaster = new SimpleApplicationEventMulticaster(beanFactory);
				beanFactory.registerSingleton(APPLICATION_EVENT_MULTICASTER_BEAN_NAME, this.applicationEventMulticaster);
				if (logger.isTraceEnabled()) {
					logger.trace("No '" + APPLICATION_EVENT_MULTICASTER_BEAN_NAME + "' bean, using " +
							"[" + this.applicationEventMulticaster.getClass().getSimpleName() + "]");
				}
			}
		}

	# 我们可以创建自己的 SimpleApplicationEventMulticaster 来达到自定义的异步广播效果
		* 内部存在一个线程池执行器, 可以自定义为自己的线程池实现
			@Nullable
			private Executor taskExecutor;

		* spring 提供了一个执行器的实现:SimpleAsyncTaskExecutor
			SimpleAsyncTaskExecutor()
			SimpleAsyncTaskExecutor(String threadNamePrefix)
			SimpleAsyncTaskExecutor(ThreadFactory threadFactory)
			
			* 但是不建议使用该执行器, 因为它会为每个任务都新启动一个线程
			* 没有达到线程池的功能, 建议自己实现, 设置线程池
			
		* 配置代码
			@Configuration
			public class AsyncApplicationEventMulticaster {


				@Bean(name = AbstractApplicationContext.APPLICATION_EVENT_MULTICASTER_BEAN_NAME)
				public ApplicationEventMulticaster simpleApplicationEventMulticaster() {

					SimpleApplicationEventMulticaster simpleApplicationEventMulticaster = new SimpleApplicationEventMulticaster();

					//设置任务执行器
					simpleApplicationEventMulticaster.setTaskExecutor(new SimpleAsyncTaskExecutor());
					return simpleApplicationEventMulticaster;
				}
			}
		
		* 所有的事件执行方法, 都会使用 taskExecutor 去执行

	
	# 异步事件的实现, 也可以使用 @Async 注解来完成
		* 需要注解驱动开启异步: @EnableAsync
		* 配置 @Async 就可以很灵活的选择, 哪些方法需要同步处理, 哪些方法需要异步处理

			@EventListener
			@Async
			public void objectListener(Object event){
				System.out.println(Thread.currentThread().getName());
				System.out.println("common:" + event);
			}
		
		* 默认就会采用:SimpleAsyncTaskExecutor 来执行, 每个任务方法, 新建一个线程执行
		* 可以通过 @Async 的 value 属性, 去指定要使用的线程池 bean name
	

