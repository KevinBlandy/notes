----------------
Git
----------------
	# 因为不同平台换行符导致的问题
		### 针对仓库进行设置

		#### 1，添加文件: .gitattributes

			*.js -text # 对JS文件进行行结尾的处理

		#### 2，重新添加所有更改的文件并规范行尾

			git add --renormalize .
	
	# 代理设置
		* http或https代理
			git config --global http.proxy http://127.0.0.1:1080
			git config --global https.proxy https://127.0.0.1:1080

		 * socks 代理
		 	git config http.proxy 'socks5://127.0.0.1:1080'
		
		* 取消代理
			git config --global --unset http.proxy
			git config --global --unset https.proxy

	# Filename too long 问题
		
		* 一般在windows下容易出现，执行如下命令就可以了

		git config --system core.longpaths true
	
	
	# 不同系统下换行符的问题
		
		* 处理 Git 中的 "LF will be replaced by CRLF" 警告
		
		* 在根目录创建 .gitattributes 文件，写入：
			
			* text=auto # Git 将以其认为的最佳方式处理文件。 这是一个合适的默认选项。
			
		