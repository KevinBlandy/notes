
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
package io.springcloud.web.filter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;


/**
 * 
 * 访问日志记录
 * 
 * @author KevinBlandy
 *
 */
@WebFilter(urlPatterns = "/**")
@Component
@Order(-7000)
public class AccessLogFilter extends ExcludeStaticPathFilter {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2067392425765043106L;
	
	public static final Logger LOGGER = LoggerFactory.getLogger(AccessLogFilter.class);
	
	private static final String LINE_SEPARATOR = System.getProperty("line.separator");
	
	// 请求日志的专用 GSON 格式化
	private Gson logGson;
	
	@Override
	public void init() throws ServletException {
		super.init();
		logGson = new GsonBuilder()
				.disableHtmlEscaping()				// 不编码HTML
				.setPrettyPrinting()				// 格式化
				.create();
	}

	@Override
	protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
		
		
		// 忽略 MuiltiPart 请求
		String contentType = req.getHeader(HttpHeaders.CONTENT_TYPE);
		if (StringUtils.hasLength(contentType) && contentType.startsWith(MediaType.MULTIPART_FORM_DATA_VALUE)) {
			super.doFilter(req, res, chain);
			return;
		}
		
		ContentCachingRequestWrapper request = new ContentCachingRequestWrapper(req);
		ContentCachingResponseWrapper response = new ContentCachingResponseWrapper(res);
		
		try {
			super.doFilter(request, response, chain);
		} catch (Exception e) {
			LOGGER.error("access error: {}", e.getMessage());
			throw e;
		}

		String requestId = req.getHeader(io.springcloud.constant.HttpHeaders.X_REQUEST_ID);
		
		// 解析请求日志
		String accessLog = logGson.toJson(this.accessLog(request, response));
		
		LOGGER.debug("access log: {}{}{}", requestId, LINE_SEPARATOR, accessLog);
		
		response.copyBodyToResponse();  // 把缓存的数据响应给客户端
	}
	
	private JsonObject accessLog (ContentCachingRequestWrapper req, ContentCachingResponseWrapper resp) {
		
		JsonObject accessLog = new JsonObject();
		
		// request method
		accessLog.addProperty("method", req.getMethod());
		// request url
		accessLog.addProperty("requestUrl", req.getRequestURL().toString());
		// query parmas
		accessLog.addProperty("queryParam", req.getQueryString());
		
		// request header
		Enumeration<String> nameEnumeration = req.getHeaderNames();
		JsonObject requestHeader = new JsonObject();
		while (nameEnumeration.hasMoreElements()) {
			String name = nameEnumeration.nextElement();
			Enumeration<String> valueEnumeration = req.getHeaders(name);
			JsonArray headers = new JsonArray();
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
		accessLog.addProperty("requestBody", new String(req.getContentAsByteArray(), StandardCharsets.UTF_8));
		
		// response header
		JsonObject resonseHeader = new JsonObject();
		for (String headerName : resp.getHeaderNames()) {
			JsonArray jsonArray = new JsonArray();
			resp.getHeaders(headerName).stream().forEach(jsonArray::add);
			resonseHeader.add(headerName, jsonArray);
		}
		accessLog.add("responseHeader", resonseHeader);
		
		// response status
		accessLog.addProperty("responseStatus", resp.getStatus());
		
		// response body
		accessLog.addProperty("responseBody", new String(resp.getContentAsByteArray(), StandardCharsets.UTF_8));
		
		return accessLog;
	}
}
