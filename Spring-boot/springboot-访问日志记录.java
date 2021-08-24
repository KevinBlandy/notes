
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
@Order(-9999) 		// ��֤����ִ��
public class AccessLogFilter extends HttpFilter {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AccessLogFilter.class);
	
	private static final long serialVersionUID = -7791168563871425753L;
	
	// ��Ϣ�����
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
		
		ContentCachingRequestWrapper cachingRequestWrapper = new ContentCachingRequestWrapper(req, 30) { // ����30���ֽ�
			@Override
			protected void handleContentOverflow(int contentCacheLimit) {
				throw new PayloadTooLargeException(contentCacheLimit);
			}
		};
		
		ContentCachingResponseWrapper cachingResponseWrapper = new ContentCachingResponseWrapper(res);
		
		
		long start = System.currentTimeMillis();
		try {
			// ִ��������
			super.doFilter(cachingRequestWrapper, cachingResponseWrapper, chain);
		} catch (NestedServletException e) {
			Throwable cause = e.getCause();
			// �����峬�����ƣ����ı���ʽ���ͻ�����Ӧ�쳣��Ϣ��ʾ
			if (cause instanceof PayloadTooLargeException) {
				cachingResponseWrapper.setStatus(HttpServletResponse.SC_REQUEST_ENTITY_TOO_LARGE);
				cachingResponseWrapper.setContentType(MediaType.TEXT_PLAIN_VALUE);
				cachingResponseWrapper.setCharacterEncoding(StandardCharsets.UTF_8.displayName());
				cachingResponseWrapper.getOutputStream().write("���������".getBytes(StandardCharsets.UTF_8));
			} else {
				throw new RuntimeException(e);
			}
		}
		
		long end = System.currentTimeMillis();
		
		String requestId = UUID.randomUUID().toString();		// ����Ψһ������ID
		cachingResponseWrapper.setHeader("x-request-id", requestId);
		
		String requestUri = req.getRequestURI();		// �����
		String queryParam = req.getQueryString();		// ��ѯ����
		String method = req.getMethod();				// ���󷽷�
		int status = cachingResponseWrapper.getStatus();// ��Ӧ״̬��
		
		// ������
		// ת��Ϊ�ַ������������������С������£���Ϊ�ֽ����ݲ�����������������룬
		String requestBody = new String(cachingRequestWrapper.getContentAsByteArray(), StandardCharsets.UTF_8);	
		// ��Ӧ��
		String responseBody = new String(cachingResponseWrapper.getContentAsByteArray(), StandardCharsets.UTF_8);
		
		LOGGER.info("{} {}ms", requestId, end - start);
		LOGGER.info("{} {} {} {}", method, requestUri, queryParam, status);
		LOGGER.info("{}", requestBody);
		LOGGER.info("{}", responseBody);
		
		// ��һ������Ҫ���ѻ������Ӧ���ݣ�������ͻ���
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
 * ������־��¼
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
	
	// ������־��ר�� GSON ��ʽ��
	private Gson logGson;
	
	@Override
	public void init() throws ServletException {
		super.init();
		logGson = new GsonBuilder()
				.disableHtmlEscaping()				// ������HTML
				.setPrettyPrinting()				// ��ʽ��
				.create();
	}

	@Override
	protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
		
		
		// ���� MuiltiPart ����
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
		
		// ����������־
		String accessLog = logGson.toJson(this.accessLog(request, response));
		
		LOGGER.debug("access log: {}{}{}", requestId, LINE_SEPARATOR, accessLog);
		
		response.copyBodyToResponse();  // �ѻ����������Ӧ���ͻ���
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
		 * TODO ��Undertow�����£�����������峬������(server.undertow.max-http-post-size)�����׳��쳣��RequestTooBigException����ôRequestBody��ȡ������Ϊ���ַ���
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
