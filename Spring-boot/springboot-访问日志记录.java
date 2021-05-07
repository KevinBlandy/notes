package com.demo.web.filter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.NestedServletException;

@Component
@WebFilter(filterName = "accessLogFilter", urlPatterns = "/*")
@Order(-9999) 		// 保证最先执行
public class AccessLogFilter extends HttpFilter {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AccessLogFilter.class);
	
	private static final long serialVersionUID = -7791168563871425753L;
	
	// 消息体过大
	@SuppressWarnings("unused")
	private static class PayloadTooLargeException extends RuntimeException {
		private static final long serialVersionUID = 3273651429076015456L;
		private final int maxBodySize;
		public PayloadTooLargeException(int maxBodySize) {
			super();
			this.maxBodySize = maxBodySize;
		}
	}

	@Override
	protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
		
		ContentCachingRequestWrapper cachingRequestWrapper = new ContentCachingRequestWrapper(req, 30) { // 限制30个字节
			@Override
			protected void handleContentOverflow(int contentCacheLimit) {
				throw new PayloadTooLargeException(contentCacheLimit);
			}
		};
		
		ContentCachingResponseWrapper cachingResponseWrapper = new ContentCachingResponseWrapper(res);
		
		
		long start = System.currentTimeMillis();
		try {
			// 执行请求链
			super.doFilter(cachingRequestWrapper, cachingResponseWrapper, chain);
		} catch (NestedServletException e) {
			Throwable cause = e.getCause();
			// 请求体超过限制，以文本形式给客户端响应异常信息提示
			if (cause instanceof PayloadTooLargeException) {
				cachingResponseWrapper.setStatus(HttpServletResponse.SC_REQUEST_ENTITY_TOO_LARGE);
				cachingResponseWrapper.setContentType(MediaType.TEXT_PLAIN_VALUE);
				cachingResponseWrapper.setCharacterEncoding(StandardCharsets.UTF_8.displayName());
				cachingResponseWrapper.getOutputStream().write("请求体过大".getBytes(StandardCharsets.UTF_8));
			} else {
				throw new RuntimeException(e);
			}
		}
		
		long end = System.currentTimeMillis();
		
		String requestId = UUID.randomUUID().toString();		// 生成唯一的请求ID
		cachingResponseWrapper.setHeader("x-request-id", requestId);
		
		String requestUri = req.getRequestURI();		// 请求的
		String queryParam = req.getQueryString();		// 查询参数
		String method = req.getMethod();				// 请求方法
		int status = cachingResponseWrapper.getStatus();// 响应状态码
		
		// 请求体
		// 转换为字符串，在限制请求体大小的情况下，因为字节数据不完整，这里可能乱码，
		String requestBody = new String(cachingRequestWrapper.getContentAsByteArray(), StandardCharsets.UTF_8);	
		// 响应体
		String responseBody = new String(cachingResponseWrapper.getContentAsByteArray(), StandardCharsets.UTF_8);
		
		LOGGER.info("{} {}ms", requestId, end - start);
		LOGGER.info("{} {} {} {}", method, requestUri, queryParam, status);
		LOGGER.info("{}", requestBody);
		LOGGER.info("{}", responseBody);
		
		// 这一步很重要，把缓存的响应内容，输出到客户端
		cachingResponseWrapper.copyBodyToResponse();
	}
}
