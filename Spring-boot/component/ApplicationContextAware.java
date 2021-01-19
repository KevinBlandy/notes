------------------------------
ApplicationContextAware		  |
------------------------------
	# Spring会调用该接口的实现,把ApplicatioContext注入进来
		
		public interface ApplicationContextAware extends Aware {
			void setApplicationContext(ApplicationContext applicationContext) throws BeansException;
		}
	
