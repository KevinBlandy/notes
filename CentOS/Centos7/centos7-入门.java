------------------------
CentOS7					|
------------------------
	# 跟 CentOS6 的改变

				[CentOS6]				[CentOS7]
	--------------------------------------------------------------
	防火墙		iptables				firewalld
	--------------------------------------------------------------
	默认数据库	MySQL					MariaDB
	--------------------------------------------------------------
	主机名		/etc/sysconfig/network	/etc/hostname
	--------------------------------------------------------------
				service xx start		systemctl start xx
				service xx stop			systemctl stop xx
	服务相关	service xx restart		systemctl restart xx
				service xx reload		systemctl reload xx
				service xx status		systemctl status xx
	--------------------------------------------------------------
	自启动服务	chkconfig xx on/off		systemctl enable/disable xx
	--------------------------------------------------------------
	服务一览	chkconfig --list		systemctl list-unit-files systemctl --type service
	--------------------------------------------------------------
	强制停止	kill -9 [pid]			systemctl kill --signal=9 [服务名]		
	...

------------------------
CentOS7-配置文件地址	|
------------------------
	 /etc/hostname
		# 主机名配置文件
	
	/etc/systemd/system
		# systemctl命令操作服务的脚本目录















privateKey:MIIBVQIBADANBgkqhkiG9w0BAQEFAASCAT8wggE7AgEAAkEAoqTS84al2hLtM52HI6VcVuD/iGjtMpW3XYSm5DGv68k1d9uqSO2A7CHONIpahNVZzphnw8X1Y3HY1gYsk9Z7RwIDAQABAkEAhL8mdiEl6NGdzIz8Kbnjnf1SUxHg13KfELMeTA3J2hU2IcteAtZ3rJKDGUJA7M4cRkxLujKuyfKXbnJpIkEjwQIhAOW7znwSIlEzxyBphMdZy72wHwCQZTwfOTorHMtkNCtRAiEAtT1U7DcFaONUwWW5w8JuWUbRJgEVzzY4cN7qm1npZxcCIQCanOIj+GRmOC2876cwL4wCluTWkVfAtdbjAh739/FwcQIgDngG/5Sk6eq8KXRlkCtFg/sTV2VeyFwbxWcGc8CJ0aECIEivUK5Ap2gbhgBDst7qFKhURvRjk4t/DiWBjH/NggLd
publicKey:MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAKKk0vOGpdoS7TOdhyOlXFbg/4ho7TKVt12EpuQxr+vJNXfbqkjtgOwhzjSKWoTVWc6YZ8PF9WNx2NYGLJPWe0cCAwEAAQ==
password:NuOqE/ylY48zVtzp4gcHlo6e4fWIKd2hzP6cHsfQh34RC67gQsfJ6cVSFFWet2CDEYhmJowqH4kDXY8Umlzt9g==
	
	