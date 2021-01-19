-----------------------
tio-入门				|
-----------------------
	# 官网
	# http://www.t-io.org:9292/
	# 国产AIO通讯框架
	# Maven地址
		<dependency>
		    <groupId>org.t-io</groupId>
		    <artifactId>tio-core</artifactId>
		    <version>1.6.9.v20170408-RELEASE</version>
		</dependency>


-----------------------
tio-常用AIP体系			|
-----------------------	
	AioServer			//服务器端对象

	AioClient			//客户端对象
	
	ReconnConf			//客户端重连对象

	Node				//目标服务端点
	
	ChannelContextFilter			//函数接口,用于过滤通道

	Packet							//数据包接口
		|-EncodedPacket		

	ChannelContext					//连接管道上下文接口
		|-ClientChannelContext
		|-ServerChannelContext

	AioListener						//监听接口
		|-ClientAioListener
		|-ServerAioListener
	
	GroupContext					//GroupContext
		|-ClientGroupContext
		|-ServerGroupContext
	
	AioHandler						//Handler
		|-ClientAioHandler
		|-ServerAioHandler
	
	GroupStat						//统计插件
		|-ClientGroupStat
		|-ServerGroupStat


-----------------------
tio-客户端连接步骤		|
-----------------------
	1,创建连接节点
		Node
	2,创建客户端Handler(消息体处理,编码,解码)
		ClientAioHandler
	3,创建事件监听(非必须,可以为null)
		ClientAioListener
	4,创建重连对象(非必须,可以为null)
		ReconnConf
	5,创建客户端Group上下文
		ClientGroupContext(ClientAioHandler,ClientAioListener,ReconnConf);
	6,创建客户端对象
		AioClient(ClientGroupContext);
	7,客户端对象执行连接,获取到 ClientChannelContext 对象
		connect(Node);
	8,创建消息体
		Package
	9,使用 AIO 静态方法,完成消息发送
		Aio.send(ClientChannelContext, Package);
	
	
	//1,目标节点
	Node node = new Node("localhost",1024);
	//2,客户端Hanlder
	ClientAioHandler<Object,DataPacket,Object> clientHandler = new ClientHandler();
	//3,客户端监听
	ClientAioListener<Object,DataPacket,Object> clientAioListener = null;
	//4,客户端重连
	ReconnConf<Object,DataPacket,Object> reconnConf = null;
	//5,客户端GroupContext
	ClientGroupContext<Object,DataPacket,Object> clientGroupContext = new ClientGroupContext<Object,DataPacket,Object>(clientHandler,clientAioListener,reconnConf);
	//6,客户端对象
	AioClient<Object,DataPacket,Object> aioClient = new AioClient<Object,DataPacket,Object>(clientGroupContext);
	//7,打开连接,获取管道上下文
	ChannelContext<Object,DataPacket,Object> clientChannelContext = aioClient.connect(node);
	//8,创建数据包
	DataPacket dataPacket = new DataPacket();
	for(int x = 1;x > 0; x++){
		Thread.sleep(1000);
		dataPacket.setData(("这是客户端第"+x+"条消息").getBytes(DataPacket.CHARSET));
		//9,执行发送
		Aio.send(clientChannelContext, dataPacket);
	}

-----------------------
tio-服务端启动步骤		|
-----------------------
	1,创建服务器端Handler,(消息体处理,编码,解码)
		ServerAioHandler
	2,创建事件监听(非必须,可以为null)
		ServerAioListener
	3,创建服务端上下文
		ServerGroupContext(ServerAioHandler,ServerAioListener);
	4,创建服务端对象
		 AioServer(ServerGroupContext);
	5,启动服务端对象
		start("ip",端口);
	
	//1,创建服务器端Hanlder
	ServerAioHandler<Object, HelloPacket, Object> aioHandler = new HelloServerAioHandler();
	//2,创建事件监听
	ServerAioListener<Object, HelloPacket, Object> aioListener = null;
	//3,创建服务端上上下文
	ServerGroupContext<Object, HelloPacket, Object> serverGroupContext = new ServerGroupContext<Object, HelloPacket, Object>(aioHandler, aioListener);
	//4,创建服务端对象
	AioServer<Object, HelloPacket, Object> aioServer = new AioServer<Object, HelloPacket, Object>(serverGroupContext);
	//5,启动服务
	aioServer.start(null,1024);
