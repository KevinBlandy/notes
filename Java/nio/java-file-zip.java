--------------------------------
关于zip的操作					|
--------------------------------
	# FileSystems 静态类获取zip文件系统对象
		FileSystem		FileSystems.newFileSystem(Path path, ClassLoader loader);
							* 使用 FileSystems 工具类,创建压缩包的path创建 FileSystem 对象
							* 该对象包含了压缩文件的所有文件
	# Path 对象实例获取zip文件系统对象 
		FileSystem		getFileSystem();
							* 获取文件系统对象

--------------------------------
读取内部文件					|
--------------------------------
Path	fileSystem.getPath(String filePath);
		* 文件相对路径
		* "/"表示压缩包的根路径

--------------------------------
遍历zip文件						|
--------------------------------
	 Files.walkFileTree(fileSystem.getPath("/"), new FileVisitor<Path>() {
		@Override
		public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
			System.out.println(dir);
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
			System.out.println(file);
			return FileVisitResult.CONTINUE;
		}
		@Override
		public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
			return FileVisitResult.CONTINUE;
		}
	});

	
