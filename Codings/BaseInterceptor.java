package com.kevin.blog.base;

import org.springframework.http.MediaType;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.kevin.blog.utils.GeneralUtils;
import com.kevin.blog.utils.JsonUtils;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by KevinBlandy on 2017/5/11 17:58
 */
public abstract class BaseInterceptor <T>implements HandlerInterceptor{
	
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception{
    	if(GeneralUtils.isOptionsRequest(httpServletRequest)) {
    		return Boolean.TRUE;
    	}
	    Method method = null;
	    if(handler instanceof HandlerMethod) {
	    	method = ((HandlerMethod) handler).getMethod();	
	    }
    	return this.doPreHandle(httpServletRequest, httpServletResponse,method);
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
    
    /**
     * 拦截执行
     * @param httpServletRequest
     * @param httpServletResponse
     * @param method
     * @return
     */
    public abstract boolean doPreHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,Method method)throws Exception;
    
    /**
     * 校验失败,执行响应
     * @param httpServletRequest
     * @param httpServletResponse
     * @param message		ajax,请求响应消息
     * @param path			同步请求,跳转页面
     * @throws IOException
     */
    protected void response(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,T message,String path) throws IOException{
    	if(GeneralUtils.isAjaxRequest(httpServletRequest)){
    		this.ajaxResponse(httpServletResponse, message);
    	}else{
    		this.htmlRedirect(httpServletRequest, httpServletResponse, path);
    	}
    }
    
    /**
     * ajax响应
     * @param httpServletResponse
     * @param message
     * @throws IOException
     */
    protected void ajaxResponse(HttpServletResponse httpServletResponse,T message) throws IOException{
    	httpServletResponse.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
		httpServletResponse.getWriter().write(JsonUtils.beanToJson(message));
		httpServletResponse.flushBuffer();
    }
    
    /**
     * Html重定向
     * @param httpServletRequest
     * @param httpServletResponse
     * @param path
     * @throws IOException
     */
    protected void htmlRedirect(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,String path) throws IOException{
    	httpServletResponse.setContentType(MediaType.TEXT_HTML_VALUE + ";charset=utf-8");
        String script = "<script>top.location.href=\""+httpServletRequest.getContextPath() + path +"\"</script>";
        httpServletResponse.getWriter().write(script);
        httpServletResponse.flushBuffer();
    }
}
