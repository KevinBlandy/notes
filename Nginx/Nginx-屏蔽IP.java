--------------------
Nginx-屏蔽指定IP	|
--------------------
	1.在nginx的安装目录下面,新建屏蔽ip文件
		* 命名为 blockip.conf 以后新增加屏蔽ip只需编辑这个文件即可。
	2,添加内容
		* deny 165.91.122.67;
	3,在nginx的配置文件nginx.conf中加入如下配置，
		include blockip.conf; 
		可以放到http, server, location, limit_except语句块
		需要注意'相对路径(nginx.conf和本文件)'，本例当中nginx.conf，blocksip.conf在同一个目录中。
	4,重启服务
	
	# 这配置文件里面的配置方式名堂很大,需要的时候再去找.可以实现各种规则的配置
