----------------------
XMLHttpRequest
----------------------
	# extends
		XMLHttpRequestEventTarget
		EventTarget

	# （XHR）对象用于与服务器交互。
	# 构造函数
		new XMLHttpRequest()

----------------------
this
----------------------

	channel
	mozAnon
	mozBackgroundRequest
	mozSystem
	readyState
		* 当前所处的状态。
			0	XMLHttpRequest.UNSENT					代理被创建，但尚未调用 open() 方法。
			1	XMLHttpRequest.OPENED					open() 方法已经被调用。
			2	XMLHttpRequest.HEADERS_RECEIVED			send() 方法已经被调用，并且 Header 和状态已经可获得。
			3	XMLHttpRequest.LOADING					下载中；responseText 属性已经包含部分数据。
			4	XMLHttpRequest.DONE						下载操作已完成，这意味着数据传输已经彻底完成或失败。

	response
		* 返回响应的正文。返回的类型为 ArrayBuffer、Blob、Document、JavaScript Object 或 String 中的一个。
		* 这取决于请求的 responseType 属性。

	responseText
		* 从服务器端返回的纯文本。

	responseType
		* 枚举字符串值，用于指定响应中包含的数据类型。
			""
				* 空的 responseType 字符串与默认类型 "text" 相同。

			"arraybuffer"
				* response 是一个包含二进制数据的 JavaScript ArrayBuffer。

			"blob"
				* response 是一个包含二进制数据的 Blob 对象。

			"document"
				* response 是一个 HTML Document 或 XML XMLDocument，根据接收到的数据的 MIME 类型而定。请参阅 XMLHttpRequest 中的 HTML，了解有关使用 XHR 获取 HTML 内容的更多信息。

			"json"
				* response 是通过将接收到的数据内容解析为 JSON 而创建的 JavaScript 对象。

			"text"
				* response 是 DOMString 对象中的文本。

	responseURL
	responseXML
	status
		* 返回响应状态码，数值类型

	statusText
		* 响应状态码的文本信息

	timeout
		* 超时毫秒数，默认值为 0，意味着没有超时。
		* 当超时发生， timeout 事件将会被触发。

	upload
		* 返回一个 XMLHttpRequestUpload 对象，用来表示上传的进度。
		* 可以被绑定在 upload 对象上的事件监听器如下

			onloadstart	获取开始
			onprogress	数据传输进行中
			onabort		获取操作终止
			onerror		获取失败
			onload		获取成功
			ontimeout	获取操作在用户规定的时间内未完成
			onloadend	获取完成（不论成功与否）

	withCredentials
		* 布尔值，它指示了是否该使用类似 cookie、Authorization Header 或者 TLS 客户端证书等凭据进行跨站点访问控制（Access-Control）请求
		* 设置 withCredentials 对同源请求是无效的。

	abort()
		* 终止该请求。当一个请求被终止，它的 readyState 将被置为 XMLHttpRequest.UNSENT（0），并且请求的 status 置为 0。

	getAllResponseHeaders()
		* 返回所有的响应头，以 CRLF 分割的字符串，或者 null 如果没有收到任何响应。

	getResponseHeader(name)
		* 获取指定的 Header，不存在返回 null

	open(method, url, async, user, password);
		* 初始化一个请求。
			method
				* 要使用的 HTTP 方法，比如 GET、POST、PUT、DELETE、等。

			url
				* 一个 DOMString 表示要向其发送请求的 URL。

			async
				* 可选，一布尔参数，表示是否异步执行操作，默认为 true。
				* 如果值为 false，send() 方法直到收到答复前不会返回。
				* 如果 true，已完成事务的通知可供事件监听器使用。如果 multipart 属性为 true 则这个必须为 true，否则将引发异常。

			user/password
				* 可选，用于认证

	overrideMimeType(mimeType)
		* 指定一个 MIME 类型用于替代服务器响应的类型，使服务端响应信息中传输的数据按照该指定 MIME 类型处理。
		* 此方法必须在 send 方法之前调用方为有效。
		* 如果服务器没有指定类型，那么 XMLHttpRequest 将会默认为 "text/xml".

	send(body)
		* 发送 HTTP 请求。如果是异步请求（默认为异步请求），则此方法会在请求发送后立即返回；如果是同步请求，则此方法直到响应到达后才会返回。
			body 可选
				* 可选的请求体，在 XHR 请求中要发送的数据体。可以是：

					Document
						* 它在发送之前被序列化。
					Blob, BufferSource, FormData, URLSearchParams, USVString
				
				* 如果 body 没有指定值，则默认值为 null。

	setAttributionReporting()
		* 实验性

	setRequestHeader(header, value);
		* 设置 HTTP 请求头的方法。
		* 此方法必须在 open() 方法和 send() 之间调用。
		* 如果多次对同一个请求头赋值，只会生成一个合并了多个值的请求头。

----------------------
event
----------------------
	abort
	error
	load
	loadend
	loadstart
	progress
		* 在请求接收到数据的时候被周期性触发。
		* 事件对象：ProgressEvent
			lengthComputable
				* 进度是否可以被测量（Content-Length 属性）。
			loaded
				* 底层流程已经执行的工作总量。
			total
				* 底层流程的工作总量。
				* 不包括 Header 和其他开销，仅是 Body

	readystatechange
		* 只要 readyState 属性发生变化，就会执行该回调
		* 当请求被 abort() 方法取消时，其对应的 readystatechange 事件不会被触发。

	timeout


----------------------
static
----------------------
