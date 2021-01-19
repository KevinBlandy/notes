---------------------------------
websocket						 |
---------------------------------
	# 类库
		WebSocketFrame
			|-BinaryWebSocketFrame			
			|-CloseWebSocketFrame
			|-ContinuationWebSocketFrame
			|-PingWebSocketFrame
			|-PongWebSocketFrame
			|-TextWebSocketFrame
		WebSocketProtocolHandler(自动处理webSocket的ping pong等消息)
			|-WebSocketServerProtocolHandler(简化开发提供的handler)
		WebSocketServerCompressionHandler(WebSocket消息压缩)
		WebSocketServerProtocolHandshakeHandler(WebSocket协议的握手实现)
		WebSocketServerHandshaker(握手处理器)
		WebSocketServerHandshakerFactory(生成握手器的工厂类)
		WebSocketDecoderConfig(解码相关的配置)
		WebSocketCloseStatus(WebSocket的状态码封装)
		
			
	# WebSocketFrame
		* ws消息类型的抽象类,提供了N个实现,表示不同的消息类型
		* 看子类,类名就知道是啥消息的实现了
	
	# WebSocketServerCompressionHandler
		* 提供了对websocket消息的压缩
	
	# WebSocketDecoderConfig
		* websocket解码相关的配置
		* 实用Builder模式构建
			WebSocketDecoderConfig webSocketDecoderConfig = WebSocketDecoderConfig.newBuilder()
				.allowExtensions(true)				 // 是否允许扩展
				.allowMaskMismatch(false)			// 是否要接受未实现标准规范的数据帧
				.closeOnProtocolViolation(true)		// 是否在协议错误(冲突)的情况下关闭(默认true)
				.expectMaskedFrames(true)			// 服务端必须设置为true(如果是客户端, 必须设置为false)
				.maxFramePayloadLength(1024)		// 最大的消息体体积
				.build();
			
			private int maxFramePayloadLength = 65536;
			private boolean expectMaskedFrames = true;
			private boolean allowMaskMismatch;
			private boolean allowExtensions;
			private boolean closeOnProtocolViolation = true;

			
	# WebSocketCloseStatus
		* websocket状态码的封装
			private final int statusCode;			// 状态码
			private final String reasonText;		// 原因
		    private String text;					// this.text = text = code() + " " + reasonText();

		* 构造函数
			public WebSocketCloseStatus(int statusCode, String reasonText)
		
		* 静态方法
			public static WebSocketCloseStatus valueOf(int code)
				* 根据code获取现有的静态实现
				* 如果不存在, 则创建:
					return new WebSocketCloseStatus(code, "Close status #" + code);
				
			public static boolean isValidStatusCode(int code)
				* 判断指定的状态码是否是一个不合法的状态码


		* 提供了N多的的静态实现
			public static final WebSocketCloseStatus NORMAL_CLOSURE = new WebSocketCloseStatus(1000, "Bye");
			...
		

-------------------------------------
WebSocketServerProtocolHandler		 |
-------------------------------------
	# 最重的一个封装,完成了N多的工作
	# 负责处理:握手,控制帧(ping/pong/close),文本消息,二进制消息....
	# 构造函数(版本迭代, 构造函数的形参也许不正确了, 但是形参列表的始终正确)
		WebSocketServerProtocolHandler(String websocketPath)
		WebSocketServerProtocolHandler(String websocketPath, long handshakeTimeoutMillis)
		WebSocketServerProtocolHandler(String websocketPath, boolean checkStartsWith)
		WebSocketServerProtocolHandler(String websocketPath, String subprotocols)
		WebSocketServerProtocolHandler(String websocketPath, String subprotocols,boolean allowExtensions) 
		WebSocketServerProtocolHandler(String websocketPath, String subprotocols,boolean allowExtensions, int maxFrameSize)
		WebSocketServerProtocolHandler(String websocketPath, String subprotocols,boolean allowExtensions, int maxFrameSize, boolean allowMaskMismatch)
		WebSocketServerProtocolHandler(String websocketPath, String subprotocols,boolean allowExtensions, int maxFrameSize, boolean allowMaskMismatch, boolean checkStartsWith)
		WebSocketServerProtocolHandler(String websocketPath, String subprotocols,boolean allowExtensions, int maxFrameSize, boolean allowMaskMismatch, boolean checkStartsWith, boolean dropPongFrames)
		
		websocketPath
			* 提供服务的uri

		subprotocols
			* 支持的子协议列表

		allowExtensions
			* 是否允许扩展

		maxFrameSize
			* 消息帧最大字节数

		allowMaskMismatch
			* 是否要接受未实现标准规范的数据帧

		checkStartsWith
			* 如果为true, 则使用startWith 检测 uri, 否则使用quals
			* 如果为false, 则必须要求uri跟websocket一摸一样
			
		dropPongFrames
			* 忽略pong信息
		
		handshakeTimeoutMillis
			* websocket握手超时,
			* 默认: private static final long DEFAULT_HANDSHAKE_TIMEOUT_MS = 10000L;
		
		decoderConfig
			* WebSocketDecoderConfig 类
			* WebSocket解码相关的配置
	
	# 它会把websocket的数据编码为 WebSocketFrame 对象,交给下一个Handler去处理

		

	
