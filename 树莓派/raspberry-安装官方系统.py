

# 官方下载
	https://www.raspberrypi.org/downloads/raspbian/
		Raspbian Stretch Lite
		Minimal image based on Debian Stretch
		- 不带桌面环境的系统

# 正常格式化,烧录TF卡

# 新系统ssh登录失败
	* 新系统,禁止了ssh服务导致没法登录
	* 用电脑打开tf卡,在根目录新建一个空文件:ssh
	* 插入机器,启动,ssh可以连接

# 默认账户名密码
	pi
	raspberry

# 开启root账号
	sudo passwd root
		* 修改root的密码,重复输入两次

	sudo passwd --unlock root
		* 允许root用户登录
	
	su root
		* 切换root用户
	
	apt-get install -y vim
		* 先安装,vim(也可以使用nano命令,但是我不熟悉)
	
	vim /etc/ssh/sshd_config
		* PermitRootLogin without-password (旧)
		* PermitRootLogin yes(新)

	reboot
		* 重启,可以使用root身份登录了
	
# 扩展可用空间
	sudo raspi-config
		Advanced Options
			Expand Filesystem
	
	df -lh

# 更改时区
	sudo raspi-config
		Localisaion Options
			Change Local
			Change Timezone

	timedatectl 
	Time zone: Asia/Shanghai (CST, +0800)

# 设置wifi
	Localisaion Options
	Change Wi-fi Country
	CN China

# 更换国内镜像源
	cp /etc/apt/sources.list /etc/apt/sources.list.bak
		* 备份

	vim /etc/apt/sources.list
		* 注释所有,添加如下两行
			deb http://mirrors.aliyun.com/raspbian/raspbian/ jessie main non-free contrib rpi
			deb-src http://mirrors.aliyun.com/raspbian/raspbian/ jessie main non-free contrib rpi



# 更新源,升级已经安装的包,升级系统
	apt-get update
	apt-get upgrade
	apt-get dist-upgrade