-----------------------------
限制请求大小
-----------------------------
	# 本身是有配置可以控制的，当然也可以自己写

	# 核心类
		LimitLengthBufferedReader			
		LimitLengthHttpServletRequest
		LimitLengthServletInputStream
		MaxPostSizeException
		LimitLengthRequestFilter
		ControllerExceptionAdvice

-----------------------------
LimitLengthHttpServletRequest
-----------------------------
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;


public class LimitLengthHttpServletRequest extends HttpServletRequestWrapper {

	private final long maxLength;
	
	public LimitLengthHttpServletRequest(HttpServletRequest request, long maxLenth) {
		super(request);
		this.maxLength = maxLenth;
	}

	@Override
	public BufferedReader getReader() throws IOException {
		return new BufferedReader(new InputStreamReader(this.getInputStream(), StandardCharsets.UTF_8));
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		return new LimitLengthServletInputStream(super.getInputStream(), this.maxLength);
	}
}



-----------------------------
LimitLengthServletInputStream
-----------------------------
package io.springcloud.web.wrapper;

import java.io.IOException;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;

public class LimitLengthServletInputStream extends ServletInputStream {

	private ServletInputStream in;

	private final long maxLength;
	
	private long total;

	public LimitLengthServletInputStream(ServletInputStream in, long maxLength) {
		super();
		this.in = in;
		this.maxLength = maxLength;
	}

	@Override
	public int readLine(byte[] b, int off, int len) throws IOException {
		int i = super.readLine(b, off, len);
		if (i >= 0) {
			incrementCounter(i);
		}
		return i;
	}
	
	@Override
	public int read() throws IOException {
		int i = in.read();
		if (i >= 0) {
			incrementCounter(1);
		}
		return i;
	}

	@Override
	public int read(byte b[]) throws IOException {
		return this.read(b, 0, b.length);
	}

	@Override
	public int read(byte b[], int off, int len) throws IOException {
		int i = in.read(b, off, len);
		if (i >= 0) {
			incrementCounter(i);
		}
		return i;
	}

	private void incrementCounter(int size) throws IOException {
		total += size;
		if (total > maxLength) {
			throw new MaxPostSizeException(this.maxLength);
		}
	}

	@Override
	public boolean isFinished() {
		return this.in.isFinished();
	}

	@Override
	public boolean isReady() {
		return this.in.isReady();
	}

	@Override
	public void setReadListener(ReadListener readListener) {
		this.in.setReadListener(readListener);
	}
}


-----------------------------
MaxPostSizeException
-----------------------------
package io.springcloud.web.wrapper;

import java.io.IOException;

public class MaxPostSizeException extends IOException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8904360989600640247L;

	private final long maxLength;
	
	public MaxPostSizeException(long maxLength) {
		super("request body exceeds limit, maximum allowed: " + maxLength);
		this.maxLength = maxLength;
	}

	public long getMaxLength() {
		return maxLength;
	}
}


-----------------------------
LimitLengthRequestFilter
-----------------------------
package io.springcloud.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.util.unit.DataSize;

import io.springcloud.web.wrapper.LimitLengthHttpServletRequest;



/**
 * 
 * 限制最大请求体
 * 
 * server.undertow.max-http-post-size 不生效，这是版本BUG，估计会在 springboot 2.6.x 修复
 * 
 */
@WebFilter(urlPatterns = "/**")
@Component
@Order(-8000)
public class LimitLengthRequestFilter extends HttpFilter {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3195769369814461381L;
	
	@Override
	protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
		
		// 忽略非 PUT/POST请求
		String method = req.getMethod();
		if (!(method.equalsIgnoreCase(HttpMethod.PUT.name()) || method.equals(HttpMethod.POST.name()))) {
			super.doFilter(req, res, chain);
			return ;
		}
		
		// 忽略 MuiltiPart 请求
		String contentType = req.getHeader(HttpHeaders.CONTENT_TYPE);
		if (StringUtils.hasLength(contentType) && contentType.startsWith(MediaType.MULTIPART_FORM_DATA_VALUE)) {
			super.doFilter(req, res, chain);
			return ;
		}
		
		super.doFilter(new LimitLengthHttpServletRequest(req, DataSize.ofMegabytes(3).toBytes()), res, chain);
	}
}

-----------------------------
ControllerExceptionAdvice
-----------------------------

	// 请求体读取过程中出现的异常
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public Object httpMessageNotReadable(HttpServletRequest request, HttpServletResponse response, HttpMessageNotReadableException exception) {
		
		Throwable cause = exception.getCause();
		
		if (cause instanceof JsonSyntaxException) {
			// JSON解析异常
			if (cause.getCause() instanceof MaxPostSizeException ) {
				// 请求体大小超出限制
				return this.errorHandler(request, response, Response.PAYLOAD_TOO_LARGE, exception);
			}
			return this.errorHandler(request, response, Response.BAD_REQUEST.withMessage("非法数据"), exception);
		}
		
		return this.errorHandler(request, response, Response.BAD_REQUEST.withMessage("请求体不能为空"), exception);
	}