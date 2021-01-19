import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

public class CorsFilter extends HttpFilter {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8387103310559517243L;
	
	private static final String OPTIONS_METHOD = "OPTIONS";

	@Override
	protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {

		String origin = req.getHeader(HttpHeaders.ORIGIN);
		
		
		// if (!isSameOrigin(req)) 
		if (!StringUtils.isEmpty(origin))
		{
			/**
			 * TODO 通过后台读取，配置
			 */
			res.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, origin);

			res.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "*");
			res.addHeader(HttpHeaders.ACCESS_CONTROL_REQUEST_HEADERS, "*");
			res.addHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "*");

			res.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
			res.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "GET, POST, PUT, OPTIONS, DELETE");
			
			/**
			 * 处理预检请求
			 */
			if (OPTIONS_METHOD.equalsIgnoreCase(req.getMethod())) {
				res.setStatus(HttpServletResponse.SC_NO_CONTENT);
				res.setContentType(MediaType.TEXT_HTML_VALUE);
				res.setCharacterEncoding("utf-8");
				res.setContentLength(0);
				res.addHeader(HttpHeaders.ACCESS_CONTROL_MAX_AGE, "30");
				return ;
			}
		}

		super.doFilter(req, res, chain);
	}
	
	public static boolean isSameOrigin(HttpServletRequest req) {
		
		String origin = req.getHeader(HttpHeaders.ORIGIN);
		
		if (origin == null) {
			return true;
		}
		
		String scheme = req.getScheme();
		String host = req.getServerName();
		int port = req.getServerPort();
		
		UriComponents originUrl = UriComponentsBuilder.fromOriginHeader(origin).build();
		
		return (ObjectUtils.nullSafeEquals(scheme, originUrl.getScheme()) &&
				ObjectUtils.nullSafeEquals(host, originUrl.getHost()) &&
				getPort(scheme, port) == getPort(originUrl.getScheme(), originUrl.getPort()));
	}
	
	public static int getPort(@Nullable String scheme, int port) {
		if (port == -1) {
			if ("http".equals(scheme) || "ws".equals(scheme)) {
				port = 80;
			}
			else if ("https".equals(scheme) || "wss".equals(scheme)) {
				port = 443;
			}
		}
		return port;
	}
}
