
# 在客户端生成密钥对
	* 使用ssh工具生成
		ssh-keygen -t rsa
	
	* 使用Git客户端生成
		ssh-keygen -t rsa -C "747692844@qq.com"

# 修改服务配置文件
	vim /etc/ssh/sshd_config

	PermitRootLogin no
		* 是否禁用ROOT登录(非必须的)

	PubkeyAuthentication yes
		* 是否允许证书登录

	PasswordAuthentication yes
		* 是否允许使用密码进行登录

	StrictModes no	
		* 是否让 sshd 去检查用户家目录或相关档案的权限数据，
		* 这是为了担心使用者将某些重要档案的权限设错，可能会导致一些问题所致。
		* 例如使用者的 ~.ssh/ 权限设错时，某些特殊情况下会不许用户登入

	AuthorizedKeysFile .ssh/authorized_keys		
		* 指定公钥文件
		* 可以指定为用户的home目录
			%h/.ssh/authorized_keys

# 在home目录添加文件
	.ssh/authorized_keys

	* 该文件, 可以有多个公钥, 一行配置一个客户端公钥


# 重启 ssh 服务
	systemctl restart sshd.service

	* 千万记得要先把公钥添加到了服务器, 才重启

# 配置Linux客户端
	* 使用Linux上的ssh客户端，登录远程服务器

	vim /etc/ssh/ssh_config

	IdentityFile ~/.ssh/id_rsa
		* 指定本机的私钥
		* 可以有多个IdentityFile
