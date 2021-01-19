————————————————————————————————
一,文件上传回忆					|
————————————————————————————————
	struts2,上传.我们还是要回忆一下!曾经WEB的东西!
	'对客户端的要求'
		1,必须使用表单,而不能是超链接
		2,表单的method必须是POST而不是GET
		3,表单的enctype必须是:multipart/form-data(enctype="multipart/form-data")
		4,在表单中添加file表单字段,即<input type="file" name="xxx"/>
	'特点'
		> 多部件表单的体
		1,分割出多个部件,即一个表单相一个部件
		2,一个部件中,自己包含请求头和空行以及请求体
			*普通表单项
				> 1个头,:Content-Disposition:包含name="xxx".也就是表单名称
				> 体就是表单项的值
			* 文件表单项
				> 2个头	
					Content-Disposition:包含name="xxx".也就是表单名称 -- filename="xxx" 表示上传文件的名称
					Content-Type:它是上传文件的MIME类型,例如:image/pjpeg 表示上传的时候图片中jpg格式的图片
				> 体就是上传文件的内容

————————————————————————————————
二,struts2文件上传				|
————————————————————————————————
	> 在struts2中,默认情况下.stuts2使用的就是当年我们使用的那俩兄弟.
	*	commons-fileupload.jar	依赖下面的jar包才能完成工作
	*	commons-io.jar			IO却不依赖上面,可以自己单独发挥)
	> struts2使用了一个 Interceptor(拦截器嘛) 帮助我们完成文件上传操作
	> <interceptor-ref name="fileUpload"/>
		*  <interceptor name="fileUpload" class="org.apache.struts2.interceptor.FileUploadInterceptor"/>
		* 它是十八罗汉其中的一个,也就是说!你想要实现上传!你还得使用默认的拦截器!
		* 或者,自己在Action中单独配置这个拦截器
	['国际化错误信息']
		* 在struts2的核心包下有一个:struts-message.properties文件
		* 该文件以'通配符'的形式配置了一些信息,例如:错误提示信息等,我们可以利用

	1,建立接收文件的Action
		* 继承ActionSupport类把还是
	2,建立以下字段,注意,字段名称是有约束的!
		* private File xxx;//与表单文件项的name属性必须一致.该文件就是上传文件
		* private String xxxContentType;//上传文件的类型
		* private String xxxFileName;//上传文件的名称
		> xxx:代表的是表单页面中.file表单项的name属性<input type="file" name="xxx"/>.必须一样!
			* 这文件,就是上传的文件.框架自动的帮我们进行了封装处理!
		> xxxContentType:也是同样的必须上门的字段相吻合!
			* 它代表上传文件的类型,例如:image/jpeg
		> xxxFileName:代表上传文件的名称,也要符合上面的约束.
			* 就是上传文件的名称,是名称.不是name属性名称,例如:我的皂骗.jpg
	3,提供上述所有字段的,get/set方法!
	4,利用工具,处理文件!
		> FileUtils.copyFile(pic,new File("D:/file/"+picFileName));
		* 这东西把,就struts2提供的一个工具.你要乐意,自己写IO也行!
		* 实际开发中要考虑到,目录打散,文件重名等情况!另外得复习一下IO的知识!翻以前的笔记吧!

	['注意']
		> 经过测试,在上传文件的Action中可以同时存在javaBean,只要是提供了get/set.以及表单提供了对应的字段!
		> 还是能进行成功的赋值的!也就是说,同时上传文件和封装对象数据!都可以!

