-----------------------
Redis-安装				|
-----------------------
	Linux(CentOS6.4)
		* 把文件包通过FTP上传至Linux服务器,保存在某个目录下
		* tar zxvf redis-x.x.xx.tar.gz	//解压完成后会生成一个文件夹目录、
		* cd redis-x.x.xx				//进入解压后的文件夹目录
			* 设置安装目录 ./configure --prefix=/usr/local/redis
		* make							//执行执行编译指令就OK
		* cd src						//进入src目录
			* redis-check-aof			
			* redis-check-dump
			* redis-cli					//终端脚本
			* redis-sentinel
			* redis-server				//启动redis服务的脚本
		* mkdir /usr/local/redis		//创建reids运行目录
		* cp redis-cli redis-server /usr/local/redis	//拷贝文件到运行目录
		* cd ..							//退回到解压文件目录
			* redis.conf				//配置文件
		* cp redis.conf /usr/local/redis//拷贝配置文件到redis的运行目录
		* cd /usr/loacl/redis			//进入redis运行目录
		* ./redis-server				//启动服务,前台方式
		//后台启动redis服务
		* vim redis.conf				//打开配置文件
			* daemonize no				//17行代码大约,no:代表前台启动,yes代表后台启动
		* ./redis-server redis.conf		//重新载入配置文件,其实后台已经启动了redis服务
		* ps -A | grep redis			//查看redis进程是否启动
		//登录redis服务
		* ./redis-cli
		//登录远程的redis服务器
		./redis-cli -h localhost -p 6379 -a password
			* ip默认为本地
			* 端口默认是6379
	Windows
		* 直接解压就OK
		* 在解压目录手动的创建配置文件:redis.conf
		* 开启服务
			* 直接双击redis-server.exe 启动服务
			* 加载配置文件启动服务:redis-server.exe redis.conf
		* 双击redis-cli 启动客户端

-----------------------
Redis-4.0.x安装			|
-----------------------
	1,下载源码解压
	2,安装依赖
		yum install -y gcc gcc-c++

	3,进入源码目录执行编译安装
		make PREFIX=/usr/local/redis install
			
		PREFIX,指定安装目录
	
	4,复制配置文件到安装目录
		cp *.conf /usr/local/redis/conf/

