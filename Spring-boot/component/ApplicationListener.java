--------------------------------
Application的Event事件			|
--------------------------------
	# Spring 的核心是 ApplicationContext,它负责管理 beans 的完整生命周期

	# 当加载 beans 时,ApplicationContext 发布某些类型的事件
		* 当上下文启动时,ContextStartedEvent 发布
		* 当上下文停止时,ContextStoppedEvent 发布

	# 通过 ApplicationEvent 类和 ApplicationListener 接口来提供在 ApplicationContext 中处理事件
	# 如果一个 bean 实现 ApplicationListener,那么每次 ApplicationEvent 被发布到 ApplicationContext 上,那个 bean 会被通知
	# Spring 提供了以下的标准事件
		1,ContextRefreshedEvent
			* ApplicationContext 被初始化或刷新时,该事件被发布
			* 这也可以在 ConfigurableApplicationContext 接口中使用 refresh() 方法来发生

		2,ContextStartedEvent
			* 当使用 ConfigurableApplicationContext 接口中的 start() 方法启动 ApplicationContext 时,该事件被发布
			* 你可以调查你的数据库,或者你可以在接受到这个事件后重启任何停止的应用程序

		3,ContextStoppedEvent
			* 当使用 ConfigurableApplicationContext 接口中的 stop() 方法停止 ApplicationContext 时,发布这个事件
			* 你可以在接受到这个事件后做必要的清理的工作

		4,ContextClosedEvent
			* 当使用 ConfigurableApplicationContext 接口中的 close() 方法关闭 ApplicationContext 时,该事件被发布
			* 一个已关闭的上下文到达生命周期末端；它不能被刷新或重启

		5,RequestHandledEvent
			* 这是一个 web-specific 事件,告诉所有 bean HTTP 请求已经被服务
	# 步骤
		1,自定义类实现 ApplicationListener<E extends ApplicationEvent>
		2,根据泛型:ApplicationEvent来决定要监听的事件
		3,把该类加入IOC
	
	# Demo
		public class CStartEventHandler implements ApplicationListener<ContextStartedEvent>{
			public void onApplicationEvent(ContextStartedEvent event) {
				System.out.println("ContextStartedEvent Received");
			}
		}
	
	# 部分系统预定义的事件
		ContextRefreshedEvent
		ServletWebServerInitializedEvent
		ApplicationStartedEvent
		ApplicationReadyEvent
		PayloadApplicationEvent


--------------------------------
自定义事件,与发布				|
--------------------------------
	# 事件对象实现接口:ApplicationEvent
		import org.springframework.context.ApplicationEvent;
		public class MyEvent extends ApplicationEvent {
			private static final long serialVersionUID = -2362115341823186646L;
			
			// 构造函数可以设置一个事件对象,可以在监听器中获取到这个事件对象
			public MyEvent(Object source) {
				super(source);
			}
		}
	

	# 实现监听器接口:
		import org.springframework.context.ApplicationListener;
		import org.springframework.stereotype.Component;

		@Component
		public class SpringApplcationListenner implements ApplicationListener<MyEvent>{

			@Override
			public void onApplicationEvent(MyEvent event) {
				System.err.println(event.getSource() + ":" + event.getClass().getName());
			}
		}

		* 泛型的定义,就是我们自定义的事件对象
	
	# 使用 ApplicationContext 对象主动的发布事件
		ApplicationContext applicationContext = SpringApplication.run(PasteApplication.class,args);
		// 通过 publishEvent(); 发布事件
    	applicationContext.publishEvent(new MyEvent("你好啊，我是事件"));
	
	
	# 事件发布后,只要是符合泛型对象的监听器(包括子类),都会捕获到事件
		import org.springframework.boot.context.event.ApplicationReadyEvent;
		import org.springframework.context.ApplicationEvent;
		import org.springframework.stereotype.Component;
		

		// 不声明没有泛型,这样就会捕获到所有实现了 ApplicationEvent 接口的事件对象
		@Component
		public class ApplicationListener implements org.springframework.context.ApplicationListener.ApplicationListener{

			@Override
			public void onApplicationEvent(ApplicationEvent event) {
				System.err.println(event.getSource() + ":" + event.getClass().getName());
			}
		}

	
