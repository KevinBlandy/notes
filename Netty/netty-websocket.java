---------------------------------
websocket						 |
---------------------------------
	# ���
		WebSocketFrame
			|-BinaryWebSocketFrame			
			|-CloseWebSocketFrame
			|-ContinuationWebSocketFrame
			|-PingWebSocketFrame
			|-PongWebSocketFrame
			|-TextWebSocketFrame
		WebSocketProtocolHandler(�Զ�����webSocket��ping pong����Ϣ)
			|-WebSocketServerProtocolHandler(�򻯿����ṩ��handler)
		WebSocketServerCompressionHandler(WebSocket��Ϣѹ��)
		WebSocketServerProtocolHandshakeHandler(WebSocketЭ�������ʵ��)
		WebSocketServerHandshaker(���ִ�����)
		WebSocketServerHandshakerFactory(�����������Ĺ�����)
		WebSocketDecoderConfig(������ص�����)
		WebSocketCloseStatus(WebSocket��״̬���װ)
		
			
	# WebSocketFrame
		* ws��Ϣ���͵ĳ�����,�ṩ��N��ʵ��,��ʾ��ͬ����Ϣ����
		* ������,������֪����ɶ��Ϣ��ʵ����
	
	# WebSocketServerCompressionHandler
		* �ṩ�˶�websocket��Ϣ��ѹ��
	
	# WebSocketDecoderConfig
		* websocket������ص�����
		* ʵ��Builderģʽ����
			private final int maxFramePayloadLength;		// ������Ϣ�����
			private final boolean expectMaskedFrames;		// Web�׽��ַ��������뽫������Ϊtrue���������������Чpaload���ͻ��˱���Ϊfalse
			private final boolean allowMaskMismatch;		// ����ſ�Խ���֡������Ҫ�󡣵���������Ϊfalseʱ��Ҳû�а��ձ�׼��ȷ���ε�֡�Խ������ܡ�
			private final boolean allowExtensions;			// �Ƿ�����ʹ�ñ�������չλ�ı�־��
			private final boolean closeOnProtocolViolation;	// ���κ�Υ��Э���������������͹ر�֡��
			private final boolean withUTF8Validator;		// �Ƿ���������⽫Utf8FrameValidator��ӵ��ܵ��С�
				* WebSocketServerProtocolHandler�Ĵ�����������ã����ٿ�������
				* ���������Web�׽���������ֻʹ�ö�����WebSocketFrameʱ�����Կ�������Ϊfalse

		* Ĭ��ʵ��
			    static final WebSocketDecoderConfig DEFAULT =
							new WebSocketDecoderConfig(65536, true, false, false, true, true);
		
			
	# WebSocketCloseStatus
		* websocket״̬��ķ�װ
			private final int statusCode;			// ״̬��
			private final String reasonText;		// ԭ��
		    private String text;					// this.text = text = code() + " " + reasonText();

		* ���캯��
			public WebSocketCloseStatus(int statusCode, String reasonText)
		
		* ��̬����
			public static WebSocketCloseStatus valueOf(int code)
				* ����code��ȡ���еľ�̬ʵ��
				* ���������, �򴴽�:
					return new WebSocketCloseStatus(code, "Close status #" + code);
				
			public static boolean isValidStatusCode(int code)
				* �ж�ָ����״̬���Ƿ���һ�����Ϸ���״̬��


		* �ṩ��N��ĵľ�̬ʵ��
			public static final WebSocketCloseStatus NORMAL_CLOSURE = new WebSocketCloseStatus(1000, "Bye");
			...
		

