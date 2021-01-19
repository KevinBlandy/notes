-------------------------------
websocket-对象编码				|
-------------------------------	
	# 把客户端传递的信息,编码为我们自定义的对象
	# 自定义对象编码器,需要是实现接口 javax.websocket.Decoder 中定义的接口
	# 返回的泛型 T,就是最终编码的后的对象
		* Decoder 接口方法
			void init(EndpointConfig config);
				* 在连接建立的时候执行
			void destroy();
				* 在连接关闭的时候执行
		* Dcoder 子接口
			//把字符串消息转换为 JAVA对象
			interface Text<T> extends Decoder {
				T decode(String s) throws DecodeException;
				boolean willDecode(String s);
			}
			//把字符串流消息转换为JAVA对象
			interface TextStream<T> extends Decoder {
				T decode(Reader reader) throws DecodeException, IOException;
			}
			//把二进制数据转换为JAVA对象
			interface Binary<T> extends Decoder {
				T decode(ByteBuffer bytes) throws DecodeException;
				boolean willDecode(ByteBuffer bytes);
			}
			//把二进制流数据转换为JAVA对象
			interface BinaryStream<T> extends Decoder {
				T decode(InputStream is) throws DecodeException, IOException;
			}
	
	# WebSocket API 为基本数据类型(以及封装类),提供了默认的编码器
		@OnMessage
		public void onMessage(Integer mesage){
			//原String类型的message会被转换为 Integer 类型,等价于 new Integer(message);
			System.out.pritnln("收到消息:" + message);
		}
	
	# 注解式端点配置解码器
		@ServerEndpoint(value="/test",decoders={xxx.Class})
	
	# 编程式端点配置解码器
		ServerEndpointConfig.Builder.create(XxxxEndPoint.class, "/test").decoders(coders).build();
		* decoders,是一个集合 List<Class<? extends Decoder>> decoders
	
	# 使用
		* 在注解式中使用,不说了.直接把对象放入形参.就会自动的进行封装
		* 在编码式端点中使用
			* 在编码式中以什么方式接收数据类型,取决于 MessageHandler 接口和它的子类型
			* 通过泛型 T 来定义你要接收啥类型,就是我们自定义的Object
				String,
				Reader,
				Byte[],
				ByteBuffer,
				InputStream,
				Object,(自定义的对象)
			//消息未分片形接收器
			interface Whole<T> extends MessageHandler {
				void onMessage(T message);
			}
			//消息分片形接收器
			interface Partial<T> extends MessageHandler {
				void onMessage(T partialMessage, boolean last);
			}
			
			* 添加 Handler 到 session
				session.addMessageHandler(MessageHandler handler);
			
-------------------------------
websocket-对象解码				|
-------------------------------
	# 把我们响应给客户端的POJO,解码为二进制信息或者是文本信息
	# 如果传入的是Java基本类型(或者其装箱类型),则会被转换为一个字符串(toString())
	# 如果是自定义的 Object 类型,那么需要准备解码器,实现接口 java.websocket.Encoder 中定义的接口
	# 泛型T,就是你自定义的 Object 类型
		* Encoder 接口方法
			void init(EndpointConfig config);
				* 在连接打开的时候执行
			void destroy();
				* 在连接关闭的时候执行
		* Encoder 子接口
			//把对象编码为文本消息
			interface Text<T> extends Encoder {
				//返回值作为响应给客户端的数据
				String encode(T object) throws EncodeException;			
			}
			//把对象编码为文本流
			interface TextStream<T> extends Encoder {
				//通过 writer 把数据响应给客户端
				void encode(T object, Writer writer) throws EncodeException, IOException;
			}
			//把对象编码为二进制消息
			interface Binary<T> extends Encoder {
				//返回值作为响应给客户端的数据
				ByteBuffer encode(T object) throws EncodeException;
			}
			//把对象编码为二进制流
			interface BinaryStream<T> extends Encoder {
				//通过 OutputStream 把数据响应给客户端
				void encode(T object, OutputStream os) throws EncodeException, IOException;
			}
	
	# WebSocket API 为基本数据类型(以及封装类),提供了默认的编码器
		@OnMessage
		public Integer onMessage(Integer mesage){
			//balabala.....
		}
	

	# 注解式端点配置解码器
		@ServerEndpoint(value="/test",encoders={xxx.Class})
	
	# 编程式端点配置解码器
		ServerEndpointConfig.Builder.create(XxxxEndPoint.class, "/test").encoders(coders).build();
		* coders,是一个集合 List<Class<? extends Encoder>>
	

	# 使用
		* 异步
			Future<Void>  RemoteEndpoint.async(Object data, SendHandler handler);
			void RemoteEndpoint.async(Object data, SendHandler handler);

		* 同步
			void RemoteEndpoint.Basic.sendObject(Object data) throws IOException, EncodeException;
		
		* 直接返回(只能是注解式)
			@OnMessage 
			public MyClass onMessage(){
				///balalalal
			}


-------------------------------
websocket-编码解码器生命周期	|
-------------------------------
	# 系统启动的时候,会创建所有的 encoder以及decoder实例
	# 每个新的连接,都会创建新的实例,
	# 在第一次连接的时候调用 init 方法,在连接关闭的时候调用 destroy 方法

-------------------------------
websocket-对象编解码总结		|
-------------------------------


	1,客户端消息到服务端
		* 定义编码器,实现接口,泛型就是我们自定义的编码后的对象
		* 添加到系统
		* 添加messageHandler,传入的泛型,就是我们自定义编码后的对象
		* 系统会自动的调用编码器来对消息进行编码,结果就是我们最后的对象
	
	2,服务端到客户端
		* 定义解码器,实现接口,泛型就是我们自定义的对象
		* 添加到系统
		* 当我们直接返回该对象,或者是调用API返回对象的时候,就会调用指定的解码器去解码

	3,多个相同类型的编解码器同时存在
		# 在发送的时候出现多个相同的解码器
			* 例如:一个二进制的Banner编码器和一个文本流的Banner解码器同时存在,当响应客户端 Banner 的时候,用哪个?
			* 谁先被添加,就使用谁
		
		# 在接收的时候,出现多个相同类型的编码器
			* 如果客户端发送的是二进制数据,
			* 则会在decoders中匹配到可以处理二进制流的'第一个'编码器,并且检查它的willDecode是否为 true,如果为 false,则继续在剩下的列表中寻找
			* 文本消息同理
			* 说白了,先根据客户端传递数据的类型来进行匹配(注意 willDecode)...有多个,再根据编码器的排序来决定

	