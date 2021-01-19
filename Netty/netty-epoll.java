------------------------
epoll					|
------------------------
	# 只能在Linux系统下运行的高性能io模式
	# 参考
		https://github.com/netty/netty/wiki/Native-transports
	
	# 需要替换的组件
		NioEventLoopGroup		→ EpollEventLoopGroup
		NioEventLoop			→ EpollEventLoop

		NioServerSocketChannel	→ EpollServerSocketChannel
		NioSocketChannel		→ EpollSocketChannel
	
	# 需要的依赖
		<dependency>
			<groupId>io.netty</groupId>
			<artifactId>netty-transport-native-epoll</artifactId>
			<version>4.1.34.Final</version>
		</dependency>
		<build>
			<extensions>
				<extension>
					<groupId>kr.motd.maven</groupId>
					<artifactId>os-maven-plugin</artifactId>
					<version>1.6.2</version>
				</extension>
			</extensions>
		</build>
	
	# 可以通过静态工具类:Epoll 判断是否支持epoll
		boolean isAvailable();	
			* 判断Epoll是否可用
		
		void ensureAvailability();
		Throwable unavailabilityCause();