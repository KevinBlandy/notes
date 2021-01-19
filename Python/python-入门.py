----------------------------
Python-征途					|
----------------------------
	# 学习地址
		http://www.liaoxuefeng.com/wiki/0014316089557264a6b348958f449949df42a6d3a2e542c000
		http://www.runoob.com/python3/python3-tutorial.html
		http://www.w3cschool.cn/python3/python3-tutorial.html
		http://www.yiibai.com/python3/
		http://code.ziqiangxuetang.com/python3/python3-tutorial.html

	# URLS
		https://www.python.org/
			* 官网
		
		https://docs.python.org/3/
			* Python3 文档

		https://www.python.org/ftp/python
			* 下载地址

	# 书籍
		《深入Python3》
		《Python3程序开发指南》

----------------------------
1,Python-环境搭建(Windows)	|
----------------------------
	1,官网下载最新的版本
		* https://www.python.org/
	2,一路无脑安装
		* 唯一注意的是,添加到环境变量(不然得手动的添加)
	3,安装OK后,打开CMD,输入:python –V
		* 成功看到版本信息则安装成功

----------------------------
1,Cetntos环境搭建			|
----------------------------
	1,下载
		https://www.python.org/ftp/python/

	2,解压,创建文件夹
		tar -zxvf Python-3.7.1.tgz
		mkdir /usr/local/python

	3,安装依赖
		yum -y install zlib
		yum -y install zlib-devel
		yum install -y libffi-devel
		yum install -y openssl-devel

	4,进入解压目录,执行编译
		 ./configure --prefix=/usr/local/python
	
	5,编译ok后,执行安装
		make && make install

	4,创建软连接
		ln -s /usr/local/python/bin/python3 /usr/bin/python3
		ln -s /usr/local/python/bin/pip3 /usr/bin/pip3
	
	5,测试
		python3 -V
	
		

----------------------------
2,Python-处理文件编码		|
----------------------------
	> 在顶部添加指定文件编码即可
	> Python3默认的编码为 UTF-8
	> 代码          
		# -*- coding: UTF-8 -*-

----------------------------
3,Python-注释				|
----------------------------
	1,使用 '#' 号开头的单行注释 
		# 单行注释
	2,使用三个 '' 的多行注释
		'''
			多行注释1
		'''
	3,使用三个 "" 的多个注释
		"""
			多行注释2
		"""


----------------------------
4,变量						|
----------------------------
	# 合法的变量声明
		x = 5;
		x = "你好";
		x,y = 5,"你好";
		x,y = [5,"你好"];
		x,y = (4,"哈哈");
	
	# 删除变量
		* 手动的执行
			x = 5
			del x	//GC会在某个时间删除内存中的 5

		* 重新赋值
			x = 5		
			x = 15	//GC会在某个时间删除内存中的 5 
		
		* 指向 None
			x = 5
			x = None	//GC会在某个时间删除内存中的 5
	
	# 常量
		* Python 其实在语法上没有常量这个概念
		* 全靠自觉
		* '变量名称全部大写,就是常量.语法上允许修改,约定上不能'
	
----------------------------
5,pyc						|
----------------------------	
	* pyc,其实就是python编译器的编译结果
	* 编译结果会首先保存在内存中(PyCodeObject)
	* 程序结束后,会把PyCodeObject写入pyc文件
	* 第二次运行,会直接在硬盘上找 pyc 文件
	* 如果找到并且修改时间等于python源码的时间,直接载入,否则重新执行编译等操作



	
----------------------------
6,交互式					|
----------------------------
	1,进入Python2
		python
	
	2,进入Python3
		python3

	3,进入带有命令的交互式环境
		ipython2
		ipython3
	

	





