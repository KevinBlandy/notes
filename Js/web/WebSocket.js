----------------------------------------------
WebSocket extends EventTarget
----------------------------------------------
	# 用于创建和管理 WebSocket 连接，以及可以通过该连接发送和接收数据的 API。

	# 构造函数
		new WebSocket(url [, protocols]);

			url
				* 要连接的 URL，这应该是 WebSocket 服务器将响应的 URL。
			
			protocols
				* 可选，一个协议字符串或者一个包含协议字符串的数组。
				* 这些字符串用于指定子协议，这样单个服务器可以实现多个 WebSocket 子协议（例如，你可能希望一台服务器能够根据指定的协议（protocol）处理不同类型的交互）。如果不指定协议字符串，则假定为空字符串。


----------------------------------------------
this
----------------------------------------------
	binaryType
		* 返回 websocket 连接所传输二进制数据的类型。
			"blob"
				* 传输的是 Blob 类型的数据。

			"arraybuffer"
				* 传输的是 ArrayBuffer 类型的数据。

	bufferedAmount
		* 只读属性，用于返回已经被 send() 方法放入队列中但还没有被发送到网络中的数据的字节数。
		* 一旦队列中的所有数据被发送至网络，则该属性值将被重置为 0。
		* 若在发送过程中连接被关闭，则属性值不会重置为 0。
		* 如果不断地调用send()，则该属性值会持续增长

	extensions
		* 只读属性，返回服务器已选择的扩展值。

	protocol
		* 只读属性，用于返回服务器端选中的子协议的名字

	readyState
		* WebSocket 的链接状态，以下其中之一，只读。

		0 (WebSocket.CONNECTING)
			* 正在链接中

		1 (WebSocket.OPEN)
			* 已经链接并且可以通讯

		2 (WebSocket.CLOSING)
			* 连接正在关闭

		3 (WebSocket.CLOSED)
			* 连接已关闭或者没有链接成功

	url
		* 只读属性，返回值为当构造函数创建WebSocket实例对象时 URL 的绝对路径。

	close(code, reason)
		* 关闭 WebSocket 连接或连接尝试（如果有的话）。如果连接已经关闭，则此方法不执行任何操作。
		code
			* 可选，一个数字状态码，它解释了连接关闭的原因。如果没有传这个参数，默认使用 1005。C
			* CloseEvent 可以访问到状态码。
				https://developer.mozilla.org/zh-CN/docs/Web/API/CloseEvent

		reason
			* 可选，一个人类可读的字符串，它解释了连接关闭的原因。
			* 这个 UTF-8 编码的字符串不能超过 123 个字节

	send(data)
		* 传输至服务器的数据。它必须是以下类型之一：

		USVString
			* 文本字符串。字符串将以 UTF-8 格式添加到缓冲区，并且 bufferedAmount 将加上该字符串以 UTF-8 格式编码时的字节数的值。
		
		ArrayBuffer
			* 使用类型化数组对象发送底层二进制数据；其二进制数据内存将被缓存于缓冲区，bufferedAmount 将加上所需字节数的值。
		
		Blob
			* Blob 类型将队列 blob 中的原始数据以二进制中传输。 bufferedAmount 将加上原始数据的字节数的值。
		
		ArrayBufferView
			* 以二进制帧的形式发送任何 JavaScript 类数组对象 ；其二进制数据内容将被队列于缓冲区中。值 bufferedAmount 将加上必要字节数的值。

		
----------------------------------------------
event
----------------------------------------------
	close
		* 连接关闭，事件对象的属性
			code
			reason
			wasClean
				* WebSocket 连接是否以干净（正常）的方式关闭。
				* 如果 wasClean 为 false，则表示连接是非正常关闭的，可能是由于网络问题、服务器崩溃或其他异常情况导致的。

	error
	message
		* 消息事件，事件对象 MessageEvent 
			
			data
				* 消息的数据

			lastEventId
			origin
			ports
			source
	open

----------------------------------------------
static
----------------------------------------------
