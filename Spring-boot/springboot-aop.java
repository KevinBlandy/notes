-----------------------
Spring boot-AOP			|
-----------------------
	1,引入依赖
		<dependency>  
			<groupId>org.springframework.boot</groupId>  
			<artifactId>spring-boot-starter-aop</artifactId>  
		</dependency>  
	
	2. 开启动态代理
		@EnableAspectJAutoProxy
			boolean proxyTargetClass() default false;
				* 控制Aop的实现方式
				* 为true 的话使用cglib,为false的话使用java的Proxy

			boolean exposeProxy() default false;
				* 是否暴露代理类，如果为true，则会把代理类存储在 ThreadLocal 中
				* 解决一个类，内部方法互相调用不走代理的场景，默认为 false.
					AopContext.currentProxy()
						* 获取到当前代理对象的实例
				
				* 使用
					public void foo (){
						// 强制转换为当前类，这个类就是代理后的对象，然后执行本类方法
						((Demo)AopContext.currentProxy()).bar();
					}
	

	3,在需要执行AOP的地方跟Spring一样的操作
		@Aspect  
		@Component  
		@Befor
		...
	