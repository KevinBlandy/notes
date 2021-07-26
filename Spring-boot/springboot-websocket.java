----------------------------
Spring-boot websocket		|
----------------------------
	# ����WebSocket���Զ����ð�
		org.springframework.boot.autoconfigure.websocket
	
	# �����war��ʽ���е�websocket,��ô endpoint ��Ӧ�ý�����������,������ ServerEndpointExporter

----------------------------
Spring-boot websocket��1	|
----------------------------
	# ��������
		<dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-websocket</artifactId>
        </dependency>
	
	# ע��Bean
		* ServerEndpointExporter
		@Configuration  
		public class WebSocketConfiguration {  
			@Bean  
			public ServerEndpointExporter serverEndpointExporter (){  
				return new ServerEndpointExporter();  
			}  
		}  
	
	# ʹ��ע�⿪��WebSocket�˵�
		 
		@Component 
		@ServerEndpoint("/websocket")		//������б�߿�ͷ,��Ȼ��������
		public WebSocketEndpoint{
			@OnMessage
			@OnOpen
			@OnClose
			@OnError
		}

	# ע��
		* @OnError Ҫ��Ӳ���:Throwable ,��Ȼ�����쳣
	
	# Demo
		import java.io.IOException;
		import java.util.concurrent.ConcurrentHashMap;
		import java.util.concurrent.ConcurrentMap;

		import javax.websocket.CloseReason;
		import javax.websocket.CloseReason.CloseCode;
		import javax.websocket.CloseReason.CloseCodes;
		import javax.websocket.EndpointConfig;
		import javax.websocket.OnClose;
		import javax.websocket.OnError;
		import javax.websocket.OnMessage;
		import javax.websocket.OnOpen;
		import javax.websocket.Session;
		import javax.websocket.server.PathParam;
		import javax.websocket.server.ServerEndpoint;

		import org.slf4j.Logger;
		import org.slf4j.LoggerFactory;

		/**
		 * 
		 * 
		 * @author KevinBlandy
		 *
		 */
		@ServerEndpoint(value = "/channel/test")
		public class TestEndpoint { 

			private static final Logger LOGGER = LoggerFactory.getLogger(TestEndpoint.class);
			
			// �ظ���¼�� CloseReason
			public static final CloseReason REPEAT_CONNECTION = new CloseReason(new CloseCode() {
				@Override
				public int getCode() {
					return 4000;  // 4000�C4999 ��������ʹ��
				}
			}, "�ظ���¼");
			
			public static final ConcurrentMap<Long, Session> SESSIONS = new ConcurrentHashMap<>(); 

			private Session session;
			
			private Long userId;

			@OnMessage(maxMessageSize = -1)
			public void onMessage(String message, boolean isFinal){
				
				LOGGER.info("�յ��ͻ�����Ϣ:{}", message);
				
				this.session.getAsyncRemote().sendText("���յ���:" + message);
				
			}

			@OnOpen
			public void onOpen(Session session, @PathParam("path") String path, EndpointConfig endpointConfig){
				
				// TODO ��Session�������û�ID
				Long userId = 1L;
				
				Session finalSession = SESSIONS.compute(userId, (key, existsChannel) -> {
					if (existsChannel != null) {
						// �Ѿ����ڣ����滻Ϊ���µģ����ҹر�ԭChannel
						try {
							// close �ᴥ�� onClose �¼������CloseReason����Ҫ
							existsChannel.close(REPEAT_CONNECTION);
						} catch (IOException e) {
						}
					}
					return session;
				});
				
				
				this.session = finalSession;
				this.userId = userId;
				
				// ����ʱ
				this.session.setMaxIdleTimeout(0);
			}

			@OnClose
			public void onClose(CloseReason closeReason){
				
				LOGGER.info("���ӶϿ�,id={} reason={}",this.session.getId(),closeReason);
				
				LOGGER.info("hash: {} = {}", closeReason.hashCode(), REPEAT_CONNECTION.hashCode());
				
				if (REPEAT_CONNECTION.getCloseCode().getCode() == closeReason.getCloseCode().getCode()) {
					/**
					 * ������ظ���¼������£���Ҫִ��remove����Ϊ�� @OnOpen �Ѿ�ִ����Remove
					 * �����ظ�ִ�У����ܻ������ӵĺϷ����Ӹ�Remove��
					 */
					return;
				}
				
				Session session = SESSIONS.remove(this.userId);
				
				LOGGER.info("�Ƴ��Ự:{}", session.getId());
			}

			@OnError
			public void onError(Throwable throwable) throws IOException {
				LOGGER.info("�����쳣,id={},throwable={}",this.session.getId(),throwable);
				this.session.close(new CloseReason(CloseCodes.UNEXPECTED_CONDITION, throwable.getMessage()));
			}
		}
	
	# �����ֶ���ע��˵�, ��ʹ�� Spring ɨ��
		@Configuration
		public class WebSocketConfiguration {
			
			@Bean  
			public ServerEndpointExporter serverEndpointExporter (){  
				ServerEndpointExporter serverEndpointExporter =  new ServerEndpointExporter();
				// ��Ӷ����ʶ�� @ServerEndpoint ע��Ķ˵���
				serverEndpointExporter.setAnnotatedEndpointClasses(TestChannel.class);
				return serverEndpointExporter;
			}  
		}

		* ����Ҫ�� TestChannel �������� @Component