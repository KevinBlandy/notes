--------------------------------
属性							|
--------------------------------
	# java.nio.file.attribute
		* 支持很详细的属性设置,包括权限等等
	# 属性的最顶层接口
	# 体系
		AttributeView
			|-FileAttributeView(文件属性接口)
				|-BasicFileAttributeView
					* 基本文件属性接口
					|-PosixFileAttributes
						* 软连接文件属性接口
				|-UserDefinedFileAttributeView
					* 用户自定义属性接口
				|-FileOwnerAttributeView
					* 文件所有者属性接口
					|-AclFileAttributeView
						* acl权限属性接口
			|-FileStoreAttributeView(磁盘分区属性接口)
		
		FileAttribute
			# 属性接口
			# 俩抽象方法
				String name();
				T value();

		PosixFilePermission
			# 权限属性枚举类
			# 所有者权限
				OWNER_READ,
				OWNER_WRITE,
				OWNER_EXECUTE,
			# 所属组权限
				GROUP_READ,
				GROUP_WRITE,
				GROUP_EXECUTE,
			# 其他人权限
				OTHERS_READ,
				OTHERS_WRITE,
				OTHERS_EXECUTE;

		PosixFilePermissions
			# 权限属性对象工具类
			# 无法实例化对象,都是静态方法
				Set<PosixFilePermission>	fromString(String perms);
					# 根据Linux的权限设置字符串,来创建一组 PosixFilePermission 对象
					# "rw-------"
				FileAttribute<Set<PosixFilePermission>>	asFileAttribute(Set<PosixFilePermission> perms);
					# 把一组 PosixFilePermission 转换为文件属性对象

--------------------------------
BasicFileAttributeView 接口方法	|
--------------------------------

BasicFileAttributes readAttributes();	
						* 获取文件属性 
void				setTimes(FileTime lastModifiedTime,FileTime lastAccessTime, FileTime createTime) 
						* 直接设置仨个时间相关的属性


--------------------------------
BasicFileAttributes				|
--------------------------------
	# 文件属性描述对象
	# 获取
		 BasicFileAttributes basicFileAttributes = Files.readAttributes(path,BasicFileAttributes.class);
	# 实例方法
		FileTime lastModifiedTime();
		FileTime lastAccessTime();
		FileTime creationTime();
		boolean isRegularFile();
		boolean isDirectory();
		boolean isSymbolicLink();
		boolean isOther();
		long size();
		Object fileKey();


--------------------------------
获取文件属性					|
--------------------------------
	Path path = Paths.get("/temp/demo.txt");
	1,直接获取指定的属性
		long size = (Long) Files.getAttribute(path, "basic:size", java.nio.file.LinkOption.NOFOLLOW_LINKS);  

	2,根据接口类型,获取属性View
		<V extends FileAttributeView> V Files.getFileAttributeView(Path path,Class<V> type,LinkOption... options)
			

--------------------------------
设置文件属性					|
--------------------------------
	Path path = Paths.get("/temp/demo.txt");
	FileTime fileTime = FileTime.fromMillis(new Date().getTime());  
	Files.setAttribute(path, "basic:lastModifiedTime", fileTime, LinkOption.NOFOLLOW_LINKS);	//最后修改时间
	Files.setAttribute(path, "basic:creationTime", fileTime, LinkOption.NOFOLLOW_LINKS);		//创建时间
	Files.setAttribute(path, "basic:lastAccessTime", fileTime, LinkOption.NOFOLLOW_LINKS);		//最后访问时间

--------------------------------
修改文件属性					|
--------------------------------
	long time = System.currentTimeMillis();  
	FileTime fileTime = FileTime.fromMillis(time);  
	BasicFileAttributeView basicFileAttributeView = Files.getFileAttributeView(path, BasicFileAttributeView.class); 
	basicFileAttributeView.setTimes(fileTime, fileTime, fileTime); 

---------------------------
权限设置					|
---------------------------
	Set<PosixFilePermission> perms = PosixFilePermissions.fromString("rw-------");  
	FileAttribute<Set<PosixFilePermission>> attrs = PosixFilePermissions.asFileAttribute(perms);
	Files.setPosixFilePermissions(path,attrs)

---------------------------
获取ACL权限					|
---------------------------
	AclFileAttributeView aclview = Files.getFileAttributeView(path, AclFileAttributeView.class);  
	List<AclEntry> acllist = aclview.getAcl();  