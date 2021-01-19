------------------
URL					|
------------------
	# 静态方法
		createObjectURL()
			* 参数是二进制图片信息/图片对象,返回的是图片的BASE64编码
		
		revokeObjectURL(objectURL)
			* 撤消之前使用URL.createObjectURL()创建的URL对象
			* 其中参数objectURL表示之前使用URL.createObjectURL()创建的URL返回值

	# 实例属性
		hash
			* URL地址中的锚链值，包含字符串'#'

		host
			* URL地址中host主机地址，包括协议端口号

		hostname
			* URL地址中主机名称，不包括端口号

		href
			* 完整的URL地址

		origin [只读]
			* 返回URL地址的来源，会包含URL协议，域名和端口

		password
			* 返回URL地址域名前的密码。ftp协议中比较常见

		username
			* 返回URL地址域名前的用户名。ftp协议中比较常见。

		pathname
			* 返回URL中的目录+文件名。例如这里ftp.pathname的返回值是'/path/file'。

		port
			* 返回URL地址中的端口号。

		protocol
			* 返回URL地址的协议，包括后面的冒号':'

		search
			* 返回URL地址的查询字符串，如果有参数，则返回值以问号'?'开头

		searchParams
			* 返回一个URLSearchParams对象，可以调用URLSearchParams对象各种方法，

	# 实例方法
		toString()
			* 返回的完整的URL地址，你可以理解为URL.href的另外一种形式，不过这个只能输出，不能修改值。

		toJSON()
			* 同样返回完整的URL地址，返回的字符串和href属性一样。
		静态方法
		URL.createObjectURL(object)
		可以把File，Blob或者MediaSource对象变成一个一个唯一的blob URL。其中参数object可以是File，Blob或者MediaSource对象。
		

------------------
URL	- Demo			|
------------------
	# WEB显示本地图片
		var url = window.URL.createObjectURL(files[0]);		//在内存中根据文本创建了一个二进制对象
		$('#img').attr({'src':url});						//直接把这个二进制对象显示到img,要注意在显示之后,释放掉内存
		window.URL.revokeObjectURL(url);					//释放内存
	