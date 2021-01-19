----------------------------
集成Servlet					|
----------------------------
	# 只需要在Servlet代码里引用ServletGroupTemplate就能集成Beetl
	# 他提供了一个render(String child, HttpServletRequest request, HttpServletResponse response)方法

		protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
			response.setContentType("text/html;charset=UTF-8");
			//模板直接访问users
			request.setAttribute("users",service.getUsers());
			// ServletGroupTemplate.instance()	返回单例对象
			ServletGroupTemplate.instance().render("/index.html", request, response);
		}
	
	# ServletGroupTemplate同其他web集成一样，将读取配置文件来配置

	# 如果需要通过代码配置，可以在Serlvet listener里 ServletGroupTemplate.instance().getGroupTemplate()方法获取GroupTemplate
