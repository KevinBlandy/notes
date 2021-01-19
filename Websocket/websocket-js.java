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
		Socket.readyState	
			* 只读属性 readyState 表示连接状态，可以是以下值
			0 - 表示连接尚未建立。
			1 - 表示连接已建立，可以进行通信。
			2 - 表示连接正在进行关闭。
			3 - 表示连接已经关闭或者连接不能打开。
	
		Socket.bufferedAmount	
			* 只读属性 bufferedAmount 已被 send() 放入正在队列中等待传输，但是还没有发出的 UTF-8 文本字节数。

			
	# 事件
		* 事件处理函数,都会有一个 event 参数
		Socket.onopen	
			* 连接建立时触发
		Socket.onmessage	
			* 客户端接收服务端数据时触发
		Socket.onerror	
			* 通信发生错误时触发
		Socket.onclose	
			* 连接关闭时触发
	
	# 方法
		Socket.send()	
			* 使用连接发送数据
		Socket.close()	
			* 关闭连接
	


