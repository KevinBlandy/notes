---------------------------------
Advice系列
---------------------------------
	# ResponseBodyAdvice

		boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType);

		@Nullable
		T beforeBodyWrite(@Nullable T body, MethodParameter returnType, MediaType selectedContentType,
				Class<? extends HttpMessageConverter<?>> selectedConverterType,
				ServerHttpRequest request, ServerHttpResponse response);
	
	# RequestBodyAdvice


		boolean supports(MethodParameter methodParameter, Type targetType,
				Class<? extends HttpMessageConverter<?>> converterType);

		HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter,
				Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException;

		Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter,
				Type targetType, Class<? extends HttpMessageConverter<?>> converterType);

		@Nullable
		Object handleEmptyBody(@Nullable Object body, HttpInputMessage inputMessage, MethodParameter parameter,
				Type targetType, Class<? extends HttpMessageConverter<?>> converterType);


	
	
	# 请求和响应 Advice, 可以在请求体和响应体被编码/解码前, 对进行数进行处理
		* 处理发生在, HttpMessageConverter 执行之前

	# 自定义实现后, 添加 @ControllerAdvice 激活

------------------------------------------
对响应体中的 data字段，进行base64编码
------------------------------------------

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;


import com.google.gson.Gson;

import io.streamer.common.Message;
/**
 * 
 * 对响应体进行加密
 * @author Administrator
 *
 */
@ControllerAdvice
public class EncodeResponseBodyAdvice implements ResponseBodyAdvice<Message<Object>> {
	
	static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(EncodeResponseBodyAdvice.class);
	
	@Autowired
	Gson gson;

	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		/**
		 * 返回对象必须是  Message 并且使用了 GsonHttpMessageConverter 
		 */
		return Message.class.isAssignableFrom(returnType.getParameterType()) 
				// && GsonHttpMessageConverter.class.isAssignableFrom(converterType)
				;
	}

	@Override
	public Message<Object> beforeBodyWrite(Message<Object> body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
		Object data = body.getData();
		if (data != null) {
			// 重写value
			body.setData(this.encode(data));
		}
		return body;
	}
	
	private String encode(Object data) {
		String jsonValue = gson.toJson(data);
		String encodeValue = Base64.getEncoder().encodeToString(jsonValue.getBytes(StandardCharsets.UTF_8));
		LOGGER.debug("对响应体进行编码：raw={},cipher={}", jsonValue, encodeValue);
		return encodeValue;
	}
}

	