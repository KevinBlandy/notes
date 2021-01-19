-------------------------
attr					 |
-------------------------
	# 类库
		Constant
			|-AbstractConstant
				|-AttributeKey
		ConstantPool
		AttributeMap
			|-DefaultAttributeMap
				|-AbstractChannel
				|-AbstractChannelHandlerContext
			|-Channel
			|-ChannelHandlerContext
		
		Attribute
	
	# ConstantPool
		* 维护了一个 ConcurrentHashMap 
			private final ConcurrentMap<String, T> constants = PlatformDependent.newConcurrentHashMap();
		
	
	# AttributeMap
		* 一个抽象的接口, Channel 实现了
			<T> Attribute<T> attr(AttributeKey<T> key);
			<T> boolean hasAttr(AttributeKey<T> key);
	
	# Attribute
		* 表示Value的接口
			AttributeKey<T> key();
			T get();
			void set(T value);
			T getAndSet(T value);
			T setIfAbsent(T value);
			boolean compareAndSet(T oldValue, T newValue);

	# ChannelHandlerContext 和 Channel 的 attr 没有区别
		Channel.attr() == ChannelHandlerContext.attr()

		* ChannelHandlerContext.attr(),已经废弃,建议使用 Channel 的 attr
		


------------------------
AttributeKey			|
------------------------
	# 用于创建一个key对象,泛型表示该key对应的Value类型
	# AttributeKey是基于ConstantPool进行缓存的

	public static <T> AttributeKey<T> valueOf(String name)
		* 采用类乐观锁的方式,当 constant != null时,那么返回已经插入的 constant
		* 如果 name 不存在就创建一个
		* 且多线程随先创建返回谁

	public static <T> AttributeKey<T> newInstance(String name)
		* 采用类乐观锁的方式,当 constant != null时,直接抛出异常
		* 如果name存在就抛出异常
		* 多线程创建,除了成功创建的那个线程外,其他线程抛出异常
	
	public static boolean exists(String name)

	public static <T> AttributeKey<T> valueOf(Class<?> firstNameComponent, String secondNameComponent)
		* 底层还是调用 valueOf(String name),
		* 在 firstNameComponent 和 secondNameComponent  之间添加了 '#' 字符串(命名空间的感觉)

