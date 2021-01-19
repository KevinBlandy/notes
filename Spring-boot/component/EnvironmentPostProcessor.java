-------------------------
动态扩展环境
-------------------------
	# 在使用spring boot做开发时，有时我们需要自定义环境变量或者编写第三方扩展点，
	# 可以使用 EnvironmentPostProcessor (只是基本的使用环境，就不需要)，在spring上下文构建之前可以设置一些系统配置

	# 而且建议实现类添加  org.springframework.core.Ordered 注解，多个实现实例也会按照@Order注解的顺序去被调用
	
	# 需要实现接口 EnvironmentPostProcessor
		@FunctionalInterface
		public interface EnvironmentPostProcessor {
			void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application);
		}
	
	# 在 META-INF/spring.factories 文件中声明
		org.springframework.boot.env.EnvironmentPostProcessor=[实现类全路径]