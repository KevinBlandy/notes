-----------------------
Linux 安装依赖			|
-----------------------
	# 安装gcc和依赖包
		yum install gcc* python-devel libffi-dev* -y 
	
	# 安装 librtmp 
		git clone git://git.ffmpeg.org/rtmpdump
		cd rtmpdump/librtmp/
		make && make install
	
	# 安装 setuptools
		* 在:https://pypi.org/ 搜索:setuptools
		* 在下载页面,下载 zip 包
			wget -S https://files.pythonhosted.org/packages/b0/d1/8acb42f391cba52e35b131e442e80deffbb8d0676b93261d761b1f0ef8fb/setuptools-40.6.2.zip
		* 解压 & 安装
			unzip setuptools-40.6.2.zip
			cd setuptools-40.6.2
			python setup.py install
		* 直接 pip 安装也行
	
	# 安装 cffi
		* 在:https://pypi.org/ 搜索:cffi
		* 在下载页面,下载 tar.gz 包
			wget -S https://files.pythonhosted.org/packages/e7/a7/4cd50e57cc6f436f1cc3a7e8fa700ff9b8b4d471620629074913e3735fb2/cffi-1.11.5.tar.gz
		* 解压 & 安装
			tar -zxvf cffi-1.11.5.tar.gz
			cd cffi-1.11.5
			python setup.py install
		* 直接pip安装也行
	
	# 安装 librtmp
		pip install python-librtmp
		
	# 尝试 import librtmp
	
	# 处理异常:ImportError: librtmp.so.1: cannot open shared object file: No such file or directory
		* 查找到librtmp.so.1路径,复制到lib64目录下即可
		* 编译安装 librtmp 的时候,日志就有 librtmp.so.1 的路径信息

		find / -name librtmp.so.1
		cp /usr/local/lib/librtmp.so.1 /usr/lib64/

-----------------------
windows 安装依赖		|
-----------------------
	# 执行
		pip install python-librtmp
	
	# 安装异常
		error: Microsoft Visual C++ 14.0 is required. Get it with "Microsoft Visual C++ Build Tools": https://visualstudio.microsoft.com/downloads/

		* 先在本机安装: visualcppbuildtools_full.exe

	
	# 编译失败:
		Can not open include file: 'librtmp/rtmp.h': No such file or directory
