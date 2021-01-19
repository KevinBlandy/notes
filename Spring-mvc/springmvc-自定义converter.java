
1,实现抽象类:AbstractHttpMessageConverter<T> 
2,在构造函数中自定义application消息类型,指定该消息转换器用于转换什么格式的消息
	super(new MediaType("application/json"))
	* 可以直接使用:super(MediaType.ALL); 所有消息类型由该解析器解析

3,抽象方法详解
	boolean supports(Class<?> clazz) 
		* 该解析器是否支持解析该数据

	Object readInternal(Class<?> clazz, HttpInputMessage inputMessage)
		* 解析请求体

	void writeInternal(Object o, HttpOutputMessage outputMessage) 
		* 输出响应体
		
