-------------------------------
模块							|
-------------------------------
	* 在Python中，一个 .py 文件就称之为一个模块(Module).

	* 使用模块可以避免函数名和变量名冲突,相同名字的函数和变量完全可以分别存在不同的模块中(但是尽量别和内置函数重名)
	
	* 导入的几种方式
		import [模块名]
		import [模块1],[模块2]...[模块n]
		import [模块名] as [别名]
		import [模块名] as [别名],[模块名] as [别名],[模块名] as [别名]

		
		from [包...模块] import [方法/属性名]
		from [包...模块] import [方法/属性名]...[方法/属性名]
		from [包...模块] import [方法/属性名] as [别名]
		from [包...模块] import [方法/属性名] as [别名],[方法/属性名] as [别名]

		from [包...模块] import *
			* 导入指定模块中的所有非私有属性/方法
			* 如果模块中,存在一个叫做 __all__ 的列表变量,那么在使用 from [包...模块] import * 的时候就只会导入该[]中指定(字符串形式)的变量,如果变量不存在,则抛出异常

		* 当试图加载一个模块时,Python会在指定的路径下依次搜索对应的.py文件,直到找到第一个,如果找不到,就会报错
		* 当被加载的模块中 import 了其他模块,会优先加载其他模块
		* 当加载(import)进来一个模块,会执行该模块的代码,哪怕仅仅是引入了该模块中的任意一个函数/属性
		* 当出现重名的函数 & 属性时,当前py的函数 & 属性会进行覆盖操作

	* 默认情况下,Python解释器会按照以下路径进行搜索
		1,内存中已经加载的模块
		2,当前目录下寻找
		3,所有已安装的内置模块
		4,第三方模块
	
	* 搜索路径存放在sys模块的path变量中
		from sys import path
		for item in path:
			print(item)
		D:\project\python-demo
		D:\project\python-demo
		C:\Users\KevinBlandy\AppData\Local\Programs\Python\Python36\DLLs
		C:\Users\KevinBlandy\AppData\Local\Programs\Python\Python36\lib
		C:\Users\KevinBlandy\AppData\Local\Programs\Python\Python36
		C:\Users\KevinBlandy\AppData\Local\Programs\Python\Python36\lib\site-packages
		C:\Users\KevinBlandy\AppData\Local\Programs\Python\Python36\python36.zip
	
	* 要添加自己的搜索目录,有两种方法
		1,直接修改 sys.path 变量
			sys.path.append('/Users/kevin/my_py_scripts')
		
			* 这种方法是在运行时修改，运行结束后失效
		
		2,第二种方法是设置环境变量 PYTHONPATH 该环境变量的内容会被自动添加到模块搜索路径中。
			* 设置方式与设置Path环境变量类似,注意只需要添加你自己的搜索路径,Python自己本身的搜索路径不受影响



	* 运行模块时候,解释器动态添加了一个属性
		* 当我们在命令行运行xxx模块文件时,Python解释器把一个特殊变量 __name__ 置为 '__main__',赋值给当前模块
		* 而如果在其他地方导入该xxx模块的时候,该属性值默认为该模块的名称
			import sys
			def test():
				if (len(sys.argv) == 1):
					print("%s"%(sys.argv))
				elif (len(sys.argv > 1)):
					for x in sys.argv:
						print("参数:%s"%(x))

			if (__name__ == '__main__'):
				# 如果是直接运行的该模块, test() 函数就会执行
				test()
    
		* sys模块有一个 argv 变量,用list存储了命令行的所有参数
		* argv至少有一个元素，因为第一个参数永远是该.py文件的名称
	
	* 每个模块都有一个内置的属性:__file__ 该值为'当前脚本的名称(相对路径)'
	* 需要注意有些IDE脑壳有包,会自己'帮你把这个值改为绝对路径'
		import os
		print("绝对路径",os.path.abspath(__file__))	# 绝对路径 E:\workspace\kevinblandy-python-demo\python\Main.py
		print("相对路径",__file__)					# 相对路径 Main.py

		
	
	* 一个模块被多次加载(包括传递性多次加载),该模块仅仅只会被加载一次
	* Python以字节码编译的形式把相应的模块加载到内存

	* 安装第三方模块
		* 在Python中，安装第三方模块，是通过包管理工具pip完成的。
			pip 是python自带的

		* 所有的第三方模块都会在官网进行注册
			https://pypi.python.org/

		* 安装第三方模块
			pip install [模块名称]

-------------------------------
包								|
-------------------------------
	
	* 为了避免模块名冲突,Python又引入了按目录来组织模块的方法,称为包(Package)

	* 每一个包目录下面都会有一个 __init__.py 的文件,这个文件是必须存在的,否则,Python就把这个目录当成普通目录,而不是一个包

	* __init__.py 可以是空文件,也可以有Python代码，因为 __init__.py 本身就是一个模块

	* 当执行 import [包名],的时候,会执行该包下的 __init__.py 文件,import 多个层级包的时候,依次执行,多次加载,仅仅执行一次

	* 导包可以使用相对路径: ./.. 来进行导入操作

	* 导入 __init__.py 文件中的函数,属性
		import xxx.xx as [别名]
			* 如果不指定别名,则需要使用全路径来操作变量,函数:xx.xxx.xx.xx.[函数/属性]

		from xx.xx import [属性/函数]

	* 可以有多级目录，组成多级层次的包结构
		mypackage
			|-__init__.py
			|-Model1.py
			|-Model2.py
			|-secendpackage
				|-__init__.py
				|-Model1.py
				|-Model2.py
	
	* 导入包中的模块

		import xx.xxx.xx.xx.[模块名] as [别名],xx.xxx.xx.xx.[模块名] as [别名] ... xx.xxx.xx.xx.[模块名] as [别名]
			* 这种方式需要使用别名来操作模块中变量,函数
			* 如果不指定,则需要使用全路径来操作变量,函数:xx.xxx.xx.xx.[模块名].[函数/属性]

		from xx.xxx.xx.xx import [模块名]
			* 这种方式,直接使用模块名称来操作模块中的变量,函数
			* 也可以通过 as [别名] 的方式解决模块名称冲突的问题
		
		from xx.xxx.xx.xx.[模块名] import [函数/属性]
			* 也可以直接引入指定模块中的属性,函数
			* 也可以使用别名来处理冲突问题

		from xx.xxx.xx.xx import *
			* 默认导入指定包下的所有模块
			* 如果包定义文件 __init__.py 存在一个叫做 __all__ 的列表变量,那么在使用 from package import * 的时候就只会导入该[]中指定(字符串形式)的模块,如果模块不存在,则抛出异常


------------------------
建议包结构				|
------------------------
	app
	|-bin				
	|-app				
	|-docs				
	|-setup.py			
	|-requirements.txt	
	|-README

	bin
		* 存放可执行文件

	app
		* 存放项目源码

	docs
		* 存放文档

	setup.py
		* 安装部署脚本

	requirements.txt	
		* 三方依赖列表
	
	README
		* 一个关于项目的描述,作用,说明,目录结构说明,启动方式...等等信息