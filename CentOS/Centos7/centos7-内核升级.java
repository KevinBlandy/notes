---------------------------
内核升级
---------------------------
	# 查看系统内核
		$ uname -rs
		Linux 3.10.0-1160.45.1.el7.x86_64
	
	# 载入公钥并安装 ELRepo 最新版本：
		## 载入公钥
		$ rpm --import https://www.elrepo.org/RPM-GPG-KEY-elrepo.org

		## 安装 ELRepo 最新版本
		$ yum install -y https://www.elrepo.org/elrepo-release-7.el7.elrepo.noarch.rpm
	
	# 安装新的内核版本
		
		* 查询出可以使用的 kernel 包版本（lt表示长期维护版，ml表示最新稳定版）
			$ yum list available --disablerepo=* --enablerepo=elrepo-kernel
		
		* 安装指定的 kernel 版本
			$ yum install -y kernel-lt-5.4.235-1.el7.elrepo --enablerepo=elrepo-kernel
		
	
	# 设置开启系统启动时使用的内核版本
		## 查看系统可用内核
		$ cat /boot/grub2/grub.cfg | grep menuentry

		## 设置开机从新内核启动
		$ grub2-set-default "CentOS Linux (5.4.235-1.el7.elrepo.x86_64) 7 (Core)"

		## 查看内核启动项
		$ grub2-editenv list
		saved_entry=CentOS Linux (5.4.235-1.el7.elrepo.x86_64) 7 (Core)
				
	
	# 重启系统并观察内核版本
		## 重启系统
		$ reboot

		## 启动完成查看内核版本是否更新：
		$ uname -rs
		Linux 5.4.235-1.el7.elrepo.x86_64
