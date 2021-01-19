关于重定向

* 一般情况下,控制器方法返回字符串类型的值会被当作逻辑视图的名处理 

*如果返回的字符串中带有forward/或者redirect前缀的时候.
  SpringMVC会对他们进行特殊,把foward和redirect当成指示符.其后的字符串作为url来处理

		redirect:success.jsp	//重定向
		forward:success.jsp		//转发

 * 如果是返回ModelAndView
		mv.setViewName("redirect:https://www.baidu.com?test=success");
