package io.javaweb.community.utils;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import io.javaweb.community.code.RedisKeys;
import io.javaweb.community.exception.ServiceException;
import io.javaweb.community.redis.RedisService;
import io.javaweb.community.web.support.HttpMessage;
import io.javaweb.community.web.support.SessionHolder;
/**
 * 
 * @author KevinBlandy
 *
 */
public class WebUtils {
	
	private WebUtils() {
		 throw new AssertionError("No io.javaweb.bbs.utils.WebUtils instances for you!");
	}
	
	private static final String AJAX_HEADER_NAME = "x-requested-with";	//ajax ����ͷkey
		
	private static final String AJAX_HEADER_VALUE = "XMLHttpRequest";	//ajax ����ͷvalue
	
	private static final String CORS_REQUEST_HEAD = "Origin";			//cors ����ͷ
	
	private static final String ERROR_PAGE = "error/error";				//�쳣ҳ��
	
	private static final String DEFAULT_DOMAIN = GeneralUtils.isWindows() ? "localhost" : ".javaweb.io"; //Cookie Domain
	
	private static RedisService redisService;							//redis ����
	
	public static final Integer SESSION_TIME_OUT = 1800;				//�Ự��ʱ
	
	public static final Integer ACTIVATE_TIME_OUT = 300;				//�����볬ʱ
	
	public static final String SESSION_COOKIE_NAME  = "PHPSESSIONID";	//Cookie Name (*^__^*) ����
	
	/**
	 * �Ƿ���Ajax����
	 * @param request
	 * @return
	 */
    public static boolean isAjaxRequest(HttpServletRequest request){
    	String header = request.getHeader(AJAX_HEADER_NAME);
	    return header == null ? false : header.equalsIgnoreCase(AJAX_HEADER_VALUE);
    }
    
    /**
     * �Ƿ���OPTIONS����(CORSԤ����)
     * @param request
     * @return
     */
    public static boolean isOptionsRequest(HttpServletRequest request) {
    	return request.getMethod().equalsIgnoreCase(RequestMethod.OPTIONS.name());
    }
    
    /**
     * �Ƿ���CORS����
     * @param request
     * @return
     */
    public static boolean isCorsRequest(HttpServletRequest request) {
    	return !GeneralUtils.isEmpty(request.getHeader(CORS_REQUEST_HEAD));
    }
    
    /**
     * HttpServletResponse,����CORS֧��
     * @param response
     * @param allowOrigin 
     */
    public static void supportCors(HttpServletResponse response,String allowOrigin) {
    	response.addHeader("Access-Control-Allow-Origin", allowOrigin);
    	response.addHeader("Access-Control-Allow-Headers", "Origin,x-requested-with,Content-Type,Accept");
    	response.addHeader("Access-Control-Allow-Credentials", "true");
    	response.addHeader("Access-Control-Allow-Methods", "GET,POST,PUT,OPTIONS,DELETE");
    	//response.addHeader("Access-Control-Expose-Headers", "");	
    }
    
    /**
     * Json��Ӧ�ͻ���
     * @param response
     * @param data
     * @throws IOException
     */
    public static <T> void jsonResponse(HttpServletResponse response,T data) throws IOException {
		response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
		response.getWriter().write(JsonUtils.beanToJson(data));
		response.flushBuffer();
    }
    
    /**
     * JS�ض���
     * @param response
     * @param path
     * @throws IOException
     */
    public static void jsRedirect(HttpServletResponse response,String path) throws IOException{
    	response.setContentType(MediaType.TEXT_HTML_VALUE);
        String script = "<script>top.location.href=\"" + path + "\"</script>";
        response.getWriter().write(script);
        response.flushBuffer();
    }
    
    /**
     * �����쳣ModelAndView,����ֱ��Ajax��Ӧ�ͻ���
     * @param request
     * @param response
     * @param message
     * @return
     * @throws IOException
     */
    public static <T> ModelAndView exceptionResponse(HttpServletRequest request,HttpServletResponse response,HttpMessage<T> message) throws IOException {
		if(WebUtils.isAjaxRequest(request)) {
			WebUtils.jsonResponse(response, message);
			return null;
		}
		ModelAndView modelAndView = new ModelAndView(ERROR_PAGE);
		modelAndView.addObject("message", message);
		return modelAndView;
	}
    
    /**
     * ��ȡָ�����Ƶ�Cookie
     * @param request
     * @param cookieName
     * @return
     */
    public static Cookie getCookie(HttpServletRequest request,String cookieName) {
    	Cookie[] cookies = request.getCookies();
    	if(!GeneralUtils.isEmpty(cookies)) {
    		for(Cookie cookie : cookies) {
    			if(cookie.getName().equalsIgnoreCase(cookieName)) {
    				return cookie;
    			}
    		}
    	}
    	return null;
    }
    
    /**
     * ��ȡָ������Cookie��ֵ
     * @param request
     * @param cookieName
     * @return
     */
    public static String getCookieValue(HttpServletRequest request,String cookieName) {
    	Cookie cookie = getCookie(request, cookieName);
    	return cookie == null ? null : cookie.getValue();
    }
    
