------------------------
WebSocket-js			|
------------------------
	# 很显然这是JS的东西
	# 检测浏览器是否支持WebSocket
		function loadDemo() {  
			if (window.WebSocket) {  
				//支持  
			} else {  
				//不支持
			}  
		}  
	

------------------------
WebSocket-WebSocket		|
------------------------
	# 创建 WebSocket 对象
		var Socket = new WebSocket(url,[protocol]);
			* url:表示连接的地址,通常以协议:"ws://"或者"wss://"(安全的websocket)协议开头
			* protocol:是一个数组,可以接受一个或者多个子协议,该参数并不是必须的
	
	# 属性
		readyState	
			* 只读属性 readyState 表示连接状态，可以是以下值
			CONNECTING	:值为0,表示正在连接
			OPEN		:值为1,表示连接成功，可以通信了
			CLOSING		:值为2,表示连接正在关闭
			CLOSED		:值为3,表示连接已经关闭,或者打开连接失败
			
		bufferedAmount	
			* 只读属性 bufferedAmount 已被 send() 放入正在队列中等待传输,但是还没有发出的 UTF-8 文本字节数.
			* 可以判断消息是否发送完毕
				if (socket.bufferedAmount === 0) {
				  // 发送完毕
				} else {
				  // 发送还没结束
				}
		
		binaryType 
			* 指定二进制数据类型
			* 枚举值(字符串)
				binaryType = "blob";
				binaryType = "arraybuffer";
			
	# 事件
		* 事件处理函数,都会有一个 event 参数
			Socket.onopen	
				* 连接建立时触发
				*  event属性
					var protocol = event.target.protocol

			Socket.onmessage	
				* 客户端接收服务端数据时触发
				* event属性
					var data = event.data;

				* 判断消息类型
					if(typeof event.data === String) {
						console.log("Received data string");
					}
					if(event.data instanceof ArrayBuffer){
						console.log("Received arraybuffer");
					}

			Socket.onerror	
				* 通信发生错误时触发

			Socket.onclose	
				* 连接关闭时触发
				* event属性
					var code = event.code;
					var reason = event.reason;
					var wasClean = event.wasClean;

		* event通用属性
			
	
	# 方法
		Socket.send()	
			* 使用连接发送数据
			* 发送二进制数据
				var file = document.querySelector('input[type="file"]').files[0];
				ws.send(file);

		Socket.close()	
			* 关闭连接
			* 在窗口关闭之前,就关闭到socket连接,有可能会导致服务器端异常
				window.onbeforeunload = function () {  
					ws.close();  
				}  
		

------------------------
WebSocket-WebSocket		|
------------------------
状态码		名称					描述
0–999		-						保留段, 未使用。
1000		CLOSE_NORMAL			正常关闭; 无论为何目的而创建, 该链接都已成功完成任务。
1001		CLOSE_GOING_AWAY		终端离开, 可能因为服务端错误, 也可能因为浏览器正从打开连接的页面跳转离开。
1002		CLOSE_PROTOCOL_ERROR	由于协议错误而中断连接。
1003		CLOSE_UNSUPPORTED		由于接收到不允许的数据类型而断开连接 (如仅接收文本数据的终端接收到了二进制数据)。
1004		-						保留。 其意义可能会在未来定义。
1005		CLOSE_NO_STATUS			保留。 表示没有收到预期的状态码。
1006		CLOSE_ABNORMAL			保留。 用于期望收到状态码时连接非正常关闭 (也就是说, 没有发送关闭帧)。
1007		Unsupported Data		由于收到了格式不符的数据而断开连接 (如文本消息中包含了非 UTF-8 数据)。
1008		Policy Violation		由于收到不符合约定的数据而断开连接。 这是一个通用状态码, 用于不适合使用 1003 和 1009 状态码的场景。
1009		CLOSE_TOO_LARGE			由于收到过大的数据帧而断开连接。
1010		Missing Extension		客户端期望服务器商定一个或多个拓展, 但服务器没有处理, 因此客户端断开连接。
1011		Internal Error			客户端由于遇到没有预料的情况阻止其完成请求, 因此服务端断开连接。
1012		Service Restart			服务器由于重启而断开连接。 [Ref]
1013		Try Again Later			服务器由于临时原因断开连接, 如服务器过载因此断开一部分客户端连接。 [Ref]
1014		-	由 WebSocket		标准保留以便未来使用。
1015		TLS Handshake			保留。 表示连接由于无法完成 TLS 握手而关闭 (例如无法验证服务器证书)。
1016–1999	-						由 WebSocket 标准保留以便未来使用。
2000–2999	-						由 WebSocket 拓展保留使用。
3000–3999	-						可以由库或框架使用。 不应由应用使用。 可以在 IANA 注册, 先到先得。
4000–4999	-						可以由应用使用。