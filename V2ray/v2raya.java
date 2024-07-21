----------------------
一个 Web 客户客户端
----------------------
	# 可以用来设置服务器的代理
		https://v2raya.org/

	# 推荐 Docker 形式安装
		docker run -d \
		  --restart=always \
		  --privileged \
		  --network=host \
		  --name v2raya \
		  -e V2RAYA_LOG_FILE=/tmp/v2raya.log \
		  -e V2RAYA_V2RAY_BIN=/usr/local/bin/v2ray \
		  -e V2RAYA_NFTABLES_SUPPORT=off \
		  -e IPTABLES_MODE=legacy \
		  -v /lib/modules:/lib/modules:ro \
		  -v /etc/resolv.conf:/etc/resolv.conf \
		  -v /etc/v2raya:/etc/v2raya \
		  mzz2017/v2raya:$Latest_version

	# 防火墙
		* 记得开放 2017 端口（入）
		* 试用期间，最好直接关闭防火墙，因为流量出去，也可能会被防火墙拦截

	# 配置
		* 使用浏览器访问 host:2017
		* 第一次初始化要设置用户名和密码
		* 然后，添加节点，启动
	
	# 设置
		* 可以修改系统代理模式
	
