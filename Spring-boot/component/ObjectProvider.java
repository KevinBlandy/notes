---------------------
ObjectProvider
---------------------
	# Spring4.3提供的东西，ObjectProvider 接口 是ObjectFactory接口的扩展，专门为注入点设计的，可以让注入变得更加宽松和更具有可选项
	# 如果待注入参数的Bean为空或有多个时，便是ObjectProvider发挥作用的时候了
		1. 如果注入实例为空时，使用 ObjectProvider 则避免了强依赖导致的依赖对象不存在异常；
		2. 如果有多个实例，ObjectProvider 的方法会根据Bean实现的Ordered接口或 @Order 注解指定的先后顺序获取一个Bean。从而了提供了一个更加宽松的依赖注入方式。

	
	# interface ObjectProvider<T> extends ObjectFactory<T>, Iterable<T>

		T getObject(Object... args) throws BeansException;
			* 返回指定类型的bean, 如果容器中不存在, 抛出NoSuchBeanDefinitionException异常
			* 如果容器中有多个此类型的bean, 抛出NoUniqueBeanDefinitionException异常

		@Nullable
		T getIfAvailable() throws BeansException;
			*  如果指定类型的bean注册到容器中, 返回 bean 实例, 否则返回 null
		
		T getIfAvailable(Supplier<T> defaultSupplier) throws BeansException 
			* 如果返回对象不存在，则进行回调，回调对象由 Supplier 传入

		void ifAvailable(Consumer<T> dependencyConsumer) throws BeansException
			* 消费对象的一个实例（可能是共享的或独立的），如果存在通过Consumer回调消耗目标对象。

		@Nullable
		T getIfUnique()
			*  如果不可用或不唯一（没有指定primary）则返回null。否则，返回对象。
		
		T getIfUnique(Supplier<T> defaultSupplier) throws BeansException 
			* 如果存在唯一对象，则调用Supplier的回调函数
		
		void ifUnique(Consumer<T> dependencyConsumer) throws BeansException
			* 如果存在唯一对象，则消耗掉该对象
		
		Iterator<T> iterator()
			* 返回符合条件的对象的Iterator，没有特殊顺序保证（一般为注册顺序）
		
		Stream<T> stream()
			* 返回符合条件对象的连续的Stream，没有特殊顺序保证（一般为注册顺序）

		Stream<T> orderedStream()
			* 返回符合条件对象的连续的Stream。在标注Spring应用上下文中采用@Order注解或实现Order接口的顺序
