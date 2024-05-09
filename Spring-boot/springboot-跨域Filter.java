package chatty.web.filter;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CorsFilter extends HttpFilter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5388821806252472667L;

	@Override
	protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		String origin = request.getHeader(HttpHeaders.ORIGIN);

		if (StringUtils.hasText(origin)) {
			response.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, origin);
			
			String requestHeaders = request.getHeader(HttpHeaders.ACCESS_CONTROL_REQUEST_HEADERS);
			if (StringUtils.hasText(requestHeaders)) {
				response.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, requestHeaders);
			}
			
			/**
			 * “*” 可能有兼容性问题
			 */
			response.addHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "*");

			response.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
			response.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "GET, POST, PUT, OPTIONS, DELETE");

			/**
			 * 预检请求
			 */
			if (HttpMethod.OPTIONS.matches(request.getMethod())) {
				response.setContentType(MediaType.TEXT_HTML_VALUE);
				response.setCharacterEncoding("utf-8");
				response.setContentLength(0);
				response.addHeader(HttpHeaders.ACCESS_CONTROL_MAX_AGE, "30");
				response.setStatus(HttpServletResponse.SC_NO_CONTENT);
				return;
			}
		}

		super.doFilter(request, response, chain);
	}
}
