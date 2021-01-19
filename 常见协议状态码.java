----------------------------
http
----------------------------

	成功响应
		200  OK(成功)
		201  Created(已创建)
		202  Accepted(已创建)
		203  Non-Authoritative Information(未授权信息)
		204  No Content(无内容)
		205  Reset Content(重置内容)
		206  Partial Content(部分内容)
	重定向
		300  Multiple Choice(多种选择)
		301  Moved Permanently(永久移动)
		302  Found(临时移动)
		303  See Other(查看其他位置)
		304  Not Modified(未修改)
		305  Use Proxy(使用代理)
		306  unused (未使用)
		307  Temporary Redirect(临时重定向)
		308  Permanent Redirect(永久重定向)
	客户端错误
		400  Bad Request(错误请求)
		401  Unauthorized(未授权)
		402  Payment Required(需要付款)
		403  Forbidden(禁止访问)
		404  Not Found(未找到)
		405  Method Not Allowed(不允许使用该方法)
		406  Not Acceptable(无法接受)
		407  Proxy Authentication Required(要求代理身份验证)
		408  Request Timeout(请求超时)
		409  Conflict(冲突)
		410  Gone(已失效)
		411  Length Required(需要内容长度头)
		412  Precondition Failed(预处理失败)
		413  Request Entity Too Large(请求实体过长)
		414  Request-URI Too Long(请求网址过长)
		415  Unsupported Media Type(媒体类型不支持)
		416  Requested Range Not Satisfiable(请求范围不合要求)
		417  Expectation Failed(预期结果失败)
	服务器端错误
		500  Internal Server Error(内部服务器错误)
		501  Implemented(未实现)
		502  Bad Gateway(网关错误)
		503  Service Unavailable(服务不可用)
		504  Gateway Timeout (网关超时)
		505  HTTP Version Not Supported(HTTP 版本不受支持)

----------------------------
websocket
----------------------------
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
	1014		-						由 WebSocket 标准保留以便未来使用。
	1015		TLS Handshake			保留。 表示连接由于无法完成 TLS 握手而关闭 (例如无法验证服务器证书)。
	1016–1999	-						由 WebSocket标准保留以便未来使用。
	2000–2999	-						由 WebSocket拓展保留使用。
	3000–3999	-						可以由库或框架使用。不应由应用使用。 可以在 IANA 注册, 先到先得。
	4000–4999	-						可以由应用使用。
