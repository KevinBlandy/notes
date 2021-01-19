————————————————————————————————
一,文件下载 					|
————————————————————————————————
	> 文件下载方式
		1,超链接
		2,服务端编码,通过流向客户端写回
	> 客户端
		* 两头一流
		1,response.setContentType(String mimetype);
		2,response.setHeader("Content-disposition;filename=xxx");
		3,通过response,获取流对象,把下载的文件进行输出!
		
————————————————————————————————
二,	struts2文件下载				|
————————————————————————————————
	> 通过<result type="stream">完成
		* stream这东西还是在struts-default.xml中
		<result-type name="stream" class="org.apache.struts2.dispatcher.StreamResult"/>
	> StreamResult
		> 在这个类中有三个属性
		* protected String contentType = "text/plain";			\\用于设置下载文件的mimeType类型
		* protected String contentDisposition = "inline";		\\用于设置进行下载操作,以及下载操作的名称
		* protected InputStream inputStream;					\\用于读取要下载的文件,输入流!读哪个文件,就下哪个文件
	> 	xml配置
		<action name="" class="处理下载请求的Action" method="处理下载请求的方法">
			<result name="" type="stream">
				<param name="contentType">${contentType}</param>	//在Action方法内部要提供getContentType方法,返回下载文件的类型
				<param name="contentDisposition">attachment;filename=${fileName}</param>//在方法内提供getFileName方法,返回下载文件的名称
				<param name="inputStream">${inputStream}</param>//这个返回的一个流对象,是读取下载文件的字节读取流.Action提供getInputStream
			</result>
		</action>
		
	* 注意有些问题
		1,如果是客户端是通过超连接的方式进行进行下载的,且,请求参数中的中文会影响下载.那么我们有必要处理请求参数中的中文乱码!'struts2没有处理get请求的乱码问题'
			* 在Tomcat的xml文件中配置(不建议)
			* 把请求参数进行转码,new String(fileName.getBytes("ios-8859-1"),"utf-8");(建议)
		2,得到文件类型的方法
			*  ServletContext.getMimeType("文件路径");//返回String字符串,就是文件类型
		3,如果Action中,返回下载文件读取流的方法就叫做'inputStream'!那么可以在配置文件中省略不写!
			* 配置文件中inputStream,的值,不能是in!也就是说Action中返回下载文件读取流的方法名称不能是in,因为in已经在StreamResult类中定义过了!
		4,在客户端中,下载框.文件名的乱码问题
			* FireFox:Base64编码
			* 其他大部分浏览器都使用URL编码
			  解决方案就是,先获取客户端浏览器版本!通过User-Agent请求头获取
			  然后再根据浏览器类型来进行编码处理
			* 通用方案可以对付大部分浏览器:
			  fileName = new String(fileName.getBytes("GBK"),"ISO-8859-1");
			  这样子就能把文件名搞成iso-8859-1的编码作为浏览器下载框的名称显示
		5,记住,是type="stream".不是name!
		6,在struts2中进行下载的时候,如果使用<result type="stream">它,有缺陷.例如:客户端点击下载后,取消下载.服务端会抛出异常
			* 在开发中,解决方案:可以下载一个struts2下载操作的插件.它解决了stream问题
————————————————————————————————
三,	struts2文件下载(旧笔记)		|
————————————————————————————————
	> 以前跟催希凡老师做的笔记,还是留着的！
	下载,就是给客户端响应字节数据
  原来我们响应的都是html页面的字符数据。
  其实就是把一个文件变成字节数组,然后使用response.getOutputStream()的一个流来响应字节数组数据

下载的要求
	* 两个头,一个流！
	响应头
	> Content-Type:image/jpeg	--给客户端传了一个图片文件
	   -- 通过文件名称调用ServletContext.getMimeType("文件路径");来获取文件类型
	> Content-Disposition:		--默认值是:inline,表示在浏览器窗口中打开(浏览器差异会有问题)
					--修改值为:attachment;filename=xxx,表示我们响应之中带了一个附件,名字叫做xxx
	   -- 在filename=后面的值,是显示在下载框中的文件名称
	流
	> 要下载的数据
	   -- 自己建议一个与文件相关的字节流对象就好

	下载的细节问题
	显示在下载框中的中文乱码问题
	一下几个浏览器的编码问题
	1,FireFox:Base64编码
	   *  
	2,其他大部分浏览器都使用URL编码
	解决方案就是,先获取客户端浏览器版本!通过User-Agent请求头获取
	然后再根据浏览器类型来进行编码处理

	通用方案可以对付大部分浏览器:
		fileName = new String(fileName.getBytes("GBK"),"ISO-8859-1");
		这样子就能把文件名搞成iso-8859-1的编码作为浏览器下载框的名称显示
	

	///////////////还有一种简单粗暴的方法,直接可以把Action处理方法的返回值设置为null
	HttpServletResponse response = ServletActionContext.getResponse();
	//响应头-类型
	response.setContentType("image/jpeg");
	//响应头-文件名 & 处理方式
	response.setHeader("Content-Disposition","attachment;filename="+new String("头像.jpeg".getBytes("GBK"),"ISO-8859-1"));
	//获取响应字节流
	OutputStream out = response.getOutputStream();
	...略