------------------------
ActiveMQ-入门			|
------------------------
	# 来自于Apache的神器
	# 官网地址
		http://activemq.apache.org/
	# 完全支持JMS1.1和JEE1.4规范的JMS实现
	
	# 端口说明
		8161
			* WEB控制台端口
		61616
			* tcp协议连接端口
		
		

------------------------
ActiveMQ-目录结构		|
------------------------
	bin		
		* 启动,服务器注册的的脚本

	conf
		* 配置文件目录,有以下核心的配置文件
		activemq.xml			//核心配置
		jetty.xml				//内置Jetty的配置文件
		jetty-realm.properties	//后台管理用户配置
	data
		* 数据存放

	docs
		* 文档

	examples
		* 案例

	lib
		* 

	webapps
		* 部署的管控台,运行启动脚本.这个管控台就会运行在Jetty容器

	webapps-demo
		* 小demo

	activemq-all-5.11.1.jar


------------------------
ActiveMQ-Consumer		|
------------------------
	* 消息接收者
	1,创建连接工厂
	2,通过工厂获取连接
	3,通过连接创建会话
	4,通过会话创建队列/主题
	5,通过会话创建消息接收者
	6,消息接收者阻塞,收到消息,获取到message
	7,获取数据


------------------------
ActiveMQ-Provider		|
------------------------
	* 消息生产者
	1,创建连接工厂
	2,通过工厂获取连接
	3,通过连接创建会话
	4,通过会话创建队列/主题

	5,通过会话创建消息生产者对象,绑定队列/主题
		* 也可以不绑定队列/主题,在执行发送的时候绑定
	6,设置消息生产者对象的部分属性
		* 也可以在执行发送的时候设置
	7,创建消息
		* 消息类型有N多种,根据需要创建
	8,通过消息生产者执行发送操作
		* 可以在这里绑定队列/主题,各种属性
	9,关闭资源,仅仅需要关闭连接即可,会自动关闭相关的连接





------------------------
ActiveMQ-Linux管理		|
------------------------
	./activemq start							//启动服务
	./activemq start > /tpm/activemqlog			//启动服务,并且指定日志文件
	./activemq start xbean:file:../../my.xml	//手动指定配置文件

	./activemq stop								//关闭节点,或者说直接暴力kill





------------------------
ActiveMQ-JAVA内嵌Broker	|
------------------------
	# JAVA main
		public class Broker {
			public static void main(String[] args) throws Exception {
				BrokerService broker = new BrokerService();
				broker.setUseJmx(Boolean.TRUE);
				broker.addConnector("tcp://localhost:61616");
				broker.start();
			}
		}
	
	# 集成spring
		* 自己去查