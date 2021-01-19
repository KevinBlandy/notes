-------------------------
WebSocket
-------------------------
	# websocket 客户端的接口
	
	# 静态属性
		int NORMAL_CLOSURE = 1000;
			* 正常关闭的消息帧的状态码

	# 静态方法
		

	# 抽象方法
		CompletableFuture<WebSocket> sendText(CharSequence data, boolean last);
		CompletableFuture<WebSocket> sendBinary(ByteBuffer data, boolean last);
			* 发送消息, last表示是否为最后一帧

		CompletableFuture<WebSocket> sendPing(ByteBuffer message);
		CompletableFuture<WebSocket> sendPong(ByteBuffer message);
			* 发送心跳

		CompletableFuture<WebSocket> sendClose(int statusCode, String reason);	
			* 关闭

		void request(long n);
			* 用于统计接收消息数量的方法

		String getSubprotocol();
		boolean isOutputClosed();
		boolean isInputClosed();
		void abort();

-------------------------
Builder
-------------------------
	# websocket 的builer接口
		Builder header(String name, String value);
			* 添加请求头

		Builder connectTimeout(Duration timeout);
			* 设置超时时间

		Builder subprotocols(String mostPreferred, String... lesserPreferred);
			* 设置子协议

		CompletableFuture<WebSocket> buildAsync(URI uri, Listener listener);
			* 异步创建链接

-------------------------
Listener
-------------------------
	# 消息监听接口
		default void onOpen(WebSocket webSocket) { 
		default CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) 
		default CompletionStage<?> onBinary(WebSocket webSocket, ByteBuffer data, boolean last) 
		default CompletionStage<?> onPing(WebSocket webSocket, ByteBuffer message) 

		default CompletionStage<?> onPong(WebSocket webSocket, ByteBuffer message) 
		default CompletionStage<?> onClose(WebSocket webSocket, int statusCode, String reason) 
		default void onError(WebSocket webSocket, Throwable error) 

		
		* 这些接口方法全是默认实现的
		* 除了 onClose 和 onError 方法以外, 其他方法都会先执行: webSocket.request(1);




-------------------------
完整的客户端
-------------------------
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.net.http.WebSocket.Listener;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * 
 * @author KevinBlandy
 *
 */
public class Client implements Test{
	
	public static void main(String[] args) throws Exception {
		
		// 执行线程池
		ExecutorService executor = Executors.newSingleThreadExecutor();
		
		// 创建监听器
		WebSocket.Listener listener = new Listener() {
			@Override
			public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
				String message = data.toString();
				System.out.println("收到服务器消息:" + message + "，是否是最后一帧:" + last);
				if (last) {
					// 完整的一条消息，才纳入消息数量统计
					webSocket.request(1);
				}
				if (message.equalsIgnoreCase("bye")) {
					// 收到服务端的 "byte", 客户端主动断开链接
					webSocket.sendClose(WebSocket.NORMAL_CLOSURE, "byte").whenComplete((websocket, throable) -> {
						// 资源释放
						executor.shutdown();
						System.out.println("已经关闭和服务端的channel");
					});
				}
	            return null;
			}
		};
		
		// 创建websocket的builder
		HttpClient.newBuilder()
			.executor(executor)
			.build()
			.newWebSocketBuilder()
			.buildAsync(new URI("ws://localhost:1024/channel"), listener).whenComplete((webSocket, throable) -> {
				
				while (true) {
					webSocket.sendText("Hello： " + System.currentTimeMillis(), true);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
		
	}
	
	public static void json(Object object) {
		System.out.println(JSON.toJSONString(object, SerializerFeature.PrettyFormat));
	}
}
