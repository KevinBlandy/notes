--------------------------------
WebSocket Java服务端			|
--------------------------------
	# 创建端点的两种方式
		1,添加注解 @ServerEndpoint
			* 直接通过注解就可以映射URI,不需要 ServerApplicationConfig 实现的存在
			* 不需要实现 MessageHandler 就可以处理数据

		2,实现抽象类 Endpoint
			* 必须要通过配置类(ServerEndpointConfig)来完成URI的映射,也就是必须要 ServerApplicationConfig 的实现来完成
			* 必须要自定义 MessageHandler 来处理数据

	# 创建端点的过程
		1,容器先会扫描所有 @ServerEndpoint 注解类,以及 ServerEndpointConfig 子类
		2,再扫描 ServerApplicationConfig 实现,并且会调实现的俩方法,来确定当前系统的所有端点
			* 可以允许有多个 ServerApplicationConfig 的实现存在,都会调用其实现的方法
		
	
	# 工作图解			
									<-		RemoteEndpoint
									|					|
		Client   <------------- SESSION				Server
									|					|
									->		MessageHandler