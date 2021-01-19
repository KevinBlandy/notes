------------------------------------
BeanFactoryPostProcessor			|
------------------------------------
	# bean工厂的bean属性处理容器,可以管理bean工厂内所有的beandefinition(未实例化)数据可以随心所欲的修改属性

		import org.springframework.beans.BeansException;
		import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
		import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
		import org.springframework.stereotype.Component;

		@Component
		public class ApplicationBeanFactoryPostProcessor implements BeanFactoryPostProcessor{

			@Override
			public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
			}
		}
