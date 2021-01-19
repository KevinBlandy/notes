-------------------------
client					 |
-------------------------
	# 类库
		WebSocketClientHandshakerFactory
		WebSocketClientHandshaker
	
------------------------------------
WebSocketClientHandshakerFactory	|
------------------------------------
	#  客户端握手处理器的工厂类
		WebSocketClientHandshaker newHandshaker(URI webSocketURL, WebSocketVersion version, String subprotocol,boolean allowExtensions, HttpHeaders customHeaders)
		WebSocketClientHandshaker newHandshaker(URI webSocketURL, WebSocketVersion version, String subprotocol,boolean allowExtensions, HttpHeaders customHeaders, int maxFramePayloadLength)
		WebSocketClientHandshaker newHandshaker(URI webSocketURL, WebSocketVersion version, String subprotocol,boolean allowExtensions, HttpHeaders customHeaders, int maxFramePayloadLength,boolean performMasking, boolean allowMaskMismatch)


-------------------------
客户端实现				 |
-------------------------

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelPromise;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketHandshakeException;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketClientCompressionHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

class WebSocketClientHandler extends SimpleChannelInboundHandler<Object> {

	private final WebSocketClientHandshaker handshaker;

	private ChannelPromise handshakeFuture;

	public WebSocketClientHandler(WebSocketClientHandshaker handshaker) {
		this.handshaker = handshaker;
	}

	public ChannelFuture handshakeFuture() {
		return handshakeFuture;
	}

	@Override
	public void handlerAdded(ChannelHandlerContext ctx) {
		handshakeFuture = ctx.newPromise();
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) {
		handshaker.handshake(ctx.channel());
	}

	@Override
	public void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
		/**
		 * 处理握手消息 
		 */
		if (!handshaker.isHandshakeComplete()) { // 握手消息
			try {
				handshaker.finishHandshake(ctx.channel(), (FullHttpResponse) msg); // 完成握手
				handshakeFuture.setSuccess();
			} catch (WebSocketHandshakeException e) {
				handshakeFuture.setFailure(e); // 握手异常
			}
			return;
		}

		/**
		 * 握手失败的响应的异常信息 
		 */
		if (msg instanceof FullHttpResponse) {
			FullHttpResponse response = (FullHttpResponse) msg;
			throw new IllegalStateException("Unexpected FullHttpResponse (getStatus=" + response.status() + ", content=" + response.content().toString(CharsetUtil.UTF_8) + ')');
		}

		/**
		 * WebSocket消息
		 */
		WebSocketFrame frame = (WebSocketFrame) msg;
		if (frame instanceof TextWebSocketFrame) { // 文本消息数据
			TextWebSocketFrame textFrame = (TextWebSocketFrame) frame;
			System.out.println("收到服务端数据:" + textFrame.text());
		} else {
			// 其他类型的数据
		}
	}
}

public class Client {
	public static void main(String[] args) throws URISyntaxException, InterruptedException, IOException {

		// 连接地址
		URI uri = new URI("ws://localhost:1024/websocket");
		
		EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
		
		try {
			
			// uri,版本,子协议,是否支持扩展,http头
			WebSocketClientHandler handler = new WebSocketClientHandler(WebSocketClientHandshakerFactory.newHandshaker(uri, WebSocketVersion.V13, null, true, new DefaultHttpHeaders()));

			Bootstrap bootstrap = new Bootstrap();
			bootstrap.group(eventLoopGroup);
			bootstrap.channel(NioSocketChannel.class);
			bootstrap.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel ch) {
					ChannelPipeline pipeline = ch.pipeline();
					pipeline.addLast(new HttpClientCodec());
					pipeline.addLast(new ChunkedWriteHandler());
					pipeline.addLast(new HttpObjectAggregator(8192));
					pipeline.addLast(WebSocketClientCompressionHandler.INSTANCE);
					pipeline.addLast(handler);
				}
			});

			Channel channel = bootstrap.connect(uri.getHost(), uri.getPort()).sync().channel();
			
			// 等待握手完成
			handler.handshakeFuture().sync().addListener(new GenericFutureListener<Future<? super Void>>() {
				@Override
				public void operationComplete(Future<? super Void> future) throws Exception {
					if (!future.isSuccess()) { // 握手失败
						future.cause().printStackTrace();
					} 
				}
			});
			
			channel.closeFuture().sync();
		} finally {
			eventLoopGroup.shutdownGracefully();
		}
	}
}
