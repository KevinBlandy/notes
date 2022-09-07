
--------------
asciidoctor
--------------
	# Ruby开发的解析器
		https://github.com/asciidoctor
		https://github.com/asciidoctor/asciidoctor/blob/main/README-zh_CN.adoc
	

	# Windows安装
		* 安装ruby
			choco install ruby

			* 如果choco没有安装的话，可以打开powershell,先安装choco
			* 执行如下命令
				Set-ExecutionPolicy Bypass -Scope Process -Force; [System.Net.ServicePointManager]::SecurityProtocol = [System.Net.ServicePointManager]::SecurityProtocol -bor 3072; iex ((New-Object System.Net.WebClient).DownloadString('https://community.chocolatey.org/install.ps1'))
		
		* 安装 asciidoctor (打开新的CMD)
			gem install asciidoctor
	



