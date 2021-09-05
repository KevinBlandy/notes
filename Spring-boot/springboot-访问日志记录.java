
----------
Filter1 
----------


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


----------
Filter2
----------
package com.xsh.common.web.filter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Enumeration;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;


/**
 * 
 * 访问日志记录
 * 
 * @author KevinBlandy
 *
 */
public abstract class AccessLogFilter extends HttpFilter {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2067392425765043106L;
	
	public static final Logger LOGGER = LoggerFactory.getLogger(AccessLogFilter.class);
	
	@Override
	protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {

		HttpServletRequest request = null;
		HttpServletResponse response = null;

		// Multipart 请求，不包装Request
		String contentType = req.getHeader(HttpHeaders.CONTENT_TYPE);
		if (StringUtils.hasLength(contentType) && contentType.startsWith(MediaType.MULTIPART_FORM_DATA_VALUE)) {
			request = req;
		} else {
			request = new ContentCachingRequestWrapper(req);
		}

		// 忽略文件下载，图片验证码，根据路径包含字符串判断
		// TODO 判断方式待优化
		String requestUir = req.getRequestURI();
		
		if (requestUir.contains("down") || requestUir.contains("captcha") || requestUir.contains("favicon.ico")) {
			response = res;
		} else {
			response = new ContentCachingResponseWrapper(res);
		}

		try {
			Instant start = Instant.now();
			
			super.doFilter(request, response, chain);
			
			Instant end = Instant.now();
			
			response.setIntHeader(com.xsh.common.constant.HttpHeaders.X_RESPONSE_TIME, (int) (end.toEpochMilli() - start.toEpochMilli()));
			
		} catch (Exception e) {
			LOGGER.error("access error: {}", e.getMessage());
			throw e;
		}

		this.onLog(request, response, this.accessLog(request, response));

		if (response instanceof ContentCachingResponseWrapper){
			((ContentCachingResponseWrapper) response).copyBodyToResponse();  // 把缓存的数据响应给客户端
		}
	}


	/**
	 * 访问日志解析
	 * @param req
	 * @param resp
	 * @return
	 */
	private JsonObject accessLog (HttpServletRequest req, HttpServletResponse resp) {

		JsonObject accessLog = new JsonObject();

		// 请求ID
		accessLog.addProperty("requestId", req.getHeader(com.xsh.common.constant.HttpHeaders.X_REQUEST_ID));
		// client address
		accessLog.addProperty("remoteAddress", req.getRemoteAddr());
		// request method
		accessLog.addProperty("method", req.getMethod());
		// request url
		accessLog.addProperty("requestUrl", req.getRequestURL().toString());
		// query params
		accessLog.addProperty("queryParam", req.getQueryString());
		
		// request header
		Enumeration<String> nameEnumeration = req.getHeaderNames();
		JsonObject requestHeader = new JsonObject();
		while (nameEnumeration.hasMoreElements()) {
			String name = nameEnumeration.nextElement();
			Enumeration<String> valueEnumeration = req.getHeaders(name);
			JsonArray headers = new JsonArray(1);
			while (valueEnumeration.hasMoreElements()) {
				headers.add(valueEnumeration.nextElement());
			}
			requestHeader.add(name, headers);
		}
		accessLog.add("requestHeader", requestHeader);
		
		// requestBody
		/**
		 * TODO 在Undertow环境下，如果是请求体超出限制(server.undertow.max-http-post-size)，而抛出异常：RequestTooBigException。那么RequestBody读取不到，为空字符串
		 */
		String requestBody = null;

		if (req instanceof ContentCachingRequestWrapper){
			requestBody = new String(((ContentCachingRequestWrapper)req).getContentAsByteArray(), StandardCharsets.UTF_8);
		}

		accessLog.addProperty("requestBody", requestBody);
		
		// response header
		JsonObject responseHeader = new JsonObject();
		for (String headerName : resp.getHeaderNames()) {
			JsonArray jsonArray = new JsonArray(1);
			resp.getHeaders(headerName).forEach(jsonArray::add);
			responseHeader.add(headerName, jsonArray);
		}
		
		// response status
		accessLog.addProperty("responseStatus", resp.getStatus());
		
		// response header
		accessLog.add("responseHeader", responseHeader);

		String responseBody = null;

		if (resp instanceof  ContentCachingResponseWrapper){
			responseBody = new String(((ContentCachingResponseWrapper)resp).getContentAsByteArray(), StandardCharsets.UTF_8);
		}
		
		// response body
		accessLog.addProperty("responseBody", responseBody);
		
		return accessLog;
	}

	protected abstract void onLog (HttpServletRequest request, HttpServletResponse response, JsonObject accessLog);
}


