---------------------------------
Web提供的全局变量				 |
---------------------------------
	request 
		* 中的所有attribute.在模板中可以直接通过attribute name 来引用
		* 如在controller层 request.setAttribute("user",user),则在模板中可以直接用${user.name} .

	session 
		* 提供了session会话，模板通过session["name"],或者session.name 引用session里的变量

	request 
		* 标准的 HTTPSerlvetRequest,可以在模板里引用request属性（getter），如${request.requestURL}。

	parameter 
		* 用户读取用户提交的参数。如${parameter.userId} 
		* (仅仅2.2.7以上版本支持)

	ctxPath 
		* Web应用ContextPath

	servlet 
		* 是WebVariable的实例
		* 包含了HTTPSession,HTTPSerlvetRequest,HTTPSerlvetResponse.三个属性
		* 模板中可以通过request.response,session 来引用，如 ${serlvet.request.requestURL};

	所有的GroupTemplate的共享变量

	pageCtx
		* 是一个内置方法 
		* 仅仅在web开发中，用于设置一个变量，然后可以在页面渲染过程中，调用此api获取，如pageCtx("title","用户添加页面")，在其后任何地方，可以pageCtx("title") 获取该变量。
		* (仅仅2.2.7以上版本支持)

---------------------------------
扩展							 |
---------------------------------
	# 需要扩展更多属性，你也可以配置beetl.properties配置文件的WEBAPP_EXT属性
	# 实现WebRenderExt接口，在渲染模板之前增加自己的扩展

		RESOURCE.root=/WEB-INF/views
		WEBAPP_EXT = com.park.oss.util.GlobalExt
	
	public class GlobalExt implements WebRenderExt{
        static long version = System.currentTimeMillis();
        @Override
        public void modify(Template template, GroupTemplate arg1, HttpServletRequest arg2, HttpServletResponse arg3) {
                //js,css 的版本编号
                template.binding("sysVersion",version);		//每次在模板里都可以访问变量sysVersion
        }
	}



	