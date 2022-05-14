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
			private final int maxFramePayloadLength;		// 最大的消息体体积
			private final boolean expectMaskedFrames;		// Web套接字服务器必须将此设置为true，处理传入的屏蔽有效paload。客户端必须为false
			private final boolean allowMaskMismatch;		// 允许放宽对接收帧的屏蔽要求。当它被设置为false时，也没有按照标准正确屏蔽的帧仍将被接受。
			private final boolean allowExtensions;			// 是否允许使用保留的扩展位的标志。
			private final boolean closeOnProtocolViolation;	// 在任何违反协议的情况下立即发送关闭帧。
			private final boolean withUTF8Validator;		// 是否允许你避免将Utf8FrameValidator添加到管道中。
				* WebSocketServerProtocolHandler的创建。这很有用（减少开销）。
				* 当你在你的Web套接字连接中只使用二进制WebSocketFrame时，可以考虑设置为false

		* 默认实例
			    static final WebSocketDecoderConfig DEFAULT =
							new WebSocketDecoderConfig(65536, true, false, false, true, true);
		
			
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
		WebSocketServerProtocolHandler(WebSocketServerProtocolConfig serverConfig)
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
	
	# WebSocketServerProtocolConfig
		* 配置类，可以通过此配置类来完成WebSocketServerProtocolHandler的配置

		private final String websocketPath;
		private final String subprotocols;
		private final boolean checkStartsWith;
		private final long handshakeTimeoutMillis;
		private final long forceCloseTimeoutMillis;
		private final boolean handleCloseFrames;
		private final WebSocketCloseStatus sendCloseFrame;
		private final boolean dropPongFrames;
		private final WebSocketDecoderConfig decoderConfig;
		

	
---------------------------------
服务端							 |
---------------------------------

import java.util.concurrent.TimeUnit;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.SimpleUserEventChannelHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketCloseStatus;
import io.netty.handler.codec.http.websocketx.WebSocketDecoderConfig;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolConfig;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler.HandshakeComplete;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class ServerChannelInitializer extends ChannelInitializer<Channel> {
	
	public static final String PATH = "/channel/demo";

	@Override
	protected void initChannel(Channel socketChannel) throws Exception {
		ChannelPipeline pipeline = socketChannel.pipeline();
		pipeline.addLast(new HttpServerCodec());
		pipeline.addLast(new ChunkedWriteHandler());
		pipeline.addLast(new HttpObjectAggregator(65536));
		// 自定义http handler，自行处理非法请求
		pipeline.addLast(new ChannelInboundHandlerAdapter() {
			@Override
			public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
				
				if(!(msg instanceof FullHttpRequest)) {
					// 非法的http请求，直接关闭连接
					ctx.channel().close();
					return ;
				}
				
				FullHttpRequest fullHttpRequest = (FullHttpRequest) msg;
				
				// 请求端点
				String uri = fullHttpRequest.uri();
				if (!uri.equalsIgnoreCase(PATH)) {
					ctx.channel().writeAndFlush(new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.NOT_FOUND))
						.addListener(ChannelFutureListener.CLOSE);
					return ;
				}
				
				// 请求方法
				if (fullHttpRequest.method() != HttpMethod.GET) {
					ctx.channel().writeAndFlush(new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.METHOD_NOT_ALLOWED))
						.addListener(ChannelFutureListener.CLOSE);
					return ;
				}
				
				// TODO 是否是合法的WS握手请求
				
				ctx.pipeline().remove(this); // 验证完成后，从pipeline移除自己，很关键

				
				super.channelRead(ctx, msg);
			}
		});
		pipeline.addLast(new WebSocketServerCompressionHandler());
		pipeline.addLast(new WebSocketServerProtocolHandler(WebSocketServerProtocolConfig.newBuilder()
				.websocketPath(PATH)
				.handshakeTimeoutMillis(TimeUnit.SECONDS.toMillis(5))
				.forceCloseTimeoutMillis(TimeUnit.SECONDS.toMillis(5))
				.decoderConfig(WebSocketDecoderConfig.newBuilder()
						.maxFramePayloadLength(1024 * 1024 * 1) // 1Mb
						.expectMaskedFrames(true)		// 服务器必须为 true
						.allowMaskMismatch(false)
						.allowExtensions(true)				// 开启压缩，则必须开启协议扩展
						.closeOnProtocolViolation(true)	// 收到不合法的桢，直接断开连接
						.withUTF8Validator(true)	// 文本消息，添加UTF8校验器
						.build())
				.build()));
		pipeline.addLast(new SimpleChannelInboundHandler<WebSocketFrame>() {
			@Override
			protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame msg) throws Exception {
				
				if (msg instanceof CloseWebSocketFrame) {
					log.info("客户端连接断开: {}", ctx.channel());
					return ;
				}
				
				if (msg instanceof TextWebSocketFrame) { // 文本消息处理
					
					String content = ((TextWebSocketFrame) msg).text();
					
					log.info("Recv: {}", content);
					
					ctx.channel().writeAndFlush(new TextWebSocketFrame(content));
					
					if (content.equalsIgnoreCase("bye")) {
						ctx.channel().writeAndFlush(WebSocketCloseStatus.NORMAL_CLOSURE).addListener(ChannelFutureListener.CLOSE);
					}
				} else {
					
					log.warn("不支持的消息类型: {}", msg.getClass().getName());
					
					ctx.channel().writeAndFlush(WebSocketCloseStatus.INVALID_MESSAGE_TYPE).addListener(ChannelFutureListener.CLOSE);
				}
			}
		});
		pipeline.addLast(new SimpleUserEventChannelHandler<HandshakeComplete>() {
			@Override
			protected void eventReceived(ChannelHandlerContext ctx, HandshakeComplete evt) throws Exception {
				String uri = evt.requestUri();
				HttpHeaders httpHeaders = evt.requestHeaders();
				String selectedSubprotocol = evt.selectedSubprotocol();
				log.debug("WS握手: uri={}, headers={}, selectedSubprotocol={}", uri,httpHeaders,selectedSubprotocol);
			}
		});
		
		pipeline.addLast(new ChannelInboundHandlerAdapter() {
			@Override
			public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
				log.error("异常: {}", cause.getMessage());
			}
		});
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
