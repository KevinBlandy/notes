
# 查看所有的配置
	git config --list
		* 列出所有可配置的配置项

	core.symlinks=false
	core.autocrlf=true
		* LF 是Linux 下的换行符, 对应ASCII中转义字符\n 表示换行
		* CRLF 是windows 下的换行符, \r\n 表示回车并换行
		* 设置换行符的处理方式,可选参数值:
			true :提交时转换为LF, 检出时转换为CRLF
			false:提交时转换为LF, 检出时不转换
			input:提交检出均不转换
		
		* 一旦换行符与源仓库的不一致时, git 会认为这次修改是删除后重来的, 这样会给 code review 带来巨大的麻烦

	core.fscache=true
	color.diff=auto
	color.status=auto
	color.branch=auto
	color.interactive=true
	help.format=html
	rebase.autosquash=true
	http.sslcainfo=C:/Program Files/Git/mingw64/ssl/certs/ca-bundle.crt
	http.sslbackend=openssl
	diff.astextplain.textconv=astextplain
	filter.lfs.clean=git-lfs clean -- %f
	filter.lfs.smudge=git-lfs smudge -- %f
	filter.lfs.process=git-lfs filter-process
	filter.lfs.required=true
	credential.helper=manager

	user.email=747692844@qq.com
	user.name=KevinBlandy
		* 用户的邮件和名称
	
	core.quotepath=false
		* 设置为false, 解决在 git bash 中,中文以编码形式出现的问题

	color.ui=true
		* 交互界面的文字带颜色
	
	gui.encoding=utf-8
		* 交互页面的编码



# 设置配置
	git config --global [配置项] [配置值]

	--global 
		* 表示全局,对所有仓库生效
		* 不添加的话,仅仅对当前仓库生效
	
	