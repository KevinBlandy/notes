----------------------------
shutil						|
----------------------------
	* 文件的高级操作

----------------------------
shutil-模块属性				|
----------------------------

----------------------------
shutil-模块方法				|
----------------------------
	None copyfileobj(f1 ,f2)
		* 拷贝文件,从f1拷贝到f2
		* f1/2应该是由 open() 函数返回的对象
		* 关键字参数:
				length 
					* 拷贝的长度
	
	str copyfile(src, dst)
		* 拷贝文件,从src拷贝到dst,返回dst的路径
		* 以字符串的形式指定的文件
	
	str copy(src, dst)
		* 拷贝文件和权限,返回dst的路径
	
	copy2(src, dst)
		* 拷贝文件和状态信息

	str copytree((src, dst, symlinks=False, ignore=None, copy_function=copy2, ignore_dangling_symlinks=False, dirs_exist_ok=False)
		* 递归的去拷贝文件夹,返回 dst 路径
		* 关键字参数
			symlinks
				* 是否要跟踪连接文件
			ignore
				* 过滤器,忽略指定名称的文件
				* ignore_patterns('*.pyc', 'tmp*')

	ignore_patterns()
		* 返回文件过滤器,支持通配符
		* 变长数组,可以传递N多个值
		* ignore_patterns('*.pyc', 'tmp*')
	
	None rmtree(dst)
		* 递归的去删除文件
		* '慎重操作,相当于Linux : rm -rf '
	
	str move(src, dst)
		* 递归的去移动文件,它类似mv命令,其实就是重命名
		* 返回 dst 路径
	
	str make_archive(base_name, format,...)
		* 创建压缩包,并且返回路径
		* base_name,指定压缩包的的绝对路径,名称(会自动的添加后缀名),如果仅仅指定名称没有目录,则表示在当前目录
		* format,压缩类型:"zip", "tar", "bztar","gztar"
		* 关键参数
			root_dir:要压缩的文件夹路径(默认当前目录)
			owner	:用户,默认当前用户
			group	:组,默认当前组
			logger	:用于记录日志,通常是logging.Logger对象
		* 'shutil 对压缩包的处理是通过调用ZipFile 和 TarFile两个模块来进行的。'
	

	copymode(src, dst)
		* 仅仅只把src的权限拷贝到dst仅拷贝权限
		* 内容,组,用户均不变
	
	copystat(src, dst)
		* 仅拷贝状态的信息
		* 包括:mode bits, atime, mtime, flags
	



