---------------------
ServerHttpResponse
---------------------
	# HTTP响应接口
		public interface ServerHttpResponse extends ReactiveHttpOutputMessage 

---------------------
this
---------------------

	HttpHeaders getHeaders();
		* 获取响应的Header，这个Header可以直接修改
	
	DataBufferFactory bufferFactory();
		* 获取DataBufferFactory实例，用于包装或者生成数据缓冲区DataBuffer实例(创建响应体)

	void beforeCommit(Supplier<? extends Mono<Void>> action);
		* 注册一个动作，在HttpOutputMessage提交(响应)之前此动作会进行回调

	boolean isCommitted();
		* 判断HttpOutputMessage是否已经提交(响应)

	Mono<Void> writeWith(Publisher<? extends DataBuffer> body);
		* 写入消息体到HTTP协议层

	Mono<Void> writeAndFlushWith(Publisher<? extends Publisher<? extends DataBuffer>> body);
		* 写入消息体到HTTP协议层并且刷新缓冲区

	Mono<Void> setComplete();
		* 指明消息处理已经结束，一般在消息处理结束自动调用此方法，多次调用不会产生副作用

	boolean setStatusCode(@Nullable HttpStatus status);
		* 设置响应状态码

	HttpStatus getStatusCode();
		*  获取响应状态码

	default boolean setRawStatusCode(@Nullable Integer value)
	default Integer getRawStatusCode()
		* 获取原始的HTTP状态码，int类型

	MultiValueMap<String, ResponseCookie> getCookies()
		* 获取响应Cookie，封装为MultiValueMap实例，可以修改

	void addCookie(ResponseCookie cookie);
		* 添加响应Cookie