    /**
     * ��ȡ�Ự�û�ID
     * @param request
     * @return
     */
    public static String getSessionUser(HttpServletRequest request) {
		String cookieVale = getCookieValue(request,SESSION_COOKIE_NAME);
    	if(!GeneralUtils.isEmpty(cookieVale)) {
    		return getRedisService().get(RedisKeys.getSessionUserKey(cookieVale));
    	}
    	return null;
    }
    
    /**
     * ���ٵ�ǰSession
     * @param request
     * @param response
     */
    public static void invalidateSession(HttpServletRequest request,HttpServletResponse response) {
    	SessionHolder.USER.remove();
    	Cookie cookie = getCookie(request,SESSION_COOKIE_NAME);
    	if(cookie != null) {
    		cookie.setMaxAge(0);
        	response.addCookie(cookie);
        	getRedisService().del(RedisKeys.getSessionUserKey(cookie.getValue()));
    	}
    }
    
    /**
     *  ����Session����ʱ��
     * @param cookieVale
     * @param timeOut
     */
    public static void refreshSession(String cookieVale,Integer timeOut) {
    	getRedisService().expire(RedisKeys.getSessionUserKey(cookieVale), timeOut == null ? SESSION_TIME_OUT : timeOut);
    }
    
    /**
     * ����Session
     * @param response
     * @param userId
     * @param domain
     * @return
     */
    public static String setSession(HttpServletResponse response,Serializable userId,String domain) {
    	String cookieVale = GeneralUtils.getUUID();
    	Cookie cookie = new Cookie(SESSION_COOKIE_NAME,cookieVale);
    	cookie.setDomain(GeneralUtils.isEmpty(domain) ? DEFAULT_DOMAIN : domain);
    	cookie.setPath("/");
    	cookie.setMaxAge(-1);
    	cookie.setHttpOnly(Boolean.TRUE);		//������JS��д
    	response.addCookie(cookie);
    	getRedisService().set(RedisKeys.getSessionUserKey(cookieVale), String.valueOf(userId), SESSION_TIME_OUT);
    	return cookieVale;
    }
    
    /**
     * ��ȡ�ͻ�����ʵ��IP
     * @param request
     * @return
     */
    public static String getClientIP(HttpServletRequest request) {
    	String ip = request.getHeader("X-Requested-For");
		if (GeneralUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Forwarded-For");
		}
		if (GeneralUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (GeneralUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (GeneralUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (GeneralUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if(GeneralUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Real-IP");
		}
		if (GeneralUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
    }

	/**
	 * ��ȡ�ͻ��˵���ʵIP��ַ��Copy Druid��д����
	 * https://github.com/alibaba/druid/blob/master/src/main/java/com/alibaba/druid/util/DruidWebUtils.java
	 * @param request
	 * @return
	 */
	public static String getRemoteAddress(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip != null && !isValidAddress(ip)) {
			ip = null;
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
			if (ip != null && !isValidAddress(ip)) {
				ip = null;
			}
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
			if (ip != null && !isValidAddress(ip)) {
				ip = null;
			}
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
			if (ip != null && !isValidAddress(ip)) {
				ip = null;
			}
		}
		return ip;
	}

	private static boolean isValidAddress(String ip) {
		if (ip == null) {
			return false;
		}
		for (int i = 0; i < ip.length(); ++i) {
			char ch = ip.charAt(i);
			if (ch >= '0' && ch <= '9') {
			} else if (ch >= 'A' && ch <= 'F') {
			} else if (ch >= 'a' && ch <= 'f') {
			} else if (ch == '.' || ch == ':') {
				//
			} else {
				return false;
			}
		}
		return true;
	}

    /**
     * ��֤��У��
     * @param validateCode			У����
     * @param verifyCode			��֤��
     * @throws ServiceException
     */
    public static void validateVerifyCode(String validateCode,String verifyCode) throws ServiceException {
    	if(!GeneralUtils.isEmpty(validateCode) && !GeneralUtils.isEmpty(verifyCode)) {
    		String code = getRedisService().get(RedisKeys.getSessionVerifyKey(validateCode));
        	if(!GeneralUtils.isEmpty(code)) {
        		getRedisService().del(RedisKeys.getSessionVerifyKey(validateCode));		//һ��һ��֤
        		if(code.equalsIgnoreCase(verifyCode)) {
        			return;
        		}
        	}
    	}
    	throw new ServiceException("��֤�����", HttpMessage.State.VERIFY_CODE_FAILD);
    }
    
    /**
     * ��ȡ������
     * @param userId
     * @return
     */
    public static String getActivateCode(String userId) {
    	String code = GeneralUtils.getUUID() + GeneralUtils.getUUID();
    	getRedisService().set(RedisKeys.getActivateCodeKey(userId), code,ACTIVATE_TIME_OUT);
    	return code;
    }
    
    /**
     * ��ȡRedis����
     * @return
     */
    private static RedisService getRedisService() {
    	if(redisService == null) {
    		redisService = SpringContext.getBean(RedisService.class);
    	}
    	return redisService;
    }
}
