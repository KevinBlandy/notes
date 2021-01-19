----------------------------
Netty-入门					|
----------------------------
	# netty是最流行的NIO框架,他的健壮性,功能,性能,可定制性和可扩展性.在同类框架中都是首屈一指的
	# 它已经得到成百上千的商业/商业项目验证
		* Hadoop的RPC框架-Avro
		* JMS框架
		* RocketMQ
		* Dubbox
	
	# 官网地址
		https://netty.io
		https://github.com/netty/netty/
		https://netty.io/4.1/api/index.html
		https://github.com/netty/netty/tree/4.0/example/src/main/java/io/netty/example

	# 学习站点
		https://waylau.gitbooks.io/netty-4-user-guide/
		https://waylau.gitbooks.io/essential-netty-in-action/

		http://www.importnew.com/17647.html
		http://ifeve.com/netty-in-action/
		http://ifeve.com/netty5-user-guide/
		http://www.cnblogs.com/zou90512/p/3492287.html

		http://www.52im.net/thread-2043-1-1.html
	
	# 为什么放弃Netty5
		https://github.com/netty/netty/issues/4466

		* 使用ForkJoinPool极大的增加了复杂性,但是没有明显的性能提升




----------------------------
Netty-HelloWorld			|
----------------------------
	
	# Netty实现通信的步骤
		1,创建两个NIO线程组,一个用于网络事件的处理(接受客户端的连接),另一个则进行网络通信的读写

		2,创建 ServerBootstrap 对象,配置Netty的一系列参数,例如传出数据的缓存大小等

		3,创建一个实际处理数据的类 ChannelInitializer ,进行初始化的准备工作.比如,设置接受传出数据的字符集,格式,以及实际处理数据的接口

		4,绑定端口,执行同步阻塞方法等待服务器成功启动



----------------------------
征途						|
----------------------------
	# attr
	# 流量整形
	# buffer
	# Promise
	# 工具类
		* FastThreadLocal
		* Recycler
