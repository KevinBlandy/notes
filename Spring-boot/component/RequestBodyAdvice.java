------------------------------
RequestBodyAdvice
------------------------------
	# �ɶ����������Ԥ����
		boolean supports(MethodParameter methodParameter, Type targetType,
			Class<? extends HttpMessageConverter<?>> converterType);
		
		HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter,
			Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException;
		
		Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter,
			Type targetType, Class<? extends HttpMessageConverter<?>> converterType);
		
		@Nullable
		Object handleEmptyBody(@Nullable Object body, HttpInputMessage inputMessage, MethodParameter parameter,
				Type targetType, Class<? extends HttpMessageConverter<?>> converterType);


------------------------------
Demo ����ͻ��˵ļ�������
------------------------------
package io.springcloud.web.advice;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;


@RestControllerAdvice
public class DecodeRequestBodyAdvice implements RequestBodyAdvice {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DecodeRequestBodyAdvice.class);

	@Override
	public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
		return true;
	}

	@Override
	public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
		
		/**
		 * 
		 * TODO ��Ҫ�ֶ��ر�����
		 * 
		 */
		try (InputStream inputStream = inputMessage.getBody()){
			
			byte[] data = StreamUtils.copyToByteArray(inputMessage.getBody());
			
			
			// TODO ����������н���
			
			LOGGER.debug("����������:{}", new String(data, StandardCharsets.UTF_8));
			
			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
			
			return new HttpInputMessage() {
				@Override
				public HttpHeaders getHeaders() {
					return inputMessage.getHeaders();
				}
				@Override
				public InputStream getBody() throws IOException {
					return byteArrayInputStream;
				}
			};
		}
	}

	@Override
	public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
		return body;
	}

	@Override
	public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
		return body;
	}

}
