--------------------------------------
AtomicIntegerFieldUpdater			  |
--------------------------------------
	# Integer 字段的原子修改器
		* 它是一个抽象类
		* 主要就是通过该修改器去修改指定对象的指定 int 字段值(使用反射)
		* 在多线程环境下,这种修改是原子性的(CAS算法)

	# 静态的工厂方法
		<U> AtomicIntegerFieldUpdater<U> newUpdater(Class<U> tclass, String fieldName)
			* tclass 指定类
			* fieldName 字段的属性必须是 int,不能是 Integer,而且不能是 static 的
			* 注意,该字段必须使用 protected 及其以上的权限修饰符,并且必须添加:volatile 修饰符

	# 实例方法
		int accumulateAndGet(T obj, int x, IntBinaryOperator accumulatorFunction)
		int addAndGet(T obj, int delta)

		boolean compareAndSet(T obj, int expect, int update)
			* obj 对象
			* expect 原始值
			* update 新的值
			* 如果修改成功,返回 true

		int decrementAndGet(T obj)

		int get(T obj)
		int getAndAccumulate(T obj, int x,IntBinaryOperator accumulatorFunction)
		int getAndAdd(T obj, int delta)
		int getAndDecrement(T obj)
		int getAndIncrement(T obj)
		int getAndSet(T obj, int newValue)
		int getAndUpdate(T obj, IntUnaryOperator updateFunction)

		int incrementAndGet(T obj)
		void lazySet(T obj, int newValue)
		void set(T obj, int newValue)
		int updateAndGet(T obj, IntUnaryOperator updateFunction)
		boolean weakCompareAndSet(T obj, int expect, int update)
	
	
	# demo
		class Foo {
			protected volatile int value = 0;
			public int getValue() {
				return value;
			}
			public void setValue(int value) {
				this.value = value;
			}
		}

		public static void casUpdate(Foo foo) {
			AtomicIntegerFieldUpdater<Foo> updater = AtomicIntegerFieldUpdater.newUpdater(Foo.class, "value");
			for (int x = 0 ;x < 100 ;x ++) {
				new Thread(() -> {
					while(true) {
						// 原始值
						int value = foo.getValue();
						// 新值
						int newValue = value + 1;
						// 原子更新
						boolean result = updater.compareAndSet(foo, value, newValue);
						if(result) {
							break;	// 更新失败,进行自旋
						}
					}
				}) .start();
			}
		}
	

--------------------------------------
还提供的其他修改器					  |
--------------------------------------
	# 几乎都是相同的aip,主要是针对的字段类型不同

	AtomicReferenceFieldUpdater
		<U,W> AtomicReferenceFieldUpdater<U,W> newUpdater(Class<U> tclass, Class<W> vclass,String fieldName) 

		* 对象引用字段的原子修改器

	AtomicLongFieldUpdater
		<U> AtomicLongFieldUpdater<U> newUpdater(Class<U> tclass,String fieldName)

		* long 属性字段的原子修改器
