
yum update -y

cat /etc/redhat-release
	* release数值大于7.3即可

wget --no-check-certificate https://github.com/teddysun/across/raw/master/bbr.sh && chmod +x bbr.sh && ./bbr.sh
	* 安装完成后, 输入y, 立即重新启动

egrep ^menuentry /etc/grub2.cfg | cut -f 2 -d \'
grub2-set-default 0
reboot
	* 以上三步, 脚本已经执行了, 没必要再次执行

uname -r
	* 查看内核是否已更换为4.9 +

echo "net.core.default_qdisc = fq" >> /etc/sysctl.conf
echo "net.ipv4.tcp_congestion_control = bbr" >> /etc/sysctl.conf
sysctl -p 
	* 以上三步, 脚本已经执行了, 没必要再次执行

sysctl net.ipv4.tcp_available_congestion_control
	* 验证当前TCP控制算法
	* 返回值一般是(或者关系)
		net.ipv4.tcp_available_congestion_control = bbr cubic reno 
		net.ipv4.tcp_available_congestion_control = reno cubic bbr
	
sysctl net.ipv4.tcp_congestion_control
	* 验证bbr是否启动
	* 返回值一般为
		net.ipv4.tcp_congestion_control = bbr
	
lsmod | grep bbr
	* 返回值有 tcp_bbr 模块即说明 bbr 已启动
		tcp_bbr                20480  9 
	




