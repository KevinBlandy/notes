	File 常见方法——————构造方法颇多，自己研究
	静态方法
		File.listRoots();
			|--返回的是一个 File[] 类型的数组.是目前PC上所有的有效盘符路径。
	
	实例方法
	File f = new File(目录/文件);
		toPath();
		f.createNewFile();//创建f文件。如果成功就返回 true 如果文件(名字)存在则不创建，返回false。和输出流不一样。
		f.exists();//判断初始化的路劲是否存在
			|--在判断文件。是否为目录，或者文件时。必须先判断。这个东西是否存在。通过 exists();判断。
		f.canExcute();//判断这个文件是否可以被执行。
		f.canWrite();//判断文件是否可读
		f.canRead();//判断文件是否可写
		f.isHidden();//判断文件是否为隐藏文件。
		f.isAbsolute();//判断文件是否为绝对路径。
		f.mkdir();//创建此目录(文件夹)。f只能是一级目录
		f.mkdirs();//创建此目录(文件夹)。f可以是多级目录。
		f.delete();//删除此目录。删除失败返回false.
			|--File 的删除方法不走回收站。
		f.deleteOnExit();//在虚拟机停止运行(程序退出时)的时候。删除掉指定目录。就算是发生异常也会删除f目录。
		f.isDirectory();//判断f是否为目录，如果目录不存在也返回false
		f.isFile();//判断f是否是一个文件。如果文件不存在也返回false
			|--在判断文件。是否为目录，或者文件时。必须先判断。这个东西是否存在。通过 exists();判断。
		separator();//[static]跨平台分隔符。增强了跨平台性。静态方法，直接类名调用。
			|--File f = new File("D:"+File.separator()+"abc"+File.separator()+"a.txt");
		f.getName();//返回f路劲表示的文件或者目录名称。返回String类型！
		f.getPath();//把f表示的路径转换成字符串。
		f.getParent()://返回f的父目录(绝对路劲)名字符串。如果f没有指定目录。则返回null。 如果相对路劲中有上层目录那么该目录就是返回结果。
		f.getAbsolutePath();//获取f表示的绝对路径。它返回的是字符串表达形式
		f.getAbsoluteFile();//获取f表示的绝对路劲。注意，它返回的是一个 File类型的对象！
		f.lastModified();//返回文件被修改的最后一次的时间。返回值是long。(毫秒值)
		f.length();//返回f表示的目录或者文件的大小.返回值类型是long
		f.renameTo(f1);//把f1路劲文件名字修改成f1路径文件名字。有点像剪切。！！！
		f.createTempFile();//创建一个临时文件夹,一般都会同时设置deleteOnExit();

		f.list();
			|--返回的是一个String[]类型的字符串数组。里面包含了f目录下的所有文件以及目录名字(包含隐藏文件,不包含子目录文件)。如果f是文件的话。会抛出空指针异常。
		f.list(匿名对象的文件过滤器);
			|--把f目录下符合过滤器规则(文件名)。的文件名添加到 String[] 类型的数组！并返回。
		f.listFiles();
			|--返回的是f目录下的所有文件对象数组。 File[]
		f.listFiles(FilenameFilter filenameFilter);
			|--把f目录下符合过滤器规则的文件(文件名).添加到 File[] 类型的数组中。并返回。