————————————————————————————————
三,struts2文件上传,细节性问题	|
————————————————————————————————
	> 以下一些关于文件上传的配置,都是来自于:default.properties的配置,当然,我们可以在xml中进行配置!
	1,配置input视图,作为文件上传失败后跳转页面!
		* 在文件上传的时候,如果发生错误!那么fileUpload拦截器会设置错误信息,workflow拦截器就会跳转到input视图
		* 在页面可以通过strust的标签<s:actionerror/>取出,记得导包
	['可以对错误信息,进行国际化处理']
		
	
	2,struts.multipart.parser=jakarta	'定义文件上传,采用commons-fileupload技术'
		* 同时支持cos,pell上传技术(如果使用其他上传技术,单独下载配置jar包)
		* 其实struts2提供了pell上传技术的支持jar包

	3,struts.multipart.maxSize			'设置上传文字的总大小限制(单位字节)'
		* struts.multipart.maxSize=2097152	['默认是2MB']	
		* 如果超过大小,那就会跳转到input视图
		* xml中配置,<constant name="struts.multipart.maxSize" value="2097152"/>
		* 该设置对全局的上传都有效
	['上传大小/文件类型限制,单独的对某个form/Action生效']
		> 可以设置'fileUpload'拦截器的属性来完成!
			* maximumSize:上传文件的最大长度(字节为单位),默认为2MB
			* allowedTypes:上传文件的类型,多个类型之间用","逗号分隔
			* allowedExtensions:运行上传文件扩展名,多个扩展名之间用","逗号分隔
			* 比较麻烦,算了下面详解
	4,struts.multipart.saveDir			'设置临时目录'
		* 默认为空,没有设置!

	5,多文件上传
		* 表单页面中提供多个file字段!如果,它们的name属性都一样!那么Action中的命名规则不变!但是,所有属性变成集合.自己挨个取吧
			* private List<File> xxx;//与表单文件项的name属性必须一致.该文件就是上传文件
			* private List<String> xxxContentType;//上传文件的类型
			* private List<String> xxxFileName;//上传文件的名称

		* 表单项目fiel字段name属性名称都得一样!


————————————————————————————————
四,struts2控制某个Action文件上传|
————————————————————————————————
		['上传大小/文件类型限制,单独的对某个form/Action生效']
		> 可以控制'fileUpload'拦截器的属性来完成!
			* maximumSize:上传文件的最大长度(字节为单位),默认为2MB
			* allowedTypes:上传文件的类型,多个类型之间用","逗号分隔
			* allowedExtensions:运行上传文件扩展名,多个扩展名之间用","逗号分隔
		> 这拦截器在默认的拦截器桟内里
			<interceptor-ref name="defaultStack">
				<param name="fileUpload.allowedTypes">txt,mp3,doc</param>
				<param name="fileUpload.maximumSize">1024</param>
				<param name="fileUpload.allowedExtensions">.gif</param>
			</interceptor-ref>
			* 在需要被控制的Action内'显式声明出:引用默认的拦截器栈'
			* 在通过OGNL表达式,进行对'fieUpload属性进行赋值'
			* 那么,这个Action的上传就会被限制!如果出现问题,就返回input界面


---------------------------------------文件上传,目录打散等
		//如果头像不为null
		if(pic != null)
		{
			String uploadFile = "/upload/user";
			//从跟目录获取绝对路径
			String rootFilePath = ServletActionContext.getServletContext().getRealPath(uploadFile);
			//生成唯一ID
			String fileName = Utils.getUUID()+"_"+picFileName;
			//目录打散,根据文件名称获取哈希值
			int hashCode = fileName.hashCode();				
			//把哈希值转换为16进制
			String hex = Integer.toHexString(hashCode);	
			//截取16进制的第1,2个字符	
			String filePath = hex.charAt(0)+"/"+hex.charAt(1);	
			//第1个字符作为父目录,第二个字符作为子目录
			File dirFile = new File(rootFilePath,filePath);	
			//创建目录链
			dirFile.mkdirs();	
			//根据目录链+文件名,创建保存文件
			File saveFile = new File(dirFile,fileName);
			//保存文件到本地
			FileUtils.copyFile(pic, saveFile);
			//设置文件名称
			user.setHeadImg(uploadFile+"/"+filePath+"/"+fileName);
		}
////////////////////////////


	1,使用哈希打散
	2,调用上传文件名的hashCode();获取一个int值,
	3,再把这个int值,变为16进制!
	4,16进制显示的字符范围为:0-9 A-F
	5,使用16进制前两位作为目录链
		* 第一位为父目录
		* 第二位为子目录

	每个目录下都可以有16个目录


	缺陷:文件存到哪里?人工不好找!
