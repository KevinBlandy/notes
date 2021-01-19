-----------------------------------
BeanPostProcessor				   |
-----------------------------------
	# Bean 生命周期中的一个接口

	# 具有两个方法,Spring在Bean初始化之前,初始化之后调调用该实现,来对Bean进行'处理Processor'
	
		import org.springframework.beans.BeansException;
		import org.springframework.beans.factory.config.BeanPostProcessor;

		@Component
		public class ApplicationBeanPostProcessor implements BeanPostProcessor {
			@Override
			public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
				// 在初始化之后对Bean进行处理
				return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
			}
			
			@Override
			public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
				// 在初始化之前对Bean进行处理
				return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
			}
		}