-------------------------------------
WebSocketServerProtocolHandler		 |
-------------------------------------
	# ���ص�һ����װ,�����N��Ĺ���
	# ������:����,����֡(ping/pong/close),�ı���Ϣ,��������Ϣ....
	# ���캯��(�汾����, ���캯�����β�Ҳ����ȷ��, �����β��б��ʼ����ȷ)
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
			* �ṩ�����uri

		subprotocols
			* ֧�ֵ���Э���б�

		allowExtensions
			* �Ƿ�������չ

		maxFrameSize
			* ��Ϣ֡����ֽ���

		allowMaskMismatch
			* �Ƿ�Ҫ����δʵ�ֱ�׼�淶������֡

		checkStartsWith
			* ���Ϊtrue, ��ʹ��startWith ��� uri, ����ʹ��quals
			* ���Ϊfalse, �����Ҫ��uri��websocketһ��һ��
			
		dropPongFrames
			* ����pong��Ϣ
		
		handshakeTimeoutMillis
			* websocket���ֳ�ʱ,
			* Ĭ��: private static final long DEFAULT_HANDSHAKE_TIMEOUT_MS = 10000L;
		
		decoderConfig
			* WebSocketDecoderConfig ��
			* WebSocket������ص�����
	
	# �����websocket�����ݱ���Ϊ WebSocketFrame ����,������һ��Handlerȥ����
	
	# WebSocketServerProtocolConfig
		* �����࣬����ͨ���������������WebSocketServerProtocolHandler������

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
�����							 |
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
		// �Զ���http handler�����д���Ƿ�����
		pipeline.addLast(new ChannelInboundHandlerAdapter() {
			@Override
			public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
				
				if(!(msg instanceof FullHttpRequest)) {
					// �Ƿ���http����ֱ�ӹر�����
					ctx.channel().close();
					return ;
				}
				
				FullHttpRequest fullHttpRequest = (FullHttpRequest) msg;
				
				// ����˵�
				String uri = fullHttpRequest.uri();
				if (!uri.equalsIgnoreCase(PATH)) {
					ctx.channel().writeAndFlush(new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.NOT_FOUND))
						.addListener(ChannelFutureListener.CLOSE);
					return ;
				}
				
				// ���󷽷�
				if (fullHttpRequest.method() != HttpMethod.GET) {
					ctx.channel().writeAndFlush(new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.METHOD_NOT_ALLOWED))
						.addListener(ChannelFutureListener.CLOSE);
					return ;
				}
				
				// TODO �Ƿ��ǺϷ���WS��������
				
				ctx.pipeline().remove(this); // ��֤��ɺ󣬴�pipeline�Ƴ��Լ����ܹؼ�

				
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
						.expectMaskedFrames(true)		// ����������Ϊ true
						.allowMaskMismatch(false)
						.allowExtensions(true)				// ����ѹ��������뿪��Э����չ
						.closeOnProtocolViolation(true)	// �յ����Ϸ����壬ֱ�ӶϿ�����
						.withUTF8Validator(true)	// �ı���Ϣ�����UTF8У����
						.build())
				.build()));
		pipeline.addLast(new SimpleChannelInboundHandler<WebSocketFrame>() {
			@Override
			protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame msg) throws Exception {
				
				if (msg instanceof CloseWebSocketFrame) {
					log.info("�ͻ������ӶϿ�: {}", ctx.channel());
					return ;
				}
				
				if (msg instanceof TextWebSocketFrame) { // �ı���Ϣ����
					
					String content = ((TextWebSocketFrame) msg).text();
					
					log.info("Recv: {}", content);
					
					ctx.channel().writeAndFlush(new TextWebSocketFrame(content));
					
					if (content.equalsIgnoreCase("bye")) {
						ctx.channel().writeAndFlush(WebSocketCloseStatus.NORMAL_CLOSURE).addListener(ChannelFutureListener.CLOSE);
					}
				} else {
					
					log.warn("��֧�ֵ���Ϣ����: {}", msg.getClass().getName());
					
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
				log.debug("WS����: uri={}, headers={}, selectedSubprotocol={}", uri,httpHeaders,selectedSubprotocol);
			}
		});
		
		pipeline.addLast(new ChannelInboundHandlerAdapter() {
			@Override
			public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
				log.error("�쳣: {}", cause.getMessage());
			}
		});
	}
}

	
---------------------------------
������ǰ����uri�����ȵ�			 |
---------------------------------
	# ˼·
		* �� HttpObjectAggregator ֮�����һ��Handler,���ڻ�ȡ��������http������Ϣ:FullHttpRequest
		* ����ͨ��FullHttpRequest��ȡ��uri,header,method,query param����Ϣ
		* �Լ����Ծ�������Ӧ�쳣��Ϣ,����ִ����һ��handler,Ҳ����ws����

	# ����
		pipeline.addLast(new HttpObjectAggregator(65536));
		pipeline.addLast(new ChannelInboundHandlerAdapter() {
			@Override
			public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
				if(msg instanceof FullHttpRequest) {
					FullHttpRequest fullHttpRequest = (FullHttpRequest) msg;
					
					// ��ȡ�������uri,�������������·�����������
					String uri = fullHttpRequest.uri();

					fullHttpRequest.setUri(ENDPOINT); // һ��Ҫ����uri��websocket�Ķ˵�ƥ��
				}
				super.channelRead(ctx, msg);
			}
		});
		pipeline.addLast(new WebSocketServerCompressionHandler());
		pipeline.addLast(new WebSocketServerProtocolHandler(ENDPOINT, null, true));	// uri,��Э��,�Ƿ�֧����չ
