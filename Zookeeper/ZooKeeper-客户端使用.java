--------------------------------
zkCli.sh						|
--------------------------------
	# 连接
		-server [ip]:[port]

		* ip默认为localhost
		* port默认为2181
	
	# 命名
		help
			* 帮助信息
				ZooKeeper -server host:port cmd args
				stat path [watch]
				set path data [version]
				ls path [watch]
				delquota [-n|-b] path
				ls2 path [watch]
				setAcl path acl
				setquota -n|-b val path
				history 
				redo cmdno
				printwatches on|off
				delete path [version]
				sync path
				listquota path
				rmr path
				get path [watch]
				create [-s] [-e] path data acl
				addauth scheme auth
				quit 
				getAcl path
				close 
				connect host:port

--------------------------------
基本的节点操作					|
--------------------------------
	# 节点的CRUD
		create [-s] [-e] [-c] [-t ttl] path data acl
			* 创建新的节点
				-s 表示顺序节点
				-e 表示临时节点 
				-c 表示容器节点
				-t 表示ttl节点, ttl 表示ttl值
				path Znode
				data 关联数据
				acl 权限控制

			* 创建多级目录的时候,父级目录必须存在
			* 如果数据有特殊符号(空格),那么可以用双引号包裹整个数据项
			* 必须设置data值,如果不设置的话,节点会创建失败
		
		ls path
			* 查看指定目录下的所有子节点(只能查看一级)
				- 查看根节点: ls /
			* 参数
				-s 不仅仅列出子节点, 还显示当前父节点的信息
				
				
		ls2 path
			* 查看指定目录下的所有子节点(只能查看一级)
			* 被标识为过期,不推荐使用了, 建议使用: ls -s [node]
			* 它不仅会列出子节点,还会展示根节点的信息
				[子节点1, 子节点n]
				cZxid = 0x200000009
				ctime = Thu Jan 03 09:32:01 CST 2019
				mZxid = 0x200000009
				mtime = Thu Jan 03 09:32:01 CST 2019
				pZxid = 0x20000000e
				cversion = 2
				dataVersion = 0
				aclVersion = 0
				ephemeralOwner = 0x0
				dataLength = 4
				numChildren = 2

		get path
			* 查看指定的节点信息
				data(我就是节点的数据)
				cZxid = 0x20000000d
				ctime = Thu Jan 03 09:39:15 CST 2019
				mZxid = 0x20000000d
				mtime = Thu Jan 03 09:39:15 CST 2019
				pZxid = 0x20000000d
				cversion = 0
				dataVersion = 0
				aclVersion = 0
				ephemeralOwner = 0x0
				dataLength = 11
				numChildren = 0
		
		stat path
			* 查看指定节点的信息
			* 跟 get一样,不过它少了节点的数据信息

		set path data [version] 
			* 更新指定的节点
				pah 节点
				data 新的数据
				version 乐观锁(dataVersion)
			
			* 乐观锁参数不是必须的,如果存在乐观锁,会判断版本号,如果版本不符合,则更新失败
		
		delete path [version]
			* 删除指定的节点
				path 节点
				version 乐观锁(dataVersion)
			* 乐观锁参数不是必须的,如果存在乐观锁,会判断版本号,如果版本不符合,则删除失败
			* 如果删除的节点存在子节点的话,那必须要先删除全部子节点


		rmr path
			* 可以递归的删除指定节点
			* 慎重!!!
	
	# 节点的限制 quota
		setquota -n|-b val path
			* 设置节点的限制
				path 节点
				-n 子节点的最大数量(如果值为-1则无限制子节点数量)
				-b 数据的最大长度(如果值为-1则表示不限制数据量大小???)
				val 子节点的最大数量/数据的最大长度 值
			* 设置root节点只能有10个子节点 :setquota -n 10 /root

		listquota path
			* 查看指定节点的 quota
				absolute path is /zookeeper/quota/root0000000000/zookeeper_limits
				Output quota for /root0000000000 count=10,bytes=-1
					* 节点的约束
				Output stat for /root0000000000 count=3,bytes=21
					* 当前节点的信息(count是包含了自己的,就是该值最少为1)
				
				count 子节点的最大数量
				bytes 当前节点的最大数据存储空间
		
		delquota -n|-b path
			* 删除指定节点的指定限制
				path 路径
				-n 删除子节数量的限制
				-b 删除节点数据的大小限制
		
		* quota 的限制不是强制的
		* 例如:限制了子节点只能有2个,实际上添加3个或者多个子节点,还是会成功
		* 只是会在日志中有警告信息,需要我们手动捕获警告信息,自己处理


--------------------------------
Watcher 机制					|
--------------------------------
	stat path [watch]
		* 监听指定节点的删除,创建,数据修改事件

		* NodeCreated(节点被创建)
			* 如果节点不存在,会提示:Node does not exist,不过仍然可以监听
		* NodeDeleted(节点被删除)
		* NodeDataChanged(数据发生了变化)

	ls path [watch]
	ls2 path [watch]
		* 监听子节点增删的事件(不能监听子节点的数据变化事件)

		* NodeChildrenChanged(子节点添加/删除)

	get path [watch]
		* 监听节点数据的变化事件
		* 当该节点数据发生变化,该监听会收到事件通知

		* NodeDataChanged(数据发生了变化)
		* NodeDeleted(节点被删除)

	* watch 其实就是随便输入一个字符
		


--------------------------------
其他							|
--------------------------------
	# 历史命令
		history
			* 在客户端执行的命令记录
				49 - delquota -n /root0000000000
				50 - delquota -n /root0000000000
				51 - setquota -n 1 /root0000000000
				52 - setquota -n 1 /root0000000000
				53 - history
				54 - listquota /root0000000000
				55 - setquota -n 10 /root0000000000
				56 - setquota -n -b 4~10 /root0000000000
				57 - setquota -n -b 10 /root0000000000
				
			* 前面的数字表示命名的编号

		redo number
			* 重复执行指定编号的命令
				number 命令的编号
			