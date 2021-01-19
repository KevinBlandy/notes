BeanPostProcessor
	# 后置处理器,一个接口
	# 实现方法
		public Object postProcessAfterInitialization(Object obj, String str);
		public Object postProcessBeforeInitialization(Object obj, String str);
	# 在Bean的初始化方法执行的前后,都会把Bean传递给对应的方法,我们可以自己进行一些特殊的处理
	# 第一个参数就是Bean实体,第二个参数是Bean的ID或者是name

ApplicationContextAware
	# 一个接口
	# 实现方法
		public void setApplicationContext(ApplicationContext applicationContext)
	# 实现该接口后,并且交给IOC管理后.IOC会调用 setApplicationContext 方法把IOC容器注入进来

InitializingBean
	# 一个接口
	# 实现方法
		public abstract void afterPropertiesSet()throws Exception;
	# 由IOC所管理的Bean实现,在Bean初始化完成,所有的属性都注入完毕后会调用该方法
	# 它的优先级别 init-method 更大,它执行完毕后才会执行 init-method

DisposableBean
	# 一个接口
	# 实现方法
		 void destroy() throws Exception;
	# 由IOC所管理的Bean实现,当IOC容器被关闭的时候,也就是容器销毁的时候,会调用 destroy 方法
	# 它的优先级比 destory-method 更大,他会先执行完毕后才回去执行 destory-method
	# 必须要保证的是IOC容器调用了 close 方法,才会去执行
	# 而且销毁方法是IOC容器执行关闭,有关闭功能的容器对象是ConfigurableApplicationContext.是 ApplictionContext的一个子接口