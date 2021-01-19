
# 参考教程
	https://baijiahao.baidu.com/s?id=1591503879926889654&wfr=spider&for=pc
	

# 下载Centos7
	http://isoredirect.centos.org/altarch/7/isos/armhfp/
	* 可以选择华为的镜像
		https://mirrors.huaweicloud.com/centos-altarch/7.5.1804/isos/armhfp/

		CentOS-Userland-7-armv7hl-RaspberryPI-Minimal-1804-sda.raw.xz
			* 基本的核心(服务器)
		CentOS-Userland-7-armv7hl-RaspberryPI-GNOME-1804-sda.raw.xz
			* GNOME桌面环境
		CentOS-Userland-7-armv7hl-RaspberryPI-KDE-1804-sda.raw.xz
			* KDE 桌面环境

# 下载 SD Memory Card Formatter 用于格式化SD卡
	* 格式化SD卡

# 下载 win32diskimager-1.0.0-install 用于烧录镜像
	* 烧录镜像到已经格式化的SD卡

# 插入SDK卡

# 链接网线,启动
	* 红灯常亮: 未能检测到TF卡
	* 双灯常亮: 未能检测到系统
	* 红灯常亮,绿灯闪烁: 系统运行正常

# 通过路由器查看地址

# 通过ssh链接
	* 初始root密码:centos

# 设置时区
	* 查看
		timedatectl 
		Time zone: Asia/Shanghai (CST, +0800)
	
	* 修改时区为上海
		timedatectl set-timezone Asia/Shanghai

# 扩展空间
	* 树莓派装完系统后,默认认没有把整个存储空间拓展到整张TF卡中,所以SD卡空间利用不充分
	* 在root用户家目录有个 README 文件,里面提供命令的方式自动扩展空间
		/user/bin/rootfs-expand
	

# 安装基本的软件
	yum -y install vim
	yum -y install git
	yum -y install gcc automake autoconf libtool make
	yum -y install wget