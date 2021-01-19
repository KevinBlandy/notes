-------------------------
openoffice安装			 |
-------------------------
	# 准备安装环境
		yum -y install libXext.x86_64
		yum -y groupinstall "X Window System"

	# 下载地址：http://www.openoffice.org/download/index.html
		wget https://jaist.dl.sourceforge.net/project/openofficeorg.mirror/4.1.5/binaries/zh-CN/Apache_OpenOffice_4.1.5_Linux_x86-64_install-rpm_zh-CN.tar.gz

	# 解压
		tar -zxvf Apache_OpenOffice_4.1.5_Linux_x86-64_install-rpm_zh-CN.tar.gz

	# 进入解压后的目录zh-CN/RPMS执行安装
		yum -y localinstall *.rpm

	# 进入解压后的目录zh-CN/RPMS/desktop-integration执行安装
		yum -y localinstall openoffice4.1.5-redhat-menus-4.1.5-9789.noarch.rpm

	# 安装成功后，程序会被安装到:/opt/openoffice4
	# 启动服务
		nohup soffice -headless -accept="socket,host=0.0.0.0,port=8100;urp;" -nofirststartwizard &

	# 查看服务是否启动:netstat -anop | grep 8100
		tcp        0      0 127.0.0.1:8100          0.0.0.0:*               LISTEN      1115/soffice.bin     off (0.00/0/0)

	#杀死进程:ps -ef | grep openoffice
		kill -9 [pid]