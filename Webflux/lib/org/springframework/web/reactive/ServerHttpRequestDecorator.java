------------------------------------
ServerHttpRequestDecorator
------------------------------------
	# Request的包装类
		public class ServerHttpRequestDecorator implements ServerHttpRequest
	
------------------------------------
static
------------------------------------

------------------------------------
this
------------------------------------
	public ServerHttpRequestDecorator(ServerHttpRequest delegate)
		* 包装Request

	public ServerHttpRequest getDelegate()
		* 获取包装的Request
	
	public static <T> T getNativeRequest(ServerHttpRequest request)
		* 返回底层服务器API的本机请求，如果可能的话，也要解包装
		* 类型只能是如下，如果是其他的类型则异常
			ServerHttpRequestDecorator
			AbstractServerHttpRequest