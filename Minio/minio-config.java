---------------------
配置
---------------------
	# 默认的配置目录是:${HOME}/.minio
		* 二进制启动, 可以使用 --config-dir 命令行选项重写
		* docker 通过: -v /mnt/config:/root/.minio 指定宿主机的目录映射
	


---------------------
配置
---------------------

---------------------
证书的配置
---------------------
	# 证书的配置目录
		${HOME}/.minio
		├── certs
		│   ├── CAs
		│   ├── private.key
		│   └── public.crt
		└── config.json
	
		
		* 在配置目录下有一个 certs 目录, 用于存放证书
		* private.key 存储私钥, public.crt 存储公钥, 名称不能修改
			[root@localhost]# cp ./fullchain.pem /mnt/config/certs/public.crt
			[root@localhost]# cp ./privkey.pem /mnt/config/certs/private.key