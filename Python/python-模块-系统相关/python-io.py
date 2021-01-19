----------------------------
io							|
----------------------------
	* 流核心工具

----------------------------
io-模块属性					|
----------------------------

----------------------------
io-模块函数					|
----------------------------

----------------------------
io-StringIO					|
----------------------------
	* 数据读写不一定是文件,也可以在内存中读写,有点像是Java的 StringBuilder
	* StringIO顾名思义就是在内存中读写str,它的操作跟 open() 差不多
	* 构造函数
		f = StringIO()
		
		f = StringIO("初始化数据 \n 哈哈")

	* 实例函数
		int write(str)
			* 写入数据,可以多次写入
		
		None writelines(seri)
			* 写入一个数组的数据

		str getvalue()
			* 获取写入的数据
		
		str readline()
			* 读取一行
	
	* 可以 while 循环读取
		while True:
			s = f.readline()
			if s == '':
				break
			print(s.strip())

----------------------------
io-BytesIO					|
----------------------------
	* StringIO操作的只能是str,如果要操作二进制数据,就需要使用BytesIO
	* BytesIO实现了在内存中读写bytes
	* 构造函数
		f = BytesIO()
		f = BytesIO('余文朋'.encode(encoding='utf_8', errors='strict'))
	
	* 实例函数
		int write('中文'.encode('utf-8'))
			* 写入二进制数据

		bytes getvalue()
			* 读取二进制数据
