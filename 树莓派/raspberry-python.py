------------------------
Python3的安装			|
------------------------
	# 依赖安装
		apt-get update
		apt-get upgrade
		apt-get dist-upgrade
		apt-get install build-essential python-dev python-setuptools python-pip python-smbus -y
		apt-get install build-essential libncursesw5-dev libgdbm-dev libc6-dev -y
		apt-get install zlib1g-dev libsqlite3-dev tk-dev -y
		apt-get install libssl-dev openssl -y
		apt-get install libffi-dev -y
	
	# 安装
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
	
	# 如果执行pip3安装依赖出现问题
		"return Command 'lsb_release -a' returned non-zero exit status 1..."
			* 删除lsb_release文件可解决此问题
			* rm /usr/bin/lsb_release
		

