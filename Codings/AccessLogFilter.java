package io.javaweb.paste.web.filter;

import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.DispatcherType;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
/**
 * 
 * 
 * @author KevinBlandy
 *
 */
@WebFilter(displayName = "accessLogFilter", urlPatterns = "/*", dispatcherTypes = { DispatcherType.REQUEST })
public class AccessLogFilter extends HttpFilter {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2829826365107767069L;

	private static final Logger LOGGER = LoggerFactory.getLogger(AccessLogFilter.class);

	@Override
	protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws IOException, ServletException {

		String uri = req.getRequestURI();
		String method = req.getMethod();
		String remoteAddr = req.getRemoteAddr();

		LOGGER.info("┏  [client] ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
		LOGGER.info("┃ uri: {}", uri);
		LOGGER.info("┃ method: {}", method);
		LOGGER.info("┃ remoteAddr: {}", remoteAddr);
		LOGGER.info("┗  ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
		
		// headers
		Enumeration<String> headerEnumeration = req.getHeaderNames();
		LOGGER.info("┏  [Headers] ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
		while (headerEnumeration.hasMoreElements()) {
			String headerName = headerEnumeration.nextElement();
			String headerValue = req.getHeader(headerName);
			LOGGER.info("┃ {}: {}", headerName, headerValue);
		}
		LOGGER.info("┗  ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

		// body
		String contentType = req.getHeader(HttpHeaders.CONTENT_TYPE);
		LOGGER.info("┏  [body] ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
		LOGGER.info("┃ Content-Type: {}", contentType);
		LOGGER.info("┃ ");
		if (!StringUtils.isEmpty(contentType)) {
			MediaType mediaType = MediaType.parseMediaType(contentType);
			if (MediaType.APPLICATION_JSON.isCompatibleWith(mediaType) || MediaType.TEXT_PLAIN.isCompatibleWith(mediaType)) { // json/text

				LOGGER.debug("┃ {}", "Unreadable!!!"); // TODO 流重用

			} else if (MediaType.APPLICATION_FORM_URLENCODED.isCompatibleWith(mediaType)) { // form
				Map<String, String[]> parameterMap = req.getParameterMap();
				parameterMap.entrySet().forEach(entry -> {
					LOGGER.debug("┃ {}={}", entry.getKey(), Stream.of(entry.getValue()).collect(Collectors.joining(",", "", "")));
				});
			} else if (MediaType.MULTIPART_FORM_DATA.isCompatibleWith(mediaType)) { // multipart
				Collection<Part> parts = req.getParts();
				parts.forEach(part -> {
					
					String name = part.getName();
					String submittedFileName = part.getSubmittedFileName();
					Collection<String> partHeaderNames = part.getHeaderNames();
					
					LOGGER.debug("┃ ┌ {} ───────────────────────────────────", name);
					if(!StringUtils.isEmpty(submittedFileName)) {
						LOGGER.debug("┃ ├ FileName: {} ", submittedFileName);	
					}
					try {
						LOGGER.debug("┃ ├ size: {}", part.getInputStream().available());
					} catch (IOException e) {
						e.printStackTrace();
						LOGGER.debug("┃ ├ size: {}", "Unknown");
					}
					
					partHeaderNames.forEach(header -> {
						LOGGER.debug("┃ ├ {}: {}",header, part.getHeaders(header).stream().collect(Collectors.joining(",", "", "")));
					});
					
					LOGGER.debug("┃ └ ───────────────────────────────────");
				});
			}
		} else {
			LOGGER.info("┃ Unsupported Content-Type Or Request Body Is None !!!");
		}
		LOGGER.info("┗  ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
		super.doFilter(req, res, chain);
	}
}
