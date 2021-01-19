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
	
	private static final String AJAX_HEADER_NAME = "x-requested-with";	//ajax 请求头key
		
	private static final String AJAX_HEADER_VALUE = "XMLHttpRequest";	//ajax 请求头value
	
	private static final String CORS_REQUEST_HEAD = "Origin";			//cors 请求头
	
	private static final String ERROR_PAGE = "error/error";				//异常页面
	
	private static final String DEFAULT_DOMAIN = GeneralUtils.isWindows() ? "localhost" : ".javaweb.io"; //Cookie Domain
	
	private static RedisService redisService;							//redis 服务
	
	public static final Integer SESSION_TIME_OUT = 1800;				//会话超时
	
	public static final Integer ACTIVATE_TIME_OUT = 300;				//激活码超时
	
	public static final String SESSION_COOKIE_NAME  = "PHPSESSIONID";	//Cookie Name (*^__^*) 哈哈
	
	/**
	 * 是否是Ajax请求
	 * @param request
	 * @return
	 */
    public static boolean isAjaxRequest(HttpServletRequest request){
    	String header = request.getHeader(AJAX_HEADER_NAME);
	    return header == null ? false : header.equalsIgnoreCase(AJAX_HEADER_VALUE);
    }
    
    /**
     * 是否是OPTIONS请求(CORS预请求)
     * @param request
     * @return
     */
    public static boolean isOptionsRequest(HttpServletRequest request) {
    	return request.getMethod().equalsIgnoreCase(RequestMethod.OPTIONS.name());
    }
    
    /**
     * 是否是CORS请求
     * @param request
     * @return
     */
    public static boolean isCorsRequest(HttpServletRequest request) {
    	return !GeneralUtils.isEmpty(request.getHeader(CORS_REQUEST_HEAD));
    }
    
    /**
     * HttpServletResponse,设置CORS支持
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
     * Json响应客户端
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
     * JS重定向
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
     * 返回异常ModelAndView,或者直接Ajax响应客户端
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
     * 获取指定名称的Cookie
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
     * 获取指定名称Cookie的值
     * @param request
     * @param cookieName
     * @return
     */
    public static String getCookieValue(HttpServletRequest request,String cookieName) {
    	Cookie cookie = getCookie(request, cookieName);
    	return cookie == null ? null : cookie.getValue();
    }
    
    /**
     * 获取会话用户ID
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
     * 销毁当前Session
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
     *  重置Session过期时间
     * @param cookieVale
     * @param timeOut
     */
    public static void refreshSession(String cookieVale,Integer timeOut) {
    	getRedisService().expire(RedisKeys.getSessionUserKey(cookieVale), timeOut == null ? SESSION_TIME_OUT : timeOut);
    }
    
    /**
     * 设置Session
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
    	cookie.setHttpOnly(Boolean.TRUE);		//不允许JS读写
    	response.addCookie(cookie);
    	getRedisService().set(RedisKeys.getSessionUserKey(cookieVale), String.valueOf(userId), SESSION_TIME_OUT);
    	return cookieVale;
    }
    
    /**
     * 获取客户端真实的IP
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
     * 验证码校验
     * @param validateCode			校验码
     * @param verifyCode			验证码
     * @throws ServiceException
     */
    public static void validateVerifyCode(String validateCode,String verifyCode) throws ServiceException {
    	if(!GeneralUtils.isEmpty(validateCode) && !GeneralUtils.isEmpty(verifyCode)) {
    		String code = getRedisService().get(RedisKeys.getSessionVerifyKey(validateCode));
        	if(!GeneralUtils.isEmpty(code)) {
        		getRedisService().del(RedisKeys.getSessionVerifyKey(validateCode));		//一码一验证
        		if(code.equalsIgnoreCase(verifyCode)) {
        			return;
        		}
        	}
    	}
    	throw new ServiceException("验证码错误", HttpMessage.State.VERIFY_CODE_FAILD);
    }
    
    /**
     * 获取激活码
     * @param userId
     * @return
     */
    public static String getActivateCode(String userId) {
    	String code = GeneralUtils.getUUID() + GeneralUtils.getUUID();
    	getRedisService().set(RedisKeys.getActivateCodeKey(userId), code,ACTIVATE_TIME_OUT);
    	return code;
    }
    
    /**
     * 获取Redis服务
     * @return
     */
    private static RedisService getRedisService() {
    	if(redisService == null) {
    		redisService = SpringContext.getBean(RedisService.class);
    	}
    	return redisService;
    }
}
