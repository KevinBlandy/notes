----------------------------
Files-Options				|
----------------------------
	# 父级接口
	# 子接口

----------------------------
StandardCopyOption			|
----------------------------
	# 父级接口
		CopyOption
	# 枚举
		REPLACE_EXISTING,		//如果目标已经存在,则覆盖目标
		COPY_ATTRIBUTES,		//复制文件的属性
		ATOMIC_MOVE				//原子性操作,要么移动成功,要么文件还在原处


----------------------------
StandardOpenOption			|
----------------------------
	# 父级接口
		OpenOption	
	# 枚举
		READ,					//打开阅读权限
		WRITE,					//打开写权限
		APPEND,					//在末尾添加,不覆盖以前的数据
		TRUNCATE_EXISTING,		//如果文件已经存在，并且打开进行WRITE 访问，则其长度将被截断为0。
		CREATE,					//如果不存在,则创建新的,如果存在,则从头开始写入内容
		CREATE_NEW,				//创建一个新的文件，如果该文件已经存在,则抛出异常。
		DELETE_ON_CLOSE,		//在jvm关闭的时候删除文件
		SPARSE,					//稀疏文件
		SYNC,					//要求将文件内容或元数据的每次更新都同步写入底层存储设备。
		DSYNC;					//要求将文件内容的每次更新都与底层存储设备同步写入

----------------------------
LinkOption					|
----------------------------
	# 父级接口
		CopyOption,OpenOption
	# 枚举
		NOFOLLOW_LINKS;			//不跟踪连接
		

----------------------------
FileVisitOption					|
----------------------------
		FOLLOW_LINKS			//跟踪连接
	
