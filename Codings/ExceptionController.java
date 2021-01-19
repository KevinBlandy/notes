package com.kevinblandy.simple.webchat.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.kevinblandy.simple.webchat.annotation.NoLogin;
import com.kevinblandy.simple.webchat.model.HttpMessage;
import com.kevinblandy.simple.webchat.model.StandardHttpMessages;
import com.kevinblandy.simple.webchat.utils.GeneralUtils;
import com.kevinblandy.simple.webchat.utils.JsonUtils;

/**
 * 异常处理Controller
 * @author	KevinBlandy
 * @version	1.0
 * @date	2017年5月52日 下午12:59:14
 */
@Controller
public class ExceptionController implements ErrorController{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionController.class);
	
	/**
	 * 异常页面
	 */
	private static final String ERROR_PATH = "error";
	
	@RequestMapping(value = ERROR_PATH,method = {RequestMethod.POST,RequestMethod.GET})
	@SuppressWarnings({ "rawtypes" })
	@NoLogin
	public ModelAndView error(HttpServletRequest request,
							  HttpServletResponse response) throws IOException{
		Class exceptionType = (Class) request.getAttribute("javax.servlet.error.exception_type");   //异常类型
		Throwable exception = (Throwable) request.getAttribute("javax.servlet.error.exception");    //异常类,只有在 500 异常的清空下,该值不为空
		Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");     //HTTP异常状态码
//		String servletName = (String) request.getAttribute("javax.servlet.error.servlet_name");     //异常的Servlet
		String erroPath = (String) request.getAttribute("javax.servlet.error.request_uri");         //发生了异常的请求地址(并不是当前地址-request.getRequestURI())
		
		LOGGER.error("服务器异常 exceptionType={},exception={},code={},path={}",exceptionType,exception,statusCode,erroPath);
		
		/**
		 * 防止部分浏览器、路由器(如小米)等劫持不显示自己的错误页面,强制将code设置为200
		 */
		response.setStatus(HttpServletResponse.SC_OK);
		HttpMessage<Void> message = null;
		if(statusCode == 400){
			message = StandardHttpMessages.BAD_REQUEST;					//参数校验失败
		}else if(statusCode == 404){
			message = StandardHttpMessages.NOT_FOND;					//请求页面未找到
		}else if(statusCode == 405){
			message = StandardHttpMessages.METHOD_NOT_SUPPORT;			//请求方法错误
		}else if(statusCode > 400 && statusCode < 500){
			message = StandardHttpMessages.REQUEST_ERROR;				//统一请求异常
		}else{
			message = StandardHttpMessages.SYSTEM_ERROR;				//系统异常
		}
		if(GeneralUtils.isAjax(request)){
			response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
			response.getWriter().write(JsonUtils.beanToJson(message));
			response.flushBuffer();
			return null;
		}
		ModelAndView modelAndView = new ModelAndView("error/error");
		modelAndView.addObject("message", message);
		return modelAndView;
	}
	
	@Override
	public String getErrorPath() {
		return ERROR_PATH;
	}
}
