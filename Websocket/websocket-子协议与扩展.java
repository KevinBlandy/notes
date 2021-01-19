--------------------------------
WebSocket - 子协议与		
--------------------------------
	# 所有的WebSocket都会定义一些特定于具体应用的消息格式,编排,这就是子协议,
	# WebSocket 的子协议使用字符串来标识
	# 在 www.iana.org 上已经定义好了N多子协议
	# 获取端点支持的子协议列表
		List<String> getSubprotocols();		//ServerEndpointConfig
	


--------------------------------
WebSocket - 扩展				|
--------------------------------
	# 获取端点支持的扩展列表
		 List<Extension> getExtensions();	//ServerEndpointConfig
		
	# Extension
		* 是扩展属性的一个描述对象
		* 源码
			public interface Extension {
				String getName();
				List<Parameter> getParameters();
				interface Parameter {
					String getName();
					String getValue();
				}
			}

--------------------------------
ServerEndpointConfig.Configurator|
--------------------------------
	# ServerEndpointConfig.Configurator
	# 该类定义了客户端如何与服务端进行握手
	# 如果没有显示的给应用进行配置,则会使用默认的
		* ServerEndpointConfig.Configurator

	# 注解式配置
		@ServerEndpoint(configurator=...)

	# 编程式配置
		* 在ServerApplicationConfig中构造出 ServerEndpointConfig 后进行配置
		ServerEndpointConfig.Builder.configurator(new ServerEndpointConfig.Configurator());
	
