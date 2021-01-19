

SpringMVC对文件上传的支持

	建议,创建虚拟目录,作为图片的存储目录!甚至是单独的采取图片服务器
	目录打散非常重要.提供IO性能!可以按照日期.也可以按照哈希值前俩

	1,在IOC中注册,文件上传的支持组件
		<!-- 文件上传支持 -->
		<bean id="multipartResolver" class="org.springframework.web.multipart.commons.CommonsMultipartResolver">
			<!-- 上传文件最大字节数 (5MB)
				-1	:默认值,代表没有限制
			-->
			<property name="maxUploadSize" value="5242880"/>
			<!--
				文本文件的编码
			-->
			<property name="defaultEncoding" value="UTF-8"/>
			<!--
				最大内存,如果超过该值,会被写入临时目录
			-->
			<property name="maxInMemorySize" value="1638400"/>
			<!--
				当超过内存值以后,写入的临时目录地址,默认是相对于WEBAPP的根目录,如果目录不存在.会自动的创建目录
			-->
			<property name="uploadTempDir" value="/temp"/>
		</bean>
		* 该Bean的id值是固定的,不可修改
	
	2,在请求处理方法中提供一个形参:MultipartFile pic
		* @RequestParam("uploadFile") MultipartFile uploadFile
		* 名称必须和表单字段的名称一样
		* 该对象就是上传的文件对象,具备以下方法
			pic.getOriginalFilename();	//获取文件原始名称
			pic.getBytes();				//获取文件字节数组
			pic.getContentType();		//获取文件图片格式
			pic.getInputStream();		//获取文件读取流4
			pic.getName();				//获取文件,表单字段名称
			pic.getSize();				//获取文件大小,返回Long值
			pic.transferTo(File f);		//把该文件写入指定的File对象
	

	问题:
		1,请求参数中还带有其他非file字段属性,处理器形参提供javaBean,400错误!
		2,如果文件上传大于限定参数,跳转哪个页面?以及如果自定义错误提示.

--------------------------
实际开发中需要注意的问题 |
--------------------------
	1,文件的上传必须要在代码里面进行如下的校验
		1,文件类型
		2,文件大小
		3,校验内容
			* 是否是图片/文本文件,怕坏人改后缀,传恶意脚本,程序
--------------------------
对于校验文件的类型解决方案|
--------------------------
	[图片]
		* 图片类型的文件,都会有宽和高的属性,我们可以通过读取文件的宽高属性
		* 如果能成功读取,则是正常的图片文件,反之则是坏人
		1,先把文件写入硬盘
		2,使用	BufferedImage 去读取图片文件对象
		3,看能够成功的获取宽高属性
				boolean isImg = false;
				try {
					//使用:BufferedImage 读取图片文件对象
					BufferedImage image = ImageIO.read(file);
					if (image != null) {
						int width =	image.getWidth();
						int hright = image.getHeight();
						//成功读取宽高,未异常.文件OK,设置状态OK
						isImg = true;
					}
				} catch (IOException e) {
					//发生异常,很显然不是图片.那么执行删除文件
				}
