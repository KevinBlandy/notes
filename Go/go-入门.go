---------------------------
Go
---------------------------
	# Go
		https://golang.org/
		https://github.com/golang/go
		https://golang.org/doc/
		https://golang.google.cn/doc/
		https://blog.golang.org/

		https://docs.microsoft.com/zh-cn/learn/paths/go-first-steps/

		https://golangnote.com/
	
	# 学习参考
		https://www.liwenzhou.com/posts/Go/go_menu/
		《Go语言圣经》		https://github.com/golang-china/gopl-zh

		《Go语言编程》

		《Go语言高级编程》	https://github.com/chai2010/advanced-go-programming-book
							https://chai2010.cn/advanced-go-programming-book/

		《玩儿转GO》		https://github.com/hantmac/Mastering_Go_ZH_CN
							https://wskdsgcf.gitbook.io/mastering-go-zh-cn/

		《Go 语言设计与实现》https://draveness.me/golang/

	
	# 博客参考
		鸟窝	https://colobu.com/categories/Go/

		https://github.com/AnkoGo/Go-Library-Demo

		
							
	
	# 文档库参考
		https://studygolang.com/pkgdoc
		https://golang.org/pkg/
		https://pkg.go.dev/
		https://books.studygolang.com/The-Golang-Standard-Library-by-Example/
	
	

---------------------------
Go 安装
---------------------------
	# 需要的环境变量
		Path			bin目录
		GOROOT			go安装目录
		GOPATH			go工作目录，多个使用分号(:)分隔

	# Windows
		* 一顿下一步
	
	# Linux
		* 下载解压包
			tar -C /usr/local -xzf go1.15.3.linux-amd64.tar.gz
		
		* 编辑 /etc/profile 文件，设置PATH
			export PATH=$PATH:/usr/local/go/bin
			
		
		* 查看版本号
			go version
	


---------------------------
Go 工具
---------------------------
	# guru
		
		
	# gocode
		go get -u -v github.com/nsf/gocode
	
	#  godef

		go get -u -v github.com/rogpeppe/godef
	
	# tools
		* clone源码
			git clone https://github.com/golang/tools.git
		
		* 在go的 %GOROOT/src下创建golang.org\x目录
			D:\Go\src\golang.org\x
		
		* 并将第一步clone的tools的放到x目录下。
			D:\Go\src\golang.org\x\tools\cmd\guru

		* 进入目录，执行build

--------------------------
Go Eclipse
--------------------------
	# 地址
		https://goclipse.github.io/
	
	# 安装
		

--------------------------
布局
---------------------------
	# 布局说明
		https://github.com/golang-standards/project-layout/blob/master/README_zh-CN.md
	
	# Go目录
		project
			|-cmd
	
	# 服务端应用程序的目录
		
--------------------------
Go Todo
--------------------------
	# 邮件
	# RSA
	# 模板引擎/text/html(https://colobu.com/2019/11/05/Golang-Templates-Cheatsheet/)
	# 正则
	# SQL(http://go-database-sql.org/)
	# XML/HTML解析
	# Redis
	# 反射
	# math