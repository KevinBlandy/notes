--------------------
跨域
--------------------
	Access-Control-Allow-Origin
		* 该字段是必须的。它的值要么是请求时Origin字段的值，要么是一个:*，表示接受任意域名的请求。
		* 如果客户端允许携带凭证，那么它的值不能是*，必须是明确的origin

	Access-Control-Allow-Credentials
		* 该字段可选。它的值是一个布尔值，表示是否允许发送Cookie。
		* 默认情况下，Cookie不包括在CORS请求之中。设为true，即表示服务器明确许可，Cookie可以包含在请求中，一起发给服务器。
		* 这个值也只能设为true，如果服务器不要浏览器发送Cookie，删除该字段即可。

	Access-Control-Allow-Methods
		* 该字段必需，它的值是逗号分隔的一个字符串，表明服务器支持的所有跨域请求的方法。
		* 注意，返回的是所有支持的方法，而不单是浏览器请求的那个方法。这是为了避免多次"预检"请求。

	Access-Control-Expose-Headers
		* CORS请求时，XMLHttpRequest对象的getResponseHeader()方法只能拿到6个基本字段：
			Cache-Control, Content-Language, Content-Type, Expires, Last-Modified, Pragma
		
		* 如果想拿到其他字段，就必须在Access-Control-Expose-Headers里面指定。上

	Access-Control-Allow-Headers
		* 如果浏览器请求包括 Access-Control-Request-Headers 字段，则Access-Control-Allow-Headers字段是必需的。
		* 它也是一个逗号分隔的字符串，表明服务器支持的所有头信息字段，不限于浏览器在"预检"中请求的字段。
		* 如果客户端需要携带凭证，那么这个Header不允许设置为: *

	Access-Control-Max-Age
		* 该字段可选，用来指定本次预检请求的有效期，单位为秒。

