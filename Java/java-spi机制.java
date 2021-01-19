------------------------
spi机制					|
------------------------
	# Service Provider Interface
		* 我感觉就是动态的加载实现类
	
	# 工作流程
									 -------------------- [实现A]
									|
		[调用] -> [接口] -> [本地服务发现加载]
									|
									 -------------------- [实现B]

	# 实现
		* 在jar的目录创建文件:META-INF/services/<接口全限定名>
		* 文件内容就是接口实现类的全路径
		* 例如 mysql-jdbc 的实现，可以有多个，使用换行隔开
			/META-INF/services/java.sql.Driver
			com.mysql.cj.jdbc.Driver
			

	
	# 通过 java.util.ServiceLoder 动态装载实现模块
		* 它通过扫描META-INF/services目录下的配置文件找到实现类的全限定名, 把类加载到JVM4
		* SPI的实现类必须携带一个不带参数的构造方法(反射创建)
	
		
		// 通过参数指定SPI的接口
		ServiceLoader<Driver> serviceLoader = ServiceLoader.load(Driver.class);
		// 返回SPI接口的实现，迭代器
        Iterator<Driver> driverIterator = serviceLoader.iterator();
        while (driverIterator.hasNext()){
			// 遍历每一个实现
            Driver driver = driverIterator.next();
            System.out.println(driver);
        }

		/*
			org.postgresql.Driver@5479e3f
			com.alibaba.druid.proxy.DruidDriver@7bfcd12c
			com.alibaba.druid.mock.MockDriver@42f30e0a
			com.mysql.cj.jdbc.Driver@46f5f779
		*/
	
	# 缺点
		* 虽然ServiceLoader也算是使用的延迟加载, 但是基本只能通过遍历全部获取, 也就是接口的实现类全部加载并实例化一遍
		* 如果并不想用某些实现类, 它也被加载并实例化了, 这就造成了浪费
		
		* 获取某个实现类的方式不够灵活, 只能通过Iterator形式获取, 不能根据某个参数来获取对应的实现类
		* 多个并发多线程使用ServiceLoader类的实例是不安全的
	
	# 查看 JDBC 的加载过程
		DriverManager.setLogWriter(new PrintWriter(System.out));
		DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/demo?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC&allowMultiQueries=true", "root", "root");
		/*
			DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/demo?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC&allowMultiQueries=true")
				trying org.postgresql.Driver
				trying com.alibaba.druid.proxy.DruidDriver
				trying com.alibaba.druid.mock.MockDriver
				trying com.mysql.cj.jdbc.Driver
			getConnection returning com.mysql.cj.jdbc.Driver
		*/

------------------------
ServiceLoader			|
------------------------
	# JDK6的东西
		final class ServiceLoader<S> implements Iterable<S>
	
	# 静态工厂函数
		<S> ServiceLoader<S> load(Class<S> service)
		<S> ServiceLoader<S> load(Class<S> service, ClassLoader loader)
		<S> ServiceLoader<S> loadInstalled(Class<S> service)
	
	# 实例方法
		Iterator<S> iterator();
		forEach(Consumer<? super T> action)
		void reload();
		Spliterator<T> spliterator()