---------------------------------
服务端							 |
---------------------------------
import java.util.Locale;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

public class Server {

	static final String ENDPOINT = "/channel";

	public static void main(String[] args) throws InterruptedException {
		EventLoopGroup bossGroup = new NioEventLoopGroup(1);
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap serverBootstrap = new ServerBootstrap();
			serverBootstrap.group(bossGroup, workerGroup);
			serverBootstrap.channel(NioServerSocketChannel.class);
			serverBootstrap.handler(new LoggingHandler(LogLevel.INFO));
			serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel socketChannel) throws Exception {
					ChannelPipeline pipeline = socketChannel.pipeline();
					pipeline.addLast(new HttpServerCodec());
					pipeline.addLast(new ChunkedWriteHandler());
					pipeline.addLast(new HttpObjectAggregator(65536));
					pipeline.addLast(new ChannelInboundHandlerAdapter() {
						@Override
						public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
							if(msg instanceof FullHttpRequest) {
								FullHttpRequest fullHttpRequest = (FullHttpRequest) msg;
								String uri = fullHttpRequest.uri();
								if (!uri.equals(ENDPOINT)) {
									// 访问的路径不是 websocket的端点地址，响应404
									ctx.channel().writeAndFlush(new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.NOT_FOUND))
										.addListener(ChannelFutureListener.CLOSE);
									return ;
								}
							}
							super.channelRead(ctx, msg);
						}
					});
					pipeline.addLast(new WebSocketServerCompressionHandler());
					pipeline.addLast(new WebSocketServerProtocolHandler(ENDPOINT, null, true));	// uri,子协议,是否支持扩展
					pipeline.addLast(new SimpleChannelInboundHandler<WebSocketFrame>() {
						@Override
						protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame msg) throws Exception {
							if (msg instanceof TextWebSocketFrame) { // 文本消息处理
								String request = ((TextWebSocketFrame) msg).text();
								System.out.println("服收到消息:" + request);
								ctx.channel().writeAndFlush(new TextWebSocketFrame(request.toUpperCase(Locale.CHINA)));
							} else {	// 其他类型的消息
								String message = "不支持的消息类型: " + msg.getClass().getName();
								throw new UnsupportedOperationException(message);
							}
						}
						// 握手结果事件通知
						@Override
						public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
							if(evt instanceof HandshakeComplete) {
								HandshakeComplete handshakeComplete = (HandshakeComplete) evt;
								String uri = handshakeComplete.requestUri();
								HttpHeaders httpHeaders = handshakeComplete.requestHeaders();
								String selectedSubprotocol = handshakeComplete.selectedSubprotocol();
								LOGGER.debug("HandshakeComplete uri={},headers={},selectedSubprotocol={}",uri,httpHeaders,selectedSubprotocol);
							}
							super.userEventTriggered(ctx, evt);
						}
					});
				}
			});
			Channel channel = serverBootstrap.bind(1024).sync().channel();
			channel.closeFuture().sync();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
}

	
---------------------------------
在握手前处理uri参数等等			 |
---------------------------------
	# 思路
		* 在 HttpObjectAggregator 之后添加一个Handler,用于获取到完整的http请求信息:FullHttpRequest
		* 可以通过FullHttpRequest获取到uri,header,method,query param等信息
		* 自己可以决定是响应异常信息,还是执行下一个handler,也就是ws握手

	# 代码
		pipeline.addLast(new HttpObjectAggregator(65536));
		pipeline.addLast(new ChannelInboundHandlerAdapter() {
			@Override
			public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
				if(msg instanceof FullHttpRequest) {
					FullHttpRequest fullHttpRequest = (FullHttpRequest) msg;
					
					// 获取到请求的uri,里面包含了请求路径和请求参数
					String uri = fullHttpRequest.uri();

					fullHttpRequest.setUri(ENDPOINT); // 一定要设置uri跟websocket的端点匹配
				}
				super.channelRead(ctx, msg);
			}
		});
		pipeline.addLast(new WebSocketServerCompressionHandler());
		pipeline.addLast(new WebSocketServerProtocolHandler(ENDPOINT, null, true));	// uri,子协议,是否支持扩展
