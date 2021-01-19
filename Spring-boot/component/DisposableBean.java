------------------------------------
BeanPostProcessor					|
------------------------------------
	# Bean 生命周期中的一个接口
	# 如果Bean实现了该接口,在ApplicationContext关闭的时候,会调用抽象方法
		public interface DisposableBean {
			void destroy() throws Exception;
		}
