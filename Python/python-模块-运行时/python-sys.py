---------------------------
sys							|
---------------------------
	* python 系统相关的参数与函数

---------------------------
sys-内置属性				|
---------------------------
	list argv
		* 返回启动当前程序,传递的参数
		* 返回 list,第一个元素永远都是,脚本所在的绝对路径
	
	str version
		* 返回当前Python解释器的版本信息
		* 3.6.0 (v3.6.0:41df79263a11, Dec 23 2016, 07:18:10) [MSC v.1900 32 bit (Intel)]
	
	list path
		* 返回模块的搜索路径列表
		* ['E:\\workspace\\kevinblandy-python-demo', 'C:\\Users\\Kevin\\AppData\\Local\\Programs\\Python\\Python36-32\\DLLs', 'C:\\Users\\Kevin\\AppData\\Local\\Programs\\Python\\Python36-32\\lib', 'C:\\Users\\Kevin\\AppData\\Local\\Programs\\Python\\Python36-32', 'C:\\Users\\Kevin\\AppData\\Local\\Programs\\Python\\Python36-32\\lib\\site-packages', 'C:\\Users\\Kevin\\AppData\\Local\\Programs\\Python\\Python36-32\\python36.zip']
		
	str platform
		* 操作系统名称
		* win32
	
	_io.TextIOWrapper stdout
		* 标准输出流对象
	
	_io.TextIOWrapper stdin
		* 标准输入流对象
	
	_io.TextIOWrapper stderr
		* 错误输出流,它是非缓冲的
	None pycache_prefix
		* 默认为None
		* 可以指定一个路径, 让.pyc文件全部按照对应代码的结构, 放在指定的路径下面, 以此来实现代码和编译字节码的分离
		* (那将来是不是可以分发Python代码的时候可以只分发编译字节码包, 从而一定程度上提高了Python的安全性呢)

---------------------------
sys-内置函数				|
---------------------------
	None exit(num)
		* 退出程序
		* 参数 num 0 ,代表正常退出,如果该值是字符串,则会进行打印操作
	
	stdout.write()
		* 标准的输出流(屏幕)
		* 其实就是,print(),不过没有换行

	str stdin.readline()
		* 标准输入流
		* input()
	
	None setrecursionlimit(int)
		* 设置程序允许函数递归的深度

	