-------------------------------
gzip							|
-------------------------------
	* 对gzip压缩的支持

-------------------------------
gzip-属性						|
-------------------------------
-------------------------------
gzip-方法						|
-------------------------------
	gzip.GzipFile GzipFile(fileobj)
		* 传递指定的gzip压缩的字节数据
		* 返回压缩对象

-------------------------------
gzip.GzipFile					|
-------------------------------
	* gzip压缩对象
	* 创建
		GzipFile(fileobj)
			fileobj	
				* 可以是 open() 的zip文件
				* 也可以是 BytesIO 对象(内存字节缓冲区)
	* 实例API
		read()
			* 解压数据

	* demo解压HTTP响应数据
		with request.urlopen() as response:
			buff = BytesIO(response.read()) 
			f = gzip.GzipFile(fileobj=buff)
			response_str = f.read().decode(encoding='utf_8', errors='strict')
