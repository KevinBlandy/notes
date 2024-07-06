------------------------------------
FileReader extends EventTarget
------------------------------------
	# 允许 Web 应用程序异步读取存储在用户计算机上的文件（或原始数据缓冲区）的内容，使用 File 或 Blob 对象指定要读取的文件或数据。
	
	# 构造函数
		new FileReader()

------------------
this
------------------

	error
	readyState
	result
		* 文件的内容。该属性仅在读取操作完成后才有效，数据的格式取决于使用哪个方法来启动读取操作。

	abort()
		* 
		
	readAsArrayBuffer(blob)
		* 读取文件并将文件内容以 ArrayBuffer 形式保存在 result 属性。

	readAsBinaryString()
		* 已弃用:

	readAsDataURL(blob)
		* 读取文件并将内容的数据 URI 保存在 result 属性（data:URL）中。

	readAsText(blob, encoding)
		* 从文件中读取纯文本内容并保存在 result 属性中。第二个参数表示编码，是可选的。


------------------
static
------------------
