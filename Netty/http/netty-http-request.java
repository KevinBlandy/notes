----------------------
Request
----------------------
	# interface FullHttpRequest extends HttpRequest, FullHttpMessage
		* 该接口封装了完整的请求信息
		* 默认的实现: DefaultFullHttpRequest
	
	# 抽线方法
		HttpMethod method();
		HttpRequest setMethod(HttpMethod method);
			* 请求方法

		String uri();
		HttpRequest setUri(String uri);
			* 请求的URI

		HttpRequest setProtocolVersion(HttpVersion version);
		HttpVersion protocolVersion();
			* 协议版本

		HttpHeaders headers();
			* 请求头
		
		HttpHeaders trailingHeaders();

		DecoderResult decoderResult();
		void setDecoderResult(DecoderResult result);
			* 编码结果

		ByteBuf content()
			* 获取到请求体

----------------------
Request - 请求参数
----------------------

----------------------
Request - 文件上传
----------------------