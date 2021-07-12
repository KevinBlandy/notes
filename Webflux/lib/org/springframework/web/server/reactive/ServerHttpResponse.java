---------------------
ServerHttpResponse
---------------------
	# HTTPÏìÓ¦½Ó¿Ú
		public interface ServerHttpResponse extends ReactiveHttpOutputMessage 

---------------------
this
---------------------
	
	DataBufferFactory bufferFactory();
	void beforeCommit(Supplier<? extends Mono<Void>> action);
	boolean isCommitted();
	Mono<Void> writeWith(Publisher<? extends DataBuffer> body);
	Mono<Void> writeAndFlushWith(Publisher<? extends Publisher<? extends DataBuffer>> body);
	Mono<Void> setComplete();


	boolean setStatusCode(@Nullable HttpStatus status);
	HttpStatus getStatusCode();
	default boolean setRawStatusCode(@Nullable Integer value)
	default Integer getRawStatusCode()
	MultiValueMap<String, ResponseCookie> getCookies()
	void addCookie(ResponseCookie cookie);
