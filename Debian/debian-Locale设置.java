---------------------
Locale 设置
---------------------

apt-get update
apt-get install -y locales
locale-gen en_US.UTF-8

dpkg-reconfigure locales
	* 选择 en_US.UTF-8 UTF-8
	* 回车确认，系统会询问默认语言环境
	* 再选 en_US.UTF-8

update-locale LANG=en_US.UTF-8 LC_ALL=en_US.UTF-8
source /etc/default/locale
