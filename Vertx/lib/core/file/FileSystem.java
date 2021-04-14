---------------------
FileSystem 
---------------------
	# 异步的文件系统接口： interface FileSystem


---------------------
抽象 
---------------------
	FileSystem copy(String from, String to, Handler<AsyncResult<Void>> handler);
	Future<Void> copy(String from, String to);
	FileSystem copy(String from, String to, CopyOptions options, Handler<AsyncResult<Void>> handler);
	Future<Void> copy(String from, String to, CopyOptions options);
	FileSystem copyBlocking(String from, String to) ;
		* 复制文件

	FileSystem copyRecursive(String from, String to, boolean recursive, Handler<AsyncResult<Void>> handler);
	Future<Void> copyRecursive(String from, String to, boolean recursive);
	FileSystem copyRecursiveBlocking(String from, String to, boolean recursive) ;
	FileSystem move(String from, String to, Handler<AsyncResult<Void>> handler);
	Future<Void> move(String from, String to);
	FileSystem move(String from, String to, CopyOptions options, Handler<AsyncResult<Void>> handler);
	Future<Void> move(String from, String to, CopyOptions options);
	FileSystem moveBlocking(String from, String to) ;
	FileSystem truncate(String path, long len, Handler<AsyncResult<Void>> handler);
	Future<Void> truncate(String path, long len);
	FileSystem truncateBlocking(String path, long len) ;
	FileSystem chmod(String path, String perms, Handler<AsyncResult<Void>> handler);
	Future<Void> chmod(String path, String perms);
	FileSystem chmodBlocking(String path, String perms) ;
	FileSystem chmodRecursive(String path, String perms, String dirPerms, Handler<AsyncResult<Void>> handler);
	Future<Void> chmodRecursive(String path, String perms, String dirPerms);
	FileSystem chmodRecursiveBlocking(String path, String perms, String dirPerms) ;
	FileSystem chown(String path, @Nullable String user, @Nullable String group, Handler<AsyncResult<Void>> handler);
	Future<Void> chown(String path, @Nullable String user, @Nullable String group);
	FileSystem chownBlocking(String path, @Nullable String user, @Nullable String group) ;
	FileSystem props(String path, Handler<AsyncResult<FileProps>> handler);
	Future<FileProps> props(String path);
	FileProps propsBlocking(String path) ;
	FileSystem lprops(String path, Handler<AsyncResult<FileProps>> handler);
	Future<FileProps> lprops(String path);
	FileProps lpropsBlocking(String path) ;
	FileSystem link(String link, String existing, Handler<AsyncResult<Void>> handler);
	Future<Void> link(String link, String existing);
	FileSystem linkBlocking(String link, String existing) ;
	FileSystem symlink(String link, String existing, Handler<AsyncResult<Void>> handler);
	Future<Void> symlink(String link, String existing);
	FileSystem symlinkBlocking(String link, String existing) ;
	FileSystem unlink(String link, Handler<AsyncResult<Void>> handler);
	Future<Void> unlink(String link);
	FileSystem unlinkBlocking(String link) ;
	FileSystem readSymlink(String link, Handler<AsyncResult<String>> handler);
	Future<String> readSymlink(String link);
	String readSymlinkBlocking(String link) ;
	FileSystem delete(String path, Handler<AsyncResult<Void>> handler);
	Future<Void> delete(String path);
	FileSystem deleteBlocking(String path) ;
	FileSystem deleteRecursive(String path, boolean recursive, Handler<AsyncResult<Void>> handler);
	Future<Void> deleteRecursive(String path, boolean recursive);
	FileSystem deleteRecursiveBlocking(String path, boolean recursive) ;
	FileSystem mkdir(String path, Handler<AsyncResult<Void>> handler);
	Future<Void> mkdir(String path);
	FileSystem mkdirBlocking(String path) ;
	FileSystem mkdir(String path, String perms, Handler<AsyncResult<Void>> handler);
	Future<Void> mkdir(String path, String perms);
	FileSystem mkdirBlocking(String path, String perms) ;
	FileSystem mkdirs(String path, Handler<AsyncResult<Void>> handler);
	Future<Void> mkdirs(String path);
	FileSystem mkdirsBlocking(String path) ;
	FileSystem mkdirs(String path, String perms, Handler<AsyncResult<Void>> handler);
	Future<Void> mkdirs(String path, String perms);
	FileSystem mkdirsBlocking(String path, String perms) ;
	FileSystem readDir(String path, Handler<AsyncResult<List<String>>> handler);
	Future<List<String>> readDir(String path);
	List<String> readDirBlocking(String path) ;
	FileSystem readDir(String path, String filter, Handler<AsyncResult<List<String>>> handler);
	Future<List<String>> readDir(String path, String filter);
	List<String> readDirBlocking(String path, String filter) ;

	FileSystem readFile(String path, Handler<AsyncResult<Buffer>> handler);
	Future<Buffer> readFile(String path);
	Buffer readFileBlocking(String path) ;
		* 读取文件内容到Buffer

	FileSystem writeFile(String path, Buffer data, Handler<AsyncResult<Void>> handler);
	Future<Void> writeFile(String path, Buffer data);
	FileSystem writeFileBlocking(String path, Buffer data) ;
		* 写入文件

	FileSystem open(String path, OpenOptions options, Handler<AsyncResult<AsyncFile>> handler);
	Future<AsyncFile> open(String path, OpenOptions options);
	AsyncFile openBlocking(String path, OpenOptions options);
		* 打开文件，指定配置

	FileSystem createFile(String path, Handler<AsyncResult<Void>> handler);
	Future<Void> createFile(String path);
	FileSystem createFileBlocking(String path) ;
	FileSystem createFile(String path, String perms, Handler<AsyncResult<Void>> handler);
	Future<Void> createFile(String path, String perms);
	FileSystem createFileBlocking(String path, String perms) ;
	FileSystem exists(String path, Handler<AsyncResult<Boolean>> handler);
	Future<Boolean> exists(String path);
	boolean existsBlocking(String path) ;
	FileSystem fsProps(String path, Handler<AsyncResult<FileSystemProps>> handler);
	Future<FileSystemProps> fsProps(String path);
	FileSystemProps fsPropsBlocking(String path) ;
	FileSystem createTempDirectory(String prefix, Handler<AsyncResult<String>> handler);
	Future<String> createTempDirectory(String prefix);
	String createTempDirectoryBlocking(String prefix);
	FileSystem createTempDirectory(String prefix, String perms, Handler<AsyncResult<String>> handler);
	Future<String> createTempDirectory(String prefix, String perms);
	String createTempDirectoryBlocking(String prefix, String perms);
	FileSystem createTempDirectory(String dir, String prefix, String perms, Handler<AsyncResult<String>> handler);
	Future<String> createTempDirectory(String dir, String prefix, String perms);
	String createTempDirectoryBlocking(String dir, String prefix, String perms);
	FileSystem createTempFile(String prefix, String suffix, Handler<AsyncResult<String>> handler);
	Future<String> createTempFile(String prefix, String suffix);
	String createTempFileBlocking(String prefix, String suffix);
	FileSystem createTempFile(String prefix, String suffix, String perms, Handler<AsyncResult<String>> handler);
	Future<String> createTempFile(String prefix, String suffix, String perms);
	String createTempFileBlocking(String prefix, String suffix, String perms);
	FileSystem createTempFile(String dir, String prefix, String suffix, String perms, Handler<AsyncResult<String>> handler);
	Future<String> createTempFile(String dir, String prefix, String suffix, String perms);
	String createTempFileBlocking(String dir, String prefix, String suffix, String perms);


---------------------
静态 
---------------------