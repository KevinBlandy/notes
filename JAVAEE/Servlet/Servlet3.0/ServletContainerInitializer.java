-----------------------------------
ServletContainerInitializer			|
-----------------------------------

	# 主要用于在容器启动阶段通过编程风格注册Filter, Servlet以及Listener,以取代通过web.xml配置注册

	# Servlet容器启动会扫描，当前应用里面每一个'jar'包的 ServletContainerInitializer 的实现
		*  注意,是jar

	# 提供 ServletContainerInitializer 的实现类特殊限制
		* 必须创建文件:META-INF/services/javax.servlet.ServletContainerInitializer
		* 文件的内容就是 ServletContainerInitializer实现类的全类名
	
	# 可以给ServletContainerInitializer 的实现类添加 @HandlesTypes 注解
		* 在其onStartup 方法上便可以得到我们感兴趣的类
		* 容器会将当前应用中所有这一类型(继承或者实现)的类放在 ServletContainerInitializer 接口的集合参数c中传递进
		* 果不定义处理类型,或者应用中不存在相应的实现类,则集合参数c为空
	
	# 它优先于 Listenner 执行

	# Demo
		import java.util.Set;

		import javax.servlet.ServletContainerInitializer;
		import javax.servlet.ServletContext;
		import javax.servlet.ServletException;
		import javax.servlet.annotation.HandlesTypes;

		@HandlesTypes(Foo.class)
		public class ApplicationServletContainerInitializer implements ServletContainerInitializer{

			@Override
			public void onStartup(Set<Class<?>> c, ServletContext ctx) throws ServletException {
				//c 里面就是 foo所有的子类
			}
		}
	
	# SpringBoot 支持war的原理就是如此


--------------------
ServletContext		|
--------------------
