--------------------------
ScopedValue
--------------------------

	public T get() 
	public boolean isBound() 
	public T orElse(T other)
	public <X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier) throws X 



	public static <T> ScopedValue<T> newInstance()

	public static <T, R> R callWhere(ScopedValue<T> key,
										 T value,
										 Callable<? extends R> op) throws Exception
	public static <T> void runWhere(ScopedValue<T> key, T value, Runnable op)

	public static <T, R> R getWhere(ScopedValue<T> key,
										T value,
										Supplier<? extends R> op) 

	public static <T> Carrier where(ScopedValue<T> key, T value)
		* 设置上下文，返回 Carrier


----------------------------------------------------
public static final class Carrier
----------------------------------------------------
	public <R> R call(Callable<? extends R> op) throws Exception
	public <T> T get(ScopedValue<T> key)
	public <R> R get(Supplier<? extends R> op)
	public void run(Runnable op)
	public <T> Carrier where(ScopedValue<T> key, T value) 


--------------------------
demo
--------------------------
	# 基本使用

		public class ScopeTest {

			public static final ScopedValue<String> CONTEXT = ScopedValue.newInstance();
			
			public static void main(String[] args) throws InterruptedException {
				
				
				// 虚拟线程
				var vt = Thread.startVirtualThread(() -> {
					/**
					 * 创建了一个 Scope ，指定其值为 "Hi"
					 */
					ScopedValue.runWhere(CONTEXT, "Hi", () -> {
						// 在这个 Scope 中 CONTEXT 获取其值都为 "Hi" 
						foo();
						new ScopeTest().bar();
					});
				});
				
				/**
				 * 创建了一个 Scope，指定其值为 "Hello"
				 */
				ScopedValue.runWhere(CONTEXT, "Hello", () -> {
					// 在这个 Scope 中 CONTEXT 获取其值都为 "Hello" 
					foo();
					new ScopeTest().bar();
					
					// 覆盖
					try {
						var ret = ScopedValue.callWhere(CONTEXT, "Hello 2", () -> {
							// 在这个 Scope 中 CONTEXT 获取其值都为 "Hello 2" 
							return CONTEXT.get();
						});
						System.out.println("覆写 CONTEXT 后的结果：" + ret);
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				});
				
				vt.join();
			}
			
			public static void foo () {
				System.out.println("Foo 静态方法：" + CONTEXT.get());
			}
			
			public void bar () {
				System.out.println("Bar 实例方法：" + CONTEXT.get());
			}
		}

	
	# 同时设置多个值
		ScopedValue.where(CONTEXT, "ctx")
				.where(TIME, Instant.now())
				.where(MEMBER_ID, 10010L)
				.run(() -> {
					System.out.println("CONTEXT:" + CONTEXT.get());
					System.out.println("TIME:" + TIME.get());
					System.out.println("MEMBER_ID:" + MEMBER_ID.get());
				});
				;
