--------------------------------
shtool							|
--------------------------------
	# 安装
		ftp://ftp.gnu.org/gnu/shtool/shtool-2.0.8.tar.gz
		tar zxvf shtool-2.0.8.tar.gz 
		cd shtool-2.0.8 
		./configure
		make
		make test
		make install
	
	# shtool 函数的使用方法
		shtool [options] [function [options] [args]]

	# 提供的方法
		函 数		描 述
		Arx			创建归档文件（包含一些扩展功能）
		Echo		显示字符串，并提供了一些扩展构件
		fixperm		改变目录树中的文件权限
		install		安装脚本或文件
		mdate		显示文件或目录的修改时间
		mkdir		创建一个或更多目录
		Mkln		使用相对路径创建链接
		mkshadow	创建一棵阴影树
		move		带有替换功能的文件移动
		Path		处理程序路径
		platform	显示平台标识
		Prop		显示一个带有动画效果的进度条
		rotate		转置日志文件
		Scpp		共享的C预处理器
		Slo			根据库的类别，分离链接器选项
		Subst		使用sed的替换操作
		Table		以表格的形式显示由字段分隔（ field-separated）的数据
		tarball		从文件和目录中创建tar文件
		version		创建版本信息文件
		
	
	# 在自己的shell脚本中使用
		#!/bin/bash
		shtool platform # 打印出操作系统
	
